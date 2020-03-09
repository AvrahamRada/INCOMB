package android_project.incomb.activites.Host;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android_project.incomb.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    public ImageView remove;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.image = itemView.findViewById(R.id.image);
        this.remove = itemView.findViewById(R.id.remove);
    }

    public void setUi(Uri uri) {
        image.setImageURI(uri);
    }

    public void setRemove(View.OnClickListener clickListener){
        remove.setOnClickListener(clickListener);
    }
}
