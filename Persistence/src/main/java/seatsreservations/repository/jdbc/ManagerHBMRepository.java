package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import seatsreservations.domain.Manager;
import seatsreservations.repository.ManagerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ManagerHBMRepository implements ManagerRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ManagerHBMRepository(Properties props) {
        logger.info("Initializing ManagerHBMRepository with properties: {} ",props);
        System.out.println("Initializing ManagerHBMRepository with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Manager findOne(String s) {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Manager manager = session.createQuery("from Manager e where e.id like :id", Manager.class).setParameter("id", s).setMaxResults(1).uniqueResult();
                tx.commit();
                return manager;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Manager> findAll() {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Manager> managerList = session.createQuery("from Manager ", Manager.class).list();
                tx.commit();
                return managerList;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Manager save(Manager entity) {
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
    public Manager update(Manager entity) {
        return null;
    }

    @Override
    public Manager delete(String s) {
        return null;
    }
}
