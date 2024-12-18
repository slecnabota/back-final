package lab5.iitu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record LoginDto(
        @NotBlank(message = "Username cannot be empty!")
        String username,

        @NotBlank(message = "Password cannot be empty!")
        String password
) {

}
