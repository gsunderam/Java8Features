package com.gs.officeapp.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

@SuppressWarnings("unused")
public class PrimeNum {
	public static void main(String[] args) {
		long fast = Long.MAX_VALUE;
		
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			final Map<Boolean, List<Integer>> primes = partitionPrimes(100000);
			long timetaken = System.nanoTime() - start;
			
			if (timetaken < fast) fast = timetaken;
		}
		
		ApplesSort.display("Fastest Exec time: " + fast);
	}
	
	public static boolean isPrime(int n) {
		return IntStream.rangeClosed(2, (int) Math.sqrt(n)).boxed().noneMatch(i -> n % i == 0);
	}
	
	public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed().
				collect(partitioningBy(number -> isPrime(number)));
	}
	
	public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
		return IntStream.rangeClosed(2, n).boxed().collect(new PrimesCollector());
	}
	
	/**
	 * New optimal prime number checking
	 * 
	 * @param primes Primes found so far below the candidate
	 * @param candidate candidate for which isPrime is sought
	 * 
	 * @return true/false
	 */
	public static boolean isPrime(List<Integer> primes, int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		List<Integer> primesSubList = getPrimesBelowCandidateRoot(primes, i -> i <= candidateRoot);
		return primesSubList.stream().noneMatch(i -> candidate % i == 0);
	}

	public static<T> List<T> getPrimesBelowCandidateRoot(List<T> items, Predicate<T> p) {
		int i = 0;
		
		for (T item : items) {
			if (!p.test(item)) { //Nice way to check and return quickly.
				return items.subList(0, i);
			}
			
			i++;
		}
		
		return items;
	}
}
