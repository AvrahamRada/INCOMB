package android_project.incomb.activites.Host.Interface;

import android.net.Uri;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface  IRentActivity {
    void setFirstData(String capacity, String price, String place, String suitable, GeoPoint geoPoint);
    void setTwoData(Map hmap);
    void setCalendarData(List <Date> dateSelect);
    void setFourData(String namePlace, List<Uri> uriImageList);
    void submitData();
}
