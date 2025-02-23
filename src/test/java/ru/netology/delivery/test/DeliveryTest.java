package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        SelenideElement form = $("form");
        boolean validCity = false;

        while (!validCity) {
            var validUser = DataGenerator.Registration.generateUser("ru");
            eraseAndSendKeys(form.$("[data-test-id=city] input"), validUser.getCity());
            eraseAndSendKeys(form.$("[data-test-id=date] input"), firstMeetingDate);
            eraseAndSendKeys(form.$("[data-test-id=name] input"), validUser.getName()); //Использование Ё вызывает ошибку "используйте только русские буквы"
            eraseAndSendKeys(form.$("[data-test-id=phone] input"), validUser.getPhone());
            if (!form.$("[data-test-id=agreement] input").isSelected()) {
                form.$("[data-test-id=agreement]").click();
            }
            form.$(By.className("button_theme_alfa-on-white")).click();
            validCity = !form.$("[data-test-id=city] .input__sub").text().equals("Доставка в выбранный город недоступна");
        }
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__title").shouldHave(text("Успешно!"));
        eraseAndSendKeys(form.$("[data-test-id=date] input"), secondMeetingDate);
        form.$(By.className("button_theme_alfa-on-white")).click();
        SelenideElement buttonReplan = $("[data-test-id=replan-notification] .button__text");
        buttonReplan.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Перепланировать"));
        buttonReplan.click();
        $("[data-test-id=success-notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
    }

    private void eraseAndSendKeys(SelenideElement element, String data) {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.setValue(data);
    }
}
