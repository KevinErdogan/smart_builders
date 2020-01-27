package fr.smart_builders.simulator.models.battery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import fr.smart_builders.simulator.models.events.battery.ChargeBatteryEvent;
import fr.smart_builders.simulator.models.events.battery.DischargeBatteryEvent;
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
 * The class <code>BatteryCoupledModel</code> implements the DEVS simulation 
 * coupled model for the battery
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								BatteryCoupledModel 
extends 									CoupledModel
{
	
	//--------------------------------------------------------------------------------------
	// Constant and variables
	//--------------------------------------------------------------------------------------
	
	/** generated */
	private static final long 				serialVersionUID = 9106771675092835402L;
	
	public static final	String	 			URI = "batteryCoupledModel" ;
	
	//--------------------------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------------------------
	public 									BatteryCoupledModel(
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
		super(uri, simulatedTimeUnit, 
				simulationEngine, submodels, imported, 
				reexported, connections, importedVars,
				reexportedVars, bindings);
	}
	
	//--------------------------------------------------------------------------------------
	// method
	//--------------------------------------------------------------------------------------
	
	@Override
	public SimulationReportI				getFinalReport ()
									throws Exception
	{
		StandardCoupledModelReport ret = 
				new StandardCoupledModelReport (this.getURI()) ; 
		for (int i = 0 ; i < this.submodels.length ; i ++ ) {
			ret.addReport(this.submodels[i].getFinalReport());
		}
		return ret ;
	}
	
	public static Architecture				build ()
	throws Exception
	{
		Map<String , AbstractAtomicModelDescriptor> amd = new HashMap<> () ; 
		amd.put(	BatteryModel.URI, AtomicHIOA_Descriptor.create(
												BatteryModel.class, 
												BatteryModel.URI, 
												TimeUnit.SECONDS, 
												null, 
												SimulationEngineCreationMode.ATOMIC_ENGINE)
												) ;

		amd.put(	BatteryUserModel.URI, AtomicModelDescriptor.create(
				BatteryUserModel.class, 
				BatteryUserModel.URI, 
				TimeUnit.SECONDS, 
				null, 
				SimulationEngineCreationMode.ATOMIC_ENGINE)
				) ;
		
		Map<String , CoupledModelDescriptor> cmd = new HashMap<> () ; 
		
		Set <String> sm = new HashSet <String> () ; 
		sm.add(BatteryModel.URI) ; 
		sm.add(BatteryUserModel.URI); 
		
		Map<EventSource , EventSink[]> connections = new HashMap<> () ;
		
		EventSource f1 = new EventSource (
							BatteryUserModel.URI , 
							ChargeBatteryEvent.class) ; 
		EventSink [] t1 = new EventSink [] {
							new EventSink (
									BatteryModel.URI , 
									ChargeBatteryEvent.class ) 
		}	;
		
		connections.put(f1, t1) ; 
		
		EventSource f2 = new EventSource (
							BatteryUserModel.URI , 
							DischargeBatteryEvent.class) ; 
		EventSink [] t2 = new EventSink [] {
							new EventSink (
								BatteryModel.URI , 
								DischargeBatteryEvent.class ) 
		}	;
		connections.put(f2, t2) ; 
		
		System.err.println(sm.size());
		
		
		assert	sm != null && sm.size() > 1 ;
		
		cmd.put ( 		BatteryCoupledModel.URI , 
						new CoupledHIOA_Descriptor (
							BatteryCoupledModel.class, 
							BatteryCoupledModel.URI, 
							sm , 
							null , 
							null , 
							connections , 
							null , 
							SimulationEngineCreationMode.COORDINATION_ENGINE , 
							null , 
							null , 
							null)) ; 
		
		return new Architecture (	BatteryCoupledModel.URI , 
									amd , 
									cmd, 
									TimeUnit.SECONDS) ; 
	}
	
}
//----------------------------------------------------------------------------------------------------------

















































