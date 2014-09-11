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

package de.metanome.algorithm_integration.configuration;


/**
 * Concrete {@link ConfigurationSpecification} for booleans.
 *
 * @author Jakob Zwiener
 * @see ConfigurationSpecification
 */
public class ConfigurationSpecificationBoolean extends ConfigurationSpecification {

  private static final long serialVersionUID = -8167469173057966270L;

  public ConfigurationSettingBoolean[] settings;

  /**
   * Exists for GWT serialization.
   */
  public ConfigurationSpecificationBoolean() {
  }

  /**
   * Construct a {@link ConfigurationSpecificationBoolean}, requesting 1 value.
   *
   * @param identifier the specification's identifier
   */
  public ConfigurationSpecificationBoolean(String identifier) {
    super(identifier);
  }

  /**
   * Constructs a {@link ConfigurationSpecificationBoolean}, potentially requesting several values.
   *
   * @param identifier     the specification's identifier
   * @param numberOfValues the number of values expected
   */
  public ConfigurationSpecificationBoolean(String identifier,
                                           int numberOfValues) {
    super(identifier, numberOfValues);
  }

  @Override
  public ConfigurationSettingBoolean[] getSettings() {
    return settings;
  }

  public void setSettings(ConfigurationSettingBoolean... settings) {
    // TODO check number
    this.settings = settings;
  }
}