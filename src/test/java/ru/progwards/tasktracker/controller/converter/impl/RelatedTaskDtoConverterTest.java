package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDto;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * тестирование конвертера между DTO и VO
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskDtoConverterTest {

    @Autowired
    private Converter<RelatedTask, RelatedTaskDto> converter;

    @Test
    void toModel() {
        RelatedTask relatedTask = converter.toModel(
                new RelatedTaskDto(1L, new RelationType(
                        1L, "блокирующая", new RelationType(2L, "блокируемая", null)),
                        1L, 2L)
        );

        assertThat(relatedTask, is(notNullValue()));
    }

    @Test
    void toModel_Return_Null() {
        RelatedTask relatedTask = converter.toModel(null);

        assertNull(relatedTask);
    }

    @Test
    void toDto() {
        RelatedTaskDto relatedTaskDto = converter.toDto(
                new RelatedTask(1L, new RelationType(
                        1L, "блокирующая", new RelationType(2L, "блокируемая", null)),
                        1L, 2L)
        );

        assertThat(relatedTaskDto, is(notNullValue()));
    }

    @Test
    void toDto_Return_Null() {
        RelatedTaskDto relatedTaskDto = converter.toDto(null);

        assertNull(relatedTaskDto);
    }
}