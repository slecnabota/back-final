package lab5.iitu.service;

import lab5.iitu.dto.TasksDto;
import lab5.iitu.entity.Tasks;
import lab5.iitu.entity.Users;
import lab5.iitu.repository.CategoriesRepository;
import lab5.iitu.repository.TasksRepository;
import lab5.iitu.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;
    private final CategoriesRepository categoriesRepository;
    public Page<Tasks> getUserTasks(Users user, Pageable pageable) {
        return tasksRepository.findByUser(user, pageable);
    }

    private String savePhoto(byte[] photoBytes) {
        try {
            Path uploadDir = Paths.get("src/main/resources/static/images/");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String fileName = System.currentTimeMillis() + ".png";
            Path filePath = uploadDir.resolve(fileName);
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(photoBytes);
            }
            return "/images/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save photo", e);
        }
    }

    public Tasks addTask(TasksDto dto) {
        if (dto.title() == null || dto.title().isBlank()) {
            throw new IllegalArgumentException("Title is required.");
        }

        if (dto.dueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }

        var category = categoriesRepository.findById(dto.categoryId()).orElseThrow();
        var user = usersRepository.findById(dto.userId()).orElseThrow();

        return tasksRepository.save(
                Tasks.builder()
                        .title(dto.title())
                        .description(dto.description())
                        .dueDate(dto.dueDate())
                        .status(dto.status())
                        .priority(dto.priority())
                        .category(category)
                        .user(user)
                        .photoPath(savePhoto(dto.photo()))
                        .build()
        );
    }

    public Tasks updateTask(Long taskId, TasksDto dto) {

        Tasks existingTask = tasksRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));

        if (dto.title() == null || dto.title().isBlank()) {
            throw new IllegalArgumentException("Title is required.");
        }
        if (dto.dueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }

        var category = categoriesRepository.findById(dto.categoryId()).orElseThrow();
        var user = usersRepository.findById(dto.userId()).orElseThrow();

        existingTask.setTitle(dto.title());
        existingTask.setDescription(dto.description());
        existingTask.setDueDate(dto.dueDate());
        existingTask.setStatus(dto.status());
        existingTask.setPriority(dto.priority());
        existingTask.setCategory(category);
        existingTask.setUser(user);
        return tasksRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));
        tasksRepository.delete(task);
    }
}
