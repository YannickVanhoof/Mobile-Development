package pxl.be.goevent;

import java.util.List;

/**
 * Created by 11500046 on 24/10/2017.
 */

public class AppUser {
    private int id;
    private String userName;
    private String password;
    private String email;
    private String firstname;
    private String lastName;
    private String address;
    private String city;
    int PostalCode;
    private List<Event> OrganisedEvents;
    private List<Event> events;

    public AppUser() {
    }

    public AppUser(int id, String userName, String password, String email, String firstname, String lastName, String address, String city, int postalCode, List<Event> organisedEvents, List<Event> events) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        PostalCode = postalCode;
        OrganisedEvents = organisedEvents;
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(int postalCode) {
        PostalCode = postalCode;
    }

    public List<Event> getOrganisedEvents() {
        return OrganisedEvents;
    }

    public void setOrganisedEvents(List<Event> organisedEvents) {
        OrganisedEvents = organisedEvents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", PostalCode=" + PostalCode +
                ", OrganisedEvents=" + OrganisedEvents +
                ", events=" + events +
                '}';
    }
}
