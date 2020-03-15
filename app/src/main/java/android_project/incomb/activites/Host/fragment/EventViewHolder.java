package android_project.incomb.activites.Host.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android_project.incomb.R;


class EventViewHolder extends RecyclerView.ViewHolder {
    private TextView eventName;
    private TextView placeName;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        eventName = itemView.findViewById(R.id.event_name);
        placeName = itemView.findViewById(R.id.place_name);
    }

    public void setEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    public void setPlaceName(String name) {
        this.placeName.setText(name);
    }
}
