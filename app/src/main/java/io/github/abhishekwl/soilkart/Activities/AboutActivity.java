package io.github.abhishekwl.soilkart.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiClient;
import io.github.abhishekwl.soilkart.Helpers.RetrofitApiInterface;
import io.github.abhishekwl.soilkart.R;

public class AboutActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private RetrofitApiInterface retrofitApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(AboutActivity.this);
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
