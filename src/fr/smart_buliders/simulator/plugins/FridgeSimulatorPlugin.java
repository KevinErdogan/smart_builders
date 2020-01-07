package fr.smart_buliders.simulator.plugins;

import fr.sorbonne_u.components.cyphy.plugins.devs.AtomicSimulatorPlugin;
import fr.sorbonne_u.cyphy.examples.sg.equipments.hairdryer.models.HairDryerModel;
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
		// Get a Java reference on the object representing the corresponding
		// simulation model.
		ModelDescriptionI m = this.simulator.getDescendentModel(modelURI) ;
		// The only model in this example that provides access to some value
		// is the HairDryerModel.
//		assert	m instanceof FridgeModel ;
		// The following is the implementation of the protocol converting
		// names used by the caller to the values provided by the model;
		// alternatively, the simulation model could take care of the
		// link between names and values.
		if (name.equals("state")) {
			return ((HairDryerModel)m).getState() ;
		} else {
			assert	name.equals("intensity") ;
			return ((HairDryerModel)m).getIntensity() ;
		}
	}

}
//------------------------------------------------------------------------------------------------------------------------




















