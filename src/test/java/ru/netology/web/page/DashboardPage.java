package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement buttonReload = $("[data-test-id='action-reload']");


    public DashboardPage() {
        heading.shouldBe(visible).shouldHave(exactText("  Личный кабинет"));
    }

    public TransferPage clickButtonToTransfer(DataHelper.DataCard dataCard) {
        cards.findBy(attribute("data-test-id", dataCard.getDataTestId())).$("button").click();
        return new TransferPage();
    }

    public ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(DataHelper.DataCard dataCard) {
        var text = cards.findBy(text(dataCard.getCardNumber().substring(12))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void clickAtReloadButton() {
        buttonReload.click();
    }
}
