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
		prov.createEntity("http://dotrural.ac.uk/TestEntity2");
		prov.createActivity("http://dotrural.ac.uk/TestActivity1");
		prov.createActivity("http://dotrural.ac.uk/TestActivity2");
		prov.createAgent("http://dotrural.ac.uk/TestAgent1");
		prov.createAgent("http://dotrural.ac.uk/TestAgent2");
		
		System.out.println("");

		prov.addEndedAtTime("http://dotrural.ac.uk/TestActivity1", (System.currentTimeMillis()/1000));
		prov.addActedOnBehalfOf("http://dotrural.ac.uk/TestAgent1", "http://dotrural.ac.uk/TestAgent2");
		prov.addStartedAtTime("http://dotrural.ac.uk/TestActivity1", (System.currentTimeMillis()/1000));
		prov.addWasAssociatedWith("http://dotrural.ac.uk/TestActivity1", "http://dotrural.ac.uk/TestAgent1");
		prov.addUsed("http://dotrural.ac.uk/TestActivity1", "http://dotrural.ac.uk/TestEntity1");
		prov.addWasAttributedTo("http://dotrural.ac.uk/TestEntity1", "http://dotrural.ac.uk/TestAgent1");
		prov.addWasDerivedFrom("http://dotrural.ac.uk/TestEntity2", "http://dotrural.ac.uk/TestEntity1");
		prov.addWasGeneratedBy("http://dotrural.ac.uk/TestEntity2", "http://dotrural.ac.uk/TestActivity1");
		prov.addWasInformedBy("http://dotrural.ac.uk/TestActivity1", "http://dotrural.ac.uk/TestActivity2");
		
		System.out.println("");
		
		prov.write(System.out);
		
		System.out.println("\nFinished...");
	}
	
}
