@login
Feature: Login form
  As a user
  So that I can become a member
  I want to be able to register (sign-in), log in, and out

  Background:
    Given I go to the home page

  Scenario: Not logged-in view
    When I am not logged in
    Then I see the login form
    And I do not see the logout form

  Scenario Outline: Miss out credentials when logging in
    Given I am not logged in
    When I provide email "<address>"
    And I provide password "<password>"
    And I log in
    Then I see the login form
    And I do not see the logout form
    And I see that the problem is "missed credentials"
    Examples:
      | address | password |
      |         |          |
      | a@b.com |          |
      |         | qwerty   |

  Scenario Outline: Miss out credentials when signing in
    Given I am not logged in
    When I provide email "<address>"
    And I provide password "<password>"
    And I sign up
    Then I see the login form
    And I do not see the logout form
    And I see that the problem is "missed credentials"
    Examples:
      | address | password |
      |         |          |
      | a@b.com |          |
      |         | qwerty   |


  Scenario Outline: Bad credentials when logging in
  The password has to be a reasonable length, and be nothing other than letters and numbers
  ToDo:
  - The UI will prevent the user from clicking either button if the email address is not a valid email
  - So more thought is needed to envisage different tests proper UI, and malicious attempts (bypassing the UI)
    Given I am not logged in
    When I provide email "<address>"
    And I provide password "<password>"
    And I log in
    Then I see the login form
    And I do not see the logout form
    And I see that the problem is "bad credentials"
    Examples:
      | address | password        |
      | a@b.com | a               |
      | a@b.com | qwertyuiopasdfg |
      | a@b.com | qf~             |
      | a@b.com | ~               |
      | a@b.com | qwe rt          |

  Scenario Outline: Bad credentials when signing in
  The password has to be a reasonable length, and be nothing other than letters and numbers
  ToDo:
  - The UI will prevent the user from clicking either button if the email address is not a valid email
  - So more thought is needed to envisage different tests proper UI, and malicious attempts (bypassing the UI)
    Given I am not logged in
    When I provide email "<address>"
    And I provide password "<password>"
    And I sign up
    Then I see the login form
    And I do not see the logout form
    And I see that the problem is "bad credentials"
    Examples:
      | address | password        |
      | a@b.com | a               |
      | a@b.com | qwertyuiopasdfg |
      | a@b.com | qf~             |
      | a@b.com | ~               |
      | a@b.com | qwe rt          |

  Scenario: Successfully sign in
    Given I am not logged in
    When I provide email "unique"
    And I provide password "Arfle"
    And I sign up
    Then I see the logout form
    And I do not see the login form

  Scenario: Log Out
  In order to reduce dependency on existing data, it signs-up first
  Clearly this is not a nice thing to do to the Live database
    Given I am not logged in
    And I provide email "unique"
    And I provide password "farfl3"
    And I sign up
    When I log out
    Then I see the login form
    And I do not see the logout form

  Scenario: Log in
  In order to reduce dependency on existing data, it signs-up first
  Clearly this is not a nice thing to do to the Live database
    Given I am not logged in
    And I provide email "unique"
    And I provide password "P1Pick"
    And I sign up
    And I log out
    When I provide that email address
    And I provide that password
    And I log in
    Then I see the logout form
    And I do not see the login form