package com.gs.officeapp.java8;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;



import com.sun.javafx.scene.traversal.WeightedClosestCorner;

import static java.util.stream.Collectors.*;

public class Tuples {
	public enum Weights { HEAVY, LIGHT, MEDIUM }
	
	public static void main(String[] args) {
//		final Stream<int[]> tuples = IntStream.rangeClosed(1, 1000).boxed().flatMap(a -> 
//			IntStream.rangeClosed(a, 100)
//			.filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
//			.mapToObj(b -> new int [] {a, b, (int) Math.sqrt(a * a + b * b)})
//		);
//		
//		tuples.limit(20).forEach(arr -> System.out.println(arr[0] + "\t" + arr[1] + "\t" + arr[2]));
		
		/** Fibonocci tuples */
//		Stream.iterate(new int [] {0, 1}, t -> new int [] { t[1], t[0] + t[1]})
//							.limit(14)
//							.forEach(t -> System.out.println(t[0] + "," + t[1]));
		
		/** Collector functions */
		final Map<String, List<Apple>> groupedApples = ApplesSort.createApples().stream().collect(groupingBy(Apple::getColor));
		stdout(groupedApples); //group by like SQL queries
		
		Map<String, Map<Weights, List<Apple>>> applesByColorAndWeight = 
				ApplesSort.createApples().stream().collect(groupingBy(Apple::getColor, groupingBy((Apple a) -> { 
					if (a.getWeight() < 120) return Weights.LIGHT;
					else if (a.getWeight() > 120 && a.getWeight() < 200) return Weights.MEDIUM;
					else return Weights.HEAVY;
		})));
		
		stdout("Apples by color and wt " + applesByColorAndWeight);
		
		final Map<String, Integer> wtbycolor = ApplesSort.createApples().stream().collect(groupingBy(Apple::getColor, summingInt((Apple a) -> a.getWeight())));
		stdout("color, wt map " + wtbycolor);
		
		double avgWt = ApplesSort.createApples().stream().collect(averagingInt(Apple::getWeight)); //avg
		stdout(avgWt);
		
		long count = ApplesSort.createApples().stream().collect(counting());//count
		stdout("count via collector " + count);
		
		/** Sub optimal way of counting! Don't do this in prod code. This is just an exercise to show reduce */
		final Optional<Integer> newCount = ApplesSort.createApples().stream().map((a) -> 1).reduce((a, b) -> a + b);
		stdout("count via map-reduce " + newCount.get());
		
		String allApples = ApplesSort.createApples().stream().map(a -> a.getName()).collect(joining(", "));
		stdout(allApples);
		
		/** Reducing functions */
		int sum = ApplesSort.createApples().stream().collect( reducing(0, a -> a.getWeight(), (p, q) -> p + q));//sum
		stdout("Collect 1 " + sum);
		
		//Another concise way
		sum = ApplesSort.createApples().stream().collect( reducing(0, Apple::getWeight, Integer::sum));
		stdout("Collect 2 " + sum);
		
		//Another way with one arg version of reducing
		sum = ApplesSort.createApples().stream().map(a -> a.getWeight()).collect( reducing((a, b) -> a + b)).get();
		stdout("Collect 3 " + sum);
	}

	private static void stdout(Object o) {
		ApplesSort.display(o);
	}
}
