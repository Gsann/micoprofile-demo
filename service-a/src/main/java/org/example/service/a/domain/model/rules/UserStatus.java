package org.example.service.a.domain.model.rules;

import java.util.Arrays;

public enum UserStatus {
    ACTIVE("active"),
    INVALID("invalid"),
    DELETED("deleted");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String target) {
        return Arrays.stream(UserStatus.values()).anyMatch(r -> r.getValue().equals(target));
    }
}
