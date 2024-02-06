/*
Author: Shreyas Bachiraju
Date: 5 February 2024
Description: DiceCup acts as a container for multiple dice, enabling collective rolling and individual 
			 manipulation of each die's "held" status. It offers functionality to roll all unheld dice, hold or release specific dice, 
			 and retrieve the current values of the dice.
 */

public class DiceCup {
    private Die[] diceArr; // Array of all the dice
    private int numDice; // Number of dice in the diceCup
    private boolean[] holds; // Boolean array for the holds in the cup

    public DiceCup(int numDice) {
        this.numDice = numDice;
        diceArr = new Die[numDice];
        for (int i = 0; i < numDice; i++) {
            diceArr[i] = new Die(); // Assuming Die() constructor sets default sides
        }
        holds = new boolean[numDice];
        resetHolds();
    }

    public void rollCup() {
        for (int i = 0; i < numDice; i++) {
            if (!holds[i]) {
                diceArr[i].roll();
            }
        }
    }

    public void resetHolds() {
        for (int i = 0; i < holds.length; i++) {
            holds[i] = false;
        }
    }

    public void hold(int x) {
        if (x <= holds.length && x > 0) {
            holds[x - 1] = true;
        }
    }

    public boolean allHeld() {
        for (boolean held : holds) {
            if (!held) {
                return false;
            }
        }
        return true;
    }

    public void unhold(int x) {
        if (x <= holds.length && x > 0) {
            holds[x - 1] = false;
        }
    }

    public int[] getDiceCupValue() {
        int[] result = new int[diceArr.length];
        for (int i = 0; i < diceArr.length; i++) {
            result[i] = diceArr[i].getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder("Roll: ");
        StringBuilder returnString2 = new StringBuilder("Keep?: ");

        for (int i = 0; i < numDice; i++) {
            returnString.append(" ").append((i + 1)).append(":[").append(diceArr[i].getValue()).append("]   ");
        }

        for (int i = 0; i < numDice; i++) {
            returnString2.append((i + 1)).append(":").append(holds[i]).append("  ");
        }

        return returnString + "\n" + returnString2;
    }
}
