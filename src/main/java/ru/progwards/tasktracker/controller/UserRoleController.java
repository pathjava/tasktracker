package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.impl.AccessRuleDtoConverter;
import ru.progwards.tasktracker.controller.converter.impl.UserRoleDtoConverter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDto;
import ru.progwards.tasktracker.controller.dto.UserRoleDto;
import ru.progwards.tasktracker.service.api.impl.UserRoleService;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRoleDtoConverter userRoleDtoConverter;
    @Autowired
    private AccessRuleDtoConverter accessRuleDtoConverter;

    @GetMapping("/rest/userRole/list")
    public ResponseEntity<List<UserRoleDto>> getUserRoleList() {
        List<UserRole> voList = userRoleService.getList();
        List<UserRoleDto> dtoList = voList.stream().map(vo -> userRoleDtoConverter.toDto(vo)).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/rest/userRole/{id}")
    public ResponseEntity<UserRoleDto> getUserRole(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userRoleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @GetMapping("/rest/userRole/{id}/accessRules")
    public ResponseEntity<List<AccessRuleDto>> getRules(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AccessRuleDto> rules = vo.getAccessRules().values().stream()
                .map(r -> accessRuleDtoConverter.toDto(r)).collect(Collectors.toList());
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/create")
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleDto dto) {
        UserRole vo = userRoleDtoConverter.toModel(dto);
        userRoleService.create(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/delete")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleService.remove(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/update")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody UserRoleDto dto) {
        if (id == null || !id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleService.refresh(userRoleDtoConverter.toModel(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/add")
    public ResponseEntity<?> addRules(@PathVariable Long id, @RequestBody List<AccessRuleDto> newRules) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AccessRule> newRulesVo = newRules.stream()
                .map(r -> accessRuleDtoConverter.toModel(r)).collect(Collectors.toList());
        userRoleService.addRules(vo, newRulesVo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/delete")
    public ResponseEntity<?> deleteRules(@PathVariable Long id, @RequestBody List<Long> ruleIds) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleService.deleteRules(vo, ruleIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/update")
    public ResponseEntity<?> updateRules(@PathVariable Long id, @RequestBody List<AccessRuleDto> rulesDto) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleService.updateRules(vo, rulesDto.stream()
                .map(r -> accessRuleDtoConverter.toModel(r)).collect(Collectors.toList()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
