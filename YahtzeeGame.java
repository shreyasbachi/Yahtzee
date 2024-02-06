/*
Author: Shreyas Bachiraju
Date: 5 February 2024
Description: The YahtzeeGame class orchestrates the overall flow of a Yahtzee game, managing player turns, the game state, 
	     and user interactions through a console-based interface. 
	     It initializes the game, conducts rounds, and processes player inputs for rolling dice and scoring.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YahtzeeGame {

	// Main class that runs the Yahtzee game, handling game setup, player turns, and the game loop

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Introduction and explanation of the game's rules.
        System.out.println("Welcome to Yahtzee!\n");
        System.out.println("Hope you have fun! :)\nBy - Shreyas Bachiraju\n");
        System.out.println("Let's go over some rules first!\n");
        System.out.println("Yahtzee involves rolling five dice over 13 rounds to score in predefined categories.");
        System.out.println("Players can roll up to three times per round, choosing after any roll to keep results for scoring. "
        		+ "\nAfter each roll, players can choose to hold specific dice, preventing them from being re-rolled.");
        System.out.println("Each category can only be used once. Categories score by dice numbers (e.g., total of ones),"
        		+ "sets (e.g., three-of-a-kind), \nsequences (e.g., straight), or sum of all dice (chance). ");
        System.out.println("Strategy involves choosing categories wisely based on rolls. "
        		+ "The game ends after all categories are filled; highest total score wins.");
        System.out.println("\nNOTE: This game includes predictive scoring. After rolling, predictive scores for each category are displayed. "
        		+ "\nOnce a category is scored, it's marked with [X], indicating it's no longer available for scoring.\n");
        
        System.out.println("\nEnter number of players (1-6): ");
        int numPlayers = scanner.nextInt();
        List<Player> players = new ArrayList<>();

        // Initialize players
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i));
        }

        // Main game loop for 13 rounds
        for (int round = 1; round <= 13; round++) {
            System.out.println("\nRound #" + round);

            for (Player player : players) {
                System.out.println("\nPlayer #" + player.getNumPlayer() + "'s turn");
                player.resetRollCount();
                player.getDiceCup().resetHolds();

                // Allow up to 3 rolls per turn
                for (int roll = 1; roll <= 3; roll++) {
                    System.out.println("Rolling dice...");
                    player.playerRoll();
                    System.out.println(player.getDiceCup());
                    player.getScoresheet().updateScores(player.getDiceCup().getDiceCupValue());
                    System.out.println(player.getScoresheet());

                    if (roll < 3) {
                        holdOrUnholdDice(scanner, player, "hold");
                        holdOrUnholdDice(scanner, player, "unhold");
                        if (!promptForAnotherRoll(scanner)) break; // Exit rolling loop if player decides not to roll again
                    }
                }

                // Score selection and validation
                chooseScoreCategory(scanner, player);
                System.out.println("\nUpdated Scoresheet:");
                System.out.println(player.getScoresheet().toString());
            }
        }

        // Game over, display final scores
        System.out.println("\nGame over! Final scores:");
        for (Player player : players) {
            System.out.println("Player #" + player.getNumPlayer() + ": " + player.getScoresheet().getTotalScore());
        }

        scanner.close();
    }
    
    // Prompts the player to decide if they want to roll the dice again
    private static boolean promptForAnotherRoll(Scanner scanner) {
        System.out.println("Roll again? (yes/no)");
        String decision = scanner.next();
        return "yes".equalsIgnoreCase(decision);
    }
    
    // Asks the player if they want to hold or unhold any dice and processes the input
    private static void holdOrUnholdDice(Scanner scanner, Player player, String action) {
        System.out.println("Would you like to " + action + " any dice? (yes/no)");
        String decision = scanner.next();
        if ("yes".equalsIgnoreCase(decision)) {
            System.out.println("Enter dice indices to " + action + " (1-5), separated by spaces:");
            scanner.nextLine(); // Clear buffer
            String[] indices = scanner.nextLine().split(" ");
            for (String indexStr : indices) {
                int index = Integer.parseInt(indexStr);
                if ("hold".equals(action)) {
                    player.holdDice(index);
                } else {
                    player.unholdDice(index);
                }
            }
        }
    }

    // Allows the player to choose a scoring category after rolling is done
    private static void chooseScoreCategory(Scanner scanner, Player player) {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.println("Choose a score category by entering its corresponding number:");
            int categoryChoice = scanner.nextInt();
            try {
                player.chooseCategory(categoryChoice);
                validChoice = true; // Break the loop if category selection is successful
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice or category already chosen. Please try again.");
            }
        }
    }
}
