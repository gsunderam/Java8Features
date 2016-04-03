package com.gs.officeapp.java8.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Discount {
	public enum Code {
		NONE(0), SILVER(5), GOLD(10), DIAMOND(20), PLATINUM(25);
		private final int percent;
		
		Code(int percent) {
			this.percent = percent;
		}
	}
	
	public static String applyRebate(String priceWithCode) {
		Shop.delay();
		final String[] split = priceWithCode.split(",");
		String name = split[0]; String product = split[1];
		double price = Double.parseDouble(split[2]);
		Discount.Code code = Discount.Code.valueOf(split[3]);
		double finalPrice = price * (1 - (code.percent/100));
		return "Price at " + name + " for " + product + " is " + finalPrice;
	}
	
	public static CompletableFuture<Object> applyAsyncRebate(String priceWithCode) {
		return CompletableFuture.supplyAsync(() -> {
			Shop.delay();
			final String[] split = priceWithCode.split(",");
			String name = split[0]; String product = split[1];
			double price = Double.parseDouble(split[2]);
			Discount.Code code = Discount.Code.valueOf(split[3]);
			double finalPrice = price * (1 - (code.percent/100));
			return "Price at " + name + " for " + product + " is " + finalPrice;
		}, Shop.exec);
	}

}
