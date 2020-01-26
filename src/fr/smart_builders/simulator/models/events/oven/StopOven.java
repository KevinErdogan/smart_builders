package fr.smart_builders.simulator.models.events.oven;

import fr.smart_builders.simulator.models.oven.OvenModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------------

/**
 * The class <code>StopOven</code> describes the event that the oven is 
 * switched off , the consumption of the oven falls to 0
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 				StopOven 
extends 					AbstractOvenEvent
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 5439714766963127454L;

	public 					StopOven(
						Time timeOfOccurrence, 
						EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 			eventAsString () 
	{
		return "Oven::Stop" ; 
	}
	
	@Override
	public boolean 			hasPriorityOver (EventI e)
	{
		if (e instanceof ScheduleRunOven || e instanceof RunOven) {
			return true;
		}
		return false ;
	}
	
	@Override
	public void 			executeOn (AtomicModel model) 
	{
		assert model instanceof OvenModel ; 
		
		((OvenModel) model).stop () ;
	}
	
	
	
}
//------------------------------------------------------------------------------------

















