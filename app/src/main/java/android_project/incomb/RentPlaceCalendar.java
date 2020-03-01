package android_project.incomb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RentPlaceCalendar extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_place_calendar);
        button = findViewById(R.id.calendarButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    public void nextStep() {
        Intent nextStep = new Intent(this, RentPlaceStepTwo.class);
        startActivity(nextStep);
        finish();
    }
}
