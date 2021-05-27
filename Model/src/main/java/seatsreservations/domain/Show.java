package seatsreservations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Show implements Serializable {
    private int ID;
    private String title;
    private String date;

    public Show(int ID, String title, String date) {
        this.ID = ID;
        this.title = title;
        this.date = date;
    }

    public Show() {

    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
