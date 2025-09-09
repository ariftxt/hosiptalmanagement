package com.hospitalManagement.controller;

import com.hospitalManagement.dto.AssignRoleRequestDTO;
import com.hospitalManagement.dto.PermissionRequestDTO;
import com.hospitalManagement.dto.RoleRequestDTO;
import com.hospitalManagement.dto.UserDTO;
import com.hospitalManagement.entity.Role;
import com.hospitalManagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping(value = "/get/all")
    public Page<Role> getAll(Pageable pageable){
        return roleService.findAll(pageable);
    }

    // Create Role with Permissions
    @PostMapping(value = "/create")
    public ResponseEntity<Role> create(@RequestBody RoleRequestDTO roleRequestDTO){
        return roleService.create(roleRequestDTO);
    }

    @PostMapping(value = "/add/permissions")
    public ResponseEntity<Role> addPermissionToRole(@RequestBody PermissionRequestDTO requestDTO){
        return roleService.addPermissionToRole(requestDTO);
    }

    // Assign Role to User
    @PostMapping("/assign")
    public ResponseEntity<UserDTO> assignRoleToUser(@RequestBody AssignRoleRequestDTO request) {
        return roleService.assignRoleToUser(request);
    }

    @DeleteMapping(value = "/unassign")
    public ResponseEntity<UserDTO> unassignRoleToUser(@RequestBody AssignRoleRequestDTO requestDTO){
        return roleService.unassignRoleToUser(requestDTO);
    }


}
