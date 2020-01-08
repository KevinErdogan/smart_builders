package fr.smart_builders.simulator.models;
//---------------------------------------------------------------------------

import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.events.RunFridge;
import fr.smart_builders.simulator.models.events.StopFridge;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;

/**
 * The class <code>FridgeInnerTempController</code> implements a simple 
 * fridge controller that runs the fridge if its inner temperature is
 * too high and stop it when it's too low
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */


//	need to read the value of inner temperature of the fridge !!!
//	i think its better to include all this behavior in FridgeModel

@ModelExternalEvents (
					exported = {
							RunFridge.class, 
							StopFridge.class
					})
public class 					FridgeInnerTempController 
extends 						AtomicES_Model
{

	/**
	 * generated
	 */
	private static final long serialVersionUID = -6476690623475401970L;

	public 						FridgeInnerTempController(
						String uri, 
						TimeUnit simulatedTimeUnit, 
						SimulatorI simulationEngine)
								throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
	}

	
}
//---------------------------------------------------------------------------






