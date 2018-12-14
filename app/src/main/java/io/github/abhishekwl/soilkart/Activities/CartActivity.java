package io.github.abhishekwl.soilkart.Activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Adapters.CartRecyclerViewAdapter;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiClient;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiInterface;
import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.R;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.cartItemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.cartGrandTotalTextView)
    TextView grandTotalTextView;

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private RetrofitApiInterface retrofitApiInterface;
    private ArrayList<Item> itemArrayList = new ArrayList<>();
    private CartRecyclerViewAdapter cartRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(CartActivity.this);
        retrofitApiInterface = RetrofitApiClient.getRetrofitClient(getApplicationContext()).create(RetrofitApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        itemArrayList = getIntent().getParcelableArrayListExtra("ITEMS");
        cartRecyclerViewAdapter = new CartRecyclerViewAdapter(getApplicationContext(), itemArrayList);
        new ComputeGrandTotal().execute(itemArrayList);
    }

    private void initializeViews() {
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        itemsRecyclerView.setAdapter(cartRecyclerViewAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class ComputeGrandTotal extends AsyncTask<ArrayList<Item>, Void, Double> {

        @Override
        protected Double doInBackground(ArrayList<Item>... arrayLists) {
            double total = 0;
            for (Item item: arrayLists[0]) total += (item.getPrice()-(item.getDiscountPercentage()*item.getPrice())) * item.getQuantity();
            return total;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            grandTotalTextView.setText("\u20b9 ".concat(Double.toString(aDouble)));
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
