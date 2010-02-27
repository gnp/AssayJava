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
        synchronized(this) {
        	if (this.thread != null) {
        		throw new IllegalStateException("An AssayerRunner may only be run one time!");
        	}
        	
	        this.thread = new Thread(this.assayer);
	        this.thread.start();
        }
    }

    public Thread getThread() {
    	return this.thread;
    }
    
    public void setAssayer(final Assayer assayer) {
        this.assayer = assayer;
    }

    /**
     * Joins the runner thread, catching InterruptedException and re-joining. When this method
     * returns, the runner thread should have truly completed.
     */
    public void joinThread() {
    	if (this.thread == null) {
    		throw new IllegalStateException("Cannot call joinThread() until after calling run()!");
    	}
    	
		while(true) {
			try {
				this.thread.join();
			}
			catch (InterruptedException e) {
				continue;
			}
			break;
		}
    }
    
}
