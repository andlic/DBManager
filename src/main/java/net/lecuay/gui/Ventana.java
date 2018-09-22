/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lecuay.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.lecuay.dbmanager.DBManager;
import net.lecuay.dbmanager.DBManager.DBType;

/**
 *
 * @author Javier Trancoso Lara
 */
public class Ventana extends JFrame {

    DBType dbtype;

    JButton boton_celda;
    JPanel panel_principal = new JPanel();
    HashMap<PanelDBM, JPanel> paneles = new HashMap<>();

    public Ventana() {
        ConfiguracionBasica();
        AñadirPanelesDisponibles();
        SetPanelSeleccionDBType();
        //SetPanelFormularioAcceso(); // Añadir valor inicial a la variable dbtype en caso de descomentar
        CambiarPanel(PanelDBM.SeleccionDBType);
        // Final
        HacerVisible();
    }

    private void ConfiguracionBasica() {
        setSize(500, 300);
        setTitle("DBManager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        this.add(panel_principal);
    }

    private enum PanelDBM {
        SeleccionDBType,
        FormularioAcceso
    }

    private void AñadirPanelesDisponibles() {
        paneles.put(PanelDBM.SeleccionDBType, new JPanel());
        paneles.put(PanelDBM.FormularioAcceso, new JPanel());
    }

    void SetPanelSeleccionDBType() {
        ArrayList<JButton> botones = new ArrayList<>();
        for (DBManager.DBType t : DBType.values()) {
            boton_celda = new JButton(t.toString());
            boton_celda.addActionListener((ActionEvent e) -> {
                dbtype = t;
                SetPanelFormularioAcceso();
                CambiarPanel(PanelDBM.FormularioAcceso);
            });
            botones.add(boton_celda);
        }

        botones.forEach((t) -> {
            paneles.get(PanelDBM.SeleccionDBType).add(t);

        });
    }

    private void CambiarPanel(PanelDBM panel) {
        remove(panel_principal);
        panel_principal = paneles.get(panel);
        add(panel_principal);
        // Hace un repaint manual
        setSize(501, 301);
        setSize(500, 300);
    }

    private void HacerVisible() {
        setVisible(true);
    }

    void SetPanelFormularioAcceso() {
        paneles.get(PanelDBM.FormularioAcceso).removeAll();
        JLabel lb_dbtype = new JLabel(dbtype.toString());
        // Panel usuario
        JPanel pnl_usuario = new JPanel();
        JLabel lb_usuario = new JLabel("Usuario:");
        JTextField tf_usuario = new JTextField(10);
        pnl_usuario.add(lb_usuario);
        pnl_usuario.add(tf_usuario);
        // Panel constraseña
        JPanel pnl_contraseña = new JPanel();
        JLabel lb_contraseña = new JLabel("Contraseña:");
        JTextField tf_contraseña = new JTextField(10);
        pnl_contraseña.add(lb_contraseña);
        pnl_contraseña.add(tf_contraseña);
        // Panel host
        JPanel pnl_host = new JPanel();
        JLabel lb_host = new JLabel("Host:");
        JTextField tf_host = new JTextField(10);
        pnl_host.add(lb_host);
        pnl_host.add(tf_host);
        paneles.get(PanelDBM.FormularioAcceso).setLayout(new FlowLayout());
        paneles.get(PanelDBM.FormularioAcceso).add(lb_dbtype);
        paneles.get(PanelDBM.FormularioAcceso).add(pnl_usuario);
        paneles.get(PanelDBM.FormularioAcceso).add(pnl_contraseña);
        paneles.get(PanelDBM.FormularioAcceso).add(pnl_host);
    }

    public DBType getDbtype() {
        return dbtype;
    }

    public void setDbtype(DBType dbtype) {
        this.dbtype = dbtype;
    }
}
