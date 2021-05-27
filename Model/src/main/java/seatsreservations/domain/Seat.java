package seatsreservations.domain;

import java.io.Serializable;

public class Seat implements Serializable {
    private int row;
    private int lodge;
    private int number;
    private boolean reserved;
    private double price;

    public Seat(int number, int row, int lodge, double price, boolean reserved) {
        this.row = row;
        this.lodge = lodge;
        this.number = number;
        this.price = price;
        this.reserved = reserved;
    }

    public Seat() {

    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getLodge() {
        return lodge;
    }

    public void setLodge(int lodge) {
        this.lodge = lodge;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", lodge=" + lodge +
                ", number=" + number +
                ", reserved=" + reserved +
                ", price=" + price +
                '}';
    }
}
