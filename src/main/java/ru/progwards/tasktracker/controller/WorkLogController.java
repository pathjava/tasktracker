package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkLogDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * контроллер для работы с логами задачи
 *
 * @author Oleg Kiselev
 */
@RestController
public class WorkLogController {

    private GetService<Long, WorkLog> getService;
    private CreateService<WorkLog> createService;
    private RemoveService<WorkLog> removeService;
    private RefreshService<WorkLog> refreshService;
    private GetListByTaskService<Long, WorkLog> listByTaskService;
    private Converter<WorkLog, WorkLogDto> converter;

    @Autowired
    public void setGetService(
            GetService<Long, WorkLog> getService,
            CreateService<WorkLog> createService,
            RemoveService<WorkLog> removeService,
            RefreshService<WorkLog> refreshService,
            GetListByTaskService<Long, WorkLog> listByTaskService,
            Converter<WorkLog, WorkLogDto> converter
    ) {
        this.getService = getService;
        this.createService = createService;
        this.removeService = removeService;
        this.refreshService = refreshService;
        this.listByTaskService = listByTaskService;
        this.converter = converter;
    }

    /**
     * метод получения списка логов по идентификатору задачи
     *
     * @param task_id идентификатор задачи, для которой необходимо вывести логи
     * @return коллекция логов задачи
     */
    @GetMapping("/rest/task/{task_id}/worklogs")
    public ResponseEntity<Collection<WorkLogDto>> getListWorkLogs(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        Collection<WorkLogDto> logs = listByTaskService.getListByTaskId(task_id).stream()
                .map(workLog -> converter.toDto(workLog))
                .collect(Collectors.toList());

        if (logs.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список логов пустой!");

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    /**
     * метод создания лога
     *
     * @param workLogDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный лог
     */
    @PostMapping("/rest/worklog/create")
    public ResponseEntity<WorkLogDto> createWorkLog(@RequestBody WorkLogDto workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("Пустой объект!");

        WorkLog workLog = converter.toModel(workLogDto);
        createService.create(workLog);
        WorkLogDto createWorkLog = converter.toDto(workLog);

        //TODO - перед добавлением проверять, есть ли уже в БД такой лог, но id генерируется в entity - подумать

        return new ResponseEntity<>(createWorkLog, HttpStatus.CREATED);
    }

    /**
     * метод обновления лога
     *
     * @param workLogDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный лог
     */
    @PutMapping("/rest/worklog/{log_id}/update")
    public ResponseEntity<WorkLogDto> updateWorkLog(@PathVariable Long log_id, @RequestBody WorkLogDto workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("Пустой объект!");

        if (!log_id.equals(workLogDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        WorkLog workLog = converter.toModel(workLogDto);
        refreshService.refresh(workLog);
        WorkLogDto updateWorkLog = converter.toDto(workLog);

        return new ResponseEntity<>(updateWorkLog, HttpStatus.CREATED);
    }

    /**
     * метод удаления лога
     *
     * @param log_id идентификатор удаляемого лога
     * @return статус ответа
     */
    @DeleteMapping("/rest/worklog/{log_id}/delete")
    public ResponseEntity<WorkLogDto> deleteWorkLog(@PathVariable Long log_id) {
        if (log_id == null)
            throw new BadRequestException("Id: " + log_id + " не задан или задан неверно!");

        WorkLog workLog = getService.get(log_id);
        if (workLog != null)
            removeService.remove(workLog);
        else
            throw new NotFoundException("Лог с id: " + log_id + " не найден!");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
