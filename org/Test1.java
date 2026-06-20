package org;

import java.util.logging.Logger;
import java.util.Scanner;

public class Test1 {

    private Logger log = Logger.getLogger(getClass().getName());

    int gridSize;
    int[][] grid;
    int[][] playerGrid;
    int[][] npcGrid;

    String gridConnectoString = "|";
    String gridDivider = "|---";
    String gridRoof = " ___";
    String gridFloor = " ```";

    public void main() {

        int in1 = 0;
        
        try (Scanner myScanner = new Scanner(System.in)) {
            while(true) {
                System.out.println("Setup grid for play(0-50)");
                System.out.print("Insert size for grid:");
                int tmpIn = myScanner.nextInt();
                if (0 < tmpIn && tmpIn < 51) {
                    in1 = tmpIn;
                    break;
                }
                System.out.println("Try Again\n");
            }
            
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        setup(in1);
        showGrid();

        try (Scanner myScanner = new Scanner(System.in)) {
            while(true) {
                System.out.print("Insert the position in RowxColumn format ( second row first column = 2x1 ) :");
                String tmpIn = myScanner.nextLine();
                if () {
                    in1 = tmpIn;
                    break;
                }
                System.out.println("Try Again\n");
            }
            
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    private boolean canSelect(int iIndex, int jIndex) {
        return (this.grid[iIndex][jIndex] != 0);
    }

    private void selectGridPos(int iIndex, int jIndex, byte selectVal) {
        this.grid[iIndex][jIndex] = selectVal;
    }

    private void showGrid() {
        System.out.print(gridRoof);
        for (int i = 0; i < gridSize; i++) {
            System.out.println();
            for (int j = 0; j < gridSize; j++) {
                System.out.print(gridConnectoString);
                System.out.print(" " + this.grid[i][j] + " ");
            }
            System.out.print(gridConnectoString);
        }
        System.out.print("\n" + gridFloor);
    }

    private void calcGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                
                if (this.playerGrid[i][j] == 1) {
                    this.grid[i][j] = 1;
                } else if (this.npcGrid[i][j] == 1) {
                    this.grid[i][j] = 2;
                } else {
                    this.grid[i][j] = 0;
                }

            }
        }
    }

    private void setup(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        this.playerGrid = new int[gridSize][gridSize];
        this.npcGrid = new int[gridSize][gridSize];
        StringBuilder sbDivider = new StringBuilder();
        StringBuilder sbRoof = new StringBuilder();
        StringBuilder sbFloor = new StringBuilder();
        
        for (int i = 0; i < gridSize; i++) {
            sbDivider.append(gridDivider);
            sbRoof.append(gridRoof);
            sbFloor.append(gridFloor);
            for (int j = 0; j < gridSize; j++) {
                this.grid[i][j] = 0;
                this.playerGrid[i][j] = 0;
                this.npcGrid[i][j] = 0;
                
            }
        }
        this.gridDivider = sbDivider.append("|").toString();
        this.gridRoof = sbRoof.toString();
        this.gridFloor = sbFloor.toString();
    }

}