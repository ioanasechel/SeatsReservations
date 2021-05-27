package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import seatsreservations.domain.Manager;
import seatsreservations.domain.Spectator;
import seatsreservations.repository.SpectatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpectatorHBMRepository implements SpectatorRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public SpectatorHBMRepository(Properties props) {
        logger.info("Initializing SpectatorHBMRepository with properties: {} ",props);
        System.out.println("Initializing SpectatorHBMRepository with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public Spectator findOne(String s) {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Spectator spectator = session.createQuery("from Spectator e where e.id like :id", Spectator.class).setParameter("id", s).setMaxResults(1).uniqueResult();
                tx.commit();
                return spectator;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Spectator> findAll() {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Spectator> spectatorList = session.createQuery("from Spectator ", Spectator.class).list();
                tx.commit();
                return spectatorList;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Spectator save(Spectator entity) {
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
    public Spectator update(Spectator entity) {
        return null;
    }

    @Override
    public Spectator delete(String s) {
        return null;
    }
}
