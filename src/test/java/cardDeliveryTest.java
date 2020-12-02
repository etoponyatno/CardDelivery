import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class cardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessIfValidateData() {
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void ifCityInEnglish() {
        $("[placeholder=Город]").setValue("Moscow");
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void ifWrongDate() {
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue("01.12.2020");
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
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(Condition.visible);
    }

    @Test
    void ifWrongPhone() {
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+7926123456");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Телефон указан неверно")).shouldBe(Condition.visible);
    }

    @Test
    void ifNotClickCheckBox() {
        $("[placeholder=Город]").setValue("Москва");
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.hidden);
    }


}
