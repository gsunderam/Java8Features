package com.gs.officeapp.java8;

import java.util.function.Function;

/**
 * Practical example to make use of the function chaining idiom
 * @author gsunderam
 *
 */
public class Letter {
	/** Any Preprocessing */
	public static String addHeader(String text) {
		return text + "\n\t" + "This is a success story of GSunderam ";
	}
	
	/** Any Post processing */
	public static String addFooter(String text) {
		return text + "\nRegards,\nPresident of PSG,\nSunderam G";
	}
	
	public static void main(String[] args) {
		Function<String, String> headerFunc = Letter::addHeader;
		Function<String, String> footerFunc = Letter::addFooter;
		
		Function<String, String> fullLetter = headerFunc.andThen(footerFunc);
		final String letterWithHeaders = fullLetter.apply("From: Amex Inc.");
		System.out.println(letterWithHeaders);
	}

}
