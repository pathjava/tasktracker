package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.repository.RelatedTaskRepository;
import ru.progwards.tasktracker.repository.RelationTypeRepository;
import ru.progwards.tasktracker.service.*;

import java.util.List;
import java.util.Map;


/**
 * Бизнес-логика типа отношения связанных задач RelationType
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class RelationTypeService implements GetService<Long, RelationType>, CreateService<RelationType>,
        RemoveService<RelationType>, RefreshService<RelationType>,
        GetListService<RelationType>, TemplateService<RelationType> {

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
     * Если model.getCounterRelation() != null, значит создается двусторонний тип отношения,
     * поэтому получаем встречный тип, проверяем чтобы он не был уже занят другим RelationType
     * и добавляем во встречный тип (обновляем его) создаваемый тип RelationType.
     *
     * @param model создаваемый тип отношения
     */
    @Transactional
    @Override
    public void create(RelationType model) {
        if (model.getCounterRelation() != null) {
            RelationType counterRelation = get(model.getCounterRelation().getId());
            if (counterRelation.getCounterRelation() == null) {
                relationTypeRepository.save(model);
                counterRelation.setCounterRelation(model);
                relationTypeRepository.save(counterRelation);
            } else
                throw new BadRequestException("RelationType id=" + counterRelation.getId() + " уже используется");
        } else
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

    /**
     * Метод создания RelationType по шаблону
     *
     * @param args null - метод без параметров
     */
    @Transactional
    @Override
    public void createFromTemplate(Object... args) {
        if (args.length != 1)
            throw new OperationIsNotPossibleException("RelationType.createFromTemplate: 1 arguments expected");
        if (!(args[0] instanceof Map))
            throw new OperationIsNotPossibleException(
                    "RelationType.createFromTemplate: argument 0 must be Map<String, String>"
            );

        /* passing the list of relation types: */
        /* "relates to", "", "duplicates", "is duplicates by", "blocks", "is blocked by", "clones", "is clones by" */
        @SuppressWarnings("unchecked")
        Map<String, String> relationNames = (Map<String, String>) args[0];

        for (Map.Entry<String, String> entry : relationNames.entrySet()) {
            RelationType relationType = new RelationType();
            relationType.setName(entry.getKey());
            if (!entry.getValue().isBlank()) {
                RelationType counterRelationType = new RelationType();
                counterRelationType.setName(entry.getValue());
                counterRelationType.setCounterRelation(relationType);
                relationType.setCounterRelation(counterRelationType);

                relationTypeRepository.save(counterRelationType);
            }
            relationTypeRepository.save(relationType);
        }
    }
}
