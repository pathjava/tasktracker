package ru.progwards.tasktracker.service.facade;

/**
 * Копировать бизнес-объект в хранилище
 *
 * @param <M> тип бизнес-объекта
 */
public interface CopyService<M> {

    /**
     * Создать копию бизнес-объекта
     * Если задан шаблон, то свойства в скопированном объекте заменятся на не null свойства объекта
     *
     * @param modelFrom бизнес объект, который необходимо скопировать
     * @param template  шаблон для замены свойств во вновь созданном объекте
     * @return копия объекта {@code model}
     */
    M copy(M modelFrom, M template);

}
