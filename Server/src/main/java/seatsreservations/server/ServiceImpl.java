package seatsreservations.server;

import seatsreservations.domain.Manager;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.domain.Spectator;
import seatsreservations.repository.*;
import seatsreservations.service.Observer;
import seatsreservations.service.Service;
import seatsreservations.service.ServiceException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImpl implements Service {

    private ManagerRepository managerRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private ReservationRepository reservationRepository;
    private SpectatorRepository spectatorRepository;
    private Map<String, Observer> loggedin;

    public ServiceImpl(ManagerRepository managerRepository, ShowRepository showRepository,
                       SeatRepository seatRepository, ReservationRepository reservationRepository, SpectatorRepository spectatorRepository) {
        this.managerRepository = managerRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.spectatorRepository = spectatorRepository;
        loggedin =  new ConcurrentHashMap<>();
    }

    @Override
    public Manager findOne(String username) {
        return managerRepository.findOne(username);
    }

    public Iterable<Manager> findAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getManager(String username, String password) {
        for(Manager m : findAllManagers())
            if (m.getUsername().equals(username) && m.getPassword().equals(password))
                return m;
        return null;
    }

    @Override
    public Iterable<Show> getAllShows() {
        return showRepository.findAll();
    }

    @Override
    public void loginManager(Manager manager, Observer client) throws ServiceException {
        Manager managerR = getManager(manager.getUsername(), manager.getPassword());
        if(managerR!=null){
            if(loggedin.size()!=0 && loggedin.get(managerR.getUsername())!=null)
                throw new ServiceException("Already logged in!");
            loggedin.put(managerR.getUsername(), client);
        }else
            throw new ServiceException("Authentication failed");
    }

    @Override
    public void loginSpectator(Spectator spectator, Observer client) throws ServiceException {
        Spectator spectator1 = getSpectator(spectator.getUsername(), spectator.getPassword());
        if(spectator1!=null){
            if(loggedin.size()!=0 && loggedin.get(spectator1.getUsername())!=null)
                throw new ServiceException("Already logged in!");
            System.out.println("!!!" + spectator1.toString());
            loggedin.put(spectator1.getUsername(), client);
        }else
            throw new ServiceException("Authentication failed");

    }

    private Spectator getSpectator(String username, String password) {
        for(Spectator s : findAllSpectators())
            if (s.getUsername().equals(username) && s.getPassword().equals(password))
                return s;
        return null;
    }

    private Iterable<Spectator> findAllSpectators() {
        return spectatorRepository.findAll();
    }

    @Override
    public void logOut(Manager manager, Observer client) throws ServiceException {
        Observer localClient = loggedin.remove(manager.getUsername());
        if(localClient == null)
            throw new ServiceException("User " + manager.getUsername() + " is not logged in");
    }

    @Override
    public void addShow(Show show, Observer clientSearch) {
        showRepository.save(show);
        notifyShowAdded();
    }

    @Override
    public Iterable<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void cancelReservation(Integer id, Observer client) {
        reservationRepository.delete(id);
    }

    @Override
    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public Iterable<Reservation> getFilteredReservations(String username) {
        List<Reservation> filtered = new ArrayList<>();
        for ( Reservation r : reservationRepository.findAll()){
            if(r.getUser().equals(username))
                filtered.add(r);
        }
        return filtered;
    }

    public List<Manager> getLoggedManagers() {
        List<Manager> managers=new ArrayList<>();

        for (Manager manager : managerRepository.findAll()){
            if (loggedin.containsKey(manager.getUsername())){//the employee is logged in
                Manager manager1=new Manager(manager.getUsername(),manager.getPassword(), "");
                managers.add(manager1);
            }
        }
        System.out.println("Size "+managers.size());
        return managers;
    }

    private final int defaultThreadsNo=5;

    private void notifyShowAdded() {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        for (Map.Entry<String, Observer> entry : loggedin.entrySet()){
            executor.execute(() -> {
                try {
                    //System.out.println("Notifying [" + ma.getId() + "]");
                    entry.getValue().showAdded((List<Show>) getAllShows());
                } catch (RemoteException e) {
                    System.err.println("Error notifying organizers");
                }
            });
        }

        executor.shutdown();
    }
}
