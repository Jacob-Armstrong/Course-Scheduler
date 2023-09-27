package scheduler.classes;

public abstract class Person {

    //////////////////////////
    //     DATA MEMBERS     //
    //////////////////////////

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String streetAddress;
    private String city;
    private String state;
    private int zip;
    private IdGenerator id;
    private String idString;

    //////////////////////////
    //     CONSTRUCTORS     //
    //////////////////////////

    public Person() {
        firstName = "Not set.";
        middleName = "Not set.";
        lastName = "Not set.";
        email = "Not set.";
        phone = "Not set.";
        streetAddress = "Not set.";
        city = "Not set.";
        state = "Not set.";
        zip = 0;
        id = null;
    }

    public Person(String firstName, String middleName, String lastName, String email, String phone, String streetAddress, String city, String state, int zip) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        id = new IdGenerator();
        idString = id.generateID();
    }

    //////////////////////////
    //   GETTERS & SETTERS  //
    //////////////////////////

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getMiddleName() + " " + getLastName();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getId() {
        return idString;
    }

    public void setId(IdGenerator ID) {
        this.id = ID;
    }

}