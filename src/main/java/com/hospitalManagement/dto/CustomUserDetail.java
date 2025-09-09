package com.hospitalManagement.dto;

import com.hospitalManagement.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CustomUserDetail implements UserDetails {

    private Long userId;
    private String username;
    private String password;
    private Set<Role> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return userRoles.stream()
//                .map(userRole -> (GrantedAuthority) () -> "ROLE_"+userRole.getName().name()).toList();
//        return userRoles.stream()
//                .map(userRole -> new SimpleGrantedAuthority("ROLE_"+userRole.getName().name())).toList();

//        List<SimpleGrantedAuthority> list = userRoles.stream().map(
//                userRole -> new SimpleGrantedAuthority("ROLE_"+userRole.getName())).toList();
//
//        List<SimpleGrantedAuthority> list1 = userRoles.stream()
//                .flatMap(userRole -> userRole.getPermissions().stream()
//                        .map(permission -> new SimpleGrantedAuthority(permission.getName().toString()))) // <-- map entity → enum
//                .toList();
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.addAll(list);
        userRoles.forEach(userRole ->{
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getName()));
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = userRole.getPermissions()
                    .stream().map(permission -> new SimpleGrantedAuthority(permission.getName().name()))
                    .toList();
            grantedAuthorities.addAll(simpleGrantedAuthorities);
        });
        return grantedAuthorities;

    }
}
