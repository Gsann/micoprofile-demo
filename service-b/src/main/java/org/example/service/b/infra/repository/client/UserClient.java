package org.example.service.b.infra.repository.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.b.domain.model.User;
import org.example.service.b.domain.repository.IUserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

@LoggingInterceptor
@ApplicationScoped
public class UserClient implements IUserRepository {

    @ConfigProperty(name = "service-a.url")
    @Inject
    private String target;

    private static final String USER_URI = "/user/";

    private Client client;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @Override
    public User findByEmail(String email) {
        try {
            var response = client.target(target + USER_URI + email).request().get();

            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new RuntimeException();
            }

            return new ObjectMapper().readValue(response.readEntity(String.class), User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
