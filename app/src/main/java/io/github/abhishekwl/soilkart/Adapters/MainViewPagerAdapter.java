package io.github.abhishekwl.soilkart.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import io.github.abhishekwl.soilkart.Fragments.CategoryFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> categoryNamesArrayList = new ArrayList<>();

    public MainViewPagerAdapter(FragmentManager fragmentManager, ArrayList<String> categoryNamesArrayList) {
        super(fragmentManager);
        this.categoryNamesArrayList = categoryNamesArrayList.isEmpty()? new ArrayList<>() : categoryNamesArrayList;
    }

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setCategoryName(categoryNamesArrayList.get(i));
        return categoryFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryNamesArrayList.get(position);
    }

    @Override
    public int getCount() {
        return categoryNamesArrayList.size();
    }
}
