package org.example.service.a.domain.services;

import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.a.domain.model.User;
import org.example.service.a.domain.repositoty.IUserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@LoggingInterceptor
@ApplicationScoped
public class UserService {

    @Inject
    private IUserRepository userRepository;

    public User getUser(String email) throws Exception {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NullPointerException();
        }
        return user.get();
    }
}
