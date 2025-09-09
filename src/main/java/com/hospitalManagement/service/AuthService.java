package com.hospitalManagement.service;

import com.hospitalManagement.dto.CustomUserDetail;
import com.hospitalManagement.dto.LoginResponseDTO;
import com.hospitalManagement.dto.UserDTO;
import com.hospitalManagement.dto.UserRequestDTO;
import com.hospitalManagement.entity.RoleType;
import com.hospitalManagement.entity.User;
import com.hospitalManagement.entity.Role;
import com.hospitalManagement.enums.AuthProviderType;
import com.hospitalManagement.repository.UserRepository;
import com.hospitalManagement.repository.RoleRepository;
import com.hospitalManagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository userRoleRepository;
    private final PatientService patientService;

    public UserDTO singUp(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByUserName(userRequestDTO.getUserName()).orElse(null);

        if(user != null)
            throw new IllegalArgumentException("User already exist.");
        Role userDefaultRole = userRoleRepository.findByName(RoleType.PATIENT).orElseThrow();
        user = userRepository.save(
                User.builder().userName(userRequestDTO.getUserName())
                        .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                        .userRoles(Set.of(userDefaultRole))
                        .providerType(AuthProviderType.EMAIL)
                        .build()
        );
        patientService.create(userRequestDTO.getUserName(), user.getId());
        return new UserDTO(user.getId(), user.getUserName(), user.getUserRoles());
    }

    public LoginResponseDTO login(UserRequestDTO userRequestDTO) {
        CustomUserDetail customUserDetail = null;
        String token = null;
        String refreshToken = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(), userRequestDTO.getPassword())
            );
            if(authentication == null)
                throw new IllegalArgumentException("Invalid request");
            customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            token = jwtUtil.generateAccessToken(customUserDetail);
            refreshToken = jwtUtil.generateRefreshToken(customUserDetail);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        assert customUserDetail != null;
        return jwtUtil.generateResponse(token, refreshToken, customUserDetail);
    }

    public LoginResponseDTO refreshToken(UserRequestDTO userRequestDTO) {
        String refreshToken = userRequestDTO.getRefreshToken();
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            return null;
        }

        String username = jwtUtil.extractUsername(refreshToken);
        Long userId = Long.valueOf(jwtUtil.extractClaim(refreshToken, "userId", String.class));

        // You can also reload user from DB here if you want to enforce user status/roles.
        CustomUserDetail principal = new CustomUserDetail(userId, username,"", null);

        String newAccessToken = jwtUtil.generateAccessToken(principal);

        // Reuse same refreshToken (or issue a new one if you prefer rotation)
        return jwtUtil.generateResponse(newAccessToken, refreshToken, principal);
//        return LoginResponseDTO.builder()
//                .jwt(newAccessToken)
//                .userId(userId)
//                .refreshToken(refreshToken)
//                .username(username)
//                .expirationTime(jwtUtil.extractClaim(newAccessToken, "exp", Long.class))
//                .iat(jwtUtil.extractClaim(newAccessToken, "iat", Long.class))
//                .build();
    }

}
