package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessIfValidateData() {
        $("[placeholder=Город]").setValue("Москва");
        String testDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(testDate);
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
    }

    @Test
    void ifCityInEnglish() {
        $("[placeholder=Город]").setValue("Moscow");
        String testDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(testDate);
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void ifWrongDate() {
        $("[placeholder=Город]").setValue("Москва");
        String testDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(testDate);
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible);
    }

    @Test
    void ifNameInEnglish() {
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=name] input").setValue("Petr Filatov");
        String testDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(testDate);
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(Condition.visible);
    }

    @Test
    void ifWrongPhone() {
        $("[placeholder=Город]").setValue("Москва");
        String testDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(testDate);
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+7926123456");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Телефон указан неверно")).shouldBe(Condition.visible);
    }

    @Test
    void ifNotClickCheckBox() {
        $("[placeholder=Город]").setValue("Москва");
        String testDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(testDate);
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(Condition.cssClass("input_invalid"));
    }
}
