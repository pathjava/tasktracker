package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.Duration;
import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogConverterTest {

    @Autowired
    private Converter<WorkLogEntity, WorkLog> converter;
    @Autowired
    private WorkLogConverter workLogConverter;

    @Test
    void toVo() {
        WorkLog workLog = converter.toVo(
                new WorkLogEntity(
                        null, null, null, null,
                        ZonedDateTime.now().toEpochSecond(), null
                )
        );

        assertThat(workLog, is(notNullValue()));
    }

    @Test
    void toVo_Return_Null() {
        WorkLog workLog = converter.toVo(null);

        assertNull(workLog);
    }

    @Test
    void checkDurationEntityNotNull() {
        Long seconds = Duration.ofHours(12).getSeconds();
        Duration duration = workLogConverter.checkDurationEntityNotNull(seconds);

        assertNotNull(duration);
    }

    @Test
    void checkDurationEntityNotNull_Return_Null() {
        Duration duration = workLogConverter.checkDurationEntityNotNull(null);

        assertNull(duration);
    }

    @Test
    void checkUpdatedEntityNotNull() {
        Long seconds = ZonedDateTime.now().toEpochSecond();
        ZonedDateTime zdt = workLogConverter.checkUpdatedEntityNotNull(seconds);

        assertNotNull(zdt);
    }

    @Test
    void checkUpdatedEntityNotNull_Return_Null() {
        ZonedDateTime zdt = workLogConverter.checkUpdatedEntityNotNull(null);

        assertNull(zdt);
    }

    @Test
    void toEntity() {
        User user = mock(User.class);

        WorkLogEntity entity = converter.toEntity(
                new WorkLog(
                        null, null, null, user, ZonedDateTime.now(),
                        null, null, null
                )
        );

        assertThat(entity, is(notNullValue()));
    }

    @Test
    void toEntity_Return_Null() {
        WorkLogEntity entity = converter.toEntity(null);

        assertNull(entity);
    }

    @Test
    void checkDurationValueObjectNotNull() {
        Duration duration = Duration.ofHours(12);
        Long seconds = workLogConverter.checkDurationValueObjectNotNull(duration);

        assertNotNull(seconds);
    }

    @Test
    void checkDurationValueObjectNotNull_Return_Null() {
        Long seconds = workLogConverter.checkDurationValueObjectNotNull(null);

        assertNull(seconds);
    }

    @Test
    void checkZonedDateTimeValueObjectNotNull() {
        ZonedDateTime zdt = ZonedDateTime.now();
        Long seconds = workLogConverter.checkZonedDateTimeValueObjectNotNull(zdt);

        assertNotNull(seconds);
    }

    @Test
    void checkZonedDateTimeValueObjectNotNull_Return_Null() {
        Long seconds = workLogConverter.checkZonedDateTimeValueObjectNotNull(null);

        assertNull(seconds);
    }
}