/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}
	
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with positive number
	 * Then we can add in all item.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventoryAllPositive() throws InventoryException {
		coffeeMaker.addInventory("4","7","3","9");
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}


	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying lass than 
	 * the coffee costs
	 * Then it will give your money back with out any change.
	 */
	@Test
	public void testMakeCoffeeWithLackMoney() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(30, coffeeMaker.makeCoffee(0, 30));
		assertEquals(-30, coffeeMaker.makeCoffee(0, -30));
	}

	/**
	 * Given a coffee maker with new recipe but only three recipes 
	 * may be added to the CoffeeMaker.
	 */
	@Test
	public void testAddRecipe() {
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertTrue(coffeeMaker.addRecipe(recipe2));
		assertTrue(coffeeMaker.addRecipe(recipe3));
		assertEquals(false,coffeeMaker.addRecipe(recipe4));
	}

	/**
	 * Given a coffee maker with new recipe but we cant added 
	 * the same recipes to the CoffeeMaker.
	 */
	@Test
	public void testAddSameRecipe() {
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertEquals(false,coffeeMaker.addRecipe(recipe1));
		Recipe recipe5 = new Recipe();
		recipe5.setName("Coffee");
		assertEquals(false,coffeeMaker.addRecipe(recipe5));
	}

	/**
	 * Given a coffee maker may be deleted any recipes 
	 * from the recipes in CoffeeMaker.
	 */
	@Test
	public void testDeleteRecipe() {
		coffeeMaker.addRecipe(recipe4);
		assertEquals("Hot Chocolate", coffeeMaker.deleteRecipe(0));
		assertEquals(null, coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Given a coffee maker may be deleted any recipes 
	 * from the recipes in CoffeeMaker
	 * it will return Null when you delete recipe doesn't have
	 * in recipe.
	 */
	@Test
	public void testDeleteNullRecipe() {
		coffeeMaker.addRecipe(recipe4);
		assertEquals(null, coffeeMaker.deleteRecipe(1));
	}

	/**
	 *  Given a coffee maker may be edited any recipes 
	 * from the recipe in CoffeeMaker but any recipes that
	 * edited it will not change name of recipe.
	 */
	@Test
	public void testEditRecipe() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		assertEquals("Coffee", coffeeMaker.editRecipe(0,recipe2));
		assertEquals("Coffee", coffeeMaker.getRecipes()[0].getName());
		assertEquals("Mocha", coffeeMaker.getRecipes()[1].getName());
	}

	/**
	 * Given a coffee maker may be edited any recipes 
	 * from the recipe in CoffeeMaker but any recipes that
	 * edited it will not change name of recipe.
	 * it will return Null when you edit recipe doesn't have
	 * in recipe.
	 */
	@Test
	public void testEditNullRecipe() {
		assertEquals(null, coffeeMaker.editRecipe(0,recipe1));
	}

	/**
	 * Given a coffee maker can choose any recpies in list 
	 * of recpies in coffee maker
	 */
	@Test
	public void testGetRecipes() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
		assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
		assertEquals("Coffee", coffeeMaker.getRecipes()[0].getName());
		assertEquals("Mocha", coffeeMaker.getRecipes()[1].getName());
		assertEquals(0, coffeeMaker.getRecipes()[0].getAmtChocolate());
		assertEquals(3, coffeeMaker.getRecipes()[0].getAmtCoffee());
		assertEquals(1, coffeeMaker.getRecipes()[0].getAmtMilk());
		assertEquals(1, coffeeMaker.getRecipes()[0].getAmtSugar());
		assertEquals(50, coffeeMaker.getRecipes()[0].getPrice());
	}

	/**
	 * Given a coffee maker can choose any recpies in list 
	 * of recpies in coffee maker
	 * it will return Null when you choose any recipes doesn't have
	 * in recipe.
	 */
	@Test
	public void testGetNullRecipes() {
		assertEquals(null, coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Given a coffee maker can check your The units of each item in the
	 * inventory are displayed  
	 */
	@Test
	public void testCheckInventory() {
		String temp = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n";
		assertEquals(temp, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker can check your The units of each item in the
	 * inventory are displayed 
	 * when any item in inventory get added  The units of each item need in the
	 * inventory need to change.
	 */
	@Test
	public void testCheckInventoryAfterAdd() throws InventoryException {
		coffeeMaker.addInventory("1","2","0","4");
		String temp = "Coffee: 16\nMilk: 17\nSugar: 15\nChocolate: 19\n";
		assertEquals(temp, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker can check your The units of each item in the
	 * inventory are displayed 
	 * when any item in inventory get used when make the coffee 
	 * The units of each item need in the
	 * inventory need to change or reduce.
	 */
	@Test
	public void testCheckInventoryAfterMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(100, coffeeMaker.makeCoffee(0, 150));
		String temp = "Coffee: 12\nMilk: 14\nSugar: 14\nChocolate: 15\n";
		assertEquals(temp, coffeeMaker.checkInventory());
		assertEquals(30, coffeeMaker.makeCoffee(0, 30));
		assertEquals(temp, coffeeMaker.checkInventory());
	}


}
