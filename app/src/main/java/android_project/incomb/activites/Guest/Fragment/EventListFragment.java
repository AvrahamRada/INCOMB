package android_project.incomb.activites.Guest.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Guest.Adapter.PartysAdapter;
import android_project.incomb.activites.Guest.Interface.IPartyActivty;
import android_project.incomb.activites.Host.Adapter.EventsAdapter;
import android_project.incomb.entities.Event;

public class EventListFragment extends Fragment {
    private final IPartyActivty activity;

    private Button btnSearch;
    private TextView listParty;
    private RecyclerView searchRecyclerViews;

    public EventListFragment(IPartyActivty iPartyActivty) { this.activity = iPartyActivty; }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        findViews(view);
        setViews();
        btnSearch.setOnClickListener(v -> activity.searchEvent());

        return view;
    }

    private void findViews(View view) {
        btnSearch = view.findViewById(R.id.search_btn);
        listParty = view.findViewById(R.id.party_text);
        searchRecyclerViews = view.findViewById(R.id.party_list);
    }

    private void setViews() {
        setSearchRecyclerView();
    }

    private void setSearchRecyclerView() {
        List<Event> events = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("events")
                .whereEqualTo("hostId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    events.addAll(queryDocumentSnapshots.toObjects(Event.class));
                    PartysAdapter adapter = new PartysAdapter(events);
                    searchRecyclerViews.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"No Events",Toast.LENGTH_LONG);
                });
    }
}
