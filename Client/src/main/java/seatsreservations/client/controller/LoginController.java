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
import seatsreservations.domain.Spectator;
import seatsreservations.service.Service;
import seatsreservations.service.ServiceException;

import java.io.IOException;
import java.io.Serializable;
import java.io.SerializablePermission;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject implements Serializable {


    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox checkbox1;
    @FXML
    private Button loginButton;

    Service service;
    Manager manager;
    ManagerMainController managerMainController;
    UserMainController userMainController;

    Parent mainParent;
    Parent userMainParent;

    public LoginController() throws RemoteException {

    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setParent(Parent p1){
        mainParent=p1;
    }

    public void setManagerMainController(ManagerMainController mainController) {
        this.managerMainController = mainController;
    }
    public void setUserMainController(UserMainController mainController) {
        this.userMainController = mainController;
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

    public void loadUserMainStage() throws IOException {

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/UserMainPage.fxml"));
        AnchorPane layout = loader.load();
        newStage.setScene(new Scene(layout));
        newStage.show();

        UserMainController mainController = loader.getController();
    }

    @FXML
    public void Login(ActionEvent actionEvent){
        String username = txtUsername.getText();
        String password = txtPassword.getText();


        try{
            if(checkbox1.isSelected()){
                Manager manager = new Manager(username, password, "");
                FXMLLoader cloader = new FXMLLoader();
                cloader.setLocation(getClass().getResource("/views/ManagerMainPage.fxml"));
                Parent croot=cloader.load();

                ManagerMainController mainController =
                        cloader.<ManagerMainController>getController();
                mainController.setService(service, manager);

                this.setManagerMainController(mainController);
                this.setParent(croot);


                service.loginManager(manager, managerMainController);
                Stage stage = new Stage();
                stage.setTitle("Window for " + manager.getUsername());
                stage.setScene(new Scene(mainParent));

                managerMainController.setService(service, manager);
                stage.show();
            }
            else {
                Spectator spectator = new Spectator(username, password, "");
                FXMLLoader uloader = new FXMLLoader();
                uloader.setLocation(getClass().getResource("/views/UserMainPage.fxml"));
                Parent uroot=uloader.load();

                UserMainController userMainController =
                        uloader.<UserMainController>getController();
                userMainController.setService(service, spectator);

                this.setUserMainController(userMainController);
                this.setParent(uroot);


                service.loginSpectator(spectator, userMainController);
                Stage stage = new Stage();
                stage.setTitle("Window for " + spectator.getUsername());
                stage.setScene(new Scene(mainParent));

                userMainController.setService(service, spectator);
                stage.show();

            }
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        }   catch  (ServiceException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password" +e.toString());
            alert.showAndWait();
        }
    }
}
