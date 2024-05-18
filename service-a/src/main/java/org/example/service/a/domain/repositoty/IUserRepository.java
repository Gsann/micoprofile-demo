package org.example.service.a.domain.repositoty;

import org.example.service.a.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
    public Optional<User> findByEmail(String email) throws Exception;
}
