package seatsreservations.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seatsreservations.domain.Manager;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.service.Observer;
import seatsreservations.service.Service;
import seatsreservations.service.ServiceException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerMainController extends UnicastRemoteObject implements Initializable, Serializable, Observer {

    Manager manager;
    Service service;
    Stage stage;
    Stage previousStage;

    @FXML
    private TextField managerLogged;

    public ManagerMainController() throws RemoteException {

    }

    public void setService(Service service) {
        this.service = service;
        managerLogged.setDisable(true);
        //managerLogged.setText(manager.getUsername());
    }

    public void initialize(){
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void reservationMade(List<Reservation> list_reservations) throws RemoteException {

    }

    @Override
    public void showAdded(List<Show> shows) throws RemoteException {

    }


    @FXML
    private void loadShowsStage() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ManagerShowsPage.fxml"));
        AnchorPane layout = loader.load();
        newStage.setScene(new Scene(layout));

        ManagerShowController managerShowController = loader.getController();
        managerShowController.setService(service);
        managerShowController.initialize();

        newStage.show();
    }
}
