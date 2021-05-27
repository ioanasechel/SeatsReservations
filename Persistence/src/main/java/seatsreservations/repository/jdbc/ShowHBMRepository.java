package seatsreservations.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import seatsreservations.domain.Show;
import seatsreservations.repository.ShowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ShowHBMRepository implements ShowRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ShowHBMRepository(Properties props) {
        logger.info("Initializing ShowHBMRepository with properties: {} ",props);
        System.out.println("Initializing ShowHBMRepository with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Show findOne(Integer integer) {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Show show = session.createQuery("from Show sh where str(sh.id) like :id", Show.class).setParameter("id", integer).setMaxResults(1).uniqueResult();
                tx.commit();
                return show;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;    }

    @Override
    public Iterable<Show> findAll() {
        try(Session session = dbUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Show> shows = session.createQuery("from Show", Show.class).list();
                tx.commit();
                return shows;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Show save(Show entity) {
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
    public Show update(Show entity) {
        try(Session session = dbUtils.getSessionFactory().openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Show show = session.load( Show.class, entity.getID());
                show.setDate(entity.getDate());
                show.setTitle(entity.getTitle());
                tx.commit();
                return null;
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        return entity;
    }

    @Override
    public Show delete(Integer integer) {
        return null;
    }
}
