package android_project.incomb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class RentPlaceCalendar extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_place_calendar);
        button = findViewById(R.id.calendarButton);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = (CalendarPickerView) findViewById(R.id.select_dates);
        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);

        //list of the dates
        //datePicker.getSelectedDates();

//        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(Date date) {
//                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
//                Calendar calSelected = Calendar.getInstance();
//                calSelected.setTime(date);
//                String selectedDate = " " + calSelected.get(Calendar.DAY_OF_MONTH) + " " + (calSelected.get(Calendar.MONTH)+1) + " " + calSelected.get(Calendar.YEAR);
//                Toast.makeText(RentPlaceCalendar.this, selectedDate, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDateUnselected(Date date) {
//
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    public void nextStep() {
        Intent nextStep = new Intent(this, RentPlaceStepTwo.class);
        startActivity(nextStep);
        finish();
    }
}
