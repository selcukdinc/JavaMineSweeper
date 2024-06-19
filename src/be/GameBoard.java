package be;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Cell[][] board;

    public GameBoard(int row, int col) {
        board = new Cell[row][col];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(new Point(i, j));
            }
        }
    }

    public void revealCell(Point location){
        Cell cell = board[location.getX()][location.getY()];
        cell.setRevealed(true);
    }

    public void setMinesAround(Point location, int minesAround){
        Cell cell = board[location.getX()][location.getY()];
        cell.setMinesAround(minesAround);
    }

    public int findMinesAround(Point location){
        int minesAround = 0;
        List<Cell> adjacentCells = getAdjacentCells(location);
        for (Cell cell : adjacentCells) {
            if(cell.isMine()){
                ++minesAround;
            }
        }
        return minesAround;
    }

    public List<Cell> getAdjacentCells(Point location){
        ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
        int x = location.getX();
        int y = location.getY();
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, board.length - 1) ; i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, board[i].length - 1); j++) {
                if(!(x == i && y == j)){
                    adjacentCells.add(board[i][j]);
                }
            }
        }
        return adjacentCells;
    }

    public boolean isRevealed(Point location){
        Cell cell = board[location.getX()][location.getY()];
        return cell.isRevealed();
    }

    public void toggleFlag(Point location){
        Cell cell = board[location.getX()][location.getY()];
        cell.setFlag(!cell.isFlag());
    }

    public void placeMine(Point location){
        Cell cell = board[location.getX()][location.getY()];
        cell.setMine(true);
    }
    
    public boolean hasMine(Point location){
        Cell cell = board[location.getX()][location.getY()];
        return cell.isMine();
    }

    public boolean hasFlag(Point location){
        Cell cell = board[location.getX()][location.getY()];
        return cell.isFlag();
    }

    // getter and setters

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    

    
}
