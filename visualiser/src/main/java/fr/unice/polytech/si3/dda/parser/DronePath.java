package fr.unice.polytech.si3.dda.parser;

import fr.unice.polytech.si3.dda.common.Context;
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
 * Created by alexh on 19/01/2017.
 */


public class DronePath {
    private int droneId;
    private List<Move> path;
    private Context context;

    DronePath(int droneId, Context context) {
        this.path = new ArrayList<>();
        this.droneId = droneId;
        this.context = context;

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
                path.get(path.size() - 1).addEnd(instruction);
                move = new Move(pointOfInterest, path.get(path.size() - 1).inventory);
                path.add(move);

//                if (instruction.isLoadInstruction()) {
//                    if (move.inventory.containsKey(instruction.getProductType())) {
//                        move.inventory.put(instruction.getProductType(), instruction.getNumberOfProducts() + move.inventory.get(instruction.getProductType()));
//                    } else {
//                        move.inventory.put(instruction.getProductType(), instruction.getNumberOfProducts());
//                    }
//                } else if (instruction.isDeliverInstruction() || instruction.isUnloadInstruction()) {
//                    move.inventory.put(instruction.getProductType(), move.inventory.get(instruction.getProductType()) - instruction.getNumberOfProducts());
//                }
            } else {
                path.get(path.size() - 1).addEnd(instruction);
            }
        }


    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        boolean first = true;
        for (int j = 0; j < path.size(); j++) {
            Move move = path.get(j);
            while (move.remaining > 0) {
                if (first) first = false;
                else stringBuilder.append(",");
                stringBuilder.append(moveToJson(j));
            }
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append("{ \"type\" : \"" + move.type + "\"");
            stringBuilder.append(",\"departure\" : ");
            stringBuilder.append("{\"x\" : " + move.start.getCoordinates().getX());
            stringBuilder.append(",\"y\" : " + move.start.getCoordinates().getY() + "},");

            if (move.end == null) move.end = move.start;

            stringBuilder.append("\"arrival\" : ");
            stringBuilder.append("{\"x\" : " + move.end.getCoordinates().getX());
            stringBuilder.append(",\"y\" : " + move.end.getCoordinates().getY() + "}");

            stringBuilder.append(",\"inventory\" :{");
            boolean firstInv = true;
            for (Integer i : move.inventory.keySet()) {
                if (firstInv) firstInv = false;
                else stringBuilder.append(",");
                stringBuilder.append("\"" + i + "\" : " + move.inventory.get(i));
            }
            stringBuilder.append("}");

            stringBuilder.append(", \"remaining\" : " + move.remaining + "}");
            move.remaining--;
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }


    private String moveToJson(int n) {
        Move move = path.get(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ \"type\" : \"" + move.type + "\"");
        stringBuilder.append(",\"departure\" : ");
        stringBuilder.append("{\"x\" : " + move.start.getCoordinates().getX());
        stringBuilder.append(",\"y\" : " + move.start.getCoordinates().getY() + "},");

        if (move.end == null) move.end = move.start;
        stringBuilder.append("\"arrival\" : ");
        stringBuilder.append("{\"x\" : " + move.end.getCoordinates().getX());
        stringBuilder.append(",\"y\" : " + move.end.getCoordinates().getY() + "}");

        stringBuilder.append(",\"inventory\" :{");
        boolean firstInv = true;
        if (n == 0) n = 1;
        for (Integer i : path.get(n - 1).inventory.keySet()) {
            if (firstInv) firstInv = false;
            else stringBuilder.append(",");
            stringBuilder.append("\"" + i + "\" : " + path.get(n - 1).inventory.get(i));
        }
        stringBuilder.append("}");

        stringBuilder.append(", \"remaining\" : " + move.remaining + "}");
        move.remaining--;
        return stringBuilder.toString();
    }

    private class Move {
        String type;
        PointOfInterest start;
        PointOfInterest end;
        //ProductId, NumberOfProduct
        Map<Integer, Integer> inventory;
        int remaining = 0;

        public Move(PointOfInterest start, Map<Integer, Integer> inventory) {
            this.start = start;
            this.inventory = new HashMap<>(inventory);
        }

        public void addEnd(Instruction instruction) throws WrongIdException {
            type = instruction.getType();
            if (instruction.isLoadInstruction()) {
                end = context.getMap().getWarehouse(((LoadInstruction) instruction).getIdWarehouse());
                if (inventory.containsKey(instruction.getProductType())) {
                    inventory.put(instruction.getProductType(), instruction.getNumberOfProducts() + inventory.get(instruction.getProductType()));
                } else {
                    inventory.put(instruction.getProductType(), instruction.getNumberOfProducts());
                }
            } else if (instruction.isDeliverInstruction() || instruction.isUnloadInstruction()) {
                if (instruction.isUnloadInstruction())
                    end = context.getMap().getWarehouse(((UnloadInstruction) instruction).getIdWarehouse());
                else
                    end = context.getMap().getDeliveryPoint(((DeliverInstruction) instruction).getOrderNumber());
                inventory.put(instruction.getProductType(), inventory.get(instruction.getProductType()) - instruction.getNumberOfProducts());
            } else
                remaining = context.getTurns();
            remaining += end.distance(start);
        }
    }
}