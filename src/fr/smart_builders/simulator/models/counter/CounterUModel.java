package fr.smart_builders.simulator.models.counter;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import fr.smart_builders.simulator.models.counter.event.ConsumeEvent;
import fr.smart_builders.simulator.models.counter.event.ConsumptionResponseEvent;
import fr.smart_builders.simulator.simulation.ConsumerI;
import fr.sorbonne_u.devs_simulation.hioa.models.AtomicHIOAwithEquations;
import fr.sorbonne_u.devs_simulation.interfaces.SimulationReportI;
import fr.sorbonne_u.devs_simulation.models.annotations.ModelExternalEvents;
import fr.sorbonne_u.devs_simulation.models.events.Event;
import fr.sorbonne_u.devs_simulation.models.events.EventI;
import fr.sorbonne_u.devs_simulation.models.time.Duration;
import fr.sorbonne_u.devs_simulation.models.time.Time;
import fr.sorbonne_u.devs_simulation.simulators.interfaces.SimulatorI;

/**
 * DELETE THIS
 * @author yacine
 *
 */
@ModelExternalEvents (exported = {ConsumptionResponseEvent.class} , imported = {ConsumeEvent.class}) 
public class 					CounterUModel 
extends 						AtomicHIOAwithEquations
implements 						ConsumerI
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2666342110874639906L;
	public static final String URI = "CounterUModel" ; 
	private boolean triggedReadValue = false ;

	public CounterUModel(String uri, TimeUnit simulatedTimeUnit, SimulatorI simulationEngine) throws Exception {
		super(uri, simulatedTimeUnit, simulationEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector<EventI> output() {
		if (this.triggedReadValue) {
			System.out.println("reading is beeing sent");
			Duration d = new Duration(10, this.getSimulatedTimeUnit());
			Time t = this.getCurrentStateTime().add(d) ;
			EventI e = new ConsumptionResponseEvent(t, CounterUModel.URI, 2.0);
			Vector<EventI> ret = new Vector<EventI> () ;
			ret.add(e) ;
			this.triggedReadValue = false ; 
			return ret ;
		}else {
			return null ; 
		}
	}
	
	
	
	@Override
	public Duration timeAdvance() {
		if (this.triggedReadValue) {
			return Duration.zero(this.getSimulatedTimeUnit()) ; 
		}
		return Duration.INFINITY ;
	}
	
	@Override
	public void userDefinedExternalTransition (Duration e) {
		super.userDefinedExternalTransition(e) ;
		
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		Event ce = (Event) currentEvents.get(0) ; 
		
		ce.executeOn(this);
		
		super.userDefinedExternalTransition(e);
		
	}

	@Override
	public void giveConsumption() {
		System.err.println("give consumption");
		this.triggedReadValue = true ; 
	}
	
	@Override
	public SimulationReportI getFinalReport() throws Exception {
		return new SimulationReportI() {
			
			@Override
			public String getModelURI() {
				return "haha";
			}
		};
	}
	
	

}
