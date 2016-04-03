package com.gs.lambdas;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface BufferedProcessReader {
	String process(BufferedReader br) throws IOException;
}
