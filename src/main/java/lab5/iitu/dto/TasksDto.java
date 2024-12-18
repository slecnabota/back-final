package lab5.iitu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
public record TasksDto(
        Long id,
        String title,
        String description,
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate dueDate,
        String status,
        Integer priority,
        Long userId,
        Long categoryId,
        byte[] photo,
        String photoPath
) {
}
