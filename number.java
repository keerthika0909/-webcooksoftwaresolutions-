import java.util.Random;
import java.util.Scanner;

public class Number{
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    int lowerBound = 1;
    int upperBound = 100;
    int numberToGuess = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    int maxAttempts =10;
    int attempts = 0;
    boolean hasGuessedCorrectly = false;
    System.out.println("Welcome to the Number Guessing Game!");
    System.out.println("Guess the number between " + lowerBound + " and " + upperBound + ":");
    while (attempts < maxAttempts && !hasGuessedCorrectly) {
    System.out.print("Enter your guess: ");
    int userGuess = scanner.nextInt();
    attempts++;
    if(userGuess < numberToGuess) {
    System.out.println("Too low!");
    }else if(userGuess > numberToGuess) {
    System.out.println("Too high!");
    }else{
       hasGuessedCorrectly = true;
       System.out.println("Congratulations! You've guessed the number!");
            }
        }
       if(!hasGuessedCorrectly){
          System.out.println("Sorry, you've used all your attempts. The number was: " + numberToGuess);
        }
       System.out.println("You made " + attempts + " attempts.");
       scanner.close();
    }
}