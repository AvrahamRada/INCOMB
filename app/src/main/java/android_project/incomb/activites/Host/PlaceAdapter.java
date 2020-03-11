package android_project.incomb.activites.Host;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.activites.Host.fragment.PlaceListFragment;
import android_project.incomb.entities.Place;

public class PlaceAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    //list of places
    List<Place> placesList = new ArrayList<>();
    private PlaceListFragment placeListFragment;

    public PlaceAdapter(PlaceListFragment placeListFragment) {
        this.placeListFragment = placeListFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (placesList.get(position).getImagesList().size() > 0) {
            Uri imageUri = Uri.parse(placesList.get(position).getImagesList().get(0));
            holder.setUi(imageUri);
        }
        else {
            holder.setUi(null);
        }
        holder.setRemove(v -> {
            deleteFromFirebase(placesList.get(position), position);
        });
        holder.setImageClickListener(v -> {
            placeListFragment.onPlaceClick(placesList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public void addPlace(Place place) {
        placesList.add(place);
        notifyItemChanged(placesList.size() - 1);
    }

    public void addPlaces(List<DocumentSnapshot> documents) {
        for(DocumentSnapshot doc : documents) {
            addPlace(doc.toObject(Place.class));
        }
    }

    private void deleteFromFirebase(Place place, int position) {
        FirebaseFirestore.getInstance()
                .collection("places")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments())
                    {
                        if(place.equals(doc.toObject(Place.class))) {
                            deletePlaceFromDB(doc);
                            deleteImagesFromDB(doc);
                            break;
                        }
                    }
                });
        placesList.remove(position);
        notifyDataSetChanged();
    }

    private void deletePlaceFromDB(DocumentSnapshot doc) {
        FirebaseFirestore.getInstance()
                .collection("places")
                .document(doc.getId())
                .delete();
    }

    private void deleteImagesFromDB(DocumentSnapshot doc) {
        for (int i = 0; i < doc.toObject(Place.class).getImagesList().size(); i++) {
            FirebaseStorage.getInstance().getReference()
                    .child("places")
                    .child(doc.getId())
                    .child(String.valueOf(i))
                    .delete();
        }
    }
}
