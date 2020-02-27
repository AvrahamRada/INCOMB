package android_project.incomb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout mEmail, mPassword;
    String userType, Phone;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.registration_btn).setOnClickListener(this);

        mEmail = (TextInputLayout) findViewById(R.id.login_email);
        mPassword = (TextInputLayout) findViewById(R.id.login_password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
    }

    private void registration() {
        Intent registrationActivityIntent = new Intent(this, Registration.class);
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
        // need to get the type from firebase and go to the right activity
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    onAuthSuccess(task.getResult().getUser());
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    mPassword.setError("");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        if (user != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("typeUser");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.getValue(String.class);
                    switch(type){
                        case "Fest":
                            startActivity(new Intent(getApplicationContext(), FindPlaceActivity.class));
                            break;
                        case "Guest":
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            break;
                        case "Host":
                            startActivity(new Intent(getApplicationContext(), RentPlace.class));
                            break;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Log.d(TAG, databaseError.getMessage());
                }
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
