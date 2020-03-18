package android_project.incomb.entities;

public class Host {
    // attributes
    private Person host;
    private Place hostPlace;
    private String placeId;
    private boolean selected = false;

    //Constructor
    public Host() {

    }

    //Getters and Setters
    public Person getHost() {
        return host;
    }

    public void setHost(Person host) {
        this.host = host;
    }

    public Place getHostPlace() {
        return hostPlace;
    }

    public void setHostPlace(Place hostPlace) {
        this.hostPlace = hostPlace;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
