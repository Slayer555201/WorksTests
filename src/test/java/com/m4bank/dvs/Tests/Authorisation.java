package com.m4bank.dvs.Tests;

import com.m4bank.dvs.GeneralMethods;
import com.m4bank.dvs.PagesInterfaces.LoginPage;
import com.m4bank.dvs.db.ConnectionUtils;
import org.junit.*;
import org.openqa.selenium.By;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import static com.m4bank.dvs.RUN.driver;
import static com.m4bank.dvs.RUN.settings;

//@RunWith(Parametrized.class)
public class Authorisation {

    private static LoginPage loginPage = new LoginPage();
    private static Connection connection;
    private static Statement statement;
    private static ResultSet rs;


    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {

        // Get Connection
        connection = ConnectionUtils.getMyConnection();
        // Create statement
        statement = connection.createStatement();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(settings.getConfig("http", "http://10.31.0.40:8000", "configuration"));
        driver.findElement(By.tagName("html"));
        GeneralMethods.waitSpinner();
    }

    @AfterClass
    public static void closeAll() throws SQLException {
        GeneralMethods.waitSpinner();
        connection.close();
        driver.quit();
    }

    @Before
    @After
    public void setupEachTest() {
        driver.get(driver.getCurrentUrl() + "#/logout");
        GeneralMethods.waitUrl(settings.getConfig("http", "http://10.31.0.40:8000", "configuration") + "/login");
        GeneralMethods.waitSpinner();
    }

    @Test
    public void blockAuth() throws SQLException {
        loginPage.inputLogin(settings.getConfig("userLogin", "user", "configuration"));
        loginPage.inputPassword("blockAuth");
        rs = statement.executeQuery("Select VALUE from M_SETTING WHERE NAME like 'session.incorrectPasswordBlock.to'");
        rs.next();
        GeneralMethods.sleep((rs.getInt("VALUE") * 60) + 1);

        int kol = -1;
        do {
            loginPage.clickLoginButton();
            kol++;
        } while (!driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText().contains("Доступ запрещен"));

        Assert.assertEquals("Доступ запрещен", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());

        GeneralMethods.sleep((rs.getInt("VALUE") * 60) + 1);

        rs = statement.executeQuery("Select VALUE from M_SETTING WHERE NAME like 'session.incorrectPasswordBlock.after'");
        rs.next();

        Assert.assertEquals(rs.getInt("VALUE"), kol);

        loginPage.inputLogin(settings.getConfig("userLogin", "user", "configuration"));
        loginPage.inputPassword(settings.getConfig("userPassword", "1234", "configuration"));
        loginPage.clickLoginButton();
        GeneralMethods.waitSpinner();
        Assert.assertEquals(settings.getConfig("userName", "user", "configuration"), driver.findElement(By.className("user-name")).getText());
    }

    @Test
    public void goodLoginGoodPassword() {
        loginPage.inputLogin(settings.getConfig("userLogin", "user", "configuration"));
        loginPage.inputPassword(settings.getConfig("userPassword", "1234", "configuration"));
        loginPage.clickLoginButton();
        GeneralMethods.waitSpinner();
        Assert.assertEquals(settings.getConfig("userName", "user", "configuration"), driver.findElement(By.className("user-name")).getText());
    }

    @Test
    public void goodLoginNoPassword() {
        loginPage.inputLogin(settings.getConfig("userLogin", "user", "configuration"));
        loginPage.clearPassword();
        loginPage.clickLoginButton();
        Assert.assertEquals("Вы ввели неправильный логин или пароль", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());
    }

    @Test
    public void goodLoginBadPassword() {
        loginPage.inputLogin(settings.getConfig("userLogin", "user", "configuration"));
        loginPage.inputPassword("goodLoginBadPassword");
        loginPage.clickLoginButton();
        Assert.assertEquals("Вы ввели неправильный логин или пароль", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());
    }

    @Test
    public void noLoginNoPassword() {
        loginPage.clearPassword();
        loginPage.clearLogin();
        loginPage.clickLoginButton();
        Assert.assertEquals("Пользователь не найден", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());
    }

    @Test
    public void noLoginWithPassword() {
        loginPage.clearLogin();
        loginPage.inputPassword("noLoginWithPassword");
        loginPage.clickLoginButton();
        Assert.assertEquals("Пользователь не найден", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());

    }

    @Test
    public void badLoginBadPassword() {
        loginPage.inputLogin("badLoginBadPassword");
        loginPage.inputPassword("badLoginBadPassword");
        loginPage.clickLoginButton();
        Assert.assertEquals("Пользователь не найден", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());

    }

    @Test
    public void LDAPgoodLoginNoPassword() {
        loginPage.inputLogin(settings.getConfig("ldapUserLogin", "user", "configuration"));
        loginPage.clearPassword();
        loginPage.clickLoginButton();
        Assert.assertEquals("LDAP отключен", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());
    }

    @Test
    public void LDAPgoodLoginBadPassword() {
        loginPage.inputLogin(settings.getConfig("ldapUserLogin", "user", "configuration"));
        loginPage.inputPassword("goodLoginBadPassword");
        loginPage.clickLoginButton();
        Assert.assertEquals("LDAP отключен", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());
    }

    @Test
    public void LDAPblockLoginWithPassword() {
        loginPage.inputLogin(settings.getConfig("ldapUserBlockLogin", "user", "configuration"));
        loginPage.inputPassword("LDAPblockLoginWithPassword");
        loginPage.clickLoginButton();
        Assert.assertEquals("LDAP отключен", driver.findElement(By.xpath("//span[@class = 'sign-in__error']")).getText());
    }

    @Test
    public void LDAPgoodLoginGoodPassword() {
        loginPage.inputLogin(settings.getConfig("ldapUserLogin", "block", "configuration"));
        loginPage.inputPassword(settings.getConfig("ldapUserPassword", "1234", "configuration"));
        loginPage.clickLoginButton();
        GeneralMethods.waitSpinner();
        Assert.assertEquals(settings.getConfig("ldapUserName", "user", "configuration"), driver.findElement(By.className("user-name")).getText());
    }
}
