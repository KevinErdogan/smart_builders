package fr.smart_builders.simulator.models.tv;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.events.tv.SwitchOffTv;
import fr.smart_builders.simulator.models.events.tv.SwitchOnTv;
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

//-----------------------------------------------------------------------------------------------------

/**
 * The class <Code>TvCoupledModel</code> implements the DEVS simulation coupled model 
 * for the TV
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 								TvCoupledModel 
extends 									CoupledModel
{
	/** generated  */
	private static final long 				serialVersionUID = -6580257128959664851L;
	
	public static final String 				URI = "TvCoupledModel" ; 

	public 									TvCoupledModel(
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
		super(uri, simulatedTimeUnit, simulationEngine, 
				submodels, imported, reexported, connections, 
				importedVars, reexportedVars, bindings);
	}

	//---------------------------------------------------------------------------------------
	// method
	//---------------------------------------------------------------------------------------
	
	@Override
	public SimulationReportI				getFinalReport () 
	throws 		Exception
	{
		System.err.println("mmmmmeeeeerrrrrdddddeeeee");
		StandardCoupledModelReport ret = 
				new StandardCoupledModelReport (this.getURI()) ; 
		for (int i = 0 ; i < this.submodels.length ; i ++ ) {
			ret.addReport(this.submodels[i].getFinalReport());
		}
		return ret ; 
	}
	
	

	public static Architecture				build () 
	throws 		Exception
	{
		Map<String , AbstractAtomicModelDescriptor> amd = new HashMap<> () ; 
		
		amd.put( TvModel.URI, AtomicHIOA_Descriptor.create(
										TvModel.class, 
										TvModel.URI, 
										TimeUnit.SECONDS,
										null, 
										SimulationEngineCreationMode.ATOMIC_ENGINE)
				);
		
		amd.put( TvUserModel.URI, AtomicModelDescriptor.create(
										TvUserModel.class, 
										TvUserModel.URI, 
										TimeUnit.SECONDS,
										null, 
										SimulationEngineCreationMode.ATOMIC_ENGINE)
				);
		
		Map<String , CoupledModelDescriptor> cmd = new HashMap<> () ; 
		
		Set <String> sm = new HashSet <> () ;
		sm.add(TvModel.URI) ; 
		sm.add(TvUserModel.URI) ; 
		

		Map<EventSource , EventSink[] > connections = new HashMap<> () ; 

		EventSource f1 = new EventSource (
								TvUserModel.URI, 
								SwitchOnTv.class ) ; 
		EventSink [] t1 = new EventSink[] {
								new EventSink (TvModel.URI , 
												SwitchOnTv.class)
		};

		connections.put(f1, t1) ; 
		
		EventSource f2 = new EventSource (
				TvUserModel.URI, 
				SwitchOffTv.class ) ; 
		EventSink [] t2 = new EventSink[] {
						new EventSink (TvModel.URI , 
										SwitchOffTv.class)
		};
		
		connections.put(f2, t2) ; 		
		
		// their is no bindings for varibales because their is no shared variables
		
		cmd.put(		TvCoupledModel.URI, 
						new CoupledHIOA_Descriptor(
											TvCoupledModel.class , 
											TvCoupledModel.URI , 
											sm , 
											null , 
											null , 
											connections , 
											null , 
											SimulationEngineCreationMode.COORDINATION_ENGINE , 
											null , 
											null , 
											null
				));
		
		
		return new Architecture(	TvCoupledModel.URI, 
									amd, 
									cmd, 
									TimeUnit.SECONDS) ;
	}

}
//-----------------------------------------------------------------------------------------------------
































