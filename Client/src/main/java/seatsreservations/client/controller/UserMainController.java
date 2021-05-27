package seatsreservations.client.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.domain.Spectator;
import seatsreservations.service.Observer;
import seatsreservations.service.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserMainController extends UnicastRemoteObject implements Initializable, Serializable, Observer {

    Service service;

    Spectator spectator;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Label label10;
    @FXML
    private Label label11;
    @FXML
    private Label label12;
    @FXML
    private Label label13;
    @FXML
    private Label label14;
    @FXML
    private Label label15;
    @FXML
    private Label label16;
    @FXML
    private Label label17;
    @FXML
    private Label label18;
    @FXML
    private Label label19;
    @FXML
    private Label label20;
    @FXML
    private Label label21;
    @FXML
    private Label label22;
    @FXML
    private Label label23;
    @FXML
    private Label label24;
    @FXML
    private Label label25;
    @FXML
    private Label label26;
    @FXML
    private Label label27;
    @FXML
    private Label label28;
    @FXML
    private Label label29;
    @FXML
    private Label label30;
    @FXML
    private Label label31;
    @FXML
    private Label label32;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSeats;
    @FXML
    private ChoiceBox<String> timePicker;

    private List<Label> labels;

    public UserMainController() throws RemoteException {

    }

    public void initializeSeats(){
        List<Reservation> reservations = (List<Reservation>) service.getAllReservations();
            for (Reservation r : reservations) {
                String[] seatsnr = r.getSeats().split(",");
                for (String number : seatsnr){
                    labels.get(Integer.parseInt(number)-1).setBackground(new Background(new BackgroundFill(Color.rgb(255,0,0, 0.7), null, null)));
                }
            }
    }

    public void setService(Service service, Spectator spectator) {
        this.service = service;
        labels = new ArrayList<>(){{
            add(label1); add(label2); add(label3); add(label4); add(label5);
            add(label6); add(label7); add(label8); add(label9); add(label10);
            add(label11); add(label12); add(label13); add(label14); add(label15);
            add(label16); add(label17); add(label18); add(label19); add(label20);
            add(label21); add(label22); add(label23); add(label24); add(label25);
            add(label26); add(label27); add(label28); add(label29); add(label30);
            add(label31); add(label32);

        }};
        String[] times = { "8:00", "12:00", "16:00", "20:00" };
        timePicker.setItems(FXCollections.observableArrayList(times));
        timePicker.setValue("8:00");
        this.spectator = spectator;
        initializeSeats();
    }

    public void initialize(){
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
    private void createReservation() throws IOException {
        String name = txtName.getText();
        String seats = txtSeats.getText();
        int i=0;
        for (Reservation r:service.getAllReservations())
            i++;
        Reservation reservation = new Reservation(i+1, 1, seats, name);
        service.addReservation(reservation);
        String[] seatsnr = seats.split(",");
        for (String number : seatsnr){
            labels.get(Integer.parseInt(number)-1).setBackground(new Background(new BackgroundFill(Color.rgb(255,0,0, 0.7), null, null)));
        }
        //labels.get(0).setBackground(new Background(new BackgroundFill(Color.rgb(255,0,0, 0.7), null, null)));
    }

    @FXML
    private void loadReservationsStage() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/UserReservationsPage.fxml"));
        AnchorPane layout = loader.load();
        newStage.setScene(new Scene(layout));

        UserReservationsController userReservationsController = loader.getController();
        userReservationsController.setService(service, spectator);
        userReservationsController.initialize();

        newStage.show();
    }
}
