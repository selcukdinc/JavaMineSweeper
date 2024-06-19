package be;
import java.util.Scanner;

import enums.GameState;

public class Test {

    public static void main(String[] args) {
        MineSweeperGame game = new MineSweeperGame(5, 10, 10);
        game.initializeGame();
        Scanner scanner = new Scanner(System.in);
        Cell[][] board = game.getGameBoard().getBoard();
        printBoard(board);
        while(game.getGameState() == GameState.GAME_IN_PROGRESS){
            System.out.print("x : ");
            int x = scanner.nextInt();
            System.out.print("y : ");
            int y = scanner.nextInt();
            game.revealCell(new Point(x, y));
            printBoard(board);
        }
        System.out.println(game.getGameState());
        scanner.close();
    }

    private static void printBoard(Cell[][] board ){
        System.out.println("\n--- Mayın Tarlası ---\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].isMine())
                    System.out.print("X   ");
                else if(board[i][j].isRevealed()){
                    System.out.print(board[i][j].getMinesAround()+"   ");
                } else
                    System.out.print("-   ");                
            }
            System.out.println("");
        }
    }
}
