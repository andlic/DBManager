
package net.lecuay.gui;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.lecuay.dbmanager.DBManager.DBType;

/**
 *
 * @author Javier Trancoso Lara
 */
public class Window extends JFrame {

    static DBType dbtype = DBType.MYSQL;

    JPanel main_panel = new JPanel();

    public Window() {
        BasicConfiguration();
        setPanel(new DBTypeSelection());

        setVisible(true);
    }

    private void BasicConfiguration() {
        setSize(500, 300);
        setTitle("DBManager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        this.add(main_panel);
    }

    protected void setPanel(JPanel panel) {
        remove(main_panel);
        main_panel = panel;
        add(main_panel);
        // Makes a manual repaint
        setSize(501, 301);
        setSize(500, 300);
    }

    public DBType getDBType() {
        return dbtype;
    }

    public void setDbtype(DBType dbtype) {
        Window.dbtype = dbtype;
    }
}
