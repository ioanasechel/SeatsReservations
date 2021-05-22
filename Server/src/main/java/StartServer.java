import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import seatsreservations.domain.Manager;
import seatsreservations.repository.ManagerRepository;
import seatsreservations.repository.jdbc.ManagerHBMRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartServer {

    public static void main(String[] args) {

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\ioana\\OneDrive\\Documents\\Facultate\\Semestrul 4\\Ingineria Sistemelor Soft\\SeatsReservations\\db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ManagerRepository managerRepository = new ManagerHBMRepository(properties);
        //Manager m = new Manager("a", "a", "a");
        //managerRepository.save(m);
        System.out.println(managerRepository.findOne("a"));
        System.out.println("Server started...");
    }

}
