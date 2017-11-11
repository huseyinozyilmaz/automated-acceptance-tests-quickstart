package org.huseyin.bdd.page.objects.large_cities;

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

public class CitySelectionPage extends AbstractPage {

    static final Logger Log = LoggerFactory.getLogger(CitySelectionPage.class);

    public static final String PATH = "/cities";

    private final By selectedCityHeader = By.cssSelector("h1.city");

    @FindBy(id = "continent")
    private WebElement continentSelect;

    @FindBy(id = "country")
    private WebElement countrySelect;

    @FindBy(id = "city")
    private WebElement citySelect;

    @FindBy(id = "btn-city-select")
    private WebElement select;

    public CitySelectionPage() {
        super(PATH);
        PageFactory.initElements(getDriver(), this);
    }

    public void selectCity(String continent, String country, String city) {
        Log.info("Selecting City {} > {} > {}", continent, country, city);
        new WebDriverWait(getDriver(), TestProperties.getSeleniumWaitTimeOutSeconds())
                .until(ExpectedConditions.elementToBeClickable(continentSelect));
        selectDropdownByText(continentSelect, continent);
        new WebDriverWait(getDriver(), TestProperties.getSeleniumWaitTimeOutSeconds())
                .until(ExpectedConditions.elementToBeClickable(countrySelect));
        selectDropdownByText(countrySelect, country);
        new WebDriverWait(getDriver(), TestProperties.getSeleniumWaitTimeOutSeconds())
                .until(ExpectedConditions.elementToBeClickable(citySelect));
        selectDropdownByText(citySelect, city);
        select.click();
    }

    public boolean isReportShown(String city) {
        return new WebDriverWait(getDriver(), TestProperties.getSeleniumWaitTimeOutSeconds())
                .until(ExpectedConditions.textToBePresentInElementLocated(selectedCityHeader, city));
    }
}
