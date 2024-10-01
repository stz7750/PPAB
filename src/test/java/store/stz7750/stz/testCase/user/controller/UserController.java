package store.stz7750.stz.testCase.user.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.stz7750.stz.testCase.user.service.UserServiceTest;

@RestController("testUserController")
@RequiredArgsConstructor
@Profile("test")
public class UserController {

    private final UserServiceTest userService;

    @PostMapping("/api/users/insert-random")
    public ResponseEntity<String> insertRandomUsers(@RequestParam int count) {
        userService.insertRandomUsers(count);
        return ResponseEntity.ok("통과");
    }
}