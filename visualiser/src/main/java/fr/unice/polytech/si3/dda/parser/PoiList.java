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

    public List<PoiStep> getOrderSteps() {
        return orderSteps;
    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        stringBuilder.append("[");
        for (int i = 1; i < orderSteps.size(); i++) {
            if (first) first = false;
            else stringBuilder.append(",");
            boolean firstTab = true;
            while (orderSteps.get(i).getTimeRemaining() > 0) {
                if (firstTab) firstTab = false;
                else stringBuilder.append(",");
                stringBuilder.append("{\"inventory\" : [");
                boolean firstInv = true;
                for (Integer integer : orderSteps.get(i).getInventory().keySet()) {
                    if (firstInv) firstInv = false;
                    else stringBuilder.append(",");
                    stringBuilder.append("{\"" + integer + "\" : " + orderSteps.get(i - 1).getInventory().get(integer) + "}");
                }
                stringBuilder.append("], \"remaining\" :" + orderSteps.get(i).getTimeRemaining() + "}");
                orderSteps.get(i).decrRemaining();
            }
            if (firstTab) firstTab = false;
            else stringBuilder.append(",");
            stringBuilder.append("{\"inventory\" : [");
            boolean firstInv = true;
            for (Integer integer : orderSteps.get(i).getInventory().keySet()) {
                if (firstInv) firstInv = false;
                else stringBuilder.append(",");
                stringBuilder.append("{\"" + integer + "\" : " + orderSteps.get(i).getInventory().get(integer) + "}");
            }
            stringBuilder.append("], \"remaining\" :" + 0 + "}");
            orderSteps.get(i).decrRemaining();

        }
        stringBuilder.append("]");
        return stringBuilder.toString();

    }


}