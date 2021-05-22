package seatsreservations.domain;

import java.io.Serializable;
import java.util.List;

public class Reservation implements Serializable {
    private int ID;
    private int showID;
    private String seats;
    private String user; //reservation contains spectator's username

    public Reservation(int id, int showID, String seats, String user) {
        this.ID = id;
        this.showID = showID;
        this.seats = seats;
        this.user = user;
    }

    public Reservation() {

    }

    public int getID() {
        return ID;
    }

    public int getShowID() {
        return showID;
    }

    public String getSeats() {
        return seats;
    }

    public String getUser() {
        return user;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
