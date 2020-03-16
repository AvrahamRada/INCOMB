package android_project.incomb.activites.Host.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Adapter.EventsAdapter;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.MyPlaceActivity;
import android_project.incomb.entities.Event;

public class PlaceEventFragment extends Fragment {
    private final IPlaceActivity activity;
    private TextView listActivity;
    private RecyclerView eventRecyclerViews;

    public PlaceEventFragment(MyPlaceActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        listActivity = view.findViewById(R.id.activity_place);
        eventRecyclerViews = view.findViewById(R.id.events_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_event, container, false);
        findViews(view);
        setViews();
        return view;
    }

    private void setViews() {
        setEventsRecyclerView();
    }

    private void setEventsRecyclerView() {
        List<Event> events = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("events")
                .whereEqualTo("hostId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    events.addAll(queryDocumentSnapshots.toObjects(Event.class));
                    EventsAdapter adapter = new EventsAdapter(events);
                    eventRecyclerViews.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"No Events",Toast.LENGTH_LONG);
        });
    }
}
