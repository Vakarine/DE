package org.orgname.app;

import org.orgname.app.database.manager.ClientEntityManager;
import org.orgname.app.database.manager.ServiceEntityManager;
import org.orgname.app.ui.ClientTableForm;
import org.orgname.app.ui.ServiceTableForm;
import org.orgname.app.util.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;

public class Application
{
    private static Application instance;

    private final MysqlDatabase database = new MysqlDatabase("116.202.236.174", "DemoExam", "DemoExam", "DemoExam");
    //public final MysqlDatabase database = new MysqlDatabase("nleontnr.beget.tech", "nleontnr_docker", "nleontnr_docker", "8udwX&9bdw");
    public final ServiceEntityManager serviceEntityManager = new ServiceEntityManager(database);
    public final ClientEntityManager clientEntityManager = new ClientEntityManager(database);

    private static final String ADMIN_PASSWORD = "0000";
    private static boolean adminMode = false;

    private Application()
    {
        instance = this;

        initDatabase();
        initUi();
        checkAdmin();

        new ServiceTableForm();
    }

    private void initDatabase()
    {
        try(Connection c = database.getConnection()) {
        } catch (Exception e) {
            DialogUtil.showError("Ошибка подлючения к бд");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void initUi()
    {
        BaseForm.setBaseApplicationTitle("Медицинский центр трубочист");
        try {
            BaseForm.setBaseApplicationIcon(ResourceUtil.getImage("icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
            DialogUtil.showError("Не удалось загрузить иконку приложения");
        }

        FontUtil.changeAllFonts(new FontUIResource(" Tw Cen MT", Font.TRUETYPE_FONT, 12));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAdmin()
    {
        if(ADMIN_PASSWORD.equals(JOptionPane.showInputDialog(
                null,
                "Введите пароль администратора если знаете",
                "Режим администратора",
                JOptionPane.INFORMATION_MESSAGE
        ))) {
            adminMode = true;
        }
    }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public static Application getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Application();
    }

    public static boolean isAdminMode() {
        return adminMode;
    }
}
