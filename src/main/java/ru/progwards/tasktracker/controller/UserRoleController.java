package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDto;
import ru.progwards.tasktracker.controller.dto.UserRoleDto;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRoleController {

    @Autowired
    private CreateService<UserRole> userRoleCreateService;
    @Autowired
    private GetListService<UserRole> userRoleGetListService;
    @Autowired
    private GetService<Long, UserRole> userRoleGetService;
    @Autowired
    private RefreshService<UserRole> userRoleRefreshService;
    @Autowired
    private RemoveService<UserRole> userRoleRemoveService;
    @Autowired
    private AddDetailingService<UserRole, AccessRule> addAccessRuleService;
    @Autowired
    private DeleteDetailingService<UserRole, Long> deleteAccessRuleService;
    @Autowired
    private UpdateDetailingService<UserRole, AccessRule> updateAccessRuleService;

    @Autowired
    private Converter<UserRole, UserRoleDto> userRoleDtoConverter;
    @Autowired
    private Converter<AccessRule, AccessRuleDto> accessRuleDtoConverter;

    @GetMapping("/rest/userRole/list")
    public ResponseEntity<List<UserRoleDto>> getUserRoleList() {
        Collection<UserRole> voList = userRoleGetListService.getList();
        List<UserRoleDto> dtoList = voList.stream().map(vo -> userRoleDtoConverter.toDto(vo)).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/rest/userRole/{id}")
    public ResponseEntity<UserRoleDto> getUserRole(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userRoleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @GetMapping("/rest/userRole/{id}/accessRules")
    public ResponseEntity<List<AccessRuleDto>> getRules(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AccessRuleDto> rules = vo.getAccessRules().values().stream()
                .map(r -> accessRuleDtoConverter.toDto(r)).collect(Collectors.toList());
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/create")
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleDto dto) {
        UserRole vo = userRoleDtoConverter.toModel(dto);
        userRoleCreateService.create(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/delete")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleRemoveService.remove(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/update")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody UserRoleDto dto) {
        if (id == null || !id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleRefreshService.refresh(userRoleDtoConverter.toModel(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/add")
    public ResponseEntity<?> addRules(@PathVariable Long id, @RequestBody List<AccessRuleDto> newRules) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AccessRule> newRulesVo = newRules.stream()
                .map(r -> accessRuleDtoConverter.toModel(r)).collect(Collectors.toList());
        addAccessRuleService.addDetailing(vo, newRulesVo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/delete")
    public ResponseEntity<?> deleteRules(@PathVariable Long id, @RequestBody List<Long> ruleIds) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        deleteAccessRuleService.deleteDetailing(vo, ruleIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/update")
    public ResponseEntity<?> updateRules(@PathVariable Long id, @RequestBody List<AccessRuleDto> rulesDto) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        updateAccessRuleService.updateDetailing(vo, rulesDto.stream()
                .map(r -> accessRuleDtoConverter.toModel(r)).collect(Collectors.toList()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}