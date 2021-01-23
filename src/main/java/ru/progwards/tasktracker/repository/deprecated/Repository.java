package ru.progwards.tasktracker.repository.deprecated;

import java.util.Collection;

/**
 * Интерфейс для общения с одной "табличкой" репозитория
 *
 * @param <T> тип поля идентификатора (Long)
 * @param <E> тип хранимой в таблице сущности
 */
@Deprecated
public interface Repository<T, E> {

    Collection<E> get();

    /**
     * Получить сущность по идентификатору
     *
     * @param id идентификатор
     * @return сущность
     */
    E get(T id);

    /**
     * Положить в репозиторий новый объект
     *
     * @param entity новая сущность
     */
    void create(E entity);

    /**
     * Обновить поля сущности в репозитории
     * Идннтификатор не может быть изменен
     *
     * @param entity измененная сущность
     */
    void update(E entity);

    /**
     * Удалить сущность из репозитория
     *
     * @param id идентификатор удаляемой сущности
     */
    void delete(T id);

}
