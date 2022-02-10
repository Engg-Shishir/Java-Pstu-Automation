/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import ImageInsert_ImageShow.Insert;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class print extends javax.swing.JFrame {

    /**
     * Creates new form print
     */
    public String getId = "";
    public String getImageName = "";
     String s;
     ResultSet rs;
     String date;
     
     
     
     
    public print(String id) {
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
          LocalDateTime now = LocalDateTime.now();  
          date = dtf.format(now); 
          
          
        System.out.println("Constructor");
        getId = id;
        initComponents();
    }
    
    
        public class func{
            public ResultSet find(String id) throws SQLException{

                conn con = new conn();
                String query = "SELECT student.*,users.status FROM student,users WHERE student.uid = users.uid AND users.uid =?";
                PreparedStatement ps=con.c.prepareStatement(query);
                ps.setString(1,id);
                rs  = ps.executeQuery();
                return rs;

            }
        }
        
        public void showComponent(){
                System.out.println("formComponentShown");
                    // Call func meathoad
                   print.func f = new print.func();
                    try {
                        
                        //  recive func meathoad return query result                   
                        rs = f.find(getId);
                        if(rs.next()){
                                byte[] img = rs.getBytes("photo");
                                
                                if(img != null){
                                
                                ImageIcon MyImage1 = new ImageIcon(img);
                                Image img1 = MyImage1.getImage();
                                Image newImage1 = img1.getScaledInstance(profilePic.getWidth(), profilePic.getHeight(), Image.SCALE_SMOOTH);
                                ImageIcon image1 = new ImageIcon(newImage1);
                                profilePic.setIcon(image1);
                                }
                                datePrint.setText(date);
                                
                                
                                jnames.setText(rs.getString(2));
                                jphones.setText(rs.getString(6));
                                Jreg.setText(rs.getString(9));
                                Jsession.setText(rs.getString(10));
                                Jid.setText(rs.getString(8));
                                JBlood.setText(rs.getString(13));
                                Jdob.setText(rs.getString(11));
                                Jnid.setText(rs.getString(7));
                                Jsem.setText("Second");
                                Jhall.setText(rs.getString(15));
                                getImageName = rs.getString(16);
                                
                                if("active".equals(rs.getString("status"))){
                                   statusValue.setText("Regular");
                                }else{
                                   statusValue.setText("E-regular");
                                   statusValue.setForeground(Color.red);
                                }
                                
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
        
        }
        
        public void prints(){
            showComponent();
            System.out.println("prints");
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print Data");
            
            job.setPrintable((Graphics pg, PageFormat pf, int pageNum) -> {
                pf.setOrientation(PageFormat.LANDSCAPE);
                if(pageNum > 0){
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D g2 = (Graphics2D)pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.47,0.47);
                print.print(g2);
                return Printable.PAGE_EXISTS;
            });
            
            
            boolean ok = job.printDialog();
            if(ok){
                try{

                job.print();
                }
                catch (PrinterException ex){
                }
            }
        }
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        print = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        underText = new javax.swing.JLabel();
        forDeanfac = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        name7 = new javax.swing.JLabel();
        name6 = new javax.swing.JLabel();
        dot = new javax.swing.JLabel();
        dot7 = new javax.swing.JLabel();
        dot5 = new javax.swing.JLabel();
        dot1 = new javax.swing.JLabel();
        dot2 = new javax.swing.JLabel();
        dot4 = new javax.swing.JLabel();
        jnames = new javax.swing.JLabel();
        jphones = new javax.swing.JLabel();
        Jsession = new javax.swing.JLabel();
        Jreg = new javax.swing.JLabel();
        Jhall = new javax.swing.JLabel();
        Jsem = new javax.swing.JLabel();
        name11 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        name9 = new javax.swing.JLabel();
        JBlood = new javax.swing.JLabel();
        Jdob = new javax.swing.JLabel();
        Jnid = new javax.swing.JLabel();
        dot10 = new javax.swing.JLabel();
        name4 = new javax.swing.JLabel();
        dot11 = new javax.swing.JLabel();
        dot12 = new javax.swing.JLabel();
        name10 = new javax.swing.JLabel();
        name12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        Jid = new javax.swing.JLabel();
        name13 = new javax.swing.JLabel();
        dot3 = new javax.swing.JLabel();
        profilePic = new javax.swing.JLabel();
        name14 = new javax.swing.JLabel();
        statusValue = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        border2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        result = new javax.swing.JTable();
        datePrint = new javax.swing.JLabel();
        name5 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(440, 10));
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        print.setBackground(new java.awt.Color(255, 255, 255));
        print.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        print.setPreferredSize(new java.awt.Dimension(1300, 810));
        print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 38)); // NOI18N
        jLabel2.setText("Patuakhali Science & Technology University");
        print.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 790, 60));

        underText.setFont(new java.awt.Font("Tahoma", 3, 20)); // NOI18N
        underText.setText("Dumki-8602, Dumki, Patuakhali ");
        print.add(underText, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 350, 40));

        forDeanfac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        forDeanfac.setText("Computer Science & Engineering");
        print.add(forDeanfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 1050, 260, 30));

        name2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name2.setText("Name ");
        print.add(name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, 70, 30));

        name7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name7.setText("Session");
        print.add(name7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 420, 80, 30));

        name6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name6.setText("Reg");
        print.add(name6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, 80, 30));

        dot.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot.setText(":");
        print.add(dot, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 20, 30));

        dot7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot7.setText(":");
        print.add(dot7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 390, 20, -1));

        dot5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot5.setText(":");
        print.add(dot5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 20, 30));

        dot1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot1.setText(":");
        print.add(dot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 450, 20, 30));

        dot2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot2.setText(":");
        print.add(dot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 480, 20, 30));

        dot4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot4.setText(":");
        print.add(dot4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 450, 10, 30));

        jnames.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(jnames, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 200, 30));

        jphones.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(jphones, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 200, 30));

        Jsession.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jsession, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 200, 30));

        Jreg.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 200, 30));

        Jhall.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jhall, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 480, 200, 30));

        Jsem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jsem, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 450, 200, 30));

        name11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name11.setText("Blood");
        print.add(name11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 360, 60, 30));

        name.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name.setText("DOB");
        print.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 390, 60, 30));

        name9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name9.setText("Semister");
        print.add(name9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 450, 110, 30));

        JBlood.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(JBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 360, 200, 30));

        Jdob.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jdob, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 390, 200, 30));

        Jnid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 420, 200, 30));

        dot10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot10.setText(":");
        print.add(dot10, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 360, 20, 30));

        name4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name4.setText("Phone");
        print.add(name4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 80, 30));

        dot11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot11.setText(":");
        print.add(dot11, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 20, 30));

        dot12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot12.setText(":");
        print.add(dot12, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 390, 20, 30));

        name10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name10.setText("Nid");
        print.add(name10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 420, 60, 30));

        name12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name12.setText("Hall");
        print.add(name12, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 480, 80, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("This page is fully justified, this students details by PSTU ");
        print.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 1210, 470, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Student Signature");
        print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 1050, 180, 30));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        print.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 1040, 300, 10));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        print.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 1040, 320, 10));

        Jid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        print.add(Jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, 200, 30));

        name13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name13.setText("Id");
        print.add(name13, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, 80, 30));

        dot3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot3.setText(":");
        print.add(dot3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 20, 30));
        print.add(profilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 230, 220));

        name14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name14.setText(" Status : ");
        print.add(name14, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 80, 40));

        statusValue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        statusValue.setText("Regular");
        print.add(statusValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 500, 150, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        print.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 60, 200, 170));

        border2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/under line design .png"))); // NOI18N
        print.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 640, 60));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        result.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "          Semister", "Date", "GPA", "Grade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        result.setPreferredSize(new java.awt.Dimension(300, 275));
        result.setRowHeight(30);
        result.setRowMargin(20);
        result.setSelectionBackground(new java.awt.Color(255, 255, 255));
        result.getTableHeader().setResizingAllowed(false);
        jScrollPane1.setViewportView(result);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1080, 300));

        print.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 600, 1040, 300));

        datePrint.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        print.add(datePrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 220, 130, 30));

        name5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name5.setText("Date :");
        print.add(name5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 220, 60, 30));

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setOpaque(true);
        print.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -90, 1330, 1900));

        getContentPane().add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 1810));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

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
            java.util.logging.Logger.getLogger(print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(print.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new print("6666666").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JBlood;
    private javax.swing.JLabel Jdob;
    private javax.swing.JLabel Jhall;
    private javax.swing.JLabel Jid;
    private javax.swing.JLabel Jnid;
    private javax.swing.JLabel Jreg;
    private javax.swing.JLabel Jsem;
    private javax.swing.JLabel Jsession;
    private javax.swing.JLabel background;
    private javax.swing.JLabel border2;
    private javax.swing.JLabel datePrint;
    private javax.swing.JLabel dot;
    private javax.swing.JLabel dot1;
    private javax.swing.JLabel dot10;
    private javax.swing.JLabel dot11;
    private javax.swing.JLabel dot12;
    private javax.swing.JLabel dot2;
    private javax.swing.JLabel dot3;
    private javax.swing.JLabel dot4;
    private javax.swing.JLabel dot5;
    private javax.swing.JLabel dot7;
    private javax.swing.JLabel forDeanfac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jnames;
    private javax.swing.JLabel jphones;
    private javax.swing.JLabel name;
    private javax.swing.JLabel name10;
    private javax.swing.JLabel name11;
    private javax.swing.JLabel name12;
    private javax.swing.JLabel name13;
    private javax.swing.JLabel name14;
    private javax.swing.JLabel name2;
    private javax.swing.JLabel name4;
    private javax.swing.JLabel name5;
    private javax.swing.JLabel name6;
    private javax.swing.JLabel name7;
    private javax.swing.JLabel name9;
    private javax.swing.JPanel print;
    private javax.swing.JLabel profilePic;
    private javax.swing.JTable result;
    private javax.swing.JLabel statusValue;
    private javax.swing.JLabel underText;
    // End of variables declaration//GEN-END:variables
}
