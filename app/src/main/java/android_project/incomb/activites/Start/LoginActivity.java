package android_project.incomb.activites.Start;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.MapAndPlacesActivity;
import android_project.incomb.activites.Fest.SearchPlaceActivity;
import android_project.incomb.activites.Guest.SearchEventActivity;
import android_project.incomb.activites.Host.MyPlaceActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout mEmail, mPassword;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.registration_btn).setOnClickListener(this);

        mEmail = (TextInputLayout) findViewById(R.id.login_email);
        mPassword = (TextInputLayout) findViewById(R.id.login_password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
    }

    private void registration() {
        Intent registrationActivityIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registrationActivityIntent);
        finish();
    }

    private void login() {
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return;
        }

        if (!checkPassword(password)) {
            mPassword.setError("Invalid Password (length >=6, A-Z,a-z,0-9).");
            return;
        }
        mPassword.setError("");
        progressBar.setVisibility(View.VISIBLE);

        // authenticate the user
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                onAuthSuccess(task.getResult().getUser());
            } else {
                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                mPassword.setError("");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        if (user != null) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        String userType = (String) documentSnapshot.get("typeUser");
                        switch (userType) {
                            case "Fest":
                                startActivity(new Intent(getApplicationContext(), SearchPlaceActivity.class));
                                //startActivity(new Intent(getApplicationContext(), MapAndPlacesActivity.class));
                                finish();
                                break;
                            case "Guest":
                                startActivity(new Intent(getApplicationContext(), SearchEventActivity.class));
                                finish();
                                break;
                            case "Host":
                                startActivity(new Intent(getApplicationContext(), MyPlaceActivity.class));
                                finish();
                                break;
                        }
                    })
                    .addOnFailureListener(e -> {
                        //to do handle failure
                    });
        }
    }

    private boolean checkPassword(String password) {
        int countChar = 0, countNum = 0, i;
        if (TextUtils.isEmpty(password) || (password.length() < 6))
            return false;
        for (i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (is_Numeric(ch))
                countNum++;
            else if (is_Letter(ch))
                countChar++;
            else
                return false;
        }
        return ((countChar >= 2) && (countNum >= 2));
    }

    public boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return ((ch >= 'A') && (ch <= 'Z'));
    }

    public boolean is_Numeric(char ch) {
        return ((ch >= '0') && (ch <= '9'));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                login();
                break;

            case R.id.registration_btn:
                registration();
                break;

            default:
                break;
        }
    }
}
