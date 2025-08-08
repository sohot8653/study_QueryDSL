package com.qsl.qsl_tutorial.base;

import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import com.qsl.qsl_tutorial.boundedContext.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
//@Profile({"dev", "test"})
@Profile("test")
public class TestInitData {
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            SiteUser u1 = SiteUser.builder()
                            .username("user1")
                            .password("{noop}1234")
                            .email("user1@test.com")
                            .build();
            SiteUser u2 = new SiteUser(null, "user2", "{noop}1234", "user2@test.com");

            List<SiteUser> siteUsers = userRepository.saveAll(Arrays.asList(u1, u2));
        };
    }
}
