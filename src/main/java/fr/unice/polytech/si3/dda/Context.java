package fr.unice.polytech.si3.dda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.unice.polytech.si3.dda.exception.NonValidCoordinatesException;
import fr.unice.polytech.si3.dda.poi.DeliveryPoint;
import fr.unice.polytech.si3.dda.poi.Warehouse;
import fr.unice.polytech.si3.dda.util.Coordinates;

public class Context {

	private final Mapping map;
	private final int maxDrones;
	private final int turns;
	private final int maxPayload;
	private final List<Product> products;
	
	private Context(ContextBuilder builder) {
		map = builder.map;
		maxDrones = builder.maxDrones;
		turns = builder.turns;
		maxPayload = builder.maxPayload;
		products = builder.products;
	}
	
	public static class ContextBuilder {
		private final Mapping map;
		private final int maxDrones;
		private final int turns;
		private final int maxPayload;
		private final List<Product> products;
		
		public ContextBuilder(int rows, int cols, int maxDrones, int turns, int maxPayload) {
			map = new Mapping(rows, cols);
			this.maxDrones = maxDrones;
			this.turns = turns;
			this.maxPayload = maxPayload;
			products = new ArrayList<>();
		}
		
		public ContextBuilder addProduct(Product p) {
			products.add(p);
			return this;
		}
		
		public ContextBuilder addWarehouse(Coordinates coor, int...stock) throws NonValidCoordinatesException {
			Warehouse w = new Warehouse();
			for (int i = 0; i < products.size(); i++)
				w.addProduct(products.get(i), stock[i]);
			map.addPointOfInterest(coor, w);
			return this;
		}
		
		public ContextBuilder addDeliveryPoint(Coordinates coor, Order o) throws NonValidCoordinatesException {
			map.addPointOfInterest(coor, new DeliveryPoint(o));
			return this;
		}
		
		public Context build() {
			return new Context(this);
		}
		
	}

	/**
	 * @return the map
	 */
	public Mapping getMap() {
		return map;
	}

	/**
	 * @return the maxDrones
	 */
	public int getMaxDrones() {
		return maxDrones;
	}

	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * @return the maxPayload
	 */
	public int getMaxPayload() {
		return maxPayload;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}
	
}
