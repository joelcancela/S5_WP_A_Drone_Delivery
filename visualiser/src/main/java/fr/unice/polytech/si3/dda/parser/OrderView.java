package fr.unice.polytech.si3.dda.parser;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.ScheduleParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.util.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexh on 18/01/2017.
 */
public class OrderView {
    private List<Instruction> instructions;
    private List<DronePath> dronePaths;
    private Context context;
    private Fleet fleet;

    public OrderView(String in, String scheduler) throws Exception {
        context = new ContextParser(in).parse();
        instructions = new ScheduleParser(scheduler).parse();

        Map<Integer, List<Instruction>> droneInstruction = new HashMap<>();

        fleet = context.getFleet();
        for (Instruction instruction : instructions) {
            int droneId = instruction.getDroneNumber();
            if (droneInstruction.containsKey(droneId)) {
                droneInstruction.get(droneId).add(instruction);
            } else {
                List<Instruction> instructionList = new ArrayList<>();
                instructionList.add(instruction);
                droneInstruction.put(droneId, instructionList);
            }
        }


        dronePaths = new ArrayList<>();

        for (Integer i : droneInstruction.keySet()) {
            DronePath dronePath = new DronePath(i, context);
            for (Instruction instruction : droneInstruction.get(i)) {
                dronePath.addMove(instruction);
            }
            dronePaths.add(dronePath);
        }


    }

    public String contextToJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"context\" : ");
        stringBuilder.append("{ \"nbDrone\" : ");
        stringBuilder.append(context.getMaxDrones());
        stringBuilder.append(",\"map\" : { \"rows\" : " + context.getMap().getRows()
                + ", \"cols\" : " + context.getMap().getCols() + "}");

        stringBuilder.append(",\"warehouses\" : [");
        boolean first = true;
        for (Coordinates coordinate : context.getMap().getWarehouses().keySet()) {
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append("{ \"x\" : " + coordinate.getX() + ", \"y\" : " + coordinate.getY() + "}");
        }
        stringBuilder.append("]");

        stringBuilder.append(",\"deliveryPoints\" : [");
        first = true;
        for (Coordinates coordinate : context.getMap().getDeliveryPoints().keySet()) {
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append("{ \"x\" : " + coordinate.getX() + ", \"y\" : " + coordinate.getY() + "}");
        }
        stringBuilder.append("]}");

        return stringBuilder.toString();

    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(contextToJson() + ",");
        stringBuilder.append("\"drones\" :  [");
        stringBuilder.append(dronePaths.get(0).toJson());
        for (int i = 1; i < dronePaths.size(); i++) {
            stringBuilder.append("," + dronePaths.get(i).toJson());
        }
        stringBuilder.append("]}");

        return stringBuilder.toString();
    }

    public String stratToJson() {
        StringBuilder stringBuilder = new StringBuilder();




        return stringBuilder.toString();
    }
}
