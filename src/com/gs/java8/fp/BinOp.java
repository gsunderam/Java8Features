package com.gs.java8.fp;

public class BinOp extends Numeric {
	String op;
	Expr left;
	Expr right;

	BinOp(String op, Expr left, Expr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
}
