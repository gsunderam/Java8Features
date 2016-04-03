package com.gs.fork;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
	private final String str;
	private int currentPos;
	
	public WordCounterSpliterator(String str, int currentPos) {
		this.str = str;
		this.currentPos = currentPos;
	}

	/**
	 * Similar to accumulator. Invoked for serial algorithms as well. word counter accumulator consumes this.
	 */
	@Override
	public boolean tryAdvance(Consumer<? super Character> action) {
		try {
			final char c = str.charAt(currentPos++);
			action.accept(c);
			return currentPos < str.length();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Only invoked for parallel algorithms to know how to split the tasks, just like the fork join framework
	 * introduced in java 7
	 */
	@Override
	public Spliterator<Character> trySplit() {
		try {
			int currentSize = str.length() - currentPos;
			
			if (currentSize < 10) return null; //To signal serial counting can begin.
			
			//Split in to two just like the fork sum calc
			for(int splitpos = currentSize / 2 + currentPos; splitpos < str.length(); splitpos++) {
				if (Character.isWhitespace(str.charAt(splitpos))) {
					final String splitstring = str.substring(currentPos, splitpos);
					currentPos = splitpos;
					final WordCounterSpliterator splitted = new WordCounterSpliterator(splitstring, 0);
					return splitted;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return null;
	}

	@Override
	public long estimateSize() {
		return str.length() - currentPos;
	}

	@Override
	public int characteristics() {
		return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
	}

}
