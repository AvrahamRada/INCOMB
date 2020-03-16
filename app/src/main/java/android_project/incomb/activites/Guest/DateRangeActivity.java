package android_project.incomb.activites.Guest;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.entities.Event;
import android_project.incomb.entities.Person;
import android_project.incomb.entities.Place;
import android_project.incomb.entities.ReservationsTimes;

public class DateRangeActivity extends AppCompatActivity {
    public static final int EVENT_SEARCH_OK = 3;

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
        setContentView(R.layout.activity_date_range_guest);
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
        setResult(EVENT_SEARCH_OK, intentCheck);
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

    public void test(){
        /* getting host information
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(placeCheck.getIdHost())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Person host = documentSnapshot.toObject(Person.class);
                    host.getEmail();
                    host.getFullName();
                    host.getPhoneNumber();
                });
         */
        /* getting events and filter them by activity and dates
        List<Event> events = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    events.addAll(queryDocumentSnapshots.toObjects(Event.class));
                    setList(events);
                });
         */
        }
    private void setList(List<Event> events) {
        /*
        List<Event> temp = new ArrayList<>();
        Place checkPlace;
        for (Event checkEvent: events) {
            Place checkPlace;
            FirebaseFirestore.getInstance()
                    .collection("places")
                    .document(checkEvent.getPlaceId())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        checkPlace = documentSnapshot.toObject(Place.class);
                    });
            ReservationsTimes check = checkPlace.getAvailability();
            if(!checkPlace.getTypeOfActivity().equals(typeActivity))
                temp.add(checkEvent);
            else if(begin.before(check.getStartEvent()) || end.after(check.getEndEvent()))
                temp.add(checkEvent);
        }
        events.removeAll(temp);
         */
    }
}

