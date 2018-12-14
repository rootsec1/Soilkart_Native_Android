package io.github.abhishekwl.soilkart.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Adapters.MainViewPagerAdapter;
import io.github.abhishekwl.soilkart.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainTabLayout)
    TabLayout mainTabLayout;
    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;

    private Unbinder unbinder;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private ArrayList<String> categoriesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(MainActivity.this);
        categoriesArrayList.add("Vegetables");
        categoriesArrayList.add("Fruits");
        categoriesArrayList.add("Grains");
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), categoriesArrayList);
    }

    private void initializeViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(getSupportActionBar()).setElevation(0f);
            mainTabLayout.setElevation(8f);
        }
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
