package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.impl.AccessRuleDtoConverter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDto;
import ru.progwards.tasktracker.service.api.impl.AccessRuleService;
import ru.progwards.tasktracker.service.vo.AccessRule;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccessRuleController {

    @Autowired
    private AccessRuleService accessRuleService;
    @Autowired
    private AccessRuleDtoConverter accessRuleDtoConverter;

    @GetMapping("/rest/accessRule/list")
    public ResponseEntity<List<AccessRuleDto>> getAccessRuleList() {
        List<AccessRule> voList = accessRuleService.getList();
        List<AccessRuleDto> dtoList = voList.stream().map(vo -> accessRuleDtoConverter.toDto(vo)).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/rest/accessRule/{id}")
    public ResponseEntity<AccessRuleDto> getAccessRule(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accessRuleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/create")
    public ResponseEntity<?> createAccessRule(@RequestBody AccessRuleDto dto) {
        AccessRule vo = accessRuleDtoConverter.toModel(dto);
        accessRuleService.create(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/{id}/delete")
    public ResponseEntity<?> deleteAccessRule(@PathVariable Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accessRuleService.remove(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/{id}/update")
    public ResponseEntity<?> updateAccessRule(@PathVariable Long id, @RequestBody AccessRuleDto dto) {
        if (id == null || !id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accessRuleService.refresh(accessRuleDtoConverter.toModel(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
