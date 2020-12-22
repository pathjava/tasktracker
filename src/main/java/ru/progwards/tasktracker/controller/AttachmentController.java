package ru.progwards.tasktracker.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.AttachmentContentDtoFull;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.model.TaskAttachment;

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
public class AttachmentController {

    @Autowired
    GetService<Long, TaskAttachment> getService;
    @Autowired
    CreateService<TaskAttachmentContent> createContentService;
    @Autowired
    Converter<TaskAttachment, TaskAttachmentDtoFull> dtoConverter;
    @Autowired
    Converter<TaskAttachmentContent, AttachmentContentDtoFull> dtoContentConverter;


    /**
     * Передать пользователю содержимое вложения
     * GET /rest/taskattachment/{id}/download/{anyFileName}
     *
     * @return передаем содержимое файла
     */
    @GetMapping("{id}/download/{name}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        if(id == null)
            throw new BadRequestException("TaskAttachment_id is not set");

        TaskAttachment vo = getService.get(id);
        TaskAttachmentDtoFull entity = dtoConverter.toDto(vo);
        AttachmentContentDtoFull content = dtoContentConverter.toDto(vo.getContent());

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
    public ResponseEntity<AttachmentContentDtoFull> upload(@PathVariable("id") Long task_id,
                                                           @RequestParam("file") MultipartFile file) {
        if (task_id == null)
            throw new BadRequestException("TaskAttachment_id is not set");
        if (file == null && file.getOriginalFilename().isEmpty())
            throw new BadRequestException("AttachmentContent 'file' is empty");

        AttachmentContentDtoFull content = null;

        try {
            content = new AttachmentContentDtoFull(0L, file.getInputStream());
            TaskAttachmentContent model = dtoContentConverter.toModel(content);
            createContentService.create(model);
            content.setId(model.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}
