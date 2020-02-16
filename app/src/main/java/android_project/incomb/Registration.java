package android_project.incomb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static final String TAG = "TAG";
    TextInputLayout mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
//    FirebaseFirestore fStore;
    String userID;

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

        mFullName   = (TextInputLayout)findViewById(R.id.full_name);
        mEmail      = (TextInputLayout)findViewById(R.id.email);
        mPassword   = (TextInputLayout)findViewById(R.id.password);
        mPhone      = (TextInputLayout)findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registration);
        mLoginBtn   = findViewById(R.id.move_to_sign_in);

        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){ // if current user is already present we dont want to re-create
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString().trim();
                final String fullName = mFullName.getEditText().getText().toString();
                final String phone    = mPhone.getEditText().getText().toString();

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

                // register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // User Created Successfully
                            Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
//                            userID = fAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
//                            Map<String,Object> user = new HashMap<>();
//                            user.put("fName",fullName);
//                            user.put("email",email);
//                            user.put("phone",phone);
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: " + e.toString());
//                                }
//                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mPassword.setError("");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
