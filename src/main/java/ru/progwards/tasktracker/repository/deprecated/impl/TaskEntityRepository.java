package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.progwards.tasktracker.repository.deprecated.*;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.model.UpdateOneValue;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@org.springframework.stereotype.Repository
public class TaskEntityRepository implements Repository<Long, TaskEntity>, RepositoryByProjectId<Long, TaskEntity>,
        RepositoryUpdateField<TaskEntity>, RepositoryByCode<String, TaskEntity> {

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandler;

    /**
     * Метод получения коллекции всех задач без привязки к какому-либо проекту
     *
     * @return возвращаем коллекцию всех задач, не помеченных как удаленные
     */
    @Override
    public Collection<TaskEntity> get() {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> !entity.isDeleted())
                .collect(Collectors.toList());
    }

    /**
     * Метод получения задачи по числовому идентификатору
     *
     * @param id идентификатор задачи, которую необходимо получить
     * @return возвращаем задачу, полученную по идентификатору
     */
    @Override
    public TaskEntity get(Long id) {
        TaskEntity task = jsonHandler.getMap().get(id);
        return task == null || task.isDeleted() ? null : task;
    }

    /**
     * Метод создания задачи
     *
     * @param entity записываемая в БД сущность
     */
    @Override
    public void create(TaskEntity entity) {
        TaskEntity task = jsonHandler.getMap().put(entity.getId(), entity);
        if (task == null)
            jsonHandler.write();
    }

    /**
     * Метод обновления задачи
     *
     * @param entity сущность обновляемой задачи
     */
    @Override
    public void update(TaskEntity entity) {
        jsonHandler.getMap().remove(entity.getId());
        entity.setUpdated(ZonedDateTime.now().toEpochSecond());
        create(entity);
    }

    /**
     * Метод удаления задачи по её числовому идентификатору
     *
     * @param id идентификатор по которому удаляем задачу
     */
    @Override
    public void delete(Long id) {
        TaskEntity task = get(id);
        if (task != null) {
            jsonHandler.getMap().remove(id);
            task.setDeleted(true);
            create(task);
        }
    }

    /**
     * Метод получения коллекции задач проекта по идентификатору проекта
     *
     * @param projectId идентификатор проекта
     * @return возвращаем коллекцию всех задач проекта, не помеченных как удаленные
     */
    @Override
    public Collection<TaskEntity> getByProjectId(Long projectId) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getProject().getId().equals(projectId) && !entity.isDeleted())
                .collect(Collectors.toList());
    }

    /**
     * Метод обновления определенного поля задачи новым значением
     *
     * @param oneValue объект, содержащий идентификатор и тип и значение обновляемого поля задачи
     */
    @Override
    public void updateField(UpdateOneValue oneValue) {
        TaskEntity entity = get(oneValue.getId());
        String field = oneValue.getFieldName();

        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (declaredField.getName().equals(field)) {
                declaredField.setAccessible(true);
                try {
                    declaredField.set(entity, oneValue.getNewValue());
                    update(entity);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод получения задачи по её текстовому коду
     *
     * @param code код задачи, генерируемый на основе префикса проекта и идентификатора
     * @return возвращает сущность из БД
     */
    @Override
    public TaskEntity getByCode(String code) {
        return jsonHandler.getMap().values().stream()
                .filter(entity -> entity.getCode().equals(code) && !entity.isDeleted())
                .findFirst().orElse(null);
    }
}

