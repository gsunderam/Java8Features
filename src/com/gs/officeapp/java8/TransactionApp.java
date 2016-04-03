package com.gs.officeapp.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TransactionApp {
	public static List<Transaction> getTrans() {
		Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300), 
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),	
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );
		
		return transactions;
	}
	
	public static void main(String[] args) {
		List<Transaction> trans = getTrans();
//		findAllTrans2011(trans);
		findUniqueCities(trans);
//		findCambridgeTrades(trans);
//		findHighValue(trans);
		
	}

	private static void findHighValue(List<Transaction> trans) {
		System.out.println(trans.stream().max(Comparator.comparing(Transaction::getValue)).get());
		System.out.println(trans.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2).get());
	}

	private static void findCambridgeTrades(List<Transaction> trans) {
		stdout(trans.stream().map(t -> t.getTrader()).filter(t -> t.getCity().equals("Cambridge")).sorted(Comparator.comparing(Trader::getName)).collect(toList()));
	}

	/** Using the custom collector implementation for understanding */
	private static void findUniqueCities(List<Transaction> trans) {
		stdout(trans.stream().map(t -> t.getTrader().getCity()).distinct().collect(new ToListCollector<String>()));
	}

	private static <T> void stdout(List<T> list) {
		ApplesSort.displayList(list);
	}

	private static void findAllTrans2011(List<Transaction> trans) {
		final List<Transaction> trans2011 = trans.stream().filter(t -> t.getYear() == 2011).collect(toList());
		ApplesSort.displayList(trans2011);
	}

}
