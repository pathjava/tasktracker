package ru.progwards.tasktracker.service.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Бизнес-логика типа отношения связанных задач RelationType
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class RelationTypeTemplateService implements TemplateService<RelationType> {

    CreateService<RelationType> relationTypeCreateService;

    /**
     * Метод создания RelationType по шаблону
     *
     * @param args null - метод без параметров
     */
    @Override
    public List<RelationType> createFromTemplate(Object... args) {
        if (args.length != 0)
            throw new OperationIsNotPossibleException("RelationType.createFromTemplate: No arguments expected");

        String[][] names = {
                {"ссылается на"}, // "relates to"
                {"дублирует", "дублирован"}, // "duplicates", "is duplicates by"
                {"блокирует", "блокирован"}, // "blocks", "is blocked by"
                {"клонирует", "клонирован"} // "clones", "is clones by"
        };
        List<RelationType> result = new ArrayList<>(names.length * 2);
        for (String[] name : names) {
            RelationType relation = new RelationType();
            relation.setName(name[0]);
            relationTypeCreateService.create(relation);
            result.add(relation);
            if (name.length > 1) {
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