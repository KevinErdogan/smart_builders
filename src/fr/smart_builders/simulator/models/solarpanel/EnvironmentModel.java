package fr.smart_builders.simulator.models.solarpanel;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import org.apache.commons.math3.random.RandomDataGenerator;
import fr.smart_builders.simulator.models.events.solarpanel.SolarBrightnessChanged;
import fr.sorbonne_u.devs_simulation.es.models.AtomicES_Model;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;
import fr.sorbonne_u.devs_simulation.utils.StandardLogger;

//-----------------------------------------------------------------------------------------------------------
// equivalent UserModel
/**
 * The class <code>EnvironmentModel</code> implements a simple weather simulation
 * very simple it is reduced to changing value of sun brightness 
 * 
 * @authors <p>Yacine Zaouzaou , Kevin Erdogan</p>
 */
@ModelExternalEvents (exported = {SolarBrightnessChanged.class})
public class 								EnvironmentModel 
extends 									AtomicES_Model
{

	//-----------------------------------------------------------------------------------------
	// Constants and variables
	//-----------------------------------------------------------------------------------------
	
	/** generated */
	private static final long 				serialVersionUID = -2464935416870219766L;
	
	public static final String 				URI = "EnvironementModel" ; 
	
	// chosen arbitrary 
	public static final double 				DAY = 1400 ; 
	
	private double 							meanDayTime ; 
	
	private double 							meanBrightnessLevel ; 
	
	private double 							meanBrihtnessLowLevel ;
	
	private double 							meanSunshineHighTime ; 
	
	private double 							meanSunshineLowTime ; 
	
	private int 							cycle ; 
	
	protected final RandomDataGenerator		rg ; 
	
	private double 							lastDayTime ; 
	private double 							lowTime ; 
	private double 							highTime ; 
	
	//-----------------------------------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------------------------------
	public 									EnvironmentModel(
									String uri, 
									TimeUnit simulatedTimeUnit, 
									SimulatorI simulationEngine) 
											throws Exception 
	{
		super(uri, simulatedTimeUnit, simulationEngine);
		this.rg = new RandomDataGenerator() ; 
		this.setLogger(new StandardLogger()) ;
	}
	
	//-----------------------------------------------------------------------------------------
	// methods (Override)
	//-----------------------------------------------------------------------------------------
	
	@Override
	public void 							initialiseState (Time initialTime)
	{
		this.meanDayTime = 700 ; 
		this.lastDayTime = 0 ; 
		this.meanBrightnessLevel = 10000 ; 
		this.meanBrihtnessLowLevel = 5000 ; 
		this.meanSunshineLowTime = 150 ; 
		this.meanSunshineHighTime = 300 ; 
		this.rg.reSeedSecure();  
		this.cycle = 1 ; 
		super.initialiseState (initialTime) ; 
		
		this.initDay();
		
		// schedule first event
		
		double day = this.nextDouble(this.meanDayTime) ; 
		double brightness = this.nextDouble(this.meanBrightnessLevel) ; 
		this.lastDayTime = day ; 
		this.scheduleEvent( new SolarBrightnessChanged(this.getCurrentStateTime(), brightness ));
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
		
		return events ; 
	}
	

	
	@Override
	public void 							userDefinedInternalTransition (Duration elapsedTime) 
	{
		Duration d ; 
		Time t ; 
		double sunshineIntensity ;
		double dur ; 
		
		if (this.cycle == 0) {	// schedule high sunshine 
			// define all variables representing a day
			sunshineIntensity = this.nextDouble(this.meanBrightnessLevel) ;
			
			dur = this.lowTime ; 
			if (dur <= 0 ) { dur = 1  ;} 
			
			d = new Duration(dur, this.getSimulatedTimeUnit()) ;
			
			
			t = this.currentStateTime.add(d) ; 
			this.scheduleEvent(new SolarBrightnessChanged (t , sunshineIntensity)) ;
			
		} else if (this.cycle == 1) {	// schedule low sunshine 
			sunshineIntensity = this.nextDouble(this.meanBrihtnessLowLevel) ; 
			
			dur = this.lastDayTime - (this.highTime + this.lowTime) ; 
			
			if (dur <= 0 ) { dur = 1  ;} 
			d = new Duration(dur, this.getSimulatedTimeUnit()) ;
			
			t = this.currentStateTime.add(d) ; 
			this.scheduleEvent(new SolarBrightnessChanged (t , sunshineIntensity)) ;
			
		} else if (this.cycle == 2) {	// schedule night 
			dur = EnvironmentModel.DAY - this.lastDayTime ; 
			
			if (dur <= 0 ) { dur = 1  ;} 
			d = new Duration(dur , this.getSimulatedTimeUnit() ) ; 

			
			t = this.getCurrentStateTime().add(d) ; 
			this.scheduleEvent(new SolarBrightnessChanged (t , 0));
		} else if (this.cycle == 3) {	// schedule low sunshine
			this.initDay();
			sunshineIntensity = this.nextDouble(this.meanBrihtnessLowLevel) ; 
			
			dur = this.highTime ; 
			
			if (dur <= 0 ) { dur = 1  ;} 
			
			d = new Duration(dur, this.getSimulatedTimeUnit()) ;

			
			t = this.currentStateTime.add(d) ; 
			this.scheduleEvent(new SolarBrightnessChanged (t , sunshineIntensity)) ;
		
			
		}
		// 4 phases in a day !! chosen arbitrary 
		this.cycle ++ ; 
		this.cycle = this.cycle % 4 ; 
	}
	
	private void 							initDay ()
	{
		this.lastDayTime = this.nextDouble(this.meanDayTime) ;
		this.lowTime = this.nextDouble(this.meanSunshineLowTime) ;
		this.highTime = this.nextDouble(this.meanSunshineHighTime) ;
		if (this.highTime > this.lastDayTime) {
			this.highTime = this.lastDayTime ; 
			this.lowTime = 0  ;
		}else if ((this.highTime + this.lowTime ) > this.lastDayTime ){
			this.lowTime = this.lastDayTime - this.highTime ;
		}
		
		if (this.lastDayTime >= EnvironmentModel.DAY) 
			initDay() ; 
	}
	
	
	
	@Override
	public SimulationReportI getFinalReport() throws Exception {
		// TODO Auto-generated method stub
		return new SimulationReportI() { 

			private static final long serialVersionUID = 5324289556420297352L;

			@Override
			public String getModelURI() {
				return EnvironmentModel.URI;
			}
		};
	}
	
	// just to simplify calling to nextBeta of 
	// RandomDataGenerator
	// because we are using same constants at every value generation 
	// we haven't look for more information about 
	// statistics of the weather
	private double nextDouble (double arround) 
	{
		return 2.0 * arround * this.rg.nextBeta(1.75, 1.75) ;
	}

	

}
//--------------------------------------------------------------------------------------------------------









































