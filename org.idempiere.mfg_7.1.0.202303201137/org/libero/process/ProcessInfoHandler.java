/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.compiere.model.MPInstance
 *  org.compiere.model.MPInstancePara
 *  org.compiere.model.MProcess
 *  org.compiere.process.ProcessInfo
 *  org.compiere.util.Env
 *  org.compiere.util.Msg
 */
package org.libero.process;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class ProcessInfoHandler {
    protected ProcessInfo pi;
    protected MPInstance pinstance;
    protected Hashtable param;
    protected MProcess process;

    public ProcessInfoHandler(int processID) {
        this.init(processID);
    }

    private void init(int processID) {
        this.process = new MProcess(Env.getCtx(), processID, null);
        if (this.process != null) {
            this.pi = this.getProcessInfo(Msg.translate((Properties)Env.getCtx(), (String)this.process.getName()), this.process.get_ID());
            this.pinstance = this.getProcessInstance(this.pi);
            this.pi.setAD_PInstance_ID(this.pinstance.getAD_PInstance_ID());
        }
    }

    protected ProcessInfo getProcessInfo(String name, int id) {
        ProcessInfo info = new ProcessInfo(name, id);
        info.setAD_User_ID(Env.getAD_User_ID((Properties)Env.getCtx()));
        info.setAD_Client_ID(Env.getAD_Client_ID((Properties)Env.getCtx()));
        return info;
    }

    protected MPInstance getProcessInstance(ProcessInfo info) {
        MPInstance instance = new MPInstance(Env.getCtx(), info.getAD_Process_ID(), info.getRecord_ID());
        if (!instance.save()) {
            info.setSummary(Msg.getMsg((Properties)Env.getCtx(), (String)"ProcessNoInstance"));
            info.setError(true);
            return null;
        }
        return instance;
    }

    protected int countParams() {
        return this.process != null ? this.process.getParameters().length : 0;
    }

    protected Hashtable extractParameters() {
        Hashtable<String, Object> param = new Hashtable<String, Object>();
        MPInstancePara p = null;
        int b = this.countParams();
        for (int i = 0; i < b; ++i) {
            p = new MPInstancePara(this.getProcessInstance(), i);
            p.load(null, new String[0]);
            param.put(p.getParameterName(), this.getValueFrom(p));
        }
        return param;
    }

    protected Object getValueFrom(MPInstancePara p) {
        Object o = null;
        o = o == null ? p.getP_Date() : o;
        o = o == null ? p.getP_Date_To() : o;
        o = o == null ? p.getP_Number() : o;
        o = o == null ? p.getP_Number_To() : o;
        o = o == null ? p.getP_String() : o;
        o = o == null ? p.getP_String_To() : o;
        return o;
    }

    public void setProcessError() {
        this.pi.setSummary(Msg.getMsg((Properties)Env.getCtx(), (String)"ProcessCancelled"));
        this.pi.setError(true);
    }

    public MPInstance getProcessInstance() {
        return this.pinstance;
    }

    public ProcessInfo getProcessInfo() {
        return this.pi;
    }

    public Object getParameterValue(String param) {
        if (this.param == null) {
            this.param = this.extractParameters();
        }
        return this.param.get(param);
    }

    public Enumeration getParameters() {
        if (this.param == null) {
            this.param = this.extractParameters();
        }
        return this.param.keys();
    }
}

