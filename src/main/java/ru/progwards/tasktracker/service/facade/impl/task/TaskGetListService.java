package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TaskGetListService implements GetListService<Task> {

    private TaskEntityRepository taskRepository;
    private TaskConverter converterTask;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(TaskConverter converterTask) {
        this.converterTask = converterTask;
    }

    @Override
    public Collection<Task> getList() {
        Collection<Task> tasks = taskRepository.get().stream()
                .map(entity -> converterTask.toVo(entity))
                .collect(Collectors.toList());

        return tasks.size() == 0 ? null : tasks; //TODO - подумать, надо ли возвращать null здесь и в других сервисах
    }
}
