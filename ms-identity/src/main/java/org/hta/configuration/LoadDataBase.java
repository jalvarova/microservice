package org.hta.configuration;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.entity.Role;
import org.hta.domain.entity.User;
import org.hta.domain.RoleRepository;
import org.hta.domain.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Slf4j
class LoadDataBase {


    //@Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            Role roleAdmin = roleRepository.findByRoleName("ADMIN");
            Role roleUser = roleRepository.findByRoleName("USER");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = User
                    .builder()
                    .name("Alvaro")
                    .lastName("Aguinaga")
                    .username("aaguinaga")
                    .documentNumber("47082903")
                    .password(encoder.encode("12334"))
                    .enabled(Boolean.TRUE)
                    .isBlocked(Boolean.FALSE)
                    .userRoles(Collections.singletonList(roleAdmin))
                    .build();

            User user1 = User
                    .builder()
                    .name("Gonzalo")
                    .lastName("Aguinaga")
                    .username("gaguinaga")
                    .documentNumber("78985851")
                    .password(encoder.encode("12345"))
                    .enabled(Boolean.TRUE)
                    .isBlocked(Boolean.FALSE)
                    .userRoles(Collections.singletonList(roleUser))
                    .build();

            userRepository.saveAll(Arrays.asList(user, user1));

            log.info("User Preloading" + user);
        };
    }
}
