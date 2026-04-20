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
            System.out.println(OBRLocations);
            return new OpenTopBusRoutingInstance(OBRLocations.size() + 1, OBRLocations.toArray(Location[]::new),depotLocation,oRandom);
        }
        catch (java.io.IOException e){
            System.out.println("Error reading file");
            return null;
        }




    }

    public static void main(String[] args) {
        System.out.println("--- Testing OBRInstanceReader ---");

        // 1. Construct the path to the square.obr file
        // Note: You may need to import java.nio.file.Paths and java.nio.file.Path at the top of the file
        java.nio.file.Path path = java.nio.file.Paths.get("src", "main", "resources", "instances", "obr", "chatgpt-instance-100-PoIs.obr");

        System.out.println("Attempting to read file at: " + path.toAbsolutePath());

        // 2. Instantiate the reader
        OBRInstanceReader reader = new OBRInstanceReader();

        // 3. Call the method with a seeded random number generator
        com.aim.project.obr.interfaces.OBRInstanceInterface instance =
            reader.readOBRInstanceFile(path, new java.util.Random(12345L));

        // 4. Verify the results
        if (instance != null) {
            System.out.println("\nFile parsed successfully!");
            System.out.println("Total Locations (including depot): " + instance.getNumberOfLocations());

            com.aim.project.obr.instance.Location depot = instance.getLocationOfBusDepot();
            System.out.println("Depot Location: (" + depot.x() + ", " + depot.y() + ")");

            System.out.println("Points of Interest:");
            // We loop from 0 to Total Locations - 2 because the depot isn't included in the PoI list
            for (int i = 0; i < instance.getNumberOfLocations() - 1; i++) {
                com.aim.project.obr.instance.Location poi = instance.getLocationForPoI(i);
                System.out.println("  PoI " + i + ": (" + poi.x() + ", " + poi.y() + ")");
            }
        } else {
            System.out.println("\nReader returned null. Make sure you have implemented readOBRInstanceFile!");
        }
    }
}
