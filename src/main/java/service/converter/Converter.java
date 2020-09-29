package service.converter;

public interface Converter<E, V> {
    V converTo(E entity);
    E convertFrom(V valueObject);
}
