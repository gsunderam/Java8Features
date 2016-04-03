package com.gs.java8.fp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {1,4,9} = {1} + {4,9} = 1,4   1,9  1,4,9
 * @author gsunderam
 *
 */
public class SubsetsList<T> {
	static int count;
	
	public List<List<T>> subset(List<T> list) {
		if (count != 0) System.out.println("Iteration " + (count));
		++count;
		if (list.isEmpty()) { 
			List<List<T>> emptyList = new ArrayList<List<T>>();
			emptyList.add(Collections.emptyList());
			return emptyList;
		}
		
		//Take the first el out
		T first = list.get(0);
		List<T> rest = list.subList(1, list.size()); //get the rest
		
		//Again find the subset of rest. This where recursion comes in handy
		final List<List<T>> result1 = subset(rest);
		System.out.println("result 1");
		printResults(result1);
		
		//Add first integer to each of the sub lists, thus creating the List of lists of integer
		System.out.println("Adding " + first + " to " + result1);
		final List<List<T>> result2 = insertAll(first, result1);
		System.out.println("result 2");
		printResults(result2);
		
		//Just PACK both in one list. Pretty simple method. Responsible for
		//aggregating the lists
		final List<List<T>> result = concat(result1, result2);
		
//		if (count == 3) printResults(result);
		return result;
	}

	private void printResults(List<List<T>> result) {
		result.stream().forEach(System.out::print);
		System.out.println();
	}

	private List<List<T>> concat(List<List<T>> s1,
			List<List<T>> s2) {
		List<List<T>> result = new ArrayList<List<T>>(s1);
		result.addAll(s2);
		System.out.println("Concatted");
		return result;
	}

	private List<List<T>> insertAll(T first, List<List<T>> sublists) {
		List<List<T>> resultList = new ArrayList<List<T>>();
		
		for (List<T> list : sublists) {
			List<T> copyList = new ArrayList<T>();
			copyList.add(first);
			copyList.addAll(list);
			resultList.add(copyList);
		}
		
		return resultList;
	}
	
	public static void main(String[] args) {
		SubsetsList<Integer> subsets = new SubsetsList<Integer>();
		final List<List<Integer>> subsetsList = subsets.subset(Arrays.asList(5,6,2));
		subsets.printResults(subsetsList);
	}

}
