package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import android_project.incomb.R;
import android_project.incomb.activites.Host.IRentActivity;

public class RentStepTwoFragment extends Fragment {
    private final IRentActivity activity;
    private Button button;
    private CheckBox chKitchen, chWifi, chYoga, chToilet, chTable, chSink;

    public RentStepTwoFragment(IRentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        button = (Button) view.findViewById(R.id.secondStep);
        chKitchen = (CheckBox) view.findViewById(R.id.checkbox_kitchen);
        chWifi = (CheckBox) view.findViewById(R.id.checkbox_wifi);
        chYoga = (CheckBox) view.findViewById(R.id.checkbox_yoga);
        chToilet = (CheckBox) view.findViewById(R.id.checkbox_toilet);
        chTable = (CheckBox) view.findViewById(R.id.checkbox_table);
        chSink = (CheckBox) view.findViewById(R.id.checkbox_sink);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_step_two, container, false);
        findViews(view);
        button.setOnClickListener(v -> {
            activity.setTwoData();
        });
        return view;
    }

    private MyData fetchData(){
        MyData data = new MyData();
        //data.capacity = chKitchen.getText().toString();
        //data.
        return data;
    }

    public class MyData{
        //String
        //int
    }
}
