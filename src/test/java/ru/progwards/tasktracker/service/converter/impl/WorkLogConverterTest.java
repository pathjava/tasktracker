package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogConverterTest {

    @Autowired
    private Converter<WorkLogEntity, WorkLog> converter;

    @Test
    void toVo_Return_Null() {
        WorkLog workLog = converter.toVo(null);

        assertNull(workLog);
    }

    @Test
    void toVo_Return_Not_Null() {
        WorkLog workLog = converter.toVo(
                new WorkLogEntity(
                        null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description"
                )
        );

        assertThat(workLog, is(notNullValue()));
    }

    @Test
    void toEntity_Return_Null() {
        WorkLogEntity entity = converter.toEntity(null);

        assertNull(entity);
    }

    @Test
    void toEntity_Return_Not_Null() {
        WorkLogEntity entity = converter.toEntity(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description", null, null
                )
        );

        assertThat(entity, is(notNullValue()));
    }
}