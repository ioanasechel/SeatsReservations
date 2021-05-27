package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import seatsreservations.domain.Seat;
import seatsreservations.domain.Show;
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
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Seat seat = session.createQuery("from Seat sh where str(sh.id) like :id", Seat.class).setParameter("id", integer.toString()).setMaxResults(1).uniqueResult();
                tx.commit();
                return seat;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Seat> findAll() {
        return null;
    }

    @Override
    public Seat save(Seat entity) {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
                return null;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return entity;
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
