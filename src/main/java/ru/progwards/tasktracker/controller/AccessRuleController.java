package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с правилами доступа (AccessRule)
 *
 * @author Artem Dikov, Oleg Kiselev
 */

@RestController
@RequestMapping(value = "/rest/accessRule")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class AccessRuleController {

    private final CreateService<AccessRule> accessRuleCreateService;
    private final GetListService<AccessRule> accessRuleGetListService;
    private final GetService<Long, AccessRule> accessRuleGetService;
    private final RefreshService<AccessRule> accessRuleRefreshService;
    private final RemoveService<AccessRule> accessRuleRemoveService;
    private final Converter<AccessRule, AccessRuleDtoFull> accessRuleDtoConverter;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccessRuleDtoFull>> getAccessRuleList() {

        List<AccessRuleDtoFull> list = accessRuleGetListService.getList().stream()
                .map(accessRuleDtoConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new NotFoundException("List AccessRuleDtoFull is empty!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessRuleDtoFull> getAccessRule(@PathVariable("id") @Positive Long id) {

        AccessRule vo = accessRuleGetService.get(id);

        return new ResponseEntity<>(accessRuleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessRuleDtoFull> createAccessRule(
            @RequestBody @Validated(Create.class) AccessRuleDtoFull dto) {

        AccessRule accessRule = accessRuleDtoConverter.toModel(dto);
        accessRuleCreateService.create(accessRule);
        AccessRuleDtoFull createdAccessRule = accessRuleDtoConverter.toDto(accessRule);

        return new ResponseEntity<>(createdAccessRule, HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteAccessRule(@PathVariable @Positive Long id) {
        AccessRule vo = accessRuleGetService.get(id);

        accessRuleRemoveService.remove(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessRuleDtoFull> updateAccessRule(@PathVariable @Positive Long id,
                                                              @RequestBody @Validated(Update.class) AccessRuleDtoFull dto) {
        if (!id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        AccessRule accessRule = accessRuleDtoConverter.toModel(dto);
        accessRuleRefreshService.refresh(accessRule);
        AccessRuleDtoFull updatedAccessRule = accessRuleDtoConverter.toDto(accessRule);

        return new ResponseEntity<>(updatedAccessRule, HttpStatus.OK);
    }
}
