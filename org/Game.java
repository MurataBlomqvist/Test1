package org;

import org.form.Gridable;
import org.form.gameForm;

public class Game implements Gridable {

    static private GameCalc gameCalc = new GameCalc();

    private gameForm form;
    
    public void gameOn(gameForm form) {
        this.form = form;

        while (true) {
            
            showGrid(this.form);
            
            if (gameCalc.playerInput(this.form) & gameCalc.checkWinCondition(this.form)) {
                showGrid(form);
                System.out.println("\nThe winner is : " + form.getWinnerName());
                break;
            }
            if (!gameCalc.hasPlayableTile(this.form)) {
                showGrid(this.form);
                System.out.println("\nNo playable tiles left. Its a tie!");
                break;
            }

            if (gameCalc.npcInput(this.form) & gameCalc.checkWinCondition(this.form)) {
                showGrid(form);
                System.out.println("\nThe winner is : " + form.getWinnerName());
                break;
            }
            if (!gameCalc.hasPlayableTile(this.form)) {
                showGrid(this.form);
                System.out.println("\nNo playable tiles left. Its a tie!");
                break;
            }

        }

    }

}
