package com.hospitalManagement.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleRequestDTO {
    private String roleName;
    private Set<String> permissions; // e.g. ["VIEW_PATIENT", "EDIT_PATIENT"]
}
