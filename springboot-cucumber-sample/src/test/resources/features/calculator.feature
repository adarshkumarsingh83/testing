Feature: arithmetic operations

  Scenario: client wants to add two numbers
    When the client calls /calc/add with values 1 and 2
    Then the client receives answer as 3

  Scenario: client wants to subtract two numbers
    When the client calls /calc/sub with values 5 and 3
    Then the client receives answer as 2

  Scenario: client wants to divide two numbers
    When the client calls /calc/div with values 6 and 2
    Then the client receives answer as 3


  Scenario Outline: client wants to multiply two numbers
    When the client calls /calc/mul with values <number1> and <number2>
    Then the client receives answer as <result>

    Examples:
      | number1 | number2 | result |
      | 2       | 3       | 6      |
      | 4       | 6       | 24     |
      | 7       | 8       | 56     |