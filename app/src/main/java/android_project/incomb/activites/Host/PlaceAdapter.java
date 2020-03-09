package android_project.incomb.activites.Host;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;
import android_project.incomb.entities.Place;

public class PlaceAdapter extends RecyclerView.Adapter<ImageViewHolder>  {
   //list of places
    List<Place> placesList = new ArrayList<>();

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.setUi( Uri.parse(placesList.get(position).getImagesList().get(0)));
        holder.setRemove(v -> {
            placesList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() { return placesList.size(); }

    public void addPlace(Place place){
        placesList.add(place);
        notifyItemChanged(placesList.size() - 1);
    }
}
