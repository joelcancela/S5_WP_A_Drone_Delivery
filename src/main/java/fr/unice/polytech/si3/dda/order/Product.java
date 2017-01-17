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
    private int id;

    /**
     * Product class constructor
     *
     * @param weight the weight of the product
     */
    public Product(int weight, int id) {
        if (weight <= 0)
            throw new IllegalArgumentException("Weight product can't be zero or negative! (" + weight + ")");
        this.weight = weight;
        this.id = id;
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
     * Returns the if of the product.
     *
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Product product = (Product) o;

        return weight == product.weight && id == product.id;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return weight;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Product{" +
                "weight=" + weight +
                '}';
    }
}
