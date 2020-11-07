package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkLogDto;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * тестирование конвертирования между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogDtoConverterTest {

    @Autowired
    private Converter<WorkLog, WorkLogDto> converter;

    @Test
    void toModel_Return_Null() {
        WorkLog workLog = converter.toModel(null);

        assertNull(workLog);
    }

    @Test
    void toModel_Return_Not_Null() {
        WorkLog workLog = converter.toModel(
                new WorkLogDto(
                        1L, 2L, null, null, ZonedDateTime.now(),
                        "Description", null, null
                )
        );

        assertThat(workLog, is(notNullValue()));
    }

    @Test
    void toDto_Return_Null() {
        WorkLogDto workLogDto = converter.toDto(null);

        assertNull(workLogDto);
    }

    @Test
    void toDto_Return_Not_Null() {
        WorkLogDto workLogDto = converter.toDto(
                new WorkLog(
                        1L, 2L, null, null, ZonedDateTime.now(),
                        "Description", null, null
                )
        );

        assertThat(workLogDto, is(notNullValue()));
    }
}