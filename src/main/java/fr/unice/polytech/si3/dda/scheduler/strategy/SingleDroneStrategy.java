package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.scheduler.Context;
import fr.unice.polytech.si3.dda.scheduler.Drone;
import fr.unice.polytech.si3.dda.scheduler.Fleet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.instruction.IInstruction;
import fr.unice.polytech.si3.dda.mapping.PointOfInterest;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Order;
import fr.unice.polytech.si3.dda.order.Product;

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
	private Fleet fleet;
	
	/**
	 * Normal constructor of SingleDroneStrategy
	 * @param context Context of execution
	 * @param fleet Fleet of drones used for this strategy
	 */
	public SingleDroneStrategy(Context context, Fleet fleet){
		this.context = context;
		this.fleet = fleet;
	}
	
	/**
	 * Return the list of all instructions of the strategy
	 * @return list of all instructions of the strategy
	 * @throws OverLoadException 
	 */
	public List<IInstruction> getInstructions() throws OverLoadException{
		Map<List<IInstruction>, Integer> instructionsLists = new HashMap<List<IInstruction>, Integer>();
		Drone droneUsed = fleet.getDrone(0).copie();
		
		List<Order> orders = context.getMap().getOrders();
		Warehouse warehouse = context.getFirstWarehouse().copie();
		
		List<Map<Product, Integer>> takens = new ArrayList<Map<Product, Integer>>();
		boolean restart = false;
		int indexToRestart = 0;
		int i =0;
		while(true){
			if(restart){
				i = indexToRestart;
				restart = false;
			}
			
			if(i==orders.size())
				break;
			
			System.out.println("im in order n°"+i);

			
			Map<Product, Integer> tempo = new HashMap<Product, Integer>();
			if(i==takens.size() && !restart){
				takens.add(tempo);
			}else{
				tempo = takens.get(i);
			}
			
			System.out.println("TAKENS :");
			System.out.println(takens);
			
			Map<Product, Integer> tempoProducts = orders.get(i).getProducts();
			System.out.println("Nb boucles à faire : "+ tempoProducts.size());
			for(Map.Entry<Product, Integer> entry : tempoProducts.entrySet()){
				System.out.println("im looking for : "+entry.getKey());
				if(tempo.containsKey(entry.getKey())){
					if(entry.getValue()-tempo.get(entry.getKey()) > 0 
							&& warehouse.howManyProduct(entry.getKey())!=0
							&& entry.getKey().getWeight() <= droneUsed.getMaxPayload()-droneUsed.getUsedPayload()){
						
						droneUsed.load(entry.getKey());
						warehouse.pullOutProduct(entry.getKey());
						System.out.println("i add : "+entry.getKey());
						
						tempo.put(entry.getKey(), tempo.get(entry.getKey())+1);
							
						restart = true;
						indexToRestart = i;
						break;
					}
				}else{
					if(entry.getValue() > 0 
							&& warehouse.howManyProduct(entry.getKey())!=0
							&& entry.getKey().getWeight() <= droneUsed.getMaxPayload()-droneUsed.getUsedPayload()){
						
						droneUsed.load(entry.getKey());
						warehouse.pullOutProduct(entry.getKey());
						System.out.println("i add : "+entry.getKey());
						
						tempo.put(entry.getKey(), 1);
							
						restart = true;
						indexToRestart = i;
						break;
					}
				}
			}
			System.out.println("-----------------");
			i++;
		}
		System.out.println(droneUsed.getLoadedProducts());
		
		
		return null;
	}

	
}
