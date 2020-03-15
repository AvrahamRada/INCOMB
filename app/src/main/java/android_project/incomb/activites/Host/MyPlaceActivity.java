package android_project.incomb.activites.Host;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
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
    public static final int PLACE_UPDATED_OK = 5;
    public static final int PLACE_UPLOADED_FAIL = 6;

    private Button button;
    private Place addNewPlace;
    private PlaceListFragment placeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        TextView listPlace = findViewById(R.id.manage_place);
        button = findViewById(R.id.logout_btn);

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
                }
                break;
            case EDIT_PLACE_REQUEST_CODE:
                if(resultCode == PLACE_UPDATED_OK){
                    String docId = data.getStringExtra("Doc id");
                    String placeString = data.getStringExtra("place");
                    addNewPlace = new Gson().fromJson(placeString, Place.class);
                    //update place in fire base
                    FirebaseFirestore.getInstance()
                            .collection("places")
                            .document(docId)
                            .update("amenities", addNewPlace.getAmenities(),
                                    "amountOfGuest", addNewPlace.getAmountOfGuest(),
                                    "availability", addNewPlace.getAvailability(),
                                    "rent", addNewPlace.getRent(),
                                    "YourNameForThePlace" ,addNewPlace.getYourNameForThePlace())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MyPlaceActivity.this, "Place Updated", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                if(resultCode == PLACE_UPLOADED_FAIL){
                    break;
                }
                break;
        }
    }
}
