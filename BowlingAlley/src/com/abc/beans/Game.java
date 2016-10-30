package com.abc.beans;

/**
 * @author nalla
 * @description Game consists of frames and the number of rames played so far	
 */
public class Game {
	private int currentFrameNum;
	private Frame frames[];
	
	public Game() {
		currentFrameNum = 0;
		frames = new Frame[10];
	}

	public int getCurrentFrame() {
		return currentFrameNum;
	}

	public void setCurrentFrame(int currentFrameNumber) {
		this.currentFrameNum = currentFrameNumber;
	}

	public Frame[] getFrames() {
		return frames;
	}

	public void setFrames(Frame[] frames) {
		this.frames = frames;
	}
}
