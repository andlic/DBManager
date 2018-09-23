package net.lecuay.gui;

import javax.swing.JOptionPane;
import net.lecuay.dbmanager.DBManager;

/**
 *
 * @author javier
 */
public class ConectionForm extends javax.swing.JPanel {

    public ConectionForm() {
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

        dbtype_lb = new javax.swing.JLabel();
        user_lb = new javax.swing.JLabel();
        password_lb = new javax.swing.JLabel();
        host_lb = new javax.swing.JLabel();
        DBName_lb = new javax.swing.JLabel();
        port_lb = new javax.swing.JLabel();
        sslmode_lb = new javax.swing.JLabel();
        user_tf = new javax.swing.JTextField();
        sslmode_cb = new javax.swing.JCheckBox();
        host_tf = new javax.swing.JTextField();
        DBName_tf = new javax.swing.JTextField();
        port_tf = new javax.swing.JTextField();
        jdbc_lb = new javax.swing.JLabel();
        jdbc_tf = new javax.swing.JTextField();
        uri_lb = new javax.swing.JLabel();
        uri_tf = new javax.swing.JTextField();
        back_btn = new javax.swing.JButton();
        next_btn = new javax.swing.JButton();
        password_pf = new javax.swing.JPasswordField();

        dbtype_lb.setText(net.lecuay.gui.MainThread.getWindow().getDBType().toString());

        user_lb.setText("User:");

        password_lb.setText("Password:");

        host_lb.setText("Host:");

        DBName_lb.setText("Database name:");

        port_lb.setText("Port:");

        sslmode_lb.setText("SSL Mode");

        user_tf.setToolTipText("User");
        user_tf.setName(""); // NOI18N
        user_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_tfActionPerformed(evt);
            }
        });

        sslmode_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sslmode_cbActionPerformed(evt);
            }
        });

        host_tf.setToolTipText("example: 192.123.123.123");

        DBName_tf.setToolTipText("Database name");
        DBName_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBName_tfActionPerformed(evt);
            }
        });

        port_tf.setToolTipText("example: 8080");

        jdbc_lb.setText("JDBC:");

        jdbc_tf.setToolTipText("JDBC");

        uri_lb.setText("URI:");

        uri_tf.setToolTipText("URI");
        uri_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uri_tfActionPerformed(evt);
            }
        });

        back_btn.setText("Back");
        back_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_btnActionPerformed(evt);
            }
        });

        next_btn.setText("Next");
        next_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next_btnActionPerformed(evt);
            }
        });

        password_pf.setToolTipText("Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(back_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(next_btn))
                    .addComponent(user_lb, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uri_lb)
                        .addGap(77, 77, 77)
                        .addComponent(uri_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(dbtype_lb))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(password_lb)
                            .addComponent(host_lb)
                            .addComponent(DBName_lb))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(user_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(80, 80, 80)
                                    .addComponent(sslmode_lb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(sslmode_cb))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(host_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(port_lb)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(port_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(DBName_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(password_pf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jdbc_lb)
                        .addGap(65, 65, 65)
                        .addComponent(jdbc_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dbtype_lb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user_lb)
                        .addComponent(user_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sslmode_lb))
                    .addComponent(sslmode_cb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password_lb)
                    .addComponent(password_pf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(host_lb)
                    .addComponent(host_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(port_lb)
                    .addComponent(port_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DBName_lb)
                    .addComponent(DBName_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jdbc_lb)
                    .addComponent(jdbc_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uri_lb)
                    .addComponent(uri_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back_btn)
                    .addComponent(next_btn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        user_tf.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void user_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_user_tfActionPerformed

    private void DBName_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBName_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBName_tfActionPerformed

    private void sslmode_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sslmode_cbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sslmode_cbActionPerformed

    private void uri_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uri_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uri_tfActionPerformed

    private void back_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_btnActionPerformed
        MainThread.getWindow().setPanel(new DBTypeSelection());
    }//GEN-LAST:event_back_btnActionPerformed

    private void next_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next_btnActionPerformed
        if (!user_tf.getText().isEmpty() && !password_pf.getText().isEmpty() && !host_tf.getText().isEmpty() && !DBName_tf.getText().isEmpty() && !port_tf.getText().isEmpty()) {
            System.out.println(user_tf.getText() + " " + password_pf.getText() + " " + host_tf.getText() + " " + DBName_tf.getText() + " " + port_tf.getText() + " ");
            try {
                try {
                    MainThread.getWindow().setPanel(new Connecting(user_tf.getText(), password_pf.getText(), host_tf.getText(), DBName_tf.getText(), Integer.parseInt(port_tf.getText()), sslmode_cb.isSelected()));
                } catch (UnsupportedOperationException e1){
                    JOptionPane.showMessageDialog(null, "Error. Not supported action");
                }
            } catch (java.lang.RuntimeException e2) {
                JOptionPane.showMessageDialog(null, "Error. Please confirm that the fields are filled correctly");
            }
        }
    }//GEN-LAST:event_next_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DBName_lb;
    private javax.swing.JTextField DBName_tf;
    private javax.swing.JButton back_btn;
    private javax.swing.JLabel dbtype_lb;
    private javax.swing.JLabel host_lb;
    private javax.swing.JTextField host_tf;
    private javax.swing.JLabel jdbc_lb;
    private javax.swing.JTextField jdbc_tf;
    private javax.swing.JButton next_btn;
    private javax.swing.JLabel password_lb;
    private javax.swing.JPasswordField password_pf;
    private javax.swing.JLabel port_lb;
    private javax.swing.JTextField port_tf;
    private javax.swing.JCheckBox sslmode_cb;
    private javax.swing.JLabel sslmode_lb;
    private javax.swing.JLabel uri_lb;
    private javax.swing.JTextField uri_tf;
    private javax.swing.JLabel user_lb;
    private javax.swing.JTextField user_tf;
    // End of variables declaration//GEN-END:variables
}
