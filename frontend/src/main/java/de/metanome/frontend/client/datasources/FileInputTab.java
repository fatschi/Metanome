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

package de.metanome.frontend.client.datasources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingDataSource;
import de.metanome.backend.results_db.FileInput;
import de.metanome.frontend.client.TabContent;
import de.metanome.frontend.client.TabWrapper;
import de.metanome.frontend.client.helpers.FilePathHelper;
import de.metanome.frontend.client.services.FileInputService;
import de.metanome.frontend.client.services.FileInputServiceAsync;

import java.util.List;


public class FileInputTab extends FlowPanel implements TabContent {

  protected FlexTable fileInputList;
  protected FileInputEditForm editForm;
  private FileInputServiceAsync fileInputService;
  private DataSourcePage parent;
  private TabWrapper messageReceiver;

  /**
   * @param parent the parent page
   */
  public FileInputTab(DataSourcePage parent) {
    this.fileInputService = com.google.gwt.core.shared.GWT.create(FileInputService.class);
    this.parent = parent;

    this.fileInputList = new FlexTable();
    this.fileInputList = new FlexTable();
    this.editForm = new FileInputEditForm(this);

    this.addFileInputsToTable(this);
  }

  private void addEditForm() {
    this.add(new HTML("<hr>"));
    this.add(new HTML("<h3>Add a new File Input</h3>"));
    this.add(this.editForm);
  }

  /**
   * Gets all File Inputs available in the database and adds them to the table.
   *
   * @param panel the parent widget of the table
   */
  public void addFileInputsToTable(final FlowPanel panel) {
    fileInputService.listFileInputs(
        new AsyncCallback<List<FileInput>>() {
          @Override
          public void onFailure(Throwable throwable) {
            panel.add(new Label("There are no File Inputs yet."));
            addEditForm();
          }

          @Override
          public void onSuccess(List<FileInput> fileInputs) {
            listFileInputs(fileInputs);
            addEditForm();
          }
        });
  }

  /**
   * Lists all given file inputs in a table.
   *
   * @param inputs the file inputs
   */
  protected void listFileInputs(List<FileInput> inputs) {
    this.fileInputList.setHTML(0, 0, "<b>File Name</b>");
    this.fileInputList.setHTML(0, 1, "<b>Comment</b>");

    for (final FileInput input : inputs) {
      this.addFileInputToTable(input);
    }

    this.add(new HTML("<h3>List of all File Inputs</h3>"));
    this.add(this.fileInputList);
  }

  /**
   * Adds the given file input to the table.
   *
   * @param input the file input, which should be added.
   */
  public void addFileInputToTable(final FileInput input) {
    int row = this.fileInputList.getRowCount();

    Button deleteButton = new Button("Delete");
    deleteButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        fileInputService.deleteFileInput(input,
                                         getDeleteCallback(input));
      }
    });

    Button runButton = new Button("Analyze");
    runButton.setTitle(String.valueOf(input.getId()));
    runButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        parent.callRunConfiguration(convertFileInputToDataSource(input));
      }
    });

    this.fileInputList.setWidget(row, 0, new HTML(FilePathHelper.getFileName(input.getFileName())));
    this.fileInputList.setText(row, 1, input.getComment());
    this.fileInputList.setWidget(row, 2, runButton);
    this.fileInputList.setWidget(row, 3, deleteButton);
  }

  /**
   * Converts a file input into a ConfigurationSettingDataSource
   *
   * @param input the file input
   * @return the ConfigurationSettingDataSource from the given file input
   */
  private ConfigurationSettingDataSource convertFileInputToDataSource(FileInput input) {
    ConfigurationSettingFileInput setting = new ConfigurationSettingFileInput();

    setting.setFileName(input.getFileName());
    setting.setEscapeChar(input.getEscapechar());
    setting.setHeader(input.isHasHeader());
    setting.setIgnoreLeadingWhiteSpace(input.isIgnoreLeadingWhiteSpace());
    setting.setQuoteChar(input.getQuotechar());
    setting.setSeparatorChar(input.getSeparator());
    setting.setSkipDifferingLines(input.isSkipDifferingLines());
    setting.setSkipLines(input.getSkipLines());
    setting.setStrictQuotes(input.isStrictQuotes());

    return setting;
  }

  /**
   * Forwards the command to update the data sources on the run configuration page to the data
   * source page.
   */
  public void updateDataSourcesOnRunConfiguration() {
    this.parent.updateDataSourcesOnRunConfiguration();
  }

  /**
   * Creates the callback for the delete call.
   *
   * @param input The file input, which should be deleted.
   * @return The callback
   */
  protected AsyncCallback<Void> getDeleteCallback(final FileInput input) {
    return new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable throwable) {
        messageReceiver.addError("Could not delete the file input: " + throwable.getMessage());
      }

      @Override
      public void onSuccess(Void aVoid) {
        fileInputList.removeRow(findRow(input));
      }
    };
  }

  /**
   * Find the row in the table, which contains the given file input.
   * @param input the file input
   * @return the row number
   */
  private int findRow(FileInput input) {
    int row = 0;
    String fileName = FilePathHelper.getFileName(input.getFileName());

    while (row < this.fileInputList.getRowCount()) {
      HTML fileWidget = (HTML) this.fileInputList.getWidget(row, 0);

      if (fileWidget != null && fileName.equals(fileWidget.getText())) {
        return row;
      }
      row++;
    }
    return -1;
  }

  @Override
  public void setMessageReceiver(TabWrapper tab) {
    this.messageReceiver = tab;
    this.editForm.setMessageReceiver(tab);
  }
}
