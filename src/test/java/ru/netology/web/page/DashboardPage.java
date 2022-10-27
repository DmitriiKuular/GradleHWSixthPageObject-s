package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement buttonFirstCard =
            $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id='action-deposit']");
    private SelenideElement buttonSecondCard =
            $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id='action-deposit']");
    private SelenideElement buttonReload = $("[data-test-id='action-reload']");
    private SelenideElement amountMoneyIn = $("[data-test-id='amount'] input");
    private SelenideElement topUpFromCard = $("[data-test-id='from'] input");
    private SelenideElement topUpToCard = $("[data-test-id='to']");
    private SelenideElement topUpButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement errorTitle = $("[data-test-id='error-notification'] .notification__title");
    private SelenideElement errorContent = $("[data-test-id='error-notification'] .notification__content");

//    .heading "Ваши карты"
//    .list__item
//    "[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id='action-deposit']"
//    "[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id='action-deposit']"
//    "[data-test-id='action-reload']"

//    "[data-test-id='amount'] input"
//    "[data-test-id='from'] input"
//    "[data-test-id='to'] "
//    кнопки
//    "[data-test-id='action-transfer']" пополнить
//    "[data-test-id='action-cancel']"  отмена

//    Ошибка
//    "[data-test-id='error-notification'] .notification__title"
//    "[data-test-id='error-notification'] .notification__content" ("Ошибка! " + "Произошла ошибка")

    public DashboardPage() {
        heading.shouldBe(visible).shouldHave(exactText("  Личный кабинет"));
    }

    public DashboardPage transferMoneyToFirstCard(DataHelper.TopUpFirstCard data) {
        buttonFirstCard.click();
        amountMoneyIn.setValue(data.getAmountMoney());
        topUpFromCard.setValue(data.getSecondCardNumber());
        topUpButton.click();
        return new DashboardPage();
    }

    public DashboardPage transferMoneyToSecondCard(DataHelper.TopUpSecondCard data) {
        buttonSecondCard.click();
        amountMoneyIn.setValue(data.getAmountMoney());
        topUpFromCard.setValue(data.getFirstCardNumber());
        topUpButton.click();
        return new DashboardPage();
    }

    public ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);

    }
    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
