package com.abc.beans;

/**
 * @author nalla
 * @description Every frame except the last frame will have atmost 2 attempts 
 */
public class RegularFrame extends Frame{
	
	public RegularFrame() {
		rolls = new int[2];
	}
}
