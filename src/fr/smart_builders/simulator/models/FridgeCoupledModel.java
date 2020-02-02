package fr.smart_builders.simulator.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.events.CloseDoorFridge;
import fr.smart_builders.simulator.models.events.OpenDoorFridge;
import fr.smart_builders.simulator.models.events.RunFridge;
import fr.smart_builders.simulator.models.events.StopFridge;
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

//--------------------------------------------------------------------


/**
 * 
 * The class <code>FridgeCoupledModel</code> implements the DEVS 
 * simulation coupled model for the fridge 
 * 
 * @authors <p> Yacine Zaouzaou, Kevin Erdogan </p>
 */

public class 				FridgeCoupledModel 
extends 					CoupledModel
{

	/**
	 * generated
	 */
	private static final long 			serialVersionUID = -7056095415196857954L;

	//-----------------------------------------------------------------
	//	Constants and variables
	//----------------------------------------------------------------

	public static final String 			URI = "FridgeCoupledModel" ;
	
	
	//-----------------------------------------------------------------
	//	Constructors 
	//----------------------------------------------------------------
	public 					FridgeCoupledModel(
			String uri, 
			TimeUnit simulatedTimeUnit, 
			SimulatorI simulationEngine,
			ModelDescriptionI[] submodels, 
			Map<Class<? extends EventI>,EventSink[]> imported,
			Map<Class<? extends EventI>,ReexportedEvent> reexported, 
			Map<EventSource, EventSink[]> connections , 
			Map<StaticVariableDescriptor, VariableSink[]> importedVars,
			Map<VariableSource, StaticVariableDescriptor> reexportedVars,
			Map<VariableSource, VariableSink[]> bindings
		)throws Exception 
	{
		super(uri, simulatedTimeUnit, 
				simulationEngine, submodels, imported, 
				reexported, connections , importedVars ,
				reexportedVars , bindings);
	}
	
	
	//-----------------------------------------------------------------
	//	Methods
	//-----------------------------------------------------------------
	
	@Override
	public SimulationReportI			getFinalReport ()
								throws Exception
	{
		StandardCoupledModelReport ret =
				new StandardCoupledModelReport(this.getURI()) ;
		for (int i = 0 ; i < this.submodels.length ; i++) {
			ret.addReport(this.submodels[i].getFinalReport()) ;
		}
		return ret ;
	}
	
	public static Architecture 			build () 
								throws	Exception 
	{
		Map<String, AbstractAtomicModelDescriptor> amd =
												new HashMap<> () ; 
		amd.put (	FridgeModel.URI , 
					AtomicHIOA_Descriptor.create(	
							FridgeModel.class, 
							FridgeModel.URI, 
							TimeUnit.SECONDS,
							null, 
							SimulationEngineCreationMode.ATOMIC_ENGINE)
					) ;
		amd.put(	FridgeInnerTempController.URI, 
					AtomicHIOA_Descriptor.create(
							FridgeInnerTempController.class, 
							FridgeInnerTempController.URI, 
							TimeUnit.SECONDS, 
							null, 
							SimulationEngineCreationMode.ATOMIC_ENGINE)
					) ;
		
		amd.put(	FridgeUserModel.URI, 
					AtomicModelDescriptor.create(
							FridgeUserModel.class, 
							FridgeUserModel.URI, 
							TimeUnit.SECONDS, 
							null, 
							SimulationEngineCreationMode.ATOMIC_ENGINE)
				) ; 
		
		Map<VariableSource , VariableSink []> bindings = new HashMap<>();
		
		VariableSource vs1 =
				new VariableSource("fridgeInnerTemp",
								   Double.class,
								   FridgeModel.URI) ;
		VariableSink[] vd1 =
			new VariableSink[] {
					new VariableSink("fridgeInnerTemp",
									 Double.class,
									 FridgeInnerTempController.URI)} ;
		bindings.put(vs1, vd1) ;
		
		
		Map<String , CoupledModelDescriptor> cmd = 
				new HashMap<> () ; 
		Set <String> sm = new HashSet<String> () ; 
		sm.add(FridgeModel.URI) ;
		sm.add(FridgeInnerTempController.URI) ; 
		sm.add(FridgeUserModel.URI) ; 
		
		Map<EventSource , EventSink[]> connections =
			new HashMap<> () ; 
		
		EventSource f1 = 
				new EventSource (
						FridgeInnerTempController.URI , 
						RunFridge.class	) ; 
		EventSink [] t1 = 
				new EventSink [] {
						new EventSink (
								FridgeModel.URI , 
								RunFridge.class )
				};
		
		connections.put(f1 , t1) ;
		EventSource f2 = 
				new EventSource (
						FridgeInnerTempController.URI , 
						StopFridge.class	) ; 
		EventSink [] t2 = 
				new EventSink [] {
						new EventSink (
								FridgeModel.URI , 
								StopFridge.class )
				};
		
		connections.put(f2 , t2) ;
		
		EventSource f3 = 
				new EventSource (
						FridgeUserModel.URI , 
						CloseDoorFridge.class
						) ; 
		
		EventSink [] t3 = 
				new EventSink [] {
						new EventSink (
								FridgeModel.URI , 
								CloseDoorFridge.class)
				};
		
		connections.put(f3, t3) ; 
		
		EventSource f4 = 
				new EventSource (
						FridgeUserModel.URI , 
						OpenDoorFridge.class
						) ; 
		
		EventSink [] t4 = 
				new EventSink [] {
						new EventSink (
								FridgeModel.URI , 
								OpenDoorFridge.class)
				};
		
		connections.put(f4, t4) ; 
		
		
		cmd.put	(		FridgeCoupledModel.URI, 
						new CoupledHIOA_Descriptor(
								FridgeCoupledModel.class, 
								FridgeCoupledModel.URI, 
								sm, 
								null, 
								null, 
								connections, 
								null, 
								SimulationEngineCreationMode.COORDINATION_ENGINE, 
								null, 
								null, 
								bindings)
						) ;
		return new Architecture(
				FridgeCoupledModel.URI, 
				amd, 
				cmd, 
				TimeUnit.SECONDS) ; 
	}
	
}
//--------------------------------------------------------------------





















