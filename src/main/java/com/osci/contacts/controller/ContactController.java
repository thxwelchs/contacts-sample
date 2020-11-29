package com.osci.contacts.controller;

import com.osci.contacts.model.dto.ContactDTO;
import com.osci.contacts.model.dto.ContactSaveOnlyGroup;
import com.osci.contacts.model.dto.ContactSaveOnlyName;
import com.osci.contacts.model.dto.ContactSaveOnlyPhone;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import com.osci.contacts.service.ContactService;
import com.osci.contacts.service.GroupService;
import com.osci.contacts.service.UserService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"주소록 API"})
@RequestMapping("/contacts")
@RestController
public class ContactController {
    private ContactService contactService;
    private UserService userService;
    private GroupService groupService;

    public ContactController(ContactService contactService, UserService userService, GroupService groupService) {
        this.contactService = contactService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @ApiOperation(value = "유저 주소록 리스트(페이징) 조회", notes = "유저 아이디(PK)로 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "0", value = "조회할 페이지의 번호"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "10", value = "조회할 페이지의 사이즈"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "조회할 페이지 데이터의 정렬 정의 {propertyName},(asc|desc)")
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<ContactDTO>> getContactsByUser(
            @ApiParam(value = "유저 ID(PK)")
            @PathVariable Integer userId,
            @PageableDefault
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "phone", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "createdDate", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "updatedDate", direction = Sort.Direction.ASC),
            }) Pageable pageable) {

        User user =userService.findById(userId);
        return ResponseEntity.ok(contactService.getContacts(user, pageable));
    }

    @ApiOperation(value = "그룹 주소록 리스트(페이징) 조회", notes = "그룹 아이디(PK)로 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "0", value = "조회할 페이지의 번호"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "10", value = "조회할 페이지의 사이즈"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "조회할 페이지 데이터의 정렬 정의 {propertyName},(asc|desc)")
    })
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<Page<ContactDTO>> getContactsByGroup(
            @ApiParam(value = "그룹 ID(PK)")
            @PathVariable Integer groupId,
            @PageableDefault
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "phone", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "createdDate", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "updatedDate", direction = Sort.Direction.ASC),
            }) Pageable pageable) {

        Group group = groupService.findById(groupId);
        return ResponseEntity.ok(contactService.getContactsByGroup(group, pageable));
    }

    @ApiOperation(value = "주소록 조회", notes = "주소록 아이디로 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContact(
            @ApiParam(value = "주소록 아이디(PK)")
            @PathVariable Integer id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @ApiOperation(value = "주소록 생성")
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(
            @RequestBody @Valid ContactDTO contact) {
        return new ResponseEntity<>(contactService.save(contact), HttpStatus.CREATED);
    }

    @ApiOperation(value = "주소록 수정(전체구조)")
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Integer id, @RequestBody @Valid ContactDTO body) {
        ContactDTO contact = contactService.findById(id);
        contact.update(body);
        return ResponseEntity.ok(contactService.save(contact));
    }

    @ApiOperation(value = "주소록 휴대폰번호 수정")
    @PatchMapping("/{id}/phone")
    public ResponseEntity<ContactDTO> updateContactPhone(
            @PathVariable Integer id,
            @ApiParam(examples = @Example(value = {@ExampleProperty(mediaType = "application/json", value = "{'test':'test'}")}),
                    name="name",value="value")
            @RequestBody @Valid ContactSaveOnlyPhone body) {
        ContactDTO contact = contactService.findById(id);
        contact.setPhone(body.getPhone());
        return ResponseEntity.ok(contactService.save(contact));
    }

    @ApiOperation(value = "주소록 이름 수정")
    @PatchMapping("/{id}/name")
    public ResponseEntity<ContactDTO> updateContactName(@PathVariable Integer id, @RequestBody @Valid ContactSaveOnlyName body) {
        ContactDTO contact = contactService.findById(id);
        contact.setName(body.getName());
        return ResponseEntity.ok(contactService.save(contact));
    }

    @ApiOperation(value = "주소록 그룹 수정")
    @PatchMapping("/{id}/group")
    public ResponseEntity<ContactDTO> updateContactName(@PathVariable Integer id, @RequestBody ContactSaveOnlyGroup body) {
        ContactDTO contact = contactService.findById(id);
        contact.setGroup(body.getGroup());
        return ResponseEntity.ok(contactService.save(contact));
    }

    @ApiOperation(value = "주소록 삭제", notes = "주소록 아이디(PK)로 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Integer id) {
        contactService.findById(id);
        contactService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
