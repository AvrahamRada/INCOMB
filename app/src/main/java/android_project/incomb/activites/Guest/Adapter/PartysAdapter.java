package android_project.incomb.activites.Guest.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import android_project.incomb.R;
import android_project.incomb.entities.Event;

public class PartysAdapter extends RecyclerView.Adapter<PartyViewHolder> {
    List<Event> eventsList = new ArrayList<>();

    public PartysAdapter() {
        refreshData();
    }

    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, null);
        //set click listeners and other magic
        return new PartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {
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

    public void refreshData(){
        List<Event> events = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("event")
                .whereEqualTo("idGuest", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.size()!=eventsList.size()){
                        eventsList.removeAll(eventsList);
                        eventsList.addAll(queryDocumentSnapshots.toObjects(Event.class));
                    }
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    //Toast.makeText(getContext(),"No Events",Toast.LENGTH_LONG);
                });
    }

}
