package lab5.iitu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record UsersDto(
        Long id,
        @NotEmpty(message = "CreateDate cannot be empty!")
        String createDate,
        @NotEmpty(message = "Username cannot be empty!")
        String username,
        @NotEmpty(message = "Email cannot be empty!")
        String email,
        @Min(value = 6, message = "Пароль должен содержать больше 6 символов")
        @NotEmpty(message = "Password cannot be empty!")
        String password
) {
    public static UsersDto defaultInstance() {
        return new UsersDto(null, "", "", "", "");
    }
}
