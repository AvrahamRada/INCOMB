package android_project.incomb;

abstract class Person {
    // attributes
    private String firstName;
    private String lastName;
    private String email;

    //constructor
    Person(String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email); /// NEEN TO CHECK (Avraham)
    }

    //Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}
