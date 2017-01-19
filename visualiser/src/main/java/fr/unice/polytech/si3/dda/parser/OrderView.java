package fr.unice.polytech.si3.dda.parser;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.ScheduleParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.instruction.UnloadInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.PointOfInterest;
import fr.unice.polytech.si3.dda.mapping.Warehouse;

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
            DronePath dronePath = new DronePath(i);
            for (Instruction instruction : droneInstruction.get(i)) {
                dronePath.addMove(instruction);
            }
            dronePaths.add(dronePath);
        }


    }

    public String contextToJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"context\" : { \"nbDrone\" : ");
        stringBuilder.append(context.getMaxDrones());

        stringBuilder.append("}");
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


    private class DronePath {
        int droneId;
        List<Move> path;


        DronePath(int droneId) {
            this.path = new ArrayList<>();
            this.droneId = droneId;
        }

        void addMove(Instruction instruction) throws GlobalException {
            PointOfInterest pointOfInterest = null;
            if (instruction.isLoadInstruction()) {
                pointOfInterest = new Warehouse(context.getMap().getWarehouse(((LoadInstruction) instruction).getIdWarehouse()));
            } else if (instruction.isUnloadInstruction()) {
                pointOfInterest = new Warehouse(context.getMap().getWarehouse(((UnloadInstruction) instruction).getIdWarehouse()));
            } else if (instruction.isDeliverInstruction()) {
                pointOfInterest = new DeliveryPoint(context.getMap().getDeliveryPoint(((DeliverInstruction) instruction).getOrderNumber()));
            }
            createMove(instruction, pointOfInterest);

        }

        void createMove(Instruction instruction, PointOfInterest pointOfInterest) throws WrongIdException {
            Move move;
            if (path.isEmpty()) {
                move = new Move(context.getFirstWarehouse(), new HashMap<>());
                path.add(move);
                createMove(instruction, pointOfInterest);
                return;
            } else {
                if (path.get(path.size() - 1).end == null) {
                    path.get(path.size() - 1).end = pointOfInterest;
                    move = new Move(pointOfInterest, path.get(path.size() - 1).inventory);
                    move.addEnd(instruction);
                    path.add(move);
                } else
                    path.get(path.size() - 1).addEnd(instruction);
            }

        }

        public String toJson() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            System.out.println("--------------------");
            for (Move move : path)
                System.out.println(move.remaing);
            while (path.get(0).remaing > 0){
                stringBuilder.append(path.get(0).toJson());
            }
            for (int i = 1; i < path.size(); i++) {
                while (path.get(i).remaing > 0){
                    stringBuilder.append("," + path.get(i).toJson());
                }
            }
            stringBuilder.append("]");

            return stringBuilder.toString();
        }
    }

    private class Move {
        PointOfInterest start;
        PointOfInterest end;
        //ProductId, NumberOfProduct
        Map<Integer, Integer> inventory;
        int remaing = 1;

        public Move(PointOfInterest start, Map<Integer, Integer> inventory) {
            this.start = start;
            this.inventory = new HashMap<>(inventory);
        }

        public void addEnd(Instruction instruction) throws WrongIdException {
            if (instruction.isLoadInstruction()) {
                if (inventory.containsKey(instruction.getProductType())) {
                    inventory.put(instruction.getProductType(), instruction.getNumberOfProducts() + inventory.get(instruction.getProductType()));
                } else {
                    inventory.put(instruction.getProductType(), instruction.getNumberOfProducts());
                }
                end = context.getMap().getWarehouse(((LoadInstruction) instruction).getIdWarehouse());
            } else if (instruction.isDeliverInstruction() || instruction.isUnloadInstruction()) {
                inventory.put(instruction.getProductType(), inventory.get(instruction.getProductType()) - instruction.getNumberOfProducts());
                if (instruction.isUnloadInstruction())
                    end = context.getMap().getWarehouse(((UnloadInstruction) instruction).getIdWarehouse());
                else
                    end = context.getMap().getDeliveryPoint(((DeliverInstruction) instruction).getOrderNumber());
            }
            remaing += end.distance(start);
        }

        String toJson() {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("{\"departure\" : ");
            stringBuilder.append("{\"x\" : " + start.getCoordinates().getX());
            stringBuilder.append(",\"y\" : " + start.getCoordinates().getY() + "},");

            if (end == null) end = start;
            stringBuilder.append("\"arrival\" : ");
            stringBuilder.append("{\"x\" : " + end.getCoordinates().getX());
            stringBuilder.append(",\"y\" : " + end.getCoordinates().getY() + "}");

            stringBuilder.append(",\"inventory\" :{");
            boolean first = true;
            for (Integer i : inventory.keySet()) {
                if (!first)
                    stringBuilder.append(",");
                else
                    first = false;
                stringBuilder.append("\"" + i + "\" : " + inventory.get(i));
            }
            stringBuilder.append("}");

            System.out.println(remaing);
            stringBuilder.append(", \"remaining\" : " + remaing + "}");
            this.remaing --;
            return stringBuilder.toString();
        }

    }
}
