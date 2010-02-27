package org.exoprax.assay.input;

import java.io.BufferedReader;
import java.io.IOException;

import org.exoprax.assay.Assay;
import org.exoprax.assay.AssayInput;

public class TabSeparatedValuesInput implements AssayInput {
	
    private Assay assay;
    
    private BufferedReader bufferedReader;

	public void setBufferedReader(final BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public void setAssay(final Assay assay) {
		this.assay = assay;
	}

	public void run() {
		try {
			final String [] columnNames = bufferedReader.readLine().split("\t");
			
			for (int i = 0; i < columnNames.length; ++i) {
				assay.addAttribute(columnNames[i], "TEXT");
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
