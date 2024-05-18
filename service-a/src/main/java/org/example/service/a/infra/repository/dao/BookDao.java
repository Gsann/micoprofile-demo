package org.example.service.a.infra.repository.dao;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.a.domain.model.Book;
import org.example.service.a.domain.repositoty.IBookRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@LoggingInterceptor
@ApplicationScoped
public class BookDao implements IBookRepository {

    @ConfigProperty(name = "cloud-spanner.host")
    @Inject
    private String host;

    @ConfigProperty(name = "cloud-spanner.id.project")
    @Inject
    private String projectId;

    @ConfigProperty(name = "cloud-spanner.id.instance")
    @Inject
    private String instanceId;

    @ConfigProperty(name = "cloud-spanner.id.database")
    @Inject
    private String databaseId;

    @ConfigProperty(name = "cloud-spanner.emulator.flg")
    @Inject
    private String emulatorFlg;

    private static final String FIND_ALL_QUERY = "select * from book";
    private static final String FIND_BY_ID = "select * from book where id = ?";

    static {
        try {
            Class.forName("com.google.cloud.spanner.jdbc.JdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(
                String.format(
                        "jdbc:cloudspanner://%s/projects/%s/instances/%s/databases/%s?autoConfigEmulator=%s",
                        host, projectId, instanceId, databaseId, emulatorFlg));
                Statement statement = connection.createStatement();
            ) {

            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                while (rs.next()) {

                    Book book = Book.newBook(
                            rs.getString("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    );

                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(String id) {
        try (
                Connection connection = DriverManager.getConnection(
                        String.format(
                                "jdbc:cloudspanner://%s/projects/%s/instances/%s/databases/%s?autoConfigEmulator=%s",
                                host, projectId, instanceId, databaseId, emulatorFlg));
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        ) {

            statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {

                    Book book = Book.newBook(
                            rs.getString("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    );

                    return Optional.of(book);
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
