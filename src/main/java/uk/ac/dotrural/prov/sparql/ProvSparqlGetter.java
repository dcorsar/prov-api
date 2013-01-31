package uk.ac.dotrural.prov.sparql;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import uk.ac.dotrural.prov.ProvO;
import uk.ac.dotrural.prov.ProvenanceGetter;

/**
 * Implementation of {@link ProvenanceGetter} using SPARQL queries
 * 
 * @author David Corsar
 *
 */
public class ProvSparqlGetter implements ProvenanceGetter {

	private SparqlUtils utils;

	public ProvSparqlGetter(SparqlUtils utils){
		super();
		this.utils = utils;
	}
	
	public Collection<String> getActedOnBehalfOf(String agentUri) {
		return executeQuery(agentUri, ProvO.actedOnBehalfOf);
	}

	public long getEndedAtTime(String activityUri) {
		Collection<String> results = executeQuery(activityUri, ProvO.endedAtTime);
		Iterator<String> s = results.iterator();
		if (s.hasNext()){
			return ProvSparqlUpdateBuilder.XML_DATE_TIME_FORMAT.parseDateTime(s.next()).getMillis();
		}
		return Long.MIN_VALUE;
	}

	public long getStartedAtTime(String activityUri) {
		Collection<String> results = executeQuery(activityUri, ProvO.startedAtTime);
		Iterator<String> s = results.iterator();
		if (s.hasNext()){
			return ProvSparqlUpdateBuilder.XML_DATE_TIME_FORMAT.parseDateTime(s.next()).getMillis();
		}
		return Long.MIN_VALUE;
	}

	public Collection<String> getUsed(String activityUri) {
		return executeQuery(activityUri, ProvO.used);
	}

	public Collection<String> getWasAssociatedWith(String activityUri) {
		return executeQuery(activityUri, ProvO.wasAssociatedWith);
	}

	public Collection<String> getWasAttributedTo(String entityUri) {
		return executeQuery(entityUri, ProvO.wasAttributedTo);
	}

	public Collection<String> getWasDerivedFrom(String entityUri) {
		return executeQuery(entityUri, ProvO.wasDerivedFrom);
	}

	public Collection<String> getWasGeneratedBy(String entityUri) {
		return executeQuery(entityUri, ProvO.wasGeneratedBy);
	}

	public Collection<String> getWasInformedBy(String activityUri) {
		return executeQuery(activityUri, ProvO.wasInformedBy);
	}

	private Collection<String> executeQuery(String subjectUri, String predicate) {
		String query = String.format("SELECT ?var WHERE {<%s> <%s> ?var.}",
				subjectUri, predicate);
		return utils.performQuerySting(query);
	}
}
