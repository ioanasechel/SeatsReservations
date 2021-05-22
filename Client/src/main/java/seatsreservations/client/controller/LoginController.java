package seatsreservations.client.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seatsreservations.domain.Manager;
import seatsreservations.service.Service;
import seatsreservations.service.ServiceException;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject implements Serializable {


    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private CheckBox checkbox1;
    @FXML
    private Button loginButton;

    Service service;
    Manager manager;
    ManagerMainController managerMainController;

    Parent mainParent;

    public LoginController() throws RemoteException {

    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setParent(Parent p){
        mainParent=p;
    }

    public void setManagerMainController(ManagerMainController mainController) {
        this.managerMainController = mainController;
    }

    public void initializeUser() {
        this.manager = service.findOne(txtUsername.getText());
    }

    public void loadManagerMainStage() throws IOException {

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ManagerMainPage.fxml"));
        AnchorPane layout = loader.load();
        newStage.setScene(new Scene(layout));
        newStage.show();

        ManagerMainController mainController = loader.getController();
    }

    @FXML
    public void Login(ActionEvent actionEvent){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        Manager manager = new Manager(username, password, "");

        try{
            service.loginManager(manager, managerMainController);
            Stage stage=new Stage();
            stage.setTitle("Window for employee " +manager.getUsername());
            stage.setScene(new Scene(mainParent));

//            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent event) {
//                    try {
//                        managerMainController.logout();
//                    } catch (ServiceException e) {
//                        e.printStackTrace();
//                    }
//                    System.exit(0);
//                }
//            });

            managerMainController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }   catch  (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }
    }
}
