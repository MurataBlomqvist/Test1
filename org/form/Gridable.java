package org.form;

public interface Gridable {
    String CONNECTO = "|";
    String DIVIDER = "|---";
    String ROOF = " ___";
    String FLOOR = " ```";
    int PLAYER_VAL = 1;
    int NPC_VAL = 2;

    default void showGrid(gameForm form) {
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