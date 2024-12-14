import java.util.Scanner;
import java.util.Random;


class Conversation {

  /**
    * An array to store a transcript of the conversation.
    */
  private static String[] transcript;

  /**
    * To keep track of position in the transcript array.
    */
  private static int currentIndex = 0;


  /**
    * Prompts the user to enter the number of rounds for chatbot
    *
    * @param scanner a Scanner object for user input
    * @return the number of rounds the user wants to play, must be a positive integer
    */
  public static int getNumRounds(Scanner scanner) {
    int rounds;

      while (true) {
        System.out.print("How many rounds? ");
        if (scanner.hasNextInt()) {
          rounds = scanner.nextInt();
          scanner.nextLine();
          if (rounds > 0) {
              return rounds;
          } else {
              System.out.println("Please enter a positive number.");
          }
        } else {
          System.out.println("Invalid input. Please enter a positive integer.");
          scanner.nextLine();
        }
      }
  }


  /**
    * Gets a line of input from the user with or without prompt.
    *
    * @param scanner a Scanner object for user input
    * @param prompt  a message to display to the user before accepting input
    * @return the user's input as a string
    */
  public static String getUserInput(Scanner scanner, String prompt) {
    if (!prompt.isEmpty()) {
      System.out.print(prompt);
    }
    return scanner.nextLine();
}


  /**
    * Replaces specific words with mirrored words and replaces periods with question marks
    * If no words are mirrored, returns a random canned response.
    *
    *
    * @param input the user's input string
    * @return the mirrored response or a random canned response
    */
  public static String mirrorWordsOrRandom(String input) {
    String[] words = input.split("\\s+"); // Split the input into words
    StringBuilder mirrored = new StringBuilder();
    boolean hasMirroredWords = false;

    for (String word : words) {
      switch (word.toLowerCase()) {
        case "i":
          mirrored.append("you ");
          hasMirroredWords = true;
          break;
        case "me":
          mirrored.append("you ");
          hasMirroredWords = true;
          break;
        case "you":
          mirrored.append("me ");
          hasMirroredWords = true;
          break;
        case "my":
          mirrored.append("your ");
          hasMirroredWords = true;
          break;
        case "your":
          mirrored.append("my ");
          hasMirroredWords = true;
          break;
        case "am":
          mirrored.append("are ");
          hasMirroredWords = true;
          break;
        case "are":
          mirrored.append("am ");
          hasMirroredWords = true;
          break;
        default:
          mirrored.append(word).append(" ");
      }
    }

    // Replace period with question mark
    if (hasMirroredWords) {
      String result = mirrored.toString().trim();
      result = result.replace(".", "?");
      return result;
    } else {
      return getRandomResponse();
    }
  }


  /**
    * Returns a random canned response from a set of phrases.
    *
    * @return a random response as a string
    */
  public static String getRandomResponse() {
    String[] responses = {
      "Mmhmm.",
      "Interesting.",
      "I see."
    };
    Random random = new Random();
    return responses[random.nextInt(responses.length)];
  }



  /**
    * Adds an entry to the transcript for a given round.
    *
    * @param round         the current round number
    * @param userInput     the user's input for the round
    * @param response      the program's response to the user's input
    * @param isFirstRound  whether this is the first round (to include a greeting in the transcript)
    */
  public static void addToTranscript(int round, String userInput, String response, boolean isFirstRound) {
    String entry;
    if (isFirstRound) {
      entry = "Hi there! What is on your mind? \n" + userInput + "\n" + response;
    } else {
      entry = userInput + "\n" + response + "\n";
    }
    transcript[currentIndex++] = entry;
  }


  /**
    * Initializes the game, manages rounds of conversation, adds entries to transcript for each round, and prints the transcript at the end.
    *
    * @param args command-line arguments (not used)
    */
  public static void main(String[] arguments) {

    Scanner scanner = new Scanner(System.in);

    try {
      // Greeting and getting the number of rounds
      System.out.println("----------");
      System.out.println("Welcome to chatbot!");
      int rounds = getNumRounds(scanner);
      transcript = new String[rounds];
      System.out.println("----------");

      // Alternating input and mirrored response for the specified number of rounds
      for (int round = 1; round <= rounds; round++) {
        String thought;
        String prompt;

        if (round == 1) {
          // First round question
          prompt = "Hi there! What is on your mind?\n";
          thought = getUserInput(scanner, prompt);
          
          String botResponse = mirrorWordsOrRandom(thought);
          System.out.println(botResponse);
          addToTranscript(round, thought, botResponse, true);

        } else {
          // Every other round
          thought = getUserInput(scanner, "");

          String botResponse = mirrorWordsOrRandom(thought);
          System.out.println(botResponse);
          addToTranscript(round, thought, botResponse, false);
        }
      }

        System.out.println("\nThanks for chatting!");
        System.out.println("----------");
        System.out.println("TRANSCRIPT: \n");
        for (String entry : transcript) {
          System.out.println(entry);
      }
    } finally {
        scanner.close();
    }


  }
}
