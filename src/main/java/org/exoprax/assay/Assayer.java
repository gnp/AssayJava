package org.exoprax.assay;

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
public class Assayer implements Runnable {

    private AssayInput input;

    private AssayOutput output;

    private Assay assay;

    public Assayer() {
        super();
    }

    public Assay getAssay() {
        return this.assay;
    }

    public AssayInput getInput() {
        return this.input;
    }

    public AssayOutput getOutput() {
        return this.output;
    }

    public void run() {
        if (this.input == null) {
            throw new IllegalStateException("No input!");
        }

        if (this.output == null) {
            throw new IllegalStateException("No output!");
        }

        this.assay = new Assay();

        this.input.setAssay(this.assay);
        this.input.run();

        this.output.setAssay(this.assay);
        this.output.run();
    }

    public void setInput(final AssayInput input) {
        this.input = input;
    }

    public void setOutput(final AssayOutput output) {
        this.output = output;
    }

}
