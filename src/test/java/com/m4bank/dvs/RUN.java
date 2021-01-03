package com.m4bank.dvs;

import com.m4bank.dvs.Tests.Authorisation;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class RUN {

    public static final String[] BROWSERS = new String[]{"Chrome", "Mozzila", "Explorer"};
    public static Settings settings = new Settings();
    public static WebDriver driver;

    public static void main(String[] args) throws FileNotFoundException {

        for (final String browser : BROWSERS) {
            if (settings.getConfig("browser" + browser, "false", "configuration").equals("true")) {

                if (browser.equals("Chrome")) {
                    System.setProperty("webdriver.chrome.driver", settings.getConfig("driverPathChrome", ".\\src\\test\\java\\com\\m4bank\\dvs\\Resources\\chromedriver.exe", "configuration"));
                    driver = new ChromeDriver();
                }

                System.setErr(new PrintStream(new File(browser + ".log")));

                final Result result = JUnitCore.runClasses(Authorisation.class);
                for (final Failure failure : result.getFailures()) {
                    System.out.println(failure.toString());
                    System.err.println(failure.toString());
                }
                System.out.println("\nAll test \"Authorisation\" in " + browser + " success: " + result.wasSuccessful());
                System.err.println("\nAll test \"Authorisation\" in " + browser + " success: " + result.wasSuccessful());
            }
        }

    }
}
