package android_project.incomb.activites.Fest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import android_project.incomb.MainActivity;
import android_project.incomb.R;

public class FindPlaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout mLocation, mDateFrom, mDateTo;
    private String activiy;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);

       // String hostString = getIntent().getStringExtra("host");
       // Host host = /*new gson from json (hostString)*/


        mLocation = (TextInputLayout) findViewById(R.id.Location);
        mDateFrom = (TextInputLayout) findViewById(R.id.from_date);
        mDateTo = (TextInputLayout) findViewById(R.id.to_date);
        button = (Button) findViewById(R.id.finding_place);

        Spinner activitySpin = (Spinner) findViewById(R.id.spinner_activity);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(this, R.array.spinner_activity, android.R.layout.simple_spinner_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpin.setAdapter(adapterActivity);
        activitySpin.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    public void nextStep() {
        Intent nextStep = new Intent(this, MainActivity.class);
        startActivity(nextStep);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        activiy = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), activiy, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
