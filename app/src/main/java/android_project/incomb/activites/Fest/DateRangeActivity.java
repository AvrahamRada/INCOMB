package android_project.incomb.activites.Fest;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.entities.ReservationsTimes;

public class DateRangeActivity extends AppCompatActivity {
    public static final int PLACE_SEARCH_OK = 3;

    private ReservationsTimes calendar;
    private String typeActivity;
    private Date today;
    private Calendar nextYear;
    private CalendarPickerView datePicker;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnSelect;
    private Date begin,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Slidr.attach(this);
        findView();
        initCalender();

        btnSelect.setOnClickListener(v -> {
            Toast.makeText(DateRangeActivity.this, "Check...", Toast.LENGTH_SHORT).show();
            sendCheckData();
        });
    }

    private void findView() {
        radioGroup = findViewById(R.id.radioGroup);
        datePicker = findViewById(R.id.date);
        btnSelect = findViewById(R.id.btn_check);
    }

    private void initCalender() {
        today = new Date();
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        datePicker.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);
    }

    private void sendCheckData() {
        isCheck();
        setDate(datePicker.getSelectedDates());
        Intent intentCheck = new Intent();
        intentCheck.putExtra("type Activity", typeActivity);
        intentCheck.putExtra("calendar", new Gson().toJson(calendar));
        setResult(PLACE_SEARCH_OK, intentCheck);
        finish();
    }

    private void isCheck() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(DateRangeActivity.this, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
        typeActivity = radioButton.getText().toString().replaceAll(" ", "");;
    }

    public void setDate(List <Date> dateSelect) {
        begin = dateSelect.get(0);
        end = dateSelect.get(dateSelect.size() - 1);
        calendar = new ReservationsTimes(begin,end);
    }
}
