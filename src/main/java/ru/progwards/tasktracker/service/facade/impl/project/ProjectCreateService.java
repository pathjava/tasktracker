package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.TaskType;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Класс по созданию бизнес-модели Project
 * @author Pavel Khovaylo
 */
@Service
public class ProjectCreateService implements CreateService<Project> {
    /**
     * репозиторий с проектами
     */
    @Autowired
    private Repository<Long, ProjectEntity> repository;
    /**
     * конвертер проектов
     */
    @Autowired
    private Converter<ProjectEntity, Project> converter;
    /**
     * для создания TaskType
     */
    @Autowired
    private CreateService<TaskType> taskTypeCreateService;

    /**
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(Project model) {
        // если значение prefix пустое, то создание нового проекта невозможно
        if (filterString(model.getPrefix()) == null)
            throw new OperationIsNotPossibleException("Create not possible");


        // получаем список проектов, чтобы искать в них одинаковые prefix
        Collection<ProjectEntity> projectEntities = repository.get();

        boolean isExist = false;

        //если в списке проектов уже имеется проект с данным префиксом, то создание нового проекта невозможно
        if (projectEntities.size() != 0) {
            for (ProjectEntity projectEntity : projectEntities) {
                if (projectEntity.getPrefix().equals(model.getPrefix())) {
                    isExist = true;
                    break;
                }
            }
        }


        if (isExist)
            throw new OperationIsNotPossibleException("Create not possible");

        if (model.getId() == null)
            model.setId(new Random().nextLong());
        //создаем список TaskType проекта
        List<TaskType> taskTypeList = new ArrayList<>(List.of(
                new TaskType(null, model.getId(),
                        //TODO сделал так исключительно чтобы протестировать
//                            new WorkFlow(null, "name", false, 0L, null, null),
                        null,
                        "EPIC"),
                new TaskType(null, model.getId(),
                        //TODO сделал так исключительно чтобы протестировать
//                            new WorkFlow(null, "name", false, 0L, null, null),
                        null,
                        "TASK"),
                new TaskType(null, model.getId(),
                        //TODO сделал так исключительно чтобы протестировать
//                            new WorkFlow(null, "name", false, 0L, null, null),
                        null,
                        "BUG")
            )
        );

        //добавляем TaskType в базу данных
        taskTypeList.forEach(e -> taskTypeCreateService.create(e));

        model.setTaskTypes(taskTypeList);
        // при создании LastTaskCode всегда = 0
        model.setLastTaskCode(0L);

        repository.create(converter.toEntity(model));
    }

    /**
     * метод фильтрует строку (prefix проекта) под нужный шаблон
     * @param str входящий prefix
     * @return отфильтрованный prefix, либо null, если нет возможности выдать корректный результат
     */
    private static String filterString(String str) {
        String digits = "0123456789";

        boolean isCorrect = false;

        // если первые символы строки содержат одну из цифр, то перебирать строку, пока не дойдем до буквы
        for (int i = 0; i < str.length(); i++) {
            if (!digits.contains(str.substring(i,i+1))) {
                str = str.substring(i);
                isCorrect = true;
            }
        }

        // если в строке есть символ алфавита, то убрать все имеющиеся пробелы
        if (isCorrect)
            str = str.replace(" ", "");

        // если длина строки > 1, то возвращаем результат
        if (str.length() > 1)
            return str.toUpperCase();

        return null;
    }
}