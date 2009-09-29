package org.exoprax.assay;

/**
 * Copyright (C) 2008 Gregor N. Purdy Sr. All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL TO GREGOR N. PURDY SR. DO NOT DUPLICATE OR DISTRIBUTE.
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
