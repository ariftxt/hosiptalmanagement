package com.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponseDTO {

    private Long userId;
    private String jwt;
    private String refreshToken;
    private String username;
    private Long expirationTime; // epoch time (milliseconds)
    private Long iat;

}
