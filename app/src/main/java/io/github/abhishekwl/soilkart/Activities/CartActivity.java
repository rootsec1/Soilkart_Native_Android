package io.github.abhishekwl.soilkart.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Adapters.CartRecyclerViewAdapter;
import io.github.abhishekwl.soilkart.Helpers.Constants;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiClient;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiInterface;
import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.Models.Order;
import io.github.abhishekwl.soilkart.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.cartItemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.cartGrandTotalTextView)
    TextView grandTotalTextView;
    @BindView(R.id.cartCardView)
    CardView cartCardView;
    @BindView(R.id.cartGstTextView)
    TextView cartGstTextView;
    @BindView(R.id.cartDeliveryChargesTextView)
    TextView cartDeliveryChargesTextView;
    @BindView(R.id.cartCheckoutFab)
    FloatingActionButton cartCheckoutFab;

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private RetrofitApiInterface retrofitApiInterface;
    private ArrayList<Item> itemArrayList = new ArrayList<>();
    private CartRecyclerViewAdapter cartRecyclerViewAdapter;
    private MaterialDialog materialDialog;
    private double rawTotal;

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

    @SuppressLint("SetTextI18n")
    private void initializeViews() {
        cartGstTextView.setText(Double.toString(Constants.GST_PERCENTAGE).concat("%"));
        cartDeliveryChargesTextView.setText(Double.toString(Constants.DELIVERY_CHARGE_PERCENTAGE).concat("%"));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        itemsRecyclerView.setAdapter(cartRecyclerViewAdapter);
    }

    private void checkout() {
        materialDialog = new MaterialDialog.Builder(CartActivity.this)
                .title(R.string.app_name)
                .content("Requesting new order")
                .progress(true, 0)
                .titleColor(Color.BLACK)
                .contentColorRes(R.color.colorTextDark)
                .show();
        retrofitApiInterface.createNewOrder(firebaseAuth.getUid(), itemArrayList).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
                if (response.body() != null) postOrderCreation(response.body());
                else
                    Snackbar.make(itemsRecyclerView, "There has been an error requesting a new order. Please try again later :(", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
                Snackbar.make(itemsRecyclerView, t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void postOrderCreation(Order currentOrder) {
        materialDialog = new MaterialDialog.Builder(CartActivity.this)
                .title(R.string.app_name)
                .content(currentOrder.toString())
                .titleColor(Color.BLACK)
                .contentColorRes(R.color.colorTextDark)
                .show();
    }

    @SuppressLint("StaticFieldLeak")
    private class ComputeGrandTotal extends AsyncTask<ArrayList<Item>, Void, Double> {

        @SafeVarargs
        @Override
        protected final Double doInBackground(ArrayList<Item>... arrayLists) {
            return Constants.computeGrandTotal(arrayLists[0]);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            grandTotalTextView.setText("\u20b9 ".concat(Double.toString(aDouble)));
        }
    }

    @OnClick(R.id.cartCheckoutFab)
    public void onCheckoutButtonPress() {
        if (rawTotal == 0) {
            Snackbar.make(itemsRecyclerView, "You have no items in your cart to checkout :(", Snackbar.LENGTH_LONG)
                    .addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            finish();
                        }
                    }).show();
        } else {
            materialDialog = new MaterialDialog.Builder(CartActivity.this)
                    .title(R.string.app_name)
                    .content("The amount payable is " + Double.toString(rawTotal) + ".\nAre you sure you want to checkout?")
                    .positiveText("YES")
                    .negativeText("NO")
                    .titleColor(Color.BLACK)
                    .contentColorRes(R.color.colorTextDark)
                    .positiveColorRes(R.color.colorPrimaryDark)
                    .negativeColorRes(android.R.color.tab_indicator_text)
                    .onPositive((dialog, which) -> checkout())
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
