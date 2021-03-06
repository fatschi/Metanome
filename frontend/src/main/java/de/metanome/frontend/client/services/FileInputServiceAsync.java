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

import de.metanome.backend.results_db.FileInput;

import java.util.List;


public interface FileInputServiceAsync {

  public void listCsvFiles(AsyncCallback<String[]> callback);

  void getFileInput(long id, AsyncCallback<FileInput> async);

  void storeFileInput(FileInput input, AsyncCallback<Void> async);

  void listFileInputs(AsyncCallback<List<FileInput>> async);

  void deleteFileInput(FileInput input, AsyncCallback<Void> async);
}
