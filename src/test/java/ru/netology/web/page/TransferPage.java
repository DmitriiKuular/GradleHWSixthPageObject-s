package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement headingTransfer = $(byText("Пополнение карты"));
    private SelenideElement amountMoneyIn = $("[data-test-id='amount'] input");
    private SelenideElement topUpFromCard = $("[data-test-id='from'] input");
    private SelenideElement topUpToCard = $("[data-test-id=to] .input__control");
    private SelenideElement topUpButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement errorTitle = $("[data-test-id='error-notification'] .notification__title");
    private SelenideElement errorContent = $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
        headingTransfer.shouldBe(visible);
    }

    public void transferMoney(String amountMoney, DataHelper.DataCard dataCard) {
        amountMoneyIn.setValue(amountMoney);
        topUpFromCard.setValue(dataCard.getCardNumber());
        topUpButton.click();
    }

    public void transferToRightCard(DataHelper.DataCard dataCard) {
        String numberCardTo = dataCard.getCardNumber().substring(12);
        topUpToCard.shouldHave(value("**** **** **** " + numberCardTo)).shouldBe(visible);
    }

    public DashboardPage validTransferMoney(String amountMoney, DataHelper.DataCard data) {
        transferMoney(amountMoney, data);
        return new DashboardPage();
    }

    public void findErrorNotification(String expectedTitleText, String expectedContentText) {
        errorTitle.shouldHave(exactText(expectedTitleText), Duration.ofSeconds(15)).shouldBe(visible);
        errorContent.shouldHave(exactText(expectedContentText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public DashboardPage returnToDashboard () {
        cancelButton.click();
        return new DashboardPage();
    }
}
