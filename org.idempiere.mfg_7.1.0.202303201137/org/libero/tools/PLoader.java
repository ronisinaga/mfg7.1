/*
 * Decompiled with CFR 0.150.
 */
package org.libero.tools;

import java.io.InputStream;
import java.util.Properties;

public class PLoader {
    protected Properties properties;

    public PLoader(String properties) {
        this.init(this.getClass(), properties);
    }

    public PLoader(Class clazz, String properties) {
        this.init(clazz, properties);
    }

    protected void init(Class clazz, String name) {
        this.properties = new Properties();
        InputStream is = clazz.getResourceAsStream(name);
        try {
            try {
                if (is != null) {
                    this.properties.load(is);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    if (is != null) {
                        is.close();
                    }
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public Properties getProperties() {
        return this.properties;
    }
}

