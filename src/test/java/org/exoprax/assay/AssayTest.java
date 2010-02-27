package org.exoprax.assay;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.exoprax.assay.input.TabSeparatedValuesInput;
import org.exoprax.assay.output.ConsoleOutput;

import junit.framework.TestCase;

/**
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
public class AssayTest extends TestCase {

	public void testAssay() throws FileNotFoundException, IOException {
		final FileInputStream fis = new FileInputStream("src/test/data/ucrcfullcatalogue_13Jan09.txt.gz");
		final GZIPInputStream gis = new GZIPInputStream(fis);
		final InputStreamReader r = new InputStreamReader(gis);
		final BufferedReader br = new BufferedReader(r);
		final TabSeparatedValuesInput i = new TabSeparatedValuesInput();
		i.setBufferedReader(br);

		final ConsoleOutput o = new ConsoleOutput();
		
		final Assayer assayer = new Assayer();
		assayer.setInput(i);
		assayer.setOutput(o);
		assayer.run();
	}

}
