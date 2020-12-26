package ru.progwards.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.AccessRule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Dikov
 */

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessRuleController {

    private final CreateService<AccessRule> accessRuleCreateService;
    private final GetListService<AccessRule> accessRuleGetListService;
    private final GetService<Long, AccessRule> accessRuleGetService;
    private final RefreshService<AccessRule> accessRuleRefreshService;
    private final RemoveService<AccessRule> accessRuleRemoveService;

    private final Converter<AccessRule, AccessRuleDtoFull> accessRuleDtoConverter;

    @GetMapping("/rest/accessRule/list")
    public ResponseEntity<List<AccessRuleDtoFull>> getAccessRuleList() {
        List<AccessRule> voList = accessRuleGetListService.getList();
        List<AccessRuleDtoFull> dtoList = voList.stream().map(accessRuleDtoConverter::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/rest/accessRule/{id}")
    public ResponseEntity<AccessRuleDtoFull> getAccessRule(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accessRuleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/create")
    public ResponseEntity<?> createAccessRule(@RequestBody AccessRuleDtoFull dto) {
        AccessRule vo = accessRuleDtoConverter.toModel(dto);
        accessRuleCreateService.create(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/{id}/delete")
    public ResponseEntity<?> deleteAccessRule(@PathVariable Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accessRuleRemoveService.remove(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/{id}/update")
    public ResponseEntity<?> updateAccessRule(@PathVariable Long id, @RequestBody AccessRuleDtoFull dto) {
        if (id == null || !id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accessRuleRefreshService.refresh(accessRuleDtoConverter.toModel(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
