package ru.progwards.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.UserRole;

/**
 * @author Artem Dikov
 */
@Repository
@Transactional(readOnly = true)
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public UserRole findByName(String name);
}
