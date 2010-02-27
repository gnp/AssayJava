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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.exoprax.assay.input.ListOfMapsInput;
import org.exoprax.assay.output.ConsoleOutput;

public class AssayerRunnerTest extends TestCase {
	
	public void testJoinThreadBeforeRun() {
		final AssayerRunner runner = new AssayerRunner();
		
		try {
			runner.joinThread();
			fail("Should have thrown IllegalStateException");
		}
		catch (IllegalStateException e) {
			// no-op. expected.
		}
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
		
		final AssayerRunner runner = new AssayerRunner();
		runner.setAssayer(assayer);
		
		runner.run();
		
		assertNotNull(runner.getThread());
		
		runner.joinThread();
		
		/*
		 * Prove its OK to call joinThread() more than once.
		 */

		runner.joinThread();
		
		/*
		 * Prove its not OK to call run() more than once.
		 */
		
		try {
			runner.run();
			fail("Expected IllegalStateException");
		}
		catch (IllegalStateException e) {
			// no-op. expected
		}
	}
	
}
