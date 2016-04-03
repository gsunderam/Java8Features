package com.gs.officeapp.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ApplesSort {
	public static List<Apple> createApples() {
		List<Apple> apples = new ArrayList<Apple>();
		Apple a1 = new Apple("Indian Apple", "green", 100);
		Apple a2 = new Apple("US Apple", "red", 200);
		Apple a3 = new Apple("UK app", "yellow", 119);
		Apple a4 = new Apple("BrazilianApple", "blue", 134);
		Apple a5 = new Apple("New Zealand Apple", "green", 198);
		
		apples.add(a1);apples.add(a2);apples.add(a3);
		apples.add(a4);apples.add(a5);
		
		return apples;
	}
	
	public static <T> List<T> filterApples(List<T> inv, Predicate<T> t) {
		List<T> result = new ArrayList<T>();
		
		for (T fruit : inv) {
			if (t.test(fruit)) result.add(fruit);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		final List<Apple> inv = createApples();
		
		/** 
		 * Java 8 func style programming. Pass the method code isGreen in the class Apple as a "reference" to
		 * the filterApples method
		 * 
		 * Apple::isGreen --> This is a method reference. IIIr to passing functions in javascript
		 */
		
		List<Apple> filteredApples = filterApples(inv, Apple::isGreen);
		
		filteredApples = filterApples(inv, Apple::isHeavy); //another method.
		
		filteredApples = filterApples(inv, (Apple a) -> "green".equals(a.color)); //Lambda notation
		
		filteredApples = filterApples(inv, (Apple a) -> "red".equals(a.color) || a.wt < 120); //Lambda notation
		
//		for (Apple apple : filteredApples)
//			System.out.println(apple);
		
		/** Using streams this time to make use of all cores, per java8 claims */
//		inv.stream().filter((Apple a) -> a.wt > 100 && a.wt < 150).forEach(System.out::println);
		
//		inv.parallelStream().filter((Apple a) -> a.wt > 100 && a.wt < 150).forEach(System.out::println);
		
//		testConstructorReferences();
		
//		sortApples();
		
		chainPredicates();
	}
	
	private static void chainPredicates() {
		Predicate<Apple> isRedApple = Apple::isGreen;
		final Predicate<Apple> isGreenOrHeavy = isRedApple.or(Apple::isHeavy);
		List<Apple> filteredApples = filterApples(createApples(), isGreenOrHeavy.negate());
		
		for (Apple apple : filteredApples)
			System.out.println(apple);
	}

	/**
	 * Sort using the new java 8 style. Chanining comparators
	 */
	private static void sortApples() {
		List<Apple> inv = createApples();
		inv.sort(Comparator.comparing(Apple::getWeight).thenComparing((a) -> a.getName()));
		for (Apple apple : inv)
			System.out.println(apple);
		
	}

	public static void testConstructorReferences() {
		BiFunction<String, String, Apple> appleFunc = Apple::new;
		
		final Apple apple = appleFunc.apply("TestAple", "LightGray");
		System.out.println(apple);
		
		TriFunction<String, String, Integer, Apple> triAppleFunc = Apple::new;
		System.out.println(triAppleFunc.apply("GalaOrganic", "DarkRed", 3));
	}
	
	public static <T> void displayList(List<? extends T> list) {
		for(T t : list) System.out.println(t);
	}
	
	public static void display(Object o) {
		if (o != null) System.out.println(o);
	}
} 

interface TriFunction<T, U, V, R> {
	public R apply(T t, U u, V v);
}
