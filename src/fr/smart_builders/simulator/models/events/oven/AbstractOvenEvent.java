package fr.smart_builders.simulator.models.events.oven;

import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//-------------------------------------------------------------------------------------

/**
 * The abstract class <code>AbstractOvenEvent</p> gives a common type for
 * all oven events
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 *
 */
public class 					AbstractOvenEvent 
extends 						ES_Event
{

	/**
	 *  generated
	 */
	private static final long serialVersionUID = -7936661509911805105L;

	
	public 						AbstractOvenEvent(
					Time timeOfOccurrence, 
					EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
}
//-------------------------------------------------------------------------------------








