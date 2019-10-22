package fr.sorbonne_u.smartgrid.coponenet;

import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.smartgrid.interfaces.ControlerCounterI;
import fr.sorbonne_u.smartgrid.interfaces.ControlerFridgeI;
import fr.sorbonne_u.smartgrid.interfaces.ControlerTvI;
import fr.sorbonne_u.smartgrid.interfaces.EnergyProviderI;
import fr.sorbonne_u.smartgrid.port.ControlerCounterOutboundPort;
import fr.sorbonne_u.smartgrid.port.ControlerEproviderOutboundPort;
import fr.sorbonne_u.smartgrid.port.ControlerFridgeOutboundPort;
import fr.sorbonne_u.smartgrid.port.ControlerTvOutboundPort;



@RequiredInterfaces ( required = {EnergyProviderI.class , 
									ControlerTvI.class, 
									ControlerFridgeI.class,
									ControlerCounterI.class
									})
public class 			Controler 
extends 		AbstractComponent 
{
	
	protected 	ControlerEproviderOutboundPort  solarpanel;	
	protected 	ControlerCounterOutboundPort	counter;
	protected 	ControlerFridgeOutboundPort		fridge;
	protected 	ControlerTvOutboundPort			tv;
	
	
	
	protected 				Controler (
			String uri, 
			String solarpanel, 
			String counter, 
			String fridge, 
			String tv
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
		
		super.finalise();
		
	}
	
	
	

}
