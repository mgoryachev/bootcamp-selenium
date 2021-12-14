Feature: Working with cart
  Scenario: Add and remove product in cart
    Given open website litecart
    When We add 3 products to cart
    And open cart
    And remove the products from the cart