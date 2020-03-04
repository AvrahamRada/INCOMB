package android_project.incomb.activites.Host.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

import android_project.incomb.R;
import android_project.incomb.activites.Host.IRentActivity;
import android_project.incomb.activites.Host.ImagesAdapter;

public class RentStepFourFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private final IRentActivity activity;
    private Button button;
    private TextInputLayout mPlaceName;
    private RecyclerView imagesRecyclerView;
    private ImagesAdapter imagesAdapter;
    private ImageView addImage;

    private TextInputLayout mName;

    private Uri mImageUri;

    public RentStepFourFragment(IRentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        button = (Button) view.findViewById(R.id.finishPlace);
        mPlaceName = (TextInputLayout) view.findViewById(R.id.name_location);
        mName = (TextInputLayout) view.findViewById(R.id.name_location);
        imagesRecyclerView = view.findViewById(R.id.images_recyclerview);
        addImage = view.findViewById(R.id.add_image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_step_four, container, false);
        findViews(view);
        setRecyclerView();
        setAddImage();
        button.setOnClickListener(v -> {
            activity.setFourData(mPlaceName.getEditText().getText().toString());
        });
        return view;
    }

    private void setAddImage() {
        addImage.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void setRecyclerView() {
        imagesAdapter = new ImagesAdapter();
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        imagesRecyclerView.setAdapter(imagesAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    if (data.getData() != null) {
                        imagesAdapter.addPhoto(data.getData());
                    } else if (data.getClipData() != null) {
                        imagesAdapter.addPhotos(data.getClipData());
                    }
                }
                break;
        }
    }
}
