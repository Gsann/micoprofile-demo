package org.example.service.a.infra.repository.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.a.domain.model.User;
import org.example.service.a.domain.repositoty.IUserRepository;

import java.sql.*;
import java.util.Optional;

@LoggingInterceptor
@ApplicationScoped
public class UserDao implements IUserRepository {

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

    private static final String FIND_BY_EMAIL = "select * from User where email = ?";

    static {
        try {
            Class.forName("com.google.cloud.spanner.jdbc.JdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<User> findByEmail(String email) throws Exception {

        try (
                Connection connection = DriverManager.getConnection(
                        String.format(
                                "jdbc:cloudspanner://%s/projects/%s/instances/%s/databases/%s?autoConfigEmulator=%s",
                                host, projectId, instanceId, databaseId, emulatorFlg));
                PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL);
        ) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    User user = User.createUser(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("status")
                    );

                    return Optional.of(user);
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
