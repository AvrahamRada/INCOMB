package android_project.incomb.activites.Host;

import java.util.Map;

public interface  IRentActivity {
    void setFirstData(String capacity, String price, String place, String suitable);
    void setTwoData(Map hmap);
    void setCalendarData();
    void setFourData();
    void submitData();
}
