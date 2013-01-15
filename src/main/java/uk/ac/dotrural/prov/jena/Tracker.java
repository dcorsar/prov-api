package uk.ac.dotrural.prov.jena;

/**
 * Class for logging provenance assertions and retractions
 * 
 * @author Chris Baillie
 */

public class Tracker {

	public void log(String msg)
	{
		System.out.println(msg);
	}
	
	public void error(String msg)
	{
		System.err.println(msg);
	}
	
}
