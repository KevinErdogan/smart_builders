package fr.smart_builders.simulator.models.counter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.devs_simulation.hioa.models.vars.StaticVariableDescriptor;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSink;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSource;
import fr.sorbonne_u.devs_simulation.interfaces.ModelDescriptionI;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.CoupledModel;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventSink;
import fr.sorbonne_u.devs_simulation.models.events.EventSource;
import fr.sorbonne_u.devs_simulation.models.events.ReexportedEvent;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardCoupledModelReport;

//--------------------------------------------------------------------------------------------------------
/**
 * The class <code>CounterCoupledModel</code>
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								CounterCoupledModel 
extends 									CoupledModel
{

	/** generated */
	private static final long 				serialVersionUID = -5875893853012001928L;
	
	public static final String 				URI = "CounterCoupledModel" ; 

	public									CounterCoupledModel(
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
			throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine, 
				submodels, imported, reexported, connections, 
				importedVars, reexportedVars, bindings);
	}
	
	public									CounterCoupledModel(
										String uri, 
										TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine,
										ModelDescriptionI[] submodels, 
										Map<Class<? extends EventI>, EventSink[]> imported,
										Map<Class<? extends EventI>, ReexportedEvent> reexported, 
										Map<EventSource, EventSink[]> connections
							)throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine, 
				submodels, imported, reexported, connections);
	}
	
	@Override
	public SimulationReportI				getFinalReport () 
									throws Exception 
	{
		StandardCoupledModelReport ret = 
				new StandardCoupledModelReport (this.getURI()) ; 
		for (int i = 0 ; i < this.submodels.length ; i ++) {
			ret.addReport(this.submodels[i].getFinalReport()) ;
		}
		return ret ; 
	}

	
}
//--------------------------------------------------------------------------------------------------------













































