package de.uni_potsdam.hpi.metanome.example_ucc_algorithm;

import java.util.ArrayList;
import java.util.List;

import de.uni_potsdam.hpi.metanome.algorithm_integration.ColumnCombination;
import de.uni_potsdam.hpi.metanome.algorithm_integration.ColumnIdentifier;
import de.uni_potsdam.hpi.metanome.algorithm_integration.algorithm_types.StringParameterAlgorithm;
import de.uni_potsdam.hpi.metanome.algorithm_integration.algorithm_types.UniqueColumnCombinationsAlgorithm;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecification;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecificationCsvFile;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecificationString;
import de.uni_potsdam.hpi.metanome.algorithm_integration.result_receiver.CouldNotReceiveResultException;
import de.uni_potsdam.hpi.metanome.algorithm_integration.result_receiver.UniqueColumnCombinationResultReceiver;

public class ExampleAlgorithm implements UniqueColumnCombinationsAlgorithm, StringParameterAlgorithm {

	protected String path = null;
	protected UniqueColumnCombinationResultReceiver resultReceiver;

	@Override
	public List<ConfigurationSpecification> getConfigurationRequirements() {
		List <ConfigurationSpecification> configurationSpecification = new ArrayList<ConfigurationSpecification>();
		
		configurationSpecification.add(new ConfigurationSpecificationString("pathToInputFile"));
		configurationSpecification.add(new ConfigurationSpecificationCsvFile("input file"));
		
		return configurationSpecification;
	}

	@Override
	public void execute() throws CouldNotReceiveResultException {
		if (path != null) {
			resultReceiver.receiveResult(
					new ColumnCombination(
							new ColumnIdentifier("table1", "column1"), 
							new ColumnIdentifier("table2", "column2")));			
		}
	}

	@Override
	public void setResultReceiver(UniqueColumnCombinationResultReceiver resultReceiver) {
		this.resultReceiver = resultReceiver;
	}

	@Override
	public void setConfigurationValue(String identifier, String value) {
		if (identifier.equals("pathToInputFile")) {
			path = value;
		}		
	}
}
