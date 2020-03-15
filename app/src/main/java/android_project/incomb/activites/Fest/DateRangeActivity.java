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
        btnSelect = findViewById(R.id.btn);
        Slidr.attach(this);
        //Avraham - use this calendar
        initCalender();

        btnSelect.setOnClickListener(v -> {
            Toast.makeText(DateRangeActivity.this, "Days!!", Toast.LENGTH_SHORT).show();
            sendDateSelected();
        });
    /*
        final Calendar nextYear = Calendar.getInstance();
        final Calendar lastYear = Calendar.getInstance();

        nextYear.add(Calendar.YEAR, 10);
        lastYear.add(Calendar.YEAR, -10);

        calender = findViewById(R.id.date);
        b = findViewById(R.id.btn);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        calender.deactivateDates(list);
        ArrayList<java.util.Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            String strDate1 = "22-2-2020";
            String strDate2 = "26-2-2020";

            java.util.Date newdate1 = dateFormat.parse(strDate1);
            java.util.Date newdate2 = dateFormat.parse(strDate2);
            arrayList.add(newdate1);
            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        calender.init(lastYear.getTime(), nextYear.getTime(),
                new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault()))
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date())
                .withDeactivateDates(list)
                .withHighlightedDates(arrayList);

        btnSelect.setOnClickListener(v -> Toast.makeText(DateRangeActivity.this, "dxfdz", Toast.LENGTH_SHORT).show());
    */

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
