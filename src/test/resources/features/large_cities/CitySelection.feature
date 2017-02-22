@cityselection
Feature: City Selection

  As a user,
  I want to select one of the large cities,
  so that I can generate reports about the city

  Scenario Outline: Select a city from different continents
    Given I have not selected any city
    When I select continent <continent> country <country> city <city>
    Then I can access <city> city's report
    Examples:
      | continent | country | city    |
      | Europe    | Turkey  | Bursa   |
      | Asia      | China   | Beijing |
