package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.RelationType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с типами отношений связанных задач
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping("/rest/relationtype")
public class RelationTypeController {

    @Autowired
    private GetService<Long, RelationType> getService;
    @Autowired
    private CreateService<RelationType> createService;
    @Autowired
    private RemoveService<RelationType> removeService;
    @Autowired
    private RefreshService<RelationType> refreshService;
    @Autowired
    private GetListService<RelationType> getListService;
    @Autowired
    private Converter<RelationType, RelationTypeDtoFull> converter;

    /**
     * Метод получения типа отношения связанных задач
     *
     * @param id идентификатор типа отношения
     * @return полученный по идентификатору Dto тип отношения
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> getRelationType(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        RelationTypeDtoFull typeDto = converter.toDto(getService.get(id));

        if (typeDto == null) //TODO - пустой тип или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Тип отношения с id: " + id + " не найден!");

        return new ResponseEntity<>(typeDto, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции типов отношений связанных задач
     *
     * @return коллекция Dto типов отношений
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RelationTypeDtoFull>> getListRelationType() {
        Collection<RelationTypeDtoFull> collection = getListService.getList().stream()
                .map(relationType -> converter.toDto(relationType))
                .collect(Collectors.toUnmodifiableList());

        if (collection.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список отношений пустой!");

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    /**
     * Метод создания типа отношения связанных задач
     *
     * @param typeDto создаваемый Dto тип отношения
     * @return созданный тип отношения
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> createRelationType(@RequestBody RelationTypeDtoFull typeDto) {
        if (typeDto == null)
            throw new BadRequestException("Пустой объект!");

        RelationType relationType = converter.toModel(typeDto);
        createService.create(relationType);
        RelationTypeDtoFull createdRelationType = converter.toDto(relationType);

        return new ResponseEntity<>(createdRelationType, HttpStatus.OK);
    }

    /**
     * Метод обновления типа отношения связанных задач
     *
     * @param id      идентификатор обновляемого типа отношения
     * @param typeDto обновляемый Dto тип отношения
     * @return обновленный тип отношения
     */
    @PutMapping(value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> updateRelationType(@PathVariable Long id,
                                                                  @RequestBody RelationTypeDtoFull typeDto) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        if (!id.equals(typeDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        RelationType relationType = converter.toModel(typeDto);
        refreshService.refresh(relationType);
        RelationTypeDtoFull updatedRelationType = converter.toDto(relationType);

        return new ResponseEntity<>(updatedRelationType, HttpStatus.OK);
    }

    /**
     * Метод удаления типа отношения связанных задач
     *
     * @param id идентификатор удаляемого типа отношения
     * @return статус
     */
    @DeleteMapping(value = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> deleteRelationType(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        RelationType relationType = getService.get(id);
        if (relationType != null)
            removeService.remove(relationType);
        else
            throw new NotFoundException("Тип отношения с id: " + id + " не найден!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
