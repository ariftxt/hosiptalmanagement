package com.hospitalManagement.repository;

import com.hospitalManagement.entity.Permission;
import com.hospitalManagement.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);

    Optional<Permission> findByName(PermissionType permission);

}