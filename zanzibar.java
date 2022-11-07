/**
 * The zanzibar program is a game that allows the user to enter a certain number of players and chips.
 * The users are given an opportunity to roll the dice and points are awarded according to the values rolled.
 * @author Vanshita Uthra
 * @since 2021-07-19
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class zanzibar {
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		// An empty arrayList
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		boolean winner = false;
					
		int numPlayers = userInput();
		// a list for the players in the game
		ArrayList<Integer> playersArray = new ArrayList<Integer>();
		for(int i = 0; i < numPlayers; i++) {
			playersArray.add(i);
		}
		int[] chips = chipsArray(numPlayers);
		int[] scores = scoreArray(numPlayers);
		
		defaultChips(chips, numPlayers);
		int firstPlayer = determineFirstPlayer(numPlayers);
		System.out.println("The player who plays first is: "); // The first element starts at 0
		System.out.println(firstPlayer+1);
		
		ArrayList<Integer> rollsDice = diceRoll(rolls,playersArray, firstPlayer); 
		System.out.println("The number of rolls for the first player is " + rollsDice);
		System.out.println("The rolls are: ");
		
		int score = calculateScore(rolls);
		System.out.println("The score of the first player is " + score);
		boolean seq= sequenceCheck(rolls);
		boolean zan = zanzibarCheck(rolls);
		boolean three = threeOfAKindCheck(rolls);
		
		// Print if they are a sequence, zanzibar, three of a kind or 
		if(seq) {
			System.out.println("This is a sequence");
		}
		else if(zan) {
			System.out.println("This is a zanzibar");
		}
		else if(three) {
			System.out.println("This is a three of a kind");
		}
		else {
			System.out.println("This is a regular list");
		}
		chipDistribution(scores, chips, rolls);
		
		declareWinner(chips, winner);
	}
	
	
	/**
	 * This method is used to allow the user to enter the number of players and
	 * adds another player, the computer, to the game
	 * @param none
	 * @return numPlayers The players playing in the game
	 */
	public static int userInput() {
		
		//Allow the user to enter the number players 
		System.out.println("Enter the number of players:");
		int totalPlayers = in.nextInt();
		in.nextLine();
		// the one is also added as the computer will also play
		int numPlayers = totalPlayers+ 1;
		return numPlayers;
				
	}
	
	
	/**
	 * This method is used to create the chips array according to the number of players in the game
	 * @param numPlayers The number of players in the game
	 * @return chips An array that is the size of the number of players
	 */
	public static int[] chipsArray(int playerNum) {
		// Create an array to store the chips 
		int[] chips = new int[playerNum]; 
		return chips;
	}
	
	/**
	 * This method is used to create the chips array according to the number of players in the game
	 * @param numPlayers The number of players in the game
	 * @return chips An array that is the size of the number of players
	 */
	public static int[] scoreArray(int playerNum) {
		//  Create an array to store the scores
		int[] scores = new int[playerNum]; 
		return scores; 
		}
	
	
	/**
	 * This method is used to distribute the chips to each player in the game
	 * @param chips An array that is the size of the number of players
	 * @param numPlayers The number of players in the game
	 * @return chips An array that has given a certain amount of chips to each player
	 */
	public static int[] defaultChips(int[] chips, int playerNum) {
		String custom = "Y";
		
		//Allow the user to customize the number of chips
		System.out.println("Would you like to enter a custom amount of chips? Y/N ");
		custom = in.nextLine().toUpperCase();
		if(custom.equals("Y")) {
			System.out.println("Enter the number of chips:");
			int customChips = in.nextInt();
			for(int i=0; i < playerNum; i++){ 
				// Each player gets a custom amount of chips entered by the user
				chips[i] = customChips;
			}
		}
		else {
			for(int i=0; i < playerNum; i++){ 
				if(playerNum == 2 ) {
					chips[i] = 15;
					}
				else {
					chips[i] = 20;
				}
			}
		}
		return chips;
	}
	

	/**
	 * This method is used to determine the first player by tossing a coin. 
	 * If a player gets heads or heads appears once in the toss, then that player will begin the same
	 * @param numPlayers The number of players in the game
	 * @return firstPlayer The player who will begin the game
	 */
	public static int determineFirstPlayer(int numPlayers) {
	
		// An array of coins with the same number of elements as the numPlayers 
		boolean[] coins = new boolean[numPlayers]; 
		int countheads = 0; 
		
		//do-while loop- will evaluate the expressions at the bottom of the loop at least once
		do {
			countheads = 0;
			for(int i=0; i < numPlayers; i++) {
				// coins[i] == false
				if(!coins[i]) { 
					// Less than 0.5 evaluates to tails which is false
					coins[i] = Math.random()<0.5; // 
				}
				if (!coins[i]) {
					//counts the number of heads if not false
					countheads++; 
					System.out.println("The number of heads rolled are: ");
					System.out.println(countheads);
				}
					
			}
		}
		// More than one person gets heads
		while(countheads > 1);
		int firstPlayer = 0;
		for(int i=0; i < numPlayers; i++) {
			if(coins[i]) {
				firstPlayer = i;
			}
		}
		
		return firstPlayer;
	}
	// calculate score of rolls
	public static int calculateScore(ArrayList<Integer> rolls) {
		int score = 0;
		for(int i = 0; i< rolls.size(); i++) {
			if(rolls.get(i) ==1) {
				score = score+100;
			}
			else if(rolls.get(i)==6) {
				score = score + 60;
			}
			else {
				score = score + rolls.get(i);
			}
		}
		return score;
	}
	
	// Roll dice
	/**
	 * This method is used to roll a dice. The players can roll the dice upto three times. The other players
	 * have to attempt to break the score of the first player in three or less rolls
	 * @param rolls An empty array
	 * @param playersArray An array that stores the number of players
	 * @param firstPlayer The player who begins the game
	 * @return rolls An array that stores all the values rolled
	 */
	public static ArrayList<Integer> diceRoll(ArrayList<Integer> rolls, ArrayList<Integer> playersArray, int firstPlayer) {
		int rollsFirstPlayer = 0;
		// starting player rolls first
		int rollDice = 0;
		String input = "Y";
		
		// First player rolls 
		while(input.equals("Y") && rollsFirstPlayer < 3) {  
			// Math.random includes intgers between 0 and 1 (not including one). Thus, 6 would not be included and we would need to add one
			rollDice = (int) (Math.random()*6) + 1; 
			System.out.println("You rolled:");
			System.out.println(rollDice);
			// Add the numbers rolled to the rolls array
			rolls.add(rollDice);
			if(rollsFirstPlayer < 2) {
				// Calculate the score
				System.out.println("Your score is now: " + calculateScore(rolls));
				System.out.println("Would you like to roll again? Y/N ");
				input = in.nextLine().toUpperCase();
			}
			if(input.equals("N")) {
				break;
			}	
		}
		
		rollsFirstPlayer++;
		
		// Remove the first player from the list
		playersArray.remove(firstPlayer);
		
		int otherRolls = 0;
		
		// All the other players roll
		for(int i = 0; i < playersArray.size(); i++) {
			while(input.equals("Y") && otherRolls < 3) {  
				// Math.random includes integers between 0 and 1 (not including one). Thus, 6 would not be included and we would need to add one
				rollDice = (int) (Math.random()*6) + 1; 
				System.out.println("You rolled" + rollDice);
				// Add the numbers rolled to the rolls array
				rolls.add(rollDice);
				
				if(otherRolls < 2) {
					// Calculate the score
					System.out.println("Your score is now: " + calculateScore(rolls));
					System.out.println("Would you like to roll again? Y/N ");
					input = in.nextLine().toUpperCase();
				}
				if(input.equals("N")) {
					break;
				}
			}
			
			otherRolls++;
		}
		return rolls;
		
	}
	// sequence
	/**
	 * This method is used to determine if the values rolled are a sequence, [1,2,3]
	 * @param rolls An array with all the values stored
	 * @return true A boolean, if rolls is a sequence
	 * @return false A boolean, if rolls is not a sequence
	 */
	public static boolean sequenceCheck(ArrayList<Integer> rolls) {
		// The elements in rolls are a sequence
		if((rolls.contains(1)) && (rolls.contains(2)) && (rolls.contains(3))){
			return true;
		}
		return false;
	}
	// zanzibar
	/**
	 * This method is used to determine if the values rolled are a zanzibar, [4,5,6]
	 * @param rolls An array with all the values stored
	 * @return true A boolean, if rolls is a zanzibar
	 * @return false A boolean, if rolls is not a zanzibar
	 */
	public static boolean zanzibarCheck(ArrayList<Integer> rolls) {
		// The elements in rolls are a zanzibar
		if ((rolls.contains(4)) && rolls.contains(5) && rolls.contains(6)) {
			return true;
		}
		return false;
	}
	// three of a kind
	/**
	 * This method is used to determine if the values rolled are a three of a kind, [1,2,3]
	 * @param rolls An array with all the values stored
	 * @return true A boolean, if rolls is a three of a kind
	 * @return false A boolean, if rolls is not a three of a kind
	 */
	public static boolean threeOfAKindCheck(ArrayList<Integer> rolls) {
		// The elements in rolls are a three of a kind
		if(rolls.get(0)==rolls.get(1) && rolls.get(0) == rolls.get(2) && rolls.get(1)==rolls.get(2)) {
			return true;
		}
		return false;
	}
	
	// chip distribution
	/**
	 * This method is used to distribute a certain amount of chips to the player with the lowest score.
	 * The other players will give a certain amount of their chips (depending on the values rolled by
	 * the player)
	 * @param scores An array with the scores of all the players
	 * @param chips An array with the chips of all the players
	 * @param rolls An array with the values rolled by the player
	 * @return chips An array with new number of chips
	 */
	public static int[] chipDistribution(int[] scores, int[] chips, ArrayList<Integer> rolls) {
		// Lowest score
		int lowestScore = scores[0];
		for(int i=1; i<scores.length; i++) {
			if(scores[i] < lowestScore) {
				lowestScore = scores[i];
			}
		}
		int indexOfLowest = Arrays.asList(scores).indexOf(lowestScore);
		int others = chips.length-1;
		
		// Check if it the values rolled is a sequence 
		if(sequenceCheck(rolls)== true && zanzibarCheck(rolls) == false && threeOfAKindCheck(rolls) == false) {
			// Each player give 1 chip away
			chips[indexOfLowest] += others;
			for(int i = 0; i < chips.length;i++) {
				chips[i]-=1;
			}		
		}
		// Check if the values rolled is a zanzibar
		else if(zanzibarCheck(rolls) == true && sequenceCheck(rolls)== false && threeOfAKindCheck(rolls) == false ){
			// Each player gives 4 chips away
			chips[indexOfLowest] += (others *4);
			for(int i = 0; i<chips.length; i++) {
				chips[i]-=1;
			}
		}
		// Check if the values rolled is a three of a kind
		else if(threeOfAKindCheck(rolls) == true && zanzibarCheck(rolls) == false && sequenceCheck(rolls)== false) {
			// Each player gives 3 chips away
			chips[indexOfLowest] += (others *3);
			for(int i=0; i < chips.length; i++) {
				chips[i]-=1;
			}
		}
		return chips;
	}
	// declare winner
	/**
	 * This method is used to display the winner of the game
	 * @param chips An array with all chips values
	 * @param winner A boolean initialized to false
	 * @return winner A boolean that shows if a player is a winner 
	 */
	public static boolean declareWinner(int[] chips, boolean winner) {
		while(winner){
			for(int i=0; i < chips.length; i++) {
				// The chips of one player equal to zero
				if(chips[i]==0) {
					winner = true;
					System.out.println("The winner of the game is: Player " + (i+1));
				}
				else {
					System.out.println("There is not winner");
					winner = false;
				}
			}
		}
	return winner;
	}
}

