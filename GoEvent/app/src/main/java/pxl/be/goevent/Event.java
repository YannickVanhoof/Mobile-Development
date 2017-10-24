package pxl.be.goevent;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pxl.be.goevent.AppUser;

/**
 * Created by 11500046 on 24/10/2017.
 */

public class Event {
   private int id;
    private String name;
    private String Street;
    private int houseNumber;
    private String city;
    private int postalCode;
    private String Venue;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String description;
    private AppUser organisator;
    private List<AppUser> attendees;
    private byte[] coverPhoto;
    private double longitude;
    private double latitude;
    public Event(){

    }
    public Event(int id, String name, String street, int houseNumber, String city, int postalCode, String venue, Date date, Time startTime, Time endTime, String description, AppUser organisator, List<AppUser> attendees, byte[] coverPhoto, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        Street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
        Venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.organisator = organisator;
        this.attendees = attendees;
        this.coverPhoto = coverPhoto;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppUser getOrganisator() {
        return organisator;
    }

    public void setOrganisator(AppUser organisator) {
        this.organisator = organisator;
    }

    public List<AppUser> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<AppUser> attendees) {
        this.attendees = attendees;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Street='" + Street + '\'' +
                ", houseNumber=" + houseNumber +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                ", Venue='" + Venue + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", organisator=" + organisator +
                ", attendees=" + attendees +
                ", coverPhoto=" + Arrays.toString(coverPhoto) +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}