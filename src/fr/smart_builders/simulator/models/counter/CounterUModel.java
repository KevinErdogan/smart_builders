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
	private Vector<EventI> events = new Vector<EventI>() ; 

	public CounterUModel(String uri, TimeUnit simulatedTimeUnit, SimulatorI simulationEngine) throws Exception {
		super(uri, simulatedTimeUnit, simulationEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector<EventI> output() {
		System.out.println("output Umodel");

		Vector<EventI> events = this.events;
		this.events.clear();
		
		return events;
	}

	@Override
	public Duration timeAdvance() {
		if (this.events.size() > 0) {
			return Duration.zero(this.getSimulatedTimeUnit()) ; 
		}
		return Duration.INFINITY ;
	}
	
	@Override
	public void userDefinedExternalTransition (Duration e) {
		Vector<EventI> currentEvents = this.getStoredEventAndReset() ; 
		
		Event ce = (Event) currentEvents.get(0) ; 
		
		ce.executeOn(this);
		
		super.userDefinedExternalTransition(e);
		
	}

	@Override
	public void giveConsumption() {
		System.out.println("received");
		this.events.add(
		new ConsumptionResponseEvent(
				this.getCurrentStateTime().add(
						new Duration(1, getSimulatedTimeUnit())), "haha", 2.0)) ; 
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
