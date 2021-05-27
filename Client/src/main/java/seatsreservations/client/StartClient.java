package seatsreservations.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import seatsreservations.client.controller.LoginController;
import seatsreservations.client.controller.ManagerMainController;
import seatsreservations.client.controller.UserMainController;
import seatsreservations.service.Service;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class StartClient extends Application {

    private Stage primaryStage;
    private static int defaultChatPort = 55557;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        //Service server = new ServicesRpcProxy(serverIP, serverPort);

        Registry registry = LocateRegistry.getRegistry("localhost");
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        Service server=(Service)factory.getBean("service");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/LogInPage.fxml"));
        Parent root = loader.load();


        LoginController loginController =
                loader.<LoginController>getController();
        loginController.setService(server);

        primaryStage.setTitle("Seats Reservations");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
