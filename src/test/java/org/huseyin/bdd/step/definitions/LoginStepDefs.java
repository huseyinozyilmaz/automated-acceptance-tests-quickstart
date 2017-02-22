package org.huseyin.bdd.step.definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.huseyin.bdd.page.objects.LoginPage;

import static org.junit.Assert.assertTrue;

public class LoginStepDefs {

    private LoginPage loginPage;

    public LoginStepDefs() {
        this.loginPage = new LoginPage();
    }

    @Given("^I am not logged in$")
    public void i_am_not_logged_in() throws Throwable {
        loginPage.navigate();
        assertTrue(loginPage.isNotLoggedIn());
    }

    @When("^I login with a valid user account$")
    public void i_login_with_a_valid_user_account() throws Throwable {
        loginPage.login();
    }

    @Then("^I can access the application$")
    public void i_can_access_the_application() throws Throwable {
        assertTrue(loginPage.isAuthorised());
    }

    @When("^I login with an invalid user account$")
    public void i_login_with_an_invalid_user_account() throws Throwable {
        loginPage.login("valid@email.com", "abc");
    }

    @Then("^I should not be allowed to access the application$")
    public void i_should_not_be_allowed_to_access_the_application() throws Throwable {
        assertTrue(loginPage.isLoginFailed());
    }
}
