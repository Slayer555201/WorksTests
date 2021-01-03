package com.m4bank.dvs.PagesInterfaces;

import com.m4bank.dvs.GeneralMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.m4bank.dvs.RUN.driver;

public class LoginPage {

    @FindBy(name = "login")
    private WebElement loginField;
    @FindBy(name = "password")
    private WebElement passwordField;
    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public void inputLogin(final String login) {
        loginField.clear();
        loginField.sendKeys(login);
    }

    public void inputPassword(final String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        GeneralMethods.waitElementIsSee(loginButton);
        GeneralMethods.clickJS(loginButton);
        GeneralMethods.sleep(1);
    }

    public void clearLogin() {
        loginField.clear();
    }

    public void clearPassword() {
        passwordField.clear();
    }
}
