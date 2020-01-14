package fr.smart_builders.simulator.models.events;

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//------------------------------------------------------------------------------


/**
 * The class <code>CloseDoorFridge</code> describes the event that the frdige's
 * door is closed by the user
 * 
 * @authors <p>Yacine Zaouzaou, Kevin Erdogan</p>
 */
public class 					CloseDoorFridge 
extends 						AbstractFridgeEvent
{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -3836579910109519300L;


	public 						CloseDoorFridge (
						Time timeOfOccurence) 
	{
		super(timeOfOccurence , null) ; 
	}
	
	
	public 						CloseDoorFridge(
						Time timeOfOccurrence, 
						EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 				eventAsString ()
	{
		return "Fridge::CloseDoor" ; 
	}
	
	@Override 
	public boolean 				hasPriorityOver (EventI e) 
	{
		return false;
	}

	@Override
	public void 				executeOn (AtomicModel model) 
	{
		assert 	model instanceof FridgeModel ; 
		((FridgeModel) model).setDoorValue(FridgeModel.DoorState.CLOSE) ;
	}
}

















