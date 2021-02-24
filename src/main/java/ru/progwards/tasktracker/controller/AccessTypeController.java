package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.progwards.tasktracker.dto.AccessTypeDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.types.AccessType;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для работы с enum AccessType
 *
 * @author Artem
 */

@RestController
@RequestMapping(value = "/rest/accessType")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class AccessTypeController {

    private final Converter<AccessType, AccessTypeDtoPreview> accessTypeConverter;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccessTypeDtoPreview>> getList() {
        AccessType[] accessTypeArr = AccessType.values();
        List<AccessTypeDtoPreview> list = new ArrayList<>(accessTypeArr.length);
        for (AccessType accessType : accessTypeArr) {
            list.add(accessTypeConverter.toDto(accessType));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
