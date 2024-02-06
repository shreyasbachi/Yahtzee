import java.util.HashMap;
import java.util.*;

public class Scoresheet {
	
	// Manages the scoring for a Yahtzee game, keeping track of scores across various categories.
	private HashMap<String, Integer> scores = new HashMap<String,Integer>(); // HashMap to store the scores
	private boolean[] categoryChosen;
	private int totalScore;
	private String[] categories = {"Aces", "Twos", "Threes", "Fours", "Fives", "Sixes", "ThreeOfAKind", 
			"FourOfAKind", "FullHouse", "SmallStraight", "LargeStraight","Yahtzee","Chance"};
	 
	
	// Default constructor initializes the score sheet with all categories set to 0 and marked as not chosen

	public Scoresheet() {
	    totalScore = 0;
	    categoryChosen = new boolean[categories.length];
	    Arrays.fill(categoryChosen, false);
	    for(String category:categories) {
	    	scores.put(category,0);
	    }
	}
   
    // Method to calculate occurrences of upper section	
    public int calcUpper(int[] d, int x) {
    	int count = 0;
    	for(int i = 0; i<d.length; i++) {
    		if(d[i]==x) {
    			count++;
    		}
    	}	
    	return count;
    }
    
    // Setters for Upper Section
    public void setAces(int[] d) { 
    	scores.put("Aces", calcUpper(d, 1));
    }
    
    public void setTwos(int[]  d) { 
    	scores.put("Twos", 2*(calcUpper(d, 2)));
    }
    
    public void setThrees(int[]  d) { 
    	scores.put("Threes", 3*(calcUpper(d, 3)));
    }
    
    public void setFours(int[] d) { 
    	scores.put("Fours" ,4*(calcUpper(d, 4)));
    }
    
    public void setFives(int[] d) { 
    	scores.put("Fives", 5*(calcUpper(d, 5)));
    }
    
    public void setSixes(int[] d) { 
    	scores.put("Sixes",6*(calcUpper(d, 6)));
    }

    // Setters for Lower Section
    
    
    // Method to calculate occurrences of a number in a given array, both of which are passed in as parameters 
    public int ofAKind(int[] ofAKind_arr, int y) {
    	int countOfAKind = 0;
    	HashMap<Integer, Integer> ofAKindHash = new HashMap<>();
    	
    	for (int dieValue : ofAKind_arr) {
    		ofAKindHash.put(dieValue, ofAKindHash.getOrDefault(dieValue, 0) + 1);
        }
    	
    	for (int key : ofAKindHash.keySet()) {
            if (ofAKindHash.get(key) == y) {
                for (int dieValue : ofAKind_arr) {
                	countOfAKind+= dieValue; 
                }
            }
        }
    	return countOfAKind;
    }
    
    // Calls the above method
    public void setThreeOfAKind(int[] d) {
        scores.put("ThreeOfAKind", ofAKind(d, 3));
    }
    
    public void setFourOfAKind(int[] d) {
    	scores.put("FourOfAKind" , ofAKind(d, 4));
    }
    
    // Setter for Full House - checks for three occurrences and a pair  
    public void setFullHouse(int[] fullHouse_arr) {
    	boolean flag = false;
    	boolean flag2 = false;
    	HashMap<Integer, Integer> fullHouseHash = new HashMap<>();
    
    	for (int dieValue : fullHouse_arr) {
    		fullHouseHash.put(dieValue, fullHouseHash.getOrDefault(dieValue, 0) + 1);
        }
    	
    	for (int key : fullHouseHash.keySet()) {
            if (fullHouseHash.get(key) == 3) {
            	flag = true;
            }
    	}
    	
    	for (int key : fullHouseHash.keySet()) {
            if (fullHouseHash.get(key) == 2) {
            	flag2 = true;
            }
    	}
    	
    	// If both conditions are met, the updated score is put into the HashMap
    	if(flag && flag2) {
    		scores.put("FullHouse", 25);
    	}
    	else {
    		scores.put("FullHouse", 0);
    	}
 
    }
    
    // Setter for small straight - sequences of 4
    public void setSmallStraight(int[] ss_arr) {
    	Arrays.sort(ss_arr);
    	int count_ss = 1;
    	for(int i = 0; i<ss_arr.length-1; i++) {
    		if(ss_arr[i]+1==ss_arr[i+1]) {
    			count_ss++;
    		}
    	}
    	if(count_ss>=4) {
    		scores.put("SmallStraight", 25);
    	}
    	else {
    		scores.put("SmallStraigth", 0);
    	}
    }
    		
    // Setter for large straight - sequences of 5
    public void setLargeStraight(int[] ls_arr) {
        Arrays.sort(ls_arr);
        boolean isLargeStraight = true;
        for (int i = 0; i < ls_arr.length - 1; i++) {
            if (ls_arr[i] + 1 != ls_arr[i + 1]) isLargeStraight = false;
        }
        scores.put("LargeStraight", isLargeStraight ? 40 : 0); // Corrected key
    }
    
    // Setter for Yahtzee
    public void setYahtzee(int[] yahtzee_Arr) {
    	HashMap<Integer, Integer> yahtzeeHash = new HashMap<>();
  
    	for (int dieValue : yahtzee_Arr) {
    		yahtzeeHash.put(dieValue, yahtzeeHash.getOrDefault(dieValue, 0) + 1);
        }
    	
        boolean yahtzeeFound = false;
    	for (int key : yahtzeeHash.keySet()) {
            if (yahtzeeHash.get(key) == 5) {
            	yahtzeeFound = true;
                break;
            }
        }
    	scores.put("Yahtzee", yahtzeeFound ? 50 : 0);
    }
    
    // Setter for chance - sum of all dice
    public void setChance(int[] chanceArr) {
        int chance = 0; // Initialize to 0 at method start
        for (int value : chanceArr) {
            chance += value;
        }
        scores.put("Chance", chance);
    }

    
    public int calcTotalScore() {        
        return totalScore;
    }

    // Method that takes in the input as the parameter to check which category needs to be scored
    public void chooseCategory(int input) {
    	if(input>0 && input<=categories.length && !categoryChosen[input - 1]) {
    		String category = categories[input - 1];
            if (scores.get(category) != null) { // Check if there's a potential score
                totalScore += scores.get(category); // Add it to the totalScore
                categoryChosen[input - 1] = true; // Mark the category as chosen
            }
        } 
    	else {
            System.out.println("Invalid input or category already chosen.");
        }
    }
    
    // Getter for total score
    public int getTotalScore() {
        return totalScore;
    }
    
    // Updates the scores
    public void updateScores(int[] values) {
    	setAces(values);
    	setTwos(values);
        setThrees(values);
        setFours(values);
        setFives(values);
        setSixes(values);
        setThreeOfAKind(values);
        setFourOfAKind(values);
        setFullHouse(values);
        setSmallStraight(values);
        setLargeStraight(values);
        setYahtzee(values);
        setChance(values);
    }
    
    // toString to print the score sheet
    public String toString() {
        StringBuilder result = new StringBuilder("\nScoresheet:\n");
        for (int i = 0; i < categories.length; i++) {
            result.append(i + 1).append(". ").append(categories[i]).append(": ");
            if (categoryChosen[i]) {
                result.append("[X]"); // Indicates category has been chosen
            } else {
                Integer score = scores.get(categories[i]);
                result.append(score != null ? score : "[-]"); // Show score if available, otherwise show placeholder
            }
            result.append("\n");
        }
        result.append("----------------------\n");
        result.append("Total Score: ").append(calcTotalScore()); // Assuming calcTotalScore() calculates total score
        return result.toString();
    }
}
