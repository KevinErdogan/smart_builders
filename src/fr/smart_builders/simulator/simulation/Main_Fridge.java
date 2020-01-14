package fr.smart_builders.simulator.simulation;
//------------------------------------------------------------------

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import java.util.Map;
import fr.smart_builders.simulator.models.FridgeCoupledModel;
import fr.smart_builders.simulator.models.FridgeInnerTempController;
import fr.sorbonne_u.devs_simulation.architectures.ArchitectureI;
import fr.sorbonne_u.devs_simulation.simulators.SimulationEngine;
import fr.sorbonne_u.utils.PlotterDescription;


/**
 * The class <code>Main_Fridge</code> make the assembly of an 
 * architecture for a fridge an run the simulation
 * 
 * @author <p>Yacine Zaouzaou, Kevin Erdogan </p>
 */
public class 						Main_Fridge 
{

	// tooken from the SimulationMain in molene example

	static final String 			MAIN_FRIDGE = "MainFridge" ;
	
	public static int	ORIGIN_X = 100 ;
	public static int	ORIGIN_Y = 0 ;
	
	public static int	getPlotterWidth()
	{
		int ret = Integer.MAX_VALUE ;
		GraphicsEnvironment ge =
						GraphicsEnvironment.getLocalGraphicsEnvironment() ;
		GraphicsDevice[] gs = ge.getScreenDevices() ;
		for (int i = 0; i < gs.length; i++) {
			DisplayMode dm = gs[i].getDisplayMode() ;
			int width = dm.getWidth() ;
			if (width < ret) {
				ret = width ;
			}
		}
		return (int) (0.25 * ret) ;
	}

	public static int	getPlotterHeight()
	{
		int ret = Integer.MAX_VALUE ;
		GraphicsEnvironment ge =
						GraphicsEnvironment.getLocalGraphicsEnvironment() ;
		GraphicsDevice[] gs = ge.getScreenDevices() ;
		for (int i = 0; i < gs.length; i++) {
			DisplayMode dm = gs[i].getDisplayMode() ;
			int height = dm.getHeight() ;
			if (height < ret) {
				ret = height ;
			}
		}
		return (int) (0.2 * ret) ;
	}
	
	// taken from the SimulationMain in molene example
	
	
	
	public static void main (String ... args) {
		try {
			
			// constructing the fridge model 
			// it uses a fridge and simple controller for inner temp
			
			ArchitectureI a = FridgeCoupledModel.build() ; 
			
			SimulationEngine se = 
					a.constructSimulator() ; 
			se.setDebugLevel(0);
			Map<String, Object> sp = new HashMap<>() ; 
			String mURI = FridgeInnerTempController.URI ; 
			sp.put(mURI+":"+PlotterDescription.PLOTTING_PARAM_NAME, 
					new PlotterDescription(
							"FridgeInnerTempControler",
							"Time (sec)",
							"run/stop",
							700,
							0,
							600,
							400)) ;
			
			
			se.setSimulationRunParameters(sp);
			SimulationEngine.SIMULATION_STEP_SLEEP_TIME = 0L ;
			long start = System.currentTimeMillis() ;
			se.doStandAloneSimulation(0.0, 5000.0) ;
			long end = System.currentTimeMillis() ;
			System.out.println(se.getFinalReport()) ;
			System.out.println("Simulation ends. " + (end - start)) ;
			Thread.sleep(1000000L);
			System.exit(0) ;

			
			// don't forget to put all simulation params
			// until this point we have : 
			// -----
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
