package ru.progwards.tasktracker.service.impl.project;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.CreateService;

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
//    /**
//     * конвертер Project <-> ProjectDtoFull
//     */
//    Converter<Project, ProjectDtoPreview> projectDtoPreviewConverter;
//    /**
//     * сервис для сохранения в базу объектов TaskType
//     */
//    CreateService<TaskType> taskTypeCreateService;
//    /**
//     * конвертер TaskType <-> TaskTypeDtoFull
//     */
//    Converter<TaskType, TaskTypeDtoFull> taskTypeDtoFullConverter;

    /**
     * метот добавляет проект в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(Project model) {
        //переводим значение префикса в верхний регистр
        model.setPrefix(model.getPrefix().toUpperCase());

        repository.save(model);

        //TODO подумать и в дальнейшем реализовать создание TaskType для проекта
//        //создаем список TaskType для проекта
//        List<TaskTypeDtoFull> taskTypeDtoFullList = new ArrayList<>(List.of(
//
//                new TaskTypeDtoFull(null,
//                        projectDtoPreviewConverter.toDto(model),
//                        new WorkFlowDtoPreview(),
//                        "TASK"),
//                new TaskTypeDtoFull(null,
//                        projectDtoPreviewConverter.toDto(model),
//                        new WorkFlowDtoPreview(),
//                        "BUG"),
//                new TaskTypeDtoFull(null,
//                        projectDtoPreviewConverter.toDto(model),
//                        new WorkFlowDtoPreview(),
//                        "EPIC")
//        ));
//
//        //создаем список TaskType для добавления его к объекту model
//        List<TaskType> taskTypeList = new ArrayList<>();
//
//        //добавляем TaskType в базу данных
//        taskTypeDtoFullList.forEach(e -> {
//            TaskType taskType = taskTypeDtoFullConverter.toModel(e);
//            taskTypeCreateService.create(taskType);
//            taskTypeList.add(taskType);
//        });
//
//        model.setTaskTypes(taskTypeList);
    }

    //проверку на корректность значения префикса реализовал через аннотацию CorrectAndUniquePrefix

//    /**
//     * метод фильтрует строку (prefix проекта) под нужный шаблон
//     * @param str входящий prefix
//     * @return отфильтрованный prefix, либо "", если нет возможности выдать корректный результат
//     */
//    private static String filterString(String str) {
//        String result = "";
//
//        char[] chars = str.toCharArray();
//
//        // если первый символ строки не является буквой алфавита, то перебирать строку, пока не дойдем до буквы
//        for (int i = 0; i < str.length(); i++) {
//            char ch = chars[i];
//            if (Character.isAlphabetic(ch)) {
//                result = getCorrectPrefix(str.substring(i));
//                break;
//            }
//        }
//
//        return result.length() == 1 ? "" : result;
//    }
//
//    /**
//     * метод фильтрует переданную строку, убирая из неё все символы кроме букв и цифр
//     * @param str строка, для которой будет производиться фильтрация
//     * @return корретная строка, состоящая из букв и цифр
//     */
//    private static String getCorrectPrefix(String str) {
//        StringBuilder result = new StringBuilder();
//
//        char[] chars = str.toCharArray();
//
//        for (int i = 0; i < chars.length; i++) {
//            char ch = chars[i];
//            if (Character.isLetterOrDigit(ch))
//                result.append(ch);
//        }
//
//        return result.toString().toUpperCase();
//    }
}