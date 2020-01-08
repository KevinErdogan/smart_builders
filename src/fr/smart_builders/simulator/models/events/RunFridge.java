package fr.smart_builders.simulator.models.events;
//---------------------------------------------------------------------------

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

/**
 * The class <code>RunFridge</code> describes the event that the fridge
 * starts running (if it is allowed to ) its cinsumtion goes to eco or
 * normal mode consumption value depending on its mode at the running moment 
 * the fridge start running when its inner temperature is high
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 				RunFridge 
extends 					AbstractFridgeEvent
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = -5563210781864349314L;

	public 					RunFridge(
			Time timeOfOccurrence, 
			EventInformationI content)
	{
		super(timeOfOccurrence, content);
	}
	
	
	@Override 
	public String 					eventAsString () 
	{
		return "Fridge::Run" ; 
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
		FridgeModel m = (FridgeModel) model;
		// I think it is tested in the fridge model specific methods !!
		// redondance ? 
		if (m.getMode() != FridgeModel.Mode.OFF) {
			m.setState(FridgeModel.State.RUN);
		}
	}

}
//---------------------------------------------------------------------------










