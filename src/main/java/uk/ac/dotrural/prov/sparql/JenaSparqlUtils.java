package uk.ac.dotrural.prov.sparql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.XSD;


/**
 * Implementation of {@link SparqlUtils} that uses an in memory Jena ontology model
 * 
 * @author David Corsar
 *
 */
public class JenaSparqlUtils implements SparqlUtils {
	
	private OntModel model;
	
	public JenaSparqlUtils(){
		super();
		this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
	}

	public boolean performUpdates(Collection<String> updates) {
		
		UpdateRequest ur = new UpdateRequest();
		for (String update : updates) {
			ur.add(update);
		}
		UpdateAction.execute(ur, this.model);
		
		return true;
	}

	public Collection<String> performQuerySting(String query) {
		
		QueryExecution queryExecution = QueryExecutionFactory.create(query,this.model);
		ResultSet results = queryExecution.execSelect();
		List<String> resultVars = results.getResultVars();
		if (resultVars.size()>1){
			throw new IllegalArgumentException("Query has more than one variable in the select so I don't know which one to return values for");
		}
		String variable = resultVars.get(0);
		Collection<String> resultsStrs = new ArrayList<String>();
		while (results.hasNext()){
			QuerySolution qs = results.next();
			resultsStrs.add(getNodeStrValue(qs.get(variable)));
		}
		return resultsStrs;
	}

	private String getNodeStrValue(RDFNode rdfNode) {
		if (rdfNode.isURIResource()){
			return rdfNode.asResource().getURI();
		} else if (rdfNode.isResource()){
			return rdfNode.asResource().getURI();
		} else if (rdfNode.isLiteral()){
			return rdfNode.asLiteral().getValue().toString();
		} else if (rdfNode.isAnon()){
			return rdfNode.toString();
		}
		
		return null;
	}

	public Long performQueryLong(String query) {
		QueryExecution queryExecution = QueryExecutionFactory.create(query,this.model);
		ResultSet results = queryExecution.execSelect();
		List<String> resultVars = results.getResultVars();
		if (resultVars.size()>1){
			throw new IllegalArgumentException("Query has more than one variable in the select so I don't know which one to return values for");
		}
		String variable = resultVars.get(0);
		if (results.hasNext()){
			QuerySolution qs = results.next();
			RDFNode node = qs.get(variable);
			if (node.isLiteral()){
				Literal l = node.asLiteral();
				if (l.getDatatype() != XSD.xlong){
					return l.getLong();
				}
			}
		}
		return null;
	}

}
