package com.ppn.mock.backendmockppn.config;

import com.ppn.mock.backendmockppn.constant.Privileges;
import com.ppn.mock.backendmockppn.entities.Role;
import com.ppn.mock.backendmockppn.entities.User;
import com.ppn.mock.backendmockppn.repository.RoleRepository;
import com.ppn.mock.backendmockppn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitialData implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            Set<Privileges> privilegesEnumSet = new HashSet<>();
            privilegesEnumSet.add(Privileges.SYSTEM_ADMIN);
            Role role = new Role();
            role.setName("System Admin");
            role.setPrivilegesEnums(privilegesEnumSet);
            roleRepository.save(role);

            roles = roleRepository.findAll();
            String pwdEncrypt = bCryptPasswordEncoder.encode("123456");
            User user = new User();
            user.setEmail("systemadmin@gmail.com");
            user.setPassword(pwdEncrypt);
            user.setRoles(new HashSet<>(roles));
            userRepository.save(user);
        }
    }
}
