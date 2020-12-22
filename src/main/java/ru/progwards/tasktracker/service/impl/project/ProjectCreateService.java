package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;

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
        if ("".equals(filterString(model.getPrefix())))
            throw new OperationIsNotPossibleException("Index is incorrect");


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
                new TaskType(null, model,
                        //TODO сделал так исключительно чтобы протестировать
//                            new WorkFlow(null, "name", false, 0L, null, null),
                        null,
                        "EPIC", null),
                new TaskType(null, model,
                        //TODO сделал так исключительно чтобы протестировать
//                            new WorkFlow(null, "name", false, 0L, null, null),
                        null,
                        "TASK", null),
                new TaskType(null, model,
                        //TODO сделал так исключительно чтобы протестировать
//                            new WorkFlow(null, "name", false, 0L, null, null),
                        null,
                        "BUG", null)
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
     * @return отфильтрованный prefix, либо "", если нет возможности выдать корректный результат
     */
    private static String filterString(String str) {
        String result = "";

        char[] chars = str.toCharArray();

        // если символ строки не является буквой алфавита, то перебирать строку, пока не дойдем до буквы
        for (int i = 0; i < str.length(); i++) {
            char ch = chars[i];
            if (Character.isAlphabetic(ch)) {
                result = getCorrectPrefix(str.substring(i));
                break;
            }
        }

        return result.length() == 1 ? "" : result;
    }

    /**
     * метод фильтрует переданную строку, убирая из неё все символы кроме букв и цифр
     * @param str строка, для которой будет производиться фильтрация
     * @return корретная строка, состоящая из букв и цифр
     */
    private static String getCorrectPrefix(String str) {
        StringBuilder result = new StringBuilder();

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isLetterOrDigit(ch))
                result.append(ch);
        }

        return result.toString().toUpperCase();
    }

    public static void main(String[] args) {
        String str = ":023;']h;  ::&$%;;";
        System.out.println(filterString(str));

    }
}