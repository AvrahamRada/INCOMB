package android_project.incomb.activites.Fest.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.function.Consumer;

import android_project.incomb.R;
import android_project.incomb.entities.Event;

public class FindPlaceAdapter extends RecyclerView.Adapter<FindPlaceViewHolder> {
    List<Event> eventsList;

    public FindPlaceAdapter(List<Event> eventsList) {
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public FindPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, null);
        //set click listeners and other magic
        return new FindPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindPlaceViewHolder holder, int position) {
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
