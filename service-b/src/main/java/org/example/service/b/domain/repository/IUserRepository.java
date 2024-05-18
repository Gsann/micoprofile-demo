package org.example.service.b.domain.repository;

import org.example.service.b.domain.model.User;

public interface IUserRepository {
    public User findByEmail(String email) throws Exception;
}
