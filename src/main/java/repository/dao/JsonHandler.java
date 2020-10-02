package repository.dao;

import java.util.List;

public interface JsonHandler<T, E> {
    void write();
    List<E> read();
}
