package uk.ac.dotrural.prov.sparql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.dotrural.prov.ProvO;
import uk.ac.dotrural.prov.ProvenanceBuilder;
import uk.ac.dotrural.prov.ProvenanceGetter;

/**
 * A test class that combines testing the {@link ProvSparqlGetter} and
 * {@link ProvSparqlUpdateBuilder}.
 * 
 * @author David Corsar
 * 
 */
public class ProvSparqlBuilderGetterTest {

	private JenaSparqlUtils utils;
	private ProvenanceGetter getter;
	private ProvSparqlUpdateBuilder builder;
	private static final String ns = "http://www.example.com/";
	private static final String agentUri1 = ns + "agent/jamesBond";
	private static final String agentUri2 = ns + "agent/britishGov";
	private static final String entityUri = ns + "entity/coolGadget";
	private static final String entityUri2 = ns + "entity/gadget";
	private static final String entityUri3 = ns + "entity/report";
	private static final String activityUri = ns + "activity/snooping";
	private static final String activityUri2 = ns + "activity/interrogation";

	@Before
	public void setUp() throws Exception {
		this.utils = new JenaSparqlUtils();
		this.getter = new ProvSparqlGetter(this.utils);
		this.builder = new ProvSparqlUpdateBuilder(ns);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testActedOnBehalfOf() {
		builder.addActedOnBehalfOf(agentUri1, agentUri2);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter.getActedOnBehalfOf(agentUri1);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), agentUri2);
	}

	@Test
	public void testEndedAtTime() {
		long end = System.currentTimeMillis() + 60000;
		builder.addEndedAtTime(activityUri, end);
		utils.performUpdates(builder.getUpdates());
		long result = this.getter.getEndedAtTime(activityUri);
		// time is stored to the second, not millisecond, so round end down
		end = end - (end % 1000);
		assertEquals(result, end);
	}

	@Test
	public void testStartedAtTime() {
		long start = System.currentTimeMillis();
		builder.addStartedAtTime(activityUri, start);
		utils.performUpdates(builder.getUpdates());
		long result = this.getter.getStartedAtTime(activityUri);
		// time is stored to the second, not millisecond, so round start down
		start = start - (start % 1000);

		assertEquals(result, start);
	}

	@Test
	public void testUsed() {
		builder.addUsed(activityUri, entityUri);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter.getUsed(activityUri);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), entityUri);
	}

	@Test
	public void testWasAssociatedWith() {
		builder.addWasAssociatedWith(activityUri, agentUri1);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter
				.getWasAssociatedWith(activityUri);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), agentUri1);
	}

	@Test
	public void testWasAttributedTo() {
		builder.addWasAttributedTo(entityUri, agentUri2);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter.getWasAttributedTo(entityUri);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), agentUri2);
	}

	@Test
	public void testWasDerivedFrom() {
		builder.addWasDerivedFrom(entityUri, entityUri2);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter.getWasDerivedFrom(entityUri);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), entityUri2);
	}

	@Test
	public void testWasGeneratedBy() {
		builder.addWasGeneratedBy(entityUri3, activityUri);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter.getWasGeneratedBy(entityUri3);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), activityUri);
	}

	@Test
	public void testWasInformedBy() {
		builder.addWasInformedBy(activityUri, activityUri2);
		utils.performUpdates(builder.getUpdates());
		Collection<String> results = this.getter.getWasInformedBy(activityUri);
		assertTrue(results.size() == 1);
		Iterator<String> it = results.iterator();
		assertEquals(it.next(), activityUri2);
	}

}
