package fr.unice.polytech.si3.dda.order;

/**
 * Class Product
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Product {

	private int weight;

	/**
	 * Product class constructor
	 *
	 * @param weight the weight of the product
	 */
	public Product(int weight) {
		if (weight <= 0)
			throw new IllegalArgumentException("Weight product can't be zero or negative! (" + weight + ")");
		this.weight = weight;
	}

	/**
	 * Returns the weight of a wanted product
	 *
	 * @return weight the weight of the current product
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * equals method
	 *
	 * @param o object to compare the order with
	 * @return true if the current instance and o are equals, else false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Product product = (Product) o;

		return weight == product.weight;
	}

	/**
	 * hashCode method
	 *
	 * @return the hashcode of the current instance
	 */
	@Override
	public int hashCode() {
		return weight;
	}

	/**
	 * toString method
	 *
	 * @return the representation of the product
	 */
	@Override
	public String toString() {
		return "Product{" +
				"weight=" + weight +
				'}';
	}
}
