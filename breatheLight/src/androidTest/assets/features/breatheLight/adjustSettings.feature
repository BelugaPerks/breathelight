Feature: Adjust settings in the main screen and see that they have been changed

#  Scenario: Change settings to minimum for target and start BPM
#    Given I start the application
#    When I drag the start BPM slider all the way to the left
#    And I drag the target BPM slider all the way to the left
#    Then I expect the starting breaths/min display to show 8
#    Then I expect the target breaths/min display to show 4

  Scenario Outline: Change settings to maximum for target and start BPM
    Given I start the application
    When I drag the start BPM slider to position <Start_BPM_Seekbar_Position>
    And I drag the target BPM slider to position <Target_BPM_Seekbar_Position>
    Then I expect the starting breaths per min display to show <Start_BPM_Value>
    Then I expect the target breaths per min display to show <Target_BPM_Value>
    Examples:
    | Start_BPM_Seekbar_Position | Start_BPM_Value | Target_BPM_Seekbar_Position | Target_BPM_Value |
    | 0                          | 8               | 0                           | 4                |
    | 6                          | 14              | 4                           | 8                |
    | 5                          | 13              | 3                           | 7                |
    | 4                          | 12              | 2                           | 6                |
    | 3                          | 11              | 1                           | 5                |
    | 2                          | 10              | 2                           | 6                |
    | 1                          | 9               | 3                           | 7                |

  Scenario Outline: Change settings for the duration
    Given I start the application
    When I tap on the duration dropdown picker
    And I tap on the <Duration> minutes option
    Then I expect the duration display to show <Duration> minutes
    Examples:
    | Duration |
    | 60       |
    | 45       |
    | 30       |
    | 20       |
    | 10       |

  Scenario Outline: Change the light colour
    Given I start the application
    When I tap the colour picker button
    And I select "<Colour>" from the buttons that appear
    Then I expect "<Colour>" to be selected
    Examples:
      | Colour      |
      | red         |
      | green       |
      | white       |
      | blue        |