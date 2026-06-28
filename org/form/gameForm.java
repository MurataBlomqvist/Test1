package org.form;

public class gameForm {
    public gameForm(int[][] grid) {
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

    private int[][] grid;
    public void addToGrid(int pos1, int pos2, int val) {
        grid[pos1][pos2] = val;
    }
    public int selGridVal(int pos1, int pos2) {
        return grid[pos1][pos2];
    }
    
    private int[][] npcInputGrid;
    public int getNpcInputGridVal(int pos1, int pos2) {
        return npcInputGrid[pos1][pos2];
    }
    public void setNpcInputGrid(int[][] npcInputGrid) {
        this.npcInputGrid = npcInputGrid;
    }

    public record gameRecord(String gridConnectoString, String gridDivider, String gridRoof, String gridFloor, int gridSize) {}
    public gameRecord gameData;
    public void setGameRecord(String gridConnectoString, String gridDivider, String gridRoof, String gridFloor, int gridSize) {
        this.gameData = new gameRecord(
            gridConnectoString,
            gridDivider,
            gridRoof,
            gridFloor,
            gridSize
        );
    }
}
