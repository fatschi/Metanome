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

package de.metanome.backend.results_db;

import de.metanome.algorithm_integration.configuration.DbSystem;
import de.metanome.test_helper.EqualsAndHashCodeTester;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link de.metanome.backend.results_db.TableInput}
 *
 * @author Jakob Zwiener
 */
public class TableInputTest {

  /**
   * Test method for {@link de.metanome.backend.results_db.TableInput#store()} and {@link
   * de.metanome.backend.results_db.TableInput#retrieve(long)} <p/> TableInputs should be storable
   * and retrievable by id.
   */
  @Test
  public void testPersistence() throws EntityStorageException {
    // Setup
    HibernateUtil.clear();

    // Expected values
    TableInput expectedTableInput = new TableInput();
    String expectedTableName = "some table name";
    expectedTableInput.setTableName(expectedTableName);

    // Execute functionality
    assertSame(expectedTableInput, expectedTableInput.store());
    long id = expectedTableInput.getId();
    TableInput actualTableInput = TableInput.retrieve(id);
    String actualTableName = actualTableInput.getTableName();

    // Check result
    assertEquals(expectedTableInput, actualTableInput);
    assertEquals(expectedTableName, actualTableName);

    // Cleanup
    HibernateUtil.clear();
  }

  /**
   * Test method for {@link de.metanome.backend.results_db.TableInput#equals(Object)} and {@link
   * de.metanome.backend.results_db.TableInput#hashCode()} <p/> Note that the equals and hashCode
   * methods are inherited from {@link de.metanome.backend.results_db.Input}.
   */
  @Test
  public void testEqualsAndHashCode() {
    // Setup
    long id = 42;
    TableInput tableInput = new TableInput()
        .setId(id);
    TableInput equalTableInput = new TableInput()
        .setId(id);
    TableInput notEqualTableInput = new TableInput()
        .setId(23);

    // Execute functionality
    // Check result
    new EqualsAndHashCodeTester<TableInput>()
        .performBasicEqualsAndHashCodeChecks(tableInput, equalTableInput, notEqualTableInput);
  }

  /**
   * Test method for {@link de.metanome.backend.results_db.TableInput#retrieveAll()}
   */
  @Test
  public void testRetrieveAll() throws EntityStorageException {
    // Setup
    HibernateUtil.clear();

    // Expected values
    Input expectedInput = new TableInput()
        .store();

    // Execute functionality
    List<Input> actualInputs = TableInput.retrieveAll();

    // Check result
    assertThat(actualInputs, IsIterableContainingInAnyOrder
        .containsInAnyOrder(expectedInput));

    // Cleanup
    HibernateUtil.clear();
  }

  @Test
  public void testGetIdentifier() {
    // Setup
    String tableName = "tableName";

    DatabaseConnection connection = new DatabaseConnection();
    connection.setUrl("url");
    connection.setPassword("pwd");
    connection.setSystem(DbSystem.DB2);
    connection.setUsername("user");

    String expectedIdentifier = "tableName; url; user; DB2";

    TableInput input = new TableInput();
    input.setDatabaseConnection(connection);
    input.setTableName("tableName");

    // Execute
    String actualIdentifier = input.getIdentifier();

    // Check
    assertEquals(expectedIdentifier, actualIdentifier);
  }
}
