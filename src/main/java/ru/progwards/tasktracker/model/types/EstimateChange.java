package ru.progwards.tasktracker.model.types;

/**
 * AUTO_REDUCE - уменьшение оставшегося времени на getSpent()
 * SET_TO_VALUE - установка произвольного оставшегося времени
 * REDUCE_BY_VALUE - уменьшение оставшегося времени на произвольное значение
 * DONT_CHANGE - не уменьшать оставшееся время
 * INCREASE_BY_VALUE - увеличение оставшегося времени на произвольное значение
 */
public enum EstimateChange {
    AUTO_REDUCE, DONT_CHANGE, SET_TO_VALUE, REDUCE_BY_VALUE, INCREASE_BY_VALUE
}
