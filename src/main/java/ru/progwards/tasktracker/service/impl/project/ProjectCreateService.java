package ru.progwards.tasktracker.service.impl.project;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
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
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectCreateService implements CreateService<Project> {
    /**
     * репозиторий с проектами
     */
    ProjectRepository repository;
    /**
     * конвертер Project <-> ProjectDtoFull
     */
    Converter<Project, ProjectDtoPreview> projectDtoPreviewConverter;
    /**
     * сервис для сохранения в базу объектов TaskType
     */
    CreateService<TaskType> taskTypeCreateService;
    /**
     * конвертер TaskType <-> TaskTypeDtoFull
     */
    Converter<TaskType, TaskTypeDtoFull> taskTypeDtoFullConverter;

    /**
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(Project model) {
        if (model == null)
            throw new OperationIsNotPossibleException("Create project is not possible");

        String prefix = filterString(model.getPrefix());

        // если значение prefix пустое, то создание нового проекта невозможно
        if ("".equals(prefix))
            throw new OperationIsNotPossibleException("Index is incorrect");

        model.setPrefix(prefix);

        // получаем список проектов, чтобы искать в них одинаковые prefix
        List<Project> projects = repository.findAll();

        //изначально повторного префикса не существует
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

        // при создании LastTaskCode всегда = 0
        model.setLastTaskCode(0L);

        repository.save(model);

        //создаем список TaskType для проекта
        List<TaskTypeDtoFull> taskTypeDtoFullList = new ArrayList<>(List.of(

                new TaskTypeDtoFull(null,
                        projectDtoPreviewConverter.toDto(model),
                        new WorkFlowDtoPreview(),
                        "TASK"),
                new TaskTypeDtoFull(null,
                        projectDtoPreviewConverter.toDto(model),
                        new WorkFlowDtoPreview(),
                        "BUG"),
                new TaskTypeDtoFull(null,
                        projectDtoPreviewConverter.toDto(model),
                        new WorkFlowDtoPreview(),
                        "EPIC")
        ));

        //создаем список TaskType для добавления его к объекту model
        List<TaskType> taskTypeList = new ArrayList<>();

        //добавляем TaskType в базу данных
        taskTypeDtoFullList.forEach(e -> {
            TaskType taskType = taskTypeDtoFullConverter.toModel(e);
            taskTypeCreateService.create(taskType);
            taskTypeList.add(taskType);
        });

        model.setTaskTypes(taskTypeList);
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