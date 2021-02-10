package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoFull;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RefreshService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Методы запросов API по работе со связкой задача-вложение
 * Работа с содержимым файла
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/taskattachment")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class TaskAttachmentContentController {

    private final GetService<Long, TaskAttachment> attachmentGetService;
    private final RefreshService<TaskAttachment> attachmentRefreshService;
    private final CreateService<TaskAttachmentContent> contentCreateService;
    private final Converter<TaskAttachmentContent, TaskAttachmentContentDtoFull> dtoConverter;
    private final Converter<TaskAttachmentContent, TaskAttachmentContentDtoPreview> dtoPreviewConverter;


    /**
     * Передать пользователю содержимое вложения
     * GET /rest/taskattachment/{id}/download/{anyFileName}
     */
    @GetMapping(value = "{id}/download/{name}")
    public void download(
            @PathVariable("id") @Positive Long id,
            @PathVariable("name") String name,
            HttpServletResponse response
    ) {
        TaskAttachment vo = attachmentGetService.get(id);
        TaskAttachmentContent content = vo.getContent();
        int size = content.getData().length;

        response.setContentType("application/x-binary");
        response.setHeader("Content-disposition", "attachment;filename=" + vo.getName()+"."+vo.getExtension());
        response.setHeader("Content-Length", String.valueOf(size));

        try (OutputStream os = response.getOutputStream()) {
            os.write(content.getData(), 0, size);
        } catch (Exception e) {
            // передача прервана, считаем, что пользователь отменил получение
        }
    }


    /**
     * Сохраняем вложение в БД
     *
     * @return возвращаем идентификатор сохраненного файла
     */
    @PostMapping(value = "{attach_id}/upload",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskAttachmentContentDtoFull> upload(
            @PathVariable("attach_id") @Positive Long attach_id,
            @RequestParam("file") @NotNull MultipartFile file
    ) {
        TaskAttachment attachment = attachmentGetService.get(attach_id);
        TaskAttachmentContent content;
        TaskAttachmentContentDtoFull result = null;

        try {
            content = new TaskAttachmentContent(null, file.getBytes(), List.of(attachment));
            attachment.setContent(content);
            contentCreateService.create(content);
            attachmentRefreshService.refresh(attachment);
            result = dtoConverter.toDto(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
