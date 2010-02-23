package org.exoprax.assay;

public class AssayerRunner implements Runnable {

    private Assayer assayer;

    private Thread thread;

    public AssayerRunner() {
        super();
    }

    public Assayer getAssayer() {
        return this.assayer;
    }

    public void run() {
        System.out.println("Starting assay in its own thread...");
        
        this.thread = new Thread(this.assayer);
        this.thread.start();
    }

    public void setAssayer(final Assayer assayer) {
        this.assayer = assayer;
    }

}
