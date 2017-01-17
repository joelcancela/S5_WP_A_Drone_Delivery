package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.EmptyFileException;
import fr.unice.polytech.si3.dda.exception.MalformedScheduleException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class Parser
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Parser {

	private BufferedReader br;

	/**
	 * Parser constructor
	 *
	 * @param filename the filename to parse
	 * @throws FileNotFoundException if the filename is incorrect
	 */
	public Parser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}

	public void parse() throws IOException, EmptyFileException, MalformedScheduleException {
		String line = br.readLine();
		if (line == null)
			throw new EmptyFileException();
		while(line!=null){
			if(line.contains("L")){

			}
			else if(line.contains("D")){

			}
			else if(line.contains("U")){

			}
			else if(line.contains("W")){

			}
			else{
				throw new MalformedScheduleException();
			}
			line=br.readLine();
		}
	}

}
