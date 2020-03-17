package android_project.incomb.activites.Fest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.Adapter.FindPlaceAdapter;
import android_project.incomb.activites.Fest.Fragment.PlaceListFestFragment;
import android_project.incomb.activites.Fest.Interface.IFestActivty;
import android_project.incomb.activites.Start.LoginActivity;

public class SearchPlaceActivity extends AppCompatActivity implements IFestActivty {
    private static final int PLACE_REQUEST_CODE = 2;
    public static final int PLACE_ADD_OK = 3;

    private Button button;
    private PlaceListFestFragment placeListFestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        button = findViewById(R.id.logout_btn);

        placeListFestFragment = new PlaceListFestFragment(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.place_list, placeListFestFragment).commit();

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    @Override
    public void searchPlace() {
        Intent intent = new Intent(this, MapAndPlacesActivity.class);
        startActivityForResult(intent, PLACE_REQUEST_CODE);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (PLACE_REQUEST_CODE){
            default:
            System.out.println("remove");
                ((FindPlaceAdapter) placeListFestFragment.getEventRecyclerView().getAdapter()).refreshData();
                //tell recyclerview adapter refresh data
        }
    }
}
