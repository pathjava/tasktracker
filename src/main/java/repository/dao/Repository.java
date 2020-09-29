package repository.dao;

import java.util.Collection;

public interface Repository<T, E> {
    Collection<E> get();
    E get(T id);

}
