package org.orgname.app.util;

import javax.swing.*;

public class FontUtil
{
    public static void changeAllFonts(javax.swing.plaf.FontUIResource font)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, font);
        }
    }
}
