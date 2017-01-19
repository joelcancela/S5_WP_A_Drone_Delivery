package fr.unice.polytech.si3.dda.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexh on 19/01/2017.
 */
public class PoiList {
    List<PoiStep> orderSteps;

    public PoiList() {
        this.orderSteps = new ArrayList<>();
    }

    public void addStep(PoiStep poiStep) {
        orderSteps.add(poiStep);
    }

    public PoiStep getLast() {
        return orderSteps.get(orderSteps.size() - 1);
    }

    public PoiStep getFirst() {
        return orderSteps.get(0);
    }

    public String toJson() {
        orderSteps.get(0).setTimeRemaining(0);
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        stringBuilder.append("[");
        for (PoiStep poiStep : orderSteps) {
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append(poiStep.toJson());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();

    }


}