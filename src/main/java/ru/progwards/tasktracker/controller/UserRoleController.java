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
import ru.progwards.tasktracker.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с ролями пользователей (UserRole)
 *
 * @author Artem Dikov, Oleg Kiselev
 */

@RestController
@RequestMapping(value = "/rest/userRole")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
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
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserRoleDtoFull>> getUserRoleList() {
        List<UserRoleDtoFull> list = userRoleGetListService.getList().stream()
                .map(userRoleDtoConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new NotFoundException("List UserRoleDtoFull is empty!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleDtoFull> getUserRole(@PathVariable("id") @Positive Long id) {
        UserRole userRole = userRoleGetService.get(id);

        return new ResponseEntity<>(userRoleDtoConverter.toDto(userRole), HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/accessRules", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccessRuleDtoFull>> getRules(@PathVariable("id") @Positive Long id) {
        UserRole userRole = userRoleGetService.get(id);

        List<AccessRuleDtoFull> rules = userRole.getAccessRules().stream()
                .map(accessRuleDtoConverter::toDto)
                .collect(Collectors.toList());

        if (rules.isEmpty())
            throw new NotFoundException("List AccessRuleDtoFull is empty!");

        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    /**
     * @param dto
     * @return
     */
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleDtoFull> createUserRole(@RequestBody @Validated(Create.class) UserRoleDtoFull dto) {

        UserRole userRole = userRoleDtoConverter.toModel(dto);
        userRoleCreateService.create(userRole);
        UserRoleDtoFull createdUserRole = userRoleDtoConverter.toDto(userRole);

        return new ResponseEntity<>(createdUserRole, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteUserRole(@PathVariable @Positive Long id) {
        UserRole vo = userRoleGetService.get(id);
        userRoleRemoveService.remove(vo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @PostMapping(value = "/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleDtoFull> updateUserRole(@PathVariable @Positive Long id,
                                            @RequestBody @Validated(Update.class) UserRoleDtoFull dto) {

        if (!id.equals(dto.getId()))
            throw new BadRequestException("This operation is not possible!");

        UserRole userRole = userRoleDtoConverter.toModel(dto);
        userRoleRefreshService.refresh(userRole);
        UserRoleDtoFull updatedUserRole = userRoleDtoConverter.toDto(userRole);

        return new ResponseEntity<>(updatedUserRole, HttpStatus.OK);
    }

    /**
     * @param id
     * @param newRules
     * @return
     */
    @PostMapping(value = "/{id}/accessRules/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRules(@PathVariable @Positive Long id,
                                      @RequestBody List<AccessRuleDtoFull> newRules) {
        UserRole userRole = userRoleGetService.get(id);
        List<AccessRule> rules = userRole.getAccessRules();
        rules.addAll(newRules.stream()
                .map(accessRuleDtoConverter::toModel)
                .collect(Collectors.toList()));
        userRoleRefreshService.refresh(userRole);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id
     * @param ruleIdList
     * @return
     */
    @PostMapping(value = "/{id}/accessRules/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRules(@PathVariable @Positive Long id,
                                         @RequestBody List<Long> ruleIdList) {
        UserRole userRole = userRoleGetService.get(id);
        List<AccessRule> rules = userRole.getAccessRules();
        rules.sort(Comparator.comparing(AccessRule::getId));
        for (long ruleId : ruleIdList) {
            int index = binarySearchById(rules, ruleId);
            if (index > -1)
                rules.remove(index);
        }
        userRoleRefreshService.refresh(userRole);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private int binarySearchById(List<AccessRule> list, long key) {
        int first = 0;
        int last = list.size() - 1;
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
    @PostMapping(value = "/{id}/accessRules/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRules(@PathVariable @Positive Long id,
                                         @RequestBody List<AccessRuleDtoFull> ruleDtoList) {
        UserRole userRole = userRoleGetService.get(id);
        List<AccessRule> ruleVOList = ruleDtoList.stream()
                .map(accessRuleDtoConverter::toModel)
                .collect(Collectors.toList());
        List<AccessRule> rules = userRole.getAccessRules();
        rules.sort(Comparator.comparing(AccessRule::getId));
        for (AccessRule rule : ruleVOList) {
            int index = binarySearchById(rules, rule.getId());
            if (index > -1) {
                accessRuleRefreshService.refresh(rule);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
