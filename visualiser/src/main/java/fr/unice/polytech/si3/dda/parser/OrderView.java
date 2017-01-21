package fr.unice.polytech.si3.dda.parser;

import fr.unice.polytech.si3.dda.ContextParser;
import fr.unice.polytech.si3.dda.ScheduleParser;
import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.exception.OverLoadException;
import fr.unice.polytech.si3.dda.exception.ProductNotFoundException;
import fr.unice.polytech.si3.dda.exception.WrongIdException;
import fr.unice.polytech.si3.dda.instruction.DeliverInstruction;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.instruction.LoadInstruction;
import fr.unice.polytech.si3.dda.mapping.DeliveryPoint;
import fr.unice.polytech.si3.dda.mapping.Warehouse;
import fr.unice.polytech.si3.dda.order.Product;
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
    private List<PoiList> warehouseList;
    private List<PoiList> deliveryPointList;

    public OrderView(String in, String scheduler) throws Exception {
        context = new ContextParser(in).parse();
        instructions = new ScheduleParser(scheduler).parse();
        warehouseList = new ArrayList<>();
        deliveryPointList = new ArrayList<>();
        Map<Integer, List<Instruction>> droneInstruction = new HashMap<>();


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

        stratExecute();


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
            stringBuilder.append("{ \"x\" : " + coordinate.getX() + ", \"y\" : " + coordinate.getY());
            stringBuilder.append(", \"order\" : [");
            boolean firstProduct = true;
            for (Product product : context.getMap().getDeliveryPoint(coordinate).getOrder().getProducts().keySet()) {
                if (firstProduct) firstProduct = false;
                else stringBuilder.append(",");
                stringBuilder.append("{\"" + product.getId() + "\" : " + context.getMap().getDeliveryPoint(coordinate).getOrder().getProducts().get(product) + "}");
            }
            stringBuilder.append("]}");
        }
        stringBuilder.append("]}");

        return stringBuilder.toString();

    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(contextToJson() + ",");

        boolean first;

        stringBuilder.append(" \"warehouse\" : [");
        first = true;
        for (PoiList warehouse : warehouseList) {
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append(warehouse.toJson());
        }
        stringBuilder.append("]");

        stringBuilder.append(", \"deliveries\" : [");
        first = true;
        for (PoiList delivery : deliveryPointList) {
            if (first) first = false;
            else stringBuilder.append(",");
            stringBuilder.append(delivery.toJson());
        }
        stringBuilder.append("]");


        stringBuilder.append(", \"drones\" :  [");
        stringBuilder.append(dronePaths.get(0).toJson());
        for (int i = 1; i < dronePaths.size(); i++) {
            stringBuilder.append("," + dronePaths.get(i).toJson());
        }
        stringBuilder.append("]}");

        return stringBuilder.toString();
    }

    public void stratExecute() throws WrongIdException, OverLoadException, ProductNotFoundException {

        Context tempContext = new Context(context);

        for (Warehouse warehouse : tempContext.getMap().getWarehouses().values()) {
            PoiList poiList = new PoiList();
            Map<Integer, Integer> map = new HashMap<>();
            for (Product product : warehouse.getStock().keySet())
                map.put(product.getId(), warehouse.getStock().get(product));
            poiList.addStep(new PoiStep(map));
            warehouseList.add(poiList);
        }


        for (DeliveryPoint deliveryPoint : tempContext.getMap().getDeliveryPoints().values()) {
            PoiList poiList = new PoiList();
            Map<Integer, Integer> map = new HashMap<>();
            for (Product product : deliveryPoint.getOrder().getProducts().keySet())
                map.put(product.getId(), deliveryPoint.getOrder().getProducts().get(product));

            poiList.addStep(new PoiStep(map));
            deliveryPointList.add(poiList);
        }


        Map<Integer, Integer> counts = new HashMap<>();
//        int count;
        for (Instruction instruction : instructions) {
//            count = instruction.execute(tempContext);
            if (counts.containsKey(instruction.getDroneNumber()))
                counts.put(instruction.getDroneNumber(), counts.get(instruction.getDroneNumber()) + instruction.execute(tempContext));
            else
                counts.put(instruction.getDroneNumber(), instruction.execute(tempContext));

            if (instruction.isLoadInstruction()) {
                PoiList warehouse = warehouseList.get(((LoadInstruction) instruction).getIdWarehouse());
                PoiStep poiStep = new PoiStep(warehouse.getLast().getInventory(), counts.get(instruction.getDroneNumber()));
//                PoiStep poiStep = new PoiStep(warehouse.getLast().getInventory(), count);
                poiStep.removeItem(instruction.getProductType(), instruction.getNumberOfProducts());
                warehouse.addStep(poiStep);
            } else if (instruction.isDeliverInstruction()) {
                PoiList deliveryPoint = deliveryPointList.get(((DeliverInstruction) instruction).getOrderNumber());
                PoiStep poiStep = new PoiStep(deliveryPoint.getLast().getInventory(), counts.get(instruction.getDroneNumber()));
//                PoiStep poiStep = new PoiStep(deliveryPoint.getLast().getInventory(), count);
                poiStep.removeItem(instruction.getProductType(), instruction.getNumberOfProducts());
                deliveryPoint.addStep(poiStep);
            } else if (instruction.isUnloadInstruction()) {
                PoiList warehouse = warehouseList.get(((LoadInstruction) instruction).getIdWarehouse());
                PoiStep poiStep = new PoiStep(warehouse.getLast().getInventory(), counts.get(instruction.getDroneNumber()) - 1);
//                PoiStep poiStep = new PoiStep(warehouse.getLast().getInventory(), count);
                poiStep.addItem(instruction.getProductType(), instruction.getNumberOfProducts());
                warehouse.addStep(poiStep);
            }
        }

    }
}
