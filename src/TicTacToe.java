import java.util.*;

public class TicTacToe {
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args) {
        // TODO
        // This is where the main menu system of the program will be written.
        // Hint: Since many of the game runner methods take in an array of player names,
        // you'll probably need to collect names here.
        showOptions();
    }

    private static void showOptions() {
        String[] lastPlayerNames = null;
        ArrayList<char[][]> lastGameHistory = null;

        while (true) {
            System.out.println("Welcome to game of Tic Tac Toe, choose one of the following options from below: ");
            System.out.println("1. Single player ");
            System.out.println("2. Two player");
            System.out.println("D. Display last match ");
            System.out.println("Q. Quit ");

            Scanner input = new Scanner(System.in);
            System.out.println("What do you want to do: ");
            String option = input.next();
            switch (option) {
                case "1" -> {
                    System.out.println("Enter player 1 name: ");
                    String name = input.next();
                    String[] names = {name, "Computer"};
                    lastPlayerNames = names;
                    lastGameHistory = runOnePlayerGame(names);
                }
                case "2" -> {
                    System.out.println("Enter player 1 name: ");
                    String name1 = input.next();
                    System.out.println("Enter player 2 name: ");
                    String name2 = input.next();
                    String[] names = {name1, name2};
                    lastPlayerNames = names;
                    lastGameHistory = runTwoPlayerGame(names);
                }
                case "D" -> {
                    if (lastGameHistory != null) {
                        runGameHistory(lastPlayerNames, lastGameHistory);
                    } else System.out.println("There is no previous match");
                }
                case "Q" -> {
                    System.out.println("Thanks for playing. Hope you had fun!");
                    return;
                }
                default -> {
                    System.out.println("Invalid input");
                    showOptions();
                }
            }
        }
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state) {
        // TODO
        // Hint: Make use of the newline character \n to get everything into one String
        // It would be best practice to do this with a loop, but since we hardcode the game to only use 3x3 boards
        // it's fine to do this without one.

        String grid = (" " + state[0][0] + " | " + state[0][1] + " | " + state[0][2]
                + "\n" + "--- --- ---" + "\n" +
                " " + state[1][0] + " | " + state[1][1] + " | " + state[1][2]
                + "\n" + "--- --- ---" + "\n" + " "
                + state[2][0] + " | " + state[2][1] + " | " + state[2][2]);

        return grid;
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.

    private static char[][] getInitialGameState() {
        char[][] state = new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
        return state;
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        System.out.println("Tossing a coin to decide who goes first!!!");

        String activePlayer;
        if (Math.random() < 0.5) {
            activePlayer = playerNames[0];
            System.out.println(playerNames[0] + " gets to go first ");
        }
        else{
            System.out.println(playerNames[1] + " gets to go first.");
            activePlayer = playerNames[1];
        }

        ArrayList<char[][]> gameHistory = new ArrayList<>();
        char[][] state = getInitialGameState();

        char activeSymbol;
        if (activePlayer.equals(playerNames[0])){
            activeSymbol = playerOneSymbol;

        }
        else{
            activeSymbol = playerTwoSymbol;
        }

        while (!checkWin(state) && !checkDraw(state)) {
            // Display current state
            System.out.println(displayGameFromState(state));
            System.out.println(activePlayer + " turns");

            // Get new move
            char[][] newState = runPlayerMove(activePlayer, activeSymbol, state);

            // Add to game history
            gameHistory.add(newState);
            // Switch to other player
            if(activePlayer.equals(playerNames[0])){
                activePlayer = playerNames[1];
                activeSymbol = playerOneSymbol;
            }
            else{
                activePlayer = playerNames[0];
                activeSymbol = playerTwoSymbol;
            }
            state = newState;
        }

        System.out.println(displayGameFromState(state));
        if (checkWin(state)) {
            // Switch back to the last player to determine the winner
            if (activePlayer.equals(playerNames[0])){
                activePlayer = playerNames[1];
            }
            else{
                activePlayer =  playerNames[0];
            }
            System.out.println("Congratulations " + activePlayer + ", you have won!");
        }
        else{
            System.out.println("The game is a draw.");
        }
        return gameHistory;
    }


    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {
        System.out.println("Tossing a coin to decide who goes first!!!");

        String activePlayer;
        if (Math.random() < 0.5) {
            activePlayer = playerNames[0];
            System.out.println(playerNames[0] + " gets to go first ");
        }
        else{
            System.out.println(playerNames[1] + " gets to go first.");
            activePlayer = playerNames[1];
        }

        ArrayList<char[][]> gameHistory = new ArrayList<>();
        char[][] state = getInitialGameState();
        char activeSymbol;

        if (activePlayer.equals(playerNames[0])){
            activeSymbol = playerOneSymbol;
        }
        else{
            activeSymbol = playerTwoSymbol;
        }

        while (!checkWin(state) && !checkDraw(state)) {
            // Display current state
            System.out.println(displayGameFromState(state));
            System.out.println(activePlayer + " turns");

            if (activePlayer.equals(playerNames[1])) {
                char[][] newState = getCPUMove(state);

                // Add to game history
                gameHistory.add(newState);
                // Switch to other player
                if (activePlayer.equals(playerNames[0])){
                    activePlayer = playerNames[1];
                    activeSymbol = playerOneSymbol;
                }
                else{
                    activePlayer = playerNames[0];
                    activeSymbol = playerTwoSymbol;

                }
                state = newState;
            }
            else{
                // Get new move
                char[][] newState = runPlayerMove(activePlayer, activeSymbol, state);
                // Add to game history
                gameHistory.add(newState);
                // Switch to other player
                if (activePlayer.equals(playerNames[0])){
                    activePlayer = playerNames[1];
                    activeSymbol = playerOneSymbol;
                }
                else{
                    activePlayer = playerNames[0];
                    activeSymbol = playerTwoSymbol;

                }
                state = newState;
            }
        }

        System.out.println(displayGameFromState(state));
        if (checkWin(state)) {
            if(activePlayer.equals(playerNames[0])){
                activePlayer = playerNames[1];
            }
            else{
                activePlayer = playerNames[0];
            }
            if (activePlayer.equals(playerNames[1])) {
                System.out.println("Game over, you lost, better luck next time!");
            }
            else{
                System.out.println("Congratulations " + activePlayer + ", you have won!");
            }
        }
        else{
            System.out.println("The game is a draw.");
        }
        return gameHistory;
    }

    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        int[] move = getInBoundsPlayerMove(playerName);

        boolean validMove = checkValidMove(move, currentState);
        if (validMove) {
            return makeMove(move, playerSymbol, currentState);
        }
        else{
            System.out.println("The space is already taken. Try again");
            return runPlayerMove(playerName, playerSymbol, currentState);
        }
    }

    //Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {
        Scanner sc = new Scanner(System.in);
        int row;
        int col;

        while (true) {
            System.out.print(playerName + " enter row: ");
            if (sc.hasNextInt()) {
                row = sc.nextInt();
                if (row >= 0 && row <= 2) {
                    break;
                }
            }
            else{
                sc.next();
            }
            System.out.println("Invalid input. Row must be a number between 0 and 2.");
        }

        while (true) {
            System.out.print(playerName + " enter col: ");
            if (sc.hasNextInt()) {
                col = sc.nextInt();
                if (col >= 0 && col <= 2) {
                    break;
                }
            }
            else{
                sc.next();
            }
            System.out.println("Invalid input. Column must be a number between 0 and 2.");
        }

        return new int[]{row, col};
    }

    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        int row = move[0];
        int col = move[1];
        return state[row][col] == emptySpaceSymbol;
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        // TODO:
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array
        char[][] currentStateCopy = Arrays.copyOf(currentState, currentState.length);
        for (int i = 0; i < currentState.length; i++) {
            currentStateCopy[i] = Arrays.copyOf(currentState[i], currentState[i].length);
        }

        int row = move[0];
        int col = move[1];
        currentStateCopy[row][col] = symbol;

        return currentStateCopy;
    }

    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {
        // TODO
        // Hint: no need to check if player one has won and if player two has won in separate steps,
        // you can just check if the same symbol occurs three times in any row, col, or diagonal (except empty space symbol)
        // But either implementation is valid: do whatever makes most sense to you.

        for (int row = 0; row < 3; row++) {
            if (state[row][0] != emptySpaceSymbol && state[row][0] == state[row][1] && state[row][0] == state[row][2]) {
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (state[0][col] != emptySpaceSymbol && state[0][col] == state[1][col] && state[0][col] == state[2][col]) {
                return true;
            }
        }

        if (state[0][0] != emptySpaceSymbol && state[0][0] == state[1][1] && state[0][0] == state[2][2]) {
            return true;
        }

        return state[0][2] != emptySpaceSymbol && state[0][2] == state[1][1] && state[0][2] == state[2][0];

    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (state[row][col] == emptySpaceSymbol) {
                    return false;
                }
            }
        }
        return true;
    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState) {
        // TODO

        // Hint: you can call makeMove() and not end up returning the result, in order to "test" a move
        // and see what would happen. This is one reason why makeMove() does not modify the state argument

        ArrayList<int[]> validMoves = getValidMoves(gameState);

        for (int[] move : validMoves) {
            char[][] newGameState = makeMove(move, playerTwoSymbol, gameState);
            if (checkWin(newGameState)) {
                return newGameState;
            }
        }

        // If not, check if opponent has a winning move, and if so, make a move there
        for (int[] move : validMoves) {
            char[][] newGameState = makeMove(move, playerOneSymbol, gameState);
            if (checkWin(newGameState)) {
                return makeMove(move, playerTwoSymbol, gameState);
            }
        }

        for (int[] move : validMoves) {

            if (move[0] == 1 && move[1] == 1) {
                return makeMove(move, playerTwoSymbol, gameState);
            }

            if ((move[0] == 0 && move[1] == 0) || (move[0] == 0 && move[1] == 2) || (move[0] == 2 && move[1] == 0) || (move[0] == 2 && move[1] == 2)) {
                return makeMove(move, playerTwoSymbol, gameState);
            }

            return makeMove(move, playerTwoSymbol, gameState);
        }
        return makeMove(validMoves.get(0), playerTwoSymbol, gameState);
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col] == emptySpaceSymbol) {
                    validMoves.add(new int[]{row, col});
                }
            }
        }
        return validMoves;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {

        char[][] firstBoard = gameHistory.get(0);
        char firstPlayerSymbol = 1;

        for (int row = 0; row < firstBoard.length; row++) {
            for (int col = 0; col < firstBoard[row].length; col++) {
                char currentChar = firstBoard[row][col];
                if(currentChar == playerOneSymbol){
                    firstPlayerSymbol  = playerOneSymbol;
                    break;
                }
                if(currentChar == playerTwoSymbol){
                    firstPlayerSymbol = playerTwoSymbol;
                    break;
                }
            }
        }

        String activePlayer = firstPlayerSymbol == playerOneSymbol ? playerNames[0] : playerNames[1];

        System.out.println(playerNames[0] + " (" + playerOneSymbol + ") " + " vs " + playerNames[1] + " (" + playerTwoSymbol + ") ");
        for (char[][] board : gameHistory) {
            System.out.println(activePlayer + ":");
            System.out.println(displayGameFromState(board));

            activePlayer = playerNames[0].equals(activePlayer) ? playerNames[1] : playerNames[0];
        }
    }
}
