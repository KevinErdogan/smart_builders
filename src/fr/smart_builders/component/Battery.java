package fr.smart_builders.component;

import java.util.concurrent.TimeUnit;

import fr.smart_builders.interfaces.BatteryI;
import fr.smart_builders.interfaces.MonitorI;
import fr.smart_builders.port.BatteryInboundPort;
import fr.smart_builders.port.MonitorInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.ports.PortI;

//----------------------------------------------------------------------------------------

/**
 * 
 * @authors <p> Yacine Zaouzaou , Kevin Erdogan </p>
 *
 */

@OfferedInterfaces ( offered = { BatteryI.class} )
public class 					Battery 
extends					AbstractComponent 
{
	
	private static double MAX_LEVEL = 100000;
	private static double MAX_PUISS = 1000;
	private static double MAX_PUISS_CHARGE = 1000;
	
	
	
	private double level = 50000;
	private double consume = 0;
	
	
	
	
	
	protected 				Battery (
				String uri,
				String inboundport,
				String minboundport
			
			) throws Exception
	{
		super (uri, 0 , 1);
		assert uri != null;
		assert inboundport != null;
		assert minboundport != null;
		
		PortI pibp = new BatteryInboundPort(inboundport, this);
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
		
		this.tracer.setTitle("battery") ;
		this.tracer.setRelativePosition(3, 1) ;		
	}
	
	public double level () {
		return this.level;
	}
	
	public boolean charge (double value ) {
		if (! this.isFul()) {
			double toAdd = value % MAX_PUISS_CHARGE;
			if (this.level + toAdd <= MAX_LEVEL) {
				this.level += toAdd;
			}else {
				this.level = MAX_LEVEL;
			}
			this.consume = toAdd;
			return true;
		}
		return false;
	}
	
	public double consumption () {
		return this.consume;
	}
	
	public boolean discharge (double quatity) {
		if (! isEmpty()) {
			double toGive = quatity % MAX_PUISS;
			if (this.level - toGive >= 0) {
				this.level -= toGive;
			}else {
				this.level = 0;
			}
			return true;
		}
		return false;
	}
	
	
	private boolean isFul () {
		return this.level >= MAX_LEVEL;
	}
	
	private boolean isEmpty () {
		return this.level <= 0;
	}
	
	private void lifeCycle () {
		this.logMessage("my level : "+this.level);
		this.consume = 0;
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((Battery) this.getTaskOwner()).lifeCycle();
						}catch (Exception e) {
							throw new RuntimeException ();
						}
					}
				}, 
				1000 , TimeUnit.MILLISECONDS);		
	}
	
	@Override
	public void start () throws ComponentStartException
	{
		super.start ();
		this.logMessage("starting battery");
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void run () {
						try {
							((Battery) this.getTaskOwner()).lifeCycle();
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
		this.logMessage("stopping battery");
		this.printExecutionLogOnFile("battery");
		
		super.finalise();
	}
	
	@Override
	public void 						shutdown () throws ComponentShutdownException
	{
		try {
			PortI [] pf = this.findPortsFromInterface(BatteryI.class);
			pf[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch ( Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public void 				shutdownNow() throws ComponentShutdownException
	{
		try {
			PortI[] pf = this.findPortsFromInterface(BatteryI.class);
			pf[0].unpublishPort();
			
			PortI [] pm = this.findPortsFromInterface(MonitorI.class);
			pm[0].unpublishPort();
		}catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}
}


























