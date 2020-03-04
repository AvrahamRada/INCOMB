package android_project.incomb.activites.Host;

import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;
import java.util.Map;

public interface  IRentActivity {
    void setFirstData(String capacity, String price, String place, String suitable, GeoPoint geoPoint);
    void setTwoData(Map hmap);
    void setCalendarData(Calendar calendarData);
    void setFourData(String namePlace);
    void submitData();
}
