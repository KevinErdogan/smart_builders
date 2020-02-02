package fr.smart_builders.simulator.models.battery;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.counter.event.AbstractCounterEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.smart_builders.simulator.models.events.battery.AbstractBatteryEvent;
import fr.smart_builders.simulator.models.events.battery.ChargeBatteryEvent;
import fr.smart_builders.simulator.models.events.battery.DischargeBatteryEvent;
import fr.smart_builders.simulator.models.events.battery.ReadBatteryLevelEvent;
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

//---------------------------------------------------------------------------------------
/**
 * The class <code>BatteryUserModel</code> implements a simple battery 
 * component ( model ) which holds the capacity and the power of the 
 * battery, the current charge ( amount of energy in it ) 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
@ModelExternalEvents (imported = {
							ChargeBatteryEvent.class, 
							DischargeBatteryEvent.class, 
							ReadBatteryLevelEvent.class, 
							ConsumeEvent.class
						},
						exported = {ConsumptionResponseEvent.class})
public class 								BatteryModel 
extends 									AtomicHIOAwithEquations
implements 									ConsumerI
{
	

	//------------------------------------------------------------------------------------
	// inner class and enum
	//------------------------------------------------------------------------------------

	public static enum						State 
	{
		CHARGING,				// the battery is charging 
		DISCHARGING, 			// the battery is supplied
		OUT						// no charge, no discharge (kind of out of system)
	}
	
	public static class						BatteryReport
	extends									AbstractSimulationReport
	{

		/** generated */
		private static final long 			serialVersionUID = 1956011480552501988L;

		public 								BatteryReport(String modelURI) {
			super(modelURI);
		}
		
		@Override
		public String 						toString () 
		{
			return "BatteryReport(" + this.getModelURI() + ")" ; 
		}
		
	}
	
	
	//------------------------------------------------------------------------------------
	// constants and variables
	//------------------------------------------------------------------------------------
	/** generated */
	private static final long 				serialVersionUID = 6418167324533123835L;
	
	/** factor of conversion from WATT to Wh
	 * 1 Wh = 3600 WATT ( from the definition of Wh ) */
	// j'ai besoin d'exagerer les choses par ce que les temps que je prend ne sont 
	// pas realise donc je change la valeur de 3600 à 60
	private static final double 			WATT2WH_CONVERSION_FACTOR = 360 ; 
	
	/** the capacity of the battery in WATT-HOUR */
	private static final double 			CAPACITY = 100000 ;
	
	/** the maximum power it can deliver in WATT */
	private static final double 			MAX_POWER_DELIVERY = 30000 ; 
	
	/** the maximum power it can charge in WATT */
	private static final double 			MAX_POWER_CHARGING = 40000 ; 
	
	public static final String 				URI = "BatteryModel" ; 
	
	private static final String 			SERIES = "batteryLevelSerie" ; 
	
	/** current level of charge in WATT-HOUR */
	private double 							level ; 
	
	/** current state of the battery  */
	private State 							currentState ; 
	
	/** the value of charging power or discharging power following the state */
	private double 							evolutionLevel ;
	
	private XYPlotter						levelPlotter ; 
	

	
	
	//------------------------------------------------------------------------------------
	// constructor
	//------------------------------------------------------------------------------------	
	public 									BatteryModel(
									String uri, 
									TimeUnit simulatedTimeUnit, 
									SimulatorI simulationEngine) 
											throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		PlotterDescription pd = new PlotterDescription (
										"Battery level" , 
										"Time (sec)" , 
										"Level (Wh)", 
										1900 , 
										0, 
										600, 
										400	) ;
		this.levelPlotter = new XYPlotter(pd) ; 
		this.levelPlotter.createSeries(SERIES);
	}

	
	
	//------------------------------------------------------------------------------------
	// override methods
	//------------------------------------------------------------------------------------
	@Override
	public void								initialiseState (Time initialTime)
	{
		this.currentState = State.OUT ; 
		this.levelPlotter.initialise() ; 
		this.levelPlotter.showPlotter() ;
		super.initialiseState(initialTime);
		
	}
	
	@Override
	public void 							initialiseVariables (Time startTime) 
	{
		// suppose that at the beginning the battery is half charged
		this.level = 50000 ; 
		this.evolutionLevel = 0.0 ; 
		super.initialiseVariables(startTime);
	}
	
	@Override
	public void 							userDefinedInternalTransition (Duration elapsedTime) 
	{
		this.computeReceiveDate();
		if (this.currentState == State.CHARGING ) {
			this.charge(this.evolutionLevel );
		}else if (this.currentState == State.DISCHARGING ) {
			this.consume(this.evolutionLevel ) ; 
		}
		this.computeReceiveDate();
	}

	@Override
	public void 							userDefinedExternalTransition (Duration elapsedTime)
	{
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		// one event at a time
		assert currentEvents != null && currentEvents.size () == 1 ; 
		
		Event ce = (Event) currentEvents.get(0) ;
		
		
		assert ce instanceof AbstractBatteryEvent || ce instanceof AbstractCounterEvent ; 
		
		ce.executeOn(this);
		
		super.userDefinedExternalTransition(elapsedTime);
	}
	
	@Override
	public Vector<EventI> 					output() {
		//		NO OUT PUT EVENT
		//		when we introduce the counter, it will send it's level as an event !
		return null;
	}



	@Override
	public Duration 						timeAdvance() {
		// TODO 
		return new Duration (1 , this.getSimulatedTimeUnit()) ; 
	}
	
	@Override
	public SimulationReportI				getFinalReport () throws Exception 
	{
		return new BatteryReport (this.getURI()) ; 
	}

	@Override
	public void 						giveConsumption() {
		double consumption = 0.0 ; 
		if (this.currentState == State.CHARGING)
			consumption = this.evolutionLevel ; 
		new ConsumptionResponseEvent(this.getCurrentStateTime(), BatteryModel.URI, consumption) ;
	}

	
	//------------------------------------------------------------------------------------
	// specific methods
	//------------------------------------------------------------------------------------
	
	/**
	 * @return the current battery's level of charge, more than 0 less than capacity 
	 */
	public double 							level () 
	{
		return this.level ; 
	}
	
	
	/**
	 * take power from the battery ( discharging it )
	 * 
	 * @param the power supplied from the battery (WATT)
	 * @return true if the battery deliverd the power, false else !
	 */
	private boolean							consume (double power) 
	{
		double discharge = power ; 
		// if it is empty 
		if (this.level == 0) 
			return false ; 
		
		// we use minus so if we take negative values it will became charging !!
		if (power < 0) 
			discharge = 0 ;
		// the battery can't deliver more than its max power delivery value
		// it returns false without discharging the operation should re done 
		// with a proper power value 
		if (power > BatteryModel.MAX_POWER_DELIVERY)
			power = BatteryModel.MAX_POWER_DELIVERY ; 
		
		// can't deliver more power than what it has in it
		if (this.level < discharge )
			discharge = this.level ; 
		
		// using minus so the value of power must be positive 
		this.level -= (discharge / BatteryModel.WATT2WH_CONVERSION_FACTOR) ; 
		return true ;
	}
	
	
	/**
	 * charge the battery 
	 * @param power given to the battery
	 */
	private void 							charge (double power) 
	{
		if (this.level < BatteryModel.CAPACITY ) {
			double charge = power ; 
			// no negatif values because it's discharging
			if (power < 0) 
				charge = 0 ; 
			//can't handle more than its max power charging value
			if (power > BatteryModel.MAX_POWER_CHARGING)
				charge = BatteryModel.MAX_POWER_CHARGING ; 
			// can't take more than its capacity 
			this.level += (charge / WATT2WH_CONVERSION_FACTOR ); 
			if (this.level > BatteryModel.CAPACITY)
				this.level = BatteryModel.CAPACITY ;
		}
	}
	
	public void 							receiveCharge (double power)
	{
		if (power == 0.0) {
			this.currentState = State.OUT ; 
		} else {
			this.currentState = State.CHARGING ; 
			this.evolutionLevel = power ;
		}
	}
	
	public void 							receiveDischarge (double power)
	{
		this.currentState = State.DISCHARGING ; 
		this.evolutionLevel = power ; 
	}
	
	public void 							readLevel () 
	{
		// TODO
	}
	
	private void 							computeReceiveDate () 
	{
		this.levelPlotter.addData(
								SERIES, 
								this.getCurrentStateTime().getSimulatedTime() , 
								this.level);
	}

}
//------------------------------------------------------------------------------------------





























