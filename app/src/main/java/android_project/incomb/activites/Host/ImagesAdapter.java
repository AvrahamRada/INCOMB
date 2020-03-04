package android_project.incomb.activites.Host;

import android.content.ClipData;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android_project.incomb.R;

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    //list of images
    List<Uri> imagesList = new ArrayList<>();

    public List<Uri> getImagesList() {
        return imagesList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        //logic here
        holder.setUi(imagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void addPhoto(Uri uri) {
        imagesList.add(uri);
        notifyItemChanged(imagesList.size()-1);
    }

    public void addPhotos(ClipData uris) {
        for(int i=0 ; i < uris.getItemCount() ; i++){
            addPhoto(uris.getItemAt(i).getUri());
        }
    }

    public void removePhoto(Uri uri) {
        imagesList.remove(uri);
        notifyItemChanged(imagesList.size()-1);
    }
}
