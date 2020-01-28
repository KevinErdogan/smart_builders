package fr.smart_builders.simulator.models.oven;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import org.apache.commons.math3.random.RandomDataGenerator;
import fr.smart_builders.simulator.models.events.oven.RunOven;
import fr.smart_builders.simulator.models.events.oven.ScheduleRunOven;
import fr.smart_builders.simulator.models.events.oven.StopOven;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardLogger;

//-------------------------------------------------------------------------------------

/**
 * The class <code>OvenUserModel</code> implements a simple user
 * simulation for the oven model ( scheduling a start set a temperature and a duration)
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</code>
 */
@ModelExternalEvents (exported = {
							RunOven.class, 
							StopOven.class, 
							ScheduleRunOven.class
							})
public class 				OvenUserModel 
extends 					AtomicES_Model
{
	
	
	//--------------------------------------------------------------------
	// Constants and variables
	//--------------------------------------------------------------------	

	/**
	 * generated
	 */
	public static final String				URI = "OvenUserModel" ; 
	
	private static final long 				serialVersionUID = 7867397700156277793L;
	
	protected double						interDayDelay ; 
	
	protected double 						meanRunDuration ; 
	
	protected double 						meanTemperature ; 
	
	protected Class<?>						nexEvent ; 
	
	protected final RandomDataGenerator 	rg ; 
	
	
	
	
	
	
	//--------------------------------------------------------------------
	// Constructor
	//--------------------------------------------------------------------

	public 					OvenUserModel(
						String uri, 
						TimeUnit simulatedTimeUnit, 
						SimulatorI simulationEngine
						) throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		
		this.rg = new RandomDataGenerator() ;
		
		this.setLogger(new StandardLogger()) ;
	}
	
	
	//----------------------------------------------------------------------
	// Override Methods
	//----------------------------------------------------------------------
	
	@Override
	public void 						initialiseState (Time initialTime) 
	{
		this.interDayDelay = 1400.0 ; 
		
		this.meanRunDuration = 200.0 ;
		
		this.meanTemperature = 200.0 ; 
		
		this.rg.reSeedSecure() ;
		
		super.initialiseState(initialTime) ;
		
		// schedule first event 
		
		Duration d1 = new Duration (
					this.interDayDelay , 
					this.getSimulatedTimeUnit()
				) ; 
		
		assert d1 != null ;
		
		Time t = this.getCurrentStateTime().add(d1) ; 
		
		double dur = 2.0 * this.meanRunDuration * this.rg.nextBeta(1.75, 1.75) ; 
		double temp = 2.0 * this.meanTemperature * this.rg.nextBeta(1.75, 1.75) ; 
		
		// impossible to set temperature more than MAX_TEMPERATURE
		temp = (temp > OvenModel.MAX_TEMPERATURE)?OvenModel.MAX_TEMPERATURE:temp ;
		this.scheduleEvent(new RunOven(t, dur , temp));
		this.nextTimeAdvance = this.timeAdvance() ; 
		this.timeOfNextEvent = 
					this.getCurrentStateTime().add(this.nextTimeAdvance) ; 		
	}
	
	@Override
	public Duration 					timeAdvance () {
		return super.timeAdvance() ; 
	}
	
	@Override
	public Vector<EventI> 				output () {
		assert ! this.eventList.isEmpty() ; 
		
		Vector<EventI> events = super.output() ; 
		
		assert events.size() == 1 ; 
		
		this.nexEvent = events.get(0).getClass() ; 
		
		return events ; 
	}
	
	@Override
	public void 						userDefinedInternalTransition (
								Duration elapsedTime)
	{
		// send a run event every about 1400 sec in the simulation 
		Duration d1 = new Duration (
				this.interDayDelay , 
				this.getSimulatedTimeUnit()
			) ; 
		
		assert d1 != null ;
		
		Time t = this.getCurrentStateTime().add(d1) ; 
		double dur = 2.0 * this.meanRunDuration * this.rg.nextBeta(1.75, 1.75) ; 
		double temp = 2.0 * this.meanTemperature * this.rg.nextBeta(1.75, 1.75) ; 
		// impossible to set temperature more than MAX_TEMPERATURE
		temp = (temp > OvenModel.MAX_TEMPERATURE)?OvenModel.MAX_TEMPERATURE:temp ;
		this.scheduleEvent(new RunOven(t, dur , temp));
	}
	
	
	

	//----------------------------------------------------------------------
	// specific methods
	//----------------------------------------------------------------------
	
	
}
//------------------------------------------------------------------------------------



























