package fr.smart_builders.simulator.models.solarpanel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.events.solarpanel.SolarBrightnessChanged;
import fr.sorbonne_u.devs_simulation.architectures.Architecture;
import fr.sorbonne_u.devs_simulation.architectures.SimulationEngineCreationMode;
import fr.sorbonne_u.devs_simulation.hioa.architectures.AtomicHIOA_Descriptor;
import fr.sorbonne_u.devs_simulation.hioa.architectures.CoupledHIOA_Descriptor;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.StaticVariableDescriptor;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSink;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSource;
import fr.sorbonne_u.devs_simulation.interfaces.ModelDescriptionI;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.CoupledModel;
import fr.sorbonne_u.devs_simulation.models.architectures.AbstractAtomicModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.AtomicModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.CoupledModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventSink;
import fr.sorbonne_u.devs_simulation.models.events.EventSource;
import fr.sorbonne_u.devs_simulation.models.events.ReexportedEvent;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardCoupledModelReport;

//----------------------------------------------------------------------------------------------------------
/**
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								SolarPanelCoupledModel 
extends 									CoupledModel
{
	//--------------------------------------------------------------------------------------
	// Constants and variables
	//--------------------------------------------------------------------------------------	
	/** generated */
	private static final long    			serialVersionUID = -3966663387990849630L;

	public static final String 				URI = "SolarPanelCoupledModel" ; 
	
	//--------------------------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------------------------	
	public 									SolarPanelCoupledModel(
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
	//--------------------------------------------------------------------------------------
	// method
	//--------------------------------------------------------------------------------------	

	@Override
	public SimulationReportI				getFinalReport () 
										throws Exception
	{
		StandardCoupledModelReport ret = 
				new StandardCoupledModelReport (this.getURI() ) ; 
		for (int i = 0 ; i < this.submodels.length ; i ++ ) {
			ret.addReport(this.submodels[i].getFinalReport());
		}
		return ret ; 
	}
	
	
	public static Architecture 				build ()
	throws 			Exception
	{
		Map<String , AbstractAtomicModelDescriptor> amd = new HashMap<> () ; 
		
		amd.put(		SolarPanelModel.URI, 
						AtomicHIOA_Descriptor.create(	
								SolarPanelModel.class, 
								SolarPanelModel.URI, 
								TimeUnit.SECONDS, 
								null, 
								SimulationEngineCreationMode.ATOMIC_ENGINE)) ;

		
		amd.put(		EnvironmentModel.URI, 
						AtomicModelDescriptor.create(
								EnvironmentModel.class,
								EnvironmentModel.URI, 
								TimeUnit.SECONDS, 
								null, 
								SimulationEngineCreationMode.ATOMIC_ENGINE)) ;
		
		Map<String , CoupledModelDescriptor> cmd = new HashMap<> () ; 
		
		Set <String> sm = new HashSet <String> () ; 
		sm.add(SolarPanelModel.URI) ; 
		sm.add(EnvironmentModel.URI) ; 
		
		Map<EventSource , EventSink[]> connections = new HashMap<> () ;
		
		EventSource f1 = new EventSource (
								EnvironmentModel.URI , 
								SolarBrightnessChanged.class) ;
		
		EventSink [] t1 = new EventSink [] {
								new EventSink(
										SolarPanelModel.URI, 
										SolarBrightnessChanged.class)  
								} ;  
		
		connections.put(f1, t1) ; 
		
		cmd.put(		SolarPanelCoupledModel.URI, 
						new CoupledHIOA_Descriptor(
								SolarPanelCoupledModel.class, 
								SolarPanelCoupledModel.URI, 
								sm, 
								null, 
								null, 
								connections, 
								null, 
								SimulationEngineCreationMode.COORDINATION_ENGINE, 
								null, null, null)) ; 
		
		return new Architecture(			SolarPanelCoupledModel.URI, 
											amd, 
											cmd, 
											TimeUnit.SECONDS) ;
	}

}
//----------------------------------------------------------------------------------------------------------







































