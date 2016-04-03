package com.gs.officeapp.java8;

public class Apple {
	String color;
	int wt;
	String name;

	public Apple(String name, String color, int wt) {
		this.name = name;
		this.color = color;
		this.wt = wt;
	}
	
	public Apple(String name, String color) {
		this.name = name;
		this.color = color;
	}
	
	public static boolean isHeavy(Apple a) {
		return a.wt > 150;
	}
	
	public static boolean isGreen(Apple a) {
		return a.color.equals("green");
	}
	
	public int getWeight() {
		return wt;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}

	public String toString() {
		return name + " Color: " + color + " wt: " + (wt != 0 ? wt : "Not provided");
	}
}
