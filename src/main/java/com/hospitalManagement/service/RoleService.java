package com.hospitalManagement.service;

import com.hospitalManagement.dto.AssignRoleRequestDTO;
import com.hospitalManagement.dto.PermissionRequestDTO;
import com.hospitalManagement.dto.RoleRequestDTO;
import com.hospitalManagement.dto.UserDTO;
import com.hospitalManagement.entity.Permission;
import com.hospitalManagement.entity.Role;
import com.hospitalManagement.entity.RoleType;
import com.hospitalManagement.entity.User;
import com.hospitalManagement.enums.PermissionType;
import com.hospitalManagement.mapper.UserMapper;
import com.hospitalManagement.repository.PermissionRepository;
import com.hospitalManagement.repository.RoleRepository;
import com.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public ResponseEntity<Role> create(RoleRequestDTO request) {
        Set<Permission> permissionSet = request.getPermissions().stream()
                .map(p -> permissionRepository.findByName(PermissionType.valueOf(p))
                        .orElseGet(() -> permissionRepository.save(new Permission(null, PermissionType.valueOf(p)))))
                .collect(Collectors.toSet());

        Role role = roleRepository.findByName(RoleType.valueOf(request.getRoleName()))
                .orElseGet(() -> Role.builder().name(RoleType.valueOf(request.getRoleName())).build());
        role.getPermissions().addAll(permissionSet);

        return ResponseEntity.ok(roleRepository.save(role));
    }

    public ResponseEntity<UserDTO> assignRoleToUser(AssignRoleRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(RoleType.valueOf(request.getRoleName()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getUserRoles().add(role);
        return ResponseEntity.ok(UserMapper.toDTO(userRepository.save(user)));
    }

    public ResponseEntity<Role> addPermissionToRole(PermissionRequestDTO requestDTO) {
        Role role = roleRepository.findById(requestDTO.getRoleId()).orElseThrow();

        Set<Permission> permissionSet = requestDTO.getPermissions().stream()
                .map(p -> permissionRepository.findByName(p)
                        .orElseGet(() -> permissionRepository.save(new Permission(null, PermissionType.valueOf(p)))))
                .collect(Collectors.toSet());

        role.getPermissions().addAll(permissionSet);
        return ResponseEntity.ok(roleRepository.save(role));
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<UserDTO> unassignRoleToUser(AssignRoleRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(RoleType.valueOf(request.getRoleName()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getUserRoles().remove(role);
        return ResponseEntity.ok(UserMapper.toDTO(userRepository.save(user)));

    }
}
