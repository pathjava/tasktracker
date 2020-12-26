package ru.progwards.tasktracker.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.CreateService;

import java.util.ArrayList;
import java.util.List;

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
    private ProjectRepository repository;
    /**
     * для создания TaskType
     */
    @Autowired
    private CreateService<TaskType> taskTypeCreateService;

    /**
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Transactional
    @Override
    public void create(Project model) {
        if (model == null)
            throw new OperationIsNotPossibleException("Create project is not possible");

        // если значение prefix пустое, то создание нового проекта невозможно
        if ("".equals(filterString(model.getPrefix())))
            throw new OperationIsNotPossibleException("Index is incorrect");

        // получаем список проектов, чтобы искать в них одинаковые prefix
        List<Project> projects = repository.findAll();

        boolean isExist = false;

        //если в списке проектов уже имеется проект с данным префиксом, то создание нового проекта невозможно
        if (projects.size() != 0) {
            for (Project project : projects) {
                if (project.getPrefix().equals(model.getPrefix())) {
                    isExist = true;
                    break;
                }
            }
        }

        if (isExist)
            throw new OperationIsNotPossibleException("Create project is not possible");

        TaskType taskType1 = new TaskType();
        taskType1.setName("EPIC");
        TaskType taskType2 = new TaskType();
        taskType2.setName("TASK");
        TaskType taskType3 = new TaskType();
        taskType3.setName("BUG");

        //создаем список TaskType проекта
        List<TaskType> taskTypeList = new ArrayList<>(List.of(
                /* TODO пока что не понимаю как записать в TaskType свойство "project"
                *   потому как изначально у model id не сгенерирован, а записывать этот model без id мы не можем */

//                new TaskType(null, model,
//                        //TODO сделал так исключительно чтобы протестировать
////                            new WorkFlow(null, "name", false, 0L, null, null),
//                        null,
//                        "EPIC", null),
//                new TaskType(null, model,
//                        //TODO сделал так исключительно чтобы протестировать
////                            new WorkFlow(null, "name", false, 0L, null, null),
//                        null,
//                        "TASK", null),
//                new TaskType(null, model,
//                        //TODO сделал так исключительно чтобы протестировать
////                            new WorkFlow(null, "name", false, 0L, null, null),
//                        null,
//                        "BUG", null)
            )
        );

        //добавляем TaskType в базу данных
        taskTypeList.forEach(e -> taskTypeCreateService.create(e));

        model.setTaskTypes(taskTypeList);
        // при создании LastTaskCode всегда = 0
        model.setLastTaskCode(0L);

        repository.save(model);
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
}