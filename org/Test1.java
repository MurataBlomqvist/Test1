package org;

import org.form.Gridable;
import org.form.gameForm;

import java.util.Objects;
import java.util.Scanner;

public class Test1 implements Gridable {

    static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        Game game = new Game();
        gameForm form = setup();

        if (Objects.isNull(form)) {
            return;
        }

        game.gameOn(form);
        
        myScanner.close();
    }

    private static gameForm setup() {
        int in1 = 0;
        try {
            while(true) {
                System.out.println("Setup grid for play(2-50)");
                System.out.print("Insert size for grid:");
                int tmpIn = myScanner.nextInt();
                myScanner.nextLine();
                if (1 < tmpIn && tmpIn < 51) {
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
        gameForm form = new gameForm(new int[gridSize][gridSize]);
        StringBuilder sbDivider = new StringBuilder();
        StringBuilder sbRoof = new StringBuilder();
        StringBuilder sbFloor = new StringBuilder();
        
        for (int i = 0; i < gridSize; i++) {
            sbDivider.append(DIVIDER);
            sbRoof.append(ROOF);
            sbFloor.append(FLOOR);
            for (int j = 0; j < gridSize; j++) {
                // init the grid
                form.addToGrid(i, j, 0);
            }
        }
        // setting the grid layout to match grid size
        form.setGameRecord(
            CONNECTO,
            sbDivider.append(DIVIDER).toString(),
            sbRoof.toString(),
            sbFloor.toString(),
            gridSize
        );
        return form;
    }

}