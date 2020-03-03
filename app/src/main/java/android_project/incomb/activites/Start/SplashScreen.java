package android_project.incomb.activites.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android_project.incomb.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent t = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(t);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
