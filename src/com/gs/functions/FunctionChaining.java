package com.gs.functions;

import java.util.function.Function;

public class FunctionChaining {
	public static void main(String[] args) {
		Function<Integer, Integer> f = (Integer x) -> x + 1; //std notation
		Function<Integer, Integer> g = x -> x * 2; //Type Inference
		
		Function<Integer, Integer> h = f.andThen(g); //g(f(x))
		int result = h.apply(9);
		System.out.println("f.andthen(g): " + result);
		
		Function<Integer, Integer> k = f.compose(g); //f(g(x))
		
		/** This is just like Java Script. Declare functions first and then apply using arguments */
		result = k.apply(9);
		System.out.println("f.compose(g): " + result);
		f = x -> x;
		System.out.println("Identity fn: 3 " + f.apply(3));
	}

}
