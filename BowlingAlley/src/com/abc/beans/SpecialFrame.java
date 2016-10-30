package com.abc.beans;

/**
 * @author nalla
 * @description Every frame except the last frame can have upto 3 attempts 
 */
public class SpecialFrame extends Frame{
	public SpecialFrame() {
		rolls = new int[3];
	}
}