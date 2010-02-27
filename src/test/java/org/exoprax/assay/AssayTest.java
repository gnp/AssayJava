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

package org.exoprax.assay;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import junit.framework.TestCase;

import org.exoprax.assay.input.ListOfMapsInput;
import org.exoprax.assay.input.TabSeparatedValuesInput;
import org.exoprax.assay.output.ConsoleOutput;

public class AssayTest extends TestCase {

	public void testTitle() {
		final Assay assay = new Assay();
		assay.setTitle("title");
		assertEquals("title", assay.getTitle());
	}
	
	public void testNullData() {
		final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		
		Map<String, String> map;
		
		map = new HashMap<String, String>();
		map.put("value", null);
		map.put("key", "1");
		data.add(map);
		
		final ListOfMapsInput i = new ListOfMapsInput();
		i.setData(data);

		final ConsoleOutput o = new ConsoleOutput();
		o.setShowValuesLimit(10);
		assertEquals(10, o.getShowValuesLimit());
		o.setValueWidth(40);
		assertEquals(40, o.getValueWidth());
		
		final Assayer assayer = new Assayer();
		assayer.setInput(i);
		assayer.setOutput(o);
		assayer.run();
	}
	
	public void testSimpleData() {
		final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		
		Map<String, String> map;
		
		map = new HashMap<String, String>();
		map.put("date1", "Feb 26 2010");
		map.put("date2", "2010-03-04");
		map.put("date3", "2010-04-14");
		map.put("date4", "2010-14-04");
		map.put("date5", "03-04-2010");
		map.put("date6", "04-14-2010");
		map.put("date7", "14-04-2010");
		map.put("date8", "2010/03/04");
		map.put("date9", "2010/04/14");
		map.put("date10", "2010/14/04");
		map.put("timestamp", "2010-04-14 23:21:55.032");
		map.put("ssn", "123-45-6789");
		map.put("ein", "12-3456789");
		map.put("telephone1", "123-456-7890");
		map.put("telephone2", "(123)456-7890");
		map.put("zip", "12345-6789");
		map.put("boolean", "true");
		map.put("fill", "####################");
		map.put("email", "foo@example.com");
		map.put("text", "Some other body of text");
		map.put("data", "\u0000\u0001\u0002");
		data.add(map);
		
		final ListOfMapsInput i = new ListOfMapsInput();
		i.setData(data);

		final ConsoleOutput o = new ConsoleOutput();
		o.setShowValuesLimit(10);
		assertEquals(10, o.getShowValuesLimit());
		o.setValueWidth(40);
		assertEquals(40, o.getValueWidth());
		
		final Assayer assayer = new Assayer();
		assayer.setInput(i);
		assayer.setOutput(o);
		assayer.run();
	}

	public void testTrivialData() {
		final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		
		Map<String, String> map;
		
		map = new HashMap<String, String>();
		map.put("value", "FOO");
		map.put("key", "1");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("value", "FOO");
		map.put("key", "2");
		data.add(map);
		
		final ListOfMapsInput i = new ListOfMapsInput();
		i.setData(data);

		final ConsoleOutput o = new ConsoleOutput();
		o.setShowValuesLimit(10);
		assertEquals(10, o.getShowValuesLimit());
		o.setValueWidth(40);
		assertEquals(40, o.getValueWidth());
		
		final Assayer assayer = new Assayer();
		assayer.setInput(i);
		assayer.setOutput(o);
		assayer.run();
		
		assertNotNull(o.getAssay());
	}
	
	public void testRealData() throws FileNotFoundException, IOException {
		final FileInputStream fis = new FileInputStream("src/test/data/ucrcfullcatalogue_13Jan09.txt.gz");
		final GZIPInputStream gis = new GZIPInputStream(fis);
		final InputStreamReader r = new InputStreamReader(gis);
		final BufferedReader br = new BufferedReader(r);
		final TabSeparatedValuesInput i = new TabSeparatedValuesInput();
		i.setBufferedReader(br);

		final ConsoleOutput o = new ConsoleOutput();
		o.setShowValuesLimit(10);
		assertEquals(10, o.getShowValuesLimit());
		o.setValueWidth(40);
		assertEquals(40, o.getValueWidth());
		
		final Assayer assayer = new Assayer();
		assayer.setInput(i);
		assayer.setOutput(o);
		assayer.run();
		
		assertNotNull(o.getAssay());
	}

}
