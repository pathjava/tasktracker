package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkLogDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
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
    private GetListByTaskService<Long, WorkLog> listByTaskService;
    private Converter<WorkLog, WorkLogDto> converter;

    @Autowired
    public void setGetService(
            GetService<Long, WorkLog> getService,
            CreateService<WorkLog> createService,
            RemoveService<WorkLog> removeService,
            GetListByTaskService<Long, WorkLog> listByTaskService,
            Converter<WorkLog, WorkLogDto> converter
    ) {
        this.getService = getService;
        this.createService = createService;
        this.removeService = removeService;
        this.listByTaskService = listByTaskService;
        this.converter = converter;
    }

    /**
     * @param task_id идентификатор задачи, для которой необходимо вывести логи
     * @return коллекция логов задачи
     */
    @GetMapping("/rest/log/{task_id}/listlog")
    public ResponseEntity<Collection<WorkLogDto>> getAllWorkLog(@PathVariable Long task_id) {
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
     * @param workLogDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный лог
     */
    @PostMapping("/rest/log/create")
    public ResponseEntity<WorkLogDto> addWorkLog(@RequestBody WorkLogDto workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("Пустой объект!");

        WorkLog workLog = converter.toModel(workLogDto);
        createService.create(workLog);
        WorkLogDto createWorkLog = converter.toDto(workLog);

        //TODO - перед добавлением проверять, есть ли уже в БД такой лог, но id генерируется в entity - подумать

        return new ResponseEntity<>(createWorkLog, HttpStatus.CREATED);
    }

    /**
     * @param log_id идентификатор удаляемого лога
     * @return статус ответа
     */
    @DeleteMapping("/rest/log/{log_id}/delete")
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
