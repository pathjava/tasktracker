package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.RelationType;

import java.util.Optional;

/**
 * @author Oleg Kiselev
 */
@Repository
@Transactional(readOnly = true)
public interface RelationTypeRepository extends JpaRepository<RelationType, Long> {

    /**
     * Метод проверки существования в БД RelationType с именем name
     *
     * @param name имя RelationType
     * @return true - если в БД есть RelationType с проверяемым именем и false - если нет
     */
    boolean existsByName(String name);

    /**
     * Метод получения RelationType по имени
     *
     * @param name имя RelationType
     * @return RelationType
     */
    Optional<RelationType> findByName(String name);

}
