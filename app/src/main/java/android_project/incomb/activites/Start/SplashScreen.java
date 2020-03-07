package android_project.incomb.activites.Start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import android_project.incomb.MainActivity;
import android_project.incomb.R;
import android_project.incomb.activites.Fest.FindPlaceActivity;
import android_project.incomb.activites.Host.MyPlaceActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fAuth = FirebaseAuth.getInstance();

        //Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fAuth.getCurrentUser() != null) {
                    Login(fAuth.getCurrentUser());
                }
                else {
                    Intent t = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(t);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }

    private void Login(FirebaseUser user) {
        if (user != null) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        String userType = (String) documentSnapshot.get("typeUser");
                        switch (userType) {
                            case "Fest":
                                startActivity(new Intent(getApplicationContext(), FindPlaceActivity.class));
                                finish();
                                break;
                            case "Guest":
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
}
