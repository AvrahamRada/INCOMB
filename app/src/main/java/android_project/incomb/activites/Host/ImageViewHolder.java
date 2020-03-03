package android_project.incomb.activites.Host;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android_project.incomb.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.image = itemView.findViewById(R.id.image);
    }

    public void setUi(Uri uri) {
        image.setImageURI(uri);
    }
}
