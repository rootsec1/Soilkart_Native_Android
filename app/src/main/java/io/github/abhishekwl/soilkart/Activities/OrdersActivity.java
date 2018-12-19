package io.github.abhishekwl.soilkart.Activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiClient;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiInterface;
import io.github.abhishekwl.soilkart.R;

public class OrdersActivity extends AppCompatActivity {

    @BindView(R.id.ordersRecyclerView)
    RecyclerView ordersRecyclerView;
    @BindView(R.id.ordersSwipeRefreshLayout)
    SwipeRefreshLayout ordersSwipeRefreshLayout;

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private RetrofitApiInterface retrofitApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(OrdersActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        retrofitApiInterface = RetrofitApiClient.getRetrofitClient(getApplicationContext()).create(RetrofitApiInterface.class);
    }

    private void initializeViews() {

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
