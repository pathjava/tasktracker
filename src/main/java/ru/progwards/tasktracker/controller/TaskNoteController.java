package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskNoteDtoPreview;
import ru.progwards.tasktracker.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskNote;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с комментариями
 *
 * @author Konstantin Kishkin
 */
@RestController
@RequestMapping("/rest")
public class TaskNoteController {
    @Autowired
    private GetService<Long, TaskNote> getService;
    @Autowired
    private RemoveService<TaskNote> removeService;
    @Autowired
    private CreateService<TaskNote> createService;
    @Autowired
    private RefreshService<TaskNote> refreshService;
    @Autowired
    private Converter<TaskNote, TaskNoteDtoFull> dtoPreviewConverter;
    @Autowired
    private Converter<TaskNote, TaskNoteDtoPreview> dtoFullConverter;
    @Autowired
    private GetListByTaskService<Long, TaskNote> listByTaskService;

    /**
     * Метод получения коллекции комментариев по идентификатору задачи
     *
     * @param id идентификатор задачи
     * @return коллекция комментариев
     */
    @GetMapping("/task/{id}/tasknotes")
    public ResponseEntity<Collection<TaskNoteDtoFull>> getListTaskNotes(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Collection<TaskNoteDtoFull> taskNotes = listByTaskService.getListByTaskId(id).stream()
                .map(tasknote -> dtoPreviewConverter.toDto(tasknote))
                .collect(Collectors.toList());

        if (taskNotes.isEmpty())
            throw new NotFoundException("Комментариев пока нет!");

        return new ResponseEntity<>(taskNotes, HttpStatus.OK);
    }

    /**
     * Метод создания комментария
     *
     * @param taskNoteDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный комментарий
     */
    @PostMapping("/tasknote/create")
    public ResponseEntity<TaskNoteDtoPreview> createTaskNote(@RequestBody TaskNoteDtoPreview taskNoteDtoFull) {
        if (taskNoteDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        TaskNote taskNote = dtoFullConverter.toModel(taskNoteDtoFull);
        createService.create(taskNote);
        TaskNoteDtoPreview createdTaskNote = dtoFullConverter.toDto(taskNote);

        return new ResponseEntity<>(createdTaskNote, HttpStatus.OK);
    }

    /**
     * Метод обновления комментария
     *
     * @param id идентификатор комментария
     * @param taskNoteDtoPreview обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленный комментарий
     */
    @PutMapping("/tasknote/{id}/update")
    public ResponseEntity<TaskNoteDtoFull> updateTaskNote(@PathVariable Long id, @RequestBody TaskNoteDtoFull taskNoteDtoPreview) {
        if (taskNoteDtoPreview == null)
            throw new BadRequestException("Пустой объект!");

        if (!id.equals(taskNoteDtoPreview.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        TaskNote taskNote = dtoPreviewConverter.toModel(taskNoteDtoPreview);
        refreshService.refresh(taskNote);
        TaskNoteDtoFull updatedTaskNote = dtoPreviewConverter.toDto(taskNote);

        return new ResponseEntity<>(updatedTaskNote, HttpStatus.OK);
    }

    /**
     * Метод удаления комменртраия
     *
     * @param id идентификатор комментария
     * @return возвращает статус ответа
     */
    @DeleteMapping("/tasknote/{id}/delete")
    public ResponseEntity<Task> deleteTaskNote(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        TaskNote taskNote = getService.get(id);
        if (taskNote != null)
            removeService.remove(taskNote);
        else
            throw new NotFoundException("Задача с id: " + id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
