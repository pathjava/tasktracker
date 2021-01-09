package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.WorkFlowDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обработка запросов API по работе с workflow
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/workflow")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowController {

    @Autowired
    GetService<Long, WorkFlow> getService;
    @Autowired
    CreateService<WorkFlow> createService;
    @Autowired
    RemoveService<WorkFlow> removeService;
    @Autowired
    RefreshService<WorkFlow> refreshService;
    @Autowired
    GetListService<WorkFlow> getListService;
    @Autowired
    Converter<WorkFlow, WorkFlowDtoFull> dtoConverter;


    /**
     * Получить список всех workflow
     * GET /rest/workflow/list
     *
     * @return список вложений
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<WorkFlowDtoFull>> getList() {
        // получили список бизнес-объектов
        Collection<WorkFlow> list = getListService.getList();
        List<WorkFlowDtoFull> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (WorkFlow entity:list) {
            WorkFlowDtoFull dto = dtoConverter.toDto(entity);
            resultList.add(dto);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    /**
     * Получить конкретный workflow
     * GET /rest/workflow/{id}
     *
     * @param id идентификатор объекта
     * @return объект dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowDtoFull> get(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Id is not set");

        WorkFlow vo = getService.get(id);
        WorkFlowDtoFull entity = dtoConverter.toDto(vo);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    /**
     * Создаём новый workflow
     * POST /rest/workflow/create
     *
     * @param entity объект для создания
     * @return объект после бизнес-логики
     */
    @PostMapping("/create")
    public ResponseEntity<WorkFlowDtoFull> create(@RequestBody WorkFlowDtoFull entity) {
        if (entity == null)
            throw new BadRequestException("WorkFlow is null");

        WorkFlow vo = dtoConverter.toModel(entity);
        createService.create(vo);
        WorkFlowDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Обновляем workflow
     * POST /rest/workflow/{id}/update
     *
     * @param id идентификатор изменяемого объекта
     * @param entity измененный объект
     */
    @PostMapping("/{id}/update")
    public ResponseEntity<WorkFlowDtoFull> update(@PathVariable("id") Long id,
                                                  @RequestBody WorkFlowDtoFull entity) {
        if (id == null)
            throw new BadRequestException("Id is not set");
        if (entity == null)
            throw new BadRequestException("WorkFlow Id is null");

        entity.setId(id);
        WorkFlow vo = dtoConverter.toModel(entity);
        refreshService.refresh(vo);
        WorkFlowDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Удалить существующий workflow
     * POST /rest/workflow/{id}/delete
     *
     * @param id идентификатор удаляемого объекта
     */
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Workflow Id is not set");

        WorkFlow vo = getService.get(id);
        removeService.remove(vo);
    }

}
