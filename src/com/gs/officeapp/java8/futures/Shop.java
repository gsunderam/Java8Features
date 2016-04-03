package com.gs.officeapp.java8.futures;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class Shop {
	private static final int N_THREADS = Runtime.getRuntime().availableProcessors() * 1 * (1 + 100);
	
	static final Executor exec = Executors.newFixedThreadPool(Math.min(6, N_THREADS), 
			new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true);
					return t;
				}
	});
	
	String name;
	
	public Shop(String name) {
		this.name = name;
	}

	public double getPrice(String product) {
		return computePrice(product);
	}
	
	/** 
	 * This is the Async version of price
	 * 
	 * @param product
	 * @return
	 */
	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<Double>();
		new Thread(() -> { 
			try {
				double price = computePrice(product);
				futurePrice.complete(price);
			} catch(Exception e) {
				futurePrice.completeExceptionally(e);
			}
		}).start();
		
		return futurePrice;
	}
	
	/**
	 * supplyAsync does all the boiler plate associated with thread creation, providing the runnable arg, 
	 * starting the thread and handling exceptions. it does 
	 * 
	 * new Thread(new Runnable() { 1) price = computePrice(product);
	 * 						   	   2) new CompletableFuture<Double>.complete(price);
	 *     						 }).start(); 
	 * and handling exex exception and finally returning Future<T>. 
	 * Just provide the runnable task and one is done.
	 * SupplyAsync just needs a supplier matching the run signature
	 * 
	 * @param product
	 * @return
	 */
	public CompletableFuture<String> getAsyncPrice(String shop, String product) {
		return CompletableFuture.supplyAsync(() -> "Price at " + shop + " is " + computePrice(product));
	}
	
	/**
	 * This will scale better in multi core cpu systems. Alwyas consider custom executors when performance tuning is desired
	 * @param shop
	 * @param product
	 * @return
	 */
	public CompletableFuture<String> getAsynCustomExecPrice(String shop, String product) {
		return CompletableFuture.supplyAsync(() -> "Price at " + shop + " is " + computePrice(product), exec);
	}
	
	public String getDisPrice(String name, String product) {
		double price = computePrice(product);
		Random r = new Random();
		Discount.Code code = Discount.Code.values()[r.nextInt(Discount.Code.values().length)];
		return String.format("%s,%s,%.2f,%s", name, product, price, code);
	}
	
	public CompletableFuture<String> getDisAsyncPrice(String name, String product) {
		return CompletableFuture.supplyAsync(() -> {
			double price = computePrice(product);
			Random r = new Random();
			Discount.Code code = Discount.Code.values()[r.nextInt(Discount.Code.values().length)];
			return String.format("%s,%s,%.2f,%s", name, product, price, code);
		});
	}

	private double computePrice(String product) {
		delay();
		Random r = new Random();
		return r.nextDouble() + product.charAt(0) + product.charAt(4);
	}

	public static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
