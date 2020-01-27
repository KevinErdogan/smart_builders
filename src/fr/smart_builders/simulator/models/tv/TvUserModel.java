package fr.smart_builders.simulator.models.tv;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.random.RandomDataGenerator;

import fr.smart_builders.simulator.models.events.tv.SwitchOffTv;
import fr.smart_builders.simulator.models.events.tv.SwitchOnTv;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardLogger;

//-------------------------------------------------------------------------------------------------------
/**
 * The class <code>TvUserModel</code> implements a simple user 
 * simulation for the TV
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
@ModelExternalEvents (exported = {
							SwitchOnTv.class , 
							SwitchOffTv.class })
public class 								TvUserModel 
extends 									AtomicES_Model
{

	//--------------------------------------------------------------------------------------
	// constants and variables
	//--------------------------------------------------------------------------------------
	
	/** generated */
	private static final long 				serialVersionUID = -5335591473544537505L;
	
	public static final String 				URI = "TvUserModel" ; 
	
	protected double 						interDayDelay ; 
	
	protected double 						meanDailyRunTime ; 
	
	protected Class<?>						nextEvent ; 
	
	protected final RandomDataGenerator		rg ; 
	
	//--------------------------------------------------------------------------------------
	// constructor 
	//--------------------------------------------------------------------------------------	
	public 									TvUserModel(
										String uri, TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine) 
												throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		
		this.rg = new RandomDataGenerator () ; 
		
		this.setLogger(new StandardLogger());
	}
	
	
	//--------------------------------------------------------------------------------------
	// methods
	//--------------------------------------------------------------------------------------
	
	@Override
	public void 							initialiseState (Time initialTime) 
	{
		this.interDayDelay = 1400 ; 
		this.meanDailyRunTime = 700 ; 
		this.rg.reSeedSecure () ; 
		
		super.initialiseState(initialTime) ; 
		
		//schedule first event 
		
		Duration d1 = new Duration (
							2 * this.interDayDelay * this.rg.nextBeta(1.75, 1.75) ,
							this.getSimulatedTimeUnit() 
				) ; 
		Time t = this.getCurrentStateTime().add(d1) ; 
		this.scheduleEvent(new SwitchOnTv(t , null )); // no information to give
		
		this.nextTimeAdvance = this.timeAdvance() ; 
		this.timeOfNextEvent = 
				this.getCurrentStateTime().add(this.nextTimeAdvance) ; 
	}
	
	@Override
	public Duration 						timeAdvance () 
	{
		return super.timeAdvance() ; 
	}
	
	@Override
	public Vector<EventI> 					output () 
	{
		assert ! this.eventList.isEmpty() ; 
		
		Vector<EventI> events = super.output() ; 
		
		assert events.size () == 1 ; 
		
		this.nextEvent = events.get(0).getClass() ;
		
		return events ; 
		
	}
	
	@Override
	public void 							userDefinedInternalTransition (Duration elapsedTime)
	{
		Duration d; 
		Time t ; 
		if (this.nextEvent.equals(SwitchOnTv.class )) {
			d = new Duration ( 
						2.0 * this.meanDailyRunTime * this.rg.nextBeta(1.75 ,  1.75) ,
						this.getSimulatedTimeUnit()
					) ; 
			t = this.getCurrentStateTime().add(d) ;
			
			this.scheduleEvent(new SwitchOffTv(t, null)); // null because their is no information to pass
					
		} else if (this.nextEvent.equals(SwitchOffTv.class)){
			d = new Duration ( 
					2.0 * this.interDayDelay * this.rg.nextBeta(1.75 ,  1.75) ,
					this.getSimulatedTimeUnit()
				) ; 
			t = this.getCurrentStateTime().add(d) ;
			
			this.scheduleEvent(new SwitchOnTv(t, null)); // null because their is no information to pass
		}
	}
	
	
	@Override
	public SimulationReportI getFinalReport() throws Exception {
		// TODO Auto-generated method stub
		return new SimulationReportI() {
			
			@Override
			public String getModelURI() {

				return "haha";
			}
		};
	}
}
//--------------------------------------------------------------------------------------------------------




















