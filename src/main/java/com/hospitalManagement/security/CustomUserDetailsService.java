package com.hospitalManagement.security;

import com.hospitalManagement.dto.CustomUserDetail;
import com.hospitalManagement.entity.User;
import com.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow();
        return new CustomUserDetail(
                user.getId(), user.getUserName(), user.getPassword(), user.getUserRoles()
        );
    }
}
