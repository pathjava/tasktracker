package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.controller.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    private Converter<UserRole, UserRoleDtoFull> userRoleDtoConverter;
    @Autowired
    private Converter<AccessRule, AccessRuleDtoFull> accessRuleDtoConverter;

    @GetMapping("/rest/userRole/list")
    public ResponseEntity<List<UserRoleDtoFull>> getUserRoleList() {
        Collection<UserRole> voList = userRoleGetListService.getList();
        List<UserRoleDtoFull> dtoList = voList.stream().map(vo -> userRoleDtoConverter.toDto(vo)).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/rest/userRole/{id}")
    public ResponseEntity<UserRoleDtoFull> getUserRole(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userRoleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    @GetMapping("/rest/userRole/{id}/accessRules")
    public ResponseEntity<List<AccessRuleDtoFull>> getRules(@PathVariable("id") Long id) {
//        if (id == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        UserRole vo = userRoleGetService.get(id);
//        if (vo == null)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        List<AccessRuleDtoFull> rules = vo.getAccessRules().values().stream()
//                .map(r -> accessRuleDtoConverter.toDto(r)).collect(Collectors.toList());
//        return new ResponseEntity<>(rules, HttpStatus.OK);
        return null;
    }

    @PostMapping("/rest/userRole/create")
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleDtoFull dto) {
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
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody UserRoleDtoFull dto) {
        if (id == null || !id.equals(dto.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRoleRefreshService.refresh(userRoleDtoConverter.toModel(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rest/userRole/{id}/accessRules/add")
    public ResponseEntity<?> addRules(@PathVariable Long id, @RequestBody List<AccessRuleDtoFull> newRules) {
//        if (id == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        UserRole vo = userRoleGetService.get(id);
//        if (vo == null)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        List<AccessRule> newRulesVo = newRules.stream()
//                .map(r -> accessRuleDtoConverter.toModel(r)).collect(Collectors.toList());
//        Map<Long, AccessRule> ruleMap = vo.getAccessRules();
//        for (AccessRule newRule : newRulesVo) {
//            ruleMap.putIfAbsent(newRule.getId(), newRule);
//        }
//        userRoleRefreshService.refresh(vo);
//        return new ResponseEntity<>(HttpStatus.OK);
        return null;
    }

    @PostMapping("/rest/userRole/{id}/accessRules/delete")
    public ResponseEntity<?> deleteRules(@PathVariable Long id, @RequestBody List<Long> ruleIds) {
//        if (id == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        UserRole vo = userRoleGetService.get(id);
//        if (vo == null)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        Map<Long, AccessRule> ruleMap = vo.getAccessRules();
//        for (Long ruleId : ruleIds) {
//            ruleMap.remove(ruleId);
//        }
//        userRoleRefreshService.refresh(vo);
//        return new ResponseEntity<>(HttpStatus.OK);
        return null;
    }

    @PostMapping("/rest/userRole/{id}/accessRules/update")
    public ResponseEntity<?> updateRules(@PathVariable Long id, @RequestBody List<AccessRuleDtoFull> rulesDto) {
//        if (id == null)
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        UserRole vo = userRoleGetService.get(id);
//        if (vo == null)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        List<AccessRule> rulesVO = rulesDto.stream().map(r -> accessRuleDtoConverter.toModel(r)).collect(Collectors.toList());
//        Map<Long, AccessRule> ruleMap = vo.getAccessRules();
//        for (AccessRule rule : rulesVO) {
//            if (ruleMap.containsKey(rule.getId()))
//                ruleMap.put(rule.getId(), rule);
//        }
//        userRoleRefreshService.refresh(vo);
//        return new ResponseEntity<>(HttpStatus.OK);
        return null;
    }
}
