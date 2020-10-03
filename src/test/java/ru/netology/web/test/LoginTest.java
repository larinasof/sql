package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.User;
import ru.netology.web.pages.LoginPage;
import ru.netology.web.pages.VerificationPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

class LoginTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldAuthorizeWithCorrectLogin() throws SQLException {
        LoginPage loginPage = new LoginPage();
        User user = DataHelper.getValidUser();
        VerificationPage verificationPage = loginPage.validLogin(user);
        String verificationCode = DataHelper.getVerificationCode(user);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotAuthorizeWithIncorrectPassword() throws SQLException {
        LoginPage loginPage = new LoginPage();
        User invalidUser = DataHelper.getInvalidPassword();
        loginPage.invalidUser(invalidUser);
    }

    @Test
    void shouldNotAuthorizeWithIncorrectVerificationCode() throws SQLException {
        LoginPage loginPage = new LoginPage();
        User user = DataHelper.getValidUser();
        VerificationPage verificationPage = loginPage.validLogin(user);
        String verificationCode = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(verificationCode);
    }

    @Test
    void shouldNotAuthorizeIfUserBlockedAfterThreeIncorrectsPasswords() throws SQLException {
        LoginPage loginPage = new LoginPage();
        for (int i = 0; i < 3; i++) {
            User blockedUser = DataHelper.getInvalidPassword();
            loginPage.blockedUser(blockedUser);
        }
    }
}

