package com.abc.beans;

/**
 * @author nalla
 * @description Frame consists of an array of rolls and number of attempts played so far
 */
public abstract class Frame {
	protected int rolls[];
	protected int numOfAttempts;
	
	public int[] getRolls() {
		return rolls;
	}
	public void setRolls(int[] rolls) {
		this.rolls = rolls;
	}	
	public int getNumOfAttempts() {
		return numOfAttempts;
	}
	public void setNumOfAttempts(int numOfAttempts) {
		this.numOfAttempts = numOfAttempts;
	}	
	
	public String toString() {
		String returnValue = "";
		if(rolls!=null) {
			for(int i=0;i<rolls.length;i++) {
				returnValue += rolls[i] + " ";
			}
		}
		return returnValue;
	}
}
