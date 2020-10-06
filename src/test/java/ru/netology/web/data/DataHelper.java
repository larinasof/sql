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

    public static User getValidUser() {
        String usersSQL = "select * from users where login = 'vasya';";
        try {
            User user = runner.query(connection, usersSQL, new BeanHandler<>(User.class));
            user.setPassword("qwerty123");
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static User getValidUser2() {
        String usersSQL = "select * from users where login = 'petya';";
        try {
            User user = runner.query(connection, usersSQL, new BeanHandler<>(User.class));
            user.setPassword("123qwerty");
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static User getInvalidPassword()  {
        User user = getValidUser();
        user.setPassword(faker.lorem().characters(9));
        return user;
    }

    public static String getVerificationCode(User user) {
        String authCodeSQL = "select code from auth_codes " +
                "where user_id = ? and created = (" +
                "select max(created) from auth_codes " +
                "where user_id = ?)";
        try { String code = runner.query(connection, authCodeSQL, new ScalarHandler<>(), user.getId(), user.getId());
              return code;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getInvalidVerificationCode() {
        return faker.number().digits(6);
    }

    public static void cleanTables() {
        String deleteUsers = "DELETE FROM users";
        String deleteCards = "DELETE FROM cards";
        String deleteAuthCodes = "DELETE FROM auth_codes";
        String deleteCardTransactions = "DELETE FROM card_transactions";
        try {
            runner.update(connection, deleteCardTransactions);
            runner.update(connection, deleteAuthCodes);
            runner.update(connection, deleteCards);
            runner.update(connection, deleteUsers);
            System.out.println("Tables are clean");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}



