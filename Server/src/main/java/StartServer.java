import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import seatsreservations.domain.Manager;
import seatsreservations.domain.Seat;
import seatsreservations.domain.Spectator;
import seatsreservations.repository.ManagerRepository;
import seatsreservations.repository.SeatRepository;
import seatsreservations.repository.SpectatorRepository;
import seatsreservations.repository.jdbc.ManagerHBMRepository;
import seatsreservations.repository.jdbc.SeatHBMRepository;
import seatsreservations.repository.jdbc.SpectatorHBMRepository;

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
        SeatRepository seatRepository = new SeatHBMRepository(properties);
        SpectatorRepository spectatorRepository = new SpectatorHBMRepository(properties);
//        Spectator spectator = new Spectator("a", "a", "a");
//        spectatorRepository.save(spectator);
//        Seat seat = new Seat(1, 1, 1, 12.0, false);
//        seatRepository.save(seat);
        //Manager m = new Manager("a", "a", "a");
        //managerRepository.save(m);
        //System.out.println(managerRepository.findOne("a"));
        //System.out.println(seatRepository.findOne(1));
        System.out.println(spectatorRepository.findOne("a"));
        System.out.println("Server started...");
    }

}
