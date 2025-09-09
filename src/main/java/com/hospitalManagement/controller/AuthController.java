package com.hospitalManagement.controller;

import com.hospitalManagement.dto.LoginResponseDTO;
import com.hospitalManagement.dto.UserDTO;
import com.hospitalManagement.dto.UserRequestDTO;
import com.hospitalManagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(
            @RequestBody UserRequestDTO userRequestDTO
    ){
        return ResponseEntity.ok(authService.singUp(userRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(authService.login(userRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(authService.refreshToken(userRequestDTO));
    }

}
