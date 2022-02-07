/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import ImageInsert_ImageShow.Insert;
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
public class teacherDetailsPrint extends javax.swing.JFrame {

    /**
     * Creates new form print
     */
    public String getId = "";
    public String getImageName = "";
     String s;
     ResultSet rs;
     String date;
    public teacherDetailsPrint(String id) {
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
          LocalDateTime now = LocalDateTime.now();  
          date = dtf.format(now); 
   
        System.out.println("Constructor");
        getId = id;
        initComponents();
    }
    
    
        public class func{
            public ResultSet find(String id) throws SQLException{

                conn con = new conn();
                String query = "SELECT teacher.*,users.* FROM teacher,users WHERE teacher.uid = users.uid AND users.uid =?";
                PreparedStatement ps=con.c.prepareStatement(query);
                ps.setString(1,id);
                rs  = ps.executeQuery();
                return rs;

            }
        }
        
        public void showComponent(){
                System.out.println("formComponentShown");
                    // Call func meathoad
                   teacherDetailsPrint.func f = new teacherDetailsPrint.func();
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
                                
                                
                                teacherPrintName.setText(rs.getString("name"));
                                teacherPrintPhone.setText(rs.getString("phone"));
                                teacherPrintEmail.setText(rs.getString("email"));
                                teacherPrintReg.setText(rs.getString("reg"));
                                teacherPrintId.setText(rs.getString("id"));
                                teacherPrintBlood.setText(rs.getString("blood"));
                                teacherPrintDob.setText(rs.getString("dob"));
                                teacherPrintNid.setText(rs.getString("nid"));
                                teacherPrintFac.setText(rs.getString("fac"));
                                teacherPrintDept.setText(rs.getString("dept"));
                                
                                if("active".equals(rs.getString("status"))){
                                   statusValue.setText("Regular");
                                }else{
                                   statusValue.setText("E-regular");
                                }
                                
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
        
        }
        
        public void prints(){
            showComponent();
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print Data");
            
            job.setPrintable((Graphics pg, PageFormat pf, int pageNum) -> {
                pf.setOrientation(PageFormat.PORTRAIT);
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
                }catch (PrinterException ex){
                }
            }
        }
        
        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        print = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        underText = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        name6 = new javax.swing.JLabel();
        dot = new javax.swing.JLabel();
        dot7 = new javax.swing.JLabel();
        dot1 = new javax.swing.JLabel();
        dot2 = new javax.swing.JLabel();
        dot4 = new javax.swing.JLabel();
        teacherPrintPhone = new javax.swing.JLabel();
        teacherPrintReg = new javax.swing.JLabel();
        teacherPrintDept = new javax.swing.JLabel();
        teacherPrintFac = new javax.swing.JLabel();
        name11 = new javax.swing.JLabel();
        name9 = new javax.swing.JLabel();
        teacherPrintBlood = new javax.swing.JLabel();
        teacherPrintDob = new javax.swing.JLabel();
        teacherPrintNid = new javax.swing.JLabel();
        dot10 = new javax.swing.JLabel();
        name4 = new javax.swing.JLabel();
        dot11 = new javax.swing.JLabel();
        dot12 = new javax.swing.JLabel();
        name10 = new javax.swing.JLabel();
        name12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        teacherPrintId = new javax.swing.JLabel();
        name13 = new javax.swing.JLabel();
        dot3 = new javax.swing.JLabel();
        profilePic = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        name14 = new javax.swing.JLabel();
        statusValue = new javax.swing.JLabel();
        name15 = new javax.swing.JLabel();
        dot8 = new javax.swing.JLabel();
        teacherPrintEmail = new javax.swing.JLabel();
        name1 = new javax.swing.JLabel();
        datePrint = new javax.swing.JLabel();
        border2 = new javax.swing.JLabel();
        name5 = new javax.swing.JLabel();
        teacherPrintName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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
        print.setPreferredSize(new java.awt.Dimension(1300, 810));
        print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 38)); // NOI18N
        jLabel2.setText("Patuakhali Science & Technology University");
        print.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 790, 60));

        underText.setFont(new java.awt.Font("Tahoma", 3, 20)); // NOI18N
        underText.setText("Dumki-8602, Dumki, Patuakhali ");
        print.add(underText, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 340, 40));

        name2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name2.setText("Name ");
        print.add(name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 350, 70, 30));

        name6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name6.setText("Reg");
        print.add(name6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 80, -1));

        dot.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot.setText(":");
        print.add(dot, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 20, 30));

        dot7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot7.setText(":");
        print.add(dot7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 20, -1));

        dot1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot1.setText(":");
        print.add(dot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, 20, 40));

        dot2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot2.setText(":");
        print.add(dot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 440, 20, 20));

        dot4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot4.setText(":");
        print.add(dot4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 410, 10, 30));

        teacherPrintPhone.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 200, 30));

        teacherPrintReg.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 440, 200, 30));

        teacherPrintDept.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintDept, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 440, 230, 30));

        teacherPrintFac.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 410, 230, 30));

        name11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name11.setText("Blood");
        print.add(name11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 350, 60, 30));

        name9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name9.setText("Faculty");
        print.add(name9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 410, 100, 30));

        teacherPrintBlood.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 350, 230, 30));

        teacherPrintDob.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 380, 230, 30));

        teacherPrintNid.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, 200, 30));

        dot10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot10.setText(":");
        print.add(dot10, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 350, 20, 30));

        name4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name4.setText("Phone");
        print.add(name4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 80, 40));

        dot11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot11.setText(":");
        print.add(dot11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 20, 30));

        dot12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot12.setText(":");
        print.add(dot12, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 380, 20, 30));

        name10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name10.setText("Nid");
        print.add(name10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 470, 60, -1));

        name12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name12.setText("Depertment");
        print.add(name12, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 440, 140, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("This page is fully justified, this teachers details by PSTU ");
        print.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 1310, 470, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Teacheacher Signature");
        print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 1050, 250, 30));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        print.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 1040, 310, 10));

        teacherPrintId.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintId, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 200, 30));

        name13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name13.setText("Id");
        print.add(name13, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 410, 80, -1));

        dot3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot3.setText(":");
        print.add(dot3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 20, 40));
        print.add(profilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 230, 220));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        print.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 200, 170));

        name14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name14.setText("Status  :");
        print.add(name14, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 80, 40));

        statusValue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(statusValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 510, 150, 30));

        name15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name15.setText("Email");
        print.add(name15, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 470, 100, 30));

        dot8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot8.setText(":");
        print.add(dot8, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 470, 10, 30));

        teacherPrintEmail.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 470, 230, 30));

        name1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name1.setText("DOB");
        print.add(name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 380, 60, 30));

        datePrint.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        print.add(datePrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 200, 130, 30));

        border2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/under line design .png"))); // NOI18N
        print.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 640, 60));

        name5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        name5.setText("Date :");
        print.add(name5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 200, 60, 30));

        teacherPrintName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        print.add(teacherPrintName, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 200, 30));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setOpaque(true);
        print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 1810));

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
 

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new teacherDetailsPrint("1802045").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel dot7;
    private javax.swing.JLabel dot8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel name1;
    private javax.swing.JLabel name10;
    private javax.swing.JLabel name11;
    private javax.swing.JLabel name12;
    private javax.swing.JLabel name13;
    private javax.swing.JLabel name14;
    private javax.swing.JLabel name15;
    private javax.swing.JLabel name2;
    private javax.swing.JLabel name4;
    private javax.swing.JLabel name5;
    private javax.swing.JLabel name6;
    private javax.swing.JLabel name9;
    private javax.swing.JPanel print;
    private javax.swing.JLabel profilePic;
    private javax.swing.JLabel statusValue;
    private javax.swing.JLabel teacherPrintBlood;
    private javax.swing.JLabel teacherPrintDept;
    private javax.swing.JLabel teacherPrintDob;
    private javax.swing.JLabel teacherPrintEmail;
    private javax.swing.JLabel teacherPrintFac;
    private javax.swing.JLabel teacherPrintId;
    private javax.swing.JLabel teacherPrintName;
    private javax.swing.JLabel teacherPrintNid;
    private javax.swing.JLabel teacherPrintPhone;
    private javax.swing.JLabel teacherPrintReg;
    private javax.swing.JLabel underText;
    // End of variables declaration//GEN-END:variables
}
