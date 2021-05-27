package seatsreservations.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.service.Observer;
import seatsreservations.service.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerReservationController extends UnicastRemoteObject implements Initializable, Serializable, Observer {

    Service service;

    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, String> showTitle;
    @FXML
    private TableColumn<Reservation, String> clientName;
    @FXML
    private TableColumn<Reservation, String> seats;

    ObservableList<Reservation> reservationsTableModel = FXCollections.observableArrayList();

    public ManagerReservationController() throws RemoteException {
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void initialize(){
        initReservationsTableModel();
        initializeReservationsTable();
    }
    private void initReservationsTableModel() {
        List<Reservation> reservations = (List<Reservation>) service.getAllReservations();
        reservationsTableModel.setAll(reservations);
    }

    private void initializeReservationsTable() {
        showTitle.setCellValueFactory(new PropertyValueFactory<>("ShowID"));
        clientName.setCellValueFactory(new PropertyValueFactory<>("User"));
        seats.setCellValueFactory(new PropertyValueFactory<>("Seats"));
        reservationsTable.setItems(reservationsTableModel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void reservationUpdate(List<Reservation> list_reservations) throws RemoteException {

    }

    @Override
    public void showAdded(List<Show> shows) throws RemoteException {

    }

    @FXML
    private void cancelReservation() throws IOException {
        Reservation reservation = reservationsTable.getSelectionModel().getSelectedItem();
        System.out.println(reservation.toString());
        Integer id = reservation.getID();
        service.cancelReservation(id, this);
        initReservationsTableModel();
    }
}
