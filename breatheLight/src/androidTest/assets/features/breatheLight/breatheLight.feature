Feature: Select options and start activity

  Scenario Outline: Successful activity start with chosen settings
    Given I start the application
    # When I click the dropdown spinner for duration
#    And I select an option from the spinner
#    And I click and drag the start BPM slider
#    And I click and drag the goal BPM slider
#    And I click the colour picker
#    And I click a colour bubble to select a colour
    When I click the start button
    Then I expect the stop button to be visible
    Examples:
      | duration      | start BPM | goal BPM |
      | 20            | 13        | 5        |
      | 30            | 12        | 6        |