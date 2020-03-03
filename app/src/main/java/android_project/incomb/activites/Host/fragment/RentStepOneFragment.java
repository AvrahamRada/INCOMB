package android_project.incomb.activites.Host.fragment;

import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import android_project.incomb.R;
import android_project.incomb.activites.Host.IRentActivity;

public class RentStepOneFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private final IRentActivity activity;
    private EditText mCapacity, mPrice, mDisPrice, mDishour;
    private TextInputLayout mLocation;
    private String sPlace, sSuitable;
    private FirebaseAuth fAuth;
    private Button button;
    private Geocoder geoCoder;

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
        mDisPrice = (EditText) view.findViewById(R.id.discount_price);
        mDishour = (EditText) view.findViewById(R.id.discount_hour);
        button = (Button) view.findViewById(R.id.firstStep);

        //spinner-place
        Spinner placeSpin = (Spinner) view.findViewById(R.id.spinner_place);
        ArrayAdapter<CharSequence> adapterPlace = ArrayAdapter.createFromResource(getContext(), R.array.spinner_place, android.R.layout.simple_spinner_item);
        adapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpin.setAdapter(adapterPlace);
        placeSpin.setOnItemSelectedListener(this);

        //spinner-suitable 'need to check how to do a multiple spinner'
        //https://stackoverflow.com/questions/5015686/android-spinner-with-multiple-choice
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
            MyData data = fetchData();
            //activity.setFirstData(data);
        });
        return view;
    }

    private MyData fetchData(){
        MyData data = new MyData();
       // data.capacity = mCapacity.getText().toString();
        //data.
        return data;
    }

    public class MyData{
        //String
        //int
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinner_place:
                sPlace = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_suitable:
                sSuitable = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
