package uk.ac.dotrural.prov.test;

import uk.ac.dotrural.prov.model.ProvenanceBundle;

public class Test {
	
	public static void main(String[] args)
	{
		new Test();
	}

	public Test()
	{
		System.out.println("Running test...\n");
		
		ProvenanceBundle prov = new ProvenanceBundle("http://dotrural.ac.uk/");
		
		prov.createEntity("http://dotrural.ac.uk/TestEntity1");
		prov.createActivity("http://dotrural.ac.uk/TestActivity1");
		prov.createAgent("http://dotrural.ac.uk/TestAgent1");
		
		System.out.println("");
		
		
		System.out.println("");
		
		prov.write(System.out);
		
		System.out.println("\nFinished...");
	}
	
}
