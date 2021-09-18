package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.CoffeeMaker;
import edu.ncsu.csc326.coffeemaker.Recipe;
import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class CucumberCoffeeMakerTest {
    private CoffeeMaker coffeeMaker;
    private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
    private Integer recipeNumber;
    private Integer customerMoney;
    /**
	 * Use for add all material in the recipe.
	 * 
	 * @param recipe recipe that want to make.
	 * @param name name of recipe.
	 * @param chocolate default of chocolate that the recipe use.
	 * @param coffee default of coffee that the recipe use.
	 * @param milk default of milk that the recipe use.
	 * @param sugar default of sugar that the recipe use.
	 * @param price total price of the recipe.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	private void makeRecipe(Recipe recipe, String name, String chocolate, String coffee, 
	String milk, String sugar, String price) throws RecipeException {
		recipe.setName(name);
		recipe.setAmtChocolate(chocolate);
		recipe.setAmtCoffee(coffee);
		recipe.setAmtMilk(milk);
		recipe.setAmtSugar(sugar);
		recipe.setPrice(price);
	}

    @Given("Coffeemaker have 4 recipes")
    public void setupcoffeemaker() throws RecipeException {
        coffeeMaker = new CoffeeMaker();
        //Set up for r1
		recipe1 = new Recipe();
		makeRecipe(recipe1,"Coffee","0","3","1","1","50");
		coffeeMaker.addRecipe(recipe1);

		//Set up for r2
		recipe2 = new Recipe();
		makeRecipe(recipe2,"Hot Chocolate","4","0","1","1","65");
		coffeeMaker.addRecipe(recipe2);

		//Set up for r3
		recipe3 = new Recipe();
		makeRecipe(recipe3,"Latte","0","3","3","1","100");
        coffeeMaker.addRecipe(recipe3);

		//Set up for r4
		recipe4 = new Recipe();
		makeRecipe(recipe4,"Mocha","20","3","1","1","75");
		coffeeMaker.addRecipe(recipe4);
	}

    @When("Customer select recipe {int}") 
    public void customerSelectRecipe(int recipe) {
        recipeNumber = recipe - 1;
    }
 

    @When("Customer have money {int} Baht")
    public void customerHaveMoney(int monney) {
        customerMoney = monney;
    }

    @Then("Customer get {int} Baht back after purchased coffeemaker")
    public void customerGetMoneyBaht(int monney) {
        assertEquals(monney,coffeeMaker.makeCoffee(recipeNumber,customerMoney));
    }

}
