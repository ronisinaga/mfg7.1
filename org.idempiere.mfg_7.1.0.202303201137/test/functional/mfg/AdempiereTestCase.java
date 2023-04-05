/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  junit.framework.TestCase
 *  org.compiere.Adempiere
 *  org.compiere.util.CLogMgt
 *  org.compiere.util.CLogger
 *  org.compiere.util.Env
 *  org.compiere.util.Ini
 *  org.compiere.util.Trx
 */
package test.functional.mfg;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import junit.framework.TestCase;
import org.compiere.Adempiere;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Trx;

public class AdempiereTestCase
extends TestCase {
    protected Properties testProperties = null;
    protected String testPropertiesFileName = "test.properties";
    private Properties m_Ctx = null;
    public final String fileName_Key = "AdempiereProperties";
    private String fileName_DefaultValue = "idempiere.properties";
    private String fileName_Value = "";
    public final String isClient_Key = "isClient";
    private String isClient_DefaultValue = "Y";
    private boolean isClient_Value = true;
    public final String AD_User_ID_Key = "AD_User_ID";
    private String AD_User_ID_DefaultValue = "100";
    private int AD_User_ID_Value = 0;
    public final String AD_Client_ID_Key = "AD_Client_ID";
    private String AD_Client_ID_DefaultValue = "11";
    private int AD_Client_ID_Value = 11;
    public final String LogLevel_Key = "LogLevel";
    private String LogLevel_DefaultValue = Level.FINEST.toString();
    private Level LogLevel_Value = Level.FINEST;
    protected final CLogger log = CLogger.getCLogger(((Object)((Object)this)).getClass());
    private String trxName = Trx.createTrxName((String)(String.valueOf(((Object)((Object)this)).getClass().getName()) + "_"));
    private Random m_randGenerator = new Random(System.currentTimeMillis());

    public Properties getCtx() {
        return this.m_Ctx;
    }

    public String getTrxName() {
        return this.trxName;
    }

    public int getAD_Client_ID() {
        return this.AD_Client_ID_Value;
    }

    public int getAD_User_ID() {
        return this.AD_User_ID_Value;
    }

    public boolean isClient() {
        return this.isClient_Value;
    }

    protected void setUp() throws Exception {
        super.setUp();
        this.testProperties = new Properties();
        File file = new File(this.testPropertiesFileName);
        if (!file.isFile()) {
            this.log.warning("File not found - " + file.getAbsolutePath());
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(null);
            file = returnVal == 0 ? chooser.getSelectedFile() : null;
        }
        this.testProperties.load(new FileInputStream(file));
        this.fileName_Value = this.testProperties.getProperty("AdempiereProperties", this.fileName_DefaultValue);
        this.isClient_Value = "Y".equals(this.testProperties.getProperty("isClient", this.isClient_DefaultValue));
        this.AD_User_ID_Value = Integer.parseInt(this.testProperties.getProperty("AD_User_ID", this.AD_User_ID_DefaultValue));
        this.AD_Client_ID_Value = Integer.parseInt(this.testProperties.getProperty("AD_Client_ID", this.AD_Client_ID_DefaultValue));
        try {
            this.LogLevel_Value = Level.parse(this.testProperties.getProperty("LogLevel", this.LogLevel_DefaultValue));
        }
        catch (Exception exception) {}
        this.m_Ctx = Env.getCtx();
        this.m_Ctx.setProperty("#AD_User_ID", new Integer(this.AD_User_ID_Value).toString());
        this.m_Ctx.setProperty("#AD_Client_ID", new Integer(this.AD_Client_ID_Value).toString());
        if (this.fileName_Value.length() < 1) {
            AdempiereTestCase.assertEquals((String)"Please specify path to idempiere.properties file!", (boolean)true, (boolean)false);
        }
        System.setProperty("PropertyFile", this.fileName_Value);
        Ini.setClient((boolean)this.isClient_Value);
        Adempiere.startup((boolean)this.isClient_Value);
        CLogMgt.setLevel((Level)this.LogLevel_Value);
    }

    protected void commit() throws Exception {
        Trx trx = null;
        if (this.trxName != null) {
            trx = Trx.get((String)this.trxName, (boolean)false);
        }
        if (trx != null && trx.isActive()) {
            trx.commit(true);
        }
    }

    protected void rollback() {
        Trx trx = null;
        if (this.trxName != null) {
            trx = Trx.get((String)this.trxName, (boolean)false);
        }
        if (trx != null && trx.isActive()) {
            trx.rollback();
        }
    }

    protected void close() {
        Trx trx = null;
        if (this.trxName != null) {
            trx = Trx.get((String)this.trxName, (boolean)false);
        }
        if (trx != null) {
            trx.close();
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        Trx trx = null;
        if (this.trxName != null) {
            trx = Trx.get((String)this.trxName, (boolean)false);
        }
        if (trx != null && trx.isActive()) {
            trx.rollback();
        }
        if (trx != null) {
            trx.close();
        }
        trx = null;
        this.testProperties = null;
        this.m_Ctx = null;
    }

    public int randomInt(int max) {
        return this.m_randGenerator.nextInt(max);
    }

    public void assertExceptionThrown(String message, Class<? extends Exception> exceptionType, Runnable runnable) throws Exception {
        Exception ex = null;
        try {
            runnable.run();
        }
        catch (Exception e) {
            ex = e;
        }
        AdempiereTestCase.assertNotNull((String)("No exception was throwed : " + message), (Object)ex);
        if (exceptionType != null && !exceptionType.isAssignableFrom(ex.getClass())) {
            throw ex;
        }
    }
}

