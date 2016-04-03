package com.gs.officeapp.java8;

 import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public class StreamTests {
	public static void main(String[] args) {
		List<Apple> inv = ApplesSort.createApples();
		
		final List<Apple> filteredApples = inv.parallelStream().filter((Apple a) -> a.getWeight() > 120).collect(toList());
		ApplesSort.displayList(filteredApples);
		
		final List<String> words = Arrays.asList(new String [] {"Iwillbe", "goingto", "dublin", "dublin"});
		words.stream().distinct().map((s) -> s.split("")).flatMap(Arrays::stream).distinct().forEach(System.out::print);
		
		System.out.println("\n");
		
		List<Integer> n1 = Arrays.asList(new Integer [] {1, 2, 3});
		List<Integer> n2 = Arrays.asList(new Integer [] {3, 4});
		
		/** My way is ok */
//		final List<Object> pairs = n1.stream().flatMap(i -> 
//			n2.stream().map(j -> new int [] {i, j})
//		).filter(pair -> {
//			int [] p = (int[]) pair;
//			return (p[0] + p[1]) % 3 == 0;
//		}).collect(toList());
		
		/** But, Book way, more intuitive */
		final List<Object> pairs = n1.stream().flatMap(i -> 
			n2.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int [] {i, j})
		).collect(toList());
		
		for (Object o : pairs) {
			if (o instanceof int []) {
				int [] pair = (int[]) o;
				System.out.println(Arrays.toString(pair));
			} else 
				System.out.println(o);
		}
	}
}
