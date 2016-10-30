package com.abc.service;

import com.abc.beans.Frame;
import com.abc.beans.Game;
import com.abc.beans.RegularFrame;
import com.abc.beans.SpecialFrame;
import com.abc.util.Constants;

/**
 * @author nalla
 * @description ScoreBoardController contains service methods to control and process multiple games
 */
public class ScoreBoardController {
	
	/* Returns score for an attempt in a frame*/
	public static int getAttemptScore(Frame f, int attempt) {
		if(f!=null && attempt<=f.getNumOfAttempts()) {
			return f.getRolls()[attempt-1];
		}
		return 0;
	}

	/* Returns true if the frame has a strike, otherwise false */
	public static boolean isStrike(Frame f) {
		boolean isStrike = false;
		if(f!=null && getAttemptScore(f, 1)==Constants.MAX_PINS) {
			isStrike = true;
		}
		return isStrike;
	}
	
	/* Returns true if the frame has a spare, otherwise false */
	public static boolean isSpare(Frame f) {
		boolean isSpare = false;
		if(f!=null && f.getNumOfAttempts()>=Constants.MAX_ATTEMPTS && (getAttemptScore(f, 1)+ getAttemptScore(f, 2))==Constants.MAX_PINS) {
			isSpare = true;
		}
		return isSpare;
	}

	/* Returns true if the frame is a last frame, otherwise false */
	public static boolean isLastFrame(int frameNumber) {
		return Constants.MAX_FRAMES == frameNumber;
	}

	/* Creates and returns a new frame - either a Regular or a Special frame*/
	public static Frame createNewFrame(int frameNumber) {
		Frame newFrame;
		if(!isLastFrame(frameNumber))
			newFrame = new RegularFrame();
		else
			newFrame = new SpecialFrame();
		return newFrame;
	}
	
	/* Compute and return Strike bonus where the input frame is a Strike */
	public static int strikeBonus(Game g, int currentFrameNumber) {
		Frame gameFrames[] = g.getFrames();
		Frame currentFrame = gameFrames[currentFrameNumber-1];
		Frame nextFrame, nextNextFrame;
		if(isLastFrame(currentFrameNumber))
			return getAttemptScore(currentFrame, 2) + getAttemptScore(currentFrame, 3);
		else {
			nextFrame = gameFrames[currentFrameNumber];		
			if(isLastFrame(currentFrameNumber+1)) 
				return getAttemptScore(nextFrame, 1) + getAttemptScore(nextFrame, 2);
			else if(isStrike(nextFrame)) {
				nextNextFrame = gameFrames[currentFrameNumber+1];
				return getAttemptScore(nextFrame, 1) + getAttemptScore(nextNextFrame, 1);
			}
			else
				return getAttemptScore(nextFrame, 1) + getAttemptScore(nextFrame, 2);
		}
	}

	/* Compute and return Spare bonus where the input frame is a Spare */
	public static int spareBonus(Game g, int currentFrameNumber) {
		Frame gameFrames[] = g.getFrames();
		Frame currentFrame = gameFrames[currentFrameNumber-1];
		Frame nextFrame;
		if(isLastFrame(currentFrameNumber))
			return getAttemptScore(currentFrame, 3);
		else {
			nextFrame = gameFrames[currentFrameNumber];		
			return getAttemptScore(nextFrame, 1);
		}
	}

	/* Returns score for a frame in a game */
	public static int getFrameScore(Game g, int currentFrameNumber) {
		Frame[] gameFrames = g.getFrames();
		Frame currentFrame = gameFrames[currentFrameNumber-1];
		
		if(isStrike(currentFrame))
			return getAttemptScore(currentFrame, 1) + strikeBonus(g, currentFrameNumber);
		else if(isSpare(currentFrame))
			return getAttemptScore(currentFrame, 1) + getAttemptScore(currentFrame, 2) + spareBonus(g, currentFrameNumber);
		else 
			return getAttemptScore(currentFrame, 1) + getAttemptScore(currentFrame, 2);
	}

	/* Returns score for input game */
	public static int getGameScore(Game g) {
		int gameScore = 0;
		int totalNumFramesSoFar = g.getCurrentFrame();
		for(int i=0;i<totalNumFramesSoFar;i++)
			gameScore += getFrameScore(g, i+1);
		return gameScore;
	}
	
	/* Performs a roll operation in a game */
	public static void roll(Game game, int pins) throws Exception {
		int currentFrameNumber = game.getCurrentFrame();
		Frame gameFrames[] = game.getFrames();
		Frame currentFrame;
		boolean isNewFrame = false;
		
		if(currentFrameNumber==0) {
			currentFrameNumber++;
			currentFrame = createNewFrame(currentFrameNumber);
			isNewFrame = true;
		}
		else {
			currentFrame = gameFrames[currentFrameNumber-1];
			if((isStrike(currentFrame)||isSpare(currentFrame) || currentFrame.getNumOfAttempts()==Constants.MAX_ATTEMPTS) && 
					!isLastFrame(currentFrameNumber)) {
				currentFrameNumber++;
				currentFrame = createNewFrame(currentFrameNumber);
				isNewFrame = true;
			}
		}
		/* If at any point, pins> MaxPins (or) it's a regular frame, not new but overall pins for the frame summed up to more than MaxPins (or)
		 * 		it is a special frame, not a strike and in 2nd attempt and overall pins summed upto more than MaxPins, then throw an exception*/
		if((pins>Constants.MAX_PINS) || (!isLastFrame(currentFrameNumber) && !isNewFrame && (getAttemptScore(currentFrame, 1)+pins>Constants.MAX_PINS))
			|| (isLastFrame(currentFrameNumber) && !isNewFrame && !isStrike(currentFrame) && currentFrame.getNumOfAttempts()<Constants.MAX_ATTEMPTS && 
					(getAttemptScore(currentFrame, 1)+pins>Constants.MAX_PINS)))
				throw new Exception("Number of pins played exceeded the maximum pins allowed for a frame");
			
		int currentAttempts = currentFrame.getNumOfAttempts();
		currentFrame.getRolls()[currentAttempts] = pins;
		currentFrame.setNumOfAttempts(++currentAttempts);
		game.setCurrentFrame(currentFrameNumber);
		gameFrames[currentFrameNumber-1] = currentFrame;
		game.setFrames(gameFrames);
	}

	/* Returns the frame result for a given input game and frame*/
	public static String outputFrameContent(Game game, int frameNumber) {
		StringBuilder frameContent = new StringBuilder();
		Frame frame = game.getFrames()[frameNumber-1];
		int[] rolls = frame.getRolls();
		int numOfAttempts = frame.getNumOfAttempts();
	
		for(int i=0;i<numOfAttempts;i++) {
			if(i==0) {
				if(isStrike(frame))
					frameContent.append("X");
				else
					frameContent.append(rolls[i]);
			}
			else if(i==1) {
				if(isSpare(frame))
					frameContent.append("\\");
				else
					frameContent.append(" "+rolls[i]);
			}
			else
				frameContent.append(" "+rolls[i]);
		}		
		return frameContent.toString();
	}
	
	/* Prints game results or score monitor*/
	public static void displayGameFrames(Game game) {
		int currentFrameNumber = game.getCurrentFrame();
		int runningScore = 0;
		
		System.out.print("Frame:\t\t");
		for(int i=0;i<Constants.MAX_FRAMES;i++) {
			System.out.print("Frame"+(i+1)+"\t");
		}
		System.out.println();
		
		System.out.print("Result:\t\t");
		for(int i=0;i<currentFrameNumber;i++) {
			System.out.print(ScoreBoardController.outputFrameContent(game, (i+1))+"\t");
		}
		System.out.println();

		System.out.print("Score:\t\t");
		for(int i=0;i<currentFrameNumber;i++) {
			System.out.print(ScoreBoardController.getFrameScore(game, (i+1))+"\t");
		}
		System.out.println();

		System.out.print("Running Total:\t");
		for(int i=0;i<currentFrameNumber;i++) {
			runningScore += ScoreBoardController.getFrameScore(game, (i+1));
			System.out.print(runningScore+"\t");
		}
		System.out.println();		
		System.out.println("GameScore:"+ScoreBoardController.getGameScore(game));
	}	
}