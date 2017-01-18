package fr.unice.polytech.si3.dda.scheduler.strategy;


import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.common.PathFinder;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.PointOfInterest;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;
import fr.unice.polytech.si3.dda.util.Coordinates;
import fr.unice.polytech.si3.dda.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * Class SingleDroneStrategy
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 *
 */
public class SingleDroneStrategy implements Strategy{
	private Context context;
	private List<Instruction> instructionsLists;
	
	/**
	 * Normal constructor of SingleDroneStrategy
	 * @param context Context of execution
	 */
	public SingleDroneStrategy(Context context){
		this.context = context;
		instructionsLists = new ArrayList<>();
	}
	
	/**
	 * Accessor of instructionsLists
	 * @return The instructions list calculated with the last context
	 */
	@Override
	public List<Instruction> getInstructions(){
		return instructionsLists;
	}
	
	/**
	 * Return the list of all instructions of the strategy
	 * @return list of all instructions of the strategy
	 * @throws OverLoadException 
	 * @throws ProductNotFoundException 
	 */
	@Override
	public void calculateInstructions() throws OverLoadException, ProductNotFoundException {
		Drone droneUsed =  context.getFleet().getDrone(0).copy();
		
		List<Order> orders = context.getMap().getOrders();
		Warehouse warehouse = context.getFirstWarehouse();
		List<Map<Product, Integer>> takens = new ArrayList<>();
		
		while(!isOrdersCompleted(orders)){
			loadOrderFromAWarehouse(orders, warehouse, droneUsed, takens);

			Pair<Map<DeliveryPoint, Map<Product, Integer>>, List<PointOfInterest>> pairOfPath = getPathForThoseProducts(takens, warehouse);
			generateInstructionsForThisPath(pairOfPath, warehouse, droneUsed);

			
			Map<Coordinates, Warehouse> warhouses =  context.getMap().getWarehouses();
			warehouse =  findTheNextWarehouse(orders, warhouses, droneUsed);
		}
	}
	
	/**
	 * Check if all orders are done
	 * @param orders List of orders to do
	 * @return true if all orders are done, false otherwise
	 */
	private boolean isOrdersCompleted(List<Order> orders){
		for(int i=0; i<orders.size(); i++){
			for(Map.Entry<Product, Integer> entry : orders.get(i).getProducts().entrySet()){
				if(entry.getValue()>0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Load all products needed and possible at a warehouse
	 * @param orders List of orders to do
	 * @param warehouse Current warehouse
	 * @param droneUsed Current drone
	 * @param takens List of products to take
	 * @throws OverLoadException
	 * @throws ProductNotFoundException
	 */
	private void loadOrderFromAWarehouse(List<Order> orders, Warehouse warehouse, Drone droneUsed, List<Map<Product, Integer>> takens) throws OverLoadException, ProductNotFoundException{	
		boolean restart = false;
		int indexToRestart = 0;
		int i =0;
		while(i<orders.size()){
			
			Map<Product, Integer> tempo = new HashMap<>();
			if(i==takens.size() && !restart){
				takens.add(tempo);
			}else{
				tempo = takens.get(i);
			}
			
			Map<Product, Integer> tempoProducts = orders.get(i).getProducts();
			for(Map.Entry<Product, Integer> entry : tempoProducts.entrySet()){
				if(tempo.containsKey(entry.getKey())){
					if(entry.getValue()-tempo.get(entry.getKey()) > 0 
							&& warehouse.howManyProduct(entry.getKey())!=0
							&& entry.getKey().getWeight() <= droneUsed.getMaxPayload()-droneUsed.getUsedPayload()){
						
						droneUsed.load(entry.getKey());
						warehouse.pullOutProduct(entry.getKey());
						
						tempo.put(entry.getKey(), tempo.get(entry.getKey())+1);
							
						restart = true;
						indexToRestart = i;
						break;
					}
				}else{
					if(warehouse==null) {
						throw new ProductNotFoundException("Product : " + entry.getKey());
					}
					if(entry.getValue() > 0 
							&& warehouse.howManyProduct(entry.getKey())!=0
							&& entry.getKey().getWeight() <= droneUsed.getMaxPayload()-droneUsed.getUsedPayload()){
						
						droneUsed.load(entry.getKey());
						warehouse.pullOutProduct(entry.getKey());
						
						tempo.put(entry.getKey(), 1);
							
						restart = true;
						indexToRestart = i;
						break;
					}
				}
			}
			i++;
			if(restart){
				i = indexToRestart;
				restart = false;
			}
		}
	}
	
	
	/**
	 * Find the next warehouse to go (nearest with useful products)
	 * @param orders List of orders to do
	 * @param warhouses List of warehouses
	 * @param droneUsed Current drone
	 * @return The next warehouse chosen by the strategy 
	 */
	private Warehouse findTheNextWarehouse(List<Order> orders, Map<Coordinates, Warehouse> warhouses, Drone droneUsed){
		double distance = Double.MAX_VALUE;
		Warehouse nextWareHouse =  null;
		for(Map.Entry<Coordinates, Warehouse> entry : warhouses.entrySet()){
			for(int i=0; i<orders.size(); i++){
				for(Map.Entry<Product, Integer> prodcuts: orders.get(i).getProducts().entrySet()){
					double currentDistance = droneUsed.getCoordinates().distance(entry.getKey());
					if(prodcuts.getValue()>0
							&& entry.getValue().howManyProduct(prodcuts.getKey()) > 0
							&& currentDistance<distance){
						nextWareHouse = entry.getValue();
						distance = currentDistance;
					}
				}
			}
		}
		
		return nextWareHouse;
	}
	
	/**
	 * Get the next path to follow and the fr.unice.polytech.si3.dda.exception.order of deliveries
	 * @param takens List of products to take
	 * @param warehouse Warehouse of departure
	 * @return Pair with in the first element a map wich deliveryPoint to the products to deliver, in second element the path of pois to visit
	 */
	private Pair<Map<DeliveryPoint, Map<Product, Integer>>, List<PointOfInterest>> getPathForThoseProducts(List<Map<Product, Integer>> takens, Warehouse warehouse){
		Map<DeliveryPoint, Map<Product, Integer>> pointToDeliver = new HashMap<>();
		for(Map.Entry<Coordinates, DeliveryPoint> entryDP : context.getMap().getDeliveryPoints().entrySet()){
			for(int i=0; i<takens.size();i++){
				boolean takeIt = true;
				for(Map.Entry<Product, Integer> entryTakenI: takens.get(i).entrySet()){
					if(entryDP.getValue().getOrder().getQuantity(entryTakenI.getKey()) == 0)
						takeIt = false;
				}
				
				if(takeIt){
					pointToDeliver.put(entryDP.getValue(), takens.get(i));
					takens.remove(i);
					break;
				}
			}
		}
		List<PointOfInterest> poiList = new ArrayList<>();
		poiList.add(warehouse);
		for(Map.Entry<DeliveryPoint, Map<Product, Integer>> entry : pointToDeliver.entrySet())
			poiList.add(entry.getKey());
		
		return new Pair<>(pointToDeliver, poiList);
	}
	
	/**
	 * Generate instructions for a path
	 * @param pair Associations of a deliveryPoint and products to deliver
	 * @param warehouse Current warehouse
	 * @param droneUsed Current drone
	 * @throws ProductNotFoundException
	 */
	private void generateInstructionsForThisPath(Pair<Map<DeliveryPoint, Map<Product, Integer>>, List<PointOfInterest>> pair, Warehouse warehouse, Drone droneUsed) throws ProductNotFoundException{
		Pair<Integer, List<PointOfInterest>> firstTravel = PathFinder.getMinimalCost(pair.getSecond());
		List<Product> alreadyCounted = new ArrayList<>();
		for(Product productTemp : droneUsed.getLoadedProducts()){
			if(alreadyCounted.contains(productTemp)){
				continue;
			}
			
			int nbof = 0;
			
			for(int i=0; i<droneUsed.getLoadedProducts().size(); i++)
				if(droneUsed.getLoadedProducts().get(i).equals(productTemp))
					nbof++;
			
			instructionsLists.add(new LoadInstruction(0, warehouse.getId(), productTemp.getId(), nbof));
			alreadyCounted.add(productTemp);
		}
		

		for(int i=1;i<firstTravel.getSecond().size();i++){
			for(Map.Entry<Product, Integer> entry : pair.getFirst().get(firstTravel.getSecond().get(i)).entrySet()){
				instructionsLists.add(new DeliverInstruction(0, firstTravel.getSecond().get(i).getId(), entry.getKey().getId(), entry.getValue()));

				for(int y=0;y<entry.getValue();y++){
					droneUsed.unload(entry.getKey());
					for(Map.Entry<Coordinates, DeliveryPoint> dps : context.getMap().getDeliveryPoints().entrySet()){
						if(dps.getKey().equals(firstTravel.getSecond().get(i).getCoordinates())){
							dps.getValue().removeThisProduct(entry.getKey());
							droneUsed.move(dps.getKey());
						}
							
					}
				}

			}
		}
	}

	
}
