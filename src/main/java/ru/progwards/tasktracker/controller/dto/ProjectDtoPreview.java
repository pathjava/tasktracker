package ru.progwards.tasktracker.controller.dto;

/**
 * DtoPreview для проекта
 * @author Pavel Khovaylo
 */
public class ProjectDtoPreview {
    /**
     * идентификатор проекта
     */
    private Long id;
    /**
     * имя проекта
     */
    private String name;
//    /**
//     * уникальная аббревиатура, созданная на основании имени проекта
//     */
//    private String prefix;
//    /**
//     * владелец (создатель) проекта
//     */
//    //TODO userDtoPreview
//    private UserDtoFull owner;

    public ProjectDtoPreview(Long id, String name) {
        this.id = id;
        this.name = name;
//        this.prefix = prefix;
//        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}