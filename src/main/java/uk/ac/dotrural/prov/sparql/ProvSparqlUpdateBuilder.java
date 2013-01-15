/**
 * File ProvSparqlUpdateBuilder.java
 * Package uk.ac.dotrural.irp.prov.core
 * Created 5 Dec 2012
 * Author David Corsar
 * Copywrite David Corsar
 * 
 * Change history
 */
package uk.ac.dotrural.prov.sparql;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import uk.ac.dotrural.prov.ProvO;
import uk.ac.dotrural.prov.ProvenanceBuilder;

/**
 * @author David Corsar Copywrite David Corsar
 * 
 */
public class ProvSparqlUpdateBuilder implements ProvenanceBuilder {

	private Collection<String> updates;
	private String ns;
	
	public ProvSparqlUpdateBuilder(String ns) {
		super();
		this.ns = ns;
		this.updates = new LinkedList<String>();
	}

	public Collection<String> getUpdates() {
		Collection<String> updateCopy = new LinkedList<String>();
		updateCopy.addAll(this.updates);
		return updateCopy;
	}

	public boolean clearUpdates() {
		this.updates.clear();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addActedOnBehalfOf(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addActedOnBehalfOf(String agent1Uri, String agent2Uri) {
		addUpdate(agent1Uri, ProvO.actedOnBehalfOf, agent2Uri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addEndedAtTime(java.lang
	 * .String, long)
	 */
	public boolean addEndedAtTime(String activityUri, long timestamp) {
		addUpdate(activityUri, ProvO.endedAtTime, "\"" + timestamp
				+ "\"^^http://www.w3.org/2001/XMLSchema#dateTime");	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addStartedAtTime(java.
	 * lang.String, long)
	 */
	public boolean addStartedAtTime(String activityUri, long timestamp) {
		addUpdate(activityUri, ProvO.startedAtTime, "\"" + timestamp
				+ "\"^^http://www.w3.org/2001/XMLSchema#dateTime");	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addUsed(java.lang.String,
	 * java.lang.String)
	 */
	public boolean addUsed(String activityUri, String entityUri) {
		addUpdate(activityUri, ProvO.used, entityUri);	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasAssociatedWith(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasAssociatedWith(String activityUri, String agentUri) {
		addUpdate(activityUri, ProvO.wasAssociatedWith, agentUri);	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasAttributedTo(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasAttributedTo(String entityUri, String agentUri) {
		addUpdate(entityUri, ProvO.wasAttributedTo, agentUri);	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasDerivedFrom(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasDerivedFrom(String entity1Uri, String entity2Uri) {
		addUpdate(entity1Uri, ProvO.wasDerivedFrom, entity2Uri);	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasGeneratedBy(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasGeneratedBy(String entityUri, String activityUri) {
		addUpdate(entityUri, ProvO.wasGeneratedBy, activityUri);	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasInformedBy(java.
	 * lang.String, java.lang.String)
	 */
	public boolean addWasInformedBy(String activity1Uri, String activity2Uri) {
		addUpdate(activity1Uri, ProvO.wasInformedBy, activity2Uri);	return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#createActivity(java.lang
	 * .String)
	 */
	public String createActivity(String uri) {
		addUpdate(uri, "a", ProvO.Activity);	return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#createAgent(java.lang.
	 * String)
	 */
	public String createAgent(String uri) {
		addUpdate(uri, "a", ProvO.Agent);	return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#createEntity(java.lang
	 * .String)
	 */
	public String createEntity(String uri) {
		addUpdate(uri, "a", ProvO.Entity);	return uri;
	}

	private void addUpdate(String s, String p, String o) {
		this.updates.add(String.format("INSERT {%s %s %s.}", s, p, o));
	}

	public String createActivity() {
		String uri = generateUri();
		createActivity(uri);
		return uri;
	}

	public String createAgent() {
		String uri = generateUri();
		createAgent(uri);
		return uri;
	}

	public String createEntity() {
		String uri = generateUri();
		createEntity(uri);
		return uri;
	}
	
	/**
	 * Generate a URI containing a UUID
	 * 
	 * @return The new unique URI
	 */
	private String generateUri()
	{
		return this.ns + UUID.randomUUID();
	}
	 

}
