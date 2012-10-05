package uk.ac.dotrural.prov.test;

import com.hp.hpl.jena.ontology.OntModel;

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

		prov.addActedOnBehalfOf(prov.getResource("http://dotrural.ac.uk/TestAgent1"), prov.getResource("http://dotrural.ac.uk/TestAgent2"));
		prov.addEndedAtTime(prov.getResource("http://dotrural.ac.uk/TestActivity1"), (System.currentTimeMillis()/1000));
		prov.addStartedAtTime(prov.getResource("http://dotrural.ac.uk/TestActivity1"), (System.currentTimeMillis()/1000));
		prov.addUsed(prov.getResource("http://dotrural.ac.uk/TestActivity1"), prov.getResource("http://dotrural.ac.uk/TestEntity1"));
		prov.addWasAssociatedWith(prov.getResource("http://dotrural.ac.uk/TestActivity1"), prov.getResource("http://dotrural.ac.uk/TestAgent1"));
		prov.addWasAttributedTo(prov.getResource("http://dotrural.ac.uk/TestEntity1"), prov.getResource("http://dotrural.ac.uk/TestAgent1"));
		prov.addWasDerivedFrom(prov.getResource("http://dotrural.ac.uk/TestEntity2"), prov.getResource("http://dotrural.ac.uk/TestEntity1"));
		prov.addWasGeneratedBy(prov.getResource("http://dotrural.ac.uk/TestEntity1"), prov.getResource("http://dotrural.ac.uk/TestActivity1"));
		prov.addWasInformedBy(prov.getResource("http://dotrural.ac.uk/TestActivity1"), prov.getResource("http://dotrural.ac.uk/TestActivity2"));
		
		System.out.println("");
		
		OntModel model = prov.getModel();
		model.write(System.out);
		
		System.out.println("\nFinished...");
	}
	
}
