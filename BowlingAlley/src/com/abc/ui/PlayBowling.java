package com.abc.ui;

import com.abc.beans.Game;
import com.abc.service.ScoreBoardController;

/**
 * @author nalla
 * @description This class is the main class and can be used to start a game and see the results of the game
 */
public class PlayBowling {

	public static void main(String[] args) throws Exception {
		Game game = new Game();

		for(int i=0;i<19;i++) {
			ScoreBoardController.roll(game, 5);
		}
		ScoreBoardController.roll(game, 5);
		ScoreBoardController.roll(game, 10);
		
/*		ScoreBoardController.roll(game, 10);
		ScoreBoardController.roll(game, 8);
		ScoreBoardController.roll(game, 2);
		ScoreBoardController.roll(game, 10);
*/
		ScoreBoardController.displayGameFrames(game);
	}

}
