package com.osci.contacts.controller;

import com.osci.contacts.model.dto.GroupDTO;
import com.osci.contacts.model.dto.UserDTO;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import com.osci.contacts.service.GroupService;
import com.osci.contacts.service.UserService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"유저 API"})
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private GroupService groupService;

    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @ApiOperation(value = "유저 조회", notes = "유저 아이디(PK)로 조회")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@ApiParam(value = "유저 PK") @PathVariable Integer id) {
       return ResponseEntity.ok(userService.findUserDTOById(id));
    }

    @ApiOperation(value = "유저 조회", notes = "유저 아이디로 조회")
    @GetMapping
    public ResponseEntity<UserDTO> getUserByUserId(@ApiParam(value = "유저 아이디") @RequestParam(value = "user_id") String userId) {
        return ResponseEntity.ok(userService.findUserDTOByUserId(userId));
    }

    @ApiOperation(value = "유저 생성")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO body) {
        return ResponseEntity.ok(userService.save(body));
    }

    @ApiOperation(value = "유저 수정")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDTO body){
        UserDTO user = userService.findUserDTOById(id);
        user.update(body);
        return ResponseEntity.ok(userService.save(user));
    }

    @ApiOperation(value = "유저 삭제", notes = "유저 PK로 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@ApiParam(value = "유저 PK") @PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok(null);
    }




//    @PostMapping("/{id}/groups")
//    public ResponseEntity<List<GroupDTO>> createGroupsByUser(@PathVariable Integer id,
//                                                             @RequestBody @Valid GroupDTO group) {
//        User user = userService.findById(id);
//        user.addGroup(groupService.toEntity(group));
//        userService.save(user);
//        return ResponseEntity.ok(null);
//    }
}
