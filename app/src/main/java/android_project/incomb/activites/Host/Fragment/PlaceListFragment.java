package android_project.incomb.activites.Host.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.Adapter.PlaceAdapter;
import android_project.incomb.entities.Place;

public class PlaceListFragment extends Fragment {
    private final IPlaceActivity activity;

    private RecyclerView placeRecyclerView;
    private ImageView addPlace;
    private PlaceAdapter placesAdapter;

    public PlaceListFragment(IPlaceActivity activity) { this.activity = activity; }

    private void findViews(View view) {
        TextView listPlace = (TextView) view.findViewById(R.id.list_place);
        placeRecyclerView = (RecyclerView) view.findViewById(R.id.place_recyclerview);
        addPlace = (ImageView) view.findViewById(R.id.add_place);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_place_list, container, false);
      findViews(view);
      setViews();
      addPlace.setOnClickListener(v -> {
          activity.addPlace();
      });
      return view;
    }

    private void setViews() {
        setPlacesRecyclerView();
    }

    private void setPlacesRecyclerView() {
        placesAdapter = new PlaceAdapter(this);
        placeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        placeRecyclerView.setAdapter(placesAdapter);

        FirebaseFirestore.getInstance()
                .collection("places")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    placesAdapter.addPlaces(queryDocumentSnapshots.getDocuments());
                });
    }

    public void addPlace(Place place) {
        placesAdapter.addPlace(place);
    }

    public void onPlaceClick(Place place) { activity.onPlaceClick(place);
    }
}
