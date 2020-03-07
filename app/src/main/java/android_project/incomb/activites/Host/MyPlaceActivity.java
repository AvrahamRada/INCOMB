package android_project.incomb.activites.Host;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.fragment.PlaceActivityFragment;
import android_project.incomb.activites.Host.fragment.PlaceListFragment;
import android_project.incomb.activites.Start.LoginActivity;

public class MyPlaceActivity extends AppCompatActivity implements IPlaceActivity {
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
        startActivity(intent);
        finish();
    }
}
