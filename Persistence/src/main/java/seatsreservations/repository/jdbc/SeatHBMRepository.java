package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seatsreservations.domain.Seat;
import seatsreservations.repository.SeatRepository;

import java.util.Properties;

public class SeatHBMRepository implements SeatRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public SeatHBMRepository(Properties props) {
        logger.info("Initializing SeatHBMRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public Seat findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Seat> findAll() {
        return null;
    }

    @Override
    public Seat save(Seat entity) {
        return null;
    }

    @Override
    public Seat update(Seat entity) {
        return null;
    }

    @Override
    public Seat delete(Integer integer) {
        return null;
    }
}
