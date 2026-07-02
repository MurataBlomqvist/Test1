package org;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.form.Gridable;
import org.form.gameForm;

public final class GameCalc implements Gridable {

    static Scanner myScanner = new Scanner(System.in);

    private final static int PLAYER_VAL = 1;
    private final static int NPC_VAL = 2;
    
    protected boolean hasPlayableTile(gameForm form) {
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

    protected boolean checkWinCondition(gameForm form) {
        boolean winCond = false;;

        winCond = calcWinCond(form);
        
        if (winCond) {
            if (form.getLastPlayed() == PLAYER_VAL) {
                form.setWinnerName("Player");
            } else {
                form.setWinnerName("Npc");
            }
        }
        return winCond;
    }

    protected boolean calcWinCond(gameForm form) {
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

    protected boolean playerInput(gameForm form) {
        
        int pos1 = 0;
        int pos2 = 0;
        try {
            while(true) {
                System.out.print("\nInsert the position in RowxColumn format ( second row first column = 2x1 ) :");
                String tmpIn = myScanner.nextLine();
                String[] tmpStr = tmpIn.split("x");
                if ((tmpStr.length == 2) && tmpStr[0].matches("[0-9]*") && tmpStr[1].matches("[0-9]*")) {
                    pos1 = Integer.valueOf(tmpStr[0]) - 1;
                    pos2 = Integer.valueOf(tmpStr[1]) - 1;

                    if ((0 < pos1+1 && pos1+1 <= form.gameData.gridSize()) && (0 < pos2+1 && pos2+1 <= form.gameData.gridSize())) {

                        if (!canSelect(form, pos1, pos2)) {
                            System.out.print("Try Again\n");
                            continue;
                        }

                        break;
                    }
                }
                
                System.out.print("Try Again\n");
            }
        } catch (Exception e) {
            System.out.print("Error in calculation\nContact someone idk");
            System.exit(0);
        }

        form.addToGrid(pos1, pos2, PLAYER_VAL);

        form.setLastPlayed(PLAYER_VAL);

        return true;
    }

    protected boolean npcInput(gameForm form) {
        boolean rtnBool = false;
        Random math_thing = new java.util.Random();
        while (!rtnBool) {
            Map<Integer, int[]> intMap = new HashMap<>();
            // Using treeSet for the naturalOrder implicit
            Set<Integer> intSetList = new TreeSet<>();

            for (int i = 0; i < form.gameData.gridSize(); i++) {
                for (int j = 0; j < form.gameData.gridSize(); j++) {
                    Integer keyVal = 0;
                    if (hasPrio(form, i ,j)) {
                        keyVal = (math_thing.nextInt()+1) / 15;
                    } else {
                        keyVal = (math_thing.nextInt()+1) / 20;
                    }

                    intSetList.add(keyVal);
                    intMap.put(keyVal, new int[] {i,j});
                }
            }
            // intSetList is naturally ordered
            // Higher value is more prio than low ones
            for (Integer integer : intSetList) {
                if (canSelect(form, intMap.get(integer)[0], intMap.get(integer)[1])) {
                    form.addToGrid(intMap.get(integer)[0], intMap.get(integer)[1], NPC_VAL);
                    form.setLastPlayed(NPC_VAL);
                    rtnBool = true;
                    break;
                }
            }
        }
        
        return true;
    }

    private boolean hasPrio(gameForm form, int pos1, int pos2) {
        boolean res = false;
        if ((0 < pos1 && pos1 < form.gameData.gridSize()-1) && (0 < pos2 && pos2 < form.gameData.gridSize()-1)) {
            // middle of grid check
            if (
                NPC_VAL == form.selGridVal(pos1-1, pos2) ||
                NPC_VAL == form.selGridVal(pos1+1, pos2) ||
                NPC_VAL == form.selGridVal(pos1, pos2-1) ||
                NPC_VAL == form.selGridVal(pos1, pos2+1)
            ) {
                res = true;
            } 
        } else if (
            (
                (pos1 == 0 && pos2 > form.gameData.gridSize()-1) && NPC_VAL == form.selGridVal(pos1, pos2+1)
            ) ||
            (
                (pos2 == 0 && pos1 < 0) && NPC_VAL == form.selGridVal(pos1-1, pos2)
            )
        ) {
            res = true;
        }

        return res;
    }

    private boolean canSelect(gameForm form, int pos1, int pos2) {
        return (form.selGridVal(pos1, pos2) == 0);
    }

}
