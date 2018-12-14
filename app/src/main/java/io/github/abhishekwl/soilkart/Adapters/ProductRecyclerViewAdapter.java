package io.github.abhishekwl.soilkart.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    private ArrayList<Item> itemArrayList;
    private Context rootContext;

    public ProductRecyclerViewAdapter(Context context, ArrayList<Item> itemArrayList) {
        this.rootContext = context;
        this.itemArrayList = (itemArrayList==null || itemArrayList.isEmpty()) ? new ArrayList<>() : itemArrayList;
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.ProductViewHolder productViewHolder, int i) {
        Item item = itemArrayList.get(i);
        productViewHolder.bind(item, productViewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.productListItemAddQuantityButton)
        FloatingActionButton itemAddButton;
        @BindView(R.id.productListItemRemoveQuantityButton)
        FloatingActionButton itemRemoveButton;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void bind(Item item, View itemView) {
            Glide.with(itemView.getContext()).load(item.getImage()).into(itemImageView);
            itemNameTextView.setText(item.getName());
            itemCostTextView.setText("\u20b9".concat(" ").concat(Double.toString(item.getPrice())));
            itemUnitTextView.setText(item.getUnit());
            itemQuantityTextView.setText(Integer.toString(item.getQuantity()));

            itemAddButton.setOnClickListener(v -> {
                item.setQuantity(item.getQuantity()+1);
                itemQuantityTextView.setText(Integer.toString(item.getQuantity()));
            });

            itemRemoveButton.setOnClickListener(v -> {
                if (item.getQuantity()>0) item.setQuantity(item.getQuantity()-1);
                itemQuantityTextView.setText(Integer.toString(item.getQuantity()));
            });
        }
    }
}
