package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class RelatedTaskConverterTest {

    @Autowired
    private Converter<RelatedTaskEntity, RelatedTask> relatedTaskConverter;

    @Test
    void toVo_return_Null() {
        RelatedTask task = relatedTaskConverter.toVo(null);

        assertThat(task, is(nullValue()));
    }

    @Test
    void toVo_return_Not_Null() {
        RelatedTask task = relatedTaskConverter.toVo(
                new RelatedTaskEntity(
                        1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        assertThat(task, is(notNullValue()));
    }

    @Test
    void toEntity_return_Null() {
        RelatedTaskEntity taskEntity = relatedTaskConverter.toEntity(null);

        assertThat(taskEntity, is(nullValue()));
    }

    @Test
    void toEntity_return_Not_Null() {
        RelatedTaskEntity taskEntity = relatedTaskConverter.toEntity(
                new RelatedTask(
                        1L, new RelationType(1L, "блокирующая", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        assertThat(taskEntity, is(notNullValue()));
    }
}