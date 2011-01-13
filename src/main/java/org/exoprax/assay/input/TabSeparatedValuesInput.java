/*
 * Copyright 2006-2010 Gregor N. Purdy, Sr.
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
 *
 */

package org.exoprax.assay.input;

import java.io.BufferedReader;
import java.io.IOException;

import org.exoprax.assay.Assay;
import org.exoprax.assay.AssayInput;

public class TabSeparatedValuesInput implements AssayInput {
	
    private Assay assay;
    
    private BufferedReader bufferedReader;

    public TabSeparatedValuesInput() {
    	super();
    }
    
	public void setBufferedReader(final BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public void setAssay(final Assay assay) {
		this.assay = assay;
	}

	public void run() {
		try {
			final String [] columnNames = bufferedReader.readLine().split("\t");

            for (final String columnName : columnNames) {
                assay.addAttribute(columnName, "TEXT");
            }
			
			while (true) {
				final String line = bufferedReader.readLine();
				
				if (line == null) {
					break;
				}
				
				final String [] columnValues = line.split("\t");
				
				final int columnCount = (columnNames.length > columnValues.length) ? columnValues.length : columnNames.length;
				
				for (int i = 0; i < columnCount; ++i) {
					assay.addAttributeValue(columnNames[i], columnValues[i]);
				}
			}
		}
		catch (final IOException e) {
			throw new RuntimeException("IOException while reading Tab Separated Values input", e);
		}
	}

}
