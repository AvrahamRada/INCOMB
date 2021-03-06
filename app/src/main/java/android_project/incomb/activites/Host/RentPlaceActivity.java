package android_project.incomb.activites.Host;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IRentActivity;
import android_project.incomb.activites.Host.Fragment.RentStepCalendarFragment;
import android_project.incomb.activites.Host.Fragment.RentStepFourFragment;
import android_project.incomb.activites.Host.Fragment.RentStepOneFragment;
import android_project.incomb.activites.Host.Fragment.RentStepTwoFragment;
import android_project.incomb.entities.Place;

public class RentPlaceActivity extends AppCompatActivity implements IRentActivity {

    public static final int PLACE_UPLOADED_OK = 3;
    Place newPlace;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPlace = new Place();
        setContentView(R.layout.activity_rent_place);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RentStepOneFragment(this))
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public void setFirstData(String capacity, String price, String place, String suitable, GeoPoint geoPoint) {
        try {
            newPlace.setAmountOfGuest(Integer.parseInt(capacity));
            newPlace.setRent(Double.parseDouble(price));
            newPlace.setTypeOfSpaces(place);
            newPlace.setTypeOfActivity(suitable);
            newPlace.setLocation(geoPoint);
            openSecondFragment();
        } catch (Exception ex) {
            Toast.makeText(this, "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTwoData(Map hmap) {
        Iterator it = hmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            newPlace.updateCheck((String) pair.getKey(), (boolean) pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        openCalendarFragment();
    }

    @Override
    public void setCalendarData(List<Date> dateSelect) {
        Date begin = dateSelect.get(0);
        Date end = dateSelect.get(dateSelect.size() - 1);
        newPlace.setAvailability(begin, end);
        openFourFragment();
    }

    @Override
    public void setFourData(String namePlace, List<Uri> uriList) {
        try {
            List<String> imageListString = new ArrayList<>();
            for (Uri uri : uriList) {
                imageListString.add(uri.toString());
            }
            newPlace.setImagesList(imageListString);
            newPlace.setYourNameForThePlace(namePlace);
            submitData();
        }catch (Exception ex) {
            Toast.makeText(this, "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
        }
    }

    private void openSecondFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, /*new fragment*/new RentStepTwoFragment(this))
                .addToBackStack("stack")
                .commit();
    }

    private void openCalendarFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, /*new fragment*/new RentStepCalendarFragment(this))
                .addToBackStack("stack")
                .commit();
    }

    private void openFourFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, /*new fragment*/new RentStepFourFragment(this))
                .addToBackStack("stack")
                .commit();
    }

    public void uploadPlaceToDB(Consumer<String> onSuccess) {
        FirebaseFirestore.getInstance()
                .collection("places")
                .add(newPlace)
                .addOnSuccessListener(documentReference -> {
                    docId = documentReference.getId();
                    onSuccess.accept(documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
                });
    }

    public void uploadImagesToStorage(String path, AtomicInteger imagesUploadedCounter, Consumer<String> onSuccess) {
        if (newPlace.getImagesList().size() == 0) {
            onSuccess.accept(null);
            return;
        }
        for (int i = 0; i < newPlace.getImagesList().size(); i++) {
            String image = newPlace.getImagesList().get(i);
            int finalI = i;
            FirebaseStorage.getInstance().getReference()
                    .child("places")
                    .child(path)
                    .child(String.valueOf(i))
                    .putFile(Uri.parse(image))
                    .addOnSuccessListener(taskSnapshot -> {
                        imagesUploadedCounter.getAndIncrement();
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    newPlace.getImagesList().set(finalI, uri.toString());
                                    if (imagesUploadedCounter.get() == newPlace.getImagesList().size())
                                        onSuccess.accept(uri.toString());
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getFragments().size() > 1){
            getSupportFragmentManager().popBackStack();
        }
        else finish();
    }

    private void updateImagesInDB(String docId) {
        FirebaseFirestore.getInstance()
                .collection("places")
                .document(docId)
                .update("imagesList", newPlace.getImagesList())
                .addOnSuccessListener(documentReference1 -> {
                    Toast.makeText(this, "Photos Uploaded", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error! Wrong input, please check", Toast.LENGTH_SHORT).show();
                });
    }

    public void submitData() {
        AtomicInteger imagesUploadedCounter = new AtomicInteger();
        uploadPlaceToDB(placeId ->
                uploadImagesToStorage(placeId, imagesUploadedCounter, urlString -> {
                    updateImagesInDB(placeId);
                    Intent intentMyPlace = new Intent();
                    intentMyPlace.putExtra("place id", docId);
                    setResult(PLACE_UPLOADED_OK, intentMyPlace);
                    finish();
                }));
    }
}
