package com.gs.lambdas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {
	public static String processFile(BufferedProcessReader p) throws IOException {
		String s = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) { //This is boiler plate
			s = p.process(br); //Fun stuff, so abstracted in to an interface
		}
		
		return s;
	}
	
	public static void main(String[] args) throws IOException {
		/** Pass a lambda to the processFile method. Provides an implementation of the process method */
		final String output = processFile((BufferedReader br) -> { return br.readLine(); } );
		
		System.out.println(output);
	}
}
