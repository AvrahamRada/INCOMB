package android_project.incomb.activites.Fest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;
//import com.savvi.rangedatepicker.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android_project.incomb.R;

public class DateRangeActivity extends AppCompatActivity {
    //CalendarPickerView calender;
    private CalendarPickerView datePicker;
    private Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range);
        datePicker = findViewById(R.id.date);
        btnSelect = findViewById(R.id.btn_check);
        Slidr.attach(this);
        //Avraham - use this calendar
        initCalender();

        btnSelect.setOnClickListener(v -> {
            Toast.makeText(DateRangeActivity.this, "Days!!", Toast.LENGTH_SHORT).show();
            sendDateSelected();
        });
    }

    private void sendDateSelected() {
        //list of the selected dates
        List<Date> select = datePicker.getSelectedDates();
        Date begin = select.get(0); //first day from the list
        Date end = select.get(select.size() - 1); //last day from the list
    }

    private void initCalender() {
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);
    }
}
