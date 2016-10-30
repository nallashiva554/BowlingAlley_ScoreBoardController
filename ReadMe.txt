1. The program contains the below core bean classes in com.abc.beans package:
	a. Game
	b. Frame
	c. RegularFrame
	d. SpecialFrame
2. All constants are defined in the file Constants.java in com.abc.util package
3. ScoreBoardController.java file in the com.abc.service package contains service methods to roll pins and computing score for the Bowling game
4. PlayBowling.java in the com.abc.ui package is the main class that shall be able to handle any runs
5. TestBowlingAlley.java in the com.abc.test package contains junit test methods to handle some important cases

Assumptions:
1. It is assumed that the correct number of rolls is passed as input to the program, else it results in an exception causing the program to terminate.
2. Game is programmed based on the instructions at http://bowling.about.com/od/rulesofthegame/a/bowlingscoring.htm