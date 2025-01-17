package fr.smart_builders.simulator.models;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.devs_simulation.hioa.models.vars.StaticVariableDescriptor;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSink;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSource;
import fr.sorbonne_u.devs_simulation.interfaces.ModelDescriptionI;
import fr.sorbonne_u.devs_simulation.models.CoupledModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventSink;
import fr.sorbonne_u.devs_simulation.models.events.EventSource;
import fr.sorbonne_u.devs_simulation.models.events.ReexportedEvent;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;

//---------------------------------------------------------------------------------------------------
/**
 * The class <code>HouseModel</code> TODO comments
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								HouseModel 
extends 									CoupledModel
{

	/** generated */
	private static final long 				serialVersionUID = -6712916844758584336L ;
	
	public static final String 				URI = "HouseModel" ; 

	public 									HouseModel(
										String uri, 
										TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine,
										ModelDescriptionI[] submodels, 
										Map<Class<? extends EventI>, EventSink[]> imported,
										Map<Class<? extends EventI>, ReexportedEvent> reexported, 
										Map<EventSource, EventSink[]> connections
										)
							throws Exception 
	{
							super(uri, simulatedTimeUnit, simulationEngine, submodels, 
							imported, reexported, connections);
	}
	
	public 									HouseModel(
										String uri, 
										TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine,
										ModelDescriptionI[] submodels, 
										Map<Class<? extends EventI>, EventSink[]> imported,
										Map<Class<? extends EventI>, ReexportedEvent> reexported, 
										Map<EventSource, EventSink[]> connections,
										Map<StaticVariableDescriptor, VariableSink[]> importedVars,
										Map<VariableSource, StaticVariableDescriptor> reexportedVars, 
										Map<VariableSource, VariableSink[]> bindings)
			throws Exception {
		super(uri, simulatedTimeUnit, simulationEngine, submodels, 
				imported, reexported, connections, 
				importedVars, reexportedVars, bindings);
	}
	
	

}
