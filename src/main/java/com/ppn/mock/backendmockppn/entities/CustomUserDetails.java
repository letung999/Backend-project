package com.ppn.mock.backendmockppn.entities;

import com.ppn.mock.backendmockppn.constant.Privileges;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        Set<Privileges> privilegesEnums = new HashSet<>();
        for (Role role : roles) {
            privilegesEnums.addAll(role.getPrivilegesEnums());
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Privileges privilegesEnum : privilegesEnums) {
            authorities.add(new SimpleGrantedAuthority(privilegesEnum.toString()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String toString() {
        return "CustomUserDetails{" + "user=" + user + '}';
    }
}
