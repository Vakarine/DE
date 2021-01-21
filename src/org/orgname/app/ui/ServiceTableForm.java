package org.orgname.app.ui;

import org.orgname.app.Application;
import org.orgname.app.database.entity.ServiceEntity;
import org.orgname.app.database.manager.ServiceEntityManager;
import org.orgname.app.util.BaseForm;
import org.orgname.app.util.CustomTableModel;
import org.orgname.app.util.DialogUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceTableForm extends BaseForm
{
    private final ServiceEntityManager serviceEntityManager = Application.getInstance().serviceEntityManager;

    private CustomTableModel<ServiceEntity> model;

    private boolean discountSort = false;
    private int maxRowCount = 0;

    private JPanel mainPanel;
    private JTable table;
    private JButton discountSortButton;
    private JComboBox discountSortBox;
    private JButton clearButton;
    private JLabel valuesCountLabel;
    private JButton addButton;

    public ServiceTableForm()
    {
        setContentPane(mainPanel);

        initTable();
        initBoxes();
        initButtons();

        setVisible(true);
    }

    private void initTable()
    {
        try {
            model = new CustomTableModel<>(
                    ServiceEntity.class,
                    new String[] {
                            "ID", "Наименование", "Стоимость", "Продолжительность", "Скидка", "Миниатюра"
                    },
                    serviceEntityManager.getAll()
            );
            table.setModel(model);
            maxRowCount = model.getValues().size();
            updateRowCount();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ServiceEntity service = model.getValues().get(row);
                c.setBackground(service.getDiscount() > 0 ?  new Color(144, 238, 144) : Color.WHITE);
                return c;
            }
        });

        if(Application.isAdminMode()) {
            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int row = table.getSelectedRow();
                    if(e.getKeyCode() == KeyEvent.VK_DELETE && row != -1) {
                        if(DialogUtil.showConfirm(ServiceTableForm.this, "Вы точно хотите удалить данную запись?")) {
                            try {
                                serviceEntityManager.delete(model.getValues().get(row));
                                model.getValues().remove(row);
                                model.fireTableDataChanged();

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    private void initBoxes()
    {
        discountSortBox.addItem("Все");
        discountSortBox.addItem("от 0 до 5%");
        discountSortBox.addItem("от 5% до 15%");
        discountSortBox.addItem("от 15% до 30%");
        discountSortBox.addItem("от 30% до 70%");
        discountSortBox.addItem("от 70% до 100%");

        discountSortBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    List<ServiceEntity> services = serviceEntityManager.getAll();
                    maxRowCount = services.size();
                    switch (discountSortBox.getSelectedIndex())
                    {
                        case 0:
                            break;
                        case 1:
                            services.removeIf(serviceEntity -> serviceEntity.getDiscount() >= 5);
                            break;
                        case 2:
                            services.removeIf(serviceEntity -> serviceEntity.getDiscount() < 5 || serviceEntity.getDiscount() >= 15);
                            break;
                        case 3:
                            services.removeIf(serviceEntity -> serviceEntity.getDiscount() < 15 || serviceEntity.getDiscount() >= 30);
                            break;
                        case 4:
                            services.removeIf(serviceEntity -> serviceEntity.getDiscount() < 30 || serviceEntity.getDiscount() >= 70);
                            break;
                        case 5:
                            services.removeIf(serviceEntity -> serviceEntity.getDiscount() < 70);
                            break;
                    }
                    model.setValues(services);
                    model.fireTableDataChanged();
                    updateRowCount();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    private void initButtons()
    {
        discountSortButton.addActionListener(e -> {
            Collections.sort(model.getValues(), new Comparator<ServiceEntity>() {
                @Override
                public int compare(ServiceEntity o1, ServiceEntity o2) {
                    if(!discountSort) {
                        return Double.compare(o1.getDiscount(), o2.getDiscount());
                    } else {
                        return Double.compare(o2.getDiscount(), o1.getDiscount());
                    }
                }
            });
            discountSort = !discountSort;
            model.fireTableDataChanged();
        });

        clearButton.addActionListener(e -> {
            discountSort = false;
            discountSortBox.setSelectedIndex(0);
        });

        if(!Application.isAdminMode()) {
            addButton.setVisible(false);
        }
    }

    private void updateRowCount()
    {
        valuesCountLabel.setText("Показано записей " + model.getValues().size() + "/" + maxRowCount);
    }

    @Override
    public int getFormWidth() {
        return 1000;
    }

    @Override
    public int getFormHeight() {
        return 600;
    }

    public CustomTableModel<ServiceEntity> getModel() {
        return model;
    }
}
