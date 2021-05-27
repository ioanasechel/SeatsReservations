package seatsreservations.service;

import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Observer extends Remote {
    void reservationUpdate(List<Reservation> list_reservations) throws RemoteException;

    void showAdded(List<Show> shows) throws RemoteException;
}