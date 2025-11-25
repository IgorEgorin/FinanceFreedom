import com.codeborne.selenide.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SmokePositiveTest {

    @BeforeAll
    public static void setup() {
        //
//        Configuration.browser = "chrome";
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        WebDriverRunner.setWebDriver(new ChromeDriver(options));

        System.out.println("Запускаем тест");
        String url = "https://systeme.io/blog/cost-of-online-course";
        System.out.println("Откроем страницу " + url);
        open(url);

    }

    @Test
    public void testSmoke(){

        SelenideElement frameToWait = $(By.xpath("//iframe[@loading='lazy']"));
        SelenideElement btnPopUpClose = $(By.xpath("//button[@data-testid='popup-close-icon']"));

        System.out.println("Ждем появления pop-up");
        frameToWait.shouldBe(clickable, Duration.ofSeconds(60));

        System.out.println("Переключаем фокус на pop-up");
        Selenide.switchTo().frame(frameToWait);

        String btnConfirmMailTxt = $(By.xpath("//button[@font-family='Lato']/div")).getText();
        String textExpected = "I want to receive my copy";

        System.out.println("Проверим, что текст в кнопке '" + btnConfirmMailTxt + "' есть в pop-up");
        Assertions.assertTrue(btnConfirmMailTxt.equals(textExpected));

        System.out.println("Закроем pop-up");
        btnPopUpClose.click();

        frameToWait.shouldNot(Condition.exist);
        System.out.println("Pop-up исчез");

        Selenide.closeWebDriver();
    }
}