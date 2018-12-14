package io.github.abhishekwl.soilkart.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Adapters.ProductRecyclerViewAdapter;
import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    @BindView(R.id.categoryItemsRecyclerView)
    RecyclerView categoryItemsRecyclerView;

    private Unbinder unbinder;
    private View rootView;
    private String categoryName;
    private ArrayList<Item> itemArrayList;
    private ProductRecyclerViewAdapter productRecyclerViewAdapter;

    public CategoryFragment() {
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        initializeComponents();
        initializeViews();
        return rootView;
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(CategoryFragment.this, rootView);
        itemArrayList = new ArrayList<>();
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(rootView.getContext(), itemArrayList);
    }

    private void initializeViews() {
        categoryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        categoryItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryItemsRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));
        categoryItemsRecyclerView.setAdapter(productRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        unbinder = ButterKnife.bind(CategoryFragment.this, rootView);
    }
}
