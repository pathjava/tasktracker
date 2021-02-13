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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;


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
                .orElseThrow(() -> new NotFoundException(format("RelationType id=%s not found", id)));
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
                throw new BadRequestException(format("RelationType id=%s already exists", counterRelation.getId()));
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
                    format("Removing RelationType id=%s is not possible, this RelationType is in use!", model.getId())
            );

        relationTypeRepository.delete(model);
    }


    CreateService<RelationType> relationTypeCreateService;

/**
 * Метод создания RelationType по шаблону
 *
 * @param args null - метод без параметров
*/
 @Transactional
 @Override
 public List<RelationType> createFromTemplate(Object... args) {
 if (args.length != 0)
 throw new OperationIsNotPossibleException("RelationType.createFromTemplate: No arguments expected");

 String[][] names = {
 {"ссылается на"}, // "relates to"
 {"дублирует","дублирован"}, // "duplicates", "is duplicates by"
 {"блокирует","блокирован"}, // "blocks", "is blocked by"
 {"клонирует","клонирован"} // "clones", "is clones by"
 };
 List<RelationType> result = new ArrayList<>(names.length);
 for (String[] name : names) {
 RelationType relation = new RelationType();
 relation.setName(name[0]);
 relationTypeCreateService.create(relation);
 result.add(relation);
 if(name.length>1) {
 RelationType counterRelation = new RelationType();
 counterRelation.setName(name[1]);
 counterRelation.setCounterRelation(relation);
 relationTypeCreateService.create(counterRelation);
 result.add(counterRelation);
 }
 }
 return result;
 }
}
