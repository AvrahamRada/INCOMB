package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;

public class PlaceListFragment extends Fragment {
    private final IPlaceActivity activity;
    private TextView listPlace;
    private RecyclerView placeRecyclerView;
    private ImageView addPlace;

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
      return view;
    }

    private void uploadPlace() {
        //setRecyclerView();
        addNewPlace();
    }

//    private void setRecyclerView() {
//
//    }

    private void addNewPlace() {
        activity.addPlace();
    }

}
