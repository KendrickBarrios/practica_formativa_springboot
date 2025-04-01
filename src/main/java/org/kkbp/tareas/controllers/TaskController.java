package org.kkbp.tareas.controllers;

import org.kkbp.tareas.models.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TaskController {
    private final ArrayList<Task> tasks = new ArrayList<Task>();

    @PostMapping("/addtask")
    public Map<String, String> addTask(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String description = request.get("description");
        String status = request.get("status");
        Task task = new Task(name, description, status);
        tasks.add(task);
        Map<String, String> response = Map.of(
                "message", "Tarea agregada correctamente",
                "task", task.toString());
    return response;
    }

    @GetMapping("/tasks")
    public Map<String, String> getTasks() {
        Map<String, String> json = new HashMap<>();
        json.put("tasks", tasks.toString());
        return json;
    }
}
