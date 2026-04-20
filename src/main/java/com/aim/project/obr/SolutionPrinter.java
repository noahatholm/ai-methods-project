package main.java.com.aim.project.obr;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import com.aim.project.obr.instance.Location;

public class SolutionPrinter {

	private final String strOutputFilePath;
	
	public SolutionPrinter(String strOutputFilePath) {
		
		this.strOutputFilePath = strOutputFilePath;
	}
	
	/**
	 * 
	 * @param loRouteLocations The array of Locations ordered in route order.
	 */
	public void printSolution(List<Location> loRouteLocations) {

		OutputStream os;
		try {
			os = new FileOutputStream(strOutputFilePath);
			PrintStream printStream = new PrintStream(os);

			Iterator<Location> oLocationIterator = loRouteLocations.iterator();
			Location oLocation;

			if(oLocationIterator.hasNext()) {

				oLocation = oLocationIterator.next();
				printStream.print(oLocation.iLocationId());
			}

			while(oLocationIterator.hasNext()) {

				oLocation = oLocationIterator.next();
				printStream.print("-" + oLocation.iLocationId());
			}
			printStream.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
}
