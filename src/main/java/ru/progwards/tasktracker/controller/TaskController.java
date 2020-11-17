package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с задачами
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping("/rest")
public class TaskController {

    @Autowired
    private GetService<Long, Task> getService;

    @Autowired
    private RemoveService<Task> removeService;

    @Autowired
    private CreateService<Task> createService;

    @Autowired
    private RefreshService<Task> refreshService;

    @Autowired
    private Converter<Task, TaskDtoPreview> dtoPreviewConverter;

    @Autowired
    private Converter<Task, TaskDtoFull> dtoFullConverter;

    @Autowired
    private GetService<String, Task> byCodeGetService;

    @Qualifier("taskUpdateOneFieldService")
    @Autowired
    private UpdateOneFieldService<Task> oneFieldService;

    @Autowired
    private GetListByProjectService<Long, Task> listByProjectService;

    /**
     * Метод получения коллекции задач по идентификатору проекта
     *
     * @param id идентификатор проекта
     * @return коллекция превью задач
     */
    @GetMapping("/project/{id}/tasks")
    public ResponseEntity<Collection<TaskDtoPreview>> getListTasks(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Collection<TaskDtoPreview> tasks = listByProjectService.getListByProjectId(id).stream()
                .map(task -> dtoPreviewConverter.toDto(task))
                .collect(Collectors.toUnmodifiableList());

        if (tasks.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список задач пустой!");

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Метод создания задачи
     *
     * @param taskDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную задачу
     */
    @PostMapping("/task/create")
    public ResponseEntity<TaskDtoFull> createTask(@RequestBody TaskDtoFull taskDtoFull) {
        if (taskDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        Task task = dtoFullConverter.toModel(taskDtoFull);
        createService.create(task);
        TaskDtoFull createdTask = dtoFullConverter.toDto(task);

        //TODO - перед добавлением проверять, есть ли уже в БД такая задача, но id генерируется в entity - подумать

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Метод обновления задачи
     *
     * @param id идентификатор задачи
     * @param taskDtoFull обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленную задачу
     */
    @PutMapping("/task/{id}/update")
    public ResponseEntity<TaskDtoFull> updateTask(@PathVariable Long id, @RequestBody TaskDtoFull taskDtoFull) {
        if (taskDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        if (!id.equals(taskDtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        Task task = dtoFullConverter.toModel(taskDtoFull);
        refreshService.refresh(task);
        TaskDtoFull updatedTask = dtoFullConverter.toDto(task);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Метод удаления задачи
     *
     * @param id идентификатор задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping("/task/{id}/delete")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Task task = getService.get(id);
        if (task != null)
            removeService.remove(task);
        else
            throw new NotFoundException("Задача с id: " + id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Метод поиска задачи по текстовому коду
     *
     * @param code текстовый идентификатор (код) задачи, создаваемый на основе префикса проекта
     * @return возвращает найденную задачу
     */
    @GetMapping("/task/{code}")
    public ResponseEntity<TaskDtoFull> getByCodeTask(@PathVariable String code) {
        if (code == null)
            throw new BadRequestException("Code не задан или задан неверно!");

        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get(code));

        if (task == null) //TODO - пустая задача или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Задача с code: " + code + " не найдена!");

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод обновления одного поля задачи
     *
     * @param id идентификатор задачи
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     * @return статус
     */
    @PutMapping("/task/{id}/field")
    public ResponseEntity<UpdateOneValue> updateOneField(
            @PathVariable Long id, @RequestBody UpdateOneValue oneValue
    ) {
        if (oneValue == null)
            throw new BadRequestException("Значение обновляемого поля отсутствует!");

        if (!id.equals(oneValue.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        oneFieldService.updateOneField(oneValue);

        return new ResponseEntity<>(HttpStatus.OK); //TODO - стоит ли тут что-то возвращать?
    }
}