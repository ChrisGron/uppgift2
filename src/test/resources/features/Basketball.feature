Feature: Basketball

  Scenario Outline: Create account
    Given I am using <browser> as browser
    Given I have a valid <date> of birth
    And I have a valid <firstname> firstname
    And I have a valid <lastname> lastname
    And I have a valid <email> email <both>
    And I have a valid <password> password <pass_confirm>
    And I have <checked_terms> the account confirmation
    And I have <clicked_ethics> the code of conduct
    When I <click> the confirm button
    Then New account is created <result>
    Examples:
      | browser | date      | firstname | lastname | email             | both | password | pass_confirm | checked_terms | clicked_ethics | click | result       |
      | Chrome  | rand      | rand      |          | rand              | yes  | yes      | yes          | yes           | yes            | yes   | lastname     |
      | Firefox | rand      | rand      | rand     | rand              | yes  | yes      |              | yes           | yes            | yes   | passwordConf |
      | Chrome  | 1/6/1985  | rand      | rand     | test@yo.com       | yes  | yes      | yes          | no            | yes            | yes   | terms        |
      | Edge    | rand      | rand      | rand     | rand              | yes  | yes      | yes          | yes           | no             | yes   | ethics       |
      | Edge    | rand      | rand      | rand     | rand              | yes  | qwe123   | qwe321       | yes           | yes            | yes   | passwordConf |
      | Edge    | rand      | rand      | rand     | rand              | yes  | yes      | yes          | yes           | yes            | yes   | created      |
      | Firefox | 06/8/2001 | Benny     | Karlsson | ben_k@yopmail.com | yes  | Qn85-    | Qn85-        | yes           | yes            | yes   | created      |