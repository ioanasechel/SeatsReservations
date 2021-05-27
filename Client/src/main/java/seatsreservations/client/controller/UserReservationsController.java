package seatsreservations.client.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.domain.Spectator;
import seatsreservations.service.Observer;
import seatsreservations.service.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;

public class UserReservationsController extends UnicastRemoteObject implements Initializable, Serializable, Observer {
    Service service;

    Spectator spectator;

    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, String> showTitle;
    @FXML
    private TableColumn<Reservation, String> clientName;
    @FXML
    private TableColumn<Reservation, String> seats;

    ObservableList<Reservation> reservationsTableModel = FXCollections.observableArrayList();

    public UserReservationsController() throws RemoteException {
    }

    public void setService(Service service, Spectator spectator) {
        this.service = service;
        this.spectator = spectator;
    }

    public void initialize() {
        initReservationsTableModel();
        initializeReservationsTable();
    }
    private void initReservationsTableModel() {
        List<Reservation> reservations = (List<Reservation>) service.getFilteredReservations(spectator.getUsername());
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
    private void printReservation() throws IOException {
        Reservation reservation = reservationsTable.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            new Alert(Alert.AlertType.ERROR, "Error! You must select a flight!");

        } else {

            String dest = "C:/itextExamples/sample.pdf";
            PdfWriter writer = new PdfWriter(dest);

            PdfDocument pdfDoc = new PdfDocument(writer);

            pdfDoc.addNewPage();

            Document document = new Document(pdfDoc);

            Paragraph paragraph1 = new Paragraph(reservation.toString());

            document.add(paragraph1);

            document.close();
            System.out.println("PDF Created");
        }
    }

    @FXML
    private void cancelReservation() throws IOException {
        Reservation reservation = reservationsTable.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            new Alert(Alert.AlertType.ERROR, "Error! You must select a flight!");
        } else {
            System.out.println("CANCELLING " + reservation.toString());
            Integer id = reservation.getID();
            service.cancelReservation(id, this);
            initReservationsTableModel();
        }
    }
}
