package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.service.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с типами отношений (RelationType) связанных задач (RelatedTask)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/relationtype")
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@Validated
public class RelationTypeController {

    private final GetService<Long, RelationType> relationTypeGetService;
    private final CreateService<RelationType> relationTypeCreateService;
    private final RemoveService<RelationType> relationTypeRemoveService;
    private final RefreshService<RelationType> relationTypeRefreshService;
    private final GetListService<RelationType> relationTypeGetListService;
    private final Converter<RelationType, RelationTypeDtoFull> converter;

    /**
     * Метод получения типа отношения (RelationType) связанных задач (RelatedTask)
     *
     * @param id идентификатор типа отношения
     * @return полученный по идентификатору RelationTypeDtoFull
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> get(@PathVariable @Min(1) Long id) {

        RelationTypeDtoFull typeDto = converter.toDto(relationTypeGetService.get(id));

        return new ResponseEntity<>(typeDto, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции типов отношений (RelationType) связанных задач (RelatedTask)
     *
     * @return лист RelationTypeDtoFull
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationTypeDtoFull>> getList() {
        List<RelationTypeDtoFull> list = relationTypeGetListService.getList().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список RelationTypeDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод создания типа отношения (RelationType) связанных задач (RelatedTask)
     *
     * @param relationTypeDto создаваемый Dto тип отношения
     * @return созданный RelationTypeDtoFull
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> create(@Valid @RequestBody RelationTypeDtoFull relationTypeDto) {
        if (relationTypeDto == null)
            throw new BadRequestException("RelationTypeDtoFull == null");

        RelationType relationType = converter.toModel(relationTypeDto);
        relationTypeCreateService.create(relationType);
        RelationTypeDtoFull createdRelationType = converter.toDto(relationType);

        return new ResponseEntity<>(createdRelationType, HttpStatus.OK);
    }

    /**
     * Метод обновления типа отношения (RelationType) связанных задач (RelatedTask)
     *
     * @param id              идентификатор обновляемого типа отношения
     * @param relationTypeDto обновляемый Dto тип отношения
     * @return обновленный RelationTypeDtoFull
     */
    @PutMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationTypeDtoFull> update(@PathVariable Long id,
                                                      @RequestBody RelationTypeDtoFull relationTypeDto) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        if (!id.equals(relationTypeDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        RelationType relationType = converter.toModel(relationTypeDto);
        relationTypeRefreshService.refresh(relationType);
        RelationTypeDtoFull updatedRelationType = converter.toDto(relationType);

        return new ResponseEntity<>(updatedRelationType, HttpStatus.OK);
    }

    /**
     * Метод удаления типа отношения (RelationType) связанных задач (RelatedTask)
     *
     * @param id идентификатор удаляемого типа отношения
     * @return статус
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<RelationTypeDtoFull> delete(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        RelationType relationType = relationTypeGetService.get(id);
        relationTypeRemoveService.remove(relationType);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
