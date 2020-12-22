package ru.progwards.tasktracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByParentId;
import ru.progwards.tasktracker.repository.deprecated.entity.WorkFlowStatusEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.WorkFlowStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Бизнес-логика работы со статусами бизнес процесса
 *
 * @author Gregory Lobkov
 */
@Service
public class WorkFlowStatusService implements CreateService<WorkFlowStatus>, RemoveService<WorkFlowStatus>, GetService<Long, WorkFlowStatus>, GetListService<WorkFlowStatus>, RefreshService<WorkFlowStatus>, GetListByParentService<Long, WorkFlowStatus> {

    //@Autowired
    private Repository<Long, WorkFlowStatusEntity> workFlowStatusRepository;
    @Autowired
    private Converter<WorkFlowStatusEntity, WorkFlowStatus> workFlowStatusConverter;
    //@Autowired
    private RepositoryByParentId<Long, WorkFlowStatusEntity> workFlowStatusEntityRepositoryByParentId;


    /**
     * Создание нового WorkFlowStatus
     *
     * @param workFlowStatus новый WorkFlowStatus
     */
    @Override
    public void create(WorkFlowStatus workFlowStatus) {
        WorkFlowStatusEntity entity = workFlowStatusConverter.toEntity(workFlowStatus);
        workFlowStatusRepository.create(entity);
        workFlowStatus.setId(entity.getId());
    }


    /**
     * Удаление WorkFlowStatus
     *
     * @param workFlowStatus удаляемый WorkFlowStatus
     */
    @Override
    public void remove(WorkFlowStatus workFlowStatus) {
        workFlowStatusRepository.delete(workFlowStatus.getId());
    }


    /**
     * Получить информацию по WorkFlowStatus
     *
     * @param id идентификатор WorkFlowStatus
     * @return WorkFlowStatus
     */
    @Override
    public WorkFlowStatus get(Long id) {
        return workFlowStatusConverter.toVo(workFlowStatusRepository.get(id));
    }


    /**
     * Обновить поля WorkFlowStatus
     *
     * @param workFlowStatus измененный WorkFlowStatus
     */
    @Override
    public void refresh(WorkFlowStatus workFlowStatus) {
        workFlowStatusRepository.update(workFlowStatusConverter.toEntity(workFlowStatus));
    }




    /**
     * Получить список статусов для определенного бизнес-процесса
     *
     * @param parentId WorkFlowStatusStatus.id
     * @return список статусов
     */
    @Override
    public Collection<WorkFlowStatus> getListByParentId(Long parentId) {
        return workFlowStatusEntityRepositoryByParentId.getByParentId(parentId).stream()
                .map(workFlowEntity -> workFlowStatusConverter.toVo(workFlowEntity))
                .collect(Collectors.toList());
    }

    /**
     * Получить весь список статусов
     *
     * @return список статусов
     */
    @Override
    public List<WorkFlowStatus> getList() {
        return workFlowStatusRepository.get().stream()
                .map(workFlowEntity -> workFlowStatusConverter.toVo(workFlowEntity))
                .collect(Collectors.toList());
    }
}
