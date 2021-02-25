package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;

/**
 * Бизнес-логика создания задачи (Task)
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskService implements CreateService<Task>, GetListService<Task>, GetService<Long, Task>,
        RefreshService<Task>, RemoveService<Task>, UpdateOneFieldService<Task>, TemplateService<Task>,
        Sorting<Task>, Paging<Task> {

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
        model.setCode(generateTaskCode(model.getProject()));
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
     * @param project проект, к которому принадлежит задача
     * @return код задачи в формате "NGR-1"
     */
    @Transactional
    public String generateTaskCode(Project project) {
        Long lastTaskCode = project.getLastTaskCode() + 1;
        String taskCode = project.getPrefix() + "-" + lastTaskCode;
        project.setLastTaskCode(lastTaskCode);
        projectRepository.save(project);
        return taskCode.toUpperCase();
    }

    /**
     * Метод получения всех задач (Task) без привязки к какому-либо проекту
     *
     * @return коллекция задач (может иметь пустое значение)
     */
    @Override
    public List<Task> getList() {
        return taskRepository.findAllByDeletedFalse();
    }

    /**
     * Метод получения всех отсортированных задач (Task) без привязки к какому-либо проекту
     *
     * @param sort параметр/параметры, по которым происходит сортировка
     * @return коллекция задач (может иметь пустое значение)
     */
    @Override
    public List<Task> getSortList(Sort sort) {
        return taskRepository.findAll(sort);
    }

    /**
     * @param id
     * @param sort
     * @return
     */
    @Override
    public List<Task> getSortListById(Long id, Sort sort) {
        return taskRepository.findByProjectId(id, sort);
    }

    /**
     * Метод получения страницы пагинации задач (Task) без привязки к какому-либо проекту
     *
     * @param pageable параметр/параметры по которым получаем страницу пагинации объектов
     * @return страница пагинации задач
     */
    @Override
    public Page<Task> getPageableList(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    /**
     * @param id
     * @param pageable
     * @return
     */
    @Override
    public Page<Task> getPageableListById(Long id, Pageable pageable) {
        return taskRepository.findByProjectId(id, pageable);
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
                .orElseThrow(() -> new NotFoundException(format("Task id=%s not found", id)));
    }

    /**
     * Метод обновления задачи (Task)
     *
     * @param model value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Transactional
    @Override
    public void refresh(Task model) {
        if (model.getTimeLeft() != null && model.getTimeLeft().isNegative())
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

        taskRepository.markTaskAsDeleted(true, model.getId());
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
                .orElseThrow(() -> new NotFoundException(format("Task id=%s not found", oneValue.getId())));

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

    CreateService<Task> taskCreateService;
    GetListService<TaskPriority> taskPriorityGetListService;
    /**
     * Метод создания Task по шаблону
     *
     * @param args – [0] - Project
     */
    @Transactional
    @Override
    public List<Task> createFromTemplate(Object... args) {
        if (args.length != 2)
            throw new OperationIsNotPossibleException("Task.createFromTemplate: 1 argument expected");
        if (!(args[0] instanceof Project))
            throw new OperationIsNotPossibleException("Task.createFromTemplate: argument 0 must be Project");
        if (!(args[1] instanceof User))
            throw new OperationIsNotPossibleException("Task.createFromTemplate: argument 1 must be User");
        Project project = (Project) args[0];
        User user = (User) args[1];

        String[][] names = { // массив входных параметров: имя, описание
                {"Это ваша первая задача", "Это ваша первая задача.\n" +
                        "Проблемы - это то, что вы делаете в проекте. В бизнес-проектах проблемы называются задачами.\n" +
                        "\n" +
                        "Типы задач\n" +
                        "Задача может представлять собой документ, творческий актив, покупку и даже человека.\n"},
                {"Рабочие процессы и статусы", "Рабочие процессы\n" +
                        "Рабочие процессы определяют шаги для выполнения задачи. Чтобы увидеть рабочий процесс, через который проходит эта задача, нажмите «Просмотреть рабочий процесс» выше.\n" +
                        "\n" +
                        "Статус\n" +
                        "Статус представляет собой «состояние» задачи в определенной точке рабочего процесса. Текущий статус вашей задачи можно посмотреть в разделе «Подробности» выше. Когда вы будете готовы перейти к следующему шагу, нажмите соответствующую кнопку перехода."},
                {"Редактирование задач","Редактирование задач\n" +
                        "Наведите курсор на контент, который хотите отредактировать, и внесите изменения. Нажмите на галочку, и все готово! Вы также можете редактировать, используя сочетания клавиш или нажав кнопку «Изменить». И не забудьте поручить кому-нибудь задачу.\n" +
                        "\n" +
                        "Комментирование\n" +
                        "Вы можете добавить комментарии к задаче ниже. Комментарии - отличный способ общаться с вашей командой и оставаться в курсе. Кроме того, вы можете уведомить определенных членов команды, используя @mentions."},
                {"Поиск информации","Поиск информации\n" +
                        "Используйте панель поиска в правом верхнем углу, чтобы быстро найти конкретную задачу.\n" +
                        "Для более расширенного поиска нажмите «Искать проблемы» в меню «Проблемы»."}
        };
        Random random = new Random();
        List<TaskType> taskTypes = project.getTaskTypes();
        List<TaskPriority> priorities = taskPriorityGetListService.getList();
        List<Task> result = new ArrayList<>(names.length);
        for (String[] name: names) {
            int randomTaskType = random.nextInt(taskTypes.size());
            int randomPriority = random.nextInt(priorities.size());
            Task task = new Task();
            task.setName(name[0]);
            task.setDescription(name[1]);
            task.setType(taskTypes.get(randomTaskType));
            task.setPriority(priorities.get(randomPriority));
            task.setProject(project);
            task.setAuthor(user);
            taskCreateService.create(task);
            result.add(task);
        }
        return result;
    }
}
