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
        for (int i = 0; i < form.gameData.gridSize(); i++) {
            for (int j = 0; j < form.gameData.gridSize(); j++) {
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
        for (int i = 0; i < form.gameData.gridSize(); i++) {
            hitCount = 0;
            for (int j = 0; j < form.gameData.gridSize(); j++) {
                // checks Diagonally
                if (form.selGridVal(j == 0 ? j+1 : j-1, i) != form.selGridVal(j, i)) {
                    winCond = false;
                    break;
                } else if (form.selGridVal(j, i) != 0) {
                    hitCount++;
                }
            }
            if (hitCount == form.gameData.gridSize()) {
                winCond = true;
                break;
            }
            hitCount = 0;
            for (int j = 0; j < form.gameData.gridSize(); j++) {
                // checks Horizontally
                if (form.selGridVal(i, j == 0 ? j+1 : j-1) != form.selGridVal(i, j)) {
                    winCond = false;
                    break;
                } else if (form.selGridVal(i, j) != 0) {
                    hitCount++;
                }
            }
            if (hitCount == form.gameData.gridSize()) {
                winCond = true;
                break;
            }
            if (i == 0) {
                hitCount = 0;
                for (int j = 0; j < form.gameData.gridSize(); j++) {
                    // checks bottom right → top left
                    if (form.selGridVal(j == 0 ? j+1 : j-1, j == 0 ? j+1 : j-1) != form.selGridVal(j, j)) {
                        winCond = false;
                        break;
                    } else if (form.selGridVal(j, j) != 0) {
                        hitCount++;
                    }
                }
                if (hitCount == form.gameData.gridSize()) {
                    winCond = true;
                    break;
                }
            }
            if (i == (form.gameData.gridSize())-1) {
                hitCount = 0;
                for (int j = 0; j < form.gameData.gridSize(); j++) {
                    // checks bottom left → top right
                    if (form.selGridVal(j == 0 ? i-j-1 : i-j+1, j == 0 ? j+1 : j-1) != form.selGridVal(i-j, j)) {
                        winCond = false;
                        break;
                    } else if (form.selGridVal(i-j, j) != 0) {
                        hitCount++;
                    }
                }
                if (hitCount == form.gameData.gridSize()) {
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
                if (0 < pos1+1 && pos2+1 <= form.gameData.gridSize()) {

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

            for (int i = 0; i < form.gameData.gridSize(); i++) {
                for (int j = 0; j < form.gameData.gridSize(); j++) {
                    Integer keyVal = 0;
                    if (hasPrio(i ,j)) {
                        keyVal = math_thing.nextInt();
                    } else {
                        keyVal = (math_thing.nextInt()+1) / 2;
                    }

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

    private boolean hasPrio(int pos1, int pos2) {
        if (
            // middle of grid check
            (
                (pos1 > 0 && pos2 > 0) && (pos1 < form.gameData.gridSize() && pos2 < form.gameData.gridSize()) &&
                NPC_VAL == form.selGridVal(pos1-1, pos2) ||
                NPC_VAL == form.selGridVal(pos1+1, pos2) ||
                NPC_VAL == form.selGridVal(pos1, pos2-1) ||
                NPC_VAL == form.selGridVal(pos1, pos2+1)
            ) ||
            // outermost lines check
            (
                (
                    pos1 == 0 && NPC_VAL == form.selGridVal(pos1, pos2+1)
                ) ||
                (
                    pos2 == 0 && NPC_VAL == form.selGridVal(pos1-1, pos2)
                )
            )
        ) {
            return true;
        }
        return false;
    }

    private boolean canSelect(int pos1, int pos2) {
        return (form.selGridVal(pos1, pos2) == 0);
    }

    private void showGrid() {
        System.out.print(form.gameData.gridRoof());
        for (int i = 0; i < form.gameData.gridSize(); i++) {
            System.out.println();
            for (int j = 0; j < form.gameData.gridSize(); j++) {
                System.out.print(form.gameData.gridConnectoString());
                if (PLAYER_VAL == form.selGridVal(i, j)) {
                    System.out.print(" O ");
                } else if (NPC_VAL == form.selGridVal(i, j)) {
                    System.out.print(" X ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.print(form.gameData.gridConnectoString());
        }
        System.out.print("\n" + form.gameData.gridFloor());
    }

}
