package android_project.incomb.entities;

public class Host {
    // attributes
    private Person host;
    private Place hostPlace;
    private String placeId;
    //
    private String name;
    private String address;
    private String phone;
    private boolean selected = false;

    //Constructor
    public Host() {

    }
    //
    public Host(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
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

    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
