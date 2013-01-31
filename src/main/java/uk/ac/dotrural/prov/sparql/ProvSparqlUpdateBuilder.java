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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import uk.ac.dotrural.prov.ProvO;
import uk.ac.dotrural.prov.ProvenanceBuilder;

/**
 * Implementation of {@link ProvenanceBuilder} using SPARQL insert queries
 * 
 * @author David Corsar Copywrite David Corsar
 * 
 */
public class ProvSparqlUpdateBuilder implements ProvenanceBuilder {

	private static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

	public static final DateTimeFormatter XML_DATE_TIME_FORMAT = ISODateTimeFormat
			.dateTimeNoMillis();

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
		addUriUpdate(agent1Uri, ProvO.actedOnBehalfOf, agent2Uri);
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
		addLiteralUpdate(activityUri, ProvO.endedAtTime, "\""
				+ toXsdDateTime(timestamp)
				+ "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>");
		return true;
	}

	private String toXsdDateTime(long timestamp) {
		return XML_DATE_TIME_FORMAT.print(timestamp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addStartedAtTime(java.
	 * lang.String, long)
	 */
	public boolean addStartedAtTime(String activityUri, long timestamp) {
		addLiteralUpdate(activityUri, ProvO.startedAtTime, "\""
				+ toXsdDateTime(timestamp)
				+ "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addUsed(java.lang.String,
	 * java.lang.String)
	 */
	public boolean addUsed(String activityUri, String entityUri) {
		addUriUpdate(activityUri, ProvO.used, entityUri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasAssociatedWith(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasAssociatedWith(String activityUri, String agentUri) {
		addUriUpdate(activityUri, ProvO.wasAssociatedWith, agentUri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasAttributedTo(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasAttributedTo(String entityUri, String agentUri) {
		addUriUpdate(entityUri, ProvO.wasAttributedTo, agentUri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasDerivedFrom(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasDerivedFrom(String entity1Uri, String entity2Uri) {
		addUriUpdate(entity1Uri, ProvO.wasDerivedFrom, entity2Uri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasGeneratedBy(java
	 * .lang.String, java.lang.String)
	 */
	public boolean addWasGeneratedBy(String entityUri, String activityUri) {
		addUriUpdate(entityUri, ProvO.wasGeneratedBy, activityUri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#addWasInformedBy(java.
	 * lang.String, java.lang.String)
	 */
	public boolean addWasInformedBy(String activity1Uri, String activity2Uri) {
		addUriUpdate(activity1Uri, ProvO.wasInformedBy, activity2Uri);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#createActivity(java.lang
	 * .String)
	 */
	public String createActivity(String uri) {
		addUriUpdate(uri, RDF_TYPE, ProvO.Activity);
		return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#createAgent(java.lang.
	 * String)
	 */
	public String createAgent(String uri) {
		addUriUpdate(uri, RDF_TYPE, ProvO.Agent);
		return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.dotrural.irp.prov.core.ProvenanceBuilder#createEntity(java.lang
	 * .String)
	 */
	public String createEntity(String uri) {
		addUriUpdate(uri, RDF_TYPE, ProvO.Entity);
		return uri;
	}

	private void addUriUpdate(String s, String p, String o) {
		this.updates.add(String
				.format("INSERT DATA {<%s> <%s> <%s>.}", s, p, o));
	}

	private void addLiteralUpdate(String s, String p, String o) {
		this.updates.add(String.format("INSERT DATA {<%s> <%s> %s.}", s, p, o));
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
	private String generateUri() {
		return this.ns + UUID.randomUUID();
	}

}
