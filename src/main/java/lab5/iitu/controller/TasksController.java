package lab5.iitu.controller;

import jakarta.validation.Valid;
import lab5.iitu.dto.TasksDto;
import lab5.iitu.entity.Tasks;
import lab5.iitu.entity.Users;
import lab5.iitu.service.TasksService;
import lab5.iitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private UserService userService;

    @Autowired
    private TasksService tasksService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Tasks>> getTasksByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page) {
        Users user = userService.getUserById(userId);
        Pageable pageable = PageRequest.of(page, 10, Sort.by("dueDate").ascending());
        Page<Tasks> tasksPage = tasksService.getUserTasks(user, pageable);

        return ResponseEntity.ok(tasksPage);
    }

    @PostMapping
    public ResponseEntity<Tasks> addTask(@Valid @RequestBody TasksDto dto) {
        Tasks createdTask = tasksService.addTask(dto);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Tasks> updateTask(@PathVariable Long taskId, @RequestBody TasksDto dto) {
        Tasks updatedTask = tasksService.updateTask(taskId, dto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        tasksService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
