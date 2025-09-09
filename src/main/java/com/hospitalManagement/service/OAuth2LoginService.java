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
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class OAuth2LoginService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository userRoleRepository;
    private final PatientService patientService;

    public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        // fetch providerId
        AuthProviderType providerType = jwtUtil.getProviderType(registrationId);
        String providerId = jwtUtil.getProviderId(registrationId, oAuth2User);
        User user = userRepository.findByProviderTypeAndProviderId(providerType, providerId).orElse(null);

        String email = oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUserName(email).orElse(null);
        UserDTO userDTO = null;
        if(user == null && emailUser == null){
            // sign up flow
            String username = jwtUtil.findUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            userDTO = singUp(
                    UserRequestDTO.builder()
                            .userName(username).password("12345")
                            .build(), providerId, providerType
            );
        }
        else if (user != null){
            if(StringUtils.isNotBlank(email) && !user.getUserName().equals(email)){
                user.setUserName(email);
                userRepository.save(user);
            }
            userDTO = new UserDTO(user.getId(), user.getUserName(), user.getUserRoles());
        } else {
            userDTO = new UserDTO(emailUser.getId(), emailUser.getUserName(), emailUser.getUserRoles());
        }
        CustomUserDetail customUserDetail = CustomUserDetail.builder()
                .userId(userDTO.getUserId())
                .username(userDTO.getUserName())
                .userRoles(userDTO.getUserRoles())
                .build();
        String token = jwtUtil.generateAccessToken(customUserDetail);
        String refreshToken = jwtUtil.generateRefreshToken(customUserDetail);
        LoginResponseDTO loginResponseDTO = jwtUtil.generateResponse(token, refreshToken, customUserDetail);
        return ResponseEntity.ok(loginResponseDTO);
    }

    private UserDTO singUp(UserRequestDTO userRequestDTO, String providerId, AuthProviderType providerType) {
        Role userDefaultRole = userRoleRepository.findByName(RoleType.PATIENT).orElseThrow();
        User user = userRepository.save(
                User.builder().userName(userRequestDTO.getUserName()).providerId(providerId).providerType(providerType)
                        .userRoles(Set.of(userDefaultRole))
                        .build()
        );
        patientService.create(userRequestDTO.getUserName(), user.getId());
        return new UserDTO(user.getId(), user.getUserName(), user.getUserRoles());
    }
}
