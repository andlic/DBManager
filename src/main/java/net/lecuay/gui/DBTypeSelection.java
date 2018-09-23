
package net.lecuay.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.lecuay.dbmanager.DBManager;

/**
 *
 * @author Javier Trancoso Lara
 */
public class DBTypeSelection extends JPanel {

    JButton cell_btn;

    public DBTypeSelection() {
        initComponents();
    }

    private void initComponents() {
        ArrayList<JButton> buttons = new ArrayList<>();
        for (DBManager.DBType t : DBManager.DBType.values()) {
            cell_btn = new JButton(t.toString());
            cell_btn.addActionListener((ActionEvent e) -> {
                MainThread.getWindow().setDbtype(t);
                MainThread.getWindow().setPanel(new ConectionForm());
                // Cambiar de panel al formulario
            });
            buttons.add(cell_btn);
        }

        buttons.forEach((t) -> {
            add(t);
        });
    }

}
