package android_project.incomb.activites.Host.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IRentActivity;

public class RentStepCalendarFragment extends Fragment {
    private final IRentActivity activity;
    private Button button;
    private Date today;
    private Calendar nextYear;
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
        selectDate();
        button.setOnClickListener(v -> {
            if(checkPicker(datePicker.getSelectedDates()))
                activity.setCalendarData(datePicker.getSelectedDates());
            else
                Toast.makeText(getContext(),"Choose duration",Toast.LENGTH_LONG).show();
        });
        return view;
    }

    private boolean checkPicker(List<Date> selectedDates) {
        if(selectedDates.isEmpty())
            return false;
        else
            return true;
    }

    private void selectDate() {
        today = new Date();
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);
    }
}
