package com.osci.contacts.controller;


import com.osci.contacts.model.dto.GroupDTO;
import com.osci.contacts.model.entity.User;
import com.osci.contacts.service.GroupService;
import com.osci.contacts.service.UserService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"주소록 그룹 API"})
@RequestMapping("/groups")
@RestController
public class GroupController {
    private GroupService groupService;
    private UserService userService;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @ApiOperation(value = "그룹 전체 조회")
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @ApiOperation(value = "그룹 조회", notes = "유저가 가진 그룹 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "그룹 이름 정렬 정의 name,(asc|desc)")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<GroupDTO>> getGroupsByUser(
            @ApiParam(value = "유저 PK")
            @PathVariable Integer id,
            @SortDefault("name") Sort nameSort) {
        User user = userService.findById(id);
        return ResponseEntity.ok(groupService.getGroupsByUser(user, nameSort));
    }

    @ApiOperation(value = "그룹 생성")
    @PostMapping
    public ResponseEntity<GroupDTO> createGroupByUser(@RequestBody @Valid GroupDTO body) {
        return ResponseEntity.ok(groupService.save(body));
    }

    @ApiOperation(value = "그룹 수정")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Integer id, @RequestBody @Valid GroupDTO body) {
        GroupDTO group = groupService.findGroupDTOById(id);
        group.update(body);
        return ResponseEntity.ok(groupService.save(group));
    }

    @ApiOperation(value = "그룹 삭제", notes = "그룹 아이디(PK)로 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer id) {
        groupService.findById(id);
        groupService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
