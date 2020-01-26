package fr.smart_builders.simulator.simulation;

import fr.smart_builders.simulator.models.oven.OvenCoupledModel;
import fr.sorbonne_u.devs_simulation.architectures.ArchitectureI;
import fr.sorbonne_u.devs_simulation.simulators.SimulationEngine;

//--------------------------------------------------------------------------------------------

/**
 * The class <code>MainOven</code> make the assembly of an architecture
 * for oven and run the simulation 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
public class 							MainOven 
{
	
	public static void main (String ... args) {
		try {
			
			ArchitectureI a = OvenCoupledModel.build() ; 
			System.err.println("________________________6________________________________");
			
			SimulationEngine se = a.constructSimulator() ; 
			
			System.err.println("________________________7________________________________");
			SimulationEngine.SIMULATION_STEP_SLEEP_TIME = 0L ;
			long start = System.currentTimeMillis() ;
			se.doStandAloneSimulation(0.0, 5000.0) ;
			long end = System.currentTimeMillis() ;
			System.out.println(se.getFinalReport()) ;
			System.out.println("Simulation ends. " + (end - start)) ;
			Thread.sleep(1000000L);
			System.exit(0) ;
			
			
		} catch (Exception e) {
			e.printStackTrace() ; 
		}
	}

}
