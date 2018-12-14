package io.github.abhishekwl.soilkart.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    }

    private void initializeViews() {
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        itemsRecyclerView.setAdapter(cartRecyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
