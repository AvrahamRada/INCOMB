package android_project.incomb.activites.Host;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.fragment.PlaceEventFragment;
import android_project.incomb.activites.Host.fragment.PlaceListFragment;
import android_project.incomb.activites.Start.LoginActivity;
import android_project.incomb.entities.Place;

public class MyPlaceActivity extends AppCompatActivity implements IPlaceActivity {
    private static final int RENT_PLACE_REQUEST_CODE = 2;
    public static final int PLACE_UPLOADED_OK = 3;
    private static final int EDIT_PLACE_REQUEST_CODE = 4;

    private Button button;
    private Place addNewPlace;
    private PlaceListFragment placeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        TextView listPlace = (TextView) findViewById(R.id.manage_place);
        button = (Button) findViewById(R.id.logout_btn);

        placeListFragment = new PlaceListFragment(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list, placeListFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_event, new PlaceEventFragment(this)).commit();

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    @Override
    public void addPlace() {
        Intent intent = new Intent(this, RentPlaceActivity.class);
        startActivityForResult(intent, RENT_PLACE_REQUEST_CODE);
    }

    @Override
    public void onPlaceClick(Place place) {
        Intent intent = new Intent(this, EditPlaceActivity.class);
        intent.putExtra("place", new Gson().toJson(place));
        startActivityForResult(intent, EDIT_PLACE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RENT_PLACE_REQUEST_CODE:
                if (resultCode == PLACE_UPLOADED_OK) {
                    String placeDocId = data.getStringExtra("place id"); //reference firebase
                    FirebaseFirestore.getInstance()
                            .collection("places")
                            .document(placeDocId)
                            .get()
                            .addOnSuccessListener(documentSnapshot -> {
                                addNewPlace = documentSnapshot.toObject(Place.class);
                                placeListFragment.addPlace(addNewPlace);
                            }).addOnFailureListener(e -> {
                        System.out.println("remove");
                        //handle failure here
                    });
                    //get data firebase
                    //set data in ui
                    //firebase instance "places"document(placeid)  get onsuccesslistener adapter.add place
                }
                break;
        }
    }
}
