package org.form;

public class gameForm {
    public gameForm(int gridSize, int[][] grid) {
        this.gridSize = gridSize;
        this.grid = grid;
    }

    private int lastPlayed;
    public int getLastPlayed() {
        return lastPlayed;
    }
    public void setLastPlayed(int lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    private String winnerName;
    public String getWinnerName() {
        return winnerName;
    }
    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    private int gridSize;
    public int getGridSize() {
        return gridSize;
    }

    private int[][] grid;
    public void addToGrid(int pos1, int pos2, int val) {
        grid[pos1][pos2] = val;
    }
    public int selGridVal(int pos1, int pos2) {
        return grid[pos1][pos2];
    }

    String gridConnectoString = "|";
    public String getGridConnectoString() {
        return gridConnectoString;
    }

    String gridDivider = "|---";
    public String getGridDivider() {
        return gridDivider;
    }
    public void setGridDivider(String gridDivider) {
        this.gridDivider = gridDivider;
    }

    String gridRoof = " ___";
    public String getGridRoof() {
        return gridRoof;
    }
    public void setGridRoof(String gridRoof) {
        this.gridRoof = gridRoof;
    }

    String gridFloor = " ```";
    public String getGridFloor() {
        return gridFloor;
    }
    public void setGridFloor(String gridFloor) {
        this.gridFloor = gridFloor;
    }
}
