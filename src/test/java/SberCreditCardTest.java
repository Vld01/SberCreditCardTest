import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import test.settings.SettingsTests;

import static com.codeborne.selenide.Selenide.$;

public class SberCreditCardTest extends SettingsTests {

    @ParameterizedTest
    @CsvFileSource(resources = "/fio.csv")
    public void sberCreditCardTest(String lastName, String firstName, String middleName, String nameCard, String birthDate, String email, String numberPhone) {

        /**
         * 2. Нажать на меню – Карты
         */
        $(By.xpath("//a[@aria-label='Меню  Карты']")).click();

        /**
         * 3. Выбрать подменю – «Дебетовые карты»
         */
        $(By.xpath("//a[text()='Дебетовые карты' and contains(@class, 'link_second')]")).click();

        /**
         * 4. Проверить наличие на странице заголовка – «Дебетовые карты»
         */
        Assertions.assertEquals("Дебетовые карты", $(By.xpath("//h1[contains(@class, 'head')]")).getText(),
                "Заголовок отсутствует/не соответствует требуемому");

        /**
         * 5. Под заголовком из представленных карт найти “Молодёжная карта” и кликнуть на кнопку данной карты “Заказать онлайн”
         */
        $(By.xpath("//h2[contains(text(), 'Молодёжная карта')]/../following::span[contains(text(), 'Заказать онлайн')][1]")).scrollTo().click();

        /**
         * 6. Проверить наличие на странице заголовка – «Молодёжная карта»
         */
        SelenideElement headerYouthCard = $(By.xpath("//h1[contains(@class, 'head')]"));
        Assertions.assertEquals("Молодёжная карта", headerYouthCard.getText(),
                "Заголовок отсутствует/не соответствует требуемому");

        /**
         * 7. Кликнуть на кнопку «Оформить онлайн» под заголовком
         */
        headerYouthCard.$(By.xpath("following::span[contains(text(), 'Оформить онлайн')][1]")).click();

        /**
         * 8. В представленной форме заполнить поля:
         *     • Фамилию, Имя, Отчетво, Имя и фамилия на карте, Дату рождения, E-mail, Мобильный телефон
         *     • Основной документ - не заполняем
         */

        putField("lastName", lastName);
        putField("firstName", firstName);
        putField("middleName", middleName);
        putField("cardName", nameCard);
        putField("birthDate", birthDate);
        putField("email", email);
        putPhoneField(numberPhone);

        /**
         * 9. Проверить, что все поля заполнены правильно
         */
        String actualLastName = getFieldAttributeValue("lastName");
        Assertions.assertEquals( lastName, actualLastName, "Фамилия не совпадает");
        String actualFirstName = getFieldAttributeValue("firstName");
        Assertions.assertEquals( firstName, actualFirstName, "Имя не совпадает");
        String actualMiddleName = getFieldAttributeValue("middleName");
        Assertions.assertEquals( middleName, actualMiddleName, "Отчество не совпадает");
        String actualCardName = getFieldAttributeValue("cardName");
        Assertions.assertEquals( nameCard, actualCardName, "Имя карты не совпадает");
        String actualContactDate = getFieldAttributeValue("birthDate");
        Assertions.assertEquals( birthDate, actualContactDate, "Дата рождения");
        String actualEmail = getFieldAttributeValue("email");
        Assertions.assertEquals( email, actualEmail, "Почта не совпадает");
        String actualPhone = getFieldAttributeValue("phone");
        Assertions.assertEquals("+7 " + numberPhone, actualPhone, "Телефон не совпадает");

        /**
         * 10. Нажать «Далее»
         */
        $(By.xpath("//button/span[contains(text(),'Далее')]")).scrollTo().click();

        /**
         * 11. Проверить, что появилось сообщение именно у незаполненных полей – «Обязательное поле»
         */
        $(By.xpath("//input[@data-name='series']")).scrollTo();

        Assertions.assertEquals("Обязательное поле", errorAlert("series").getText(),
                "Проверка ошибки у серии не была пройдена");
        Assertions.assertEquals("Обязательное поле", errorAlert("number").getText(),
                "Проверка ошибки у номера не была пройдена");
        Assertions.assertEquals("Обязательное поле", errorAlertDate("issueDate").getText(),
                "Проверка ошибки у дыты выдачи не была пройдена");
    }
}
