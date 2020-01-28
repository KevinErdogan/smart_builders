package fr.smart_builders.simulator.models.events.solarpanel;

import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//----------------------------------------------------------------------------------------------------

/**
 * The abstract class <code>AbstractSolarPanelEvent</code> gives a common type 
 * for all oven events 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 * 
 */
public class 								AbstractSolarPanelEvent 
extends 									ES_Event
{

	/** generated */
	private static final long 				serialVersionUID = 7429319654377633973L;

	
	public 									AbstractSolarPanelEvent(
										Time timeOfOccurrence, 
										EventInformationI content) 
	{ 
		super(timeOfOccurrence, content);
	}

}
//----------------------------------------------------------------------------------------------------








