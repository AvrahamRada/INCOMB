package android_project.incomb.activites.Guest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.Adapter.FindPlaceAdapter;
import android_project.incomb.activites.Guest.Adapter.PartysAdapter;
import android_project.incomb.activites.Guest.Fragment.EventListFragment;
import android_project.incomb.activites.Guest.Interface.IPartyActivty;
import android_project.incomb.activites.Start.LoginActivity;

public class SearchEventActivity extends AppCompatActivity implements IPartyActivty {
    private static final int EVENT_REQUEST_CODE = 2;

    private Button button;
    private EventListFragment eventListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        button = findViewById(R.id.logout_btn);

        eventListFragment = new EventListFragment(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.event_list, eventListFragment).commit();

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    @Override
    public void searchEvent() {
        Intent intent = new Intent(SearchEventActivity.this, MapAndEventsActivity.class);
        startActivityForResult(intent, EVENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (EVENT_REQUEST_CODE){
            default:
                ((PartysAdapter) eventListFragment.getEventRecyclerView().getAdapter()).refreshData();
                //tell recyclerview adapter refresh data
        }
    }
}
