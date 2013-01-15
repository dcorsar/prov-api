package uk.ac.dotrural.prov.jena;

import java.io.OutputStream;
import java.util.UUID;

import uk.ac.dotrural.prov.ProvenanceBuilder;
import uk.ac.dotrural.prov.jena.Tracker;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
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

public class ProvenanceBundle implements ProvenanceBuilder{
	
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
	 * Add a statement to the model
	 * 
	 * @param s A resource describing the subject
	 * @param p Property
	 * @param o A resource describing the object
	 * @return The URI of the subject
	 */
	private String add(Resource s, Property p, Resource o)
	{
		Statement stmt = createStatement(s, p, o); 
		prov.add(stmt);
		tracker.log(stmt.toString() + " added to model");
		return s.getURI();		
	}

	/**
	 * Add a statement to the model
	 * 
	 * @param s A resource describing the subject
	 * @param p Property
	 * @param o A literal describing the object
	 * @return The URI of the subject
	 */
	private String add(Resource s, Property p, Literal o)
	{
		Statement stmt = createStatement(s, p, o);
		prov.add(stmt);
		tracker.log(stmt.toString() + " added to model");
		return s.getURI();
	}

	/**
	 * Add statement describing agent1 acting on behalf of another
	 * 
	 * @param agent1 The agent associated with an activity
	 * @param agent2 The agent acted on behalf of
	 * @return boolean indicating success
	 */
	public boolean addActedOnBehalfOf(String agent1, String agent2)
	{
		return addActedOnBehalfOf(prov.createResource(agent1), prov.createResource(agent2));
	}
	
	/**
	 * Add statement describing agent1 acting on behalf of another
	 * 
	 * @param agent1 Agent resource
	 * @param agent2 Agent resource
	 * @return boolean indicating success
	 */
	public boolean addActedOnBehalfOf(Resource agent1, Resource agent2)
	{
		if(checkType(agent1, "Agent") && checkType(agent2, "Agent"))
		{
			add(agent1, prov.createProperty(PROV_NS + "actedOnBehalfOf"), agent2);
			return true;				
		}
		return false;
	}

	/**
	 * Add statement describing when an activity ended
	 * 
	 * @param activity The activity that ended at timestamp
	 * @param timestamp The time at which activity ended
	 * @return boolean indicating success
	 */
	public boolean addEndedAtTime(String activity, long timestamp)
	{
		return addEndedAtTime(prov.createResource(activity), timestamp);
	}
	
	/**
	 * Add statement describing when an activity ended
	 * 
	 * @param activity The activity that ended at timestamp
	 * @param timestamp The time at which activity ended
	 * @return boolean indicating success
	 */
	public boolean addEndedAtTime(Resource activity, long timestamp)
	{
		if(checkType(activity, "Activity"))
		{
			add(activity, prov.createProperty(PROV_NS + "endedAtTime"), prov.createTypedLiteral(timestamp));
		}
		return false;
	}

	/**
	 * Add statement describing when an activity started
	 * 
	 * @param activity The activity that started at timestamp
	 * @param timestamp The time at which activity started
	 * @return boolean indicating success
	 */
	public boolean addStartedAtTime(String activity, long timestamp)
	{
		return addStartedAtTime(prov.createResource(activity), timestamp);
	}
	
	/**
	 * Add statement describing when an activity started
	 * 
	 * @param activity The activity that started at timestamp
	 * @param timestamp The time at which activity started
	 * @return boolean indicating success
	 */
	public boolean addStartedAtTime(Resource activity, long timestamp)
	{
		if(checkType(activity, "Activity"))
		{
			add(activity, prov.createProperty(PROV_NS + "startedAtTime"), prov.createTypedLiteral(timestamp));
			return true;
		}
		return false;		
	}

	/**
	 * Add statement describing an entity used in an activity
	 * 
	 * @param activity The activity that used entity
	 * @param entity The entity used  by activity
	 * @return boolean indicating success
	 */
	public boolean addUsed(String activity, String entity)
	{
		return addUsed(prov.createResource(activity), prov.createResource(entity));
	}
	
	/**
	 * Add statement describing an entity used in an activity
	 * 
	 * @param activity The activity that used entity
	 * @param entity The entity used  by activity
	 * @return boolean indicating success
	 */
	public boolean addUsed(Resource activity, Resource entity)
	{
		if(checkType(activity, "Activity") && checkType(entity, "Entity"))
		{
			add(activity, prov.createProperty(PROV_NS + "used"), entity);
			return true;
		}
		return false;
	}

	/**
	 * Add statement describing the association between an activity and agent
	 * @param activity The activity associated with an agent
	 * @param agent The agent associated with the activity
	 * @return boolean indicating success
	 */
	public boolean addWasAssociatedWith(String activity, String agent)
	{
		return addWasAssociatedWith(prov.createResource(activity), prov.createResource(agent));
	}

	/**
	 * Add statement describing the association between an activity and agent
	 * @param activity The activity associated with an agent
	 * @param agent The agent associated with the activity
	 * @return boolean indicating success
	 */
	public boolean addWasAssociatedWith(Resource activity, Resource agent)
	{
		if(checkType(activity, "Activity") && checkType(agent, "Agent"))
		{
			add(activity, prov.createProperty(PROV_NS + "wasAssociatedWith"), agent);
			return true;
		}
		return false;
	}
	
	/**
	 * Add a statement describing the agent an entity is attributed to
	 * 
	 * @param entity The entity attributed to agent
	 * @param agent The agent entity is attributed to
	 * @return boolean indicating success
	 */
	public boolean addWasAttributedTo(String entity, String agent)
	{
		return addWasAttributedTo(prov.createResource(entity), prov.createResource(agent));
	}
	
	/**
	 * Add a statement describing the agent an entity is attributed to
	 * 
	 * @param entity The entity attributed to agent
	 * @param agent The agent entity is attributed to
	 * @return boolean indicating success
	 */
	public boolean addWasAttributedTo(Resource entity, Resource agent)
	{
		if(checkType(entity, "Entity") && checkType(agent, "Agent"))
		{
			add(entity, prov.createProperty(PROV_NS + "wasAttributedTo"), agent);
			return true;
		}
		return false;
	}

	/**
	 * Add a statement describing entity1 being derived from entity2
	 * 
	 * @param entity1 The entity derived from entity2
	 * @param entity2 The entity used to derive entity1
	 * @return boolean indicating success
	 */
	public boolean addWasDerivedFrom(String entity1, String entity2)
	{
		return addWasDerivedFrom(prov.createResource(entity1), prov.createResource(entity2));
	}
	
	/**
	 * Add a statement describing entity1 being derived from entity2
	 * 
	 * @param entity1 The entity derived from entity2
	 * @param entity2 The entity used to derive entity1
	 * @return boolean indicating success
	 */
	public boolean addWasDerivedFrom(Resource entity1, Resource entity2)
	{
		if(checkType(entity1, "Entity") && checkType(entity2, "Entity"))
		{
			add(entity1, prov.createProperty(PROV_NS + "wasDerivedFrom"), entity2);
		}
		return false;
	}

	/**
	  * Add a statement describing entity being generated by activity
	  * 
	  * @param entity The entity generated by activity
	  * @param activity The activity that generated entity
	  * @return
	  */
	public boolean addWasGeneratedBy(String entity, String activity)
	{
		return addWasGeneratedBy(prov.createResource(entity), prov.createResource(activity));
	}
	
	/**
	  * Add a statement describing entity being generated by activity
	  * 
	  * @param entity The entity generated by activity
	  * @param activity The activity that generated entity
	  * @return
	  */
	public boolean addWasGeneratedBy(Resource entity, Resource activity)
	{
		if(checkType(entity, "Entity") && checkType(activity, "Activity"))
		{
			add(entity, prov.createProperty(PROV_NS + "wasGeneratedBy"), activity);
			return true;
		}
		return false;
	}

	/**
	 * Add a statement describing an activity being informed by another activity 
	 * @param activity1
	 * @param activity2
	 * @return boolean indicating success
	 */
	public boolean addWasInformedBy(String activity1, String activity2)
	{
		return addWasInformedBy(prov.createResource(activity1), prov.createResource(activity2));
	}
	
	/**
	 * Add a statement describing an activity being informed by another activity 
	 * @param activity1
	 * @param activity2
	 * @return boolean indicating success
	 */
	public boolean addWasInformedBy(Resource activity1, Resource activity2)
	{
		if(checkType(activity1, "Activity") && checkType(activity2, "Activity"))
		{
			add(activity1, prov.createProperty(PROV_NS + "wasInformedBy"), activity2);
		}
		else
		{
			tracker.error("Both arguments must be an activity");
		}
		return false;
	}
	
	/**
	 * Check the type of the given Resource
	 * 
	 * @param r The resource to type check
	 * @param type String describing the type of resource
	 * @return boolean indicating success
	 */
	private boolean checkType(Resource r, String type)
	{
		tracker.log(r.getURI() + " was accessed at " + (System.currentTimeMillis() / 1000));
		boolean success = prov.contains(createStatement(r, prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + type)));
		if(!success)
			tracker.error(r.getURI() + " is not of type " + type);
		return success;
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
		return add(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Activity"));
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
		return add(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Agent"));
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
		return add(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + "Entity"));
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
		//tracker.log("Statement created " + subject.getLocalName() + " " + predicate.getLocalName() + " " + object.getLocalName());
		return stmt;
	}

	/**
	 * Create a JENA statement
	 *  
	 * @param subject The subject
	 * @param predicate The predicate
	 * @param object The object
	 * @return The statement
	 */
	private Statement createStatement(Resource subject, Property predicate, Literal object)
	{
		Statement stmt = prov.createStatement(subject, predicate, object);
		//tracker.log("Statement created " + subject.getLocalName() + " " + predicate.getLocalName() + " " + object.getValue());
		return stmt;
	}

	/**
	 * Generate a URI containing a UUID
	 * 
	 * @return The new unique URI
	 */
	private String generateUri()
	{
		return namespace + UUID.randomUUID();
	}
	
	/**
	 * Get the entire OntModel
	 * 
	 * @return OntModel
	 */
	public OntModel getModel()
	{
		return prov;
	}
	
	/**
	 * Get/create a resource from the model
	 *  
	 * @param uri The URI of the resource
	 * @return The resource
	 */
	public Resource getResource(String uri)
	{
		return prov.createResource(uri);
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
}
