package com.gs.java8.fp;

import java.util.function.Predicate;
import java.util.function.Supplier;


interface LazyList<T> {
	public T head();
	public LazyList<T> tail();
	public LazyList<T> filter(Predicate<T> p);
	
	default boolean isEmpty() {
		return false;
	}
}

public class LazyIntList<T> implements LazyList<T> {
	T head;
	Supplier<LazyList<T>> tail; 
	
	public LazyIntList(T head, Supplier<LazyList<T>> tail) {
		this.head = head;
		this.tail = tail;
	}

	@Override
	public T head() {
		return head;
	}

	@Override
	public LazyList<T> tail() {
//		System.out.println("Calling tail");
		return tail.get();
	}

	@Override
	/**
	 * Do the same thing that the Stream's filter does. The main trick is here in the filter!
	 * this portion "() -> tail().filter(p)" is the crux of this algorithm. This creates a chain of 
	 * filters in the caller like tail.filter(n % 2).filter(n % 3).filter(n % 5)... until the division occurs
	 * by all numbers i.e for 11 division occurs till 7
	 * 
	 * tail() call in tail().filter(p) returns the previous filter. new LL(primeNo, () -> filter(PREV).filter(NEXT))
	 */
	public LazyList<T> filter(Predicate<T> p) {
		return isEmpty() ? this :
			p.test(head()) ? new LazyIntList<T>(head(), () -> tail().filter(p)) : tail().filter(p);
	}
	
}
