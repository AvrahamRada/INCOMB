package android_project.incomb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.registration_btn).setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:

                break;

            case R.id.registration_btn:
                registration();
                //onStop();

                break;
        }

    }

    private void registration() {
        Intent registrationActivityIntent = new Intent(this, Registration.class);
        startActivity(registrationActivityIntent);
    }
}
