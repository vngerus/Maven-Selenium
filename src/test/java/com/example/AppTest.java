package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class AppTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Configura la ruta del ChromeDriver
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Angel\\.cache\\selenium\\chromedriver\\win64\\127.0.6533.119\\chromedriver.exe");

        // Configura opciones del navegador
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Inicia el navegador maximizado

        // Inicializa el WebDriver y WebDriverWait
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testPlayVideo() {
        try {
            // Abre Google
            driver.get("https://www.google.com");

            // Utilizar el buscador y usar el primer resultado
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("YouTube");
            searchBox.submit();

            // Esperar y abrir el primer enlace de resultados
            WebElement firstResult = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("h3")));
            firstResult.click();

            // Espera a que se cargue la página de YouTube
            WebElement searchBoxYouTube = wait.until(ExpectedConditions.elementToBeClickable(By.name("search_query")));
            searchBoxYouTube.sendKeys("Chery Chery Lady de Modern Talking");
            searchBoxYouTube.submit();

            // Espera a que se cargue la página de resultados de búsqueda de YouTube
            WebElement firstVideo = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("ytd-video-renderer a#thumbnail")));
            firstVideo.click();

            // Espera a que el video comience a reproducirse
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("video")));

            // Intenta saltar los anuncios si aparecen
            try {
                WebElement skipAdButton = wait
                        .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ytp-ad-skip-button")));
                skipAdButton.click();
            } catch (Exception e) {
                System.out.println("No se encontró el botón de saltar anuncio.");
            }

            // Reproduce el video durante 30 segundos
            Thread.sleep(30000); // 30 segundos

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        // Cierra el navegador si no se ha cerrado durante la prueba
        if (driver != null) {
            driver.quit();
        }
    }
}
