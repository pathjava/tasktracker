package repository.dao;

import java.util.Collection;

public interface Repository<T, E> {
    Collection<E> get();
    E get(T id);
    void create(T id);
    void update(T id);
    void delete(T id);
}
