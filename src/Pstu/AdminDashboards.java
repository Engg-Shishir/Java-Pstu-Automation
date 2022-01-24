/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author ASUS
 */
public class AdminDashboards extends javax.swing.JFrame {

    /**
     * Creates new form AdminDashboards
     */
    public String getadminusername = "";
    public String identity = "";
    public String dateFromDatePicker = "";
    
    
    ResultSet rs;
    String imagePath;
    
    
    	Connection con = null;
	FileInputStream fs=null;
	PreparedStatement ps=null;
    
    
    
    ArrayList<String> column = new ArrayList<String>();
   
   
    public AdminDashboards(String username, String identitys) {
        getadminusername = username;
        identity = identitys;
        initComponents();
    }
    
    public  void alert(String check, String mes){
        if("false".equals(check)){
            alert.setVisible(false);
            alertLogo.setVisible(false);
            alertClose.setVisible(false);
            alert.setBackground(Color.white);
            alertText.setText("");
        }else{
            alert.setVisible(true);
            alertLogo.setVisible(true);
            alertClose.setVisible(true);
            alert.setBackground(new Color(102,0,102));
            alertText.setText(mes); 
        }
    }
    
    public  void alertSuccess(String mes){
        alertSuccess.setVisible(true);
        alertSuccessText.setText(mes);
        alertsuccessLogo.setVisible(true);
        alertSuccessClose.setVisible(true);
    }
    public boolean count(String s, int n, String msg)
    {
           if(s.length()==n){
               alert("false","");
           }else{
               alert("true",msg); 
               return false;
           }
            return true;
    }
    
    public void resetField(){
       nameVal.setText("");
       fnameVal.setText("");
       mnameVal.setText("");
       addrVal.setText("");
       nidVal.setText("");
       idVal.setText("");
       regVal.setText("");
       sessionVal.setSelectedIndex(0);
       bloodVal.setSelectedIndex(0);
       facVal.setSelectedIndex(0);
       hallVal.setSelectedIndex(0);
    }
    
         public void insertStudent(ArrayList<String> languages){
             
            String[] data = new String[languages.size()];
            data = languages.toArray(data);

            try{
                conn cc = new conn();
                
                    String query = "INSERT INTO `student`(`name`, `fname`, `mname`, `addr`, `nid`,`dob`, `roll`, `reg`, `session`, `blood`, `fac`, `hall`) "
                            + "VALUES ('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"')";
                    int i = cc.s.executeUpdate(query);
                    if(i==1){
                        alertSuccess("Woo! Student Added Successfully");
                    }           
            }catch(SQLException ee){
                System.out.println("The error is:"+ee);
            }
        }
         
         
        public class func{
            public ResultSet find(String usernames, String identy) throws SQLException{

                try{      
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","");

                    PreparedStatement ps=con.prepareStatement("SELECT * FROM users where(email='"+usernames+"' and role='"+identy+"') "
                    + "or(uid='"+usernames+"' and role='"+identy+"')"
                    + "or(username='"+usernames+"' and role='"+identy+"')");

                    rs  = ps.executeQuery(); 
                }catch(ClassNotFoundException ev){
                    
                }
                return rs;

            }
        }
        
        
    public ImageIcon ResizeImage(String imafePaths){
        ImageIcon MyImage = new ImageIcon(imafePaths);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(UpdateProfilePic.getWidth(), UpdateProfilePic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
    
    
    
    public class test {
            public ResultSet finds(String usernames, String identy) throws SQLException, ClassNotFoundException, FileNotFoundException{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","");
                    System.out.println(imagePath);
                    File f=new File(imagePath);
                    fs=new FileInputStream(f);
                    ps= con.prepareStatement("UPDATE users SET photo=? Where email=?");
                    
                    ps.setBinaryStream(1,fs,(int)f.length());
                    ps.setString(2, getadminusername);
                    ps.executeUpdate();
//                    String query = "UPDATE `users` SET photo=LOAD_FILE(imagePath) WHERE email='s@gmail.com'";
//                    PreparedStatement ps = con.prepareStatement(query);
//                     ps.executeUpdate(query); 
                    
                return rs;
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

        SideBarPanel = new javax.swing.JPanel();
        profilepic = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        view = new javax.swing.JButton();
        student1 = new javax.swing.JButton();
        teacher1 = new javax.swing.JButton();
        faculty1 = new javax.swing.JButton();
        Hall1 = new javax.swing.JButton();
        library1 = new javax.swing.JButton();
        bank1 = new javax.swing.JButton();
        health1 = new javax.swing.JButton();
        administrator1 = new javax.swing.JButton();
        organaization1 = new javax.swing.JButton();
        transport1 = new javax.swing.JButton();
        others1 = new javax.swing.JButton();
        bg = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        dashboardPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        profileViewPanel = new javax.swing.JPanel();
        bottomrightborder = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        bottomrightborder1 = new javax.swing.JLabel();
        UpdateProfilePic = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        studentPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        addStudentBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        serchIdField = new javax.swing.JTextField();
        addNewStudent2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        teacherPanel = new javax.swing.JPanel();
        serchIdField1 = new javax.swing.JTextField();
        addNewStudent3 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jComboBox15 = new javax.swing.JComboBox<>();
        jComboBox16 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jComboBox18 = new javax.swing.JComboBox<>();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        addStudentBtn2 = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        facultyPanel = new javax.swing.JPanel();
        hallPanel = new javax.swing.JPanel();
        LibraryPanel = new javax.swing.JPanel();
        bankPanel = new javax.swing.JPanel();
        healthPanel = new javax.swing.JPanel();
        AdministrationPanel = new javax.swing.JPanel();
        organaizationPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        transportPanel = new javax.swing.JPanel();
        othersPanel = new javax.swing.JPanel();
        addStudent = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        nameVal = new javax.swing.JTextField();
        fnameVal = new javax.swing.JTextField();
        mnameVal = new javax.swing.JTextField();
        addrVal = new javax.swing.JTextField();
        nidVal = new javax.swing.JTextField();
        dobVal = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        idVal = new javax.swing.JTextField();
        regVal = new javax.swing.JTextField();
        sessionVal = new javax.swing.JComboBox<>();
        bloodVal = new javax.swing.JComboBox<>();
        facVal = new javax.swing.JComboBox<>();
        hallVal = new javax.swing.JComboBox<>();
        alertSuccessText = new javax.swing.JLabel();
        alertsuccessLogo = new javax.swing.JLabel();
        alertSuccessClose = new javax.swing.JButton();
        alertSuccess = new javax.swing.JLabel();
        alertText = new javax.swing.JLabel();
        alertLogo = new javax.swing.JLabel();
        alertClose = new javax.swing.JButton();
        alert = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1465, 855));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SideBarPanel.setPreferredSize(new java.awt.Dimension(350, 855));
        SideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        SideBarPanel.add(profilepic, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 200, 180));

        logout.setBackground(new java.awt.Color(14, 0, 82));
        logout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/logout.png"))); // NOI18N
        logout.setText("Logout");
        logout.setBorder(null);
        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        SideBarPanel.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, 40));

        dashboard.setBackground(new java.awt.Color(14, 0, 82));
        dashboard.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dashboard.setForeground(new java.awt.Color(255, 255, 255));
        dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/home.png"))); // NOI18N
        dashboard.setText("Dashboard");
        dashboard.setBorder(null);
        dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        SideBarPanel.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, 40));

        view.setBackground(new java.awt.Color(14, 0, 82));
        view.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view.setForeground(new java.awt.Color(255, 255, 255));
        view.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        view.setText("View");
        view.setBorder(null);
        view.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });
        SideBarPanel.add(view, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

        student1.setBackground(new java.awt.Color(14, 0, 82));
        student1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        student1.setForeground(new java.awt.Color(255, 255, 255));
        student1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/student.png"))); // NOI18N
        student1.setText("Student");
        student1.setBorder(null);
        student1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        student1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(student1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 40));

        teacher1.setBackground(new java.awt.Color(14, 0, 82));
        teacher1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        teacher1.setForeground(new java.awt.Color(255, 255, 255));
        teacher1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/teacher.png"))); // NOI18N
        teacher1.setText("Teacher");
        teacher1.setBorder(null);
        teacher1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacher1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(teacher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 40));

        faculty1.setBackground(new java.awt.Color(14, 0, 82));
        faculty1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        faculty1.setForeground(new java.awt.Color(255, 255, 255));
        faculty1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/faculty.png"))); // NOI18N
        faculty1.setText("Faculty");
        faculty1.setBorder(null);
        faculty1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        faculty1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faculty1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(faculty1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 140, 40));

        Hall1.setBackground(new java.awt.Color(14, 0, 82));
        Hall1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Hall1.setForeground(new java.awt.Color(255, 255, 255));
        Hall1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/hall.png"))); // NOI18N
        Hall1.setText(" Hall");
        Hall1.setBorder(null);
        Hall1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Hall1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hall1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(Hall1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 140, 40));

        library1.setBackground(new java.awt.Color(14, 0, 82));
        library1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        library1.setForeground(new java.awt.Color(255, 255, 255));
        library1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/library.png"))); // NOI18N
        library1.setText("Library");
        library1.setBorder(null);
        library1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        library1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                library1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(library1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 140, 40));

        bank1.setBackground(new java.awt.Color(14, 0, 82));
        bank1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bank1.setForeground(new java.awt.Color(255, 255, 255));
        bank1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        bank1.setText("Bank");
        bank1.setBorder(null);
        bank1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bank1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bank1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(bank1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, 140, 40));

        health1.setBackground(new java.awt.Color(14, 0, 82));
        health1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        health1.setForeground(new java.awt.Color(255, 255, 255));
        health1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/doctor.png"))); // NOI18N
        health1.setText("Health care");
        health1.setBorder(null);
        health1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        health1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                health1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(health1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 140, 40));

        administrator1.setBackground(new java.awt.Color(14, 0, 82));
        administrator1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        administrator1.setForeground(new java.awt.Color(255, 255, 255));
        administrator1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bulb.png"))); // NOI18N
        administrator1.setText("Administration");
        administrator1.setBorder(null);
        administrator1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        administrator1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administrator1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(administrator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 510, 140, 40));

        organaization1.setBackground(new java.awt.Color(14, 0, 82));
        organaization1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        organaization1.setForeground(new java.awt.Color(255, 255, 255));
        organaization1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/organaization.png"))); // NOI18N
        organaization1.setText("Oraniztion");
        organaization1.setBorder(null);
        organaization1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organaization1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organaization1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(organaization1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 140, 40));

        transport1.setBackground(new java.awt.Color(14, 0, 82));
        transport1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        transport1.setForeground(new java.awt.Color(255, 255, 255));
        transport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bus.png"))); // NOI18N
        transport1.setText("Transport");
        transport1.setBorder(null);
        transport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transport1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(transport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 560, 140, 40));

        others1.setBackground(new java.awt.Color(14, 0, 82));
        others1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        others1.setForeground(new java.awt.Color(255, 255, 255));
        others1.setText("Others");
        others1.setBorder(null);
        others1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        others1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                others1ActionPerformed(evt);
            }
        });
        SideBarPanel.add(others1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 300, 40));

        bg.setBackground(new java.awt.Color(0, 5, 42));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        bg.setOpaque(true);
        SideBarPanel.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 855));

        getContentPane().add(SideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 860));

        contentPanel.setBackground(new java.awt.Color(159, 0, 87));
        contentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboardPanel.setBackground(new java.awt.Color(14, 0, 82));
        dashboardPanel.setPreferredSize(new java.awt.Dimension(1200, 855));
        dashboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("Student");
        dashboardPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 127, 300, 87));

        jTabbedPane.addTab("tab1", dashboardPanel);

        profileViewPanel.setBackground(new java.awt.Color(0, 5, 42));
        profileViewPanel.setPreferredSize(new java.awt.Dimension(1110, 890));
        profileViewPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bottomrightborder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border left bottom .png"))); // NOI18N
        profileViewPanel.add(bottomrightborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 330, 320));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/pastu-admin.png"))); // NOI18N
        jLabel16.setText("jLabel16");
        profileViewPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 400, 70));

        bottomrightborder1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        profileViewPanel.add(bottomrightborder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 470, 330, 340));

        UpdateProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        profileViewPanel.add(UpdateProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 200, 180));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Change Profile :");
        profileViewPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 220, 150, 30));

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/folder.png"))); // NOI18N
        jLabel56.setText("jLabel56");
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
        });
        profileViewPanel.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 220, 40, 30));

        jTabbedPane.addTab("tab2", profileViewPanel);

        studentPanel.setBackground(new java.awt.Color(14, 0, 82));
        studentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/student.png"))); // NOI18N
        studentPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 410, 60));

        addStudentBtn.setBackground(new java.awt.Color(0, 5, 42));
        addStudentBtn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addStudentBtn.setForeground(new java.awt.Color(255, 255, 255));
        addStudentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/addNew.png"))); // NOI18N
        addStudentBtn.setText(" Add New");
        addStudentBtn.setBorder(null);
        addStudentBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentBtnActionPerformed(evt);
            }
        });
        studentPanel.add(addStudentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 170, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        studentPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 390, 270));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jComboBox2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CSE Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        studentPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, 200, 200));

        serchIdField.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        serchIdField.setText("     Search with student id");
        serchIdField.setBorder(null);
        serchIdField.setMargin(new java.awt.Insets(2, 20, 2, 2));
        serchIdField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchIdFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchIdFieldFocusLost(evt);
            }
        });
        serchIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchIdFieldActionPerformed(evt);
            }
        });
        studentPanel.add(serchIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

        addNewStudent2.setBackground(new java.awt.Color(0, 5, 42));
        addNewStudent2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        addNewStudent2.setActionCommand("Add Student");
        addNewStudent2.setBorder(null);
        addNewStudent2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewStudent2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStudent2ActionPerformed(evt);
            }
        });
        studentPanel.add(addNewStudent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jComboBox4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

        jComboBox5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DVM Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

        jComboBox6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BBA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

        jComboBox7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

        jComboBox8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NFS Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jComboBox3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jComboBox9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LLA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

        jComboBox10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AH Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });
        studentPanel.add(jComboBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        jTabbedPane.addTab("tab3", studentPanel);

        teacherPanel.setBackground(new java.awt.Color(14, 0, 82));
        teacherPanel.setPreferredSize(new java.awt.Dimension(1100, 770));
        teacherPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        serchIdField1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        serchIdField1.setText("     Search with Teacher id");
        serchIdField1.setBorder(null);
        serchIdField1.setMargin(new java.awt.Insets(2, 20, 2, 2));
        serchIdField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchIdField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchIdField1FocusLost(evt);
            }
        });
        serchIdField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchIdField1ActionPerformed(evt);
            }
        });
        teacherPanel.add(serchIdField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

        addNewStudent3.setBackground(new java.awt.Color(0, 5, 42));
        addNewStudent3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addNewStudent3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        addNewStudent3.setActionCommand("Add Student");
        addNewStudent3.setBorder(null);
        addNewStudent3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewStudent3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStudent3ActionPerformed(evt);
            }
        });
        teacherPanel.add(addNewStudent3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jComboBox11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NFS Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

        jComboBox12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

        jComboBox13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BBA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

        jComboBox14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DVM Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

        jComboBox15.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

        jComboBox16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CSE Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jComboBox17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

        jComboBox18.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LLA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

        jComboBox19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AH Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });
        teacherPanel.add(jComboBox19, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminTeacher.png"))); // NOI18N
        teacherPanel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 350, 350));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        teacherPanel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 180, -1, 80));

        addStudentBtn2.setBackground(new java.awt.Color(0, 5, 42));
        addStudentBtn2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addStudentBtn2.setForeground(new java.awt.Color(255, 255, 255));
        addStudentBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/addNew.png"))); // NOI18N
        addStudentBtn2.setText("  Add New");
        addStudentBtn2.setBorder(null);
        addStudentBtn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudentBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentBtn2ActionPerformed(evt);
            }
        });
        teacherPanel.add(addStudentBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 220, 50));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel57.setText("jLabel8");
        teacherPanel.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 200, 200));

        jTabbedPane.addTab("tab4", teacherPanel);

        facultyPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout facultyPanelLayout = new javax.swing.GroupLayout(facultyPanel);
        facultyPanel.setLayout(facultyPanelLayout);
        facultyPanelLayout.setHorizontalGroup(
            facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        facultyPanelLayout.setVerticalGroup(
            facultyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab5", facultyPanel);

        hallPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout hallPanelLayout = new javax.swing.GroupLayout(hallPanel);
        hallPanel.setLayout(hallPanelLayout);
        hallPanelLayout.setHorizontalGroup(
            hallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        hallPanelLayout.setVerticalGroup(
            hallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab6", hallPanel);

        LibraryPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout LibraryPanelLayout = new javax.swing.GroupLayout(LibraryPanel);
        LibraryPanel.setLayout(LibraryPanelLayout);
        LibraryPanelLayout.setHorizontalGroup(
            LibraryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        LibraryPanelLayout.setVerticalGroup(
            LibraryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab7", LibraryPanel);

        bankPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout bankPanelLayout = new javax.swing.GroupLayout(bankPanel);
        bankPanel.setLayout(bankPanelLayout);
        bankPanelLayout.setHorizontalGroup(
            bankPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        bankPanelLayout.setVerticalGroup(
            bankPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab8", bankPanel);

        healthPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout healthPanelLayout = new javax.swing.GroupLayout(healthPanel);
        healthPanel.setLayout(healthPanelLayout);
        healthPanelLayout.setHorizontalGroup(
            healthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        healthPanelLayout.setVerticalGroup(
            healthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab9", healthPanel);

        AdministrationPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout AdministrationPanelLayout = new javax.swing.GroupLayout(AdministrationPanel);
        AdministrationPanel.setLayout(AdministrationPanelLayout);
        AdministrationPanelLayout.setHorizontalGroup(
            AdministrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        AdministrationPanelLayout.setVerticalGroup(
            AdministrationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab10", AdministrationPanel);

        organaizationPanel.setBackground(new java.awt.Color(14, 0, 82));
        organaizationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Dashboard");
        organaizationPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 183, 389, 82));

        jTabbedPane.addTab("tab11", organaizationPanel);

        transportPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout transportPanelLayout = new javax.swing.GroupLayout(transportPanel);
        transportPanel.setLayout(transportPanelLayout);
        transportPanelLayout.setHorizontalGroup(
            transportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        transportPanelLayout.setVerticalGroup(
            transportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab12", transportPanel);

        othersPanel.setBackground(new java.awt.Color(14, 0, 82));

        javax.swing.GroupLayout othersPanelLayout = new javax.swing.GroupLayout(othersPanel);
        othersPanel.setLayout(othersPanelLayout);
        othersPanelLayout.setHorizontalGroup(
            othersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        othersPanelLayout.setVerticalGroup(
            othersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("tab13", othersPanel);

        addStudent.setBackground(new java.awt.Color(14, 0, 82));
        addStudent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/addStudent.png"))); // NOI18N
        addStudent.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 470, 80));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        jLabel30.setText("jLabel5");
        addStudent.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 390, 260));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText(":");
        addStudent.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 20, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText(":");
        addStudent.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 20, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText(":");
        addStudent.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 470, 20, 40));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText(":");
        addStudent.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 520, 20, 40));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText(":");
        addStudent.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, 20, 30));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText(":");
        addStudent.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 20, 40));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Birth Date");
        addStudent.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 90, 40));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Nid No ");
        addStudent.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 70, 40));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Address");
        addStudent.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 80, 40));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Mother Name");
        addStudent.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 120, 40));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Father Name");
        addStudent.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 110, 40));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Name");
        addStudent.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 60, 40));

        nameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        nameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameValActionPerformed(evt);
            }
        });
        addStudent.add(nameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 300, 40));

        fnameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fnameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fnameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameValActionPerformed(evt);
            }
        });
        addStudent.add(fnameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 300, 40));

        mnameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        mnameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnameValActionPerformed(evt);
            }
        });
        addStudent.add(mnameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, 300, 40));

        addrVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addrVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addrVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrValActionPerformed(evt);
            }
        });
        addStudent.add(addrVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 300, 40));

        nidVal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nidVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nidVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nidValActionPerformed(evt);
            }
        });
        addStudent.add(nidVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, 300, 40));

        dobVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dobVal.setDateFormatString("dd-MM-yyyy");
        dobVal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dobValMouseClicked(evt);
            }
        });
        dobVal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dobValPropertyChange(evt);
            }
        });
        addStudent.add(dobVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 300, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText(":");
        addStudent.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, 20, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText(":");
        addStudent.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 430, 20, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText(":");
        addStudent.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 20, 40));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText(":");
        addStudent.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 520, 20, 30));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText(":");
        addStudent.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 570, 20, 40));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText(":");
        addStudent.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 620, 20, 30));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Faculty");
        addStudent.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 570, 60, 40));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Blood");
        addStudent.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 520, 50, 30));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Session");
        addStudent.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 470, 70, 40));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Reg");
        addStudent.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 40, 40));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Id");
        addStudent.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 30, 40));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Hall");
        addStudent.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 40, 30));

        idVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        idVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        idVal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idValFocusLost(evt);
            }
        });
        idVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idValActionPerformed(evt);
            }
        });
        idVal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idValKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idValKeyTyped(evt);
            }
        });
        addStudent.add(idVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, 300, 40));

        regVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        regVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        regVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regValActionPerformed(evt);
            }
        });
        addStudent.add(regVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 300, 40));

        sessionVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        sessionVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "2015-2106", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", "2021-2022", "2022-2023", "2023-2024", "2024-2025", "2025-2026", " " }));
        sessionVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent.add(sessionVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, 300, 40));

        bloodVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bloodVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "A +", "A -", "B +", "B -", "O +", "O -", "AB +", "AB -", " " }));
        bloodVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent.add(bloodVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 300, 40));

        facVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Computer Science & Engineering", "Agriculture", "Business Administration", "Animal Science and Veterinary Medicine", "Fisheries", "Environmental Science and Disaster Management", "Nutrition and Food Science", "Land Management and Administration" }));
        facVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent.add(facVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 570, 300, 40));

        hallVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        hallVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Sher-E-Bangla Hall 1", "Sher-E-Bangla Hall 2", "Keramat Ali Hall", "Bangabandhu Sheikh Mujibor Rahman Hall", "Kobi Begum Sufia Kamal Hall", "Sheikh Fajilatunnesa Mujib Hall", "Captain Mohiuddin Jahangir Hall", "Sheikh Fajilatunnesa Mujib Hall-Babuganj", " " }));
        hallVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent.add(hallVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 620, 300, 40));

        alertSuccessText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        alertSuccessText.setForeground(new java.awt.Color(255, 255, 255));
        addStudent.add(alertSuccessText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 360, 50));

        alertsuccessLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/successlogos.png"))); // NOI18N
        alertsuccessLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent.add(alertsuccessLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 50, 40));

        alertSuccessClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        alertSuccessClose.setBorder(null);
        alertSuccessClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alertSuccessClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertSuccessCloseActionPerformed(evt);
            }
        });
        addStudent.add(alertSuccessClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 40, 40));

        alertSuccess.setBackground(new java.awt.Color(0, 102, 51));
        alertSuccess.setOpaque(true);
        addStudent.add(alertSuccess, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 490, 60));

        alertText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        alertText.setForeground(new java.awt.Color(255, 255, 255));
        addStudent.add(alertText, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 370, 60));

        alertLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/warning.png"))); // NOI18N
        alertLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent.add(alertLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 50, 60));

        alertClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        alertClose.setBorder(null);
        alertClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alertClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertCloseActionPerformed(evt);
            }
        });
        addStudent.add(alertClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 40, 40));

        alert.setBackground(new java.awt.Color(102, 0, 102));
        alert.setOpaque(true);
        addStudent.add(alert, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 490, 60));

        jButton1.setBackground(new java.awt.Color(0, 5, 42));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        jButton1.setText("Back");
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        addStudent.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 690, 100, 40));

        jButton2.setBackground(new java.awt.Color(0, 5, 42));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/save.png"))); // NOI18N
        jButton2.setText("  Submit");
        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        addStudent.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 680, 100, 40));

        jTabbedPane.addTab("tab14", addStudent);

        contentPanel.add(jTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 1115, 890));

        getContentPane().add(contentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, -35, 1115, 890));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        dispose();
                login log = new login();
                log.setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

    private void Hall1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hall1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(5);
    }//GEN-LAST:event_Hall1ActionPerformed

    private void library1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_library1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(6);
    }//GEN-LAST:event_library1ActionPerformed

    private void faculty1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faculty1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(4);
    }//GEN-LAST:event_faculty1ActionPerformed

    private void teacher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacher1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(3);
    }//GEN-LAST:event_teacher1ActionPerformed

    private void bank1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bank1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(7);
    }//GEN-LAST:event_bank1ActionPerformed

    private void health1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_health1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(8);
    }//GEN-LAST:event_health1ActionPerformed

    private void administrator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administrator1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(9);
    }//GEN-LAST:event_administrator1ActionPerformed

    private void organaization1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organaization1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(10);
    }//GEN-LAST:event_organaization1ActionPerformed

    private void transport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transport1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(11);
    }//GEN-LAST:event_transport1ActionPerformed

    private void others1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_others1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(12);
    }//GEN-LAST:event_others1ActionPerformed

    private void student1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_student1ActionPerformed

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
        // TODO add your handling code here:
         jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_viewActionPerformed

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_dashboardActionPerformed

    private void addStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtnActionPerformed
        // TODO add your handling code here:        
        
        alert("false","");
        alertSuccess.setVisible(false);
        alertSuccessText.setText("");
        alertsuccessLogo.setVisible(false);
        alertSuccessClose.setVisible(false);
        
        
        jTabbedPane.setSelectedIndex(13);
    }//GEN-LAST:event_addStudentBtnActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void serchIdFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdFieldFocusGained
        // TODO add your handling code here:
        serchIdField.setText("");
    }//GEN-LAST:event_serchIdFieldFocusGained

    private void serchIdFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdFieldFocusLost
        // TODO add your handling code here:

        //        serchIdField.setText("   Search with teacher id");
    }//GEN-LAST:event_serchIdFieldFocusLost

    private void serchIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchIdFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdFieldActionPerformed

    private void addNewStudent2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStudent2ActionPerformed
        // TODO add your handling code here:
        System.out.println(serchIdField.getText());
        new print(serchIdField.getText()).setVisible(true);
        serchIdField.setText("Search with student id");
    }//GEN-LAST:event_addNewStudent2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void serchIdField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdField1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField1FocusGained

    private void serchIdField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdField1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField1FocusLost

    private void serchIdField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchIdField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField1ActionPerformed

    private void addNewStudent3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStudent3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addNewStudent3ActionPerformed

    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jComboBox17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox17ActionPerformed

    private void jComboBox18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox18ActionPerformed

    private void jComboBox19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox19ActionPerformed

    private void addStudentBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addStudentBtn2ActionPerformed

    private void nameValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameValActionPerformed

    private void fnameValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameValActionPerformed

    private void mnameValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnameValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnameValActionPerformed

    private void addrValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addrValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addrValActionPerformed

    private void nidValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nidValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nidValActionPerformed

    private void dobValMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dobValMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dobValMouseClicked

    private void dobValPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dobValPropertyChange
        if ("date".equals(evt.getPropertyName())){
            Date getDate =  dobVal.getDate();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFromDatePicker = dateFormat.format(getDate);
        }
    }//GEN-LAST:event_dobValPropertyChange

    private void idValFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idValFocusLost
        // TODO add your handling code here:

        //        System.out.println("Focus Lost");
    }//GEN-LAST:event_idValFocusLost

    private void idValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idValActionPerformed

    private void idValKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idValKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_idValKeyPressed

    private void idValKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idValKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_idValKeyTyped

    private void regValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regValActionPerformed

    private void alertCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertCloseActionPerformed
        // TODO add your handling code here:
        alert("false","");
    }//GEN-LAST:event_alertCloseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String name = nameVal.getText();
        String fname = fnameVal.getText();
        String mname = mnameVal.getText();
        String id = idVal.getText();
        String reg =  regVal.getText();
        String addr = addrVal.getText();
        String nid =  nidVal.getText();
        String session =  (String)sessionVal.getSelectedItem();
        String fac = (String)facVal.getSelectedItem();
        String hall = (String)hallVal.getSelectedItem();
        String blood = (String)bloodVal.getSelectedItem();
        String dob = dateFromDatePicker;
             

        
        String regexAlphabet = "^[A-Za-z_ ]+$";
        String regexNumber = "^[0-9]+$";
        
        
            if(name.matches(regexAlphabet)){ // Check name,fname,mname only have Alpahabet or not
            column.add(name);
            if(fname.matches(regexAlphabet)){ 
                column.add(fname);
                if(mname.matches(regexAlphabet)){ 
                    column.add(mname);
                    if(!"".equals(addr)){
                        column.add(addr);
                        if(nid.matches(regexNumber)){ // Check nid only have digits or not
                            if(count(nid,10,"Nid no sholuld be 10 digit") == true){ // Call count() meathoad with three arguements
                                column.add(nid);
                                if(!"".equals(dob)){
                                    column.add(dob);
                                    if(id.matches(regexNumber)){
                                        if(count(id,7,"Id no sholuld be 7 digit") == true){
                                            column.add(id);
                                            if(reg.matches(regexNumber)){
                                                if(count(reg,5,"Reg no sholuld be 5 digit") == true){
                                                    column.add(reg);
                                                    if(!"Select one".equals(session)){
                                                        column.add(session);
                                                        if(!"Select one".equals(blood)){
                                                            column.add(blood);
                                                            if(!"Select one".equals(fac)){
                                                                column.add(fac);
                                                                if(!"Select one".equals(hall)){
                                                                column.add(hall);
                                                                 //  If Everything is ok lets begain insert data into database
                                                                  insertStudent(column);
                                                                  resetField();
                                                                }else{alert("true","Select student Residence hall");}
                                                            }else{alert("true","Select student Faculty");}
                                                        }else{alert("true","Select student Blood group");}
                                                    }else{alert("true","Select student Session");}
                                                }
                                            }else{alert("true","You insert wrong Registration number");}
                                        }
                                    }else{alert("true","You insert wrong Id");}

                                }else{ alert("true","Select Birth Date");}
                            }
                        }else{alert("true","You insert wrong Nid");}                       
                    }else{alert("true","Insert Address");}
                }else{alert("true","Mother name should be alpahbet");}
            }else{alert("true","Fataher name should be alpahbet");}
        }else{alert("true","Name should be alpahbet");}
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling
        AdminDashboards.func f = new AdminDashboards.func();
        try { 
            //  recive func meathoad return query result                   
            rs = f.find(getadminusername,identity);
            if(rs.next()){
                byte[] img = rs.getBytes("photo");
                ImageIcon MyImage1 = new ImageIcon(img);
                Image img1 = MyImage1.getImage();
                Image newImage1 = img1.getScaledInstance(profilepic.getWidth(), profilepic.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon image1 = new ImageIcon(newImage1);
                profilepic.setIcon(image1);
                UpdateProfilePic.setIcon(image1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        serchIdField.setBorder(BorderFactory.createCompoundBorder(regVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        serchIdField1.setBorder(BorderFactory.createCompoundBorder(regVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                
        nameVal.setBorder(BorderFactory.createCompoundBorder(nameVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        fnameVal.setBorder(BorderFactory.createCompoundBorder(fnameVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        mnameVal.setBorder(BorderFactory.createCompoundBorder(mnameVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        addrVal.setBorder(BorderFactory.createCompoundBorder(addrVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        nidVal.setBorder(BorderFactory.createCompoundBorder(nidVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        dobVal.setBorder(BorderFactory.createCompoundBorder(dobVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        idVal.setBorder(BorderFactory.createCompoundBorder(idVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        regVal.setBorder(BorderFactory.createCompoundBorder(regVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        
    }//GEN-LAST:event_formComponentShown

    private void alertSuccessCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertSuccessCloseActionPerformed
        // TODO add your handling code here:

        alertSuccess.setVisible(false);
        alertSuccessText.setText("");
        alertsuccessLogo.setVisible(false);
        alertSuccessClose.setVisible(false);
    }//GEN-LAST:event_alertSuccessCloseActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String name = nameVal.getText();
        String fname = fnameVal.getText();
        String mname = mnameVal.getText();
        String id = idVal.getText();
        String reg =  regVal.getText();
        String addr = addrVal.getText();
        String nid =  nidVal.getText();
        String session =  (String)sessionVal.getSelectedItem();
        String fac = (String)facVal.getSelectedItem();
        String hall = (String)hallVal.getSelectedItem();
        String blood = (String)bloodVal.getSelectedItem();
        String dob = dateFromDatePicker;
             

        
        String regexAlphabet = "^[A-Za-z_ ]+$";
        String regexNumber = "^[0-9]+$";
        
        
        
        if(name.matches(regexAlphabet)){ // Check name,fname,mname only have Alpahabet or not
            column.add(name);
            if(fname.matches(regexAlphabet)){ 
                column.add(fname);
                if(mname.matches(regexAlphabet)){ 
                    column.add(mname);
                    if(!"".equals(addr)){
                        column.add(addr);
                        if(nid.matches(regexNumber)){ // Check nid only have digits or not
                            if(count(nid,10,"Nid no sholuld be 10 digit") == true){ // Call count() meathoad with three arguements
                                column.add(nid);
                                if(!"".equals(dob)){
                                    column.add(dob);
                                    if(id.matches(regexNumber)){
                                        if(count(id,7,"Id no sholuld be 7 digit") == true){
                                            column.add(id);
                                            if(reg.matches(regexNumber)){
                                                if(count(reg,5,"Reg no sholuld be 5 digit") == true){
                                                    column.add(reg);
                                                    if(!"Select one".equals(session)){
                                                        column.add(session);
                                                        if(!"Select one".equals(blood)){
                                                            column.add(blood);
                                                            if(!"Select one".equals(fac)){
                                                                column.add(fac);
                                                                if(!"Select one".equals(hall)){
                                                                column.add(hall);
                                                                 //  If Everything is ok lets begain insert data into database
                                                                  insertStudent(column);
                                                                  resetField();
                                                                }else{alert("true","Select student Residence hall");}
                                                            }else{alert("true","Select student Faculty");}
                                                        }else{alert("true","Select student Blood group");}
                                                    }else{alert("true","Select student Session");}
                                                }
                                            }else{alert("true","You insert wrong Registration number");}
                                        }
                                    }else{alert("true","You insert wrong Id");}

                                }else{ alert("true","Select Birth Date");}
                            }
                        }else{alert("true","You insert wrong Nid");}                       
                    }else{alert("true","Insert Address");}
                }else{alert("true","Mother name should be alpahbet");}
            }else{alert("true","Fataher name should be alpahbet");}
        }else{alert("true","Name should be alpahbet");}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        // TODO add your handling code here:
        
        conn cc = new conn();
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
        
        
        
       int result = fileChooser.showSaveDialog(null);
       if(result == JFileChooser.APPROVE_OPTION){
           File selectedFile = fileChooser.getSelectedFile();
           String photoPath = selectedFile.getAbsolutePath();
           UpdateProfilePic.setIcon(ResizeImage(photoPath));
           profilepic.setIcon(ResizeImage(photoPath));
           imagePath = photoPath;
           
            AdminDashboards.test f = new AdminDashboards.test();                  
            try {
               try {
                   rs = f.finds(getadminusername,identity);
                   if(rs.next()){
                       System.out.println("Done");
                   }
               } catch (ClassNotFoundException ex) {
                   Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
               } catch (FileNotFoundException ex) {
                   Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
               }
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       }
       
       
       //        try{      
//            conn cc = new conn();
//            
////            FileInputStream fis= new FileInputStream(new File(imagePath));
//            String query = "UPDATE `users` set `photo`= LOAD_FILE(imagePath) where(email='"+getadminusername+"' and role='"+identity+"')";
//            ResultSet rs = cc.s.executeQuery(query);
//            if(rs.next()){
//             JOptionPane.showMessageDialog(this,"Hello, Welcome to Javatpoint."); 
//            }
//             
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_jLabel56MouseClicked

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
            java.util.logging.Logger.getLogger(AdminDashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                 String user="s@gmail.com", identity="admin";
                new AdminDashboards(user,identity).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdministrationPanel;
    private javax.swing.JButton Hall1;
    private javax.swing.JPanel LibraryPanel;
    private javax.swing.JPanel SideBarPanel;
    private javax.swing.JLabel UpdateProfilePic;
    private javax.swing.JButton addNewStudent2;
    private javax.swing.JButton addNewStudent3;
    private javax.swing.JPanel addStudent;
    private javax.swing.JButton addStudentBtn;
    private javax.swing.JButton addStudentBtn2;
    private javax.swing.JTextField addrVal;
    private javax.swing.JButton administrator1;
    private javax.swing.JLabel alert;
    private javax.swing.JButton alertClose;
    private javax.swing.JLabel alertLogo;
    private javax.swing.JLabel alertSuccess;
    private javax.swing.JButton alertSuccessClose;
    private javax.swing.JLabel alertSuccessText;
    private javax.swing.JLabel alertText;
    private javax.swing.JLabel alertsuccessLogo;
    private javax.swing.JButton bank1;
    private javax.swing.JPanel bankPanel;
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> bloodVal;
    private javax.swing.JLabel bottomrightborder;
    private javax.swing.JLabel bottomrightborder1;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton dashboard;
    private javax.swing.JPanel dashboardPanel;
    private com.toedter.calendar.JDateChooser dobVal;
    private javax.swing.JComboBox<String> facVal;
    private javax.swing.JButton faculty1;
    private javax.swing.JPanel facultyPanel;
    private javax.swing.JTextField fnameVal;
    private javax.swing.JPanel hallPanel;
    private javax.swing.JComboBox<String> hallVal;
    private javax.swing.JButton health1;
    private javax.swing.JPanel healthPanel;
    private javax.swing.JTextField idVal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JButton library1;
    private javax.swing.JButton logout;
    private javax.swing.JTextField mnameVal;
    private javax.swing.JTextField nameVal;
    private javax.swing.JTextField nidVal;
    private javax.swing.JButton organaization1;
    private javax.swing.JPanel organaizationPanel;
    private javax.swing.JButton others1;
    private javax.swing.JPanel othersPanel;
    private javax.swing.JPanel profileViewPanel;
    private javax.swing.JLabel profilepic;
    private javax.swing.JTextField regVal;
    private javax.swing.JTextField serchIdField;
    private javax.swing.JTextField serchIdField1;
    private javax.swing.JComboBox<String> sessionVal;
    private javax.swing.JButton student1;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JButton teacher1;
    private javax.swing.JPanel teacherPanel;
    private javax.swing.JButton transport1;
    private javax.swing.JPanel transportPanel;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables
}
