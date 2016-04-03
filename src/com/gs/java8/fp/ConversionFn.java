package com.gs.java8.fp;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public class ConversionFn {
	/**
	 * Functional programming technique 1. Always try to return formulae like 
	 * F to Celsius as a function that could be used with variable args for
	 * various conversions
	 * 
	 * @param f
	 * @param b
	 * @return
	 */
	static DoubleUnaryOperator convert(double f, double b) {
		return (double x) -> f * x + b;
	}
	
	static <S, T, R> Function<S, R> compose(Function<T, R> f, Function<S, T> g) {
		return x -> f.apply(g.apply(x));
	}
	
	static <A> Function<A, A> repeat(int n, Function<A, A> f) {
		return n == 0 ? x -> x : compose(f, repeat(n - 1, f)); 
	}
	
	public static void main(String[] args) {
		final DoubleUnaryOperator celsToFah = convert(9.0/5, 32);
		System.out.println(celsToFah.applyAsDouble(20d));
		final Function<Integer, Integer> fn = repeat(3, (Integer x) -> x + 3);
		final Integer n = fn.apply(3);
		System.out.println("Repeat is " + n);
	}

}
