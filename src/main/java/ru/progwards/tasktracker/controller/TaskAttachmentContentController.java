package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoFull;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Методы запросов API по работе со связкой задача-вложение
 * Работа с содержимым файла
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/taskattachment")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskAttachmentContentController {

    private final GetService<Long, TaskAttachment> getService;
    private final CreateService<TaskAttachmentContent> createContentService;
    private final Converter<TaskAttachment, TaskAttachmentDtoFull> dtoConverter;
    private final Converter<TaskAttachmentContent, TaskAttachmentContentDtoFull> dtoContentConverter;


    /**
     * Передать пользователю содержимое вложения
     * GET /rest/taskattachment/{id}/download/{anyFileName}
     *
     * @return передаем содержимое файла
     */
    @GetMapping(value = "{id}/download/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        if(id == null)
            throw new BadRequestException("TaskAttachment_id is not set");

        TaskAttachment vo = getService.get(id);
        TaskAttachmentDtoFull entity = dtoConverter.toDto(vo);
        TaskAttachmentContentDtoFull content = dtoContentConverter.toDto(vo.getContent());

        response.setContentType("application/x-binary");
        response.setHeader("Content-disposition", "attachment;filename=" + entity.getName());
        response.setHeader("Content-Length", entity.getSize().toString());

        try {
            IOUtils.copy(content.getData(), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            // передача прервана, считаем, что пользователь отменил получение
        }
    }


    /**
     * Сохраняем вложение в БД
     *
     * @return возвращаем идентификатор сохраненного файла
     */
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskAttachmentContentDtoFull> upload(@PathVariable("id") Long task_id,
                                                               @RequestParam("file") MultipartFile file) {
        if (task_id == null)
            throw new BadRequestException("TaskAttachment_id is not set");
        if (file == null && file.getOriginalFilename().isEmpty())
            throw new BadRequestException("AttachmentContent 'file' is empty");

        TaskAttachmentContentDtoFull content = null;

        try {
            content = new TaskAttachmentContentDtoFull(0L, file.getInputStream());
            TaskAttachmentContent model = dtoContentConverter.toModel(content);
            createContentService.create(model);
            content.setId(model.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}
