package com.gs.officeapp.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.HashMap;

import static com.gs.officeapp.java8.PrimeNum.isPrime;

/**
 * 
 * This custom collector is 56% faster than the out of the box collector that comes with java8! 
 * Performance tests are a must -:)
 *  
 * @author gsunderam
 */
public class PrimesCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>> > {

	@SuppressWarnings("serial")
	@Override
	public Supplier<Map<Boolean, List<Integer>>> supplier() {
		return () -> new HashMap<Boolean, List<Integer>>() {
						 {
							put(true, new ArrayList<Integer>());
							put(false, new ArrayList<Integer>());
						 }
					  };			
	}

	/**
	 * This is the key piece to the implementation. Finds if the number t is prime and then adds to the correct list.
	 * "true" or "false"
	 */
	@Override
	public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
		return (Map<Boolean, List<Integer>> container, Integer t) -> {
			container.get(isPrime(container.get(true), t)).add(t);
		};
	}

	@Override
	public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
		return (map1, map2) -> {
			map1.get(true).addAll(map2.get(true));
			map1.get(false).addAll(map2.get(false));
			return map1;
		};
	}

	@Override
	public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
		return Function.identity(); //Returns input arg as is
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
	}

}
