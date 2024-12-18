package lab5.iitu.controller;

import jakarta.validation.Valid;
import lab5.iitu.dto.LoginDto;
import lab5.iitu.dto.UsersDto;
import lab5.iitu.entity.Session;
import lab5.iitu.entity.Users;
import lab5.iitu.repository.SessionRepository;
import lab5.iitu.repository.UsersRepository;
import lab5.iitu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionRepository sessionRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UsersDto userDto) {

        if (userService.usernameExists(userDto.username())) {
            return ResponseEntity.badRequest().body("Пользователь с таким username уже существует!");
        }

        if (userService.emailExists(userDto.email())) {
            return ResponseEntity.badRequest().body("Пользователь с таким email уже существует!");
        }

        Users user = new Users();
        user.setUsername(userDto.username().trim());
        user.setPassword(passwordEncoder.encode(userDto.password().trim()));
        user.setEmail(userDto.email().trim());
        user.setCreateDate(userDto.createDate());
        userService.registerUser(user);

        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginDto loginDto) {

        if (loginDto.username() == null || loginDto.username().isEmpty() ||
                loginDto.password() == null || loginDto.password().isEmpty()) {
            return ResponseEntity.badRequest().body("Поля username и password обязательны для заполнения!");
        }

        Users user = userRepository.findByUsername(loginDto.username());
        if (user == null) {
            return ResponseEntity.badRequest().body("Пользователь с таким username не найден!");
        }

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Неверный пароль!");
        }

        sessionRepository.findByUsersId(user.getId())
                .ifPresent(sessionRepository::delete);

        var session = Session.builder()
                .token(UUID.randomUUID().toString())
                .users(user)
                .build();
        sessionRepository.save(session);

        return ResponseEntity.ok(session);
    }
}
