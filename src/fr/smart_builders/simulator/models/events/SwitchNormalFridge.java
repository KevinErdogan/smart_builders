package fr.smart_builders.simulator.models.events;

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//----------------------------------------------------------------------


public class 				SwitchNormalFridge 
extends 					AbstractFridgeEvent		
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 2358882934463424875L;

	public 					SwitchNormalFridge(
					Time timeOfOccurrence, 
					EventInformationI content) 
	{
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 			eventAsString () 
	{
		return "Fridge::SwitchNormal" ;
	}

	@Override
	public boolean 			hasPriorityOver (EventI e)
	{
		// it has the last priority
		return false;
	}
	
	@Override
	public void 			executeOn (AtomicModel model) {
		assert model instanceof FridgeModel ;
		
		((FridgeModel) model).setMode(FridgeModel.Mode.NORMAL);
	}

}


//----------------------------------------------------------------------












