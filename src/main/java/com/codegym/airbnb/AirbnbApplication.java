package com.codegym.airbnb;

import com.codegym.airbnb.model.Role;
import com.codegym.airbnb.model.UserModel;
import com.codegym.airbnb.services.RoleService;
import com.codegym.airbnb.services.UserService;
import com.codegym.airbnb.storage.StorageProperties;
import com.codegym.airbnb.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AirbnbApplication {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(AirbnbApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }

    @PostConstruct
    public void init() {
        List<UserModel> users = (List<UserModel>) userService.findAll();
        List<Role> roleList = (List<Role>) roleService.findAll();
        if (roleList.isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setId(1L);
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setId(2L);
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);
        }
        if (users.isEmpty()) {
            UserModel admin = new UserModel();
            Set<Role> roles = new HashSet<>();
            Role roleAdmin = new Role();
            roleAdmin.setId(1L);
            roleAdmin.setName("ROLE_ADMIN");
            roles.add(roleAdmin);
            admin.setName("admin");
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setRoles(roles);
            userService.save(admin);
        }
    }


}
