package android_project.incomb.activites.Fest.Adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.MapAndPlacesActivity;
import android_project.incomb.entities.Host;
import android_project.incomb.entities.Person;
import android_project.incomb.entities.Place;

public class PersonListAdapter extends ArrayAdapter<Host> {

    private Context mContext;
    private int mResource;

    public PersonListAdapter(Context context, int resource, ArrayList<Host> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the place and the host
        Place hostPlace = getItem(position).getHostPlace();
        Person host = getItem(position).getHost();
        //get the persons information
        String name = host.getFullName();
        String email = host.getEmail();
        String phone = host.getPhoneNumber();
        String address = getAddress(hostPlace.getLocation());

        //Create the person object with the information
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
        TextView tvEmail = (TextView)convertView.findViewById(R.id.textView2);
        TextView tvPhone = (TextView)convertView.findViewById(R.id.textView3);
        TextView tvAddress = (TextView)convertView.findViewById(R.id.textView4);

        tvName.setText(name);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvAddress.setText(address);

        return convertView;
    }

    private String getAddress(GeoPoint location) {
        String sLocation = new String();
        Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getCountryName());
            }
            sLocation = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sLocation;
    }
}
