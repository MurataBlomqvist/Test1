package org;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.form.gameForm;

public class Game {

    static Scanner myScanner = new Scanner(System.in);

    private gameForm form;

    private final static int PLAYER_VAL = 1;
    private final static int NPC_VAL = 2;
    
    public void gameOn(gameForm form) {
        this.form = form;

        while (true) {
            
            showGrid();
            
            if (playerInput() & checkWinCondition()) {
                printWinner();
                break;
            }
            if (!hasPlayableTile()) {
                showGrid();
                System.out.println("\nNo playable tiles left. Its a tie!");
                break;
            }

            if (npcInput() & checkWinCondition()) {
                printWinner();
                break;
            }
            if (!hasPlayableTile()) {
                showGrid();
                System.out.println("\nNo playable tiles left. Its a tie!");
                break;
            }

        }

        myScanner.close();
    }

    private boolean hasPlayableTile() {
        boolean hasPlayableTile = false;
        for (int i = 0; i < form.getGridSize(); i++) {
            for (int j = 0; j < form.getGridSize(); j++) {
                if (form.selGridVal(i, j) == 0) {
                    hasPlayableTile = true;
                    break;
                }
            }
            if (hasPlayableTile) {
                break;
            }
        }
        return hasPlayableTile;
    }

    private void printWinner() {
        showGrid();
        System.out.println("\nThe winner is : " + form.getWinnerName());
    }

    private boolean checkWinCondition() {
        boolean winCond = false;;

        winCond = calcWinCond();
        
        if (winCond) {
            if (form.getLastPlayed() == PLAYER_VAL) {
                form.setWinnerName("Player");
            } else {
                form.setWinnerName("Npc");
            }
        }
        return winCond;
    }

    private boolean calcWinCond() {
        boolean winCond = false;
        int hitCount = 0;
        for (int i = 0; i < form.getGridSize(); i++) {
            hitCount = 0;
            for (int j = 0; j < form.getGridSize(); j++) {
                // checks Diagonally
                if (form.selGridVal(j == 0 ? j+1 : j-1, i) != form.selGridVal(j, i)) {
                    winCond = false;
                    break;
                } else if (form.selGridVal(j, i) != 0) {
                    hitCount++;
                }
            }
            if (hitCount == form.getGridSize()) {
                winCond = true;
                break;
            }
            hitCount = 0;
            for (int j = 0; j < form.getGridSize(); j++) {
                // checks Horizontally
                if (form.selGridVal(i, j == 0 ? j+1 : j-1) != form.selGridVal(i, j)) {
                    winCond = false;
                    break;
                } else if (form.selGridVal(i, j) != 0) {
                    hitCount++;
                }
            }
            if (hitCount == form.getGridSize()) {
                winCond = true;
                break;
            }
            if (i == 0) {
                hitCount = 0;
                for (int j = 0; j < form.getGridSize(); j++) {
                    // checks bottom right → top left
                    if (form.selGridVal(j == 0 ? j+1 : j-1, j == 0 ? j+1 : j-1) != form.selGridVal(j, j)) {
                        winCond = false;
                        break;
                    } else if (form.selGridVal(j, j) != 0) {
                        hitCount++;
                    }
                }
                if (hitCount == form.getGridSize()) {
                    winCond = true;
                    break;
                }
            }
            if (i == (form.getGridSize()-1)) {
                hitCount = 0;
                for (int j = 0; j < form.getGridSize(); j++) {
                    // checks bottom left → top right
                    if (form.selGridVal(j == 0 ? i-j-1 : i-j+1, j == 0 ? j+1 : j-1) != form.selGridVal(i-j, j)) {
                        winCond = false;
                        break;
                    } else if (form.selGridVal(i-j, j) != 0) {
                        hitCount++;
                    }
                }
                if (hitCount == form.getGridSize()) {
                    winCond = true;
                    break;
                }
            }
        }
       
        return winCond;
    }

    private boolean playerInput() {
        
        int pos1 = 0;
        int pos2 = 0;
        try {
            while(true) {
                System.out.print("\nInsert the position in RowxColumn format ( second row first column = 2x1 ) :");
                String tmpIn = myScanner.nextLine();
                String[] tmpStr = tmpIn.split("x");
                if (tmpIn.matches("[0-9]*[x][0-9]*")) {
                    pos1 = Integer.valueOf(tmpStr[0]) - 1;
                    pos2 = Integer.valueOf(tmpStr[1]) - 1;
                }
                if (0 < pos1+1 && pos2+1 <= form.getGridSize()) {

                    if (!canSelect(pos1, pos2)) {
                        System.out.print("Try Again\n");
                        continue;
                    }

                    break;
                }
                System.out.print("Try Again\n");
            }
        } catch (Exception e) {
            System.out.print("Try Again\n");
        }

        form.addToGrid(pos1, pos2, PLAYER_VAL);

        form.setLastPlayed(PLAYER_VAL);

        return true;
    }

    private boolean npcInput() {
        boolean rtnBool = false;
        Random math_thing = new java.util.Random();
        while (!rtnBool) {
            Map<Integer, int[]> intMap = new HashMap<>();
            // Using treeSet for the naturalOrder implicit
            Set<Integer> intSetList = new TreeSet<>();

            for (int i = 0; i < form.getGridSize(); i++) {
                for (int j = 0; j < form.getGridSize(); j++) {
                    Integer keyVal = math_thing.nextInt();
                    intSetList.add(keyVal);
                    intMap.put(keyVal, new int[] {i,j});
                }
            }
            // intSetList is naturally ordered
            // Higher value is more prio than low ones
            for (Integer integer : intSetList) {
                if (canSelect(intMap.get(integer)[0], intMap.get(integer)[1])) {
                    form.addToGrid(intMap.get(integer)[0], intMap.get(integer)[1], NPC_VAL);
                    form.setLastPlayed(NPC_VAL);
                    rtnBool = true;
                    break;
                }
            }
        }
        
        return true;
    }

    private boolean canSelect(int pos1, int pos2) {
        return (form.selGridVal(pos1, pos2) == 0);
    }

    private void showGrid() {
        System.out.print(form.getGridRoof());
        for (int i = 0; i < form.getGridSize(); i++) {
            System.out.println();
            for (int j = 0; j < form.getGridSize(); j++) {
                System.out.print(form.getGridConnectoString());
                System.out.print(" " + form.selGridVal(i, j) + " ");
            }
            System.out.print(form.getGridConnectoString());
        }
        System.out.print("\n" + form.getGridFloor());
    }

}
