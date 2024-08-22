package com.twd.SpringSecurityJWT.AdminUserInitializer;

import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class AdminUserInitializer {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeAdminUser() {
        return args -> {
            // Check if the admin user already exists
            if (!ourUserRepo.findByEmail("oussama@ump.ac.ma").isPresent()) {
                // Create the admin user directly
                OurUsers adminUser = new OurUsers();
                adminUser.setEmail("oussama@ump.ac.ma");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
               // adminUser.setBirth_date(LocalDate.of(1990, 1, 1));
                adminUser.setAddress("6000 Casa Avenue");
                adminUser.setRole("ADMIN");

                ourUserRepo.save(adminUser);
            }
            // Create the second admin user if it does not exist
            if (!ourUserRepo.findByEmail("abdelmawla@ump.ac.ma").isPresent()) {
                OurUsers adminUser2 = new OurUsers();
                adminUser2.setEmail("abdelmawla@ump.ac.ma");
                adminUser2.setPassword(passwordEncoder.encode("admin456"));
                adminUser2.setAddress("7000 Rabat Avenue");
                adminUser2.setRole("ADMIN");

                ourUserRepo.save(adminUser2);
            }
        };
    }
}

