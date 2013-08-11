package de.uni_potsdam.hpi.metanome.frontend.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import de.uni_potsdam.hpi.metanome.frontend.client.tabs.AlgorithmTab;
import de.uni_potsdam.hpi.metanome.frontend.client.tabs.FunctionalDependencyTab;

/**
 * Tests for the algorithm specific pages (tabs)
 */
public class AlgorithmTabTest extends GWTTestCase {

	@Test
	public void testAddJarChooser(){
		//Setup
		AlgorithmTab algoTab = new FunctionalDependencyTab();
		
		//Execute
		algoTab.addJarChooser("Algo1", "Algo2", "Algo3");
		
		//Check
		assertEquals(1, algoTab.getWidgetCount());
	}
	
	@Test
	public void testJarChooserSubmit() {
		//Setup
		AlgorithmTab algoTab = new FunctionalDependencyTab();
		algoTab.addJarChooser("Algo1", "Algo2", "Algo3");
		
		//Execute
		algoTab.getJarChooser().submit();
		
		//Check
		assertEquals(2, algoTab.getWidgetCount());
		assertNotNull(algoTab.getParameterTable());
	}

	@Override
	public String getModuleName() {
		return "de.uni_potsdam.hpi.metanome.frontend.Hello";
	}

}