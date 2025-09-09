package com.hospitalManagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {
    PATIENT_VIEW("patient:view"),
    PATIENT_WRITE("patient:write"),
    APPOINTMENT_CREATE("appointment:create"),
    APPOINTMENT_UPDATE("appointment:update"),
    APPOINTMENT_DELETE("appointment:delete"),
    APPOINTMENT_VIEW("appointment:view"),
    ROLE_DELETE("role:delete");

    private final String permission;
}
