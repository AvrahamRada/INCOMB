package android_project.incomb.activites.Fest.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.Adapter.FindPlaceAdapter;
import android_project.incomb.activites.Fest.Interface.IFestActivty;
import android_project.incomb.entities.Event;

public class PlaceListFestFragment extends Fragment {
    private final IFestActivty activity;

    private Button btnSearch;
    private TextView listPlace;
    private RecyclerView searchRecyclerViews;

    public PlaceListFestFragment(IFestActivty iFestActivty) { this.activity = iFestActivty; }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_list_fest, container, false);
        findViews(view);
        setViews();
        btnSearch.setOnClickListener(v -> activity.searchPlace());

        return view;
    }

    private void findViews(View view) {
        btnSearch = view.findViewById(R.id.search_btn);
        listPlace = view.findViewById(R.id.find_text);
        searchRecyclerViews = view.findViewById(R.id.find_place_list);
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
                    FindPlaceAdapter adapter = new FindPlaceAdapter(events);
                    searchRecyclerViews.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),"No Events",Toast.LENGTH_LONG);
                });
    }
}
