package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import android_project.incomb.R;
import android_project.incomb.activites.Host.IRentActivity;

public class RentStepCalendarFragment extends Fragment {
    private final IRentActivity activity;
    private Button button;
    private Date today;
    private Calendar nextYear, calSelected;
    private CalendarPickerView datePicker;

    public RentStepCalendarFragment(IRentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        button = (Button) view.findViewById(R.id.calendarButton);
        datePicker = (CalendarPickerView) view.findViewById(R.id.select_dates);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_step_calendar, container, false);
        findViews(view);
        button.setOnClickListener(v -> {
            selectDate();
            activity.setCalendarData(calSelected);
        });
        return view;
    }

    //https://codinginflow.com/tutorials/android/timesquare-calendarpickerview
    private void selectDate() {
        today = new Date();
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                calSelected = Calendar.getInstance();
                calSelected.setTime(date);
            }
            @Override
            public void onDateUnselected(Date date) {
            }
        });
    }
}
