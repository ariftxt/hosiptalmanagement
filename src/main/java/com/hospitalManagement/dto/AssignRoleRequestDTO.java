package com.hospitalManagement.dto;

import lombok.Data;

@Data
public class AssignRoleRequestDTO {
    private Long userId;
    private String roleName;
}
