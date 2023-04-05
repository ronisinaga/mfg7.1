/*
 * Decompiled with CFR 0.150.
 */
package org.libero.tools.worker;

public abstract class MultiWorker {
    protected boolean isWorking;
    protected WorkerThread workerThread;
    protected int timeout;
    protected Object value;

    public MultiWorker() {
        this.setTimeout(-1);
    }

    public abstract void start();

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void waitForComplete(int timeout) {
        this.setTimeout(timeout);
        this.waitForComplete();
    }

    public void stop() {
        this.workerThread.interrupt();
    }

    public void waitForComplete() {
        boolean to = this.getTimeout() > -1;
        int c = 0;
        int i = 1000;
        while (this.isWorking()) {
            try {
                Thread.sleep(i);
                c += to ? (c = c + i) : -1;
            }
            catch (Exception exception) {}
            if (!to || c < this.getTimeout()) continue;
            this.workerThread.interrupt();
            this.workerThread = null;
            break;
        }
    }

    protected abstract class WorkerThread
    extends Thread {
        protected WorkerThread() {
        }

        public abstract Object doWork();

        @Override
        public void run() {
            MultiWorker.this.isWorking = true;
            MultiWorker.this.value = this.doWork();
            MultiWorker.this.isWorking = false;
        }

        @Override
        public void interrupt() {
            super.interrupt();
            MultiWorker.this.isWorking = false;
        }
    }
}

