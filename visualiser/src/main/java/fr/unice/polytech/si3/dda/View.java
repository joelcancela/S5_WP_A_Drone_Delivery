package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;

public abstract class View {

	public abstract void display() throws InterruptedException, WrongIdException, OverLoadException, ProductNotFoundException;

	protected void clearScreen() {
		System.out.print("\033[" + "2J");
	}

}
