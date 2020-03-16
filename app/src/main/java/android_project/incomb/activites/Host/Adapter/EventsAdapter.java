package android_project.incomb.activites.Host.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.function.Consumer;

import android_project.incomb.R;
import android_project.incomb.entities.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {
    List<Event> eventsList;

    public EventsAdapter(List<Event> eventsList) {
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, null);
        //set click listeners and other magic
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventsList.get(position);
        holder.setEventName(event.getEventName());
        getPlaceName(event.getPlaceId(), name -> holder.setPlaceName(name));
    }

    private void getPlaceName(String placeId, Consumer<String> onSuccess) {
        FirebaseFirestore.getInstance()
                .collection("places")
                .document(placeId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    onSuccess.accept((String) documentSnapshot.get("yourNameForThePlace"));
                })
                .addOnFailureListener(e -> {
                    //handle failure
                });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
