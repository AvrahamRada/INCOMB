package android_project.incomb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class RentPlaceStepTwo extends AppCompatActivity {

    private CheckBox chKitchen, chWifi, chYoga, chToilet, chTable, chSink;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_place_step_two);
        button = (Button) findViewById(R.id.secondStep);
        chKitchen = (CheckBox) findViewById(R.id.checkbox_kitchen);
        chWifi = (CheckBox) findViewById(R.id.checkbox_wifi);
        chYoga = (CheckBox) findViewById(R.id.checkbox_yoga);
        chToilet = (CheckBox) findViewById(R.id.checkbox_toilet);
        chTable = (CheckBox) findViewById(R.id.checkbox_table);
        chSink = (CheckBox) findViewById(R.id.checkbox_sink);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    public void nextStep() {
        Intent nextStep = new Intent(this, RentPlaceStepThree.class);
        startActivity(nextStep);
        finish();
    }
}
