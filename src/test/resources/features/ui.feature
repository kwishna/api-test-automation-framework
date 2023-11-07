Feature: As a learner, I want to test Selenium 4 New Features

  @ui
  Scenario: Capture screenshot of specific web element:
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User clicks on 'Slow calculator'
    And User takes screenshot of 'Calculator' element
    Then User quits The Browser

  Scenario: Open the new window on the browser
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User opens "https://google.co.in/" in a new window
    And User verify total tabs opened are: 2
    Then User quits The Browser

  Scenario: Open a new tab on the browser
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User opens "https://www.crmpro.com/" in a new tab
    And User verify total tabs opened are: 2
    Then User quits The Browser

  Scenario: Object location
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User print the location of the object
    Then User quits The Browser

