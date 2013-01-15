package uk.ac.dotrural.prov;

public interface ProvenanceBuilder {
	/**
	 * Add statement describing Agent with URI agent1Uri acting on behalf of
	 * Agent with URI agent2URi
	 * 
	 * @param agent1Uri
	 *            URI of Agent resource
	 * @param agent2Uri
	 *            URI of Agent resource
	 */
	public boolean addActedOnBehalfOf(String agent1Uri, String agent2Uri);

	/**
	 * Add statement describing Activity with URI activityUri ended at timeStamp
	 * 
	 * @param activityUri
	 *            URI of Activity resource
	 * @param timestamp
	 *            The dateTime the activity ended
	 */
	public boolean addEndedAtTime(String activityUri, long timestamp);

	/**
	 * Add statement describing Activity with URI activityUri started at
	 * timeStamp
	 * 
	 * @param activityUri
	 *            URI of Activity resource
	 * @param timestamp
	 *            The dateTime the activity started
	 */
	public boolean addStartedAtTime(String activityUri, long timestamp);

	/**
	 * Add statement describing Activity with URI activityUri used Entity with
	 * URI entityUri
	 * 
	 * @param activityUri
	 *            URI of Activity resource
	 * @param entityUri
	 *            URI of Entity resource
	 */
	public boolean addUsed(String activityUri, String entityUri);

	/**
	 * Add statement describing Activity with URI activityUri was associated
	 * with Agent with URI agentUri
	 * 
	 * @param activityUri
	 *            URI of Activity resource
	 * @param agentUri
	 *            URI of Agent resource
	 */
	public boolean addWasAssociatedWith(String activityUri, String agentUri);

	/**
	 * Add statement describing Entity with URI entityUri was associated with
	 * Agent with URI agentUri
	 * 
	 * @param entityUri
	 *            URI of Entity resource
	 * @param agentUri
	 *            URI of Agent resource
	 */
	public boolean addWasAttributedTo(String entityUri, String agentUri);

	/**
	 * Add statement describing Entity with URI entity1Uri was associated with
	 * Entity with URI entity2Uri
	 * 
	 * @param entity1Uri
	 *            URI of Entity resource
	 * @param entity2Uri
	 *            URI of Entity resource
	 */
	public boolean addWasDerivedFrom(String entity1Uri, String entity2Uri);

	/**
	 * Add statement describing Entity with URI entityUri was generated by
	 * Activity with URI activityUri
	 * 
	 * @param entityUri
	 *            URI of Entity resource
	 * @param activityUri
	 *            URI of Activity resource
	 */
	public boolean addWasGeneratedBy(String entityUri, String activityUri);

	/**
	 * Add statement describing Activity with URI activity1Uri was informed by
	 * Activity with URI activity2Uri
	 * 
	 * @param activity1Uri
	 *            URI of Activity resource
	 * @param activity2Uri
	 *            URI of Activity resource
	 */
	public boolean addWasInformedBy(String activity1Uri, String activity2Uri);

	/**
	 * Add statement describing that a new resource is an Activity
	 * 
	 * @return URI of created resource
	 */
	public String createActivity();

	/**
	 * Add statement describing that resource with URI uri is an Activity
	 * 
	 * @param uri
	 *            URI of resource
	 */
	public String createActivity(String uri);

	/**
	 * Add statement describing that a new resource is an Agent
	 * 
	 * @return URI of creted resource
	 */
	public String createAgent();

	/**
	 * Add statement describing that resource with URI uri is an Agent
	 * 
	 * @param uri
	 *            URI of resource
	 */
	public String createAgent(String uri);

	/**
	 * Add statement describing a new resource is an Entity
	 * 
	 * @return URI created of resource
	 */
	public String createEntity();

	/**
	 * Add statement describing that resource with URI uri is an Entity
	 * 
	 * @param uri
	 *            URI of resource
	 */
	public String createEntity(String uri);
}
