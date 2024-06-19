package be;
import java.util.Random;

import enums.GameState;

import java.util.List;

public class MineSweeperGame {

    private GameBoard gameBoard;
    private int numOfMines;
    private int height;
    private int width;
    private GameState gameState;
    private int revealedCells;
    

    public MineSweeperGame(int _height, int _width, int _numOfMines) {
        this.numOfMines = _numOfMines;
        this.height = _height;
        this.width = _width;
    }

    public void initializeGame(){
        revealedCells = 0;
        gameState = GameState.GAME_IN_PROGRESS;
        gameBoard = new GameBoard(height, width);
        placeMines();
    }

    public void revealCell(Point location){
        if(gameBoard.isRevealed(location) || gameBoard.hasFlag(location)){
            return;
        }else if(gameBoard.hasMine(location)){
            gameState = GameState.GAME_LOST;
        }else {
            revealCellRecursive(location);
        }
    }

    private void revealCellRecursive(Point location){
        gameBoard.revealCell(location);
        ++revealedCells;
        if(revealedCells == (height * width) - numOfMines){
            gameState = GameState.GAME_WON;
            return;
        }

        int minesAround = gameBoard.findMinesAround(location);
        gameBoard.setMinesAround(location, minesAround);
        
        if(minesAround == 0){
            List<Cell> adjacentCells = gameBoard.getAdjacentCells(location);
            for (Cell cell : adjacentCells) {
                // Chat gpt'nin yardımı alınarak düzeltilen kısım
                if(!gameBoard.isRevealed(cell.getLocation()) && !gameBoard.hasFlag(cell.getLocation())){
                    revealCellRecursive(cell.getLocation());
                }
            }
        }
    }

    public void toggleFlag(Point location){
        if(!gameBoard.isRevealed(location))
            gameBoard.toggleFlag(location);
    }

    private void placeMines(){
        Random R = new Random();
        int placedMines = 0;
        while (placedMines < numOfMines) {
            int randomX = R.nextInt(height);
            int randomY = R.nextInt(width);
            
            Point mineLocation = new Point(randomX, randomY);
            
            if(!gameBoard.hasMine(mineLocation)){
                gameBoard.placeMine(mineLocation);
                placedMines++;
            }
        }
    }

    // getters and setters 

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    
}
