package fr.smart_builders.simulator.models.events;

import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//-----------------------------------------------------------------------------


/**
 * The abstract class <code>AbstractFridgeEvent</code> gives a common 
 * type for all fridge events
 * 
 * normally there are three
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan </p>
 */
public class 			AbstractFridgeEvent 
extends 				ES_Event
{

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = 556361262877949306L;

	public 				AbstractFridgeEvent(
				Time timeOfOccurrence, 
				EventInformationI content
				) {
		super(timeOfOccurrence, content);
	}
}

//-----------------------------------------------------------------------------















