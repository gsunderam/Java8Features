package com.gs.java8.fp;

public class TailRecursion {
	public static void main(String[] args) {
		int result = factorial(8);
		System.out.println("Result is: " + result);
	}

	private static int factorial(int n) {
		return factorialHelper(1, n);
	}

	private static int factorialHelper(int initial, int n) {
		return n == 1 ? initial : factorialHelper(initial * n, n -1);
	}
}
