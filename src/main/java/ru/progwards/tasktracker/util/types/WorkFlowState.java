package ru.progwards.tasktracker.util.types;

/**
 * Состояние воркфлоу
 *
 * @author Gregory Lobkov
 */
public enum WorkFlowState {

    /**
     * Задача ожидает выполнения
     */
    TO_DO,

    /**
     * Задача в работе
     */
    IN_PROGRESS,

    /**
     * Работы над задачей завершены
     */
    DONE

}
