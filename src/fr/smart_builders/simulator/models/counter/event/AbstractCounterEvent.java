package fr.smart_builders.simulator.models.counter.event;
import fr.sorbonne_u.devs_simulation.es.events.ES_Event;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;


//--------------------------------------------------------------------------------------------------
/**
 * The class <code>AbstractCounterEvent</code> gives a common type for 
 * all counter events
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								AbstractCounterEvent 
extends 									ES_Event
{

	/**
	 * 	generated
	 */
	private static final long 				serialVersionUID = -6030990310277881074L;

	public 									AbstractCounterEvent(
										Time timeOfOccurrence, 
										EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}

	
}
