package uk.ac.dotrural.prov.model;

import java.io.OutputStream;
import java.util.UUID;

import uk.ac.dotrural.prov.tracking.Tracker;

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
		if(checkType(agent1, "Agent") && checkType(agent2, "Agent"))
		{
			add(prov.createResource(agent1), prov.createProperty(PROV_NS + "actedOnBehalfOf"), prov.createResource(agent2));
			return true;
		}
		tracker.error("Both arguments must be of type Agent");
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
		if(checkType(activity, "Activity"))
		{
			add(prov.createResource(activity), prov.createProperty(PROV_NS + "endedAtTime"), prov.createTypedLiteral(timestamp));
			return true;
		}
		tracker.error(activity + " is not of type Activity");
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
		if(checkType(activity, "Activity"))
		{
			add(prov.createResource(activity), prov.createProperty(PROV_NS + "startedAtTime"), prov.createTypedLiteral(timestamp));
			return true;
		}
		tracker.error(activity + " is not of type Activity");
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
		if(checkType(activity, "Activity"))
		{
			if(checkType(entity, "Entity"))
			{
				add(prov.createResource(activity), prov.createProperty(PROV_NS + "used"), prov.createResource(entity));
				return true;
			}
			else
			{
				tracker.error(entity + " is not an entity");
			}
		}
		else
		{
			tracker.error(activity + " is not an activity");
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
		if(checkType(activity, "Activity"))
		{
			if(checkType(agent, "Agent"))
			{
				add(prov.createResource(activity), prov.createProperty(PROV_NS + "wasAssociatedWith"), prov.createResource(agent));
				return true;
			}
			else
			{
				tracker.error(agent + " is not an Agent");
			}
		}
		else
		{
			tracker.error(activity + " is not an activity");
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
		if(checkType(entity, "Entity"))
		{
			if(checkType(agent, "Agent"))
			{
				add(prov.createResource(entity), prov.createProperty(PROV_NS + "wasAttributedTo"), prov.createResource(agent));
				return true;
			}
			else
			{
				tracker.error(agent + " is not an agent");
			}
		}
		else
		{
			tracker.error(entity + " is not an entity");
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
		if(checkType(entity1, "Entity") && checkType(entity2, "Entity"))
		{
			add(prov.createResource(entity1), prov.createProperty(PROV_NS + "wasDerivedFrom"), prov.createResource(entity2));
			return true;
		}
		else
		{
			tracker.error("Both arguments must be entities");
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
		if(checkType(entity, "Entity"))
		{
			if(checkType(activity, "Activity"))
			{
				add(prov.createResource(entity), prov.createProperty(PROV_NS + "wasGeneratedBy"), prov.createResource(activity));
				return true;
			}
			else
			{
				tracker.error(activity + " is not an Activity");
			}
		}
		else
		{
			tracker.error(entity + " is not an Entity");
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
		if(checkType(activity1, "Activity") && checkType(activity2, "Activity"))
		{
			add(prov.createResource(activity1), prov.createProperty(PROV_NS + "wasInformedBy"), prov.createProperty(activity2));
		}
		else
		{
			tracker.error("Both arguments must be an activity");
		}
		return false;
	}

	/**
	 * Query the model for the resource with a given URI
	 * 
	 * @param uri The URI of the resource
	 * @return The resource
	 */
	private boolean checkType(String uri, String type)
	{
		tracker.log(uri + " was accessed at " + (System.currentTimeMillis() / 1000));
		return prov.contains(createStatement(prov.createResource(uri), prov.createProperty(RDF_NS + "type"), prov.createResource(PROV_NS + type)));
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
		tracker.log("Statement created " + subject.getLocalName() + " " + predicate.getLocalName() + " " + object.getLocalName());
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
		tracker.log("Statement created " + subject.getLocalName() + " " + predicate.getLocalName() + " " + object.getValue());
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
	 * Write the provenance model to the given OutputStream
	 * 
	 * @param out The OutputStream to write the model to
	 */
	public void write(OutputStream out)
	{
		prov.write(out);
	}
}
