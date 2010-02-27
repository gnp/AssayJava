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

import junit.framework.TestCase;

public class AssayerTest extends TestCase {

	public void testBadArgs() {
		final Assayer assayer = new Assayer();
		
		assertNull(assayer.getInput());
		assertNull(assayer.getOutput());
		assertNull(assayer.getAssay());
		
		/*
		 * Try with no 'input':
		 */
		try {
			assayer.run();
			fail("Should have thrown an IllegalStateException when not fully set up");
		}
		catch (IllegalStateException e) {
			// no-op. this is expected
		}
		
		/*
		 * Try with 'input' set to a trival implementation, and 'output' not set:
		 */
		
		assayer.setInput(new AssayInput() {

			public void setAssay(Assay assay) {
				// no-op stub
			}

			public void run() {
				// no-op stub
			} });

		assertNotNull(assayer.getInput());
		
		try {
			assayer.run();
			fail("Should have thrown an IllegalStateException when not fully set up");
		}
		catch (IllegalStateException e) {
			// no-op. this is expected
		}
		
		/*
		 * Try with 'input' and 'output' both set to trival implementations:
		 */
		
		assayer.setOutput(new AssayOutput() {

			public void setAssay(Assay assay) {
				// no-op stub
			}

			public void run() {
				// no-op stub
			} });
		
		assayer.run();
		
		assertNotNull(assayer.getAssay());
	}
	
}
