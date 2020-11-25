package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с логами (Журналом работ) задачи
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping("/rest")
public class WorkLogController {

    @Autowired
    private GetService<Long, WorkLog> getService;
    @Autowired
    private CreateService<WorkLog> createService;
    @Autowired
    private RemoveService<WorkLog> removeService;
    @Autowired
    private RefreshService<WorkLog> refreshService;
    @Autowired
    private GetListByTaskService<Long, WorkLog> listByTaskService;
    @Autowired
    private Converter<WorkLog, WorkLogDtoFull> converter;

    /**
     * Метод получения списка логов по идентификатору задачи
     *
     * @param id идентификатор задачи, для которой необходимо вывести логи
     * @return коллекция логов задачи
     */
    @GetMapping("/task/{id}/worklogs")
    public ResponseEntity<Collection<WorkLogDtoFull>> getListWorkLogs(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Collection<WorkLogDtoFull> logs = listByTaskService.getListByTaskId(id).stream()
                .map(workLog -> converter.toDto(workLog))
                .collect(Collectors.toList());

        if (logs.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список логов пустой!");

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    /**
     * Метод создания лога
     *
     * @param workLogDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный лог
     */
    @PostMapping("/worklog/create")
    public ResponseEntity<WorkLogDtoFull> createWorkLog(@RequestBody WorkLogDtoFull workLogDtoFull) {
        if (workLogDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        WorkLog workLog = converter.toModel(workLogDtoFull);
        createService.create(workLog);
        WorkLogDtoFull createWorkLog = converter.toDto(workLog);

        //TODO - перед добавлением проверять, есть ли уже в БД такой лог, но id генерируется в entity - подумать

        return new ResponseEntity<>(createWorkLog, HttpStatus.OK);
    }

    /**
     * Метод обновления лога
     *
     * @param workLogDtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный лог
     */
    @PutMapping("/worklog/{id}/update")
    public ResponseEntity<WorkLogDtoFull> updateWorkLog(@PathVariable Long id, @RequestBody WorkLogDtoFull workLogDtoFull) {
        if (workLogDtoFull == null)
            throw new BadRequestException("Пустой объект!");

        if (!id.equals(workLogDtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        WorkLog workLog = converter.toModel(workLogDtoFull);
        refreshService.refresh(workLog);
        WorkLogDtoFull updateWorkLog = converter.toDto(workLog);

        return new ResponseEntity<>(updateWorkLog, HttpStatus.OK);
    }

    /**
     * Метод удаления лога
     *
     * @param id идентификатор удаляемого лога
     * @return статус ответа
     */
    @DeleteMapping("/worklog/{id}/delete")
    public ResponseEntity<WorkLogDtoFull> deleteWorkLog(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        WorkLog workLog = getService.get(id);
        if (workLog != null)
            removeService.remove(workLog);
        else
            throw new NotFoundException("Лог с id: " + id + " не найден!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
