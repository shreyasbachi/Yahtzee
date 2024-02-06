/*
Author: Shreyas Bachiraju
Date: 5 February 2024
Description: This class models a single die with a customizable number of sides, capable of rolling to generate a random value within its range. 
	     It provides methods to get the current face value, set a specific value, and determine the number of sides.
*/

import java.util.Random;

public class Die {
	private int sides;
	private int value;
	private Random randgen;
	
	// Default constructor for a single die that can be rolled to produce a random value
	public Die(){
		sides = 6;
		value = -1;
		randgen = new Random();
	}
	
	// Parameterized constructor for creating a die with 'x' number of sides
	public Die(int sides) {
		this.sides = sides;
		value = -1;
		randgen = new Random();
	}
	
	// Rolling the dice
	public void roll() {
		value = randgen.nextInt(sides) + 1;
	}
	
	// Getter for returning the face value
	public int getValue() {
		return value;
	}
	
	// Setter for setting the face value
	public void setValue(int val) {
		this.value = val;
	}
	
	// Getter for returning sides of the die
	public int getSides() {
		return sides;
	}
	
	// Returns a string representation of the die, indicating the number of sides it has
	public String toString(int sides) {
		String dieStr = "The die has " + sides + ".";
		return dieStr;
	}
}
