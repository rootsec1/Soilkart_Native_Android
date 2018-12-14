package io.github.abhishekwl.soilkart.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.abhishekwl.soilkart.R;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.signInEmailAddressEditText)
    TextInputEditText signInEmailAddressEditText;
    @BindView(R.id.signInPasswordEditText)
    TextInputEditText signInPasswordEditText;
    @BindView(R.id.signInButton)
    Button signInButton;

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.WHITE);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_sign_in);

        initializeComponents();
        initializeViews();
    }

    private void initializeComponents() {
        unbinder = ButterKnife.bind(SignInActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initializeViews() {

    }

    private void notifyMessage(String message) {
        if (materialDialog!=null && materialDialog.isShowing()) materialDialog.dismiss();
        Snackbar.make(signInButton, message, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.signInButton)
    public void onSignInButtonPress() {
        String emailAddress = Objects.requireNonNull(signInEmailAddressEditText.getText()).toString();
        String password = Objects.requireNonNull(signInPasswordEditText.getText()).toString();

        materialDialog = new MaterialDialog.Builder(SignInActivity.this)
                .title(R.string.app_name)
                .content("Signing In..")
                .titleColorRes(android.R.color.black)
                .contentColorRes(R.color.colorTextDark)
                .progress(true, 0)
                .show();

        firebaseAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (materialDialog!=null && materialDialog.isShowing()) materialDialog.dismiss();
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
            else notifyMessage(Objects.requireNonNull(task.getException()).getMessage());
        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
