package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DataHelper.*;

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
    void shouldTransferMoneyFromSecondToFirstCard() {
        var dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);
        var amount = generateValidAmount(firstCardBalance);
        var transferPage = dashboardPage.clickButtonToTransfer(firstCardData);
        transferPage.transferToRightCard(firstCardData);
        dashboardPage = transferPage.validTransferMoney(String.valueOf(amount), secondCardData);
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardData);
        if (expectedFirstCardBalance != actualFirstCardBalance ||
                expectedSecondCardBalance != actualSecondCardBalance) {
            dashboardPage.clickAtReloadButton();
        }
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);
        var amount = generateValidAmount(secondCardBalance);
        var transferPage = dashboardPage.clickButtonToTransfer(secondCardData);
        transferPage.transferToRightCard(secondCardData);
        dashboardPage = transferPage.validTransferMoney(String.valueOf(amount), firstCardData);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardData);
        if (expectedFirstCardBalance != actualFirstCardBalance ||
                expectedSecondCardBalance != actualSecondCardBalance) {
            dashboardPage.clickAtReloadButton();
        }
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldFindErrorNotificationIfWrongCardNumber() {
        var dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);
        var amount = generateValidAmount(firstCardBalance);
        var transferPage = dashboardPage.clickButtonToTransfer(firstCardData);
        transferPage.transferToRightCard(firstCardData);
        transferPage.transferMoney(String.valueOf(amount), getInvalidCardNumber());
        transferPage.findErrorNotification("Ошибка", "Карты с таким номером не существует");
        dashboardPage = transferPage.returnToDashboard();
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardData);
        if (firstCardBalance != actualFirstCardBalance ||
                secondCardBalance != actualSecondCardBalance) {
            dashboardPage.clickAtReloadButton();
        }
        assertEquals(firstCardBalance, actualFirstCardBalance);
        assertEquals(secondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldFindErrorNotificationIfInvalidAmountMoney() {
        var dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getFirstCardData();
        var secondCardData = DataHelper.getSecondCardData();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardData);
        var amount = generateInvalidAmount(firstCardBalance);
        var transferPage = dashboardPage.clickButtonToTransfer(firstCardData);
        transferPage.transferToRightCard(firstCardData);
        transferPage.transferMoney(String.valueOf(amount), secondCardData);
        transferPage.findErrorNotification("Ошибка", "Не хватает средств на карте списания");
        dashboardPage = transferPage.returnToDashboard();
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardData);
        if (firstCardBalance != actualFirstCardBalance ||
                secondCardBalance != actualSecondCardBalance) {
            dashboardPage.clickAtReloadButton();
        }
        assertEquals(firstCardBalance, actualFirstCardBalance);
        assertEquals(secondCardBalance, actualSecondCardBalance);
    }
}
