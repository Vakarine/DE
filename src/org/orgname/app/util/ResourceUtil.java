package org.orgname.app.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ResourceUtil
{
    public static File getFile(String path) throws IOException {
        return new File(ResourceUtil.class.getClassLoader().getResource(path).getFile());
    }

    public static Image getImage(String path) throws IOException {
        return Toolkit.getDefaultToolkit().getImage(ResourceUtil.class.getClassLoader().getResource(path));
    }

    public static Font getFont(String path) throws IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, ResourceUtil.class.getClassLoader().getResourceAsStream(path));
    }
}
