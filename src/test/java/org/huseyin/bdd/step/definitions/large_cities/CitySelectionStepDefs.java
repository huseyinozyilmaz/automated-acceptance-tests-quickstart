package org.huseyin.bdd.step.definitions.large_cities;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.huseyin.bdd.page.objects.large_cities.CitySelectionPage;

import static org.junit.Assert.assertTrue;

public class CitySelectionStepDefs {

    private final CitySelectionPage citySelectionPage;

    public CitySelectionStepDefs() {
        this.citySelectionPage = new CitySelectionPage();
    }

    @Given("^I have not selected any city$")
    public void i_have_not_selected_any_city() throws Throwable {
        this.citySelectionPage.navigate();
    }

    @When("^I select continent (.*) country (.*) city (.*)$")
    public void i_select_continent(String continent, String country, String city) throws Throwable {
        this.citySelectionPage.selectCity(continent, country, city);
    }

    @Then("^I can access (.*) city's report$")
    public void i_can_access_city_s_report(String city) throws Throwable {
        assertTrue(this.citySelectionPage.isReportShown(city));
    }
}
