package io.github.abhishekwl.soilkart.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.abhishekwl.soilkart.Helpers.Constants;
import io.github.abhishekwl.soilkart.Models.Order;
import io.github.abhishekwl.soilkart.R;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrderViewHolder> {

    private ArrayList<Order> orderArrayList;

    public OrdersRecyclerViewAdapter(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrdersRecyclerViewAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list_item, viewGroup, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersRecyclerViewAdapter.OrderViewHolder orderViewHolder, int i) {
        Order order = orderArrayList.get(i);
        orderViewHolder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.orderListItemPriceTextView)
        TextView orderGrandTotalTextView;
        @BindView(R.id.orderListItemOrderDateAndTimeTextView)
        TextView orderDateAndTimeTextView;
        @BindView(R.id.orderListItemProductsTextView)
        TextView orderProductsTextView;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Order order) {
            double grandTotal = Constants.computeGrandTotal(order.getItemArrayList());
            orderGrandTotalTextView.setText("\u20b9".concat(" ").concat(Double.toString(grandTotal)));
        }
    }
}
