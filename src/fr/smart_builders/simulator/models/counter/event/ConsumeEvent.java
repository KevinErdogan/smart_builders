package fr.smart_builders.simulator.models.counter.event;

import fr.smart_builders.simulator.simulation.ConsumerI;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//----------------------------------------------------------------------------------------------------------
/**
 * The class <code>ConsumeEvent</code> describes the event that the counter is reading 
 * the consumption of all consumers
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								ConsumeEvent 
extends										AbstractCounterEvent
{

	/** generated */
	private static final long 				serialVersionUID = 2274134765049916502L;

	public 									ConsumeEvent(
										Time timeOfOccurrence, 
										EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	public 									ConsumeEvent (
										Time timeOfOccurence)
	{
		super(timeOfOccurence , null) ; 
	}
	
	@Override
	public String 						eventAsString ()
	{
		return "Counter::getConsumptions" ; 
	}
	
	@Override
	public boolean 						hasPriorityOver (EventI e) 
	{
		return false ; 
	}
	
	@Override
	public void 						executeOn (AtomicModel model) 
	{
		// perhaps ass some king of interface for consumer !!!
		// to be able to test if 
		
		assert model instanceof ConsumerI ; 
		
		((ConsumerI) model).giveConsumption() ;   
		
	}
	
	

}
//----------------------------------------------------------------------------------------------------------
