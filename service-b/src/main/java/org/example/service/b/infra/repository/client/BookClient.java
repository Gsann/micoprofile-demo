package org.example.service.b.infra.repository.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.b.domain.model.Book;
import org.example.service.b.domain.model.User;
import org.example.service.b.domain.repository.IBookRepository;

@LoggingInterceptor
@ApplicationScoped
public class BookClient implements IBookRepository {

    @ConfigProperty(name = "service-a.url")
    @Inject
    private String target;

    private static final String BOOK_URI = "/book/";

    private Client client;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }


    @Override
    public Book findById(String bookId) {
        try {
            var response = client.target(target + BOOK_URI + bookId).request().get();

            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new RuntimeException();
            }

            return new ObjectMapper().readValue(response.readEntity(String.class), Book.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
