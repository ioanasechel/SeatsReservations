package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import seatsreservations.domain.Reservation;
import seatsreservations.domain.Show;
import seatsreservations.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
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
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Reservation> reservations = session.createQuery("from Reservation ", Reservation.class).list();
                tx.commit();
                return reservations;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Reservation save(Reservation entity) {
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
    public Reservation update(Reservation entity) {
        return null;
    }

    @Override
    public Reservation delete(Integer integer) {
        String idStr = integer.toString();
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Reservation crit = session.createQuery("from Reservation where str(ID) like :id", Reservation.class)
                        .setParameter("id", idStr)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(crit);
                tx.commit();
                return crit;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}

