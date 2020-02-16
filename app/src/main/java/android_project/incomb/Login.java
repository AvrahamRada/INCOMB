package android_project.incomb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.registration_btn).setOnClickListener(this);

        mEmail = (TextInputLayout)findViewById(R.id.login_email);
        mPassword = (TextInputLayout)findViewById(R.id.login_password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
    }

    private void registration() {
        Intent registrationActivityIntent = new Intent(this, Registration.class);
        startActivity(registrationActivityIntent);
        finish();
    }

    private boolean checkPassword(String password) {
        int countChar = 0,countNum = 0,i;
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
                String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(!checkPassword(password)) {
                    mPassword.setError("Invalide Password (length >=6, A-Z,a-z,0-9).");
                    return;
                }
                mPassword.setError("");
                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mPassword.setError("");
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
                break;

            case R.id.registration_btn:
                registration();
                break;

            default:
                break;
        }
    }
}
