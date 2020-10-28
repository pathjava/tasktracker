package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class RelationTypeConverterTest {

    @Autowired
    private Converter<RelationTypeEntity, RelationType> relationTypeConverter;

    @Test
    void toVo_return_Null() {
        RelationType type = relationTypeConverter.toVo(null);

        assertThat(type, is(nullValue()));
    }

    @Test
    void toVo_return_NotNull() {
        RelationType type = relationTypeConverter.toVo(
                new RelationTypeEntity(
                        1L, "блокирующая", new RelationTypeEntity(2L, "блокируемая", null
                ))
        );

        assertThat(type, is(notNullValue()));
    }

    @Test
    void toEntity_returnNull() {
        RelationTypeEntity typeEntity = relationTypeConverter.toEntity(null);

        assertThat(typeEntity, is(nullValue()));
    }

    @Test
    void toEntity_return_Not_Null() {
        RelationTypeEntity typeEntity = relationTypeConverter.toEntity(
                new RelationType(
                        1L, "блокирующая", new RelationType(2L, "блокируемая", null
                ))
        );
    }
}