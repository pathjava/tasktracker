package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
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
 * Бизнес-логика создания задачи (Task)
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class TaskService implements CreateService<Task>, GetListService<Task>, GetService<Long, Task>,
        RefreshService<Task>, RemoveService<Task>, UpdateOneFieldService<Task> {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final RelatedTaskRepository relatedTaskRepository;
    private final RelatedTaskService relatedTaskService;

    /**
     * Метод создает задачу  (Task)
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
     * Метод получения всех задач (Task) без привязки к какому-либо проекту
     *
     * @return коллекцию задач (может иметь пустое значение)
     */
    @Override
    public List<Task> getList() {
        return taskRepository.findAllByDeletedFalse();
    }

    /**
     * Метод получения задачи (Task) по идентификатору
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
     * Метод обновления задачи (Task)
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
     * Метод удаления задачи (Task)
     * Предварительно в методе deleteRelatedTasks(Long id) проверяем наличие
     * связей на задачи и если они есть, удаляем встречные (входящие)
     *
     * @param model value object - объект бизнес логики (задача), который необходимо удалить
     */
    @Transactional
    @Override
    public void remove(Task model) {
        deleteRelatedTasksBeforeDeleteTask(model);

//        model.setDeleted(true);
//        taskRepository.save(model);
        taskRepository.updateTaskAsDeleted(true, model.getId());
    }

    /**
     * Метод удаления связанных RelatedTask удаляемой задачи (Task)
     *
     * @param model задача Task
     */
    private void deleteRelatedTasksBeforeDeleteTask(Task model) {
        List<RelatedTask> list = relatedTaskRepository.findAllByCurrentTask(model);

        for (RelatedTask relatedTask : list) {
            relatedTaskService.remove(relatedTask);
        }
    }

    /**
     * Метод обновления поля задачи (Task)
     *
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     */
    @Transactional
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
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

}
