package ru.progwards.tasktracker.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AttachmentContentDto;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDto;
import ru.progwards.tasktracker.controller.exceptions.NullObjectException;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;
import ru.progwards.tasktracker.service.vo.TaskAttachment;

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
public class TaskAttachmentsController {

    @Autowired
    GetService<Long, TaskAttachment> getService;
    @Autowired
    CreateService<AttachmentContent> createContentService;
    @Autowired
    Converter<TaskAttachment, TaskAttachmentDto> dtoConverter;
    @Autowired
    Converter<AttachmentContent, AttachmentContentDto> dtoContentConverter;


    /**
     * Передать пользователю содержимое вложения
     * GET /rest/taskattachment/{id}/download/{anyFileName}
     *
     * @return передаем содержимое файла
     */
    @GetMapping("{id}/download/{name}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        if(id == null)
            throw new NullObjectException("TaskAttachment_id is not set");

        TaskAttachment vo = getService.get(id);
        TaskAttachmentDto entity = dtoConverter.toDto(vo);
        AttachmentContentDto content = dtoContentConverter.toDto(vo.getContent());

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
    @PostMapping("/upload")
    public ResponseEntity<AttachmentContentDto> upload(@PathVariable("id") Long task_id,
                                                       @RequestParam("file") MultipartFile file) {
        if (task_id == null)
            throw new NullObjectException("TaskAttachment_id is not set");
        if (file == null && file.getOriginalFilename().isEmpty())
            throw new NullObjectException("AttachmentContent 'file' is empty");

        AttachmentContentDto content = null;

        try {
            content = new AttachmentContentDto(0L, file.getInputStream());
            AttachmentContent model = dtoContentConverter.toModel(content);
            createContentService.create(model);
            content.setId(model.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}
