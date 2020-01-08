package fr.smart_builders.simulator.models;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import fr.smart_builders.simulator.models.events.AbstractFridgeEvent;
import fr.smart_builders.simulator.models.events.RunFridge;
import fr.smart_builders.simulator.models.events.StopFridge;
import fr.smart_builders.simulator.models.events.SwitchEcoFridge;
import fr.smart_builders.simulator.models.events.SwitchNormalFridge;
import fr.smart_builders.simulator.models.events.SwitchOffFridge;
import fr.sorbonne_u.devs_simulation.hioa.annotations.ExportedVariable;
import fr.sorbonne_u.devs_simulation.hioa.models.AtomicHIOAwithEquations;
import fr.sorbonne_u.devs_simulation.hioa.models.vars.Value;
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

//-----------------------------------------------------------------------

/**
 * 
 * this class has been created following the examples of 
 * BCM-CyPhy-Components for HairDryer model
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

@ModelExternalEvents (imported = {
							RunFridge.class, 
							StopFridge.class,
							SwitchEcoFridge.class,
							SwitchNormalFridge.class,
							SwitchOffFridge.class})
public class 		FridgeModel 
extends 			AtomicHIOAwithEquations
{
	
	
	//-----------------------------------------------------------------------
	//	inner classes -- enum
	//-----------------------------------------------------------------------
	
	
	
	/**
	 * The enumeration <code>Mode</code> describes the modes of running
	 * allowed to the smart fridge.
	 * the controller changes these modes according to needs
	 */
	public static enum Mode {
		OFF, 			/* the fridge isn't allowed to start*/
		ECO, 			/* the fridge can only run on echo mode
		 				   in order to have a smaller consumption*/
		NORMAL			/* the fridge can run on normal mode */
	}
	
	/**
	 * The enumeration <code>State</code> describes the state of the 
	 * fridge weather it's running or not 
	 * it depends on the inner temperature of the fridge
	 */
	public static enum State {
		STOP,			/* the fridge isn't running
		 					the inner temperature is good */
		RUN				/* the fridge is running
		 					the inner temperature is high */
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
		PlotterDescription pd = 
				new PlotterDescription(
						"Frdige power", 
						"Time (sec)", 
						"Power (watt)", 
						100, 
						0, 
						600, 
						400) ;
		this.powerPlotter = new XYPlotter (pd) ; 
		this.powerPlotter.createSeries (SERIES) ;
	}

	
	
	//-----------------------------------------------------------------------
	//	consts and vars
	//-----------------------------------------------------------------------
	
	/*
	 * generated
	 */
	private static final long serialVersionUID = -1851082464766332877L;

	
	public static final String 				URI = "FridgeModel-1" ;
	
	private static final String 			SERIES = "power" ;
	
	//	energy consumption ( electric power ) using normal mode ( full power ) 
	//  unit : watt
	protected static final double 			NORMAL_MODE_CONSUMPTION = 200;
	
	//	energy consumption ( electric power ) using eco mode
	//  unit : watt
	protected static final double 			ECO_MODE_CONSUMPTION = 150;
	
	
	//	top threshold for inner temperature in the fridge
	//	when inner temperature goes above this value
	//	the fridge tries to run if it it is allowed to 
	//	the unit used is °C
	protected static final double 			MAX_INNER_TEMP = 6 ;
	
	//	bottom threshold for inner temperature in the fridge
	//	when inner temperature goes under this value
	//	the fridge stops running
	//	the unit used is °C
	protected static final double 			MIN_INNER_TEMP = 4 ; 
	
	
	//	current power consumption in watt
	@ExportedVariable (type= Double.class)
	protected final Value <Double> 	currentPower = 
								new Value<Double> (this , 0.0 , 0);
	
	//	current mode either OFF or ECO or NORMAL of the fridge
	protected Mode 					currentMode ; 
	
	//	current state either RUN or STOP of the fridge
	protected State 				currentState ; 
	
	//  Inner temperature in °C
	//  it is exported just for monitoring
	@ExportedVariable (type = Double.class)
	protected final Value <Double> temperature = 
								new Value <Double> (this , 2.0 , 0);

	
	
	//	inner event used to run or stop fridge !!
	protected Class<?>				computedEvent ;
	
	protected XYPlotter				powerPlotter ;
	
	protected XYPlotter 			temperaturePlotter ;
	
	

	
	//-----------------------------------------------------------------------
	//	methods override
	//-----------------------------------------------------------------------

	
	@Override
	public void 					initialiseState (Time initialTime) 
	{
		// the fridge starts in normal mode
		this.currentMode = Mode.NORMAL ; 
		
		
		// initialization of the plotter
		this.powerPlotter.initialise() ;
		this.powerPlotter.showPlotter() ;
		
		try {
			this.setDebugLevel(1);
		}catch (Exception e) {
			throw new RuntimeException (e);
		}
		
		super.initialiseState(initialTime);
	}
	
	@Override
	public void 					initialiseVariables (Time startTime)
	{
		this.currentPower.v = 0.0 ; 
		
		
	}
	
	
	@Override
	public Vector<EventI> output() {
		// fridge model doesn't output any event it just receives
		return null;
	}

	
	// JUST A TRY
	@Override
	public Duration 			timeAdvance() { 
		// TODO need to understand more about it
		
		
		// what to return ? 
		return null ; 
	}
	
	
	@Override
	public void 					userDefinedInternalTransition 
					(Duration elapsedTime)
	{
		// TODO need to understand to do it
		// i think here we have to put all inner behaviors 
		// of the fridge
		// -----
		// or create an other Model which will be sending
		// event to run or stop the fridge depending on 
		// its inner temperature. in this case 
		// it would be done in External Transition ? 
		
		// JUST A TRY -- need to confirme that with the teacher
		
		Duration d ; 
		if (this.computedEvent.equals(RunFridge.class)) {
			
		}else if (this.computedEvent.equals (StopFridge.class)) {
			
		}
	}
	
	@Override
	public void 					userDefinedExternalTransition 
					(Duration elapsedTime) 
	{
		if (this.hasDebugLevel(2)) {
			this.logMessage("FridgeModel::userDefinedExternalTransition 1") ;
		}
		
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ;
		
		
		// there is only one event at time 
		assert currentEvents != null && currentEvents.size() == 1 ; 
		
		Event ce = (Event) currentEvents.get(0) ;
		assert ce instanceof AbstractFridgeEvent ;
		
		if (this.hasDebugLevel(2)) {
			this.logMessage("FridgeModel::userDefinedExternalTransition 2 "
								+ce.getClass().getCanonicalName());
		}
		
		this.powerPlotter.addData (
				SERIES, 
				this.getCurrentStateTime().getSimulatedTime(),
				this.getConsumption()
				);
		
		if (this.hasDebugLevel(2)) {
			this.logMessage("FridgeModel::userDefinedExternalTransition 3 "
								+this.getMode());
		}
		
		ce.executeOn(this);
		
		if (this.hasDebugLevel(1)) {
			this.logMessage("FridgeModel::userDefinedExternalTransition 4 "
								+this.getMode());
		}
		
		this.powerPlotter.addData(
				SERIES, 
				this.getCurrentStateTime().getSimulatedTime(), 
				this.getConsumption());
		
		super.userDefinedExternalTransition(elapsedTime);
		
		if (this.hasDebugLevel(2)) {
			this.logMessage("FridgeModel::userDefinedExternalTransition 5") ;
		}
	}
	
	@Override 
	public void 					endSimulation (Time endTime )
	throws 							Exception 
	{
		this.powerPlotter.addData(
				SERIES, 
				endTime.getSimulatedTime(), 
				this.getConsumption()) ;
		Thread.sleep(10000L);
		this.powerPlotter.dispose() ;
		
		super.endSimulation(endTime);
	}
	
	@Override 
	public SimulationReportI 		getFinalReport () throws Exception 
	{
		return new FridgeReport (this.getURI()) ; 
	}
	
	//-----------------------------------------------------------------------
	//	other methods
	//-----------------------------------------------------------------------
	
	
	private void 		computePower () 
	{
		Mode m = this.getMode();
		State s = this.getState();
		if (m == Mode.OFF || s == State.STOP) {
			this.currentPower.v = 0.0;
		}else if ( s == State.RUN ) {
			if (m == Mode.ECO ) {
				this.currentPower.v = ECO_MODE_CONSUMPTION ; 
			} else if ( m == Mode.NORMAL ) {
				this.currentPower.v = NORMAL_MODE_CONSUMPTION ;
			}
		}
	}
	
	public void			setMode (Mode s) 
	{
		this.currentMode = s ; 
		this.computePower ();
	}
	
	public void 		setState (State s) 
	{
		this.currentState = s;
		this.computePower ();
	}
	
	
	public Mode 		getMode () 
	{
		return this.currentMode ; 
	}
	
	public State 		getState () 
	{
		return this.currentState ;
	}

	
	public double 		getConsumption () 
	{
		return this.currentPower.v ;
	}
	
	public double 		getTemperature () 
	{
		return this.temperature.v ; 
	}

}
