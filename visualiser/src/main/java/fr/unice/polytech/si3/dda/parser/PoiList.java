package fr.unice.polytech.si3.dda.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alexh on 19/01/2017.
 */
public class PoiList {
    List<PoiStep> orderSteps;

    public PoiList() {
        this.orderSteps = new ArrayList<>();
    }

    public void addStep(Map<Integer, Integer> inventory, int remaining) {
        orderSteps.add(new PoiStep(inventory, remaining));
    }

    public String toJson(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        boolean first = true;
        for (PoiStep poiStep : orderSteps){
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append(poiStep.toJson());
        }

        stringBuilder.append("]");
        return stringBuilder.toString();

    }


}