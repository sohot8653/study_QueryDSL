package com.qsl.qsl_tutorial.boundedContext.user.controller;

import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import com.qsl.qsl_tutorial.boundedContext.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/user/{id}")
    public SiteUser user(@PathVariable Long id) {
        return userRepository.getQslUser(id);
    }
}
