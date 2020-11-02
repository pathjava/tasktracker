package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDto;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccessRuleController {

    @Autowired
    private CreateService<AccessRule> accessRuleCreateService;
    @Autowired
    private GetListService<AccessRule> accessRuleGetListService;
    @Autowired
    private GetService<Long, AccessRule> accessRuleGetService;
    @Autowired
    private RefreshService<AccessRule> accessRuleRefreshService;
    @Autowired
    private RemoveService<AccessRule> accessRuleRemoveService;

    @Autowired
    private Converter<AccessRule, AccessRuleDto> accessRuleDtoConverter;

    @GetMapping("/rest/accessRule/list")
    public ResponseEntity<List<AccessRuleDto>> getAccessRuleList() {
        Collection<AccessRule> voList = accessRuleGetListService.getList();
        List<AccessRuleDto> dtoList = voList.stream().map(vo -> accessRuleDtoConverter.toDto(vo)).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/rest/accessRule/{id}")
    public ResponseEntity<AccessRuleDto> getAccessRule(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accessRuleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @PostMapping("/rest/accessRule/create")
    public ResponseEntity<?> createAccessRule(@RequestBody AccessRuleDto dto) {
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
    public ResponseEntity<?> updateAccessRule(@PathVariable Long id, @RequestBody AccessRuleDto dto) {
        if (id == null || !id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AccessRule vo = accessRuleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accessRuleRefreshService.refresh(accessRuleDtoConverter.toModel(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
