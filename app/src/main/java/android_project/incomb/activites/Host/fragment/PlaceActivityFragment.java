package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IPlaceActivity;
import android_project.incomb.activites.Host.MyPlaceActivity;

public class PlaceActivityFragment extends Fragment {
    private final IPlaceActivity activity;
    private TextView listActivity;
    private ListView activityList;

    public PlaceActivityFragment(MyPlaceActivity activity) { this.activity = activity; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        listActivity = (TextView) view.findViewById(R.id.activity_place);
        activityList = (ListView) view.findViewById(R.id.list);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_activity, container, false);
        findViews(view);
        return view;
    }
}
