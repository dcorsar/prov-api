package uk.ac.dotrural.prov.model;

import java.io.OutputStream;
import java.util.UUID;

import uk.ac.dotrural.prov.tracking.Tracker;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * Handle a Provenance model and provide methods for
 * creating entities, activities, and agents
 * 
 * @author Chris Baillie
 */

public class ProvenanceBundle {
	
	private OntModel prov;
	private String NAMESPACE;
	private Tracker tracker;
	
	// Namespaces
	private String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private String PROV_NS = "http://www.w3.org/ns/prov#";
	
	public ProvenanceBundle(String ns)
	{
		NAMESPACE = ns;
		prov = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		tracker = new Tracker();
		
		//Set namespaces
		prov.setNsPrefix("prov", PROV_NS);
		prov.setNsPrefix("rdf", RDF_NS);
	}
	
	/**
	 * Create a new entity and add it to prov model
	 * 
	 * @return The URI of the new entity
	 */
	public String createEntity()
	{
		String uri = NAMESPACE + UUID.randomUUID();
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Entity")));
		tracker.log("Entity created with URI " + uri);
		return uri;
	}
	
	/**
	 * Add an existing entity to prov model
	 * 
	 * @param uri The URI of the entity
	 * @return The URI of the entity
	 */
	public String createEntity(String uri)
	{
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Entity")));
		tracker.log("Entity created with URI " + uri);
		return uri;
	}
	
	/**
	 * Create a new activity and add it to prov model
	 * 
	 * @return The URI of the new activity
	 */
	public String createActivity()
	{
		String uri = NAMESPACE + UUID.randomUUID();
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Activity")));
		tracker.log("Activity created with URI " + uri);
		return uri;
	}
	
	/** Add an existing activity to prov model
	 * 
	 * @param uri The URI of the activity
	 * @return The URI of the activity
	 */
	public String createActivity(String uri)
	{
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Activity")));
		tracker.log("Activity created with URI " + uri);
		return uri;
	}
	
	/**
	 * Create a new agent and add it to prov model
	 * 
	 * @return The URI of the agent
	 */
	public String createAgent()
	{
		String uri = NAMESPACE + UUID.randomUUID();
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Agent")));
		tracker.log("Activity created with URI " + uri);
		return uri;
	}
	
	/**
	 * Add an existing agent to prov model
	 * 
	 * @return The URI of the agent
	 */
	public String createAgent(String uri)
	{
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Agent")));
		tracker.log("Activity created with URI " + uri);
		return uri;
	}
	
	public void write(OutputStream out)
	{
		prov.write(out);
	}
	
	private Statement createStatement(Resource subject, Property predicate, Resource object)
	{
		Statement stmt = prov.createStatement(subject, predicate, object);
		tracker.log("Statement created " + subject.getLocalName() + " " + predicate.getLocalName() + " " + object.getLocalName());
		return stmt;
	}
}
