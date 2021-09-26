package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    private String deliveryDate(int plusDays) {
        LocalDate localDate = LocalDate.now();
        LocalDate deliveryDate = localDate.plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDate.format(formatter);
        return dateText;
    }

    @Test
    void shouldPassed() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Ульяновск");
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(deliveryDate(5));
        form.$("[data-test-id=name] input").setValue("Иван");
        form.$("[data-test-id=phone] input").setValue("+79995554433");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Встреча успешно забронирована на " + deliveryDate(5)));
    }
}
