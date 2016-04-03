package com.gs.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalc extends RecursiveTask<Long> {
	private static final long serialVersionUID = 6187145572180091087L;
	long [] numbers;
	int start, end;
	boolean isParallel;
	
	public ForkJoinSumCalc(long [] numbers, int start, int end, boolean isParallel) {
		super();
		this.numbers = numbers;
		this.start = start;
		this.end = end;
		this.isParallel = isParallel;
	}

	public ForkJoinSumCalc(long[] testNumbers, boolean isParallel) {
		this(testNumbers, 0, testNumbers.length, isParallel);
	}

	@Override
	protected Long compute() {
		int len = end - start;
		if (!isParallel && len <= 50) return computeSeq();
		return computeParallel(len);
	}

	private long computeParallel(int len) {
		ForkJoinSumCalc leftTask = new ForkJoinSumCalc(numbers, start, start + len / 2, false);
		leftTask.fork();
		
		ForkJoinSumCalc rightTask = new ForkJoinSumCalc(numbers, start + len / 2, end, false);
		final Long rightResult = rightTask.compute();
		
		final Long leftResult = leftTask.join();
		System.out.println("left " + leftResult + " Right " + rightResult);
		return leftResult + rightResult;
	}

	private Long computeSeq() {
		long sum = 0;
		for(int i = start; i < end; i++) sum += numbers[i];
		
		return sum;
	}
	
	public static void main(String[] args) {
		final long[] testNumbers = LongStream.rangeClosed(1, 1000000).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalc(testNumbers, true);
		final Long result = new ForkJoinPool().invoke(task);
		System.out.println("Result of fork join is : " + result);
	}

}
