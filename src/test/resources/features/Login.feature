@authentication
Feature: Application Login

  As a user,
  I want to securely login to the application,
  so that I can access my personal information

  Scenario: Login with a valid user account
    Given I am not logged in
    When I login with a valid user account
    Then I can access the application

  Scenario: Login with an invalid user account
    Given I am not logged in
    When I login with an invalid user account
    Then I should not be allowed to access the application