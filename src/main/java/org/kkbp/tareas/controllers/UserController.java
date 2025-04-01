package org.kkbp.tareas.controllers;

import org.kkbp.tareas.models.User;
import org.kkbp.tareas.models.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    private final ArrayList<User> users = new ArrayList<>();

    public boolean validateUsername(String name) {
        for (User user : users) {
            if (Objects.equals(name, user.getName())) {
                return false;
            }
        }
        return true;
    }

    @PostMapping("/adduser")
    public Map<String, String> addUser(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password");
        Role role = Role.valueOf(request.get("role"));
        User user = new User(name, email, password, role);
        if (validateUsername(name)) {
            users.add(user);
            Map<String, String> response = Map.of("message", "Usuario agregado correctamente",
                    "user", user.toString());
            return response;
        } else {
            Map<String, String> response = Map.of("message", "Error al agregar usuario",
                    "cause", "Nombre de usuario ya existe");
            return response;
        }
    }

    @GetMapping("/users")
    public Map<String, String> getUsers() {
        Map<String, String> json = new HashMap<>();
        json.put("users", users.toString());
        return json;
    }

    @GetMapping("/usercount")
    public Map<String, Integer> getUserCount() {
        int studentCount = (int) users.stream().filter(user -> user.getRole() != null && user.getRole() == Role.STUDENT).count();
        int teacherCount = (int) users.stream().filter(user -> user.getRole() != null && user.getRole() == Role.TEACHER).count();
        HashMap<String, Integer> json = new HashMap<>();
        json.put("StudentCount", studentCount);
        json.put("TeacherCount", teacherCount);
        return json;
    }
}
