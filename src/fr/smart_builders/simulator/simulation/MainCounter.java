package fr.smart_builders.simulator.simulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import fr.smart_builders.simulator.models.FridgeCoupledModel;
import fr.smart_builders.simulator.models.FridgeInnerTempController;
import fr.smart_builders.simulator.models.FridgeModel;
import fr.smart_builders.simulator.models.FridgeUserModel;
import fr.smart_builders.simulator.models.HouseModel;
import fr.smart_builders.simulator.models.battery.BatteryCoupledModel;
import fr.smart_builders.simulator.models.battery.BatteryModel;
import fr.smart_builders.simulator.models.battery.BatteryUserModel;
import fr.smart_builders.simulator.models.counter.CounterModel;
import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.smart_builders.simulator.models.events.CloseDoorFridge;
import fr.smart_builders.simulator.models.events.OpenDoorFridge;
import fr.smart_builders.simulator.models.events.RunFridge;
import fr.smart_builders.simulator.models.events.StopFridge;
import fr.smart_builders.simulator.models.events.battery.ChargeBatteryEvent;
import fr.smart_builders.simulator.models.events.battery.DischargeBatteryEvent;
import fr.smart_builders.simulator.models.events.oven.RunOven;
import fr.smart_builders.simulator.models.events.tv.SwitchOffTv;
import fr.smart_builders.simulator.models.events.tv.SwitchOnTv;
import fr.smart_builders.simulator.models.oven.OvenCoupledModel;
import fr.smart_builders.simulator.models.oven.OvenModel;
import fr.smart_builders.simulator.models.oven.OvenUserModel;
import fr.smart_builders.simulator.models.tv.TvCoupledModel;
import fr.smart_builders.simulator.models.tv.TvModel;
import fr.smart_builders.simulator.models.tv.TvUserModel;
import fr.sorbonne_u.devs_simulation.architectures.Architecture;
import fr.sorbonne_u.devs_simulation.architectures.ArchitectureI;
import fr.sorbonne_u.devs_simulation.architectures.SimulationEngineCreationMode;
import fr.sorbonne_u.devs_simulation.hioa.architectures.AtomicHIOA_Descriptor;
import fr.sorbonne_u.devs_simulation.hioa.architectures.CoupledHIOA_Descriptor;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSink;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.VariableSource;
import fr.sorbonne_u.devs_simulation.models.architectures.AbstractAtomicModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.AtomicModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.CoupledModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.events.EventSink;
import fr.sorbonne_u.devs_simulation.models.events.EventSource;
import fr.sorbonne_u.devs_simulation.models.events.ReexportedEvent;
import fr.sorbonne_u.devs_simulation.simulators.SimulationEngine;
import fr.sorbonne_u.utils.PlotterDescription;

//---------------------------------------------------------------------------------------------
/**
 * The class <code>MainCounter</code> constructs a simple simulation architecture 
 * for (TV , Fridge, Oven, Battery ) running with simple user simulation and a Counter
 * making the sum of their consumption 
 * 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 							MainCounter 
{
	
	public static void main (String ... args)
	{
		TimeUnit tu = TimeUnit.SECONDS ; 
		try {
			Map<String , AbstractAtomicModelDescriptor> amd = new HashMap<> () ; 
			Map<String , CoupledModelDescriptor> cmd = new HashMap<> () ; 
			//---------------------------------------
			// TV model
			//--------------------------------------
			Set<String> submodelsTv = new HashSet<> () ;
			Map<EventSource , EventSink[]> connectionsTv = new HashMap<>() ;
			
			amd.put (	TvModel.URI , AtomicHIOA_Descriptor.create(
														TvModel.class, 
														TvModel.URI, 
														tu, 
														null , 
														SimulationEngineCreationMode.ATOMIC_ENGINE)) ;
			
			amd.put( TvUserModel.URI, AtomicModelDescriptor.create(
					TvUserModel.class, 
					TvUserModel.URI, 
					tu,
					null, 
					SimulationEngineCreationMode.ATOMIC_ENGINE)) ;
			
			submodelsTv.add(TvModel.URI) ; 
			submodelsTv.add(TvUserModel.URI) ;  
		
			EventSource f1tv = new EventSource (
					TvUserModel.URI, 
					SwitchOnTv.class ) ; 
			EventSink [] t1tv = new EventSink[] {
								new EventSink (TvModel.URI , 
												SwitchOnTv.class)
			};
			
			connectionsTv.put(f1tv, t1tv) ; 
			
			EventSource f2tv = new EventSource (
				TvUserModel.URI, 
				SwitchOffTv.class ) ; 
			EventSink [] t2tv = new EventSink[] {
						new EventSink (TvModel.URI , 
										SwitchOffTv.class)
			};
			
			connectionsTv.put(f2tv, t2tv) ; 	
			
			Map<Class<? extends EventI>,EventSink[]> importedTv =
					new HashMap<Class<? extends EventI>,EventSink[]>() ;

			Map<Class<? extends EventI>,ReexportedEvent> reexportedTv =
					new HashMap<Class<? extends EventI>,ReexportedEvent>() ;
			
			reexportedTv.put(
						ConsumptionResponseEvent.class, 
						new ReexportedEvent(	TvModel.URI, 
												ConsumptionResponseEvent.class)) ;
			
			importedTv.put(
						ConsumeEvent.class, 
						new EventSink[] {
							new EventSink(	TvModel.URI , 
											ConsumeEvent.class)  	
						}) ;
			
			cmd.put(
					TvCoupledModel.URI, 
					new CoupledHIOA_Descriptor(
							TvCoupledModel.class, 
							TvCoupledModel.URI, 
							submodelsTv, 
							importedTv, 
							reexportedTv, 
							connectionsTv, 
							null, 
							SimulationEngineCreationMode.COORDINATION_ENGINE, 
							null, null, null)) ;
			
			//---------------------------------------
			// Fridge model
			//--------------------------------------
			Set<String> submodelsFridge = new HashSet<String>() ; 
			Map<EventSource , EventSink[]> connectionsFridge = new HashMap<>() ;
			
			
			amd.put (	FridgeModel.URI , 
					AtomicHIOA_Descriptor.create(	
							FridgeModel.class, 
							FridgeModel.URI, 
							tu,
							null, 
							SimulationEngineCreationMode.ATOMIC_ENGINE)
					) ;
			amd.put(	FridgeInnerTempController.URI, 
						AtomicHIOA_Descriptor.create(
								FridgeInnerTempController.class, 
								FridgeInnerTempController.URI, 
								tu, 
								null, 
								SimulationEngineCreationMode.ATOMIC_ENGINE)
						) ;
			
			amd.put(	FridgeUserModel.URI, 
					AtomicModelDescriptor.create(
							FridgeUserModel.class, 
							FridgeUserModel.URI, 
							tu, 
							null, 
							SimulationEngineCreationMode.ATOMIC_ENGINE)
				) ; 
			
			Map<VariableSource , VariableSink []> bindingsFridge = new HashMap<>();
			
			VariableSource vs1Fridge =
					new VariableSource("fridgeInnerTemp",
									   Double.class,
									   FridgeModel.URI) ;
			VariableSink[] vd1Fridge =
				new VariableSink[] {
						new VariableSink("fridgeInnerTemp",
										 Double.class,
										 FridgeInnerTempController.URI)} ;
			bindingsFridge.put(vs1Fridge, vd1Fridge) ;
			
			submodelsFridge.add(FridgeModel.URI) ; 
			submodelsFridge.add(FridgeInnerTempController.URI) ; 
			submodelsFridge.add(FridgeUserModel.URI) ;  
			
			EventSource f1Fridge = 
					new EventSource (
							FridgeInnerTempController.URI , 
							RunFridge.class	) ; 
			EventSink [] t1Fridge = 
					new EventSink [] {
							new EventSink (
									FridgeModel.URI , 
									RunFridge.class )
					};
			
			connectionsFridge.put(f1Fridge , t1Fridge) ;
			EventSource f2Fridge = 
					new EventSource (
							FridgeInnerTempController.URI , 
							StopFridge.class	) ; 
			EventSink [] t2Fridge = 
					new EventSink [] {
							new EventSink (
									FridgeModel.URI , 
									StopFridge.class )
					};
			
			connectionsFridge.put(f2Fridge , t2Fridge) ;
			
			EventSource f3Fridge = 
					new EventSource (
							FridgeUserModel.URI , 
							CloseDoorFridge.class
							) ; 
			
			EventSink [] t3Fridge = 
					new EventSink [] {
							new EventSink (
									FridgeModel.URI , 
									CloseDoorFridge.class)
					};
			
			connectionsFridge.put(f3Fridge, t3Fridge) ; 
			
			EventSource f4Fridge = 
					new EventSource (
							FridgeUserModel.URI , 
							OpenDoorFridge.class
							) ; 
			
			EventSink [] t4Fridge = 
					new EventSink [] {
							new EventSink (
									FridgeModel.URI , 
									OpenDoorFridge.class)
					};
			
			connectionsFridge.put(f4Fridge, t4Fridge) ; 
			
			Map<Class<? extends EventI>,EventSink[]> importedFridge =
					new HashMap<Class<? extends EventI>,EventSink[]>() ;

			Map<Class<? extends EventI>,ReexportedEvent> reexportedFridge =
					new HashMap<Class<? extends EventI>,ReexportedEvent>() ;
			
			reexportedFridge.put(
						ConsumptionResponseEvent.class, 
						new ReexportedEvent(	FridgeModel.URI, 
												ConsumptionResponseEvent.class)) ;
			
			importedFridge.put(
						ConsumeEvent.class, 
						new EventSink[] {
							new EventSink(	FridgeModel.URI , 
											ConsumeEvent.class)  	
						}) ;
			
			cmd.put(	FridgeCoupledModel.URI, 
						new CoupledHIOA_Descriptor(
								FridgeCoupledModel.class, 
								FridgeCoupledModel.URI, 
								submodelsFridge, 
								importedFridge, 
								reexportedFridge, 
								connectionsFridge, 
								null, 
								SimulationEngineCreationMode.COORDINATION_ENGINE, 
								null, null, 
								bindingsFridge)
					) ;
			//---------------------------------------
			// Oven model
			//--------------------------------------
			amd.put(	OvenModel.URI, AtomicHIOA_Descriptor.create(
					OvenModel.class, 
					OvenModel.URI, 
					tu, 
					null, 
					SimulationEngineCreationMode.ATOMIC_ENGINE)
					) ;
			amd.put(	OvenUserModel.URI, AtomicModelDescriptor.create(
					OvenUserModel.class, 
					OvenUserModel.URI, 
					tu, 
					null, 
					SimulationEngineCreationMode.ATOMIC_ENGINE)
					); 
			Set <String> submodelsOven = new HashSet <String> () ; 
			submodelsOven.add(OvenModel.URI) ; 
			submodelsOven.add(OvenUserModel.URI) ; 
			
			Map<EventSource , EventSink[]> connectionsOven = new HashMap<> () ;
			
			// connecting RunOven event 
			// i will use only this one for the moment 
			EventSource f1Oven = new EventSource (
									OvenUserModel.URI, 
									RunOven.class ) ; 
			EventSink [] t1Oven = new EventSink [] {
									new EventSink (
											OvenModel.URI , 
											RunOven.class ) 
			} ;
			
			connectionsOven.put(f1Oven, t1Oven) ; 
			
			
			Map<Class<? extends EventI>,EventSink[]> importedOven =
					new HashMap<Class<? extends EventI>,EventSink[]>() ;

			Map<Class<? extends EventI>,ReexportedEvent> reexportedOven =
					new HashMap<Class<? extends EventI>,ReexportedEvent>() ;
			
			reexportedOven.put(
						ConsumptionResponseEvent.class, 
						new ReexportedEvent(	OvenModel.URI, 
												ConsumptionResponseEvent.class)) ;
			
			importedOven.put(
						ConsumeEvent.class, 
						new EventSink[] {
							new EventSink(	OvenModel.URI , 
											ConsumeEvent.class)  	
						}) ;
			
			cmd.put (			OvenCoupledModel.URI , 
								new CoupledHIOA_Descriptor(
										OvenCoupledModel.class, 
										OvenCoupledModel.URI, 
										submodelsOven, 
										importedOven, 
										reexportedOven, 
										connectionsOven, 
										null, 
										SimulationEngineCreationMode.COORDINATION_ENGINE, 
										null, 
										null, 
										null));
			//---------------------------------------
			// Battery model
			//--------------------------------------
			amd.put(	BatteryModel.URI, AtomicHIOA_Descriptor.create(
					BatteryModel.class, 
					BatteryModel.URI, 
					tu, 
					null, 
					SimulationEngineCreationMode.ATOMIC_ENGINE)
					) ;

			amd.put(	BatteryUserModel.URI, AtomicModelDescriptor.create(
					BatteryUserModel.class, 
					BatteryUserModel.URI, 
					tu, 
					null, 
					SimulationEngineCreationMode.ATOMIC_ENGINE)
					) ;
			Set <String> submodelsBattery = new HashSet <String> () ; 
			submodelsBattery.add(BatteryModel.URI) ; 
			submodelsBattery.add(BatteryUserModel.URI); 
			
			Map<EventSource , EventSink[]> connectionsBattery = new HashMap<> () ;
			
			EventSource f1Battery = new EventSource (
								BatteryUserModel.URI , 
								ChargeBatteryEvent.class) ; 
			EventSink [] t1Battery= new EventSink [] {
								new EventSink (
										BatteryModel.URI , 
										ChargeBatteryEvent.class ) 
			}	;
			
			connectionsBattery.put(f1Battery, t1Battery) ; 
			
			EventSource f2Battery = new EventSource (
								BatteryUserModel.URI , 
								DischargeBatteryEvent.class) ; 
			EventSink [] t2Battery = new EventSink [] {
								new EventSink (
									BatteryModel.URI , 
									DischargeBatteryEvent.class ) 
			} ;
			
			connectionsBattery.put(f2Battery, t2Battery) ; 
			
			Map<Class<? extends EventI>,EventSink[]> importedBattery =
					new HashMap<Class<? extends EventI>,EventSink[]>() ;

			Map<Class<? extends EventI>,ReexportedEvent> reexportedBattery =
					new HashMap<Class<? extends EventI>,ReexportedEvent>() ;
			
			reexportedBattery.put(
						ConsumptionResponseEvent.class, 
						new ReexportedEvent(	BatteryModel.URI, 
												ConsumptionResponseEvent.class)) ;
			
			importedBattery.put(
						ConsumeEvent.class, 
						new EventSink[] {
							new EventSink(	BatteryModel.URI , 
											ConsumeEvent.class)  	
						}) ;
			
			cmd.put(BatteryCoupledModel.URI,
					new CoupledHIOA_Descriptor(
							BatteryCoupledModel.class, 
							BatteryCoupledModel.URI, 
							submodelsBattery, 
							importedBattery, 
							reexportedBattery, 
							connectionsBattery, 
							null, 
							SimulationEngineCreationMode.COORDINATION_ENGINE, 
							null, 
							null, 
							null)
					) ;
			
			
			//---------------------------------------
			// Counter model
			//--------------------------------------
			amd.put(	CounterModel.URI,
						AtomicModelDescriptor.create(
								CounterModel.class, 
								CounterModel.URI,
								tu, 
								null,
								SimulationEngineCreationMode.ATOMIC_ENGINE) 
						) ;
//			Map<Class<? extends EventI>,EventSink[]> importedCounter =
//					new HashMap<Class<? extends EventI>,EventSink[]>() ;
//
//			Map<Class<? extends EventI>,ReexportedEvent> reexportedCounter =
//					new HashMap<Class<? extends EventI>,ReexportedEvent>() ;
//			
//			Set<String> submodelsCounter = new HashSet<> () ; 
//			submodelsCounter.add(CounterModel.URI) ;
//			
//			reexportedCounter.put(
//						ConsumeEvent.class, 
//						new ReexportedEvent(	CounterModel.URI, 
//												ConsumeEvent.class)) ;
//			
//			importedCounter.put(
//						ConsumptionResponseEvent.class, 
//						new EventSink[] {
//							new EventSink(	CounterModel.URI , 
//											ConsumptionResponseEvent.class)  	
//						}) ;
//			
//			cmd.put(	CounterCoupledModel.URI,
//						new CoupledModelDescriptor(
//								CounterCoupledModel.class, 
//								CounterCoupledModel.URI, 
//								submodelsCounter, 
//								importedCounter, 
//								reexportedCounter, 
//								null, null, 
//								SimulationEngineCreationMode.COORDINATION_ENGINE)
//					) ; 
			
			
			//--------------------------------------
			// full architecture
			//--------------------------------------
			Set<String> submodels = new HashSet<String>() ;
			submodels.add(OvenCoupledModel.URI) ;
			submodels.add(TvCoupledModel.URI) ;
			submodels.add(FridgeCoupledModel.URI) ;
			submodels.add(BatteryCoupledModel.URI) ;
			submodels.add(CounterModel.URI) ;
			
			Map<EventSource,EventSink[]> connections =
					new HashMap<EventSource,EventSink[]>() ;
			
			EventSource f1 =
					new EventSource(
							CounterModel.URI,
							ConsumeEvent.class) ;
			EventSink[] t1 =
					new EventSink[] {
							new EventSink(
									TvCoupledModel.URI,
									ConsumeEvent.class),
							new EventSink(
									BatteryCoupledModel.URI,
									ConsumeEvent.class),
							new EventSink(
									OvenCoupledModel.URI,
									ConsumeEvent.class),
							new EventSink(
									FridgeCoupledModel.URI,
									ConsumeEvent.class)} ;
			connections.put(f1, t1) ;
			
			EventSource f2 = 
					new EventSource (FridgeCoupledModel.URI , ConsumptionResponseEvent.class) ; 
			EventSink [] t2 = new EventSink [] {
					new EventSink (CounterModel.URI , ConsumptionResponseEvent.class)  
			} ;
			
			connections.put(f2, t2) ;
			
			EventSource f3 = 
					new EventSource (OvenCoupledModel.URI , ConsumptionResponseEvent.class) ; 
			EventSink [] t3 = new EventSink [] {
					new EventSink (CounterModel.URI , ConsumptionResponseEvent.class)  
			} ;
			
			connections.put(f3, t3) ;
			
			EventSource f4 = 
					new EventSource (TvCoupledModel.URI , ConsumptionResponseEvent.class) ; 
			EventSink [] t4 = new EventSink [] {
					new EventSink (CounterModel.URI , ConsumptionResponseEvent.class)  
			} ;
			
			connections.put(f4, t4) ;
			
			EventSource f5 = 
					new EventSource (BatteryCoupledModel.URI , ConsumptionResponseEvent.class) ; 
			EventSink [] t5 = new EventSink [] {
					new EventSink (CounterModel.URI , ConsumptionResponseEvent.class)  
			} ;
			
			connections.put(f5, t5) ;
			
			cmd.put(	HouseModel.URI, 
						new CoupledModelDescriptor(
								HouseModel.class, 
								HouseModel.URI, 
								submodels, 
								null, 
								null, 
								connections, 
								null, 
								SimulationEngineCreationMode.COORDINATION_ENGINE)
					) ;
			
			ArchitectureI architecture = 
					new Architecture (
								HouseModel.URI ,
								amd ,
								cmd,
								tu
							) ;
			
			//--------------------------------------
			// Run simulation
			//--------------------------------------
			
			
			SimulationEngine se = architecture.constructSimulator() ;
			
			
			Map<String, Object> sp = new HashMap<>() ; 
			String mURI = FridgeInnerTempController.URI ; 
			sp.put(mURI+":"+PlotterDescription.PLOTTING_PARAM_NAME, 
					new PlotterDescription(
							"FridgeInnerTempControler",
							"Time (sec)",
							"run/stop",
							700,
							0,
							600,
							400)) ;
			
			se.setSimulationRunParameters(sp);
			
			SimulationEngine.SIMULATION_STEP_SLEEP_TIME = 0L ;
			long start = System.currentTimeMillis() ;
			se.doStandAloneSimulation(0.0, 500.0) ;
			long end = System.currentTimeMillis() ;
			System.out.println(se.getFinalReport()) ;
			System.out.println("Simulation ends. " + (end - start)) ;
			Thread.sleep(1000000L);
			System.exit(0) ;
			
		}catch (Exception e) {
			e.printStackTrace() ;
		}
	
	}
	

}
//---------------------------------------------------------------------------------------------








































