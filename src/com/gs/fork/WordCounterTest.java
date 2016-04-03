package com.gs.fork;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCounterTest {
	public static void main(String[] args) {
//		System.out.print(args[0]);
		final Stream<Character> stream = IntStream.range(0, args[0].length()).mapToObj(args[0]::charAt);
//		countWords(stream);
		countWordsWithSpliterator(args);
	}

	private static void countWords(final Stream<Character> stream) {
		final WordCounter words = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		System.out.print(words.getCounter());
	}
	
	private static void countWordsWithSpliterator(final String [] args) {
		Spliterator<Character> splitter = new WordCounterSpliterator(args[0], 0);
		Stream<Character> stream = StreamSupport.stream(splitter, true);
		countWords(stream);
	}

}
