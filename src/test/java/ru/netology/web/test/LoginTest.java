package ru.netology.web.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.User;
import ru.netology.web.pages.LoginPage;
import ru.netology.web.pages.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

class LoginTest {
    @BeforeAll
    static void CleanAllTables() {
        DataHelper.cleanTables();
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
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
        User invalidUser = DataHelper.getInvalidPassword();
        loginPage.invalidUser(invalidUser);
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
        loginPage.blockedUser(blockedUser);
    }
}

