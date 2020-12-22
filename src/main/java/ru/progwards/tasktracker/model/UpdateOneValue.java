package ru.progwards.tasktracker.model;

public class UpdateOneValue {

    private Long id;
    private Object newValue;
    private String fieldName;

    public UpdateOneValue(Long id, Object newValue, String fieldName) {
        this.id = id;
        this.newValue = newValue;
        this.fieldName = fieldName;
    }

    public Object getNewValue() {
        return newValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
