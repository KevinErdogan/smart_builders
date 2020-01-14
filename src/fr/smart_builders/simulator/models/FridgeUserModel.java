package fr.smart_builders.simulator.models;

import java.security.SecureRandom;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import org.apache.commons.math3.random.RandomDataGenerator;
import fr.smart_builders.simulator.models.events.CloseDoorFridge;
import fr.smart_builders.simulator.models.events.OpenDoorFridge;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardLogger;

//------------------------------------------------------------------------------


/**
 * The class <code>FridgeUserModel</code> implements a simple user 
 * simulation for the fridge (opening and closing the door)  
 * 
 * @authors <p>Yacine Zaouzaou, Kevin Erdogan</p> 
 */

@ModelExternalEvents (exported = { 
								OpenDoorFridge.class,
								CloseDoorFridge.class
					})
public class 							FridgeUserModel 
extends 								AtomicES_Model
{
	
	//--------------------------------------------------------------------
	// Constants and variables
	//--------------------------------------------------------------------
	
	public static final String 				URI = "FridgeUserModel" ;
	
	// can't put it in final without initialize it !
//	@ImportedVariable (type = FridgeModel.DoorState.class)
//	protected Value<FridgeModel.DoorState> 	doorState ; 
	
	// usage behavior took from the HairDryerUserModel example
	
	/** initial delay before sending the first door open    */
	protected double 						initialDelay ;
	
	/** delay between two days -- in the night no opening !*/
	protected double 						openDuration ; 
	
	/** delay between two opening of the day in one day */
	protected double 						meanTimeBetweenOpen ; 
	
	protected double 						interDayDelay ;
	
	protected Class<?> 						nextEvent ; 

	protected final RandomDataGenerator		rg;
	
	protected int 							numberOfOpningPerDay ; 
	
	protected int 							counterNumberOfOpening ;
	
//	protected XYPlotter 					plotter ;
	
	
	/**
	 *  generated
	 */
	private static final long serialVersionUID = 3129646836981536715L;

	
	//---------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------
	
	
	public						FridgeUserModel(
							String uri, 
							TimeUnit simulatedTimeUnit, 
							SimulatorI simulationEngine) 
						throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		
		this.rg = new RandomDataGenerator() ;
		
		this.setLogger(new StandardLogger ());
	}
	
	
	
	//----------------------------------------------------------------------
	// Methods
	//----------------------------------------------------------------------
	
	
	// need to understand more about it !! particularly at the end
	@Override
	public void 					initialiseState (Time initialTime) 
	{
		this.initialDelay = 70.0 ; 
		this.counterNumberOfOpening = 0 ; 
		this.meanTimeBetweenOpen = 140.0 ; 
		this.openDuration = 45.0 ; 
		this.interDayDelay = 700.0 ; 
		
		this.gennopd () ; 
		
		this.rg.reSeedSecure() ;
		
		super.initialiseState(initialTime) ;
		
		Duration d1 = new Duration (
							this.initialDelay, 
							this.getSimulatedTimeUnit() ) ;
		Time t = this.getCurrentStateTime().add(d1) ; 
		System.err.println("scheduling open at " + t.getSimulatedTime()) ;
		this.scheduleEvent(new OpenDoorFridge(t)) ;
		
		this.nextTimeAdvance = this.timeAdvance() ; 
		this.timeOfNextEvent = 
					this.getCurrentStateTime().add(this.nextTimeAdvance) ; 
	}
	
	
	@Override
	public Duration 				timeAdvance () {
		System.out.println("FridgeUserModel::timeAdvance") ;
		return super.timeAdvance() ;
	}
	
	@Override
	public Vector<EventI> 			output () 
	{
		System.out.println("FridgeUserModel::output");
		
		assert ! this.eventList.isEmpty() ; 
		
		Vector<EventI> events = super.output() ; 
		
		assert events.size() == 1 ; 
		
		this.nextEvent = events.get(0).getClass() ; 
		
		this.logMessage("FridgeUserModel::output()" + 
								this.nextEvent.getCanonicalName()) ;
		
		return events ;
	}
	
	
	// every thing must be done here -- lifecycle 
	@Override
	public void 					userDefinedInternalTransition (
							Duration elapsedTime )
	{
		System.out.println("--\nFridgeUserMode::userDefinedInternalTransition\n--");
		Duration d ; 
		Time t ;
		if (this.nextEvent.equals(OpenDoorFridge.class)) {
			// why multiply by 2
			d = new Duration (
					2.0 * this.openDuration * this.rg.nextBeta(1.75 ,  1.75), 
					this.getSimulatedTimeUnit()) ; 
			t = this.getCurrentStateTime().add(d) ; 
			System.err.println("------------\n"
					+ "\n"
					+ "sending closedoor"
					+ "\n"
					+ "\n--------------");
			this.scheduleEvent(new CloseDoorFridge(t));
		}else 
		if (this.nextEvent.equals(CloseDoorFridge.class)) {
			if (this.counterNumberOfOpening == this.numberOfOpningPerDay) {
				this.counterNumberOfOpening = 0;
				this.gennopd();
				d = new Duration ( 
						2.0 * this.interDayDelay * this.rg.nextBeta(1.75 , 1.75) , 
						this.getSimulatedTimeUnit() ) ; 
				t = this.getCurrentStateTime().add(d) ;
			}else {
				d = new Duration ( 
						2.0 * this.meanTimeBetweenOpen * this.rg.nextBeta(1.75 , 1.75) , 
						this.getSimulatedTimeUnit() ) ; 
				t = this.getCurrentStateTime().add(d) ; 
			}
			this.scheduleEvent(new OpenDoorFridge(t));
			this.counterNumberOfOpening ++;
			
		}
		
	}
	
	//------------------------------------------------------------------------
	// specific methods
	//------------------------------------------------------------------------
	
	private void 					gennopd () 
	{
		this.numberOfOpningPerDay = (new SecureRandom()).nextInt (10) ; 
		this.numberOfOpningPerDay += 10 ; 
	}
	
}

































