package fr.unice.polytech.si3.dda;

public abstract class View {

	public abstract void display() throws InterruptedException;

	protected void clearScreen() {
		System.out.print("\033[" + "2J");
	}

}
