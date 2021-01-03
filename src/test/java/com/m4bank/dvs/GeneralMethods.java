package com.m4bank.dvs;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.m4bank.dvs.RUN.driver;

public class GeneralMethods {

    public static void waitSpinner() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean exite = false;
        while (!exite) {
            if (driver.findElement(By.id("spinner_overlay_page")).getAttribute("style").contains("display: none;")) {
                exite = true;
            }
        }
    }

    public static void sleep(final int timeInSeconds) {
        try {
            TimeUnit.SECONDS.sleep(timeInSeconds);

        } catch (InterruptedException e) {
            System.out.println("Function sleep ERROR: " + e.getMessage());
        }
    }

    public static void waitElementIsSee(final WebElement bufElement) {
        while (!bufElement.isDisplayed()) {
            sleep(100);
        }
        waitElementClickable(bufElement);
    }

    public static void waitElementClickable(final WebElement bufElement) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(bufElement));
    }

    public static void clickJS(final WebElement bufElement) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", bufElement);
    }

    public static void waitUrl(final String bufUrl) {
        boolean exite = false;
        while (!exite) {
            if (driver.getCurrentUrl().equals(bufUrl)) {
                exite = true;
            }
        }
    }

}
