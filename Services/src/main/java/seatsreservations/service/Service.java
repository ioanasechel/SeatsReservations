package seatsreservations.service;

import seatsreservations.domain.Manager;
import seatsreservations.domain.Show;

public interface Service {

    Manager findOne(String username);

    Manager getManager(String username, String password);

    Iterable<Show> getAllShows();

    void loginManager(Manager manager, Observer client) throws ServiceException;

    void logOut(Manager manager, Observer client) throws ServiceException;

    void addShow(Show show, Observer clientSearch);

}
