package fr.smart_builders.simulator.models.controller;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.devs_simulation.models.AtomicModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;

//------------------------------------------------------------------------------------------------
/**
 * The class <code>ControllerModel</code> implements a simple Controller
 * component (model).
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Zaouzaou</>
 */
public class 								ControllerModel 
extends 									AtomicModel
{

	/** generated */
	private static final long 				serialVersionUID = 715523791682697959L;

	public static final String 				URI = "ControllerModel" ;
	
	public 									ControllerModel(
										String uri, 
										TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine) 
												throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
	}

	@Override
	public Vector<EventI> output() {
		return null;
	}

	@Override
	public Duration timeAdvance() {
		return null;
	}

	
}
//------------------------------------------------------------------------------------------------




























