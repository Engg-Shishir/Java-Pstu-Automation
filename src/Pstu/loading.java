/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;


import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author ASUS
 */
public class loading extends javax.swing.JFrame {

    /**
     * Creates new form loading
     */


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Backgroundpanel = new javax.swing.JPanel();
        team = new javax.swing.JLabel();
        loadingbar = new javax.swing.JProgressBar();
        loadingtext = new javax.swing.JLabel();
        loadingvalue = new javax.swing.JLabel();
        bgimage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Backgroundpanel.setBackground(new java.awt.Color(0, 153, 153));
        Backgroundpanel.setPreferredSize(new java.awt.Dimension(900, 500));
        Backgroundpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        team.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/teamwork-and-team-building-removebg-preview.png"))); // NOI18N
        Backgroundpanel.add(team, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, -1));

        loadingbar.setForeground(new java.awt.Color(204, 0, 51));
        Backgroundpanel.add(loadingbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 860, 10));

        loadingtext.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        loadingtext.setForeground(new java.awt.Color(255, 51, 51));
        loadingtext.setText("Loading...");
        Backgroundpanel.add(loadingtext, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 510, 20));

        loadingvalue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        loadingvalue.setForeground(new java.awt.Color(255, 255, 255));
        loadingvalue.setText("0 %");
        loadingvalue.setToolTipText("");
        Backgroundpanel.add(loadingvalue, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 430, 70, 30));

        bgimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo/grd2.jpg"))); // NOI18N
        Backgroundpanel.add(bgimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Backgroundpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Backgroundpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public loading() {
        initComponents();
    }
    public static void main(String args[]) {
        
                loading sp = new loading();
                sp.setVisible(true);
                
                int i = 0;

                for(i=0;i<=100;i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(loading.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sp.loadingvalue.setText(i +"%");
                  
                    if(i==10){
                     sp.loadingtext.setText("PSTU Turning on module...");
                    }
                    if(i==20){
                        sp.loadingtext.setText("PSTU Loading module...");
                    }

                    if(i==50){
                        sp.loadingtext.setText("PSTU Conecting to database...");
                    }

                    if(i==70){
                        sp.loadingtext.setText("PSTU Connection successfull !");
                    }

                    if(i==80){
                        sp.loadingtext.setText("PSTU Launching application...");
                    }
                    
                    if(i==100){
                        sp.loadingtext.setText("Wait a bit.");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(loading.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        sp.dispose();
//                        new Background().setVisible(true);
                        new login().setVisible(true);  
                    }
                    sp.loadingbar.setValue(i);
                }
        
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Backgroundpanel;
    private javax.swing.JLabel bgimage;
    private javax.swing.JProgressBar loadingbar;
    private javax.swing.JLabel loadingtext;
    private javax.swing.JLabel loadingvalue;
    private javax.swing.JLabel team;
    // End of variables declaration//GEN-END:variables
}
