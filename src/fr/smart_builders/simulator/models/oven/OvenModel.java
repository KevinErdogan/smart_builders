package fr.smart_builders.simulator.models.oven;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.smart_builders.simulator.models.events.oven.AbstractOvenEvent;
import fr.smart_builders.simulator.models.events.oven.RunOven;
import fr.smart_builders.simulator.models.events.oven.ScheduleRunOven;
import fr.smart_builders.simulator.models.events.oven.StopOven;
import fr.smart_builders.simulator.simulation.ConsumerI;
import fr.sorbonne_u.devs_simulation.hioa.models.AtomicHIOAwithEquations;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.Event;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.AbstractSimulationReport;
import fr.sorbonne_u.utils.PlotterDescription;
import fr.sorbonne_u.utils.XYPlotter;

//----------------------------------------------------------------------------


/**
 * 
 * The class <code>OvenModel</code> implements a simple oven component (model)
 * which holds the state and consumption values, it can be scheduled to 
 * run at a certain time
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 *
 */

@ModelExternalEvents (imported = {
							RunOven.class , 
							StopOven.class , 
							ScheduleRunOven.class,
							ConsumeEvent.class} , 
						exported = {ConsumptionResponseEvent.class})
public class 				OvenModel 
extends 					AtomicHIOAwithEquations
implements 					ConsumerI
{
	
	//------------------------------------------------------------------------
	//	inner classes and enumerations
	//------------------------------------------------------------------------

	public static enum State {
		STOP, 		// the oven is off 
		RUN,		// the oven is on 
		MAINTAIN	// the oven is on and hot it maintains it inner temperature 
					// that means that the oven doesn't run at it's full power
	}
	
	public static class 		OvenReport
	extends 					AbstractSimulationReport
	{
		
		
		/**
		 * 	generated
		 */
		private static final long serialVersionUID = -2911399908054872124L;

		public 					OvenReport (String modelURI) 
		{
			super(modelURI) ; 
		}
		
		@Override
		public String 			toString ()
		{
			return "OvenReport(" + this.getModelURI() + ")" ; 
		}
	}


	
	//------------------------------------------------------------------------
	//	variables and constants 
	//------------------------------------------------------------------------
	
	/**
	 * 	generated 
	 */
	private static final long serialVersionUID = -434306293293986711L;
	
	public static final String 			URI = "OvenModel-1" ; 
	
	private static final String 		SERIES = "power" ;
	
	/** energy consumption ( electric power ) unit : Watt */
	protected static final double 		POWER = 2000 ; 
	
	protected static final double 		MAINTAIN_CONSUMPTION = 600 ; 
	
	/** the maximum temperature the oven can get */
	protected static final double 		MAX_TEMPERATURE 	= 260 ; 
	
	
	
	
	/** duration of max power time till the right temperature  */
	private Duration 			delay ; 
	
	/** used to schedule a start */
	private Duration 			runIn  ; 
	
	/** used to stop running the oven after a while  */
	private Duration 			stopIn ; 
	
	// need a function to compute the temperature during the simulation 
	/** current inner temperature of the oven it is set by the user */
	private double	 			currentTemperature ; 
	
	/** the inner temperature desired  */
	private double 				wantedTemperature ;
	
	/** current state of the oven whether it is on or off */
	protected State 			currentState ; 
	
	/** the current energy consumption of the oven ( we are speaking about power )*/
	private double 				currentConsumption ;
	
	protected XYPlotter			powerPlotter ; 
	
	private boolean 			triggedSendConsumption ; 

	
	
	//------------------------------------------------------------------------
	//	Constructor 
	//------------------------------------------------------------------------
		
	
	public 						OvenModel(
							String uri, 
							TimeUnit simulatedTimeUnit, 
							SimulatorI simulationEngine
							) throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		
		PlotterDescription pd =
				new PlotterDescription(
						"OvenPower", 
						"Time (sec)", 
						"Power (watt)", 
						700, 
						400, 
						600, 
						400) ;
		this.powerPlotter = new XYPlotter(pd) ; 
		this.powerPlotter.createSeries(SERIES) ; 
	}
	
	//------------------------------------------------------------------------
	//	Override methods 
	//------------------------------------------------------------------------

	@Override
	public void 				initialiseState (Time initialTime) 
	{
		this.currentState = OvenModel.State.STOP ; 
		this.powerPlotter.initialise();  
		this.powerPlotter.showPlotter();
		this.runIn = Duration.INFINITY ; 
		this.stopIn = Duration.INFINITY ; 
		this.delay = Duration.INFINITY ;
		
		super.initialiseState(initialTime) ;
	}
	
	@Override
	public void 				initialiseVariables (Time startTime) 
	{
		this.currentConsumption = 0 ; 
		// need a function to compute the temperature during the simulation 
		this.currentTemperature = 0 ; 
		this.triggedSendConsumption = false ; 
	}
	
	
	@Override
	public Vector<EventI> output() {
	if (this.triggedSendConsumption)
	{
		Vector<EventI> event = new Vector<EventI>() ; 
		EventI e ;
		e = new ConsumptionResponseEvent(
				this.getCurrentStateTime(), 
				OvenModel.URI, 
				this.currentConsumption) ;
		event.add(e) ; 
		this.triggedSendConsumption = false ; 
		return event;
	}
		return null ; 
	}

	@Override
	public Duration timeAdvance() {
		if (this.triggedSendConsumption) {
			return Duration.zero(this.getSimulatedTimeUnit()) ; 
		}
		if (this.currentState == OvenModel.State.RUN) {
			if (this.delay.lessThan(this.stopIn)) {
				return this.delay ;
			}else {
				return this.stopIn ;
			}
		} else {
			return this.runIn ; 
		}
	}
	
	@Override
	public void 				userDefinedInternalTransition (Duration elpasedTime)
	{
		// normally it's called only in three cases !
		// case 1 : oven just started it will be called to set consumption to
		//			cruise value after a while 
		// case 2 : when a run is scheduled ! -> not sure of that
		// case 3 : the oven is on and must stop in a while !!
		
		if (this.currentState == OvenModel.State.RUN) {
			if (this.delay.lessThan(this.stopIn)) {
				this.delay = Duration.INFINITY ;
				this.computeNewData();
				this.currentConsumption = OvenModel.MAINTAIN_CONSUMPTION ; 
				this.currentTemperature = this.wantedTemperature ; 
				this.computeNewData();
				
			} else if (this.stopIn != Duration.INFINITY) { 
				this.runIn = Duration.INFINITY ; 
				this.stopIn = Duration.INFINITY ; 
				this.stop() ;
				// normally temperature will fall gradually 
				// but for simplicity we put it to 0 directly 
				this.currentTemperature = 0.0 ; 
			}
		} else {
			if (this.runIn != Duration.INFINITY) { 
				this.runIn = Duration.INFINITY ; 
				// the temperature might grow gradually !! not done for simplicity 
				this.run() ;
			}
		} 
	}
	
	@Override
	public void 				userDefinedExternalTransition (Duration elapsedTime) 
	{
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		// one event at a time
		assert currentEvents != null && currentEvents.size () == 1 ; 
		
		Event ce = (Event) currentEvents.get(0) ;
		
		assert ce instanceof AbstractOvenEvent || ce instanceof ConsumeEvent ; 
		
		ce.executeOn(this);
		
		super.userDefinedExternalTransition(elapsedTime);
		
		
	}
	
	
	@Override 
	public void 					endSimulation (Time endTime )
	throws 							Exception 
	{
		this.powerPlotter.addData(
				SERIES, 
				endTime.getSimulatedTime(), 
				this.currentConsumption) ;
		Thread.sleep(100000L);
		this.powerPlotter.dispose() ;
		
		super.endSimulation(endTime);
	}
	
	@Override 
	public SimulationReportI 		getFinalReport () throws Exception 
	{
		return new OvenReport(this.getURI()) ; 
	}
	
	@Override
	public void giveConsumption() {
		this.triggedSendConsumption = true ; 
	}
	
	
	
	//------------------------------------------------------------------------
	//	specific methods
	//------------------------------------------------------------------------

	
	
	/**
	 * give the time t needed by the oven to get its inner temperature to desired one
	 * this means when the oven is switched on it will run at its max power for
	 * t time unit and then starts running at cruise power to maintain its inner
	 * temperature 
	 * 
	 * the function used to compute the value of t insn't realistic 
	 */
	private Duration 			computeMaxPowerTimeRun (double temp) {

		double d = (1 - Math.exp((-temp / 90)))* 80 ;
		
		return  new Duration(d, getSimulatedTimeUnit ()); 
	}
	
	public void 				run (double d , double t) 
	{
		// can't set the temperature to more than the max of the oven
		assert t <= OvenModel.MAX_TEMPERATURE ; 
		this.wantedTemperature = t ;
		this.computeNewData(); 
		this.delay = computeMaxPowerTimeRun(t) ; 
		System.err.println(delay.getSimulatedDuration());
		this.currentState = OvenModel.State.RUN ; 
		this.currentConsumption = OvenModel.POWER ;
		this.stopIn = new Duration(d,this.getSimulatedTimeUnit()) ; 
		this.computeNewData(); 
	}
	
	
	
	private void 				run () {
		this.computeNewData(); 
		this.delay = computeMaxPowerTimeRun(this.wantedTemperature) ; 
		this.currentState = OvenModel.State.RUN ; 
		this.currentConsumption = OvenModel.POWER ;
		this.computeNewData(); 
	}
	
	public void 				stop () 
	{
		this.computeNewData() ;
		this.currentState = OvenModel.State.STOP ; 
		this.currentConsumption = 0 ; 
		this.computeNewData();  
		
	}
	
	public void 				schedule (
									Time startTime , 
									double temperature,
									double duration)
	{
		// to simplify the work 
		assert  this.currentState == OvenModel.State.STOP ;
		
		// can't set the temperature to more than the maximum
		assert temperature <= OvenModel.MAX_TEMPERATURE ; 

		this.wantedTemperature = temperature ;
		
		double diff = startTime.getSimulatedTime() - this.getCurrentStateTime().getSimulatedTime() ; 
		this.runIn = new Duration(
				diff, 
				this.getSimulatedTimeUnit()) ;
		this.stopIn = new Duration (duration , this.getSimulatedTimeUnit()) ; 
	}
	
	private void 				computeNewData () 
	{
		this.powerPlotter.addData(
				SERIES, this.getCurrentStateTime().getSimulatedTime(), 
				this.currentConsumption) ;
	}
	
	public double 				innerTemperature ()
	{
		return this.currentTemperature ;
	}

	
}
//-------------------------------------------------------------------------------------------------------







