package io.github.abhishekwl.soilkart.Activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Adapters.ProductRecyclerViewAdapter;
import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.R;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;

    private Unbinder unbinder;
    private ProductRecyclerViewAdapter productRecyclerViewAdapter;
    private ArrayList<Item> itemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(SearchActivity.this);
        itemArrayList = getIntent().getParcelableArrayListExtra("ITEMS");
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(getApplicationContext(), itemArrayList);
    }

    private void initializeViews() {
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setAdapter(productRecyclerViewAdapter);
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
            Intent cartIntent = new Intent(SearchActivity.this, CartActivity.class);
            cartIntent.putExtra("ITEMS", itemArrayList);
            startActivity(cartIntent);
        }
    }

    @OnClick(R.id.searchCheckoutFab)
    public void onCheckoutFabPress() {
        new FilterSelectedItems().execute(itemArrayList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seach_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuItemSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent mainActivityIntent = new Intent(SearchActivity.this, MainActivity.class);
                mainActivityIntent.putExtra("ITEMS", itemArrayList);
                startActivity(mainActivityIntent);
                finish();
                break;
        }
        return true;
    }

    private void filter(String text) {
        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item: itemArrayList) if (item.getName().toLowerCase().contains(text.toLowerCase())) filteredItems.add(item);
        productRecyclerViewAdapter.filterList(filteredItems);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
