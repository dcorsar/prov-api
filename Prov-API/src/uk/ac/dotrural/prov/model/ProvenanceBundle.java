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
	private String namespace;
	private Tracker tracker;
	
	// Namespaces
	private static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static final String PROV_NS = "http://www.w3.org/ns/prov#";
	
	public ProvenanceBundle(String ns)
	{
		namespace = ns;
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
		return createEntity(this.generateUri());
	}
	
	/**
	 * Add an existing entity to prov model
	 * 
	 * @param uri The URI of the entity
	 * @return The URI of the entity
	 */
	public String createEntity(String uri)
	{
		return add(uri, "Entity");
	}
	
	/**
	 * Create a new activity and add it to prov model
	 * 
	 * @return The URI of the new activity
	 */
	public String createActivity()
	{
		return createActivity(generateUri());
	}
	
	/** Add an existing activity to prov model
	 * 
	 * @param uri The URI of the activity
	 * @return The URI of the activity
	 */
	public String createActivity(String uri)
	{
		return add(uri, "Activity");
	}
	
	/**
	 * Create a new agent and add it to prov model
	 * 
	 * @return The URI of the agent
	 */
	public String createAgent()
	{
		return createAgent(generateUri());
	}
	
	/**
	 * Add an existing agent to prov model
	 * 
	 * @return The URI of the agent
	 */
	public String createAgent(String uri)
	{
		return add(uri, "Agent");
	}
	
	public String add(String uri, String type)
	{
		prov.add(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + type)));
		tracker.log(type + " created with URI " + uri);
		return uri;		
	}
	
	/**
	 * Query the model for the resource with a given URI
	 * 
	 * @param uri The URI of the resource
	 * @return The resource
	 */
	public Resource get(String uri)
	{
		
		
		tracker.log("");
		return null;
	}
	
	/**
	 * Add statement describing agent1 acting on behalf of another
	 * 
	 * @param agent1 The agent associated with an activity
	 * @param agent2 The agent acted on behalf of
	 * @return boolean indicating success
	 */
	public boolean addActedOnBehalfOfStatement(String agent1, String agent2)
	{
		return false;
	}
	
	public String generateUri()
	{
		return namespace + UUID.randomUUID();
	}
	
	/**
	 * Write the provenance model to the given OutputStream
	 * 
	 * @param out The OutputStream to write the model to
	 */
	public void write(OutputStream out)
	{
		prov.write(out);
	}
	
	/**
	 * Create a JENA statement
	 *  
	 * @param subject The subject
	 * @param predicate The predicate
	 * @param object The object
	 * @return The statement
	 */
	private Statement createStatement(Resource subject, Property predicate, Resource object)
	{
		Statement stmt = prov.createStatement(subject, predicate, object);
		tracker.log("Statement created " + subject.getLocalName() + " " + predicate.getLocalName() + " " + object.getLocalName());
		return stmt;
	}
}
