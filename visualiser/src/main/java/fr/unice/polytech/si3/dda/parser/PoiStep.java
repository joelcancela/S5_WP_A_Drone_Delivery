package fr.unice.polytech.si3.dda.parser;

import java.util.Map;

/**
 * Created by alexh on 19/01/2017.
 */
public class PoiStep {

    private Map<Integer, Integer> inventory;
    private int timeRemaining;

    public PoiStep(Map<Integer, Integer> inventory, int timeRemaining) {
        this.inventory = inventory;
        this.timeRemaining = timeRemaining;
    }


    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"inventory\" : [");
        boolean first = true;
        for (Integer i : inventory.keySet()) {
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append("{\"" + i + "\" : " + inventory.get(i));
        }
        stringBuilder.append("], \"inventory\" :" + timeRemaining + "}");
        return stringBuilder.toString();
    }




}
