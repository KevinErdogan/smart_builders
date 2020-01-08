package fr.smart_builders.simulator.models.events;

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventInformationI;
import fr.sorbonne_u.devs_simulation.models.time.Time;

//-----------------------------------------------------------------------



/**
 * The class <code>SwitchEcoFridge</code> defines the event of the fridge
 * mode set to eco to consume less.
 * 
 * @authors  <p>Yacine Zaouzaou , Kevin Erdogan</p>
 *
 */
public class 				SwitchEcoFridge 
extends 					AbstractFridgeEvent
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 7117549292956312324L;

	
	public SwitchEcoFridge(Time timeOfOccurrence, EventInformationI content) {
		super(timeOfOccurrence, content);
	}
	
	@Override
	public String 					eventAsString ()
	{
		return "Fridge::SwitchEco";
	}
	
	@Override
	public boolean 					hasPriorityOver (EventI e) 
	{
		if (e instanceof SwitchNormalFridge ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void 					executeOn (AtomicModel model)
	{
		assert 	model instanceof FridgeModel;
		
		FridgeModel m = (FridgeModel) model;
		m.setMode(FridgeModel.Mode.ECO);
	}


}
//-----------------------------------------------------------------------






