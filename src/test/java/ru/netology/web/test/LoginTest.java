package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.User;
import ru.netology.web.pages.LoginPage;
import ru.netology.web.pages.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

class LoginTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanAllTables() {
        DataHelper.cleanTables();
    }

    @Test
    void shouldAuthorizeWithCorrectLogin() {
        LoginPage loginPage = new LoginPage();
        User user = DataHelper.getValidUser();
        VerificationPage verificationPage = loginPage.validLogin(user);
        String verificationCode = DataHelper.getVerificationCode(user);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotAuthorizeWithIncorrectPassword() {
        LoginPage loginPage = new LoginPage();
        User invalidLogin = DataHelper.getInvalidPassword();
        loginPage.invalidLogin(invalidLogin);
        loginPage.blockedUser();
    }

    @Test
    void shouldNotAuthorizeWithIncorrectVerificationCode() {
        LoginPage loginPage = new LoginPage();
        User user = DataHelper.getValidUser();
        VerificationPage verificationPage = loginPage.validLogin(user);
        String verificationCode = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(verificationCode);
    }

    @Test
    void shouldNotAuthorizeIfUserBlockedAfterThreeIncorrectPasswords() {
        LoginPage loginPage = new LoginPage();
        User blockedUser = DataHelper.getInvalidPassword();
        loginPage.invalidLogin(blockedUser);
        loginPage.invalidLogin(blockedUser);
        loginPage.invalidLogin(blockedUser);
        loginPage.blockedUser();
    }
}

