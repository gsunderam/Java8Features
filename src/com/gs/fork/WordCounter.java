package com.gs.fork;

public class WordCounter {
	int counter;
	boolean lastspace; //to take care of multiple spaces between words
	
	public WordCounter(int counter, boolean lastspace) {
		this.counter = counter;
		this.lastspace = lastspace;
	}
	
	/**
	 * Needs to return the WordCounter object that will be used for the next iteration
	 * 
	 * @param ch
	 * @return
	 */
	public WordCounter accumulate(Character ch) {;
		if (Character.isWhitespace(ch)) {
			return lastspace ? this : new WordCounter(counter, true);
		} else return lastspace ? new WordCounter(counter + 1, false) : this ;
	}
	
	/**
	 * Only called for parallel streams. Always the results from two tasks will be combined.
	 * That's how the fork/join java 7 framework is designed. Hence only two counters
	 * @param wc
	 * @return
	 */
	public WordCounter combine(WordCounter wc) {
		System.out.println("Combiner : " + counter + " " + wc.counter);
		return new WordCounter(counter + wc.counter, wc.lastspace);
	}

	public int getCounter() {
		return counter;
	}
}
