package fr.smart_builders.simulator.models.counter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.sorbonne_u.devs_simulation.architectures.Architecture;
import fr.sorbonne_u.devs_simulation.architectures.ArchitectureI;
import fr.sorbonne_u.devs_simulation.architectures.SimulationEngineCreationMode;
import fr.sorbonne_u.devs_simulation.hioa.architectures.AtomicHIOA_Descriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.AbstractAtomicModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.AtomicModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.architectures.CoupledModelDescriptor;
import fr.sorbonne_u.devs_simulation.models.events.EventSink;
import fr.sorbonne_u.devs_simulation.models.events.EventSource;
import fr.sorbonne_u.devs_simulation.simulators.SimulationEngine;

public class MainCounterModel {
	
	public static void main( String ... args)
	{
		try {
		Map<String, AbstractAtomicModelDescriptor> amd =
				new HashMap<> () ; 
		Map<String , CoupledModelDescriptor> cmd = new HashMap<> () ; 
		
		Set<String> submodel = new HashSet<> () ; 
		
		Map<EventSource , EventSink[]> connectionsTv = new HashMap<>() ;
		
		
		
		amd.put (	CounterModel.URI , 
					AtomicModelDescriptor.create(	
						CounterModel.class, 
						CounterModel.URI, 
						TimeUnit.SECONDS,
						null, 
						SimulationEngineCreationMode.ATOMIC_ENGINE)
		) ;
		
		amd.put (	CounterUModel.URI, 
				AtomicHIOA_Descriptor.create(
						CounterUModel.class, 
						CounterUModel.URI, 
						TimeUnit.SECONDS, 
						null, 
						SimulationEngineCreationMode.ATOMIC_ENGINE));
		
		submodel.add(CounterUModel.URI ) ;
		submodel.add(CounterModel.URI ) ;
		
		EventSource f1 = new EventSource(CounterModel.URI, ConsumeEvent.class) ;
		EventSink [] t1 = new EventSink [] {new EventSink (CounterUModel.URI , ConsumeEvent.class)} ; 
		connectionsTv.put (f1 , t1) ; 
		EventSource f2 = new EventSource (CounterUModel.URI , ConsumptionResponseEvent.class) ;
		EventSink[] t2 = new EventSink[] { new EventSink (CounterModel.URI , ConsumptionResponseEvent.class)} ; 
		connectionsTv.put(f2 ,  t2) ; 
		
		cmd.put(CounterCoupledModel.URI, new CoupledModelDescriptor(CounterCoupledModel.class, CounterCoupledModel.URI, submodel, null, null, connectionsTv, null, SimulationEngineCreationMode.COORDINATION_ENGINE));
		
		
		
		
		ArchitectureI a = new Architecture(CounterCoupledModel.URI, amd, cmd, TimeUnit.SECONDS) ;
		
		SimulationEngine se = a.constructSimulator() ;
		
		SimulationEngine.SIMULATION_STEP_SLEEP_TIME = 0L ;
		long start = System.currentTimeMillis() ;
		se.doStandAloneSimulation(0.0, 50000.0) ;
		long end = System.currentTimeMillis() ;
		System.out.println(se.getFinalReport()) ;
		System.out.println("Simulation ends. " + (end - start)) ;
		Thread.sleep(1000000L);
		System.exit(0) ;
		
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
