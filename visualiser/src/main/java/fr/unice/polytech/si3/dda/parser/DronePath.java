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
            } else {
                path.get(path.size() - 1).addEnd(instruction);
            }
        }


    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        boolean first = true;
        for (Move move : path) {
            while (move.remaining >= 0) {
                if (first) first = false;
                else stringBuilder.append(",");
                stringBuilder.append(move.toJson());
            }
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }


    private class Move {
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
            remaining = end.distance(start) + 1;
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
                if (first) first = false;
                else stringBuilder.append(",");
                stringBuilder.append("\"" + i + "\" : " + inventory.get(i));
            }
            stringBuilder.append("}");

            stringBuilder.append(", \"remaining\" : " + remaining + "}");
            this.remaining--;
            return stringBuilder.toString();
        }

    }
}