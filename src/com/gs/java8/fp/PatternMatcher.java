package com.gs.java8.fp;

import java.util.function.Function;
import java.util.function.Supplier;

interface TriFunction<S, T, U, R> {
	R apply(S s, T t, U u);
}

public class PatternMatcher {
	static <T> T patternMatchExpr(Expr e, TriFunction<String, Expr, Expr, T> binopcase,
			Function<Double, T> numCase, Supplier<T> defaultCase) {
		T retVal = null;
		
		if (e instanceof BinOp) {
			System.out.println("Bin Op instance");
			BinOp op = (BinOp) e;
			retVal = binopcase.apply(op.op, op.left, op.right);
		} else if (e instanceof Numeric) {
			System.out.println("numeric instance");
			Numeric n = (Numeric) e;
			retVal = numCase.apply(n.val);
		} else {
			System.out.println("default instance");
			defaultCase.get();
		}
		
		return retVal;
	}
	
	public static Expr simplify(Expr e) {
		TriFunction<String, Expr, Expr, Expr> binopcase = 
			(op, left, right) -> {
				System.out.println("Executing Bin Op case");
				if ("+".equals(op) && left instanceof Numeric && ((Numeric)left).val == 0.0)
					return right;
				if ("+".equals(op) && right instanceof Numeric && ((Numeric)right).val == 0.0)
					return left;
				if ("*".equals(op) && right instanceof Numeric && ((Numeric)right).val == 1.0)
					return left;
				if ("*".equals(op) && left instanceof Numeric && ((Numeric)left).val == 1.0) {
					return right;
				}
				
				return new BinOp(op, left, right);
			};
			
		Function<Double, Expr> numcase = n -> new Numeric(n);
		Supplier<Expr> defaultcase = () -> new Numeric(0);
				
		return patternMatchExpr(e, binopcase, numcase, defaultcase);
	}
	
	public static void main(String[] args) {
		Expr e = new BinOp("*", new Numeric(1.0), new Numeric(12.0));
		final Expr result = simplify(e);
		System.out.println(((Numeric)result).val);
	}
}
