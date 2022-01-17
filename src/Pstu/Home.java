/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author ASUS
 */
public class Home extends javax.swing.JFrame {
    /**
     * Creates new form Home
    */

    private static String logedUser,logedUserIdentity ;
    public String  image="";
    
    public Home(String pailam, String identity) {
        logedUser = pailam;
        logedUserIdentity = identity;
        System.out.println(logedUser);
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

        jPanel1 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        separetor = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        profilePic = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setMinimumSize(new java.awt.Dimension(1220, 690));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        name.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setText(" Shishir Bhuiyan");
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 40, 330, 60));

        email.setBackground(new java.awt.Color(0, 51, 51));
        email.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setText("         shishirbhuiyan82@gmail.com");
        jPanel1.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 100, 340, 20));

        separetor.setBackground(new java.awt.Color(255, 255, 255));
        separetor.setOpaque(true);
        jPanel1.add(separetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 123, 400, 3));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/Dashboard/Untitled design (9).png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 410, 80, 80));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Faculty");
        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 340, 140, 30));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Balance");
        jButton4.setBorder(null);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 490, 140, 30));

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/Dashboard/icons8_shutdown_35px_5.png"))); // NOI18N
        jButton14.setText("Log Out");
        jButton14.setBorder(null);
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 150, 40));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/Dashboard/Untitled design (12).png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 260, 80, 80));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/Dashboard/icons8_eye_35px.png"))); // NOI18N
        jButton15.setText("View Profile");
        jButton15.setBorder(null);
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 150, 40));
        jPanel1.add(profilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 200, 190));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/bg2.jpg"))); // NOI18N
        background.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                backgroundComponentShown(evt);
            }
        });
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void backgroundComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_backgroundComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_backgroundComponentShown

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
            conn cc = new conn();
                    String q = "select * from student where name='"+logedUser+"'";
        try { 
            ResultSet rs = cc.s.executeQuery(q);
            if(rs.next()){
            String nilam = rs.getString(16);
                System.out.println(nilam);
                ImageIcon gCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("university/management/system/upload/"+nilam));
                Image gCardBtnIcon = gCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
                ImageIcon icongCardBtn = new ImageIcon(gCardBtnIcon);
                profilePic.setIcon(icongCardBtn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formComponentShown

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
         // TODO add your handling code here:
//        profilePic.setIcon( new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/student.gif")).getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT));
    }//GEN-LAST:event_jButton15ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                String dilam = "ok";
//                new Home(dilam).setVisible(true);
//            }
//        });
                 String username ="";
                 String userIdentity = "";
                Home sp = new Home(username,userIdentity);
                sp.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel email;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel name;
    private javax.swing.JLabel profilePic;
    private javax.swing.JLabel separetor;
    // End of variables declaration//GEN-END:variables

    private void setIcon(Image scaledInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
