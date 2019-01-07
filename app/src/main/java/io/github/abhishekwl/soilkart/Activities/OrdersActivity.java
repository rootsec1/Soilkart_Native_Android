package io.github.abhishekwl.soilkart.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiClient;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiInterface;
import io.github.abhishekwl.soilkart.Models.Order;
import io.github.abhishekwl.soilkart.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    @BindView(R.id.ordersRecyclerView)
    RecyclerView ordersRecyclerView;
    @BindView(R.id.ordersSwipeRefreshLayout)
    SwipeRefreshLayout ordersSwipeRefreshLayout;
    @BindString(R.string.base_server_url)
    String baseServerUrl;

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private RetrofitApiInterface retrofitApiInterface;
    private MaterialDialog materialDialog;
    private ArrayList<Order> orderArrayList = new ArrayList<>();

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
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ordersRecyclerView.setHasFixedSize(true);
        ordersRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void performNetworkRequest() {
        retrofitApiInterface.getOrders(firebaseAuth.getUid()).enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                orderArrayList = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                notifyMessage(t.getMessage());
            }
        });
    }

    private void notifyMessage(String message) {
        if (materialDialog!=null && materialDialog.isShowing()) materialDialog.dismiss();
        Snackbar.make(ordersRecyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
