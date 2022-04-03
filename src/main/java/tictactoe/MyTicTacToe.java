package tictactoe;


import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTicTacToe {
    static ArrayList<Integer> playerPosition = new ArrayList<Integer>();
    static ArrayList<Integer> computerPosition = new ArrayList<Integer>();

    public boolean gameRunning;
    public boolean winner = false;

    public MyTicTacToe() {
        this.gameRunning = true;
    }

    public char[][] introboard = {{'┌', '─', '─', '─', '┬', '─', '─', '─', '┬', '─', '─', '─', '┐', '\n'},
            {'│', ' ', '1', ' ', '│', ' ', '2', ' ', '│', ' ', '3', ' ', '│', '\n'},
            {'├', '─', '─', '─', '┼', '─', '─', '─', '┼', '─', '─', '─', '┤', '\n'},
            {'│', ' ', '4', ' ', '│', ' ', '5', ' ', '│', ' ', '6', ' ', '│', '\n'},
            {'├', '─', '─', '─', '┼', '─', '─', '─', '┼', '─', '─', '─', '┤', '\n'},
            {'│', ' ', '7', ' ', '│', ' ', '8', ' ', '│', ' ', '9', ' ', '│', '\n'},
            {'└', '─', '─', '─', '┴', '─', '─', '─', '┴', '─', '─', '─', '┘'}};

    public char[][] board = {{'┌', '─', '─', '─', '┬', '─', '─', '─', '┬', '─', '─', '─', '┐', '\n'},
            {'│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', '\n'},
            {'├', '─', '─', '─', '┼', '─', '─', '─', '┼', '─', '─', '─', '┤', '\n'},
            {'│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', '\n'},
            {'├', '─', '─', '─', '┼', '─', '─', '─', '┼', '─', '─', '─', '┤', '\n'},
            {'│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', '\n'},
            {'└', '─', '─', '─', '┴', '─', '─', '─', '┴', '─', '─', '─', '┘'}};


    public void game(String userInput, MessageChannel channel) {

        Stream<char[]> charStream = Arrays.stream(board);
        String currentBoard = "```" + charStream.map(String::valueOf).collect(Collectors.joining()) + "```";

        int playerInputPosition = Integer.parseInt(userInput);
        if (playerPosition.contains(playerInputPosition) || computerPosition.contains(playerInputPosition)) {
            channel.sendMessage("Position is already taken! Enter an existing number").queue();
            playerInputPosition = Integer.parseInt(userInput);
        }
        placeSign(playerInputPosition, board, "player");

        String winnerMessage = checkWinner();
        if (winnerMessage.length() > 0) {
            channel.sendMessage(winnerMessage).queue();
            gameRunning = false;
            playerPosition.clear();
            computerPosition.clear();

            return;
        }

        if (isRunning()) {
            Random random = new Random();
            int computerInputPosition = random.nextInt(9) + 1;
            while (playerPosition.contains(computerInputPosition) || computerPosition.contains(computerInputPosition)) {
                System.out.println("Position is already taken! Enter an existing number");
                computerInputPosition = random.nextInt(9) + 1;
            }
            placeSign(computerInputPosition, board, "computer");


            channel.sendMessage(currentBoard).queue();

//            printBoard(board);

            winnerMessage = checkWinner();
            if (winnerMessage.length() > 0) {
                channel.sendMessage(winnerMessage).queue();
                gameRunning = false;
                board = new char[][]{{'┌', '─', '─', '─', '┬', '─', '─', '─', '┬', '─', '─', '─', '┐', '\n'},
                        {'│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', '\n'},
                        {'├', '─', '─', '─', '┼', '─', '─', '─', '┼', '─', '─', '─', '┤', '\n'},
                        {'│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', '\n'},
                        {'├', '─', '─', '─', '┼', '─', '─', '─', '┼', '─', '─', '─', '┤', '\n'},
                        {'│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', ' ', ' ', ' ', '│', '\n'},
                        {'└', '─', '─', '─', '┴', '─', '─', '─', '┴', '─', '─', '─', '┘'}};
                playerPosition.clear();
                computerPosition.clear();

            }
        }


    }

    private void placeSign(int pos, char[][] board, String user) {
        char sign = 'X';
        if (user.equals("player")) {
            sign = 'X';
            playerPosition.add(pos);

        } else if (user.equals("computer")) {
            sign = 'O';
            computerPosition.add(pos);
        }
        switch (pos) {
            case 1:
                board[1][2] = sign;
                break;
            case 2:
                board[1][6] = sign;
                break;
            case 3:
                board[1][10] = sign;
                break;
            case 4:
                board[3][2] = sign;
                break;
            case 5:
                board[3][6] = sign;
                break;
            case 6:
                board[3][10] = sign;
                break;
            case 7:
                board[5][2] = sign;
                break;
            case 8:
                board[5][6] = sign;
                break;
            case 9:
                board[5][10] = sign;
                break;
            default:
                break;
        }
    }

    public void printBoard(char[][] board) {
        for (char[] rows : board) {
            for (char c : rows) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public boolean isRunning() {
        return gameRunning;
    }


    public String checkWinner() {
        List firstRow = Arrays.asList(1, 2, 3);
        List secondRow = Arrays.asList(4, 5, 6);
        List thirdRow = Arrays.asList(7, 8, 9);
        List firstColumn = Arrays.asList(1, 4, 7);
        List secondColumn = Arrays.asList(2, 5, 8);
        List thirdColumn = Arrays.asList(3, 6, 9);
        List toplefttobottomright = Arrays.asList(1, 5, 9);
        List bottomlefttotopright = Arrays.asList(7, 5, 3);
        List<List> winningCons = new ArrayList<List>();
        winningCons.add(firstRow);
        winningCons.add(secondRow);
        winningCons.add(thirdRow);
        winningCons.add(firstColumn);
        winningCons.add(secondColumn);
        winningCons.add(thirdColumn);
        winningCons.add(toplefttobottomright);
        winningCons.add(bottomlefttotopright);

        for (List l : winningCons) {
            if (playerPosition.containsAll(l)) {
                winner = true;
                return "U WON!";
            } else if (computerPosition.containsAll(l)) {
                winner = true;
                return "Computer has Won :(";
            } else if (playerPosition.size() + computerPosition.size() == 9) {
                winner = true;
                return "DRAW!";

            }
        }
        return "";
    }

}
