package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import seatsreservations.domain.Reservation;
import seatsreservations.repository.ReservationRepository;

import java.util.Properties;

public class ReservationHBMRepository implements ReservationRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ReservationHBMRepository(Properties props) {
        logger.info("Initializing ReservationHBMRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public Reservation findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation save(Reservation entity) {
        return null;
    }

    @Override
    public Reservation update(Reservation entity) {
        return null;
    }

    @Override
    public Reservation delete(Integer integer) {
        return null;
    }
}
