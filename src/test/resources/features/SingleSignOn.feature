@authentication
Feature: Single Sign On

  As a user,
  I want to securely login to the application using active directory account instead of login form,
  so that I can access my personal information

  @manual
  Scenario: Sign in with a valid user account
    Given I am not logged in
    And Single Sign On is enabled
    And I am a valid user in active directory
    When I navigate to home page
    Then I should be able to access the application

  @manual
  Scenario: Sign in with an invalid user account
    Given I am not logged in
    And Single Sign On is enabled
    And I am not a valid user in active directory
    When I navigate to home page
    Then I should not be allowed to access the application