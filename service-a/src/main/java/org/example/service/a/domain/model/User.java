package org.example.service.a.domain.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.example.service.a.domain.model.rules.UserStatus;

@Schema(name="User")
public class User {

    @Schema(required = true, description = "User id is UUID")
    private String id;
    @Schema(required = true, description = "User name")
    private String name;
    @Schema(required = true, description = "User's email")
    private String email;
    @Schema(required = true, description = "User's status")
    private String status;

    public User() {}

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
}
