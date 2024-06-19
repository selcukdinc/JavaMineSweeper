package be;
public class Cell {

    private boolean isMine;
    private boolean isFlag;
    private boolean isRevealed;
    private Point location;
    private int minesAround;

    public Cell(Point location) {
        this.isMine = false;
        this.isFlag = false;
        this.isRevealed = false;
        this.location = location;
        this.minesAround = 0;
    }


    // getter and setters

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean isFlag) {
        this.isFlag = isFlag;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

}
