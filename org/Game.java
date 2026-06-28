package org;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.form.gameForm;

public class Game {

    static Scanner myScanner = new Scanner(System.in);

    private gameForm form;

    private Logger log = Logger.getLogger(getClass().getName());

    private final static int PLAYER_VAL = 1;
    private final static int NPC_VAL = 2;
    
    public void gameOn(gameForm form) {
        this.form = form;

        while (true) {
            
            showGrid();
            
            if (playerInput() && checkWinCondition()) {
                printWinner();
                break;
            }
            if (!hasPlayableTile()) {
                System.out.println("\nNo playable tiles left. Its a tie!");
                break;
            }

            if (npcInput() && checkWinCondition()) {
                printWinner();
                break;
            }
            if (!hasPlayableTile()) {
                System.out.println("\nNo playable tiles left. Its a tie!");
                break;
            }

        }
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
                if (tmpIn.matches("[1-" + form.gameData.gridSize() + "][x][1-" + form.gameData.gridSize() + "]")
                && (0 < Integer.valueOf(tmpIn.substring(0, 1)) && Integer.valueOf(tmpIn.substring(2, 3)) <= form.gameData.gridSize())) {
                    String[] tmpStr = tmpIn.split("x");
                    pos1 = Integer.valueOf(tmpStr[0]) - 1;
                    pos2 = Integer.valueOf(tmpStr[1]) - 1;
                    break;
                }
                System.out.println("Try Again\n");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

            if (!canSelect(pos1, pos2)) {
                System.out.println("Try Again\n");
                return false;
            }

            form.addToGrid(pos1, pos2, PLAYER_VAL);

            form.setLastPlayed(PLAYER_VAL);

            return true;
    }

    private boolean npcInput() {
        while (NPC_VAL == form.getLastPlayed()) {
            Map<Integer, int[]> intMap = new HashMap<>();
            List<Integer> intList = new ArrayList<>();
            for (int i = 0; i < form.gameData.gridSize(); i++) {
                for (int j = 0; j < form.gameData.gridSize(); j++) {
                    int keyVal = (int)Math.random() * 1000;
                    intList.add(keyVal);
                    intMap.put(keyVal, new int[] {i,j});
                }
            }
            intList.sort( (a, b) -> {return a.compareTo(b);});
            for (Integer integer : intList) {
                if (canSelect(intMap.get(integer)[0], intMap.get(integer)[1])) {
                    form.addToGrid(intMap.get(integer)[0], intMap.get(integer)[1], NPC_VAL);
                    form.setLastPlayed(NPC_VAL);
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
        System.out.print(form.gameData.gridRoof());
        for (int i = 0; i < form.gameData.gridSize(); i++) {
            System.out.println();
            for (int j = 0; j < form.gameData.gridSize(); j++) {
                System.out.print(form.gameData.gridConnectoString());
                System.out.print(" " + form.selGridVal(i, j) + " ");
            }
            System.out.print(form.gameData.gridConnectoString());
        }
        System.out.print("\n" + form.gameData.gridFloor());
    }

}
