package org.orgname.app.util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BaseSubForm<T extends BaseForm> extends BaseForm
{
    protected final T mainForm;

    public BaseSubForm(T mainForm, String title)
    {
        this.mainForm = mainForm;

        if(title != null) {
            setTitle(title);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeSubForm();
            }
        });

        mainForm.setEnabled(false);
    }

    public BaseSubForm(T mainForm)
    {
        this(mainForm, null);
    }

    public void closeSubForm()
    {
        mainForm.setEnabled(true);

        //костыль, чтобы выкинуть окно наверх
        mainForm.setVisible(false);
        mainForm.setVisible(true);

        dispose();
    }

    public BaseForm getMainForm() {
        return mainForm;
    }
}
