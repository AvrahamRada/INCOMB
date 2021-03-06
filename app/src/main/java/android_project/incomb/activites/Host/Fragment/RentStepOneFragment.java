package android_project.incomb.activites.Host.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Host.Interface.IRentActivity;

public class RentStepOneFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private final IRentActivity activity;
    private EditText mCapacity, mPrice;
    private TextInputLayout mLocation;
    private Button button;
    private GeoPoint geoPoint;

    private String sPlace, sSuitable, sCapacity, sPrice;

    public RentStepOneFragment(IRentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        mCapacity = (EditText) view.findViewById(R.id.capacity);
        mLocation = (TextInputLayout) view.findViewById(R.id.Location);
        mPrice = (EditText) view.findViewById(R.id.price);
        button = (Button) view.findViewById(R.id.firstStep);

        //spinner-place
        Spinner placeSpin = (Spinner) view.findViewById(R.id.spinner_place);
        ArrayAdapter<CharSequence> adapterPlace = ArrayAdapter.createFromResource(getContext(), R.array.spinner_place, android.R.layout.simple_spinner_item);
        adapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpin.setAdapter(adapterPlace);
        placeSpin.setOnItemSelectedListener(this);

        //spinner-suitable
        Spinner suitableSpin = (Spinner) view.findViewById(R.id.spinner_suitable);
        ArrayAdapter<CharSequence> adapterSuitable = ArrayAdapter.createFromResource(getContext(), R.array.spinner_suitable, android.R.layout.simple_spinner_item);
        adapterSuitable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suitableSpin.setAdapter(adapterSuitable);
        suitableSpin.setOnItemSelectedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_step_one, container, false);
        findViews(view);
        button.setOnClickListener(v -> {
            getDatafromUser();
            activity.setFirstData(sCapacity, sPrice, sPlace, sSuitable, geoPoint);
        });
        return view;
    }

    private void getDatafromUser() {
        sCapacity = mCapacity.getText().toString();
        sPrice = mPrice.getText().toString();
        geoPoint = getLocationFromAddress(mLocation.getEditText().getText().toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinner_place:
                sPlace = parent.getItemAtPosition(position).toString().replaceAll(" ", "");
                break;
            case R.id.spinner_suitable:
                sSuitable = parent.getItemAtPosition(position).toString().replaceAll(" ", "");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public GeoPoint getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        GeoPoint p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null)
                return null;
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new GeoPoint((double) (location.getLatitude()), (double) (location.getLongitude()));
        } catch (IOException e) {
            Toast.makeText(getContext(),"Wrong Place",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return p1;
    }

}
