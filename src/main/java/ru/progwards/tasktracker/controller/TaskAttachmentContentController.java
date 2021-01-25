package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoFull;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoPreview;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RefreshService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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

    private final GetService<Long, TaskAttachment> attachmentGetService;
    private final RefreshService<TaskAttachment> attachmentRefreshService;
    private final CreateService<TaskAttachmentContent> contentCreateService;
    private final Converter<TaskAttachment, TaskAttachmentDtoFull> dtoAttachmentConverter;
    private final Converter<TaskAttachmentContent, TaskAttachmentContentDtoFull> dtoContentConverter;
    private final Converter<TaskAttachmentContent, TaskAttachmentContentDtoPreview> dtoContentPreviewConverter;


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

        TaskAttachment vo = attachmentGetService.get(id);
        TaskAttachmentDtoFull entity = dtoAttachmentConverter.toDto(vo);
        TaskAttachmentContentDtoFull content = dtoContentConverter.toDto(vo.getContent());
        int size = content.getData().length;

        response.setContentType("application/x-binary");
        response.setHeader("Content-disposition", "attachment;filename=" + entity.getName()+"."+entity.getExtension());
        response.setHeader("Content-Length", String.valueOf(size));

        try (OutputStream os = response.getOutputStream()) {
            os.write(content.getData(), 0, size);
        } catch (Exception excp) {
            // передача прервана, считаем, что пользователь отменил получение
        }
    }


    /**
     * Сохраняем вложение в БД
     *
     * @return возвращаем идентификатор сохраненного файла
     */
    @PostMapping(value = "{attach_id}/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskAttachmentContentDtoPreview> upload(@PathVariable("attach_id") Long attach_id,
                                                                  @RequestParam("file") MultipartFile file) {
        if (attach_id == null)
            throw new BadRequestException("TaskAttachment_id is not set");
        if (file == null)
            throw new BadRequestException("TaskAttachmentContent 'file' is empty");

        TaskAttachment attachment = attachmentGetService.get(attach_id);
        TaskAttachmentContentDtoFull content = null;
        TaskAttachmentContentDtoPreview result = null;

        try {
            content = new TaskAttachmentContentDtoFull(null, file.getBytes());
            TaskAttachmentContent model = dtoContentConverter.toModel(content);
            contentCreateService.create(model);
            attachment.setContent(model);
            attachmentRefreshService.refresh(attachment);
            result = dtoContentPreviewConverter.toDto(model);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
