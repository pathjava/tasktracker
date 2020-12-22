package ru.progwards.tasktracker.repository.deprecated.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByCode;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByProjectId;
import ru.progwards.tasktracker.repository.deprecated.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Методы работы сущности с базой данных
 *
 * @author Oleg Kiselev
 */
@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskRepository implements Repository<Long, Task>, RepositoryByProjectId<Long, Task>,
        RepositoryUpdateField<Task>, RepositoryByCode<String, Task> {

//    private final @NonNull TaskTypeRepository<Task, Long> repository;

    /**
     * Метод получения коллекции всех задач без привязки к какому-либо проекту
     *
     * @return возвращаем коллекцию всех задач, не помеченных как удаленные
     */
    @Override
    public Collection<Task> get() {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> !entity.isDeleted())
//                .collect(Collectors.toList());
        return null;
    }

    /**
     * Метод получения задачи по числовому идентификатору
     *
     * @param id идентификатор задачи, которую необходимо получить
     * @return возвращаем задачу, полученную по идентификатору
     */
    @Override
    public Task get(Long id) {
//        Task task = jsonHandler.getMap().get(id);
//        return task == null || task.isDeleted() ? null : task;
        return null;
    }

    /**
     * Метод создания задачи
     *
     * @param entity записываемая в БД сущность
     */
    @Override
    public void create(Task entity) {
//        Task task = jsonHandler.getMap().put(entity.getId(), entity);
//        if (task == null)
//            jsonHandler.write();
    }

    /**
     * Метод обновления задачи
     *
     * @param entity сущность обновляемой задачи
     */
    @Override
    public void update(Task entity) {
//        jsonHandler.getMap().remove(entity.getId());
//        entity.setUpdated(ZonedDateTime.now().toEpochSecond());
//        create(entity);
    }

    /**
     * Метод удаления задачи по её числовому идентификатору
     *
     * @param id идентификатор по которому удаляем задачу
     */
    @Override
    public void delete(Long id) {
//        Task task = get(id);
//        if (task != null) {
//            jsonHandler.getMap().remove(id);
//            task.setDeleted(true);
//            create(task);
//        }
    }

    /**
     * Метод получения коллекции задач проекта по идентификатору проекта
     *
     * @param projectId идентификатор проекта
     * @return возвращаем коллекцию всех задач проекта, не помеченных как удаленные
     */
    @Override
    public Collection<Task> getByProjectId(Long projectId) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getProject().getId().equals(projectId) && !entity.isDeleted())
//                .collect(Collectors.toList());
        return null;
    }

    /**
     * Метод обновления определенного поля задачи новым значением
     *
     * @param oneValue объект, содержащий идентификатор и тип и значение обновляемого поля задачи
     */
    @Override
    public void updateField(UpdateOneValue oneValue) {
        Task entity = get(oneValue.getId());
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
    public Task getByCode(String code) {
//        return jsonHandler.getMap().values().stream()
//                .filter(entity -> entity.getCode().equals(code) && !entity.isDeleted())
//                .findFirst().orElse(null);
        return null;
    }
}

