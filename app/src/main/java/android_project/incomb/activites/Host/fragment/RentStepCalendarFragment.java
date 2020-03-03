package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android_project.incomb.R;
import android_project.incomb.activites.Host.IRentActivity;

public class RentStepCalendarFragment extends Fragment {
    private final IRentActivity activity;
    private Button button;

    public RentStepCalendarFragment(IRentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        button = (Button) view.findViewById(R.id.calendarButton);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_step_calendar, container, false);
        findViews(view);
        button.setOnClickListener(v -> {
            activity.setCalendarData();
        });
        return view;
    }
}
