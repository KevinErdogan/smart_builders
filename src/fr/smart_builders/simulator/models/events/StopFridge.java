package fr.smart_builders.simulator.models.events;

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;
//----------------------------------------------------------------------------

/**
 * The class <code>StopFridge</code> describes the event that the fridge
 * stops running, its consumption falls to a negligible value
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 			StopFridge 
extends 				AbstractFridgeEvent
{
	
	/**
	 * 	generated
	 */
	private static final long serialVersionUID = 2586720976116658966L;

	public StopFridge(Time timeOfOccurrence, EventInformationI content) {
		super(timeOfOccurrence, content);
	}
	
	
	@Override
	public String 			eventAsString () 
	{
		return "Fridge::Stop" ; 
	}
	
	@Override
	public boolean 			hasPriorityOver (EventI e) 
	{
		if (e instanceof RunFridge ) {
			return true;
		}
		return false;
	}
	
	@Override
	public void 			executeOn (AtomicModel model)
	{
		assert 	model instanceof FridgeModel ; 
		
		((FridgeModel) model).setState (FridgeModel.State.STOP) ;
	}
	



}
//----------------------------------------------------------------------------












