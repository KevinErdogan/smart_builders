package fr.smart_buliders.simulator.plugins;

import fr.smart_builders.simulator.models.FridgeModel;
import fr.sorbonne_u.components.cyphy.plugins.devs.AtomicSimulatorPlugin;
import fr.sorbonne_u.devs_simulation.interfaces.ModelDescriptionI;

//------------------------------------------------------------------------------------------------------------------------


// don't forget to add the assertion in the method getModelStateValue

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */
public class 		FridgeSimulatorPlugin 
extends 			AtomicSimulatorPlugin

{

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = -2019319673227530963L;

	@Override
	public Object		getModelStateValue(String modelURI, String name)
	throws Exception
	{

		ModelDescriptionI m = this.simulator.getDescendentModel(modelURI) ;
	
		assert	m instanceof FridgeModel ;
		
		if (name.equals("state")) {
			return ((FridgeModel)m).getState() ;
		} else {
			assert	name.equals("intensity") ;
			return ((FridgeModel)m).getConsumption() ;
		}
	}

}
//------------------------------------------------------------------------------------------------------------------------




















