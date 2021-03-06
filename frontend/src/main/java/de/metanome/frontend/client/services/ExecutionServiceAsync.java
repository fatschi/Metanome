/*
 * Copyright 2014 by the Metanome project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.metanome.frontend.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.results.Result;

import java.util.ArrayList;
import java.util.List;

public interface ExecutionServiceAsync {

  public void executeAlgorithm(String algorithmName,
                               String executionIdentifier,
                               List<ConfigurationRequirement> parameters,
                               AsyncCallback<Long> callback);

  public void fetchNewResults(String algorithmName, AsyncCallback<ArrayList<Result>> callback);

  public void fetchProgress(String executionIdentifier, AsyncCallback<Float> callback);
}
