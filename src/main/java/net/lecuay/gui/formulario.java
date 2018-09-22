/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lecuay.gui;

/**
 *
 * @author javier
 */
public class formulario extends javax.swing.JPanel {

    /**
     * Creates new form formulario
     */
    public formulario() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lb_usuario = new javax.swing.JLabel();
        lb_contraseña = new javax.swing.JLabel();
        lb_host = new javax.swing.JLabel();
        lb_base_datos = new javax.swing.JLabel();
        lb_puerto = new javax.swing.JLabel();
        lb_sslmode = new javax.swing.JLabel();
        tf_usuario = new javax.swing.JTextField();
        cb_sslmode = new javax.swing.JCheckBox();
        tf_contraseña = new javax.swing.JTextField();
        tf_host = new javax.swing.JTextField();
        tf_base_datos = new javax.swing.JTextField();
        tf_puerto = new javax.swing.JTextField();
        lb_jdbc = new javax.swing.JLabel();
        tf_jdbc = new javax.swing.JTextField();
        lb_uri = new javax.swing.JLabel();
        tf_uri = new javax.swing.JTextField();
        btn_atras = new javax.swing.JButton();
        btn_siguiente = new javax.swing.JButton();

        jLabel1.setText(Ventana.getDBType().toString());

        lb_usuario.setText("Usuario:");

        lb_contraseña.setText("Contraseña:");

        lb_host.setText("Host:");

        lb_base_datos.setText("Base de datos:");

        lb_puerto.setText("Puerto:");

        lb_sslmode.setText("Modo SSL");

        tf_usuario.setText("Usuario");
        tf_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usuarioActionPerformed(evt);
            }
        });

        cb_sslmode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_sslmodeActionPerformed(evt);
            }
        });

        tf_contraseña.setText("Contraseña");
        tf_contraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_contraseñaActionPerformed(evt);
            }
        });

        tf_host.setText("ej: 192.123.123.123");

        tf_base_datos.setText("Base de datos");
        tf_base_datos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_base_datosActionPerformed(evt);
            }
        });

        tf_puerto.setText("ej: 8080");

        lb_jdbc.setText("JDBC");

        tf_jdbc.setText("JDBC");

        lb_uri.setText("URI");

        tf_uri.setText("URI");
        tf_uri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_uriActionPerformed(evt);
            }
        });

        btn_atras.setText("Atrás");

        btn_siguiente.setText("Siguiente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_atras)
                        .addGap(222, 222, 222)
                        .addComponent(btn_siguiente))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lb_usuario)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(lb_uri)
                            .addGap(77, 77, 77)
                            .addComponent(tf_uri, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(157, 157, 157)
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lb_contraseña)
                                .addComponent(lb_host)
                                .addComponent(lb_base_datos))
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(tf_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb_sslmode)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cb_sslmode))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_base_datos, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(tf_host, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                                .addComponent(tf_contraseña))
                                            .addGap(30, 30, 30)
                                            .addComponent(lb_puerto)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tf_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(lb_jdbc)
                            .addGap(65, 65, 65)
                            .addComponent(tf_jdbc, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_usuario)
                        .addComponent(tf_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_sslmode))
                    .addComponent(cb_sslmode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_contraseña)
                    .addComponent(tf_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_host)
                    .addComponent(tf_host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_puerto)
                    .addComponent(tf_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_base_datos)
                    .addComponent(tf_base_datos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_jdbc)
                    .addComponent(tf_jdbc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_uri)
                    .addComponent(tf_uri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_atras)
                    .addComponent(btn_siguiente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tf_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_usuarioActionPerformed

    private void tf_contraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_contraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_contraseñaActionPerformed

    private void tf_base_datosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_base_datosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_base_datosActionPerformed

    private void cb_sslmodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_sslmodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_sslmodeActionPerformed

    private void tf_uriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_uriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_uriActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_atras;
    private javax.swing.JButton btn_siguiente;
    private javax.swing.JCheckBox cb_sslmode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lb_base_datos;
    private javax.swing.JLabel lb_contraseña;
    private javax.swing.JLabel lb_host;
    private javax.swing.JLabel lb_jdbc;
    private javax.swing.JLabel lb_puerto;
    private javax.swing.JLabel lb_sslmode;
    private javax.swing.JLabel lb_uri;
    private javax.swing.JLabel lb_usuario;
    private javax.swing.JTextField tf_base_datos;
    private javax.swing.JTextField tf_contraseña;
    private javax.swing.JTextField tf_host;
    private javax.swing.JTextField tf_jdbc;
    private javax.swing.JTextField tf_puerto;
    private javax.swing.JTextField tf_uri;
    private javax.swing.JTextField tf_usuario;
    // End of variables declaration//GEN-END:variables
}
