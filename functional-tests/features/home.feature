Feature: As a user of bruuser
  I want to be able to get to the web app

  Scenario: Getting to home page
    Given the user has reached home page
    And the user fills user form
    When the user clicks send button in user form
    Then the table of user has to have one row