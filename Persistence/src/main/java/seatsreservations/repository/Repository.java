package seatsreservations.repository;

public interface Repository<ID, E> {
    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity);
    E update(E entity);
    E delete(ID id);
}
