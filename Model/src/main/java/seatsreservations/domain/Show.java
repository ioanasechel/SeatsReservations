package seatsreservations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Show implements Serializable {
    private int ID;
    private String title;
    private String date;
    private List<Seat> seats;

    public Show(int ID, String title, String date) {
        this.ID = ID;
        this.title = title;
        this.date = date;
        //this.seats = initializeSeats();
    }

    public Show() {

    }


    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    private List<Seat> initializeSeats(){
        List<Seat> seatList =new ArrayList<>();
        for(int i=1; i<=40; i++){
            if(i<=8){
                Seat s = new Seat(i, 1, 1, false);
                seatList.add(s);
            }
            if(i>=9 && i<=16){
                Seat s = new Seat(i, 2, 1, false);
                seatList.add(s);
            }
            if(i>=17 && i<=24){
                Seat s = new Seat(i, 3, 2, false);
                seatList.add(s);
            }
            if(i>=25 && i<=32){
                Seat s = new Seat(i, 4, 2, false);
                seatList.add(s);
            }
            if(i>=33){
                Seat s = new Seat(i, 5, 3, false);
                seatList.add(s);
            }
        }
        return seatList;
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
