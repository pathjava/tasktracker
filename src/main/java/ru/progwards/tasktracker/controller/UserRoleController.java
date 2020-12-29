package ru.progwards.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Dikov
 */

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleController {

    private final CreateService<UserRole> userRoleCreateService;
    private final GetListService<UserRole> userRoleGetListService;
    private final GetService<Long, UserRole> userRoleGetService;
    private final RefreshService<UserRole> userRoleRefreshService;
    private final RemoveService<UserRole> userRoleRemoveService;

    private final Converter<UserRole, UserRoleDtoFull> userRoleDtoConverter;
    private final Converter<AccessRule, AccessRuleDtoFull> accessRuleDtoConverter;

    private final RefreshService<AccessRule> accessRuleRefreshService;

    /**
     * @return
     */
    @GetMapping("/rest/userRole/list")
    public ResponseEntity<List<UserRoleDtoFull>> getUserRoleList() {
        List<UserRole> voList = userRoleGetListService.getList();
        List<UserRoleDtoFull> dtoList = voList.stream().map(userRoleDtoConverter::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/rest/userRole/{id}")
    public ResponseEntity<UserRoleDtoFull> getUserRole(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userRoleDtoConverter.toDto(vo), HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/rest/userRole/{id}/accessRules")
    public ResponseEntity<List<AccessRuleDtoFull>> getRules(@PathVariable("id") Long id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AccessRuleDtoFull> rules = vo.getAccessRules().stream()
                .map(accessRuleDtoConverter::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    /**
     * @param dto
     * @return
     */
    @PostMapping("/rest/userRole/create")
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleDtoFull dto) {
        UserRole vo = userRoleDtoConverter.toModel(dto);
        userRoleCreateService.create(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
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

    /**
     * @param id
     * @param dto
     * @return
     */
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

    /**
     * @param id
     * @param newRules
     * @return
     */
    @PostMapping("/rest/userRole/{id}/accessRules/add")
    public ResponseEntity<?> addRules(@PathVariable Long id, @RequestBody List<AccessRuleDtoFull> newRules) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<AccessRule> newRulesVo = newRules.stream().map(accessRuleDtoConverter::toModel).collect(Collectors.toList());
        List<AccessRule> rules = vo.getAccessRules();
        rules.addAll(newRulesVo);
        userRoleRefreshService.refresh(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id
     * @param ruleIdList
     * @return
     */
    @PostMapping("/rest/userRole/{id}/accessRules/delete")
    public ResponseEntity<?> deleteRules(@PathVariable Long id, @RequestBody List<Long> ruleIdList) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<AccessRule> rules = vo.getAccessRules();
        rules.sort(Comparator.comparing(AccessRule::getId));
        for (long ruleId : ruleIdList) {
            int index = binarySearchById(rules, ruleId);
            if (index > -1)
                rules.remove(index);
        }
        userRoleRefreshService.refresh(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private int binarySearchById(List<AccessRule> list, long key) {
        int first = 0;
        int last = list.size()-1;
        int index = -1;
        while (first <= last) {
            int mid = (first + last) / 2;
            long id = list.get(mid).getId();
            if (id < key) {
                first = mid + 1;
            } else if (id > key) {
                last = mid - 1;
            } else if (id == key) {
                index = mid;
                break;
            }
        }
        return index;
    }

    /**
     * @param id
     * @param ruleDtoList
     * @return
     */
    @PostMapping("/rest/userRole/{id}/accessRules/update")
    public ResponseEntity<?> updateRules(@PathVariable Long id, @RequestBody List<AccessRuleDtoFull> ruleDtoList) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserRole vo = userRoleGetService.get(id);
        if (vo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<AccessRule> ruleVOList = ruleDtoList.stream().map(accessRuleDtoConverter::toModel).collect(Collectors.toList());
        List<AccessRule> rules = vo.getAccessRules();
        rules.sort(Comparator.comparing(AccessRule::getId));
        for (AccessRule rule : ruleVOList) {
            int index = binarySearchById(rules, rule.getId());
            if (index > -1) {
                accessRuleRefreshService.refresh(rule); // ???
            }
        }
//        userRoleRefreshService.refresh(vo); // ???
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
