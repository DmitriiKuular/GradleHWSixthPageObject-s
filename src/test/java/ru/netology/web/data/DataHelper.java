package ru.netology.web.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class TopUpFirstCard {
        private String amountMoney;
        private String secondCardNumber;
    }
    public static TopUpFirstCard getSecondCardData() {
        return new TopUpFirstCard("0","5559 0000 0000 0002");
    }

    @Value
    public static class TopUpSecondCard {
        private String amountMoney;
        private String firstCardNumber;
    }
    public static TopUpSecondCard getFirstCardData() {
        return new TopUpSecondCard("800","5559 0000 0000 0001");
    }
}
