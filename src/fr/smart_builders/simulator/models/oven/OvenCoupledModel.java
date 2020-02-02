package fr.smart_builders.simulator.models.oven;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import fr.smart_builders.simulator.models.events.oven.RunOven;
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
 * The class <code>OvenCoupledModel</code> implements the DEVS simulation
 * coupled model for the oven
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 						OvenCoupledModel 
extends 							CoupledModel
{
	
	//--------------------------------------------------------------------------------------
	// Constant and variables
	//--------------------------------------------------------------------------------------
	/**
	 * 	generated
	 */
	private static final long serialVersionUID = -8374678917667405576L;
	
	public static final String 		URI = "OvenCoupledModel" ; 
	
	
	//--------------------------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------------------------	
	public 							OvenCoupledModel(
								String uri, 
								TimeUnit simulatedTimeUnit, 
								SimulatorI simulationEngine,
								ModelDescriptionI[] submodels, 
								Map<Class<? extends EventI>,EventSink[]> imported,
								Map<Class<? extends EventI>, ReexportedEvent> reexported, 
								Map<EventSource, EventSink[]> connections,
								Map<StaticVariableDescriptor,VariableSink[]> importedVars,
								Map<VariableSource,StaticVariableDescriptor> reexportedVars, 
								Map<VariableSource, VariableSink[]> bindings
			)throws Exception 
	{
		super(uri, simulatedTimeUnit, 
				simulationEngine,submodels, imported, 
				reexported, connections, importedVars,
				reexportedVars, bindings);
	}	
	
	//---------------------------------------------------------------------------------------
	// Override method
	//---------------------------------------------------------------------------------------
	
	@Override
	public SimulationReportI		getFinalReport ()
								throws Exception
	{
		StandardCoupledModelReport ret = 
				new StandardCoupledModelReport(this.getURI()) ; 
		for (int i = 0 ; i < this.submodels.length ; i ++) {
			ret.addReport(this.submodels[i].getFinalReport()) ;
		}
		return ret ; 
	}
	
	public static Architecture 		build () 
	throws Exception 
	{
		Map<String , AbstractAtomicModelDescriptor> amd = new HashMap<> () ; 
		amd.put(	OvenModel.URI, AtomicHIOA_Descriptor.create(
											OvenModel.class, 
											OvenModel.URI, 
											TimeUnit.SECONDS, 
											null, 
											SimulationEngineCreationMode.ATOMIC_ENGINE)
											) ;
		amd.put(	OvenUserModel.URI, AtomicModelDescriptor.create(
											OvenUserModel.class, 
											OvenUserModel.URI, 
											TimeUnit.SECONDS, 
											null, 
											SimulationEngineCreationMode.ATOMIC_ENGINE)
					); 
		// no bindings because their is no exported or imported variables
//		Map<VariableSource , VariableSink []> bindings = new HashMap<>() ;
		
		Map<String , CoupledModelDescriptor> cmd = new HashMap<> () ; 
		
		Set <String> sm = new HashSet <String> () ; 
		sm.add(OvenModel.URI) ; 
		sm.add(OvenUserModel.URI) ; 
		
		Map<EventSource , EventSink[]> connections = new HashMap<> () ;
		
		// connecting RunOven event 
		// i will use only this one for the moment 
		EventSource f1 = new EventSource (
								OvenUserModel.URI, 
								RunOven.class ) ; 
		EventSink [] t1 = new EventSink [] {
								new EventSink (
										OvenModel.URI , 
										RunOven.class ) 
		} ;
		
		connections.put(f1, t1) ; 
		cmd.put (			OvenCoupledModel.URI , 
							new CoupledHIOA_Descriptor(
									OvenCoupledModel.class, 
									OvenCoupledModel.URI, 
									sm, 
									null, 
									null, 
									connections, 
									null, 
									SimulationEngineCreationMode.COORDINATION_ENGINE, 
									null, 
									null, 
									null));
		return new Architecture(	OvenCoupledModel.URI , 
									amd , 
									cmd , 
									TimeUnit.SECONDS) ; 
	}

	
	//---------------------------------------------------------------------------------------
	// method
	//---------------------------------------------------------------------------------------
	
	
}
//----------------------------------------------------------------------------------------------------------













































