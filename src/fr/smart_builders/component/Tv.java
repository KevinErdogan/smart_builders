package fr.smart_builders.component;

import java.util.concurrent.TimeUnit;

import fr.smart_builders.interfaces.MonitorI;
import fr.smart_builders.interfaces.TvI;
import fr.smart_builders.port.MonitorInboundPort;
import fr.smart_builders.port.TvInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;


/**
 * The class <code>Tv</code> represent a TV in real life
 * it has only one schedulable thread and no simple thread
 * the controller can't take the control of it only a human
 * can
 * 
 * @authors <p>Zaouzaou Yacine , Erdogan Kevin </p>
 *
 */

@OfferedInterfaces(offered = {TvI.class})
public class 		Tv 
extends 		AbstractComponent
{
	
	
	protected final static double RUN_CONSUMPTION = 450;
	protected final static double STOP_CONSUMPTION = 0;
	
	protected boolean isOn;
	
	
	
	
	protected 				Tv (
			String uri, 
			String inboundport, 
			String minboundport
			) throws Exception
	{
		super (uri , 0 , 1);
		
		PortI ibp = new TvInboundPort(
									inboundport, 
									this);
		ibp.publishPort();
		
		assert 	ibp.isPublished();
		
		PortI obp = new MonitorInboundPort(
									minboundport, 
									this);
		obp.publishPort();
		
		assert 	obp.isPublished();
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		this.tracer.setTitle("Smart tv") ;
		this.tracer.setRelativePosition(2, 1) ;
		
	
	}
	
	
	public double getConsumption() {
		if (isOn) 
			return RUN_CONSUMPTION;
		return STOP_CONSUMPTION;
	}
	
	
	private void 				life_cycle () {
		
		String message = "";
		if (isOn) {
			message = "i'm on";
		}else {
			message = "i'm off";
		}
		
		this.logMessage(message);
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {			
					@Override
					public void 		run () {
						try {
							((Tv) this.getTaskOwner()).life_cycle ();
						}catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				},
				1000, TimeUnit.MILLISECONDS);
	}
	
	
	
	@Override
	public void 		start () throws ComponentStartException
	{
		
		this.logMessage("stating smart tv");
		this.isOn = true;
		super.start();
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {			
					@Override
					public void 		run () {
						try {
							((Tv) this.getTaskOwner()).life_cycle ();
						}catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				},
				1000, TimeUnit.MILLISECONDS);
	}
	
	@Override 
	public void 		finalise () throws Exception 
	{
		this.logMessage("stopping smart tv");
		this.printExecutionLogOnFile("tv");
		super.finalise();
	}
	
	@Override
	public void 		shutdown () throws ComponentShutdownException
	{
		try {
			PortI [] pt = this.findPortsFromInterface(TvI.class);
			pt[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public void 		shutdownNow () throws ComponentShutdownException
	{
		try {
			PortI [] pt = this.findPortsFromInterface(TvI.class);
			pt[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}
	
	
	
	

}
