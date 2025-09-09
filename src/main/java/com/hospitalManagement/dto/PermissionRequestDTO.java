package com.hospitalManagement.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PermissionRequestDTO {
    private Long roleId;
    private Set<String> permissions; // e.g. ["VIEW_PATIENT", "EDIT_PATIENT"]
}
