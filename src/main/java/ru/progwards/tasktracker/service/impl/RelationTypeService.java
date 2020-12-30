package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.repository.RelatedTaskRepository;
import ru.progwards.tasktracker.repository.RelationTypeRepository;
import ru.progwards.tasktracker.service.*;

import java.util.List;


/**
 * Бизнес-логика типа отношения связанных задач RelationType
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class RelationTypeService implements GetService<Long, RelationType>, CreateService<RelationType>,
        RemoveService<RelationType>, RefreshService<RelationType>, GetListService<RelationType> {

    private final RelationTypeRepository relationTypeRepository;
    private final RelatedTaskRepository relatedTaskRepository;

    /**
     * Метод получения типа отношения связанных задач
     *
     * @param id идентификатор типа отношения
     * @return полученный тип отношения
     */
    @Override
    public RelationType get(Long id) {
        return relationTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RelationType id=" + id + " not found"));
    }

    /**
     * Метод получения коллекции типов отношений связанных задач
     *
     * @return коллекция типов отношений
     */
    @Override
    public List<RelationType> getList() {
        return relationTypeRepository.findAll();
    }

    /**
     * Метод создания типа отношения связанных задач
     *
     * @param model создаваемый тип отношения
     */
    @Transactional
    @Override
    public void create(RelationType model) {
        //TODO - make check exists and set counter type
        relationTypeRepository.save(model);
    }

    /**
     * Метод обновления типа отношения связанных задач
     *
     * @param model обновляемый тип отношения
     */
    @Transactional
    @Override
    public void refresh(RelationType model) {
        relationTypeRepository.save(model);
    }

    /**
     * Метод обновления типа отношения связанных задач
     *
     * @param model удаляемый тип отношения
     */
    @Transactional
    @Override
    public void remove(RelationType model) {
        if (relatedTaskRepository.existsRelatedTaskByRelationType(model))
            throw new OperationIsNotPossibleException(
                    "Удаление RelationType id=" + model.getId() + " невозможно, данный RelationType используется!"
            );

        relationTypeRepository.delete(model);
    }
}
