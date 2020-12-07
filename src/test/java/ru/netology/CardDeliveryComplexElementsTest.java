package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryComplexElementsTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessUseDropdownLists() {
        $("[placeholder=Город]").setValue("Мо");
        $(byText("Москва")).click();
        $("[data-test-id=date] input").click();
        LocalDate meetDate = LocalDate.now().plusWeeks(1);
        String nextWeekDay = meetDate.format(DateTimeFormatter.ofPattern("d"));
        String thisWeekDay = LocalDate.now().format(DateTimeFormatter.ofPattern("d"));
        if (Integer.parseInt(thisWeekDay) > Integer.parseInt(nextWeekDay)) {
            $("div[data-step='1']").click();
        }
        $$(".calendar__day").find(exactText(nextWeekDay)).click();
        $("[data-test-id=name] input").setValue("Петр Филатов");
        $("[data-test-id=phone] input").setValue("+79261234567");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").waitUntil(Condition.visible, 15000).shouldHave(exactText("Успешно! Встреча успешно забронирована на " + meetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }
}
