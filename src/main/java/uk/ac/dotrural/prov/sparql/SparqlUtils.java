package uk.ac.dotrural.prov.sparql;

import java.util.Collection;

public interface SparqlUtils {

	
	public boolean performUpdates(Collection<String> updates);
	
	public Collection<String> performQueryStings(String query);
	
	public Collection<Long> performQueryLong(String query);

}
