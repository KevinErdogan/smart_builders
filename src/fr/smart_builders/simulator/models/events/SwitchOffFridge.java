package fr.smart_builders.simulator.models.events;
//-----------------------------------------------------------------------------

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

/**
 * The class <code>SwitchOffFridge</code> defines the event of the fridge
 * is not allowed to run
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan </p>
 */
public class 				SwitchOffFridge
extends 					AbstractFridgeEvent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1844854859948456875L;
	
	public 					SwitchOffFridge(
				Time timeOfOccurrence, 
				EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 			eventAsString () 
	{
		return "Fridge::SwitchOff" ;
	}
	
	
	//	must look for the order between events !!
	@Override
	public boolean 			hasPriorityOver (EventI e) 
	{
		return false;
	}
	
	@Override
	public void 			executeOn (AtomicModel model) 
	{
		assert model instanceof FridgeModel ; 
		((FridgeModel) model).setMode (FridgeModel.Mode.OFF);
	}

	



}
//-----------------------------------------------------------------------------














