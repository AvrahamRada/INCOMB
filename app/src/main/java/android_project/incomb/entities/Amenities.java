package android_project.incomb.entities;

public class Amenities {
    private boolean kitchen;
    private boolean wifi;
    private boolean yoga;
    private boolean toilet;
    private boolean table;
    private boolean sink;

    public Amenities() {
        this.kitchen = false;
        this.wifi = false;
        this.yoga = false;
        this.toilet = false;
        this.table = false;
        this.sink = false;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isYoga() {
        return yoga;
    }

    public boolean isToilet() {
        return toilet;
    }

    public boolean isTable() {
        return table;
    }

    public boolean isSink() {
        return sink;
    }

    //Methods
    public void updateAmenities(String string, boolean check){
        switch (string) {
            case "kitchen":
                this.kitchen = check;
                break;
            case "wifi":
                this.wifi = check;
                break;
            case "yoga":
                this.yoga = check;
                break;
            case "toilet":
                this.toilet = check;
                break;
            case "table":
                this.table = check;
                break;
            case "sink":
                this.sink = check;
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amenities)) return false;
        Amenities amenities = (Amenities) o;
        return kitchen == amenities.kitchen &&
                wifi == amenities.wifi &&
                yoga == amenities.yoga &&
                toilet == amenities.toilet &&
                table == amenities.table &&
                sink == amenities.sink;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
