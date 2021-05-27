package seatsreservations.service;

import seatsreservations.domain.Manager;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.domain.Spectator;

public interface Service {

    Manager findOne(String username);

    Manager getManager(String username, String password);

    Iterable<Show> getAllShows();

    void loginManager(Manager manager, Observer client) throws ServiceException;

    void loginSpectator(Spectator spectator, Observer client) throws ServiceException;

    void logOut(Manager manager, Observer client) throws ServiceException;

    void addShow(Show show, Observer clientSearch);

    Iterable<Reservation> getAllReservations();

    void cancelReservation(Integer id, Observer client);

    void addReservation(Reservation reservation);

    Iterable<Reservation> getFilteredReservations(String username);
}
