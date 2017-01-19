package fr.unice.polytech.si3.dda.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexh on 19/01/2017.
 */
public class PoiStep {

    private Map<Integer, Integer> inventory;
    private int timeRemaining = -1 ;

    public PoiStep(Map<Integer, Integer> inventory, int timeRemaining) {
        this.inventory = inventory;
        this.timeRemaining = timeRemaining;
    }

    public PoiStep(Map<Integer, Integer> inventory) {
        this.inventory = inventory;
    }

    public Map<Integer, Integer> getInventory() {
        return new HashMap<>(inventory);
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void addItem(int productId, int nb){
        inventory.put(productId, inventory.get(productId) + nb);
    }

    public void removeItem(int productId, int nb){
        inventory.put(productId, inventory.get(productId) - nb);
    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstTab = true;
        while (timeRemaining >= 0) {
            if (firstTab) firstTab = false;
            else stringBuilder.append(",");
            stringBuilder.append("{\"inventory\" : [");
            boolean first = true;
            for (Integer i : inventory.keySet()) {
                if (first) first = false;
                else stringBuilder.append(",");
                stringBuilder.append("{\"" + i + "\" : " + inventory.get(i) + "}");
            }
            stringBuilder.append("], \"remaining\" :" + timeRemaining + "}");
            timeRemaining -= 1;
        }
        return stringBuilder.toString();
    }


}
