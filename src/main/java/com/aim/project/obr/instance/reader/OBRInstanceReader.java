package com.aim.project.obr.instance.reader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.util.Scanner;

import com.aim.project.obr.instance.Location;
import com.aim.project.obr.instance.OpenTopBusRoutingInstance;
import com.aim.project.obr.interfaces.OBRInstanceInterface;
import com.aim.project.obr.interfaces.OBRInstanceReaderInterface;




/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRInstanceReader implements OBRInstanceReaderInterface {

	@Override
	public OBRInstanceInterface readOBRInstanceFile(Path oPath, Random oRandom) {
        File OBRFile = new File(oPath.toString());
        try(Scanner OBRReader = new Scanner(OBRFile)){
            //Read name
            String line =  OBRReader.nextLine();
            String name = line.substring(7);

            //Read comment
            line = OBRReader.nextLine();
            String comment = line.substring(10);

            // Read depot location
            OBRReader.nextLine();
            line = OBRReader.nextLine();
            String[] coordinates = line.split(" |[ \t]"); //This is necessary because some of the test data is split by tab other is split by space
            Location depotLocation = new Location(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]));

            //Read Points of Interest
            ArrayList<Location> OBRLocations = new ArrayList<Location>();
            OBRReader.nextLine();
            line =  OBRReader.nextLine();
            while(OBRReader.hasNextLine()) {
                coordinates = line.split(" |[ \t]");
                OBRLocations.add(new Location(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1])));
                line =  OBRReader.nextLine();

            }
            System.out.println(OBRLocations.size());
            return new OpenTopBusRoutingInstance(OBRLocations.size() + 1, OBRLocations.toArray(Location[]::new),depotLocation,oRandom);
        }
        catch (java.io.IOException e){
            System.out.println("Error reading file");
            return null;
        }
    }
}
