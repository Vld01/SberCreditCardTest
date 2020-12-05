package test.settings;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SettingsTests {

    @BeforeEach
    void beforeEach() {
        System.out.println("Настраиваем ресурсы если нужно");
        Configuration.startMaximized = true;

        /**
         * 1. Перейти на страницу http://www.sberbank.ru/ru/person
         */
        open("https://www.sberbank.ru/ru/person");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Освобождаем ресурсы если нужно");
        Selenide.closeWindow();
    }

    protected void putField(String name, String text) {
        $(By.xpath(String.format("//input[@data-name='%s']",name))).setValue(text);
    }

    protected void putPhoneField(String numberPhone) {
        String phoneXPath = String.format("//input[@data-name='%s']","phone");
        $(By.xpath(phoneXPath)).scrollTo();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(By.xpath(phoneXPath)).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(By.xpath(phoneXPath)).sendKeys(numberPhone);
    }

    protected String getFieldAttributeValue(String name) {
        return $(By.xpath(String.format("//input[@data-name='%s']",name))).getAttribute("value");
    }

    protected WebElement errorAlert(String dataName) {
        return $(By.xpath(String.format("//input[@data-name='%s']/../div[contains(@class, 'error')]", dataName)));
    }

    protected WebElement errorAlertDate(String dataName) {
        return $(By.xpath(String.format("//input[@data-name='%s']/../../../div[contains(@class, 'error')]", dataName)));
    }

}
