package fr.unice.polytech.si3.dda.scheduler.strategy;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.common.Drone;
import fr.unice.polytech.si3.dda.common.Fleet;
import fr.unice.polytech.si3.dda.exception.GlobalException;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import fr.unice.polytech.si3.dda.mapping.Mapping;
import fr.unice.polytech.si3.dda.mapping.Warehouse;

import java.util.List;

/**
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MultipleMaxDronePayloadStrategy implements Strategy {
    private Context context;

    public MultipleMaxDronePayloadStrategy(Context context) {
        this.context = context;
    }

    @Override
    public void calculateInstructions() throws GlobalException{

    }

    @Override
    public List<Instruction> getInstructions() {
        return null;
    }

    public void computeMultipleDrone(){
        Fleet fleet = context.getFleet();
        Mapping mapping = context.getMap();


        for (int i = 0; i < context.getMaxDrones(); i++){
            fleet.getDrone(i);





        }


    }

    public void loadDrone(Drone drone, Warehouse warehouse){

    }



}
