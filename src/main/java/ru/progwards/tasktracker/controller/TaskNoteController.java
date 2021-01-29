package ru.progwards.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.dto.TaskNoteDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.service.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с комментариями
 *
 * @author Konstantin Kishkin
 */
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskNoteController {

    private final GetService<Long, TaskNote> getService;

    private final RemoveService<TaskNote> removeService;

    private final CreateService<TaskNote> createService;

    private final RefreshService<TaskNote> refreshService;

    private final Converter<TaskNote, TaskNoteDtoFull> dtoPreviewConverter;

    private final Converter<TaskNote, TaskNoteDtoPreview> dtoFullConverter;

    private final GetListService<TaskNote> taskNoteGetService;

    /**
     * Метод получения коллекции комментариев по идентификатору задачи
     *
     * @param id идентификатор задачи
     * @return коллекция комментариев
     */
    @GetMapping("/task/{id}/tasknotes")
    public ResponseEntity<List<TaskNoteDtoFull>> getListTaskNotes(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        List<TaskNoteDtoFull> taskNotes = taskNoteGetService.getList().stream()
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
    public ResponseEntity<TaskNoteDtoFull> updateTaskNote(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id, @RequestBody TaskNoteDtoFull taskNoteDtoPreview) {
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
    public ResponseEntity<Task> deleteTaskNote(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {
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
