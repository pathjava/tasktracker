package ru.progwards.service.vo;

public class UpdateOneValue {

    private final Long id;
    private final Object newValue;
    private final String fieldName;

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
}
