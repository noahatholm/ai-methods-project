package main.java.com.aim.project.obr.instance;

import main.java.com.aim.project.obr.interfaces.OBRInstanceInterface;
import main.java.com.aim.project.obr.interfaces.OBRSolutionInterface;
import main.java.com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import main.java.com.aim.project.obr.solution.OBRSolution;

import java.util.ArrayList;

public class OBRInstance implements OBRInstanceInterface {
    @Override
    public OBRSolution createSolution(InitialisationMode oMode) {
        return null;
    }

    @Override
    public ObjectiveFunctionInterface getOBRObjectiveFunction() {
        return null;
    }

    @Override
    public int getNumberOfLocations() {
        return 0;
    }

    @Override
    public Location getLocationForPoI(int iPointOfInterestId) {
        return null;
    }

    @Override
    public Location getLocationOfBusDepot() {
        return null;
    }

    @Override
    public ArrayList<Location> getSolutionAsListOfLocations(OBRSolutionInterface oSolution) {
        return null;
    }
}
