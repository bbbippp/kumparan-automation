Feature: User post comment on story

  Scenario: User post comment on story
    Given User logged in to kumparan
    When User click one of the Trending Stories
    Then User taken to the stories page that user clicked before
    When User fill the comments field and click Post button
    And User refresh the page
    Then User can see comment is posted
