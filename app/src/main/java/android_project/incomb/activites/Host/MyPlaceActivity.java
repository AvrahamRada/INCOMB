package android_project.incomb.activites.Host;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.fragment.PlaceActivityFragment;
import android_project.incomb.activites.Host.fragment.PlaceListFragment;
import android_project.incomb.activites.Start.LoginActivity;
import android_project.incomb.entities.Place;

public class MyPlaceActivity extends AppCompatActivity implements IPlaceActivity {
    private static final int RENT_PLACE_REQUEST_CODE = 2;
    public static final int PLACE_UPLOADED_OK = 3;
    private TextView listPlace;
    private RecyclerView placeRecyclerView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        listPlace = (TextView) findViewById(R.id.manage_place);
        button = (Button) findViewById(R.id.logout_btn);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list, new PlaceListFragment(this)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_activity, new PlaceActivityFragment(this)).commit();

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    public void addPlace(){
        Intent intent = new Intent(this, RentPlaceActivity.class);
        startActivityForResult(intent, RENT_PLACE_REQUEST_CODE);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RENT_PLACE_REQUEST_CODE:
                    if(resultCode == PLACE_UPLOADED_OK){
                        data.getStringExtra("place id"); //reference firebase
                        FirebaseFirestore.getInstance().collection("places")
                                .document(String.valueOf(data))
                                .get().addOnSuccessListener(documentSnapshot -> {
                                    Place addPlace = documentSnapshot.toObject(Place.class);
                                }).addOnFailureListener(e -> {
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
