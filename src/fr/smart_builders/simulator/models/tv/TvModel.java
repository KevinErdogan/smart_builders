package fr.smart_builders.simulator.models.tv;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.smart_builders.simulator.models.events.tv.AbstractTvEvent;
import fr.smart_builders.simulator.models.events.tv.SetEcoModeTv;
import fr.smart_builders.simulator.models.events.tv.SetNormalModeTv;
import fr.smart_builders.simulator.models.events.tv.SwitchOffTv;
import fr.smart_builders.simulator.models.events.tv.SwitchOnTv;
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

//--------------------------------------------------------------------------------------------------------
/**
 * The class <code>TvModel</code> ::
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan </p>
 */
@ModelExternalEvents (imported = {
							SwitchOnTv.class , 
							SwitchOffTv.class , 
							SetEcoModeTv.class , 
							SetNormalModeTv.class,
							ConsumeEvent.class},
						exported = {ConsumptionResponseEvent.class
})
public class 							TvModel 
extends 								AtomicHIOAwithEquations
implements 								ConsumerI
{

	//------------------------------------------------------------------------------------------
	//	inner class & enumeration
	//------------------------------------------------------------------------------------------	
	
	public static enum 					State
	{
		ON, 				// the tv is on 
		OFF					// the tv is off
	}
	
	
	/**
	 *	describes the mode of the tv 
	 */
	public static enum					Mode
	{
		ECO, 				// the tv is on eco mode (less brightness for example)
		NORMAL				// the tv is on normal mode (work normally) 
	}
	
	public static class					TvReport
	extends 							AbstractSimulationReport
	{
		/** generated */
		private static final long serialVersionUID = 7843114760427898969L;

		public 							TvReport(
									String modelURI) 
		{
			super(modelURI);
		}
		
		@Override
		public String 					toString () 
		{
			return "TvReport(" + this.getModelURI() + ")" ; 
		}		
	}
	
	

	//------------------------------------------------------------------------------------------
	//	Constructor
	//------------------------------------------------------------------------------------------	
	/**	generated */
	private static final long 			serialVersionUID = -4368520552108157259L;
	
	public static final String 			URI = "TV" ; 
	
	/* the power of TV, literally this TV consume 40 WATT */
	protected static final double		NORMAL_MODE_CONSUMPTION = 40 ; 
	
	/* the power of TV when it is on eco mode, the unit is WATT */
	protected static final double		ECO_MODE_CONSUMPTION 	= 30 ; 
	
	protected static final String 		SERIES = "tv power" ;
	
	
	/* the current state of the TV , it can be ON or OFF */
	private State 						currentState ; 
	
	/* the current Mode of the TV */
	private Mode 						currentMode ; 
	
	/* power plotter */
	private XYPlotter 					powerPlotter ; 
	
	
	
	//------------------------------------------------------------------------------------------
	//	Constructor
	//------------------------------------------------------------------------------------------
	
	public 								TvModel(
									String uri, 
									TimeUnit simulatedTimeUnit, 
									SimulatorI simulationEngine) 
								throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		PlotterDescription pd = new PlotterDescription(
										"TV Consumption" , 
										"Time (sec)" , 
										"Power (WATT)", 
										1300, 
										0, 
										600 ,
										400
				) ; 
		this.powerPlotter = new XYPlotter(pd) ; 
		this.powerPlotter.createSeries(SERIES);
	}

	
	
	//------------------------------------------------------------------------------------------
	//	Override methods
	//------------------------------------------------------------------------------------------
	
	@Override
	public void 						initialiseState (Time initialTime) 
	{
		this.currentMode = TvModel.Mode.NORMAL ; 
		this.currentState = TvModel.State.OFF ; 
		this.powerPlotter.initialise(); 
		this.powerPlotter.showPlotter();
		super.initialiseState(initialTime);
	}
	
	@Override
	public void 						initialiseVariables (Time startTime) 
	{
		super.initialiseVariables(startTime);
	}
	
	
	@Override
	public Vector<EventI> output() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Duration timeAdvance() {
		return Duration.INFINITY ;
	}
	
	@Override
	public void 						userDefinedInternalTransition (Duration elapsedTime) 
	{
		// nothing to do 
		super.userDefinedInternalTransition(elapsedTime) ;
	}
	
	@Override
	public void 						userDefinedExternalTransition (Duration elapsedTime) 
	{
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		assert currentEvents != null && currentEvents.size() == 1 ;
		
		Event ce = (Event) currentEvents.get(0) ; 
		assert ce instanceof AbstractTvEvent || ce instanceof ConsumeEvent; 
		
		this.computeAddNewDate() ; 
		ce.executeOn(this);
		this.computeAddNewDate() ; 
		
		super.userDefinedExternalTransition(elapsedTime) ;
	}
	
	@Override 
	public SimulationReportI 		getFinalReport () throws Exception 
	{
		return new TvReport (this.getURI()) ; 
	}
	
	@Override
	public void giveConsumption() {
		new ConsumptionResponseEvent(
					this.getCurrentStateTime(), 
					TvModel.URI, 
					this.getCurrentConsumption()) ; 
	}

	//------------------------------------------------------------------------------------------
	//	specific methods
	//------------------------------------------------------------------------------------------
	
	public void 						switchOn () {
		this.currentState = TvModel.State.ON ; 
	}
	
	public void 						switchOff () {
		this.currentState = TvModel.State.OFF ;
	}

	public void 						setMode (TvModel.Mode mode) {
		this.currentMode = mode ; 
	}

	
	
	public double 						getCurrentConsumption () 
	{
		if (this.currentState == TvModel.State.OFF) {
			return 0.0 ; 
		}
		if (this.currentMode == TvModel.Mode.ECO) {
			return TvModel.ECO_MODE_CONSUMPTION ; 
		}
		return TvModel.NORMAL_MODE_CONSUMPTION ; 
	}
	
	private void 						computeAddNewDate () 
	{
		this.powerPlotter.addData(SERIES, 
							this.getCurrentStateTime().getSimulatedTime(), 
							this.getCurrentConsumption ());
	}
	
	// getters
	
	public TvModel.Mode 				getMode () {
		return this.currentMode ; 
	}
	
	public TvModel.State				getState () {
		return this.currentState ; 
	}

}
//----------------------------------------------------------------------------------------------------------


























