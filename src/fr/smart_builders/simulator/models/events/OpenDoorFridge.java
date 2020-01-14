package fr.smart_builders.simulator.models.events;

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------

/**
 * The class <code>OpenDoorFridge</code> describes the event that the fridge's
 * door is opened by the user
 * 
 * @authors <p>Yacine Zaouzaou, Kevin Erdogan</p>
 */
public class 						OpenDoorFridge 
extends 							AbstractFridgeEvent
{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -4252806763213976489L;
	
	public 							OpenDoorFridge (
						Time timeOfOccurence)
	{
		super (timeOfOccurence , null) ;
	}

	public 							OpenDoorFridge(
						Time timeOfOccurrence, 
						EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 					eventAsString () 
	{
		return "Frdige::OpenDoor" ; 
	}
	
	@Override
	public boolean 					hasPriorityOver (EventI e)
	{
		return false;
	}
	
	@Override
	public void 					executeOn (AtomicModel model) 
	{
		assert	model instanceof FridgeModel ; 
		((FridgeModel) model).setDoorValue (FridgeModel.DoorState.OPEN) ;  
	}
}











