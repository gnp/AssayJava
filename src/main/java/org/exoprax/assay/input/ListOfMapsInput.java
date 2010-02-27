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

import java.util.List;
import java.util.Map;

import org.exoprax.assay.Assay;
import org.exoprax.assay.AssayInput;

public class ListOfMapsInput implements AssayInput {
	
    private Assay assay;
    private List<Map<String, String>> data;

    public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public ListOfMapsInput() {
    	super();
    }
    
	public void setAssay(final Assay assay) {
		this.assay = assay;
	}

	public void run() {
		if (assay == null) {
			throw new IllegalStateException("assay must not be null");
		}
		
		if (data == null) {
			throw new IllegalStateException("data must not be null");
		}
		
		for (final Map<String, String> map: data) {
			for (final Map.Entry<String, String> entry: map.entrySet()) {
				assay.addAttribute(entry.getKey(), "TEXT");
				assay.addAttributeValue(entry.getKey(), entry.getValue());
			}
		}
	}
	
}
