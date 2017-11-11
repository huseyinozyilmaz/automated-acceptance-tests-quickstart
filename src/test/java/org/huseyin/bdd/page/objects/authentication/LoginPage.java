package org.huseyin.bdd.page.objects.authentication;

import org.huseyin.bdd.config.TestProperties;
import org.huseyin.bdd.page.objects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends AbstractPage {

    static final Logger Log = LoggerFactory.getLogger(LoginPage.class);
    public static final String PATH = "/login";

    private final By success = By.cssSelector("p.login-welcome-msg");
    private final By failure = By.cssSelector("div.alert-danger");

    @FindBy(id = "simple-form-page-header")
    private WebElement header;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "pwd")
    private WebElement password;

    @FindBy(id = "submit-btn")
    private WebElement submit;

    public LoginPage() {
        super(PATH);
        PageFactory.initElements(getDriver(), this);
    }

    public void login() {
        setText(email, TestProperties.getLoginValidUsername());
        setText(password, TestProperties.getLoginValidPassword());
        submit.submit();
    }

    public void login(String username, String secret) {
        Log.info("Attempting to login with username and password");
        setText(email, username);
        setText(password, secret);
        submit.submit();
    }

    public boolean isNotLoggedIn() {
        return header.getText().equalsIgnoreCase("Login");
    }

    public boolean isLoginFailed() {
        return new WebDriverWait(getDriver(), TestProperties.getSeleniumWaitTimeOutSeconds())
                .until(ExpectedConditions.textToBePresentInElementLocated(failure, "Enter a valid email address and a password to login"));
    }

    public boolean isAuthorised() {
        return new WebDriverWait(getDriver(), TestProperties.getSeleniumWaitTimeOutSeconds())
                .until(ExpectedConditions.textToBePresentInElementLocated(success, "Your login is successful"));
    }
}
