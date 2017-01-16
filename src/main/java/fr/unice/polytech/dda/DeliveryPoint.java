package fr.unice.polytech.dda;

public class DeliveryPoint implements IPointOfInterest {
	private Order order;
	
	public DeliveryPoint() throws WrongArgumentException{
		this(null);
	}
	
	public DeliveryPoint(Order order) throws WrongArgumentException{
		if(order==null)
			throw new WrongArgumentException("Argument passed \"order\" is null.");
		
		 this.order =  order;
	}
	
	public Order getOrder(){
		return order;
	}
	
	public boolean isWarehouse() {
		return false;
	}

	public boolean isDeliveryPoint() {
		return true;
	}

}
