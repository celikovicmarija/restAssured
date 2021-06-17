Feature: Manipulate Users's repositories

  Scenario: the authorized user creates a repository
    Given the user is authorized
    When user creates a new repository
    Then the new repository will be created

  Scenario: the authorized user requests his repositories
    Given the user is authorized
    When user asks for list of repositories
    Then the list of repositories will be shown

  Scenario: the authorized user updates a repository
    Given the user is authorized
    When user updates a repository
    Then the repository will be updated

  Scenario: the authorized user requests specific repository
    Given the user is authorized
    When user asks for specific repository
    Then the repository will be shown

  Scenario: the authorized user deletes a repository
    Given the user is authorized
    When user deletes a repository
    Then the repository is deleted