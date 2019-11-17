package fr.smart_builders.component;

import java.util.concurrent.TimeUnit;

import fr.smart_builders.interfaces.ControlerCounterI;
import fr.smart_builders.interfaces.ControlerFridgeI;
import fr.smart_builders.interfaces.ControlerOwenI;
import fr.smart_builders.interfaces.ControlerTvI;
import fr.smart_builders.interfaces.EnergyProviderI;
import fr.smart_builders.port.ControlerCounterOutboundPort;
import fr.smart_builders.port.ControlerEproviderOutboundPort;
import fr.smart_builders.port.ControlerFridgeOutboundPort;
import fr.smart_builders.port.ControlerOwenOutboundPort;
import fr.smart_builders.port.ControlerTvOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentStartException;



@RequiredInterfaces ( required = {EnergyProviderI.class , 
									ControlerTvI.class, 
									ControlerFridgeI.class,
									ControlerCounterI.class, 
									ControlerOwenI.class
									})
public class 			Controler 
extends 		AbstractComponent 
{
	
	protected 	ControlerEproviderOutboundPort  solarpanel;	
	protected 	ControlerCounterOutboundPort	counter;
	protected 	ControlerFridgeOutboundPort		fridge;
	protected 	ControlerTvOutboundPort			tv;
	protected 	ControlerOwenOutboundPort		owen;
	
	
	
	protected 				Controler (
			String uri, 
			String solarpanel, 
			String counter, 
			String fridge, 
			String tv, 
			String owen
			) throws Exception
	{
		super (uri , 0 , 1);
		assert solarpanel != null;
		
		this.solarpanel = new ControlerEproviderOutboundPort(
							solarpanel, 
							this);
		this.solarpanel.localPublishPort();
		
		assert 	this.solarpanel.isPublished();
		
		
		
		
		this.counter = new ControlerCounterOutboundPort(
							counter, 
							this);
		this.counter.localPublishPort();
		
		assert 	this.counter.isPublished();
		
		
		
		this.fridge = new ControlerFridgeOutboundPort(
							fridge, 
							this);
		this.fridge.localPublishPort();
		
		assert	this.fridge.isPublished();
		
		this.owen = new ControlerOwenOutboundPort(
							owen, 
							this);
		this.owen.localPublishPort();
		
		assert this.owen.isPublished();
		
		
		
		this.tv = new ControlerTvOutboundPort(
							tv, 
							this);
		
		this.tv.localPublishPort();
		
		assert	this.tv.isPublished();
		
		
		
		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		
		this.tracer.setTitle("controler") ;
		this.tracer.setRelativePosition(1, 0) ;
		
	}
	
	
	protected void 			life_cycle () throws Exception 
	{
		this.logMessage("controler do control : \n");
		double generated = this.solarpanel.egergyProduiced();
		this.logMessage("produced : "+generated+"\n");
		double consumed = this.counter.immediateConsumption();
		this.logMessage("consumed : "+consumed+"\n");
		
		if (generated > consumed) {
			this.fridge.switchOn();
		}
		
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void 		run () {
						try {
							((Controler) this.getTaskOwner()).life_cycle();
						}catch (Exception e) {
							throw new RuntimeException (e);
						}
					}
				},
				1000 , TimeUnit.MILLISECONDS);
	}
	
	
	@Override 
	public void 			start () throws ComponentStartException 
	{
		this.logMessage("start conroler");
		super.start();
		this.scheduleTask (
				new AbstractComponent.AbstractTask () {
					@Override
					public void 		run () {
						try {
							((Controler) this.getTaskOwner()).life_cycle();
						}catch (Exception e) {
							throw new RuntimeException (e);
						}
					}
				},
				1000 , TimeUnit.MILLISECONDS);
	}
	
	
	@Override 
	public void 			finalise () throws Exception 
	{
		this.logMessage("stopping controler");
		this.printExecutionLogOnFile("controler");
		
		this.solarpanel.unpublishPort();
		this.fridge.unpublishPort();
		this.tv.unpublishPort();
		this.counter.unpublishPort();
		this.owen.unpublishPort();
		
		super.finalise();
		
	}
	
	
	

}
