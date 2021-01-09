package ru.progwards.tasktracker.model.types;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Тестирование соответствия значений EstimateChange
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class EstimateChangeTest {

    @Test
    void values() {
        for (EstimateChange value : EstimateChange.values()) {
            switch (value) {
                case AUTO_REDUCE:
                case DONT_CHANGE:
                case SET_TO_VALUE:
                case REDUCE_BY_VALUE:
                case INCREASE_BY_VALUE:
                    break;
                default:
                    throw new IllegalArgumentException(value.toString());
            }
        }
    }

    @Test
    void valueOf() {
        for (String s : new String[]{"AUTO_REDUCE", "DONT_CHANGE", "SET_TO_VALUE", "REDUCE_BY_VALUE", "INCREASE_BY_VALUE"}) {
            EstimateChange.valueOf(s);
        }
    }
}