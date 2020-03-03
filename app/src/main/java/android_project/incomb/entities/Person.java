package android_project.incomb.entities;

public class Person{
    // attributes
    private String fullName;
    private String email;
    private String phoneNumber;
    private type typeUser;
    private enum type {Fest,Guest,Host};

    //constructor
    public Person(String fullName, String email, String phoneNumber, String type) {
        setFullName(fullName);
        setEmail(email); /// NEEN TO CHECK (Avraham)
        setPhoneNumber(phoneNumber);
        setTypeUser(type);
    }

    public Person() {
    }

    //Getters and Setters
    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}

    public type getTypeUser() { return typeUser; }

    public void setTypeUser(String typeUser) {this.typeUser = type.valueOf(typeUser); }
}
