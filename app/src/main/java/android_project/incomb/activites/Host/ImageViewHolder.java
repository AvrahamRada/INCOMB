package android_project.incomb.activites.Host;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
        if (uri == null) image.setImageResource(R.drawable.place_icon);
        else {
            Picasso.with(image.getContext())
                    .load(uri)
                    .into(image);
        }
    }

    public void setRemove(View.OnClickListener clickListener) {
        remove.setOnClickListener(clickListener);
    }

    public void setImageClickListener(View.OnClickListener clickListener) {
        image.setOnClickListener(clickListener);
    }
}
