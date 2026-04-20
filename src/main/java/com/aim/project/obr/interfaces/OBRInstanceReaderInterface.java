package main.java.com.aim.project.obr.interfaces;

import java.nio.file.Path;
import java.util.Random;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface OBRInstanceReaderInterface {

	/**
	 * 
	 * @param oPath The path to the instance file.
	 * @param oRandom The oRandom number generator to use.
	 * @return A new instance of the OBR problem as specified by the instance file.
	 */
	public OBRInstanceInterface readOBRInstanceFile(Path oPath, Random oRandom);
}
