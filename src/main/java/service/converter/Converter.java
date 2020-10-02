package service.converter;

public interface Converter<E, V> {
    V convertTo(E entity);
    E convertFrom(V valueObject);
}
