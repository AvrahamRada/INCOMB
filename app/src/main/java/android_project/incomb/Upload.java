package android_project.incomb;

public class Upload {
    // attributes
    private String mImageUrl;

    //constructor
    public Upload() {
    }

    public Upload(String imageUrl) {
        mImageUrl = imageUrl;
    }

    //Getters and Setters
    public String getImageUrl() { return mImageUrl; }

    public void setImageUrl(String imageUrl) { mImageUrl = imageUrl; }
}
