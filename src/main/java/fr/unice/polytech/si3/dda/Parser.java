package fr.unice.polytech.si3.dda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {
	
	public final String filename = "context0.in";

	private BufferedReader br;
	
	public Parser() throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}
	
}
