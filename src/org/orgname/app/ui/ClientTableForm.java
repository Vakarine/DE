package org.orgname.app.ui;

import org.orgname.app.Application;
import org.orgname.app.database.entity.ClientEntity;
import org.orgname.app.database.manager.ClientEntityManager;
import org.orgname.app.util.BaseForm;
import org.orgname.app.util.CustomTableModel;
import org.orgname.app.util.DialogUtil;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientTableForm extends BaseForm
{
    private ClientEntityManager clientEntityManager = Application.getInstance().clientEntityManager;

    private CustomTableModel<ClientEntity> model;

    private boolean idSort = true;

    private JPanel mainPanel;
    private JTable table;
    private JButton idSortButton;
    private JButton bithdaySortButton;
    private JComboBox genderSortBox;

    public ClientTableForm()
    {
        setContentPane(mainPanel);

        initTable();
        initButtons();
        initBoxes();

        setVisible(true);
    }

    private void initTable()
    {
        table.getTableHeader().setReorderingAllowed(false);

        try {
            model = new CustomTableModel<>(
                    ClientEntity.class,
                    new String[] {
                            "ID", "FirstName", "LastName", "Patronymic", "Birthday", "RegistrationDate", "Email", "Phone", "GenderCode", "PhotoPath"
                    },
                    clientEntityManager.getAll()
            );
            table.setModel(model);

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2 && table.getSelectedRow() != -1)
                    {
                        int row = table.rowAtPoint(e.getPoint());
                        System.out.println(model.getValues().get(row));
                    }
                }
            });

            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int row = table.getSelectedRow();
                    if(e.getKeyCode() == KeyEvent.VK_DELETE && row != -1)
                    {
                        if(!DialogUtil.showConfirm(ClientTableForm.this, "ВЫ точно хоиите удалить данную запись?")) {
                            return;
                        }

                        try {
                            clientEntityManager.delete(model.getValues().get(row));
                            model.getValues().remove(row);
                            model.fireTableDataChanged();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initButtons()
    {
        idSortButton.addActionListener(e -> {
            Collections.sort(model.getValues(), new Comparator<ClientEntity>() {
                @Override
                public int compare(ClientEntity o1, ClientEntity o2) {
                    if(!idSort) {
                        return Integer.compare(o1.getId(), o2.getId());
                    } else {
                        return Integer.compare(o2.getId(), o1.getId());
                    }
                }
            });
            idSort = !idSort;
            model.fireTableDataChanged();
        });

        bithdaySortButton.addActionListener(e -> {
            Collections.sort(model.getValues(), new Comparator<ClientEntity>() {
                @Override
                public int compare(ClientEntity o1, ClientEntity o2) {
                    return o1.getBirthday().compareTo(o2.getBirthday());
                }
            });
            model.fireTableDataChanged();
        });
    }

    private void initBoxes()
    {
        genderSortBox.addItem("Все гендеры");
        genderSortBox.addItem("Мужской");
        genderSortBox.addItem("Женский");

        genderSortBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {

                try {
                    List<ClientEntity> allClients = clientEntityManager.getAll();
                    int selected = genderSortBox.getSelectedIndex();
                    if(selected == 0) {
                        model.setValues(allClients);
                    } else if(selected == 1) {
                        List<ClientEntity> temp = new ArrayList<>();
                        for(ClientEntity c : allClients) {
                            if(c.getGenderCode() == 'м') {
                                temp.add(c);
                            }
                        }
                        model.setValues(temp);
                    } else if(selected == 2) {
                        List<ClientEntity> temp = new ArrayList<>();
                        for(ClientEntity c : allClients) {
                            if(c.getGenderCode() == 'ж') {
                                temp.add(c);
                            }
                        }
                        model.setValues(temp);
                    }
                    model.fireTableDataChanged();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getFormWidth() {
        return 1000;
    }

    @Override
    public int getFormHeight() {
        return 600;
    }

    public CustomTableModel<ClientEntity> getModel() {
        return model;
    }
}
