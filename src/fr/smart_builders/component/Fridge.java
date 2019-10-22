package fr.sorbonne_u.smartgrid.coponenet;

import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;
import fr.sorbonne_u.smartgrid.interfaces.FridgeI;
import fr.sorbonne_u.smartgrid.interfaces.MonitorI;
import fr.sorbonne_u.smartgrid.port.FridgeInboundPort;
import fr.sorbonne_u.smartgrid.port.MonitorInboundPort;

//----------------------------------------------------------------------------------
/**
 * 
 * 
 * 
 * 
 * 
 * <p> Created on : 2019-10-17 </p>
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

@OfferedInterfaces ( offered = {FridgeI.class} )
public class 					Fridge  
extends 		AbstractComponent
{
	
	private static double RUN_CONSUMPTION = 300;
	private static double STOP_CONSUMPTION = 0;
	private static double MAX_TEMP = 7;
	private static double MIN_TEMP = 2;
	private static double STEP = 0.2;
	
	
	private double temperature;
	private boolean isOn;
	private boolean canRun;
	
	
	
	

	protected Fridge				(
			String uri, 
			String inboundPort,
			String minboundport
			) throws Exception 
	{
		super(uri, 0 , 2);
		assert uri != null;
		assert inboundPort != null;
		assert minboundport != null;
		
		PortI pibp = new FridgeInboundPort(inboundPort, this);
		pibp.publishPort();
		
		assert 	pibp.isPublished();
		
		PortI mibp = new MonitorInboundPort(minboundport, this);
		mibp.publishPort();
		
		assert	mibp.isPublished();
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		
		this.tracer.setTitle("Smart fridge") ;
		this.tracer.setRelativePosition(1, 2) ;
	}
	
	
	
	
	
	public boolean forbidStart () {
		this.canRun = false;
		return ! this.canRun;
	}
	
	public boolean allowStart () {
		this.canRun = true;
		return this.canRun;
	}
	
	
	public double getConsumption () {
		if (isOn) 
			return Fridge.RUN_CONSUMPTION; 
		return Fridge.STOP_CONSUMPTION;
	}

	
	
	
	private boolean mustRun () {
		if (! this.isOn)
			return this.temperature >= MAX_TEMP;
		return false;
	}
	
	private boolean mustStop () {
		if ( this.isOn) {
			return this.temperature <= MIN_TEMP;
		}
		return false;
	}
	
	private void lifeCycle () {
		if (canRun) {
			if (mustRun()) {
				isOn = true;
			}
			if (mustStop()) {
				isOn = false;
			}
			if (isOn) {
				this.temperature -= STEP;
			}else {
				this.temperature += STEP;
			}
			
		}else {
			if (isOn) { 
				isOn = false;
			}
			this.temperature += 0.2;
		}
		
		this.logMessage("ineer temp : "+this.temperature
				+"\nis on "+isOn+"\n");
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((Fridge) this.getTaskOwner()).lifeCycle();
						}catch (Exception e) {
							throw new RuntimeException ();
						}
					}
				}, 
				1000 , TimeUnit.MILLISECONDS);
	}
	
	
	
	@Override
	public void start() throws ComponentStartException {
		super.start();
		this.logMessage("starting smart fridge");
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((Fridge) this.getTaskOwner()).lifeCycle();
						}catch (Exception e) {
							throw new RuntimeException (e);
						}
					}
				}, 
				1000 , TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void			finalise () throws Exception 
	{
		this.logMessage("stopping smart fridge");
		this.printExecutionLogOnFile("fridge");
		
		super.finalise();
	}
	
	
	@Override
	public void 				shutdown () throws ComponentShutdownException 
	{
		try {
			PortI [] pf = this.findPortsFromInterface(FridgeI.class);
			pf[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
			
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public void 				shutdownNow() throws ComponentShutdownException
	{
		try {
			PortI[] pf = this.findPortsFromInterface(FridgeI.class);
			pf[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}
	
}
