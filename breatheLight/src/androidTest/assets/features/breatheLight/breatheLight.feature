Feature: Visit the different screen views in the app, and start the main light pulse activity

  Scenario: Successful activity start with default settings
    Given I start the application
    When I click the start button
    Then I expect the stop button to be visible

  Scenario: Visit the about view via the sidebar menu
    Given I start the application
    When I click the hamburger menu
    And I select the About menu option
    Then I expect the about text to be visible
    Then I expect the view source code button to be visible
    Then I expect the view license button to be visible

  Scenario: Visit the about view and tap the View Source Code button
    Given I start the application
    When I click the hamburger menu
    And I select the About menu option
    And I click the View Source Code button
    Then I expect the about text to be visible

  Scenario: Visit the about view and tap the View License button
    Given I start the application
    When I click the hamburger menu
    And I select the About menu option
    And I click the View License button
    Then I expect the about text to be visible

  Scenario: Visit the share view via the sidebar menu
    Given I start the application
    When I click the hamburger menu
    And I select the Share menu option
    Then I expect to be viewing the Share screen
    Then I expect the thank you message to be displayed on the share screen

  Scenario: Click the share button in the share view
    Given I start the application
    When I click the hamburger menu
    And I select the Share menu option
    And I click the Share button
    Then I expect the share prompt to come up