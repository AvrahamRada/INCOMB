package android_project.incomb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    TextInputLayout mFullName, mEmail, mPassword, mPhone;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        //Spinner
        Spinner spinner = findViewById(R.id.columnSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.columns_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mFullName = (TextInputLayout) findViewById(R.id.full_name);
        mEmail = (TextInputLayout) findViewById(R.id.email);
        mPassword = (TextInputLayout) findViewById(R.id.password);
        mPhone = (TextInputLayout) findViewById(R.id.phone);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.move_to_sign_in).setOnClickListener(this);
        findViewById(R.id.registration).setOnClickListener(this);

        if (fAuth.getCurrentUser() != null) { // if current user is already present we dont want to re-create
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_to_sign_in:
                login();
                break;

            case R.id.registration:
                registration();
                break;

            default:
                break;
        }
    }

    private void login() {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    private void registration() {
        final String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return;
        }

        if (!checkPassword(password)) {
            mPassword.setError("Invalide Password (length >=6, A-Z,a-z,0-9).");
            return;
        }

        mPassword.setError("");
        progressBar.setVisibility(View.VISIBLE);

        // register the user in firebase
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { // User Created Successfully
                    Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    mPassword.setError("");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


}
