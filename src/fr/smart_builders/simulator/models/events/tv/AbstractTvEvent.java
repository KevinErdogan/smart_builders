package fr.smart_builders.simulator.models.events.tv;

import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//---------------------------------------------------------------------------------------------

/**
 * The class <code>AbstractTvEvent</code> gives a common type for 
 * all tv events 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 			AbstractTvEvent 
extends 				ES_Event
{

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = -6477660061200281557L;

	public 				AbstractTvEvent(
					Time timeOfOccurrence, 
					EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}

}
//---------------------------------------------------------------------------------------------

