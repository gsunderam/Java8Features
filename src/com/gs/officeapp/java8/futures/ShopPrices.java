package com.gs.officeapp.java8.futures;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ShopPrices {
	private static final String IPHONE = "IPhone 7";
	private static final List<Shop> shops = Arrays.asList(new Shop("Apple"), new Shop("Microsoft"), 
			new Shop("samsung"), new Shop("Google"), new Shop("Nokia"), new Shop("Verizon"));
	
	public static void main(String[] args) {
//		findDiscountPricesByStream(IPHONE);
//		findDiscountPricesByFuture(IPHONE);
		reactToDiscountPricesByFuture(IPHONE);
//		findPricesByStream(IPHONE);
//		findPricesByFuture(IPHONE);
//		findPricesByCustomExec(IPHONE);
	}
	
	public static void findPricesByStream(String product) {
		long start = System.nanoTime();
		
		/** Parallel version is 50% faster for four shops! Definitely use parallel when needed */
		shops.stream().parallel().map((shop) -> String.format("price at %s is %.2f" , shop.name, shop.getPrice(product))).
			forEach(System.out::println);
		System.out.println("Avg time Time taken is " + (System.nanoTime() - start) / (4 * 10E6));
	}
	
	/**
	 * This is faster than parallel version by 50.x%! Vow.
	 * @param product
	 */
	public static void findPricesByFuture(String product) {
		long start = System.nanoTime();
		
		final List<CompletableFuture<String>> futurePrices = shops.stream().map(shop -> shop.getAsyncPrice(shop.name,  product))
				.collect(Collectors.toList());
		
		futurePrices.stream().map(CompletableFuture::join).forEach(System.out::println);
		System.out.println("Avg time Time taken for Java8 future is: " + (System.nanoTime() - start) / (4 * 10E6));
	}
	
	private static void findPricesByCustomExec(String product) {
		long start = System.nanoTime();
		
		final List<CompletableFuture<String>> futurePrices = shops.stream().map(shop -> shop.getAsynCustomExecPrice(shop.name, product)).collect(Collectors.toList());
		futurePrices.stream().map(CompletableFuture::join).forEach(System.out::println);
		
		System.out.println(String.format("Avg time Time taken for custom executors is: %.2f", (System.nanoTime() - start) / (4 * 10E6)));
	}
	
	private static void findDiscountPricesByStream(String product) {
		long start = System.nanoTime();
		shops.stream().parallel().map(shop -> shop.getDisPrice(shop.name, product))
							.map(Discount::applyRebate).forEach(System.out::println);
		System.out.println(String.format("Avg time Time taken for ||l streams is: %.2f", (System.nanoTime() - start) / (shops.size() * 10E6)));
	}
	
	/** this is thrice as fast as parallel version */
	private static void findDiscountPricesByFuture(String product) {
		long start = System.nanoTime();
		final List<CompletableFuture<Object>> futurePrices = shops.stream().map(shop -> shop.getDisAsyncPrice(shop.name, product))
					  .map(future -> future.thenCompose(price -> Discount.applyAsyncRebate(price))).collect(Collectors.toList());
		
		futurePrices.stream().map(CompletableFuture::join).forEach(System.out::println);
							
		System.out.println(String.format("Avg time Time taken for java8 future is: %.2f", (System.nanoTime() - start) / (shops.size() * 10E6)));
	}
	
	/** Use f.thenAccept to perform a terminal operation. same as above This shows HOW to react to events like a callback in java 7 future.
	 *  then accept method acts as a callback.
	 */
	@SuppressWarnings("rawtypes")
	private static void reactToDiscountPricesByFuture(String product) {
		long start = System.nanoTime();
		final CompletableFuture[] futures = shops.stream().map(shop -> shop.getDisAsyncPrice(shop.name, product))
					  .map(future -> future.thenCompose(price -> Discount.applyAsyncRebate(price)))
					  .map(f -> f.thenAccept(System.out::println)) //React to the event like a callback
					  .toArray(size -> new CompletableFuture[size]);
		
		CompletableFuture.allOf(futures).join();
		System.out.println(String.format("Avg time Time taken for thenAccept async is: %.2f", (System.nanoTime() - start) / (shops.size() * 10E6)));
	}
}
