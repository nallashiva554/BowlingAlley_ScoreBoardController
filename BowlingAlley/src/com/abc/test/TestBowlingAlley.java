package com.abc.test;

import com.abc.beans.Game;
import com.abc.service.ScoreBoardController;

import junit.framework.TestCase;

public class TestBowlingAlley extends TestCase{
	  private Game game;
	  
	  /* create a game instance*/
	  protected void setUp() throws Exception {
		  game = new Game();
	  }
	  /* Roll multiple times */
	  private void rollManyAttempts(int numOfAttempts, int pins) throws Exception {
		  for (int i = 0; i < numOfAttempts; i++)
			  ScoreBoardController.roll(game, pins);
	  }
	  /*Roll a Strike */
	  private void rollAStrike() throws Exception {
		  ScoreBoardController.roll(game, 10);
	  }
	  /*Roll a Spare */
	  private void rollASpare() throws Exception {
		  ScoreBoardController.roll(game, 4);
		  ScoreBoardController.roll(game, 6);		  
	  }
	  /*Test - Roll all Threes */
	  public void testAllThrees() throws Exception {
		  rollManyAttempts(20, 3);
		  assertEquals(60, ScoreBoardController.getGameScore(game));
	  }
	  /*Test - Roll All Gutters */
	  public void testAllGutterRolls() throws Exception {
		  rollManyAttempts(20, 0);
		  assertEquals(0, ScoreBoardController.getGameScore(game));
	  }
	  /*Test - Roll all Strikes */
	  public void testAllStrikes() throws Exception {
		  rollManyAttempts(12, 10);
		  assertEquals(300, ScoreBoardController.getGameScore(game));
	  }
	  /*Test - Roll all Spares */
	  public void testAllSpares() throws Exception {
		  rollManyAttempts(20, 5);
		  ScoreBoardController.roll(game, 5);
		  assertEquals(150, ScoreBoardController.getGameScore(game));
	  }
	  /*Test - Roll a Single first strike */
	  public void testFirstStrike() throws Exception {
		  rollAStrike();
		  ScoreBoardController.roll(game, 8);
		  ScoreBoardController.roll(game, 1);
		  rollManyAttempts(16, 0);
		  assertEquals(28, ScoreBoardController.getGameScore(game));		  
	  }
	  /*Test - Roll a Single first spare */
	  public void testFirstSpare() throws Exception {
		  rollASpare();
		  ScoreBoardController.roll(game, 6);
		  rollManyAttempts(17,0);
		  assertEquals(22, ScoreBoardController.getGameScore(game));
	  }
	  /*Test - Roll a Single last strike */
	  public void testLastStrike() throws Exception {
		  rollManyAttempts(18, 0);
		  rollAStrike();
		  ScoreBoardController.roll(game, 8);
		  ScoreBoardController.roll(game, 10);		  
		  assertEquals(28, ScoreBoardController.getGameScore(game));
	  }
	  /*Test - Roll a Single last spare */
	  public void testLastSpare() throws Exception {
		  rollManyAttempts(18, 0);
		  rollASpare();
		  ScoreBoardController.roll(game, 8);		  
		  assertEquals(18, ScoreBoardController.getGameScore(game));
	  }
}