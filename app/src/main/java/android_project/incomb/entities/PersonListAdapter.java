package android_project.incomb.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Fest.MapAndPlacesActivity;

public class PersonListAdapter extends ArrayAdapter<Host> {

    private Context mContext;
    private int mResource;

    public PersonListAdapter(Context context, int resource, ArrayList<Host> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String address = getItem(position).getAddress();
        String phone = getItem(position).getPhone();

        //Create the person object with the information
        Host host = new Host(name,address,phone);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
        TextView tvAddress = (TextView)convertView.findViewById(R.id.textView2);
        TextView tvPhone = (TextView)convertView.findViewById(R.id.textView3);


        tvName.setText(name);
        tvAddress.setText(address);
        tvPhone.setText(phone);

        return convertView;
    }
}
