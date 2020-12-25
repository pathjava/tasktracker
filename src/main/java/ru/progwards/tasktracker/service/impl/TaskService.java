package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.repository.RelatedTaskRepository;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.service.*;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Бизнес-логика создания задачи
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService implements CreateService<Task>, GetListService<Task>, GetService<Long, Task>,
        RefreshService<Task>, RemoveService<Task>, UpdateOneFieldService<Task> {

    private final @NonNull TaskRepository taskRepository;
    private final @NonNull ProjectRepository projectRepository;
    private final @NonNull RelatedTaskRepository relatedTaskRepository;

    /**
     * Метод создает задачу
     * 1) создается текстовый код задачи на основе префикса проекта
     * 2) устанавливается время создания задачи
     * 3) устанавливается статус WorkFlowStatus
     *
     * @param model value object
     */
    @Transactional
    @Override
    public void create(Task model) {
        model.setCode(generateTaskCode(model.getProject().getId()));
        model.setCreated(ZonedDateTime.now());

        if (model.getType() != null) {
            WorkFlow workFlow = model.getType().getWorkFlow();
            if (workFlow != null)
                model.setStatus(workFlow.getStartStatus());
        }
        taskRepository.save(model);
    }

    /**
     * Метод создания кода задачи на основе префикса проекта
     * Из бизнес-объекта проекта получаем крайний индекс задачи,
     * инкрементируем +1 и создаем код задачи, обновляем проект
     *
     * @param project_id идентификатор проекта, к которому принадлежит задача
     * @return код задачи в формате "NGR-1"
     */
    @Transactional
    public String generateTaskCode(Long project_id) {
        Project project = projectRepository.findById(project_id)
                .orElseThrow(() -> new NotFoundException("Project id=" + project_id + " not found"));
        Long lastTaskCode = project.getLastTaskCode() + 1;
        String taskCode = project.getPrefix() + "-" + lastTaskCode;
        project.setLastTaskCode(lastTaskCode);
        projectRepository.save(project);
        return taskCode;
    }

    /**
     * Метод получения всех задач без привязки к какому-либо проекту
     *
     * @return коллекцию задач (может иметь пустое значение)
     */
    @Override
    public List<Task> getList() {
        return taskRepository.findAllByDeletedFalse();
    }

    /**
     * Метод получения задачи по идентификатору
     *
     * @param id идентификатор по которому необходимо получить задачу
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(Long id) {
        return taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Task id=" + id + " not found"));
    }

    /**
     * Метод обновления задачи
     *
     * @param model value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Transactional
    @Override
    public void refresh(Task model) {
        if (model.getTimeLeft().isNegative())
            model.setTimeLeft(Duration.ZERO);

        model.setUpdated(ZonedDateTime.now());

        taskRepository.save(model);
    }

    /**
     * Метод удаления задачи
     * Предварительно в методе deleteRelatedTasks(Long id) проверяем наличие
     * связей на задачи и если они есть, удаляем встречные (входящие)
     *
     * @param model value object - объект бизнес логики (задача), который необходимо удалить
     */
    @Transactional
    @Override
    public void remove(Task model) {
        deleteRelatedTasksBeforeDeleteTask(model);

        model.setDeleted(true);
        taskRepository.save(model);
    }

    /**
     * Метод удаления связанных RelatedTask удаляемой задачи Task
     *
     * @param model задача Task
     */
    private void deleteRelatedTasksBeforeDeleteTask(Task model) {
        List<RelatedTask> list = relatedTaskRepository.findAllByCurrentTask(model);

        for (RelatedTask relatedTask : list) {
            relatedTaskRepository.delete(relatedTask);
        }

        /* old version */
//        Collection<RelatedTask> collection = listByAttachedTaskId.getListByAttachedTaskId(id);
//        if (!collection.isEmpty()){
//            collection.forEach(relatedTask -> repositoryRelatedTask.delete(relatedTask.getId()));
//        }
    }

    /**
     * Метод обновления поля задачи
     *
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     */
    @Transactional
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
        if (oneValue.getFieldName().equals("code"))
            throw new OperationIsNotPossibleException("Обновление поля: " + oneValue.getFieldName() + " невозможно!");

        Task task = taskRepository.findById(oneValue.getId())
                .orElseThrow(() -> new NotFoundException("Task id=" + oneValue.getId() + " not found"));

        for (Field declaredField : task.getClass().getDeclaredFields()) {
            if (declaredField.getName().equals(oneValue.getFieldName())) {
                declaredField.setAccessible(true);
                try {
                    declaredField.set(task, oneValue.getNewValue());
                    taskRepository.save(task);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* old version */
//    /**
//     * Метод обновления определенного поля задачи новым значением
//     *
//     * @param oneValue объект, содержащий идентификатор и тип и значение обновляемого поля задачи
//     */
//    @Override
//    public void updateField(UpdateOneValue oneValue) {
//        TaskEntity entity = get(oneValue.getId());
//        String field = oneValue.getFieldName();
//
//        for (Field declaredField : entity.getClass().getDeclaredFields()) {
//            if (declaredField.getName().equals(field)) {
//                declaredField.setAccessible(true);
//                try {
//                    declaredField.set(entity, oneValue.getNewValue());
//                    update(entity);
//                    break;
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    /* -- Deprecated -- */
//    /**
//     * Метод получения коллекции задач по идентификатору проекта
//     *
//     * @param projectId идентификатор проекта, для которого делается выборка задач (не помеченных как удаленные)
//     * @return коллекция задач (может иметь пустое значение)
//     */
//    @Override
//    public List<Task> getListByProjectId(Long projectId) {
//        return repository.get().stream()
//                .filter(taskEntity -> taskEntity.getProject().getId().equals(projectId) && !taskEntity.isDeleted())
//                .map(taskEntity -> converter.toVo(taskEntity))
//                .collect(Collectors.toList());
//    }

}
