package fr.smart_builders.component;

import java.util.concurrent.TimeUnit;

import fr.smart_builders.interfaces.MonitorI;
import fr.smart_builders.interfaces.SolarPanelI;
import fr.smart_builders.port.MonitorInboundPort;
import fr.smart_builders.port.PanelInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;

//--------------------------------------------------------------------


/**
 * 
 * <p>Created on : 2019-20-20</p>
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin</p>
 *
 */

@OfferedInterfaces (offered = {SolarPanelI.class})
public class 			SolarPanel 
extends 		AbstractComponent 
{
	
	private int stepper_to_remove = 0; 			// used to simulate a stop of production after 80 seconsds
	
	private double energyproduced = 1000;
	
	
	protected 				SolarPanel (
				String uri, 
				String inboundport, 
				String minboudport
			)throws Exception
	{
		super (uri , 0 , 2);
		
		
		//create ports 
		
		PortI ibp = new PanelInboundPort(
						inboundport, 
						this);
		ibp.publishPort();
		
		assert	ibp.isPublished();
		
		PortI mibp = new MonitorInboundPort(
						minboudport, 
						this);
		mibp.publishPort();
		
		assert 	mibp.isPublished();
		
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		
		this.tracer.setTitle("solar panel") ;
		this.tracer.setRelativePosition(2, 0) ;
		
		
	}
	
	
	// get a function that computes the value of the production
	public double energy () {
		
		return energyproduced;
	}
	
	private void lifeCycle () {
		
		this.logMessage("I produced "+this.energyproduced+" now !");
		this.stepper_to_remove ++;
		if (this.stepper_to_remove > 80) {
			this.energyproduced = 0;
		}
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((SolarPanel) this.getTaskOwner()).lifeCycle();
						}catch (Exception e) {
							throw new RuntimeException ();
						}
					}
				}, 
				1000 , TimeUnit.MILLISECONDS);
	}
	
	
	@Override
	public void 			start () throws ComponentStartException 
	{
		this.logMessage("starting solar panel");
		super.start();
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((SolarPanel) this.getTaskOwner()).lifeCycle();
						}catch (Exception e) {
							throw new RuntimeException ();
						}
					}
				}, 
				1000 , TimeUnit.MILLISECONDS);

	}
	
	@Override
	public void 			finalise () throws Exception 
	{
		this.logMessage("stopping solar panel");
		this.printExecutionLogOnFile("solar panel");
		super.finalise();
	}
	
	
	@Override
	public void 			shutdown () throws ComponentShutdownException 
	{
		try {
			PortI [] ps = this.findPortsFromInterface(SolarPanelI.class);
			ps[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	
	@Override
	public void 			shutdownNow () throws ComponentShutdownException 
	{
		try {
			PortI [] ps = this.findPortsFromInterface(SolarPanelI.class);
			ps[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

	
}
