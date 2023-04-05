/*
 * Decompiled with CFR 0.150.
 */
package org.libero.tools.worker;

import org.libero.tools.worker.MultiWorker;

public abstract class SingleWorker
extends MultiWorker {
    @Override
    public void start() {
        this.workerThread = new MultiWorker.WorkerThread(this){

            @Override
            public Object doWork() {
                return SingleWorker.this.doIt();
            }
        };
        this.workerThread.start();
    }

    protected abstract Object doIt();
}

