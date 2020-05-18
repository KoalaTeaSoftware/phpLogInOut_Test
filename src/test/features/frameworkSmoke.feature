@framework
Feature: Framework Smoke Test
  As a test engineer
  So that I can tell if the test framework works
  I want to invoke the SUT and see some sort of result

  Background: See background is run at the correct moments
    Given I say on Stdout "Background steps are executed"
    And I say in the scenario output "Notes can be recorded in the fancy report"

  Scenario: Look at the default resource
    Given I say on Stdout "Steps for scenarios are also invoked"
    When I see the default resource
    Then that resource is retrieved

  Scenario: Fail a step
    Given I say on Stdout "This scenario will fail, and record extra data in the report"
    When I see the default resource
    And the step fails