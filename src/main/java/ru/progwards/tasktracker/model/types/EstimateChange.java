package ru.progwards.tasktracker.model.types;


public enum EstimateChange {
    /**
     * AUTO_REDUCE - уменьшение оставшегося времени на getSpent()
     */
    AUTO_REDUCE,

    /**
     * DONT_CHANGE - не уменьшать оставшееся время
     */
    DONT_CHANGE,

    /**
     * SET_TO_VALUE - установка произвольного оставшегося времени
     */
    SET_TO_VALUE,

    /**
     * REDUCE_BY_VALUE - уменьшение оставшегося времени на произвольное значение
     */
    REDUCE_BY_VALUE,

    /**
     * INCREASE_BY_VALUE - увеличение оставшегося времени на произвольное значение
     */
    INCREASE_BY_VALUE
}
