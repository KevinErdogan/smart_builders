package fr.smart_builders.simulator.models.events.battery;

import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------------------------------
/**
 * The abstract class <code>AbstractBatteryEvent</code> gives a common 
 * type for all battery events
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan </p>
 */
public class 								AbstractBatteryEvent 
extends 									ES_Event
{

	/**	generated */
	private static final long serialVersionUID = -5777911161967835605L;

	public 									AbstractBatteryEvent(
										Time timeOfOccurrence, 
										EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
}
//--------------------------------------------------------------------------------------------------------
