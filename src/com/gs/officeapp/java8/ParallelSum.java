package com.gs.officeapp.java8;

import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

class Adder {
	int total;
	
	public void add(int n) {
		total += n;
	}

}
/**
 * Demonstrated how not to use paralle streams for shared mutable state.
 * 
 * @author gsunderam
 *
 */
public class ParallelSum {
	public static void main(String[] args) {
		Adder adder = new Adder();
		/** Incorrect use of parallel streams as total is a shared var and is not synced! */
		//IntStream.rangeClosed(1, 10000).parallel().forEach((i) -> adder.add(i));
		
		IntStream.rangeClosed(1, 10000).forEach((i) -> adder.add(i));//correct. this is a serial op
		System.out.println("total is " + adder.total);
		
		UnaryOperator<String> unary = (s) -> s.toUpperCase(); //lambda
		final String upper = unary.apply("amex_is_excellent_and_joyful");
		System.out.println(upper);
		UnaryOperator<String> unaryMethRef = String::toLowerCase;
		System.out.println(unaryMethRef.apply(upper));
	}
}
