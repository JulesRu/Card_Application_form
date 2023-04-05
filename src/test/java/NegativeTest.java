import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFailWhenEmptyName() {

        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79603746590");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenNameLatin() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ann Shirley");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79603746590");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenNameSpecialChar() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("*&$#?");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79603746590");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenEmptyPhone() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Виктория Степанова");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneHasLessNumbers() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Виктория Степанова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+796037465");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneHasLetters() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Виктория Степанова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Jess");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneHasSpecialChar() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Виктория Степанова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+$%*&)(?|((");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenEmptyCheckbox() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Виктория Степанова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79603746590");
        driver.findElement(By.cssSelector("button")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }
}
