package ru.netology.web.data;

import com.github.javafaker.Faker;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataHelper {
    private static QueryRunner runner = new QueryRunner();
    private static Faker faker = new Faker(new Locale("en"));
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/database", "user", "pass");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User getValidUser() throws SQLException {
        String usersSQL = "select * from users where login = 'vasya';";
        User user = runner.query(connection, usersSQL, new BeanHandler<>(User.class));
        user.setPassword("qwerty123");
        return user;
    }

    public static User getValidUser2() throws SQLException {
        String usersSQL = "select * from users where login = 'petya';";
        User user = runner.query(connection, usersSQL, new BeanHandler<>(User.class));
        user.setPassword("123qwerty");
        return user;
    }

    public static User getInvalidPassword() throws SQLException {
        User user = getValidUser();
        user.setPassword(faker.lorem().characters(9));
        return user;
    }

    public static String getVerificationCode(User user) throws SQLException {
        String authCodeSQL = "select code from auth_codes " +
                "where user_id = ? and created = (" +
                "select max(created) from auth_codes " +
                "where user_id = ?)";
        String code = runner.query(connection, authCodeSQL, new ScalarHandler<>(), user.getId(), user.getId());
        return code;
    }

    public static String getInvalidVerificationCode() {
        return faker.number().digits(6);
    }
}



