//package ru.progwards.tasktracker.repository.deprecated.entity;
//
//import ru.progwards.tasktracker.model.User;
//
///**
// * value object - объект бизнес логики (комментарий)
// *
// * @author Konstantin Kishkin
// */
//
//public class TaskNoteEntity {
//
//    private Long id;
//    private Long task_id;
//    private User author;
//    private User updater;
//    private Long created;
//    private Long updated;
//    private String comment;
//
//    public TaskNoteEntity(){
//
//    }
//
//    public TaskNoteEntity(Long id, Long task_id, User author, User updater, Long created, Long updated, String comment) {
//        this.id = id;
//        this.task_id = task_id;
//        this.author = author;
//        this.updater = updater;
//        this.created = created;
//        this.updated = updated;
//        this.comment = comment;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getTask_id() {
//        return task_id;
//    }
//
//    public void setTask_id(Long task_id) {
//        this.task_id = task_id;
//    }
//
//    public User getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(User author) {
//        this.author = author;
//    }
//
//    public Long getCreated() {
//        return created;
//    }
//
//    public void setCreated(Long created) {
//        this.created = created;
//    }
//
//    public Long getUpdated() {
//        return updated;
//    }
//
//    public void setUpdated(Long updated) {
//        this.updated = updated;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public User getUpdater() {
//        return updater;
//    }
//
//    public void setUpdater(User updater) {
//        this.updater = updater;
//    }
//}
