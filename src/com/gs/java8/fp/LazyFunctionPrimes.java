package com.gs.java8.fp;

public class LazyFunctionPrimes {
	public static void main(String[] args) {
		int two = 0, three = 0, four = 0;
		final LazyList<Integer> from2 = from(2);
		two = from2.head();
//		three = from2.tail().head();
		four = from2.tail().tail().head();
		System.out.println("Two: " + two); 
		System.out.println("Three: " + three);
		System.out.println("Four: " + four);
		
		LazyList<Integer> numbers = primes(from2);
		printPrimes(numbers, 101);
	}

	private static void printPrimes(LazyList<Integer> numbers, final int MAX) {
		System.out.print("Prime Nos: ");
		
		while(!numbers.isEmpty()) {
			/** Every time the head will the prime number. It moves ahead after the division succeeds. */
			final Integer head = numbers.head();
			System.out.println(head);
			
			if (head >= MAX) {
				break;
			}
			
			/** tail will be like [8, f9].filter(by 2,3,5) */
			numbers = numbers.tail();
		}
	}
	
	static LazyList<Integer> from(int n) {
//		System.out.println("Evaluating from index: " + n);
		return new LazyIntList<Integer>(n, () -> from(n + 1));
	}
	
	static LazyList<Integer> primes(LazyList<Integer> numbers) {
		final Integer head = numbers.head();
		final LazyList<Integer> primeStream = numbers.tail().filter(n -> n % head != 0);
		return new LazyIntList<Integer>(head, () -> primes(primeStream));
	}
	
}
