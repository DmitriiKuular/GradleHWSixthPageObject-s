package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    void login() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        var secondCardData = DataHelper.getSecondCardData();
        var successfulTopUp = dashboardPage.transferMoneyToFirstCard(secondCardData);

        Thread.sleep(2000);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getFirstCardData();
        var successfulTopUp = dashboardPage.transferMoneyToSecondCard(firstCardData);

        Thread.sleep(2000);
    }
}
