package fr.smart_builders.simulator.models.battery;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import org.apache.commons.math3.random.RandomDataGenerator;
import fr.smart_builders.simulator.models.events.battery.ChargeBatteryEvent;
import fr.smart_builders.simulator.models.events.battery.DischargeBatteryEvent;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardLogger;

//--------------------------------------------------------------------------------------------------------
/**
 * The class <code>BatteryUserModel</code> implements a simple usage scenario for 
 * the usage of the battery, according to the amount of energy in the system, it will be
 * charged or supplied, this class will simulate this.
 * 
 * the simulation is simple, it is represented by a cycle of FastCharging, stable Charging, 
 * Fast discharging and no charging (no discharging) 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
@ModelExternalEvents (exported = {
							ChargeBatteryEvent.class, 
							DischargeBatteryEvent.class 
					})
public class 								BatteryUserModel 
extends 									AtomicES_Model
{
	
	//------------------------------------------------------------------------------------------
	// constants and variables
	//------------------------------------------------------------------------------------------

	/** generated */
	private static final long 				serialVersionUID = -5884279528078306573L;
	
	public static final String 				URI = "BatteryUserModel" ; 
	
	protected double 						meanFastChargeTime ;
	
	protected double 						meanNoChargeTime ;
	
	protected double 						meanStableChargeTime ; 
	
	protected double 						meanFastDischargeTime ;
	
	protected double 						meanFastChargePower ; 
	
	protected double 						meanStableChargePower ; 
	
	protected double 						meanFastDischargePower ; 
	
	protected Class<?> 						nextEvent ; 

	protected final RandomDataGenerator		rg;
	
	
	// used to simulated the cycle 
	// its value goes from 0 to 3 following the cycle defined in the 
	// documentation of BatteryUserModel 
	private int 							cycle ; 
	
	
	
	//------------------------------------------------------------------------------------------
	// constructor 
	//------------------------------------------------------------------------------------------

	public 									BatteryUserModel(
										String uri, 
										TimeUnit simulatedTimeUnit, 
										SimulatorI simulationEngine) 
												throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		this.rg  = new RandomDataGenerator() ; 
		this.setLogger(new StandardLogger()) ;
	}
	
	//------------------------------------------------------------------------------------------
	// method
	//------------------------------------------------------------------------------------------
	
	@Override
	public void 						initialiseState (Time initialTime) 
	{
		// initialize variable 
		this.meanFastChargePower = 24000.0 ; 
		this.meanStableChargePower = 2000.0 ; 
		this.meanFastDischargePower = 30000.0 ; 
		
		this.meanFastChargeTime = 200.0 ; 
		this.meanStableChargeTime = 400.0 ; 
		this.meanNoChargeTime = 300.0 ; 
		this.meanFastDischargeTime = 500.0 ; 
		
		this.cycle = 0 ;
		
		this.rg.reSeedSecure() ; 
		super.initialiseState (initialTime) ; 
		
		double power = 2.0 * this.meanFastChargePower * this.rg.nextBeta(1.75, 1.75) ; 
		Duration decal = new Duration (10 , this.simulatedTimeUnit) ; 
		
		// schedule first event 
		this.scheduleEvent(new ChargeBatteryEvent(this.getCurrentStateTime(), power));
		
		this.nextTimeAdvance = this.timeAdvance() ; 
		
		assert this.nextTimeAdvance != null ; 
		
		this.timeOfNextEvent = 
					this.getCurrentStateTime().add(this.nextTimeAdvance) ; 
	}
	
	@Override
	public Duration 					timeAdvance () 
	{
		return super.timeAdvance() ; 
	}
	
	@Override
	public Vector<EventI> 				output () 
	{
		assert  ! this.eventList.isEmpty() ; 
		Vector<EventI> events = super.output() ; 
		assert events.size() == 1 ; 
		this.nextEvent = events.get(0).getClass() ; 
		System.err.println("se d"+this.nextEvent.getCanonicalName());
		return events ; 
	}
	
	@Override
	public void 						userDefinedInternalTransition (Duration elapsedTime) 
	{
		Duration d; 
		Time t; 
		double p; 
		if (this.cycle == 0 ) { // schedule a stable charging event
			d = new Duration (this.nextDouble(this.meanStableChargeTime) , this.getSimulatedTimeUnit()) ; 
			t = this.getCurrentStateTime().add(d) ; 
			p = this.nextDouble(this.meanStableChargePower) ; 
			this.scheduleEvent(new ChargeBatteryEvent (t , p));
		} else if ( this.cycle == 1 ) { // schedule a fast discharging event
			d = new Duration (this.nextDouble(this.meanFastDischargeTime) , this.getSimulatedTimeUnit()) ; 
			t = this.getCurrentStateTime().add(d) ; 
			p = this.nextDouble(this.meanFastDischargePower) ; 
			this.scheduleEvent(new DischargeBatteryEvent (t , p));
		} else if ( this.cycle == 2 ) {	// schedule a no charging event
			d = new Duration (this.nextDouble(this.meanNoChargeTime) , this.getSimulatedTimeUnit()) ; 
			t = this.getCurrentStateTime().add(d) ; 
			this.scheduleEvent(new ChargeBatteryEvent (t , 0));		
		} else {	// schedule a fast charging value
			d = new Duration (this.nextDouble(this.meanFastChargeTime) , this.getSimulatedTimeUnit()) ; 
			t = this.getCurrentStateTime().add(d) ; 
			p = this.nextDouble(this.meanFastChargePower) ; 
			this.scheduleEvent(new ChargeBatteryEvent (t , p));	
		}
		this.cycle ++ ; 
		this.cycle = this.cycle % 4 ;
	}
	
	
	@Override
	public SimulationReportI getFinalReport() throws Exception {
		// TODO Auto-generated method stub
		return new SimulationReportI() {

			private static final long serialVersionUID = 5324289556420297352L;

			@Override
			public String getModelURI() {
				return BatteryUserModel.URI;
			}
		};
	}
	
	// just to simplify calling to nextBeta of 
	// RandomDataGenerator
	// because we are using same constants at every value generation 
	// we haven't look for more information about 
	// statistics of the usage of a battery 
	private double nextDouble (double arround) 
	{
		return 2.0 * arround * this.rg.nextBeta(1.75, 1.75) ;
	}
}
//--------------------------------------------------------------------------------------------------------





































