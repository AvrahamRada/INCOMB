package android_project.incomb.activites.Start;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.MapAndPlacesActivity;
import android_project.incomb.activites.Guest.SearchEventActivity;
import android_project.incomb.activites.Host.MyPlaceActivity;
import android_project.incomb.entities.Person;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextInputLayout mFullName, mEmail, mPassword, mPhone;
    private String userType;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Spinner
        Spinner spinner = findViewById(R.id.columnSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.columns_array, android.R.layout.simple_spinner_item);
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

    //Spinner - get the user type
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userType = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), userType, Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void registration() {
        final String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();
        final String name = mFullName.getEditText().getText().toString().trim();
        final String phone = mPhone.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            mEmail.setError("Name is Required.");
            return;
        }

        if (!checkPassword(password)) {
            mPassword.setError("Invalid Password (length >=6, A-Z,a-z,0-9).");
            return;
        }

        if (phone.isEmpty() || phone.length() != 10) {
            mPhone.setError("Invalid Phone number ");
            return;
        }

        mPassword.setError("");
        progressBar.setVisibility(View.VISIBLE);

        // register the user in firebase
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Person user = new Person(name, email, phone, userType);
                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .set(user)
                            .addOnSuccessListener(documentReference -> {
                                switch (userType) {
                                    case "Fest":
                                        //startActivity(new Intent(getApplicationContext(), SearchPlaceActivity.class));
                                        startActivity(new Intent(getApplicationContext(), MapAndPlacesActivity.class));
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
                                Log.i("n", "n");

                            });
                } else {
                    Toast.makeText(RegistrationActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    mPassword.setError("");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
