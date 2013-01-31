package uk.ac.dotrural.prov.sparql;

import java.util.Collection;

/**
 * Class with some utility methods for performing queries to support the SPARQL implementations
 * 
 * @author David Corsar
 *
 */
public interface SparqlUtils {

	
	public boolean performUpdates(Collection<String> updates);
	
	public Collection<String> performQuerySting(String query);
	
	public Long performQueryLong(String query);

}
