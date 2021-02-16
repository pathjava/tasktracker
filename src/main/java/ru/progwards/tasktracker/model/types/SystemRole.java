package ru.progwards.tasktracker.model.types;

/**
 * Роли пользователей
 * @author Pavel Khovaylo
 */
public enum SystemRole {
    /**
     * Полные права на роли
     */
    ROLE_ADMIN,
    /**
     * Права берутся из привязанного к роли AccessRule
     */
    ROLE_USER
}
