package uk.ac.dotrural.prov.sparql;

import java.util.Collection;

import uk.ac.dotrural.prov.ProvO;
import uk.ac.dotrural.prov.ProvenanceGetter;

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
		// TODO Auto-generated method stub
		return 0;
	}

	public long getStartedAtTime(String activityUri) {
		// TODO Auto-generated method stub
		return 0;
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
		return utils.performQueryStings(query);
	}
}
