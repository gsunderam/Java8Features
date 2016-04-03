package com.gs.java8.fp;

import java.util.stream.IntStream;

/**
 * Throws an error at line 30 as streams can't be lazily evaluated
 * @author gsunderam
 *
 */
public class LazyStream {
	public static void main(String[] args) {
		primes(numbers());
	}
	
	static IntStream numbers() {
		return IntStream.iterate(2, n -> n + 1).limit(25);
	}
	
	static int head(IntStream numbers) {
		return numbers.findFirst().getAsInt();
	}
	
	static IntStream tail(IntStream numbers) {
		return numbers.skip(1);
	}
	
	static IntStream primes(IntStream numbers) {
		final int head = head(numbers);
		final IntStream primeStream = tail(numbers).filter(n -> n % head != 0);
		//Throws error here because of terminal operations head and skip
		return IntStream.concat(IntStream.of(head), primes(primeStream));
	}
 
}
