package fr.smart_builders.simulator.models;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.devs_simulation.hioa.annotations.ExportedVariable;
import fr.sorbonne_u.devs_simulation.hioa.models.AtomicHIOAwithEquations;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.Value;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.AbstractSimulationReport;
import fr.sorbonne_u.utils.XYPlotter;

//-----------------------------------------------------------------------

/**
 * 
 * this class has been created following the examples of 
 * BCM-CyPhy-Components for HairDryer model
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

public class 		FridgeModel 
extends 			AtomicHIOAwithEquations
{
	
	
	//-----------------------------------------------------------------------
	//	inner classes -- enum
	//-----------------------------------------------------------------------
	
	
	
	/**
	 * The enumeration <code>State</code> describes the modes of running
	 * allowed to the smart fridge.
	 * the controller changes these modes according to needs
	 */
	public static enum State {
		OFF, 			/* the fridge isn't allowed to start*/
		ECO, 			/* the fridge can only run on echo mode
		 				   in order to have a smaller consumption*/
		NORMAL			/* the fridge can run on normal mode */
	}
	
	
	/**
	 * 	The class <code>FridgeReport</code> implements the 
	 * simulation report of the Fridge model
	 * 
	 */
	public static class 	FridgeReport
	extends 				AbstractSimulationReport
	{
		
		/**
		 * generated
		 */
		private static final long serialVersionUID = 5468593802915832795L;

		public FridgeReport(String modelURI) {
			super(modelURI);
		}
		
		@Override
		public String toString () 
		{
			return "FridgeReport(" + this.getModelURI() + ")" ;
		}
	}
	
	

	
	//-----------------------------------------------------------------------
	//	constructor
	//-----------------------------------------------------------------------
	/**
	 * @param uri
	 * @param simulatedTimeUnit
	 * @param simulationEngine
	 * @throws Exception
	 */

	public 				FridgeModel(
			String uri, 
			TimeUnit simulatedTimeUnit, 
			SimulatorI simulationEngine
			) throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		//		 don't forget to create the plotter
		//		 don't forget to create the logger 
	}

	
	
	//-----------------------------------------------------------------------
	//	consts and vars
	//-----------------------------------------------------------------------
	
	/*
	 * generated
	 */
	private static final long serialVersionUID = -1851082464766332877L;

	
	public static final String 			URI = "FridgeModel-1" ;
	
	private static final String 			SERIES = "power" ;
	
	//	energy consumption ( electric power ) using normal mode ( full power ) 
	//  unit : watt
	protected static final double 			NORMAL_MODE_CONSUMPTION = 200;
	
	//	energy consumption ( electric power ) using eco mode
	//  unit : watt
	protected static final double 			ECO_MODE_CONSUMPTION = 150;
	
	
	//	current power consumption in watt
	@ExportedVariable (type= Double.class)
	protected final Value <Double> 	currentPower = 
								new Value<Double> (this , 0.0 , 0);
	
	//	current state either OFF or ECO or NORMAL of the fridge
	protected State 				currentState ; 

	
	protected XYPlotter				powerPlotter ; 
	
	

	
	//-----------------------------------------------------------------------
	//	methods
	//-----------------------------------------------------------------------

	@Override
	public Vector<EventI> output() {
		// fridge model doesn't output any event it just receives
		return null;
	}

	
	
	// need to understand more about it
	@Override
	public Duration timeAdvance() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
