package org.example.service.b.domain.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.example.service.b.domain.model.rules.UserStatus;

import java.util.HashMap;
import java.util.Map;

@Schema(name = "User")
public class User {

    @Schema(required = true, description = "User id is UUID")
    private String id;
    @Schema(required = true, description = "User name")
    private String name;
    @Schema(required = true, description = "User's email")
    private String email;
    @Schema(required = true, description = "User's status")
    private String status;

    public static User readUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setName(email);
        user.setId(email);
        user.setStatus("active");
        return user;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        if (status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        if (!UserStatus.contains(status)) {
            throw new IllegalArgumentException("Status is not a valid user status");
        }
        this.status = status;
    }

    public boolean isNonActive() {
        return UserStatus.ACTIVE.equals(status);
    }

    public static User createUser(String id, String name, String email, String status) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setStatus(status);
        return user;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("id:").append(this.id).append(",")
                .append("name:").append(this.name).append(",")
                .append("status:").append(this.status).append(",")
                .append("email:").append(this.email)
                .toString();
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("status", this.status);
        return map;
    }
}
