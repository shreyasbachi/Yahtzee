/*
Author: Shreyas Bachiraju
Date: 5 February 2024
Description: Player encapsulates all aspects of a player's participation in the game, including their dice rolls, 
	     the management of their dice cup, and their scoring decisions. This class handles the logic for rolling dice, 
	     holding specific dice between rolls, and selecting scoring categories on the scoresheet.
*/

public class Player {
	private int numRolls; // Number of rolls the player has made in the current turn
	private DiceCup diceCup; // Dice cup containing the player's dice
	private Scoresheet sheet; // Each player is assigned a score sheet
	private int numPlayer; // Identifier for the player
	
	// Constructor for the player that initializes the player with a specific number, a new score sheet, and a dice cup with 5 dice.
	public Player(int playerNumber) {
		this.numPlayer = playerNumber;
		numRolls=0;
		sheet = new Scoresheet();
		diceCup = new DiceCup(5);
	}
	
	
	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}
	
	// Getters and setters for the player number
	
	public int getNumPlayer() {
		return numPlayer;
	}
	
	// Rolls the dice in the dice cup if the player has not exceeded the maximum roll count (3)
	public int[] playerRoll() {
		if(numRolls<3) {
			diceCup.rollCup();
			numRolls++; 
		}
		return diceCup.getDiceCupValue();
	}
	
	// Setter for each player's score sheet
	public void setScoresheet(Scoresheet newSheet) {
        this.sheet = newSheet;
    }
	
	// Marks a specific die as held, preventing it from being rolled

	public void holdDice(int index){
        diceCup.hold(index);
    }

	// Removes the hold status from a specific die, allowing it to be rolled again.
    public void unholdDice(int index){
        diceCup.unhold(index);
    }
    
    // Chooses the category on the score sheet based on the player's input
    public void chooseCategory(int index){
        sheet.chooseCategory(index);
    }
	
	public void setNumRolls(int numRolls) {
		this.numRolls = numRolls;
	}
	
	// Setters and getters for the number of rolls 
	
	public int getNumRolls() {
		return numRolls;
	}
	
	// toString to print which player's turn it is
	public String toString() {
		return "Player " + getNumPlayer() + "'s turn!\n\n";
	}
	
	// Return's the dice cup containing the player's dice
	public DiceCup getDiceCup() {
        return diceCup;
    }

	// Returns the score sheet tracking the player's scores
    public Scoresheet getScoresheet() {
        return sheet;
    }
    
    // Resets the roll count to 0
    public void resetRollCount() {
    	numRolls = 0;
    }
    
    // Returns the player's current roll count
    public int getRollCount() {
    	return numRolls;
    }

}
