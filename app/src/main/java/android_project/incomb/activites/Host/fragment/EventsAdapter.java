package android_project.incomb.activites.Host.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import android_project.incomb.R;
import android_project.incomb.entities.Event;

class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {
    List<Event> eventsList = new ArrayList<>();

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
                    //todd handle failure
                });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
