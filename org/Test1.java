package org;

import org.form.gameForm;
import java.util.Objects;
import java.util.Scanner;

public class Test1 {

    static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        Game game = new Game();
        gameForm form = setup();

        if (Objects.isNull(form)) {
            return;
        }

        game.gameOn(form);
        
    }

    private static gameForm setup() {
        int in1 = 0;
        try {
            while(true) {
                System.out.println("Setup grid for play(0-50)");
                System.out.print("Insert size for grid:");
                int tmpIn = myScanner.nextInt();
                myScanner.nextLine();
                if (0 < tmpIn && tmpIn < 51) {
                    in1 = tmpIn;
                    break;
                }
                System.out.println("Try Again\n");
            }
        } catch (Exception e) {
            System.out.println("Error during setup");
            return null;
        }
        return setupParams(in1);
    }

    private static gameForm setupParams(int gridSize) {
        gameForm form = new gameForm(gridSize, new int[gridSize][gridSize]);
        StringBuilder sbDivider = new StringBuilder();
        StringBuilder sbRoof = new StringBuilder();
        StringBuilder sbFloor = new StringBuilder();
        
        for (int i = 0; i < gridSize; i++) {
            sbDivider.append(form.getGridDivider());
            sbRoof.append(form.getGridRoof());
            sbFloor.append(form.getGridFloor());
            for (int j = 0; j < gridSize; j++) {
                // init the grid
                form.addToGrid(i, j, 0);
            }
        }
        // setting the grid layout to match grid size
        form.setGridDivider(sbDivider.append("|").toString());
        form.setGridRoof(sbRoof.toString());
        form.setGridFloor(sbFloor.toString());
        return form;
    }

}