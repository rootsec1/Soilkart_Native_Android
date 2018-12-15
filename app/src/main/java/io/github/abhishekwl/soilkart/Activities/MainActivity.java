package io.github.abhishekwl.soilkart.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Adapters.MainViewPagerAdapter;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiClient;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiInterface;
import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainTabLayout)
    TabLayout mainTabLayout;
    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.mainProgressBar)
    ProgressBar mainProgressBar;

    private Unbinder unbinder;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private ArrayList<String> categoriesArrayList = new ArrayList<>();
    private ArrayList<Item> allItemsArrayList = new ArrayList<>();
    private RetrofitApiInterface retrofitApiInterface;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(MainActivity.this);
        mainProgressBar.setVisibility(View.VISIBLE);
        categoriesArrayList.add("Vegetables");
        categoriesArrayList.add("Fruits");
        categoriesArrayList.add("Grains");
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), categoriesArrayList);
        retrofitApiInterface = RetrofitApiClient.getRetrofitClient(getApplicationContext()).create(RetrofitApiInterface.class);
        fetchAllItems();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser()==null) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private void fetchAllItems() {
        mainProgressBar.setVisibility(View.VISIBLE);
        retrofitApiInterface.getAllItems().enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                mainProgressBar.setVisibility(View.GONE);
                if (response.body()==null || response.body().isEmpty()) Snackbar.make(mainViewPager, "We are currently experiencing issues, please try again later." ,Snackbar.LENGTH_LONG).show();
                else {
                    allItemsArrayList = response.body();
                    mainViewPager.setAdapter(mainViewPagerAdapter);
                    mainTabLayout.setupWithViewPager(mainViewPager);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                mainProgressBar.setVisibility(View.GONE);
                Snackbar.make(mainViewPager, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("RETRY", v -> fetchAllItems())
                        .setActionTextColor(Color.YELLOW)
                        .show();
            }
        });
    }

    private void initializeViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(getSupportActionBar()).setElevation(0f);
            mainTabLayout.setElevation(8f);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FilterSelectedItems extends AsyncTask<ArrayList<Item>, Void, ArrayList<Item>> {

        @Override
        protected ArrayList<Item> doInBackground(ArrayList<Item>... arrayLists) {
            ArrayList<Item> selectedItemsArrayList = new ArrayList<>();
            for (Item item: arrayLists[0]) if (item.getQuantity()>0) selectedItemsArrayList.add(item);
            return selectedItemsArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Item> itemArrayList) {
            super.onPostExecute(itemArrayList);
            if (itemArrayList==null || itemArrayList.isEmpty())
                Snackbar.make(mainViewPager, "You have no items in your cart :(", Snackbar.LENGTH_LONG)
                .setAction("DISMISS", v -> {})
                .setActionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                .show();
            else {
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                cartIntent.putExtra("ITEMS", itemArrayList);
                startActivity(cartIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSearch:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.putExtra("ITEMS", allItemsArrayList);
                startActivity(searchIntent);
                break;
            case R.id.menuItemCart:
                new FilterSelectedItems().execute(allItemsArrayList);
                break;
            case R.id.menuItemSignOut:
                firebaseAuth.signOut();
                break;
            case R.id.menuItemOrders:
                startActivity(new Intent(MainActivity.this, OrdersActivity.class));
                break;
        }
        return true;
    }

    public ArrayList<Item> getAllItemsArrayList() {
        return allItemsArrayList;
    }

    public void setAllItemsArrayList(ArrayList<Item> allItemsArrayList) {
        this.allItemsArrayList = allItemsArrayList;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
