Feature: Customer order beverage
    Customer can order beverage from recipes list
    and paying more than or equal the beverage costs 
    Customer will get the correct change back. 

    Background:
        Given Coffeemaker have 4 recipes

    Scenario: Customers order beverage cost 50 Baht (paying more than beverage cost)
        When Customer select recipe 1 
        And  Customer have money 100 Baht
        Then Customer get 50 Baht back after purchased coffeemaker

    Scenario: Customers order beverage cost 65 Baht (paying equal beverage cost)
        When Customer select recipe 2
        And  Customer have money 65 Baht
        Then Customer get 0 Baht back after purchased coffeemaker

    Scenario: Customers order beverage cost 100 Baht (paying less than beverage cost)
        When Customer select recipe 3
        And  Customer have money 70 Baht
        Then Customer get 70 Baht back after purchased coffeemaker

    Scenario: Customers order beverage cost 75 Baht but not enough inventory (paying enough but not enough inventory for make beverage)
        When Customer select recipe 4
        And  Customer have money 120 Baht
        Then Customer get 120 Baht back after purchased coffeemaker
