package android_project.incomb.activites.Host;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import android_project.incomb.R;
import android_project.incomb.entities.Place;

public class EditPlaceActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);

        String placeString = getIntent().getStringExtra("place");
        mPlace = new Gson().fromJson(placeString, Place.class);
        findViews();
        setViews();
    }

    private void setViews() {
        nameEditText.setText(mPlace.getYourNameForThePlace());
    }

    private void findViews() {
        this.nameEditText = findViewById(R.id.name);
    }
}
