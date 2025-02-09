package kory.spring.com.bekoryfurniture.config;

import kory.spring.com.bekoryfurniture.entity.Admin;
import kory.spring.com.bekoryfurniture.enums.Role;
import kory.spring.com.bekoryfurniture.repository.AdminRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(AdminRepo adminRepo){
        return args -> {
            if(!adminRepo.existsByUserName("kory")){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                Admin admin = new Admin();
                admin.setUserName("kory");
                admin.setPassword(passwordEncoder.encode("kory"));
                admin.setRoles(roles);
                admin.setName("kory");
                admin.setAddress("HCM");
                admin.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzpWIBHvC9orthuHBdJ1RoqREiXtvmuh3izIcp9qUjpKOZKdeQS0wA3oM&s");
                admin.setDob("7/02/2024");
                admin.setGender("male");
                admin.setPhone("0123456789");
                admin.setDelete(false);
                admin.setCreatedAt("00:00-10/10/2024");

                adminRepo.save(admin);
                log.warn("kory user has been created with default password: kory, please change it");
            }

        };
    }
}
