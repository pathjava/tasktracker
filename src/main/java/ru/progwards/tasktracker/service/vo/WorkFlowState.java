package ru.progwards.tasktracker.service.vo;

/**
 * Состояние воркфлоу
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
