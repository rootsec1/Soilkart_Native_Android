package io.github.abhishekwl.soilkart.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.R;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder> {

    private ArrayList<Item> itemArrayList;
    private Context rootContext;

    public CartRecyclerViewAdapter(Context context, ArrayList<Item> itemArrayList) {
        this.rootContext = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_item, viewGroup, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.CartViewHolder cartViewHolder, int i) {
        Item item = itemArrayList.get(i);
        cartViewHolder.bind(item, cartViewHolder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productListItemImageView)
        ImageView itemImageView;
        @BindView(R.id.productListItemNameTextView)
        TextView itemNameTextView;
        @BindView(R.id.productListItemCostTextView)
        TextView itemCostTextView;
        @BindView(R.id.productListItemUnitTextView)
        TextView itemUnitTextView;
        @BindView(R.id.productListItemQuantityTextView)
        TextView itemQuantityTextView;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void bind(Item item, Context context) {
            Glide.with(itemView.getContext()).load("https://www.vegetables.co.nz/assets/Uploads/vegetables.jpg").into(itemImageView);
            itemNameTextView.setText(item.getName());
            itemCostTextView.setText("\u20b9".concat(" ").concat(Double.toString(item.getPrice())));
            itemUnitTextView.setText(" / ".concat(item.getUnit()));
            itemQuantityTextView.setText(Integer.toString(item.getQuantity()));

        }
    }
}
