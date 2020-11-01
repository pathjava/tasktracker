package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

import static org.mockito.Mockito.*;

/**
 * тестирование создания и удаления связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityRepositoryTest {

    @Mock
    private Repository<Long, RelatedTaskEntity> entityRepository;

    @Test
    void create() {
        entityRepository.create(
                new RelatedTaskEntity(
                        1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        verify(entityRepository, times(1)).create(any(RelatedTaskEntity.class));
    }

    @Test
    void delete() {
        entityRepository.delete(1L);

        verify(entityRepository, times(1)).delete(1L);
    }
}