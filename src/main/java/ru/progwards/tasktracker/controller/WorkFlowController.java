package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowDto;
import ru.progwards.tasktracker.controller.exceptions.NullObjectException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkFlow;

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
    Converter<WorkFlow, WorkFlowDto> dtoConverter;

    /**
     * Получить список всех workflow
     * GET /rest/workflow/list
     *
     * @return список вложений
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<WorkFlowDto>> getList() {
        // получили список бизнес-объектов
        Collection<WorkFlow> list = getListService.getList();
        List<WorkFlowDto> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (WorkFlow entity:list) {
            WorkFlowDto dto = dtoConverter.toDto(entity);
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
    public ResponseEntity<WorkFlowDto> get(@PathVariable("id") Long id) {
        if (id == null)
            throw new NullObjectException("Id is not set");

        WorkFlow vo = getService.get(id);
        WorkFlowDto entity = dtoConverter.toDto(vo);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * Создаём новый workflow
     * POST /rest/workflow/create
     *
     * @param entity бъект для создания
     * @return объект после бизнес-логики
     */
    @PostMapping("/create")
    public ResponseEntity<WorkFlowDto> create(@RequestBody WorkFlowDto entity) {
        if (entity == null)
            throw new NullObjectException("WorkFlow is null");

        WorkFlow vo = dtoConverter.toModel(entity);
        createService.create(vo);
        WorkFlowDto result = dtoConverter.toDto(vo);

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
    public ResponseEntity<WorkFlowDto> update(@PathVariable("id") Long id,
                                              @RequestBody WorkFlowDto entity) {
        if (id == null)
            throw new NullObjectException("Id is not set");
        if (entity == null)
            throw new NullObjectException("WorkFlow Id is null");

        entity.setId(id);
        WorkFlow vo = dtoConverter.toModel(entity);
        refreshService.refresh(vo);
        WorkFlowDto result = dtoConverter.toDto(vo);

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
    public void deleteList(@PathVariable("id") Long id) {
        if (id == null)
            throw new NullObjectException("Workflow Id is not set");

        WorkFlow vo = getService.get(id);
        removeService.remove(vo);
    }

}
