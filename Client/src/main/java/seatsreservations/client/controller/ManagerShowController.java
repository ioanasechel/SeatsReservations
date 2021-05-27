package seatsreservations.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seatsreservations.domain.Manager;
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

public class ManagerShowController extends UnicastRemoteObject implements Initializable, Serializable, Observer {

    Manager manager;
    Service service;
    Stage stage;
    Stage previousStage;

    @FXML
    private TableView<Show> showsTable;
    @FXML
    private TableColumn<Show, String> title;
    @FXML
    private TableColumn<Show, String> date;
    @FXML
    private TextField txtTitle;
    @FXML
    private DatePicker txtDate;

    ObservableList<Show> showsTableModel = FXCollections.observableArrayList();

    public ManagerShowController() throws RemoteException {
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void initialize(){
        initShowsTableModel();
        initializeShowsTable();
    }
    private void initShowsTableModel() {
        List<Show> shows = (List<Show>) service.getAllShows();
        showsTableModel.setAll(shows);
    }

    private void initializeShowsTable() {
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        showsTable.setItems(showsTableModel);
    }

    @FXML
    private void addShow() throws IOException {
        String title = txtTitle.getText();
        String date = txtDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int i=1;
        for (Show t:service.getAllShows())
            i++;
        Show show = new Show(i, title, date);

        service.addShow(show, this);
        initShowsTableModel();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void reservationUpdate(List<Reservation> list_reservations) throws RemoteException {

    }

    @Override
    public void showAdded(List<Show> shows) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //showsTableModel.add(show);
                showsTableModel.clear();
                shows.addAll(shows);
            }
        });
    }

}
