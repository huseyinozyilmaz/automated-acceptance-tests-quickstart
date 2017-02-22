package org.huseyin.bdd.page.objects;

import org.huseyin.bdd.config.webdriver.SharedDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractPage {

    private String path;
    private final WebDriver driver;
    private final int waitTimeOutSeconds;
    private final String baseUrl;

    public AbstractPage(String path) {
        this.path = path;
        this.driver = new SharedDriver();
        this.waitTimeOutSeconds = 10;
        this.baseUrl = "http://localhost:3000";
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getPath() {
        return path;
    }

    public void navigate(){
        try {
            getDriver().navigate().to(new URL(new URL(this.baseUrl), this.path));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public void selectDropdownByText(WebElement element, String visibleText){
        Select filterSelect = new Select(element);
        waitForDropdownItems(element);
        filterSelect.selectByVisibleText(visibleText);
    }

    private void waitForDropdownItems(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(),waitTimeOutSeconds );
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement find(By locator) {
        try {
            return getDriver().findElement(locator);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(ex.getMessage() + "\n\nPageSource:\n\n" + getDriver().getPageSource());
        }
    }
}
