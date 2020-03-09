package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.PlaceAdapter;

public class PlaceListFragment extends Fragment {
    private static final int RENT_PLACE_REQUEST_CODE = 2;
    private final IPlaceActivity activity;

    private TextView listPlace;
    private RecyclerView placeRecyclerView;
    private ImageView addPlace;
    private PlaceAdapter placesAdapter;

    public PlaceListFragment(IPlaceActivity activity) { this.activity = activity; }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addPlace.setOnClickListener(v -> {
            uploadPlace();
        });
    }

    private void findViews(View view) {
        listPlace = (TextView) view.findViewById(R.id.list_place);
        placeRecyclerView = (RecyclerView) view.findViewById(R.id.place_recyclerview);
        addPlace = (ImageView) view.findViewById(R.id.add_place);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_place_list, container, false);
      findViews(view);
      setViews();
      return view;
    }

    private void uploadPlace() {
        activity.addPlace();
    }

    private void setViews() {
        setPlacesRecyclerView();
    }

    private void setPlacesRecyclerView() {
        placesAdapter = new PlaceAdapter();
        placeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        placeRecyclerView.setAdapter(placesAdapter);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case RENT_PLACE_REQUEST_CODE:
//                if(resultCode == PLACE_UPLOADED_OK){
//                    data.getStringExtra("place id"); //reference firebase
//                    FirebaseFirestore.getInstance().collection("places")
//                            .document(String.valueOf(data))
//                            .get().addOnSuccessListener(documentSnapshot -> {
//                        Place addPlace = documentSnapshot.toObject(Place.class);
//                        placesAdapter.addPlace(addPlace);
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            //handle failure here
//                        }
//                    });
//                    //get data firebase
//                    //set data in ui
//                    //firebase instance "places"document(placeid)  get onsuccesslistener adapter.add place
//                }
//                break;
//        }
//    }

}
