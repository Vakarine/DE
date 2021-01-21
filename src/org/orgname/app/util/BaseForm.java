package org.orgname.app.util;

import javax.swing.*;
import java.awt.*;

public abstract class BaseForm extends JFrame
{
    protected static String baseApplicationTitle;
    protected static Image baseApplicationIcon;

    public BaseForm()
    {
        setTitle(baseApplicationTitle == null ? "Крутая приложуха" : baseApplicationTitle);
        if(baseApplicationIcon != null) {
            setIconImage(baseApplicationIcon);
        }

        setMinimumSize(new Dimension(getFormWidth(), getFormHeight()));
        setPreferredSize(new Dimension(getFormWidth(), getFormHeight()));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getFormWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getFormHeight() / 2
        );
    }

    public abstract int getFormWidth();

    public abstract int getFormHeight();

    public static String getBaseApplicationTitle() {
        return baseApplicationTitle;
    }

    public static void setBaseApplicationTitle(String baseApplicationTitle) {
        BaseForm.baseApplicationTitle = baseApplicationTitle;
    }

    public static Image getBaseApplicationIcon() {
        return baseApplicationIcon;
    }

    public static void setBaseApplicationIcon(Image baseApplicationIcon) {
        BaseForm.baseApplicationIcon = baseApplicationIcon;
    }
}
