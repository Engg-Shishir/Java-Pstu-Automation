/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import ImageInsert_ImageShow.Insert;
import static com.mysql.cj.util.SaslPrep.StringType.QUERY;
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
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

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
    public String logeduserid = "";
    public String dateFromDatePicker = "";
    public String studentsDetailsId = "";
    private int random;
    public String selectedFaculty = "";
    
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
    

    
    
    public void hide(){
        AdminSideBarPanel.setVisible(false);
        AdminTabedPane.setVisible(false);
        StudentSideBarPanel.setVisible(false);
        StudentTabedPane.setVisible(false);
        
        
        
        error.setVisible(false);
        errorLogo.setVisible(false);
        errorText.setText("");
        errorClose.setVisible(false);
        
        success.setVisible(false);
        successLogo.setVisible(false);
        successText.setText("");
        successClose.setVisible(false);
        
    }
    
       
    public void openSidebar(String user){
        if("admin".equals(user)){ 
            AdminSideBarPanel.setVisible(true);
            AdminTabedPane.setVisible(true);
        }else if("student".equals(user)){ 
            StudentSideBarPanel.setVisible(true);
            StudentTabedPane.setVisible(true);
        }
    }
    
    
    public  void alert(String type,String permission, String message){ 
         if("error".equals(type)){ 
            if("false".equals(permission)){
                  error.setVisible(false);
                  errorLogo.setVisible(false);
                  errorText.setText(message);
                  errorClose.setVisible(false);
            }else{
                  alert("success","false","");
                  error.setVisible(true);
                  errorLogo.setVisible(true);
                  errorText.setText(message);
                  errorClose.setVisible(true);
            }
         }else{ 
            if("false".equals(permission)){
                  success.setVisible(false);
                  successLogo.setVisible(false);
                  successText.setText(message);
                  successClose.setVisible(false);
            }else{
                  alert("error","false","");
                  success.setVisible(true);
                  successLogo.setVisible(true);
                  successText.setText(message);
                  successClose.setVisible(true);
            }
         }
    }
    public boolean count(String s, int n, String msg)
    {
           if(s.length()==n){
               alert("error","false",msg);
           }else{
               alert("error","true",msg); 
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
       
       
       
       cseSession.setSelectedIndex(0);
       agriSession.setSelectedIndex(0);
       dvmSession.setSelectedIndex(0);
       bamSession.setSelectedIndex(0);
       fishSession.setSelectedIndex(0);
       nfsSession.setSelectedIndex(0);
       esdmSession.setSelectedIndex(0);
       llaSession.setSelectedIndex(0);
       ahSession.setSelectedIndex(0);
    }
    public void insertStudent(ArrayList<String> languages){

        String[] data = new String[languages.size()];
        data = languages.toArray(data);

        try{
            conn cc = new conn();
                int random = new Random().nextInt(900000) + 100000;
                String query = "INSERT INTO `student`(`name`,`password`, `fname`, `mname`, `addr`, `nid`,`dob`, `roll`, `reg`, `session`, `blood`, `fac`, `hall`) "
                        + "VALUES ('"+data[0]+"','"+random+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"')";
                int i = cc.s.executeUpdate(query);
                if(i==1){
                   alert("error","false","");
                   alert("success","true","Student Successfully Added");
                }           
        }catch(SQLException ee){
            System.out.println("The error is:"+ee);
        }
    }
    public class func{
        public ResultSet find(String usernames, String identy) throws SQLException{

            try{      
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","@Bismillah");

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
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","@Bismillah");
                    File f=new File(imagePath);
                    fs=new FileInputStream(f);
                    ps= con.prepareStatement("UPDATE users SET photo=? Where uid=?");
                    
                    ps.setBinaryStream(1,fs,(int)f.length());
                    ps.setString(2, logeduserid);
                   int count = ps.executeUpdate();
                   if(count > 0) { 
                      alert("success","true","Image Updated Successfully");
                   }else{ 
                      alert("eror","true","Image Size To Larage");
                   }
                return rs;
            }
    }
    public class select{
        public ResultSet find(String session,String fac) throws SQLException{

            conn con = new conn();

            PreparedStatement ps=con.c.prepareStatement("Select * from  student where session = ? and fac=? ");
            ps.setString(1,session);
            ps.setString(2,fac);
            rs  = ps.executeQuery();

            return rs;

        }
    }
    public class selectForPrint{
        public ResultSet find(String id) throws SQLException{

            conn con = new conn();

            PreparedStatement ps=con.c.prepareStatement("Select * from  student where roll = ? ");
            ps.setString(1,id);
            rs  = ps.executeQuery();

            return rs;

        }
    }
    public void  deleteStudent(String id){

            conn conn = new conn();
               try{		
                    String sql = "DELETE FROM student WHERE roll ='"+id+"'";	
                    conn.s.executeUpdate(sql);
                    PreparedStatement pst =  conn.c.prepareStatement(sql);
                    int numRowsChanged = pst.executeUpdate(sql);
                    if(numRowsChanged==0){
                      alert("success","true","Student Delete Successfully");
                    }

                    conn.s.close();
            }catch(SQLException e){
            }
    }   
    public void status(String user,String mode) throws SQLException{ 
        System.out.println(user);
        System.out.println(mode);
          
            conn cc = new conn();
            
            if("student".equals(user)){
                if("Active".equals(mode)){ 
                    ps= cc.c.prepareStatement("UPDATE student SET status=? Where roll=?");
                    // set status 1 as a active symble
                    ps.setString(1, "1");
                    ps.setString(2, sid.getText());
                    int count = ps.executeUpdate();
                    if(count > 0) { 
                        sstatusBtn.setText("Active");
                        sstatusBtn.setBackground(Color.GREEN);
                        sstatusBtn.setForeground(Color.BLACK);
                        alert("success","true","This account Active now");
                    } 
                }else{ 
                    ps= cc.c.prepareStatement("UPDATE student SET status=? Where roll=?");
                    ps.setString(1, "0");
                    ps.setString(2, sid.getText());
                    int count = ps.executeUpdate();
                    if(count > 0) { 
                        sstatusBtn.setText("Disable");
                        sstatusBtn.setBackground(Color.RED);
                        sstatusBtn.setForeground(Color.WHITE);
                        alert("error","true","This account Dissable now");
                    } 
              }
            }else { 
              // user code here
            }
        
    }
    public String getfirstLeter_replace_some_speacial_charecter_and_word(String str)
    {
        // First remove and,& from string        
        str = str.replaceAll("and", "");
        str = str.replaceAll("&", "");
        
        
        
        boolean continueAdding = true;
        int wordCount = 0;
        String forLongString = "";
        String forShortString = "";
        
        
        for (int i = 0; i < str.length(); i++)
        {
            if(i<4 && wordCount==0){ 
                 forShortString += (str.charAt(i));
            }
            
            if (str.charAt(i) == ' ')
            {
                wordCount++;
                continueAdding = true;
            }else if (str.charAt(i) != ' ' && continueAdding == true)
            {
                forLongString += (str.charAt(i));
                continueAdding = false;
            }
            
           
        }
        if(wordCount==0) { 
          return forShortString;
        }else{ 
          return forLongString;
        }
 
    }
    
    
    public String make_String_Cappitalize(String message){ 
        // create a string

    // stores each characters to a char array
    char[] charArray = message.toCharArray();
    boolean foundSpace = true;

    for(int i = 0; i < charArray.length; i++) {
      // if the array element is a letter
        if(Character.isLetter(charArray[i])) {
            // check space is present before the letter
            if(foundSpace) {
              // change the letter into uppercase
              charArray[i] = Character.toUpperCase(charArray[i]);
              foundSpace = false;
            }
        }
        else {
          // if the new character is not character
          foundSpace = true;
        }
    }
    
    
      message = String.valueOf(charArray);
      return message;    
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StudentSideBarPanel = new javax.swing.JPanel();
        studentSidebarProfilepic = new javax.swing.JLabel();
        studentSidebarLogoutBtn = new javax.swing.JButton();
        studentSidebarViewBtn = new javax.swing.JButton();
        studentSideBarDashboardBtn = new javax.swing.JButton();
        studentSidebarPaymentBtn = new javax.swing.JButton();
        studentSideBarClassBtn = new javax.swing.JButton();
        studentSideBarNotificationBtn = new javax.swing.JButton();
        Hall2 = new javax.swing.JButton();
        library2 = new javax.swing.JButton();
        studentSideBarExamBtn = new javax.swing.JButton();
        studentSideBarResultBtn = new javax.swing.JButton();
        organaization2 = new javax.swing.JButton();
        bg1 = new javax.swing.JLabel();
        AdminSideBarPanel = new javax.swing.JPanel();
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
        alertPanel = new javax.swing.JPanel();
        errorClose = new javax.swing.JButton();
        errorLogo = new javax.swing.JLabel();
        errorText = new javax.swing.JLabel();
        error = new javax.swing.JLabel();
        successClose = new javax.swing.JButton();
        successLogo = new javax.swing.JLabel();
        successText = new javax.swing.JLabel();
        success = new javax.swing.JLabel();
        StudentTabedPane = new javax.swing.JTabbedPane();
        studentDashboard = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        profileViewPanel3 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        UpdateProfilePic1 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        adminName1 = new javax.swing.JLabel();
        file1 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        adminEmail1 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        adminBlood3 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        adminUsername1 = new javax.swing.JTextField();
        adminPhone1 = new javax.swing.JTextField();
        adminPassword1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        adminDob12 = new javax.swing.JLabel();
        adminNid1 = new javax.swing.JLabel();
        bottomrightborder6 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel111 = new javax.swing.JLabel();
        studentPanel4 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        teacherPanel5 = new javax.swing.JPanel();
        serchIdField3 = new javax.swing.JTextField();
        addNewStudent5 = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();
        jComboBox21 = new javax.swing.JComboBox<>();
        jComboBox22 = new javax.swing.JComboBox<>();
        jComboBox23 = new javax.swing.JComboBox<>();
        jComboBox24 = new javax.swing.JComboBox<>();
        jComboBox25 = new javax.swing.JComboBox<>();
        jLabel130 = new javax.swing.JLabel();
        jComboBox26 = new javax.swing.JComboBox<>();
        jComboBox27 = new javax.swing.JComboBox<>();
        jComboBox28 = new javax.swing.JComboBox<>();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        addStudentBtn3 = new javax.swing.JButton();
        jLabel135 = new javax.swing.JLabel();
        facultyPanel6 = new javax.swing.JPanel();
        hallPanel7 = new javax.swing.JPanel();
        LibraryPanel8 = new javax.swing.JPanel();
        bankPanel9 = new javax.swing.JPanel();
        healthPanel10 = new javax.swing.JPanel();
        AdministrationPanel11 = new javax.swing.JPanel();
        organaizationPanel12 = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        transportPanel13 = new javax.swing.JPanel();
        othersPanel14 = new javax.swing.JPanel();
        addStudent15 = new javax.swing.JPanel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        nameVal1 = new javax.swing.JTextField();
        fnameVal1 = new javax.swing.JTextField();
        mnameVal1 = new javax.swing.JTextField();
        addrVal1 = new javax.swing.JTextField();
        nidVal1 = new javax.swing.JTextField();
        dobVal1 = new com.toedter.calendar.JDateChooser();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        idVal1 = new javax.swing.JTextField();
        regVal1 = new javax.swing.JTextField();
        sessionVal1 = new javax.swing.JComboBox<>();
        bloodVal1 = new javax.swing.JComboBox<>();
        facVal1 = new javax.swing.JComboBox<>();
        hallVal1 = new javax.swing.JComboBox<>();
        alertSuccessText1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        bottomrightborder7 = new javax.swing.JLabel();
        verifyaccount16 = new javax.swing.JPanel();
        userVerifyIdentity1 = new javax.swing.JComboBox<>();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        bottomrightborder8 = new javax.swing.JLabel();
        bottomrightborder9 = new javax.swing.JLabel();
        vkey1 = new javax.swing.JTextField();
        jLabel165 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jButton12 = new javax.swing.JButton();
        verifyUsername1 = new javax.swing.JTextField();
        allStudent17 = new javax.swing.JPanel();
        view4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        allStudentsByFaculty1 = new javax.swing.JTable();
        cseSession3 = new javax.swing.JComboBox<>();
        view5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        studentsDetails18 = new javax.swing.JPanel();
        jLabel168 = new javax.swing.JLabel();
        sDetailsProfilePic1 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        adminBlood4 = new javax.swing.JLabel();
        adminDob14 = new javax.swing.JLabel();
        snid1 = new javax.swing.JLabel();
        adminDob15 = new javax.swing.JLabel();
        adminDob16 = new javax.swing.JLabel();
        adminDob17 = new javax.swing.JLabel();
        adminDob18 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        adminDob19 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        adminNid4 = new javax.swing.JLabel();
        sname1 = new javax.swing.JLabel();
        sfname1 = new javax.swing.JLabel();
        smname1 = new javax.swing.JLabel();
        saddr1 = new javax.swing.JLabel();
        sdob1 = new javax.swing.JLabel();
        sblood1 = new javax.swing.JLabel();
        sphone1 = new javax.swing.JLabel();
        adminNid5 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        adminBlood5 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        adminDob20 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        adminDob21 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        adminDob22 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        adminDob23 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        adminDob24 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        ssem1 = new javax.swing.JLabel();
        shall1 = new javax.swing.JLabel();
        sfac1 = new javax.swing.JLabel();
        ssession1 = new javax.swing.JLabel();
        semail1 = new javax.swing.JLabel();
        sreg1 = new javax.swing.JLabel();
        sid1 = new javax.swing.JLabel();
        printbtn1 = new javax.swing.JButton();
        view6 = new javax.swing.JButton();
        jLabel199 = new javax.swing.JLabel();
        adminDob25 = new javax.swing.JLabel();
        jLabel200 = new javax.swing.JLabel();
        sstatusBtn1 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        AdminTabedPane = new javax.swing.JTabbedPane();
        dashboardPanel01 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        profileViewPanel02 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        UpdateProfilePic = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        adminName = new javax.swing.JLabel();
        file = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        adminEmail = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        adminBlood = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        adminUsername = new javax.swing.JTextField();
        adminPhone = new javax.swing.JTextField();
        adminPassword = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        adminDob = new javax.swing.JLabel();
        adminNid = new javax.swing.JLabel();
        bottomrightborder5 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel61 = new javax.swing.JLabel();
        studentPanel03 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        addStudentBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        serchIdField = new javax.swing.JTextField();
        addNewStudent2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cseSession = new javax.swing.JComboBox<>();
        agriSession = new javax.swing.JComboBox<>();
        dvmSession = new javax.swing.JComboBox<>();
        bamSession = new javax.swing.JComboBox<>();
        fishSession = new javax.swing.JComboBox<>();
        nfsSession = new javax.swing.JComboBox<>();
        esdmSession = new javax.swing.JComboBox<>();
        llaSession = new javax.swing.JComboBox<>();
        ahSession = new javax.swing.JComboBox<>();
        teacherPanel04 = new javax.swing.JPanel();
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
        facultyPanel05 = new javax.swing.JPanel();
        hallPanel06 = new javax.swing.JPanel();
        LibraryPanel07 = new javax.swing.JPanel();
        bankPanel08 = new javax.swing.JPanel();
        healthPanel09 = new javax.swing.JPanel();
        AdministrationPanel10 = new javax.swing.JPanel();
        organaizationPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        transportPanel12 = new javax.swing.JPanel();
        othersPanel13 = new javax.swing.JPanel();
        addStudent14 = new javax.swing.JPanel();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        bottomrightborder4 = new javax.swing.JLabel();
        verifyaccount15 = new javax.swing.JPanel();
        userVerifyIdentity = new javax.swing.JComboBox<>();
        jLabel54 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        bottomrightborder2 = new javax.swing.JLabel();
        bottomrightborder3 = new javax.swing.JLabel();
        vkey = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        verifyUsername = new javax.swing.JTextField();
        allStudent16 = new javax.swing.JPanel();
        view1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        allStudentsByFaculty = new javax.swing.JTable();
        cseSession1 = new javax.swing.JComboBox<>();
        view2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        studentsDetails17 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        sDetailsProfilePic = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        adminBlood1 = new javax.swing.JLabel();
        adminDob1 = new javax.swing.JLabel();
        snid = new javax.swing.JLabel();
        adminDob2 = new javax.swing.JLabel();
        adminDob3 = new javax.swing.JLabel();
        adminDob4 = new javax.swing.JLabel();
        adminDob5 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        adminDob6 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        adminNid2 = new javax.swing.JLabel();
        sname = new javax.swing.JLabel();
        sfname = new javax.swing.JLabel();
        smname = new javax.swing.JLabel();
        saddr = new javax.swing.JLabel();
        sdob = new javax.swing.JLabel();
        sblood = new javax.swing.JLabel();
        sphone = new javax.swing.JLabel();
        adminNid3 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        adminBlood2 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        adminDob7 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        adminDob8 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        adminDob9 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        adminDob10 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        adminDob11 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        ssem = new javax.swing.JLabel();
        shall = new javax.swing.JLabel();
        sfac = new javax.swing.JLabel();
        ssession = new javax.swing.JLabel();
        semail = new javax.swing.JLabel();
        sreg = new javax.swing.JLabel();
        sid = new javax.swing.JLabel();
        printbtn = new javax.swing.JButton();
        view3 = new javax.swing.JButton();
        jLabel102 = new javax.swing.JLabel();
        adminDob13 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        sstatusBtn = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentSideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        StudentSideBarPanel.add(studentSidebarProfilepic, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 200, 180));

        studentSidebarLogoutBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSidebarLogoutBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSidebarLogoutBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSidebarLogoutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/logout.png"))); // NOI18N
        studentSidebarLogoutBtn.setText("Logout");
        studentSidebarLogoutBtn.setBorder(null);
        studentSidebarLogoutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSidebarLogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSidebarLogoutBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSidebarLogoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, 40));

        studentSidebarViewBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSidebarViewBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSidebarViewBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSidebarViewBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        studentSidebarViewBtn.setText("View");
        studentSidebarViewBtn.setBorder(null);
        studentSidebarViewBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSidebarViewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSidebarViewBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSidebarViewBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

        studentSideBarDashboardBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSideBarDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSideBarDashboardBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSideBarDashboardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/home.png"))); // NOI18N
        studentSideBarDashboardBtn.setText("Dashboard");
        studentSideBarDashboardBtn.setBorder(null);
        studentSideBarDashboardBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSideBarDashboardBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentSideBarDashboardBtnMouseClicked(evt);
            }
        });
        studentSideBarDashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSideBarDashboardBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSideBarDashboardBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, 40));

        studentSidebarPaymentBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSidebarPaymentBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSidebarPaymentBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSidebarPaymentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        studentSidebarPaymentBtn.setText("Payment");
        studentSidebarPaymentBtn.setBorder(null);
        studentSidebarPaymentBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSidebarPaymentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSidebarPaymentBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSidebarPaymentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 40));

        studentSideBarClassBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSideBarClassBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSideBarClassBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSideBarClassBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/faculty.png"))); // NOI18N
        studentSideBarClassBtn.setText("Class");
        studentSideBarClassBtn.setBorder(null);
        studentSideBarClassBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSideBarClassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSideBarClassBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSideBarClassBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 40));

        studentSideBarNotificationBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSideBarNotificationBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSideBarNotificationBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSideBarNotificationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/notification.png"))); // NOI18N
        studentSideBarNotificationBtn.setText("Notification");
        studentSideBarNotificationBtn.setBorder(null);
        studentSideBarNotificationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSideBarNotificationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSideBarNotificationBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSideBarNotificationBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 140, 40));

        Hall2.setBackground(new java.awt.Color(14, 0, 82));
        Hall2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Hall2.setForeground(new java.awt.Color(255, 255, 255));
        Hall2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/hall.png"))); // NOI18N
        Hall2.setText(" Hall");
        Hall2.setBorder(null);
        Hall2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Hall2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hall2ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(Hall2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 140, 40));

        library2.setBackground(new java.awt.Color(14, 0, 82));
        library2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        library2.setForeground(new java.awt.Color(255, 255, 255));
        library2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/library.png"))); // NOI18N
        library2.setText("Library");
        library2.setBorder(null);
        library2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        library2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                library2ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(library2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 140, 40));

        studentSideBarExamBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSideBarExamBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSideBarExamBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSideBarExamBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/exam.png"))); // NOI18N
        studentSideBarExamBtn.setText("Exam");
        studentSideBarExamBtn.setBorder(null);
        studentSideBarExamBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSideBarExamBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSideBarExamBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSideBarExamBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, 140, 40));

        studentSideBarResultBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentSideBarResultBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentSideBarResultBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentSideBarResultBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/result.png"))); // NOI18N
        studentSideBarResultBtn.setText("Result");
        studentSideBarResultBtn.setBorder(null);
        studentSideBarResultBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentSideBarResultBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSideBarResultBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentSideBarResultBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 140, 40));

        organaization2.setBackground(new java.awt.Color(14, 0, 82));
        organaization2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        organaization2.setForeground(new java.awt.Color(255, 255, 255));
        organaization2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/organaization.png"))); // NOI18N
        organaization2.setText("Oraniztion");
        organaization2.setBorder(null);
        organaization2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organaization2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organaization2ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(organaization2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 510, 140, 40));

        bg1.setBackground(new java.awt.Color(0, 5, 42));
        bg1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        bg1.setOpaque(true);
        StudentSideBarPanel.add(bg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 855));

        getContentPane().add(StudentSideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 860));

        AdminSideBarPanel.setPreferredSize(new java.awt.Dimension(350, 855));
        AdminSideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        AdminSideBarPanel.add(profilepic, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 200, 180));

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
        AdminSideBarPanel.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, 40));

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
        AdminSideBarPanel.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, 40));

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
        AdminSideBarPanel.add(view, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

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
        AdminSideBarPanel.add(student1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 40));

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
        AdminSideBarPanel.add(teacher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 40));

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
        AdminSideBarPanel.add(faculty1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 140, 40));

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
        AdminSideBarPanel.add(Hall1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 140, 40));

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
        AdminSideBarPanel.add(library1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 140, 40));

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
        AdminSideBarPanel.add(bank1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, 140, 40));

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
        AdminSideBarPanel.add(health1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 140, 40));

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
        AdminSideBarPanel.add(administrator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 510, 140, 40));

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
        AdminSideBarPanel.add(organaization1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 140, 40));

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
        AdminSideBarPanel.add(transport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 560, 140, 40));

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
        AdminSideBarPanel.add(others1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 300, 40));

        bg.setBackground(new java.awt.Color(0, 5, 42));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        bg.setOpaque(true);
        AdminSideBarPanel.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 855));

        getContentPane().add(AdminSideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 860));

        contentPanel.setBackground(new java.awt.Color(159, 0, 87));
        contentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        alertPanel.setBackground(new java.awt.Color(0, 5, 42));
        alertPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        errorClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        errorClose.setBorder(null);
        errorClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        errorClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                errorCloseActionPerformed(evt);
            }
        });
        alertPanel.add(errorClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 45, 40, 40));

        errorLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/warning.png"))); // NOI18N
        errorLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alertPanel.add(errorLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, -1, 40));

        errorText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        errorText.setForeground(new java.awt.Color(255, 255, 255));
        alertPanel.add(errorText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 370, 50));

        error.setBackground(new java.awt.Color(102, 0, 102));
        error.setOpaque(true);
        alertPanel.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 35, 490, 60));

        successClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        successClose.setBorder(null);
        successClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        successClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                successCloseActionPerformed(evt);
            }
        });
        alertPanel.add(successClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 45, 40, 40));

        successLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/successlogos.png"))); // NOI18N
        successLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alertPanel.add(successLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, -1, 40));

        successText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        successText.setForeground(new java.awt.Color(255, 255, 255));
        alertPanel.add(successText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 35, 370, 60));

        success.setBackground(new java.awt.Color(0, 102, 51));
        success.setOpaque(true);
        alertPanel.add(success, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 35, 490, 60));

        contentPanel.add(alertPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 510, 100));

        studentDashboard.setBackground(new java.awt.Color(0, 5, 42));
        studentDashboard.setPreferredSize(new java.awt.Dimension(1200, 855));
        studentDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Student   Dashboard");
        studentDashboard.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, 560, 87));

        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Background/bg5.jpg"))); // NOI18N
        jLabel112.setText("jLabel112");
        studentDashboard.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 720, 490));

        StudentTabedPane.addTab("tab1", studentDashboard);

        profileViewPanel3.setBackground(new java.awt.Color(0, 5, 42));
        profileViewPanel3.setPreferredSize(new java.awt.Dimension(1110, 890));
        profileViewPanel3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                profileViewPanel3ComponentShown(evt);
            }
        });
        profileViewPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setText("Change Profile :");
        profileViewPanel3.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 160, 30));

        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/studentProfile.png"))); // NOI18N
        jLabel101.setText("jLabel16");
        profileViewPanel3.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 470, 70));

        UpdateProfilePic1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        profileViewPanel3.add(UpdateProfilePic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 200, 180));

        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel104.setText("jLabel56");
        jLabel104.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 30, 40));

        adminName1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        adminName1.setForeground(new java.awt.Color(153, 0, 102));
        adminName1.setText("Name");
        profileViewPanel3.add(adminName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 410, 40));

        file1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/folder.png"))); // NOI18N
        file1.setText("jLabel56");
        file1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                file1MouseClicked(evt);
            }
        });
        profileViewPanel3.add(file1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, 40, 30));

        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel105.setText("jLabel56");
        jLabel105.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, 30, 30));

        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/nid.png"))); // NOI18N
        jLabel106.setText("jLabel56");
        jLabel106.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel106.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel106MouseClicked(evt);
            }
        });
        profileViewPanel3.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 30, 30));

        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/dob.png"))); // NOI18N
        jLabel107.setText("jLabel56");
        jLabel107.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 30, 30));

        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/uname.png"))); // NOI18N
        jLabel108.setText("jLabel56");
        jLabel108.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 30, 30));

        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/phone.png"))); // NOI18N
        jLabel109.setText("jLabel56");
        jLabel109.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 30, 30));

        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel110.setText("jLabel56");
        jLabel110.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 30, 30));

        adminEmail1.setBackground(new java.awt.Color(0, 5, 42));
        adminEmail1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminEmail1.setForeground(new java.awt.Color(255, 255, 255));
        adminEmail1.setText("gfhgj");
        adminEmail1.setBorder(null);
        profileViewPanel3.add(adminEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 290, 40));
        profileViewPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 300, 10));

        adminBlood3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood3.setForeground(new java.awt.Color(255, 204, 0));
        adminBlood3.setText("Name");
        profileViewPanel3.add(adminBlood3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 300, -1));
        profileViewPanel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 300, 10));
        profileViewPanel3.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 300, 10));

        adminUsername1.setBackground(new java.awt.Color(0, 5, 42));
        adminUsername1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminUsername1.setForeground(new java.awt.Color(255, 255, 255));
        adminUsername1.setText("ghghghgh");
        adminUsername1.setBorder(null);
        profileViewPanel3.add(adminUsername1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 300, 40));

        adminPhone1.setBackground(new java.awt.Color(0, 5, 42));
        adminPhone1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminPhone1.setForeground(new java.awt.Color(255, 255, 255));
        adminPhone1.setText("iiiiii");
        adminPhone1.setBorder(null);
        adminPhone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPhone1ActionPerformed(evt);
            }
        });
        profileViewPanel3.add(adminPhone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 290, 40));

        adminPassword1.setBackground(new java.awt.Color(0, 5, 42));
        adminPassword1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminPassword1.setForeground(new java.awt.Color(255, 255, 255));
        adminPassword1.setText("uiiuo");
        adminPassword1.setBorder(null);
        profileViewPanel3.add(adminPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, 300, 40));

        jButton8.setBackground(new java.awt.Color(14, 0, 82));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/send.png"))); // NOI18N
        jButton8.setText("Update");
        jButton8.setBorder(null);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        profileViewPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 110, 50));

        adminDob12.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob12.setForeground(new java.awt.Color(255, 204, 0));
        adminDob12.setText("Name");
        profileViewPanel3.add(adminDob12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 310, -1));

        adminNid1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid1.setForeground(new java.awt.Color(255, 204, 0));
        adminNid1.setText("Nid");
        profileViewPanel3.add(adminNid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 260, 30));

        bottomrightborder6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        profileViewPanel3.add(bottomrightborder6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 330, 340));
        profileViewPanel3.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 300, 10));

        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/blood.png"))); // NOI18N
        jLabel111.setText("jLabel56");
        jLabel111.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel3.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 30));

        StudentTabedPane.addTab("tab2", profileViewPanel3);

        studentPanel4.setBackground(new java.awt.Color(0, 5, 42));
        studentPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel113.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        studentPanel4.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 390, 270));

        StudentTabedPane.addTab("tab3", studentPanel4);

        teacherPanel5.setBackground(new java.awt.Color(0, 5, 42));
        teacherPanel5.setPreferredSize(new java.awt.Dimension(1100, 770));
        teacherPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        serchIdField3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        serchIdField3.setText("     Search with Teacher id");
        serchIdField3.setBorder(null);
        serchIdField3.setMargin(new java.awt.Insets(2, 20, 2, 2));
        serchIdField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchIdField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchIdField3FocusLost(evt);
            }
        });
        serchIdField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchIdField3ActionPerformed(evt);
            }
        });
        teacherPanel5.add(serchIdField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

        addNewStudent5.setBackground(new java.awt.Color(14, 0, 82));
        addNewStudent5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addNewStudent5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        addNewStudent5.setActionCommand("Add Student");
        addNewStudent5.setBorder(null);
        addNewStudent5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewStudent5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStudent5ActionPerformed(evt);
            }
        });
        teacherPanel5.add(addNewStudent5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel124.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel124.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jLabel125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel125.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel126.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel127.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel128.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel129.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jComboBox20.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NFS Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox20ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox20, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

        jComboBox21.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox21ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox21, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

        jComboBox22.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BBA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox22ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox22, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

        jComboBox23.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DVM Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox23ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox23, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

        jComboBox24.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox24ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox24, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

        jComboBox25.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox25.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CSE Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox25ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel130.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jComboBox26.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox26.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox26ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

        jComboBox27.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox27.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LLA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox27ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox27, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

        jComboBox28.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AH Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox28ActionPerformed(evt);
            }
        });
        teacherPanel5.add(jComboBox28, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        jLabel131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel131.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

        jLabel132.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel132.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel5.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jLabel133.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminTeacher.png"))); // NOI18N
        teacherPanel5.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 350, 350));

        jLabel134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        teacherPanel5.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 240, -1, 80));

        addStudentBtn3.setBackground(new java.awt.Color(14, 0, 82));
        addStudentBtn3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addStudentBtn3.setForeground(new java.awt.Color(255, 255, 255));
        addStudentBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/addNew.png"))); // NOI18N
        addStudentBtn3.setText("  Add New");
        addStudentBtn3.setBorder(null);
        addStudentBtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudentBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentBtn3ActionPerformed(evt);
            }
        });
        teacherPanel5.add(addStudentBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 220, 50));

        jLabel135.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel135.setText("jLabel8");
        teacherPanel5.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, 200, 200));

        StudentTabedPane.addTab("tab4", teacherPanel5);

        facultyPanel6.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout facultyPanel6Layout = new javax.swing.GroupLayout(facultyPanel6);
        facultyPanel6.setLayout(facultyPanel6Layout);
        facultyPanel6Layout.setHorizontalGroup(
            facultyPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        facultyPanel6Layout.setVerticalGroup(
            facultyPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab5", facultyPanel6);

        hallPanel7.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout hallPanel7Layout = new javax.swing.GroupLayout(hallPanel7);
        hallPanel7.setLayout(hallPanel7Layout);
        hallPanel7Layout.setHorizontalGroup(
            hallPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        hallPanel7Layout.setVerticalGroup(
            hallPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab6", hallPanel7);

        LibraryPanel8.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout LibraryPanel8Layout = new javax.swing.GroupLayout(LibraryPanel8);
        LibraryPanel8.setLayout(LibraryPanel8Layout);
        LibraryPanel8Layout.setHorizontalGroup(
            LibraryPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        LibraryPanel8Layout.setVerticalGroup(
            LibraryPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab7", LibraryPanel8);

        bankPanel9.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout bankPanel9Layout = new javax.swing.GroupLayout(bankPanel9);
        bankPanel9.setLayout(bankPanel9Layout);
        bankPanel9Layout.setHorizontalGroup(
            bankPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        bankPanel9Layout.setVerticalGroup(
            bankPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab8", bankPanel9);

        healthPanel10.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout healthPanel10Layout = new javax.swing.GroupLayout(healthPanel10);
        healthPanel10.setLayout(healthPanel10Layout);
        healthPanel10Layout.setHorizontalGroup(
            healthPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        healthPanel10Layout.setVerticalGroup(
            healthPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab9", healthPanel10);

        AdministrationPanel11.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout AdministrationPanel11Layout = new javax.swing.GroupLayout(AdministrationPanel11);
        AdministrationPanel11.setLayout(AdministrationPanel11Layout);
        AdministrationPanel11Layout.setHorizontalGroup(
            AdministrationPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        AdministrationPanel11Layout.setVerticalGroup(
            AdministrationPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab10", AdministrationPanel11);

        organaizationPanel12.setBackground(new java.awt.Color(0, 5, 42));
        organaizationPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel136.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel136.setText("Dashboard");
        organaizationPanel12.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 183, 389, 82));

        StudentTabedPane.addTab("tab11", organaizationPanel12);

        transportPanel13.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout transportPanel13Layout = new javax.swing.GroupLayout(transportPanel13);
        transportPanel13.setLayout(transportPanel13Layout);
        transportPanel13Layout.setHorizontalGroup(
            transportPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        transportPanel13Layout.setVerticalGroup(
            transportPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab12", transportPanel13);

        othersPanel14.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout othersPanel14Layout = new javax.swing.GroupLayout(othersPanel14);
        othersPanel14.setLayout(othersPanel14Layout);
        othersPanel14Layout.setHorizontalGroup(
            othersPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        othersPanel14Layout.setVerticalGroup(
            othersPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        StudentTabedPane.addTab("tab13", othersPanel14);

        addStudent15.setBackground(new java.awt.Color(0, 5, 42));
        addStudent15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/addStudent.png"))); // NOI18N
        addStudent15.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 470, 80));

        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        jLabel138.setText("jLabel5");
        addStudent15.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 390, 260));

        jLabel139.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setText(":");
        addStudent15.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 10, -1));

        jLabel140.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setText(":");
        addStudent15.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 10, -1));

        jLabel141.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setText(":");
        addStudent15.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 470, 10, 40));

        jLabel142.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setText(":");
        addStudent15.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 520, 10, 40));

        jLabel143.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setText(":");
        addStudent15.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, 10, 30));

        jLabel144.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setText(":");
        addStudent15.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 10, 40));

        jLabel145.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("Birth Date");
        addStudent15.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 90, 40));

        jLabel146.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("Nid No ");
        addStudent15.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 70, 40));

        jLabel147.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setText("Address");
        addStudent15.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 80, 40));

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setText("Mother Name");
        addStudent15.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 120, 40));

        jLabel149.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(255, 255, 255));
        jLabel149.setText("Father Name");
        addStudent15.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 110, 40));

        jLabel150.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(255, 255, 255));
        jLabel150.setText("Name");
        addStudent15.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 60, 40));

        nameVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        nameVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nameVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(nameVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 300, 40));

        fnameVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fnameVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fnameVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(fnameVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 300, 40));

        mnameVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        mnameVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnameVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnameVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(mnameVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, 300, 40));

        addrVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addrVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addrVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(addrVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 300, 40));

        nidVal1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nidVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nidVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nidVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(nidVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, 300, 40));

        dobVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dobVal1.setDateFormatString("dd-MM-yyyy");
        dobVal1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dobVal1MouseClicked(evt);
            }
        });
        dobVal1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dobVal1PropertyChange(evt);
            }
        });
        addStudent15.add(dobVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 300, 40));

        jLabel151.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setText(":");
        addStudent15.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, 20, -1));

        jLabel152.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(255, 255, 255));
        jLabel152.setText(":");
        addStudent15.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 430, 20, -1));

        jLabel153.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(255, 255, 255));
        jLabel153.setText(":");
        addStudent15.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 20, 40));

        jLabel154.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel154.setForeground(new java.awt.Color(255, 255, 255));
        jLabel154.setText(":");
        addStudent15.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 520, 20, 30));

        jLabel155.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(255, 255, 255));
        jLabel155.setText(":");
        addStudent15.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 570, 20, 40));

        jLabel156.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel156.setForeground(new java.awt.Color(255, 255, 255));
        jLabel156.setText(":");
        addStudent15.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 620, 20, 30));

        jLabel157.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel157.setForeground(new java.awt.Color(255, 255, 255));
        jLabel157.setText("Faculty");
        addStudent15.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 570, 60, 40));

        jLabel158.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel158.setForeground(new java.awt.Color(255, 255, 255));
        jLabel158.setText("Blood");
        addStudent15.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 520, 50, 30));

        jLabel159.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel159.setForeground(new java.awt.Color(255, 255, 255));
        jLabel159.setText("Session");
        addStudent15.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 470, 70, 40));

        jLabel160.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(255, 255, 255));
        jLabel160.setText("Reg");
        addStudent15.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 40, 40));

        jLabel161.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel161.setForeground(new java.awt.Color(255, 255, 255));
        jLabel161.setText("Id");
        addStudent15.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 30, 40));

        jLabel162.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel162.setForeground(new java.awt.Color(255, 255, 255));
        jLabel162.setText("Hall");
        addStudent15.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 40, 30));

        idVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        idVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        idVal1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idVal1FocusLost(evt);
            }
        });
        idVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idVal1ActionPerformed(evt);
            }
        });
        idVal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idVal1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idVal1KeyTyped(evt);
            }
        });
        addStudent15.add(idVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, 300, 40));

        regVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        regVal1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        regVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(regVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 300, 40));

        sessionVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        sessionVal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", "2021-2022", "2022-2023", "2023-2024", "2024-2025", "2025-2026", " " }));
        sessionVal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent15.add(sessionVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, 300, 40));

        bloodVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bloodVal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "A +", "A -", "B +", "B -", "O +", "O -", "AB +", "AB -", " " }));
        bloodVal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent15.add(bloodVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 300, 40));

        facVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facVal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Computer Science & Engineering", "Agriculture", "Business Administration & Management", "Animal Husbandry", "Doctor Veterinary Medicine", "Fisheries", "Environmental Science and Disaster Management", "Nutrition and Food Science", "Land Management and Administration" }));
        facVal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facVal1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facVal1ItemStateChanged(evt);
            }
        });
        facVal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facVal1ActionPerformed(evt);
            }
        });
        addStudent15.add(facVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 570, 300, 40));

        hallVal1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        hallVal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Sher-E-Bangla Hall 1", "Sher-E-Bangla Hall 2", "Keramat Ali Hall", "Bangabandhu Sheikh Mujibor Rahman Hall", "Kobi Begum Sufia Kamal Hall", "Sheikh Fajilatunnesa Mujib Hall", "Captain Mohiuddin Jahangir Hall", "Sheikh Fajilatunnesa Mujib Hall-Babuganj", " " }));
        hallVal1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent15.add(hallVal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 620, 300, 40));

        alertSuccessText1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        alertSuccessText1.setForeground(new java.awt.Color(255, 255, 255));
        addStudent15.add(alertSuccessText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 360, 50));

        jButton9.setBackground(new java.awt.Color(14, 0, 82));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        jButton9.setText("Back");
        jButton9.setBorder(null);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        addStudent15.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 100, 40));

        jButton10.setBackground(new java.awt.Color(14, 0, 82));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/save.png"))); // NOI18N
        jButton10.setText("  Submit");
        jButton10.setBorder(null);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        addStudent15.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 680, 100, 40));

        bottomrightborder7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/bordertop left270_270.png"))); // NOI18N
        addStudent15.add(bottomrightborder7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, 240));

        StudentTabedPane.addTab("tab14", addStudent15);

        verifyaccount16.setBackground(new java.awt.Color(0, 5, 42));
        verifyaccount16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userVerifyIdentity1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        userVerifyIdentity1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select yuor identity", "Student", "Teacher", "Admin" }));
        userVerifyIdentity1.setBorder(null);
        userVerifyIdentity1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userVerifyIdentity1ActionPerformed(evt);
            }
        });
        verifyaccount16.add(userVerifyIdentity1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, 190, 40));

        jLabel163.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/verifyAccount.png"))); // NOI18N
        verifyaccount16.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 580, 90));

        jLabel164.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel164.setText("jLabel8");
        verifyaccount16.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 200, 200));

        jButton11.setBackground(new java.awt.Color(14, 0, 82));
        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        jButton11.setText("Back");
        jButton11.setBorder(null);
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        verifyaccount16.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 560, 100, 45));

        bottomrightborder8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border left bottom .png"))); // NOI18N
        verifyaccount16.add(bottomrightborder8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 350, 370));

        bottomrightborder9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        verifyaccount16.add(bottomrightborder9, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, 330, 340));

        vkey1.setBackground(new java.awt.Color(0, 5, 42));
        vkey1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        vkey1.setForeground(new java.awt.Color(255, 255, 255));
        vkey1.setBorder(null);
        verifyaccount16.add(vkey1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, 300, 50));

        jLabel165.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel165.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verifyaccount16.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 30, 40));

        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        verifyaccount16.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 410, 20));

        jLabel166.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(255, 255, 255));
        jLabel166.setText("Code");
        jLabel166.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verifyaccount16.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 490, 40, 40));

        jLabel167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel167.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verifyaccount16.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, 40, 40));

        jSeparator12.setForeground(new java.awt.Color(255, 255, 255));
        verifyaccount16.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, 410, 10));

        jButton12.setBackground(new java.awt.Color(14, 0, 82));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/login.png"))); // NOI18N
        jButton12.setText("Verify");
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        verifyaccount16.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, 120, 47));

        verifyUsername1.setBackground(new java.awt.Color(0, 5, 42));
        verifyUsername1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        verifyUsername1.setForeground(new java.awt.Color(255, 255, 255));
        verifyUsername1.setText("Your Email Address");
        verifyUsername1.setBorder(null);
        verifyUsername1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                verifyUsername1ComponentRemoved(evt);
            }
        });
        verifyUsername1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                verifyUsername1FocusGained(evt);
            }
        });
        verifyUsername1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyUsername1ActionPerformed(evt);
            }
        });
        verifyaccount16.add(verifyUsername1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 350, 40));

        StudentTabedPane.addTab("tab15", verifyaccount16);

        allStudent17.setBackground(new java.awt.Color(0, 5, 42));
        allStudent17.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                allStudent17ComponentShown(evt);
            }
        });
        allStudent17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view4.setBackground(new java.awt.Color(14, 0, 82));
        view4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view4.setForeground(new java.awt.Color(255, 255, 255));
        view4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        view4.setText("Back");
        view4.setBorder(null);
        view4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view4ActionPerformed(evt);
            }
        });
        allStudent17.add(view4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 120, 48));

        allStudentsByFaculty1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        allStudentsByFaculty1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Id", "Reg", "email", "phone", "hall"
            }
        ));
        allStudentsByFaculty1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allStudentsByFaculty1.setRowHeight(30);
        allStudentsByFaculty1.setSelectionBackground(new java.awt.Color(0, 5, 42));
        allStudentsByFaculty1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allStudentsByFaculty1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(allStudentsByFaculty1);
        if (allStudentsByFaculty1.getColumnModel().getColumnCount() > 0) {
            allStudentsByFaculty1.getColumnModel().getColumn(5).setMinWidth(200);
        }

        allStudent17.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 1050, 520));

        cseSession3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cseSession3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        cseSession3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cseSession3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cseSession3ItemStateChanged(evt);
            }
        });
        cseSession3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cseSession3ActionPerformed(evt);
            }
        });
        allStudent17.add(cseSession3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 240, 48));

        view5.setBackground(new java.awt.Color(14, 0, 82));
        view5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view5.setForeground(new java.awt.Color(255, 255, 255));
        view5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        view5.setText("View");
        view5.setBorder(null);
        view5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view5ActionPerformed(evt);
            }
        });
        allStudent17.add(view5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 120, 48));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        allStudent17.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 110, -1, 48));

        StudentTabedPane.addTab("tab16", allStudent17);

        studentsDetails18.setBackground(new java.awt.Color(0, 5, 42));
        studentsDetails18.setPreferredSize(new java.awt.Dimension(1110, 890));
        studentsDetails18.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                studentsDetails18ComponentShown(evt);
            }
        });
        studentsDetails18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/studentDetails.png"))); // NOI18N
        jLabel168.setText("jLabel16");
        studentsDetails18.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 620, 70));

        sDetailsProfilePic1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        studentsDetails18.add(sDetailsProfilePic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 200, 180));

        jLabel169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel169.setText("jLabel56");
        jLabel169.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 30, 30));

        jLabel170.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel170.setText("jLabel56");
        jLabel170.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 670, 30, 30));

        jLabel171.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(255, 255, 255));
        jLabel171.setText(":");
        jLabel171.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel171.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel171MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 710, 20, 30));

        jLabel172.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel172.setText("jLabel56");
        jLabel172.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 30, 30));

        jLabel173.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel173.setText("jLabel56");
        jLabel173.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 30, 30));

        jLabel174.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel174.setText("jLabel56");
        jLabel174.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 30, 30));

        jLabel175.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel175.setText("jLabel56");
        jLabel175.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 620, 30, 30));

        adminBlood4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood4.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood4.setText("Father Name");
        studentsDetails18.add(adminBlood4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 130, 30));

        adminDob14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob14.setForeground(new java.awt.Color(255, 255, 255));
        adminDob14.setText("Phone");
        studentsDetails18.add(adminDob14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 670, 70, -1));

        snid1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        snid1.setForeground(new java.awt.Color(255, 204, 0));
        snid1.setText("Name");
        studentsDetails18.add(snid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 710, 240, 30));

        adminDob15.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob15.setForeground(new java.awt.Color(255, 255, 255));
        adminDob15.setText("Mother Name");
        studentsDetails18.add(adminDob15, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 130, -1));

        adminDob16.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob16.setForeground(new java.awt.Color(255, 255, 255));
        adminDob16.setText("Address");
        studentsDetails18.add(adminDob16, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 90, -1));

        adminDob17.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob17.setForeground(new java.awt.Color(255, 255, 255));
        adminDob17.setText("Birth Date");
        studentsDetails18.add(adminDob17, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 570, 110, -1));

        adminDob18.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob18.setForeground(new java.awt.Color(255, 255, 255));
        adminDob18.setText("Blood Group ");
        studentsDetails18.add(adminDob18, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 620, 120, -1));

        jLabel176.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel176.setText("jLabel56");
        jLabel176.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 710, 30, 30));

        adminDob19.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob19.setForeground(new java.awt.Color(255, 255, 255));
        adminDob19.setText("Nid no");
        studentsDetails18.add(adminDob19, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 710, 70, -1));

        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel177.setText("jLabel56");
        jLabel177.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel177.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel177MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 30, 30));

        jLabel178.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(255, 255, 255));
        jLabel178.setText(":");
        jLabel178.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel178.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel178MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 20, 20));

        jLabel179.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel179.setForeground(new java.awt.Color(255, 255, 255));
        jLabel179.setText(":");
        jLabel179.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel179.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel179MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, 20, 20));

        jLabel180.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel180.setForeground(new java.awt.Color(255, 255, 255));
        jLabel180.setText(":");
        jLabel180.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel180.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel180MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, 20, 20));

        jLabel181.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel181.setForeground(new java.awt.Color(255, 255, 255));
        jLabel181.setText(":");
        jLabel181.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel181.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel181MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 20, 20));

        jLabel182.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel182.setForeground(new java.awt.Color(255, 255, 255));
        jLabel182.setText(":");
        jLabel182.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel182.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel182MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 570, 20, 20));

        jLabel183.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel183.setForeground(new java.awt.Color(255, 255, 255));
        jLabel183.setText(":");
        jLabel183.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel183.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel183MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel183, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 620, 20, 20));

        jLabel184.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel184.setForeground(new java.awt.Color(255, 255, 255));
        jLabel184.setText(":");
        jLabel184.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel184.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel184MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel184, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 670, 20, -1));

        adminNid4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid4.setForeground(new java.awt.Color(255, 255, 255));
        adminNid4.setText("Name");
        studentsDetails18.add(adminNid4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 90, 30));

        sname1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sname1.setForeground(new java.awt.Color(255, 204, 0));
        sname1.setText("Name");
        studentsDetails18.add(sname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 240, 40));

        sfname1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfname1.setForeground(new java.awt.Color(255, 204, 0));
        sfname1.setText("Name");
        studentsDetails18.add(sfname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 240, 40));

        smname1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        smname1.setForeground(new java.awt.Color(255, 204, 0));
        smname1.setText("Name");
        studentsDetails18.add(smname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 240, 40));

        saddr1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saddr1.setForeground(new java.awt.Color(255, 204, 0));
        saddr1.setText("Name");
        studentsDetails18.add(saddr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 240, 40));

        sdob1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sdob1.setForeground(new java.awt.Color(255, 204, 0));
        sdob1.setText("Name");
        studentsDetails18.add(sdob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 560, 240, 40));

        sblood1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sblood1.setForeground(new java.awt.Color(255, 204, 0));
        sblood1.setText("Name");
        studentsDetails18.add(sblood1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 610, 240, 40));

        sphone1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sphone1.setForeground(new java.awt.Color(255, 204, 0));
        sphone1.setText("Name");
        studentsDetails18.add(sphone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 660, 240, 40));

        adminNid5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid5.setForeground(new java.awt.Color(255, 255, 255));
        adminNid5.setText("Id");
        studentsDetails18.add(adminNid5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, 90, 30));

        jLabel185.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel185.setText("jLabel56");
        jLabel185.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel185.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel185MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel185, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 30, 30));

        jLabel186.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel186.setText("jLabel56");
        jLabel186.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel186, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 30, 30));

        adminBlood5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood5.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood5.setText("Reg");
        studentsDetails18.add(adminBlood5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 130, 30));

        jLabel187.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel187.setText("jLabel56");
        jLabel187.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 470, 30, 30));

        adminDob20.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob20.setForeground(new java.awt.Color(255, 255, 255));
        adminDob20.setText("Email");
        studentsDetails18.add(adminDob20, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 130, -1));

        jLabel188.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel188.setText("jLabel56");
        jLabel188.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 520, 30, 30));

        adminDob21.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob21.setForeground(new java.awt.Color(255, 255, 255));
        adminDob21.setText("Session");
        studentsDetails18.add(adminDob21, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, 90, -1));

        jLabel189.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel189.setText("jLabel56");
        jLabel189.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, 30, 30));

        adminDob22.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob22.setForeground(new java.awt.Color(255, 255, 255));
        adminDob22.setText("Faculty");
        studentsDetails18.add(adminDob22, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 570, 110, -1));

        jLabel190.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel190.setText("jLabel56");
        jLabel190.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 620, 30, 30));

        adminDob23.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob23.setForeground(new java.awt.Color(255, 255, 255));
        adminDob23.setText("Hall");
        studentsDetails18.add(adminDob23, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 120, -1));

        jLabel191.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel191.setText("jLabel56");
        jLabel191.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel191, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 670, 30, 30));

        adminDob24.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob24.setForeground(new java.awt.Color(255, 255, 255));
        adminDob24.setText("Semmister");
        studentsDetails18.add(adminDob24, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 670, 140, -1));

        jLabel192.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel192.setForeground(new java.awt.Color(255, 255, 255));
        jLabel192.setText(":");
        jLabel192.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel192.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel192MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, 20, 20));

        jLabel193.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel193.setForeground(new java.awt.Color(255, 255, 255));
        jLabel193.setText(":");
        jLabel193.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel193.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel193MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, 20, 20));

        jLabel194.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel194.setForeground(new java.awt.Color(255, 255, 255));
        jLabel194.setText(":");
        jLabel194.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel194.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel194MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel194, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 470, 20, 20));

        jLabel195.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel195.setForeground(new java.awt.Color(255, 255, 255));
        jLabel195.setText(":");
        jLabel195.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel195.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel195MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 520, 20, 20));

        jLabel196.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(255, 255, 255));
        jLabel196.setText(":");
        jLabel196.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel196.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel196MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel196, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 570, 20, 20));

        jLabel197.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel197.setForeground(new java.awt.Color(255, 255, 255));
        jLabel197.setText(":");
        jLabel197.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel197.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel197MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel197, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 620, 20, 20));

        jLabel198.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(255, 255, 255));
        jLabel198.setText(":");
        jLabel198.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel198.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel198MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel198, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 670, 20, -1));

        ssem1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssem1.setForeground(new java.awt.Color(255, 204, 0));
        ssem1.setText("Name");
        studentsDetails18.add(ssem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 660, 240, 40));

        shall1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shall1.setForeground(new java.awt.Color(255, 204, 0));
        shall1.setText("Name");
        studentsDetails18.add(shall1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 610, 240, 40));

        sfac1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfac1.setForeground(new java.awt.Color(255, 204, 0));
        sfac1.setText("Name");
        studentsDetails18.add(sfac1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 560, 240, 40));

        ssession1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssession1.setForeground(new java.awt.Color(255, 204, 0));
        ssession1.setText("Name");
        studentsDetails18.add(ssession1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 240, 40));

        semail1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        semail1.setForeground(new java.awt.Color(255, 204, 0));
        semail1.setText("Name");
        studentsDetails18.add(semail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 460, 240, 40));

        sreg1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sreg1.setForeground(new java.awt.Color(255, 204, 0));
        sreg1.setText("Name");
        studentsDetails18.add(sreg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 410, 240, 40));

        sid1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sid1.setForeground(new java.awt.Color(255, 204, 0));
        sid1.setText("Name");
        studentsDetails18.add(sid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 360, 240, 40));

        printbtn1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        printbtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/print.png"))); // NOI18N
        printbtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtn1ActionPerformed(evt);
            }
        });
        studentsDetails18.add(printbtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 60, 40));

        view6.setBackground(new java.awt.Color(14, 0, 82));
        view6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view6.setForeground(new java.awt.Color(255, 255, 255));
        view6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        view6.setText("Back");
        view6.setBorder(null);
        view6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view6ActionPerformed(evt);
            }
        });
        studentsDetails18.add(view6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 40));

        jLabel199.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel199.setText("jLabel56");
        jLabel199.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails18.add(jLabel199, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 30, 30));

        adminDob25.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob25.setForeground(new java.awt.Color(255, 255, 255));
        adminDob25.setText("Status");
        studentsDetails18.add(adminDob25, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 70, 30));

        jLabel200.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel200.setForeground(new java.awt.Color(255, 255, 255));
        jLabel200.setText(":");
        jLabel200.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel200.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel200MouseClicked(evt);
            }
        });
        studentsDetails18.add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 20, 30));

        sstatusBtn1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sstatusBtn1.setBorder(null);
        sstatusBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sstatusBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sstatusBtn1ActionPerformed(evt);
            }
        });
        studentsDetails18.add(sstatusBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 110, 35));

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        studentsDetails18.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, 60, 40));

        StudentTabedPane.addTab("tab2", studentsDetails18);

        contentPanel.add(StudentTabedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 1115, 890));

        dashboardPanel01.setBackground(new java.awt.Color(0, 5, 42));
        dashboardPanel01.setPreferredSize(new java.awt.Dimension(1200, 855));
        dashboardPanel01.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/bordertop left.png"))); // NOI18N
        jLabel1.setText("Student");
        dashboardPanel01.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 127, 300, 87));

        AdminTabedPane.addTab("tab1", dashboardPanel01);

        profileViewPanel02.setBackground(new java.awt.Color(0, 5, 42));
        profileViewPanel02.setPreferredSize(new java.awt.Dimension(1110, 890));
        profileViewPanel02.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Change Profile :");
        profileViewPanel02.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 160, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/pastu-admin.png"))); // NOI18N
        jLabel16.setText("jLabel16");
        profileViewPanel02.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 400, 70));

        UpdateProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        profileViewPanel02.add(UpdateProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 200, 180));

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel56.setText("jLabel56");
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 30, 40));

        adminName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        adminName.setForeground(new java.awt.Color(153, 0, 102));
        adminName.setText("Name");
        profileViewPanel02.add(adminName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 410, 40));

        file.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/folder.png"))); // NOI18N
        file.setText("jLabel56");
        file.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileMouseClicked(evt);
            }
        });
        profileViewPanel02.add(file, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, 40, 30));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel65.setText("jLabel56");
        jLabel65.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, 30, 30));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/nid.png"))); // NOI18N
        jLabel66.setText("jLabel56");
        jLabel66.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });
        profileViewPanel02.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 30, 30));

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/dob.png"))); // NOI18N
        jLabel67.setText("jLabel56");
        jLabel67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 30, 30));

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/uname.png"))); // NOI18N
        jLabel68.setText("jLabel56");
        jLabel68.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 30, 30));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/phone.png"))); // NOI18N
        jLabel69.setText("jLabel56");
        jLabel69.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 30, 30));

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel70.setText("jLabel56");
        jLabel70.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 30, 30));

        adminEmail.setBackground(new java.awt.Color(0, 5, 42));
        adminEmail.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminEmail.setForeground(new java.awt.Color(255, 255, 255));
        adminEmail.setText("gfhgj");
        adminEmail.setBorder(null);
        profileViewPanel02.add(adminEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 290, 40));
        profileViewPanel02.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 300, 10));

        adminBlood.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood.setForeground(new java.awt.Color(255, 204, 0));
        adminBlood.setText("Name");
        profileViewPanel02.add(adminBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 300, -1));
        profileViewPanel02.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 300, 10));
        profileViewPanel02.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 300, 10));

        adminUsername.setBackground(new java.awt.Color(0, 5, 42));
        adminUsername.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminUsername.setForeground(new java.awt.Color(255, 255, 255));
        adminUsername.setText("ghghghgh");
        adminUsername.setBorder(null);
        profileViewPanel02.add(adminUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 300, 40));

        adminPhone.setBackground(new java.awt.Color(0, 5, 42));
        adminPhone.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminPhone.setForeground(new java.awt.Color(255, 255, 255));
        adminPhone.setText("iiiiii");
        adminPhone.setBorder(null);
        adminPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPhoneActionPerformed(evt);
            }
        });
        profileViewPanel02.add(adminPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 290, 40));

        adminPassword.setBackground(new java.awt.Color(0, 5, 42));
        adminPassword.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminPassword.setForeground(new java.awt.Color(255, 255, 255));
        adminPassword.setText("uiiuo");
        adminPassword.setBorder(null);
        profileViewPanel02.add(adminPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, 300, 40));

        jButton3.setBackground(new java.awt.Color(14, 0, 82));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/send.png"))); // NOI18N
        jButton3.setText("Update");
        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        profileViewPanel02.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 110, 50));

        adminDob.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob.setForeground(new java.awt.Color(255, 204, 0));
        adminDob.setText("Name");
        profileViewPanel02.add(adminDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 310, -1));

        adminNid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid.setForeground(new java.awt.Color(255, 204, 0));
        adminNid.setText("Nid");
        profileViewPanel02.add(adminNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 260, 30));

        bottomrightborder5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        profileViewPanel02.add(bottomrightborder5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 330, 340));
        profileViewPanel02.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 300, 10));

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/blood.png"))); // NOI18N
        jLabel61.setText("jLabel56");
        jLabel61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profileViewPanel02.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 30));

        AdminTabedPane.addTab("tab2", profileViewPanel02);

        studentPanel03.setBackground(new java.awt.Color(0, 5, 42));
        studentPanel03.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                studentPanel03ComponentShown(evt);
            }
        });
        studentPanel03.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/student.png"))); // NOI18N
        studentPanel03.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, 410, 60));

        addStudentBtn.setBackground(new java.awt.Color(14, 0, 82));
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
        studentPanel03.add(addStudentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 170, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        studentPanel03.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 390, 270));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        studentPanel03.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 200, 200));

        serchIdField.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        serchIdField.setText("Search with student id");
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
        serchIdField.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                serchIdFieldComponentShown(evt);
            }
        });
        serchIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchIdFieldActionPerformed(evt);
            }
        });
        studentPanel03.add(serchIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

        addNewStudent2.setBackground(new java.awt.Color(14, 0, 82));
        addNewStudent2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        addNewStudent2.setActionCommand("Add Student");
        addNewStudent2.setBorder(null);
        addNewStudent2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewStudent2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStudent2ActionPerformed(evt);
            }
        });
        studentPanel03.add(addNewStudent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        studentPanel03.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

        cseSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cseSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cse Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        cseSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cseSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cseSessionItemStateChanged(evt);
            }
        });
        cseSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cseSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(cseSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

        agriSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        agriSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        agriSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        agriSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                agriSessionItemStateChanged(evt);
            }
        });
        agriSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agriSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(agriSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

        dvmSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        dvmSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dvm Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        dvmSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dvmSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dvmSessionItemStateChanged(evt);
            }
        });
        dvmSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dvmSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(dvmSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

        bamSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bamSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bam Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        bamSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bamSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bamSessionItemStateChanged(evt);
            }
        });
        bamSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bamSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(bamSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

        fishSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fishSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        fishSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fishSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fishSessionItemStateChanged(evt);
            }
        });
        fishSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fishSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(fishSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

        nfsSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        nfsSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nfs Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        nfsSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nfsSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                nfsSessionItemStateChanged(evt);
            }
        });
        nfsSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nfsSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(nfsSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

        esdmSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        esdmSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        esdmSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        esdmSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                esdmSessionItemStateChanged(evt);
            }
        });
        esdmSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                esdmSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(esdmSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

        llaSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        llaSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lla Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        llaSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        llaSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                llaSessionItemStateChanged(evt);
            }
        });
        llaSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                llaSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(llaSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

        ahSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ahSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ah Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        ahSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ahSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ahSessionItemStateChanged(evt);
            }
        });
        ahSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ahSessionActionPerformed(evt);
            }
        });
        studentPanel03.add(ahSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        AdminTabedPane.addTab("tab3", studentPanel03);

        teacherPanel04.setBackground(new java.awt.Color(0, 5, 42));
        teacherPanel04.setPreferredSize(new java.awt.Dimension(1100, 770));
        teacherPanel04.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        teacherPanel04.add(serchIdField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

        addNewStudent3.setBackground(new java.awt.Color(14, 0, 82));
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
        teacherPanel04.add(addNewStudent3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jComboBox11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NFS Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

        jComboBox12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

        jComboBox13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BBA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

        jComboBox14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DVM Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

        jComboBox15.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

        jComboBox16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CSE Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jComboBox17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

        jComboBox18.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LLA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

        jComboBox19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AH Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });
        teacherPanel04.add(jComboBox19, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        teacherPanel04.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminTeacher.png"))); // NOI18N
        teacherPanel04.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 350, 350));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        teacherPanel04.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 240, -1, 80));

        addStudentBtn2.setBackground(new java.awt.Color(14, 0, 82));
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
        teacherPanel04.add(addStudentBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 220, 50));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel57.setText("jLabel8");
        teacherPanel04.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, 200, 200));

        AdminTabedPane.addTab("tab4", teacherPanel04);

        facultyPanel05.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout facultyPanel05Layout = new javax.swing.GroupLayout(facultyPanel05);
        facultyPanel05.setLayout(facultyPanel05Layout);
        facultyPanel05Layout.setHorizontalGroup(
            facultyPanel05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        facultyPanel05Layout.setVerticalGroup(
            facultyPanel05Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab5", facultyPanel05);

        hallPanel06.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout hallPanel06Layout = new javax.swing.GroupLayout(hallPanel06);
        hallPanel06.setLayout(hallPanel06Layout);
        hallPanel06Layout.setHorizontalGroup(
            hallPanel06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        hallPanel06Layout.setVerticalGroup(
            hallPanel06Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab6", hallPanel06);

        LibraryPanel07.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout LibraryPanel07Layout = new javax.swing.GroupLayout(LibraryPanel07);
        LibraryPanel07.setLayout(LibraryPanel07Layout);
        LibraryPanel07Layout.setHorizontalGroup(
            LibraryPanel07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        LibraryPanel07Layout.setVerticalGroup(
            LibraryPanel07Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab7", LibraryPanel07);

        bankPanel08.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout bankPanel08Layout = new javax.swing.GroupLayout(bankPanel08);
        bankPanel08.setLayout(bankPanel08Layout);
        bankPanel08Layout.setHorizontalGroup(
            bankPanel08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        bankPanel08Layout.setVerticalGroup(
            bankPanel08Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab8", bankPanel08);

        healthPanel09.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout healthPanel09Layout = new javax.swing.GroupLayout(healthPanel09);
        healthPanel09.setLayout(healthPanel09Layout);
        healthPanel09Layout.setHorizontalGroup(
            healthPanel09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        healthPanel09Layout.setVerticalGroup(
            healthPanel09Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab9", healthPanel09);

        AdministrationPanel10.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout AdministrationPanel10Layout = new javax.swing.GroupLayout(AdministrationPanel10);
        AdministrationPanel10.setLayout(AdministrationPanel10Layout);
        AdministrationPanel10Layout.setHorizontalGroup(
            AdministrationPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        AdministrationPanel10Layout.setVerticalGroup(
            AdministrationPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab10", AdministrationPanel10);

        organaizationPanel11.setBackground(new java.awt.Color(0, 5, 42));
        organaizationPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Dashboard");
        organaizationPanel11.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 183, 389, 82));

        AdminTabedPane.addTab("tab11", organaizationPanel11);

        transportPanel12.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout transportPanel12Layout = new javax.swing.GroupLayout(transportPanel12);
        transportPanel12.setLayout(transportPanel12Layout);
        transportPanel12Layout.setHorizontalGroup(
            transportPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        transportPanel12Layout.setVerticalGroup(
            transportPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab12", transportPanel12);

        othersPanel13.setBackground(new java.awt.Color(0, 5, 42));

        javax.swing.GroupLayout othersPanel13Layout = new javax.swing.GroupLayout(othersPanel13);
        othersPanel13.setLayout(othersPanel13Layout);
        othersPanel13Layout.setHorizontalGroup(
            othersPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        othersPanel13Layout.setVerticalGroup(
            othersPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        AdminTabedPane.addTab("tab13", othersPanel13);

        addStudent14.setBackground(new java.awt.Color(0, 5, 42));
        addStudent14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/addStudent.png"))); // NOI18N
        addStudent14.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 470, 80));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        jLabel30.setText("jLabel5");
        addStudent14.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 390, 260));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText(":");
        addStudent14.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 10, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText(":");
        addStudent14.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 10, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText(":");
        addStudent14.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 470, 10, 40));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText(":");
        addStudent14.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 520, 10, 40));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText(":");
        addStudent14.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, 10, 30));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText(":");
        addStudent14.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 10, 40));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Birth Date");
        addStudent14.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 90, 40));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Nid No ");
        addStudent14.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 70, 40));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Address");
        addStudent14.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 80, 40));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Mother Name");
        addStudent14.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 120, 40));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Father Name");
        addStudent14.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 110, 40));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Name");
        addStudent14.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 60, 40));

        nameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        nameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameValActionPerformed(evt);
            }
        });
        addStudent14.add(nameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 300, 40));

        fnameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fnameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fnameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameValActionPerformed(evt);
            }
        });
        addStudent14.add(fnameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 300, 40));

        mnameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        mnameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnameValActionPerformed(evt);
            }
        });
        addStudent14.add(mnameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, 300, 40));

        addrVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addrVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addrVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrValActionPerformed(evt);
            }
        });
        addStudent14.add(addrVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 300, 40));

        nidVal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nidVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nidVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nidValActionPerformed(evt);
            }
        });
        addStudent14.add(nidVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, 300, 40));

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
        addStudent14.add(dobVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 300, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText(":");
        addStudent14.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, 20, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText(":");
        addStudent14.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 430, 20, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText(":");
        addStudent14.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 20, 40));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText(":");
        addStudent14.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 520, 20, 30));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText(":");
        addStudent14.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 570, 20, 40));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText(":");
        addStudent14.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 620, 20, 30));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Faculty");
        addStudent14.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 570, 60, 40));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Blood");
        addStudent14.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 520, 50, 30));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Session");
        addStudent14.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 470, 70, 40));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Reg");
        addStudent14.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 40, 40));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Id");
        addStudent14.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 30, 40));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Hall");
        addStudent14.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 40, 30));

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
        addStudent14.add(idVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, 300, 40));

        regVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        regVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        regVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regValActionPerformed(evt);
            }
        });
        addStudent14.add(regVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 300, 40));

        sessionVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        sessionVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", "2021-2022", "2022-2023", "2023-2024", "2024-2025", "2025-2026", " " }));
        sessionVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent14.add(sessionVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, 300, 40));

        bloodVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bloodVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "A +", "A -", "B +", "B -", "O +", "O -", "AB +", "AB -", " " }));
        bloodVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent14.add(bloodVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 300, 40));

        facVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Computer Science & Engineering", "Agriculture", "Business Administration & Management", "Animal Husbandry", "Doctor Veterinary Medicine", "Fisheries", "Environmental Science and Disaster Management", "Nutrition and Food Science", "Land Management and Administration" }));
        facVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facVal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facValItemStateChanged(evt);
            }
        });
        facVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facValActionPerformed(evt);
            }
        });
        addStudent14.add(facVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 570, 300, 40));

        hallVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        hallVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Sher-E-Bangla Hall 1", "Sher-E-Bangla Hall 2", "Keramat Ali Hall", "Bangabandhu Sheikh Mujibor Rahman Hall", "Kobi Begum Sufia Kamal Hall", "Sheikh Fajilatunnesa Mujib Hall", "Captain Mohiuddin Jahangir Hall", "Sheikh Fajilatunnesa Mujib Hall-Babuganj", " " }));
        hallVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudent14.add(hallVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 620, 300, 40));

        alertSuccessText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        alertSuccessText.setForeground(new java.awt.Color(255, 255, 255));
        addStudent14.add(alertSuccessText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 360, 50));

        jButton1.setBackground(new java.awt.Color(14, 0, 82));
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
        addStudent14.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 100, 40));

        jButton2.setBackground(new java.awt.Color(14, 0, 82));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
        addStudent14.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 670, 100, 40));

        bottomrightborder4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/bordertop left270_270.png"))); // NOI18N
        addStudent14.add(bottomrightborder4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, 240));

        AdminTabedPane.addTab("tab14", addStudent14);

        verifyaccount15.setBackground(new java.awt.Color(0, 5, 42));
        verifyaccount15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userVerifyIdentity.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        userVerifyIdentity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select yuor identity", "Student", "Teacher", "Admin" }));
        userVerifyIdentity.setBorder(null);
        userVerifyIdentity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userVerifyIdentityActionPerformed(evt);
            }
        });
        verifyaccount15.add(userVerifyIdentity, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, 190, 40));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/verifyAccount.png"))); // NOI18N
        verifyaccount15.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 580, 90));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel58.setText("jLabel8");
        verifyaccount15.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 200, 200));

        jButton5.setBackground(new java.awt.Color(14, 0, 82));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        jButton5.setText("Back");
        jButton5.setBorder(null);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        verifyaccount15.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 560, 100, 45));

        bottomrightborder2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border left bottom .png"))); // NOI18N
        verifyaccount15.add(bottomrightborder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 350, 370));

        bottomrightborder3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        verifyaccount15.add(bottomrightborder3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, 330, 340));

        vkey.setBackground(new java.awt.Color(0, 5, 42));
        vkey.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        vkey.setForeground(new java.awt.Color(255, 255, 255));
        vkey.setBorder(null);
        verifyaccount15.add(vkey, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, 300, 50));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verifyaccount15.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 30, 40));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        verifyaccount15.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 410, 20));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Code");
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verifyaccount15.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 490, 40, 40));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verifyaccount15.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, 40, 40));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        verifyaccount15.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, 410, 10));

        jButton4.setBackground(new java.awt.Color(14, 0, 82));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/login.png"))); // NOI18N
        jButton4.setText("Verify");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        verifyaccount15.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, 120, 47));

        verifyUsername.setBackground(new java.awt.Color(0, 5, 42));
        verifyUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        verifyUsername.setForeground(new java.awt.Color(255, 255, 255));
        verifyUsername.setText("Your Email Address");
        verifyUsername.setBorder(null);
        verifyUsername.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                verifyUsernameComponentRemoved(evt);
            }
        });
        verifyUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                verifyUsernameFocusGained(evt);
            }
        });
        verifyUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyUsernameActionPerformed(evt);
            }
        });
        verifyaccount15.add(verifyUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 350, 40));

        AdminTabedPane.addTab("tab15", verifyaccount15);

        allStudent16.setBackground(new java.awt.Color(0, 5, 42));
        allStudent16.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                allStudent16ComponentShown(evt);
            }
        });
        allStudent16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        view1.setBackground(new java.awt.Color(14, 0, 82));
        view1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view1.setForeground(new java.awt.Color(255, 255, 255));
        view1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        view1.setText("Back");
        view1.setBorder(null);
        view1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view1ActionPerformed(evt);
            }
        });
        allStudent16.add(view1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 120, 48));

        allStudentsByFaculty.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        allStudentsByFaculty.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Id", "Reg", "email", "phone", "hall"
            }
        ));
        allStudentsByFaculty.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allStudentsByFaculty.setRowHeight(30);
        allStudentsByFaculty.setSelectionBackground(new java.awt.Color(0, 5, 42));
        allStudentsByFaculty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allStudentsByFacultyMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(allStudentsByFaculty);
        if (allStudentsByFaculty.getColumnModel().getColumnCount() > 0) {
            allStudentsByFaculty.getColumnModel().getColumn(5).setMinWidth(200);
        }

        allStudent16.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 1050, 520));

        cseSession1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cseSession1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        cseSession1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cseSession1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cseSession1ItemStateChanged(evt);
            }
        });
        cseSession1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cseSession1ActionPerformed(evt);
            }
        });
        allStudent16.add(cseSession1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 240, 48));

        view2.setBackground(new java.awt.Color(14, 0, 82));
        view2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view2.setForeground(new java.awt.Color(255, 255, 255));
        view2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        view2.setText("View");
        view2.setBorder(null);
        view2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view2ActionPerformed(evt);
            }
        });
        allStudent16.add(view2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 120, 48));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        allStudent16.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 110, -1, 48));

        AdminTabedPane.addTab("tab16", allStudent16);

        studentsDetails17.setBackground(new java.awt.Color(0, 5, 42));
        studentsDetails17.setPreferredSize(new java.awt.Dimension(1110, 890));
        studentsDetails17.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                studentsDetails17ComponentShown(evt);
            }
        });
        studentsDetails17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/studentDetails.png"))); // NOI18N
        jLabel63.setText("jLabel16");
        studentsDetails17.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 620, 70));

        sDetailsProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        studentsDetails17.add(sDetailsProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 200, 180));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel64.setText("jLabel56");
        jLabel64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 30, 30));

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel71.setText("jLabel56");
        jLabel71.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 670, 30, 30));

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText(":");
        jLabel72.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel72MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 710, 20, 30));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel73.setText("jLabel56");
        jLabel73.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 30, 30));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel74.setText("jLabel56");
        jLabel74.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 30, 30));

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel75.setText("jLabel56");
        jLabel75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 30, 30));

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel76.setText("jLabel56");
        jLabel76.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 620, 30, 30));

        adminBlood1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood1.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood1.setText("Father Name");
        studentsDetails17.add(adminBlood1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 130, 30));

        adminDob1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob1.setForeground(new java.awt.Color(255, 255, 255));
        adminDob1.setText("Phone");
        studentsDetails17.add(adminDob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 670, 70, -1));

        snid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        snid.setForeground(new java.awt.Color(255, 204, 0));
        snid.setText("Name");
        studentsDetails17.add(snid, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 710, 240, 30));

        adminDob2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob2.setForeground(new java.awt.Color(255, 255, 255));
        adminDob2.setText("Mother Name");
        studentsDetails17.add(adminDob2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 130, -1));

        adminDob3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob3.setForeground(new java.awt.Color(255, 255, 255));
        adminDob3.setText("Address");
        studentsDetails17.add(adminDob3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 90, -1));

        adminDob4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob4.setForeground(new java.awt.Color(255, 255, 255));
        adminDob4.setText("Birth Date");
        studentsDetails17.add(adminDob4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 570, 110, -1));

        adminDob5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob5.setForeground(new java.awt.Color(255, 255, 255));
        adminDob5.setText("Blood Group ");
        studentsDetails17.add(adminDob5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 620, 120, -1));

        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel77.setText("jLabel56");
        jLabel77.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 710, 30, 30));

        adminDob6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob6.setForeground(new java.awt.Color(255, 255, 255));
        adminDob6.setText("Nid no");
        studentsDetails17.add(adminDob6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 710, 70, -1));

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel78.setText("jLabel56");
        jLabel78.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 30, 30));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText(":");
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel79MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 20, 20));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText(":");
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel80MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, 20, 20));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText(":");
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel81MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, 20, 20));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText(":");
        jLabel82.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel82MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 20, 20));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText(":");
        jLabel83.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel83MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 570, 20, 20));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText(":");
        jLabel84.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel84MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 620, 20, 20));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText(":");
        jLabel85.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel85MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 670, 20, -1));

        adminNid2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid2.setForeground(new java.awt.Color(255, 255, 255));
        adminNid2.setText("Name");
        studentsDetails17.add(adminNid2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 90, 30));

        sname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sname.setForeground(new java.awt.Color(255, 204, 0));
        sname.setText("Name");
        studentsDetails17.add(sname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 240, 40));

        sfname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfname.setForeground(new java.awt.Color(255, 204, 0));
        sfname.setText("Name");
        studentsDetails17.add(sfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 240, 40));

        smname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        smname.setForeground(new java.awt.Color(255, 204, 0));
        smname.setText("Name");
        studentsDetails17.add(smname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 240, 40));

        saddr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saddr.setForeground(new java.awt.Color(255, 204, 0));
        saddr.setText("Name");
        studentsDetails17.add(saddr, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 240, 40));

        sdob.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sdob.setForeground(new java.awt.Color(255, 204, 0));
        sdob.setText("Name");
        studentsDetails17.add(sdob, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 560, 240, 40));

        sblood.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sblood.setForeground(new java.awt.Color(255, 204, 0));
        sblood.setText("Name");
        studentsDetails17.add(sblood, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 610, 240, 40));

        sphone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sphone.setForeground(new java.awt.Color(255, 204, 0));
        sphone.setText("Name");
        studentsDetails17.add(sphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 660, 240, 40));

        adminNid3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid3.setForeground(new java.awt.Color(255, 255, 255));
        adminNid3.setText("Id");
        studentsDetails17.add(adminNid3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, 90, 30));

        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel86.setText("jLabel56");
        jLabel86.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel86.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel86MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 30, 30));

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel87.setText("jLabel56");
        jLabel87.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 30, 30));

        adminBlood2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood2.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood2.setText("Reg");
        studentsDetails17.add(adminBlood2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 130, 30));

        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel88.setText("jLabel56");
        jLabel88.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 470, 30, 30));

        adminDob7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob7.setForeground(new java.awt.Color(255, 255, 255));
        adminDob7.setText("Email");
        studentsDetails17.add(adminDob7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 130, -1));

        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel89.setText("jLabel56");
        jLabel89.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 520, 30, 30));

        adminDob8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob8.setForeground(new java.awt.Color(255, 255, 255));
        adminDob8.setText("Session");
        studentsDetails17.add(adminDob8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, 90, -1));

        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel90.setText("jLabel56");
        jLabel90.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, 30, 30));

        adminDob9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob9.setForeground(new java.awt.Color(255, 255, 255));
        adminDob9.setText("Faculty");
        studentsDetails17.add(adminDob9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 570, 110, -1));

        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel91.setText("jLabel56");
        jLabel91.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 620, 30, 30));

        adminDob10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob10.setForeground(new java.awt.Color(255, 255, 255));
        adminDob10.setText("Hall");
        studentsDetails17.add(adminDob10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 120, -1));

        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel92.setText("jLabel56");
        jLabel92.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 670, 30, 30));

        adminDob11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob11.setForeground(new java.awt.Color(255, 255, 255));
        adminDob11.setText("Semmister");
        studentsDetails17.add(adminDob11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 670, 140, -1));

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText(":");
        jLabel94.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel94.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel94MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, 20, 20));

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText(":");
        jLabel95.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel95.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel95MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, 20, 20));

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText(":");
        jLabel96.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel96.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel96MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 470, 20, 20));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText(":");
        jLabel97.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel97.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel97MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 520, 20, 20));

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText(":");
        jLabel98.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel98.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel98MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 570, 20, 20));

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText(":");
        jLabel99.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel99.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel99MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 620, 20, 20));

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setText(":");
        jLabel100.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel100.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel100MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 670, 20, -1));

        ssem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssem.setForeground(new java.awt.Color(255, 204, 0));
        ssem.setText("Name");
        studentsDetails17.add(ssem, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 660, 240, 40));

        shall.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shall.setForeground(new java.awt.Color(255, 204, 0));
        shall.setText("Name");
        studentsDetails17.add(shall, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 610, 240, 40));

        sfac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfac.setForeground(new java.awt.Color(255, 204, 0));
        sfac.setText("Name");
        studentsDetails17.add(sfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 560, 240, 40));

        ssession.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssession.setForeground(new java.awt.Color(255, 204, 0));
        ssession.setText("Name");
        studentsDetails17.add(ssession, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 240, 40));

        semail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        semail.setForeground(new java.awt.Color(255, 204, 0));
        semail.setText("Name");
        studentsDetails17.add(semail, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 460, 240, 40));

        sreg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sreg.setForeground(new java.awt.Color(255, 204, 0));
        sreg.setText("Name");
        studentsDetails17.add(sreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 410, 240, 40));

        sid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sid.setForeground(new java.awt.Color(255, 204, 0));
        sid.setText("Name");
        studentsDetails17.add(sid, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 360, 240, 40));

        printbtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        printbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/print.png"))); // NOI18N
        printbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtnActionPerformed(evt);
            }
        });
        studentsDetails17.add(printbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 60, 40));

        view3.setBackground(new java.awt.Color(14, 0, 82));
        view3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view3.setForeground(new java.awt.Color(255, 255, 255));
        view3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        view3.setText("Back");
        view3.setBorder(null);
        view3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view3ActionPerformed(evt);
            }
        });
        studentsDetails17.add(view3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 40));

        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel102.setText("jLabel56");
        jLabel102.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentsDetails17.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 30, 30));

        adminDob13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob13.setForeground(new java.awt.Color(255, 255, 255));
        adminDob13.setText("Status");
        studentsDetails17.add(adminDob13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 70, 30));

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setText(":");
        jLabel103.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel103.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel103MouseClicked(evt);
            }
        });
        studentsDetails17.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 20, 30));

        sstatusBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sstatusBtn.setBorder(null);
        sstatusBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sstatusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sstatusBtnActionPerformed(evt);
            }
        });
        studentsDetails17.add(sstatusBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 110, 35));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        studentsDetails17.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, 60, 40));

        AdminTabedPane.addTab("tab2", studentsDetails17);

        contentPanel.add(AdminTabedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 1115, 890));

        getContentPane().add(contentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, -35, 1115, 890));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        dispose();
                Login log = new Login();
                log.setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

    private void Hall1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hall1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(5);
    }//GEN-LAST:event_Hall1ActionPerformed

    private void library1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_library1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(6);
    }//GEN-LAST:event_library1ActionPerformed

    private void faculty1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faculty1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(4);
    }//GEN-LAST:event_faculty1ActionPerformed

    private void teacher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacher1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(3);
    }//GEN-LAST:event_teacher1ActionPerformed

    private void bank1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bank1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(7);
    }//GEN-LAST:event_bank1ActionPerformed

    private void health1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_health1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(8);
    }//GEN-LAST:event_health1ActionPerformed

    private void administrator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administrator1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(9);
    }//GEN-LAST:event_administrator1ActionPerformed

    private void organaization1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organaization1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(10);
    }//GEN-LAST:event_organaization1ActionPerformed

    private void transport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transport1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(11);
    }//GEN-LAST:event_transport1ActionPerformed

    private void others1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_others1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(12);
    }//GEN-LAST:event_others1ActionPerformed

    private void student1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student1ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(2);
    }//GEN-LAST:event_student1ActionPerformed

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
        // TODO add your handling code here:

         AdminTabedPane.setSelectedIndex(1);
    }//GEN-LAST:event_viewActionPerformed

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(0);
    }//GEN-LAST:event_dashboardActionPerformed

    private void addStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtnActionPerformed
        // TODO add your handling code here:        
        AdminTabedPane.setSelectedIndex(13);
    }//GEN-LAST:event_addStudentBtnActionPerformed

    private void cseSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cseSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cseSessionActionPerformed

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
             studentsDetailsId = serchIdField.getText();
             if("".equals(studentsDetailsId) || "Search with student id".equals(studentsDetailsId)){ 
             
                alert("error","true","Insert student id");
             }else{ 
             AdminTabedPane.setSelectedIndex(16);     
            serchIdField.setText("Search with student id");
             }
//        System.out.println(serchIdField.getText());
////        new print(serchIdField.getText()).setVisible(true);
//        print ok = new print(serchIdField.getText());
//        ok.prints();
//        serchIdField.setText("Search with student id");
    }//GEN-LAST:event_addNewStudent2ActionPerformed

    private void agriSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agriSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_agriSessionActionPerformed

    private void dvmSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dvmSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dvmSessionActionPerformed

    private void bamSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bamSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bamSessionActionPerformed

    private void fishSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fishSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fishSessionActionPerformed

    private void nfsSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nfsSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nfsSessionActionPerformed

    private void esdmSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esdmSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_esdmSessionActionPerformed

    private void llaSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_llaSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_llaSessionActionPerformed

    private void ahSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ahSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ahSessionActionPerformed

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       AdminTabedPane.setSelectedIndex(2);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling
        hide();
        openSidebar(identity);
        
        AdminDashboards.func object = new AdminDashboards.func();
        try { 
            //  recive func meathoad return query result                   
            rs = object.find(getadminusername,identity);
            if(rs.next()){
                logeduserid = rs.getString(6);
                
                byte[] img = rs.getBytes("photo");
                if(img != null){ 
                    ImageIcon MyImage1 = new ImageIcon(img);
                    Image img1 = MyImage1.getImage();
                    Image newImage1 = img1.getScaledInstance(profilepic.getWidth(), profilepic.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon image1 = new ImageIcon(newImage1);
                    profilepic.setIcon(image1);
                    UpdateProfilePic.setIcon(image1);
                }
                
                adminName.setText(rs.getString(2));
                adminNid.setText(rs.getString(8));
                adminBlood.setText(rs.getString(11));
                adminDob.setText(rs.getString(10));
                adminUsername.setText(rs.getString(3));
                adminPhone.setText(rs.getString(9));
                adminPassword.setText(rs.getString(4));
                adminEmail.setText(rs.getString(7));
                
            }
        } catch (SQLException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String name = make_String_Cappitalize(nameVal.getText());
        String fname =make_String_Cappitalize(fnameVal.getText()) ;
        String mname =make_String_Cappitalize(mnameVal.getText()) ;
        String id = idVal.getText();
        String reg =  regVal.getText();
        String addr = addrVal.getText();
        String nid =  nidVal.getText();
        String session =  (String)sessionVal.getSelectedItem();
        String fac = (String)facVal.getSelectedItem();
        fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac).toLowerCase();
        String hall = (String)hallVal.getSelectedItem();
        hall = getfirstLeter_replace_some_speacial_charecter_and_word(hall).toLowerCase();
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
                                                                }else{alert("error","true","Select student Residence hall");}
                                                            }else{alert("error","true","Select student Faculty");}
                                                        }else{alert("error","true","Select student Blood group");}
                                                    }else{alert("error","true","Select student Session");}
                                                }
                                            }else{alert("error","true","You insert wrong Registration number");}
                                        }
                                    }else{alert("error","true","You insert wrong Id");}

                                }else{ alert("error","true","Select Birth Date");}
                            }
                        }else{alert("error","true","You insert wrong Nid");}                       
                    }else{alert("error","true","Insert Address");}
                }else{alert("error","true","Mother name should be alpahbet");}
            }else{alert("error","true","Fataher name should be alpahbet");}
        }else{alert("error","true","Name should be alpahbet");}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileMouseClicked
        // TODO add your handling code here:
              
        conn cc = new conn();
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
        
        
        
       int result = fileChooser.showSaveDialog(null);
       if(result == JFileChooser.APPROVE_OPTION){
            try {
                File selectedFile = fileChooser.getSelectedFile();
                String photoPath = selectedFile.getAbsolutePath();
                UpdateProfilePic.setIcon(ResizeImage(photoPath));
                profilepic.setIcon(ResizeImage(photoPath));
                imagePath = photoPath;
                
                AdminDashboards.test f = new AdminDashboards.test();
                rs = f.finds(getadminusername,identity); 
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       }
    }//GEN-LAST:event_fileMouseClicked

    private void jLabel66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel66MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
            random = new Random().nextInt(900000) + 100000;
            
            String to = adminEmail.getText(); // to address. It can be any like gmail, yahoo etc.
            String from = "somethingisw@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
            String password = "bismillahw@gmail.com180204308453"; // password for from gmail address that you have used in above line. 

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(from, password);
             }
            });


            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("PSTU Verification Mail");
                message.setContent("<div style=\" max-width:450px;margin:0px auto; background-color:white;box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;  \"><table style=\"font-family:'Helvetica Neue',Helvetica,Arial,'Lucida Grande',sans-serif;border:solid lightgray 1px;background-color:transparent;max-width:450px;margin:0px auto\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"    <tbody>" +
"        <tr>" +
"            <td style=\"text-align:center\">" +
"                <p style=\"color:#000;display:block;background-color:#91034a;margin:0px auto;font-size:1.5rem;text-align:center;padding:10px 0px;width:100%;\">Java, PSTU Mail System</p>\n" +

"                <img src=\"https://i.postimg.cc/qqjdGYYD/pstulogo.png\" style=\"width:300px;margin-bottom:10px\">\n" +
"            </td>\n" +
"        </tr>\n" +
"\n" +
"        <tr>\n" +
"\n" +
"            <td style=\"padding:10px;text-align:justify\">\n" +
"                <p><strong>Dear User,</strong><br><br>\n" +
"\n" +
"                    PSTU send this verification mail.So that your account alive with secure. If you think this proces is going to with your permission, please give us your verification code. Otherwise change your password quickly & inform PSTU. \n" +
" \n" +
"                </p>\n" +
"\n" +
"\n" +
"                <p style=\"display:block;text-decoration:none!important;color:#fff;cursor:pointer;background-color:#91034a;margin:0px auto;margin-top:30px;font-size:1.5rem;text-align:center;padding:10px 0px;width:90%;\">"+to+"</p>" +
"                <p style=\"color:#000;display:block;background-color:#299314;margin:0px auto;font-size:1.5rem;text-align:center;padding:5px 0px;width:90%;\">Verification key : "+random+"</p>\n" +
"\n" +
"                <p>\n" +
"                    I am Shishir and interested in doing positive things about every aspect of life. I love projects with challenges. I like works to make an impact in the real world. I always try to work for my world with my community. I learn to extended . Also I am a specialized in Front-End and Back-End web Development.Besides within the web designing and development area every sector I want to deliver my best. I always try to maintain performance with achievements. Actually I am not a successful programmer but I want to be also successful. Because the program is my way & programming is my passion. If you think? I will help you with your project! yes, I am ready to assist you InsyaAllah.Send your query  \n" +
"                    <p></strong><strong>shishir16@cse.pstu.ac.bd</strong></p>\n" +
"                </p>\n" +
"                <p>\n" +
"                    If you are having any issue, kindly reach out to us by replying to this email. \n" +
"                    <a href=\"https://pstu.ac.bd/contact-us/\" target=\"_blank\"\">Patuakhali Science & Technology University</a>\n" +
"                </p>\n" +
"\n" +
"                <p>Thanking you<br>PSTU Atumation Team</p>\n" +
"            </td>\n" +
"\n" +
"        </tr>\n" +
"        <tr>\n" +
"\n" +
"            <td>\n" +
"                <img src=\"https://i.postimg.cc/qvz0d0ph/152-405-copy-removebg-preview.png\" style=\"color:#000;display:block;margin:0px auto;margin-botom:-10px;width:250px; height:300px;\"" +

"            </td>" +
"        </tr>" +
"        <tr>" +
"            <td>" +
"                   <p style=\"color:#000;display:block;background-color:#91034a;margin:2px auto;font-size:20px;text-align:center;padding:10px 0px;width:100%;\">&copy; All right reseve by PSTU</p>\n"+
"\n"+
"            </td>\n" +
"\n" +
"        </tr>\n" +
"    </tbody>\n" +
"</table></div>'", "text/html");
                try{
                  
                    conn cc = new conn();
                    ps= cc.c.prepareStatement("UPDATE users SET token=? Where uid=?");
                    ps.setString(1, String.valueOf(random));
                    ps.setString(2, logeduserid);
                    int count = ps.executeUpdate();
                    
                    
                    Transport.send(message);
                    alert("success","true","Please verify your mail");
                    AdminTabedPane.setSelectedIndex(14);

                    
                    
                }catch(Exception ex){
                  alert("error","true","Something going wrong !");
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void successCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_successCloseActionPerformed
        // TODO add your handling code here:
        alert("success","false","");
        alert("error","false","");
    }//GEN-LAST:event_successCloseActionPerformed

    private void userVerifyIdentityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userVerifyIdentityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userVerifyIdentityActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         String verifyUserEmail = verifyUsername.getText();
         String verifyUserVkey = vkey.getText();
         String identitys = (String)userVerifyIdentity.getSelectedItem();
         identitys = identitys.toLowerCase();
         conn cc = new conn();
         
         
         
         if("student".equals(identitys)){
            alert("error","true","Student under constructions");
         }else{ 
            if(!"".equals(verifyUserEmail)){
                if(verifyUserEmail.equals(adminEmail.getText())){
                  if(!"".equals(verifyUserVkey)){
                        try {
                            String query = "SELECT * FROM users where(uid='"+logeduserid+"' and role='"+identitys+"' and token='"+verifyUserVkey+"')";
                            ResultSet rs = cc.s.executeQuery(query);
                            if(rs.next()){  
                                ps= cc.c.prepareStatement("UPDATE users SET username=?, password=?, email=?, phone=? token=? Where uid=?");
                                ps.setString(1, adminUsername.getText());
                                ps.setString(2, adminPassword.getText());
                                ps.setString(3, adminEmail.getText());
                                ps.setString(4, adminPhone.getText());

                                ps.setString(5, "");
                                ps.setString(6,logeduserid);
                                

                                int count = ps.executeUpdate();
                                if(count > 0){ 
                                   alert("success","true","Your profile is upto date");
                                   verifyUsername.setText("Your Email Address");
                                   AdminTabedPane.setSelectedIndex(1);
                                }else{ 
                                   alert("error","true","Somethis is wrong with your data");
                                }
                            }else{ 
                                alert("error","true","Wrong verification key inserted");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
                        }
                  }else{ 
                    alert("error","true","Insert verification key from email");
                  }
                }else{ 
                    alert("error","true","Write your email correctly");
                }
            }else{ 
              alert("error","true","Insert your selected mail");
            }
         }
         

         
         
         
         
                
                   
      
        
            
    }//GEN-LAST:event_jButton4ActionPerformed

    private void errorCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_errorCloseActionPerformed
        // TODO add your handling code here:
        alert("error","false","");
        alert("success","false","");
    }//GEN-LAST:event_errorCloseActionPerformed

    private void adminPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminPhoneActionPerformed

    private void verifyUsernameComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_verifyUsernameComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_verifyUsernameComponentRemoved

    private void verifyUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_verifyUsernameFocusGained
        // TODO add your handling code here:
        verifyUsername.setText("");
    }//GEN-LAST:event_verifyUsernameFocusGained

    private void verifyUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verifyUsernameActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        AdminTabedPane.setSelectedIndex(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cseSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cseSessionItemStateChanged
        // TODO add your handling code here:

          selectedFaculty = "cse";
//        String sessions = (String)cseSession.getSelectedItem();
          AdminTabedPane.setSelectedIndex(15);
//        try{
////            System.out.println("Form Component Shown");
//            conn cc = new conn();
//            try (ResultSet rs = cc.s.executeQuery("select * from student where session='"+sessions+"'")) {
//                DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
//                if(rs.next()) 
//                    do{
//                        String name = rs.getString(2);
//                        String id = rs.getString(8);
//                        String reg = rs.getString(9);
//                        String email = rs.getString(12);
//                        String phone = rs.getString(6);
//                        String hall = rs.getString(15);
//                        String st[] = {name,id,reg,hall};
//                        table.addRow(st);
//                    }while(rs.next());
//                    jTabbedPane.setSelectedIndex(15);
//                    
//                
//            }
//            cc.s.close();
//            
//               
//               
//        }catch(SQLException e){
//        }
        
       
    }//GEN-LAST:event_cseSessionItemStateChanged

    private void facValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facValActionPerformed

    private void allStudent16ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_allStudent16ComponentShown
        // TODO add your handling code here:
        String sessions = (String)cseSession.getSelectedItem();
        cseSession1.getModel().setSelectedItem(cseSession.getSelectedItem());
//        cseSession1.setSelectedItem("sessions");
        try{
            conn cc = new conn();
            try (ResultSet rs = cc.s.executeQuery("select * from student where session='"+sessions+"' and fac='"+selectedFaculty+"' ")) {
                
                DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
                for( int i = table.getRowCount() - 1; i >= 0; i-- ){
                    table.removeRow(i);
                }

                if(rs.next())
                    do{
                        String name = rs.getString("name");
                        String id = rs.getString("roll");
                        String reg = rs.getString("reg");
                        String email = rs.getString("email");
                        String phone = rs.getString("phone");
                        String hall = rs.getString("hall");
                        String st[] = {name,id,reg,email,phone,hall};
                        table.addRow(st);
                    }while(rs.next());
            }
               cc.s.close();
               
        }catch(SQLException e){
        }
    }//GEN-LAST:event_allStudent16ComponentShown

    private void cseSession1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cseSession1ItemStateChanged
        // TODO add your handling code here:
        String sessions = (String)cseSession1.getSelectedItem();
        if("Select Session".equals(sessions)){
          alert("error","true","Select valid session");
        }else{ 
            try{
                conn cc = new conn();
                try (ResultSet rs = cc.s.executeQuery("select * from student where session='"+sessions+"' and fac='"+selectedFaculty+"'")) {
                    DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
                    for( int i = table.getRowCount() - 1; i >= 0; i-- ){
                        table.removeRow(i);
                    }

                    if(rs.next())
                        do{
                            String name = rs.getString("name");
                            String id = rs.getString("roll");
                            String reg = rs.getString("reg");
                            String email = rs.getString("email");
                            String phone = rs.getString("phone");
                            String hall = rs.getString("hall");
                            String st[] = {name,id,reg,email,phone,hall};
                            table.addRow(st);
                        }while(rs.next());
                }
                   cc.s.close();

            }catch(SQLException e){
            }
        }

    }//GEN-LAST:event_cseSession1ItemStateChanged

    private void cseSession1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cseSession1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cseSession1ActionPerformed

    private void view1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view1ActionPerformed
        // TODO add your handling code here:
        studentsDetailsId = "";
         resetField();
         AdminTabedPane.setSelectedIndex(2);
         
    }//GEN-LAST:event_view1ActionPerformed

    private void view2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view2ActionPerformed
        // TODO add your handling code here:
        
             DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
             int id = allStudentsByFaculty.getSelectedRow();
             if(id>=0){ 
              AdminTabedPane.setSelectedIndex(16);
             }else{ 
               alert("error","true","No field selected");
             }
             
    }//GEN-LAST:event_view2ActionPerformed

    private void jLabel72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel72MouseClicked

    private void jLabel78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel78MouseClicked

    private void jLabel79MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel79MouseClicked

    private void jLabel80MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel80MouseClicked

    private void jLabel81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel81MouseClicked

    private void jLabel82MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel82MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel82MouseClicked

    private void jLabel83MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel83MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel83MouseClicked

    private void jLabel84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel84MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel84MouseClicked

    private void jLabel85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel85MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel85MouseClicked

    private void jLabel86MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel86MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel86MouseClicked

    private void jLabel94MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel94MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel94MouseClicked

    private void jLabel95MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel95MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel95MouseClicked

    private void jLabel96MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel96MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel96MouseClicked

    private void jLabel97MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel97MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel97MouseClicked

    private void jLabel98MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel98MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel98MouseClicked

    private void jLabel99MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel99MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel99MouseClicked

    private void jLabel100MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel100MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel100MouseClicked

    private void studentsDetails17ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_studentsDetails17ComponentShown
        // TODO add your handling code here:
                    AdminDashboards.selectForPrint f = new AdminDashboards.selectForPrint();
                    try {
                        
                        //  recive func meathoad return query result                   
                        rs = f.find(studentsDetailsId);
                        if(rs.next()){
                                byte[] img = rs.getBytes("photo");
                                
                               if(img != null){
                               
                                int imgs = img[0];
                                if(imgs == -1) {
                                    ImageIcon MyImage1 = new ImageIcon(img);
                                    Image img1 = MyImage1.getImage();
                                    Image newImage1 = img1.getScaledInstance(sDetailsProfilePic.getWidth(), sDetailsProfilePic.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon image1 = new ImageIcon(newImage1);
                                    sDetailsProfilePic.setIcon(image1);
                                }
                               }else{
                                   alert("error","true","Image is not inserted yet");
                                    sDetailsProfilePic.setIcon(null);
                               }
                               
                               if(rs.getInt("status")== 0){
                                    sstatusBtn.setText("Disable");
                                    sstatusBtn.setBackground(Color.RED);
                                    sstatusBtn.setForeground(Color.WHITE);
                               }else{ 
                                    sstatusBtn.setText("Active");
                                    sstatusBtn.setBackground(Color.GREEN);
                                    sstatusBtn.setForeground(Color.BLACK);
                               }
                                
                                
                                sname.setText(rs.getString(2));
                                sfname.setText(rs.getString(3));
                                smname.setText(rs.getString(4));
                                saddr.setText(rs.getString(5));
                                sdob.setText(rs.getString(11));
                                sblood.setText(rs.getString(13));
                                sphone.setText(rs.getString(6));
                                snid.setText(rs.getString(7));
                                sid.setText(rs.getString(8));
                                sreg.setText(rs.getString(9));
                                semail.setText(rs.getString(12));
                                ssession.setText(rs.getString(10));
                                sfac.setText(rs.getString(14));
                                shall.setText(rs.getString(15));
                                
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }//GEN-LAST:event_studentsDetails17ComponentShown

    private void allStudentsByFacultyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allStudentsByFacultyMouseClicked
        // TODO add your handling code here:
        
             DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
             int id = allStudentsByFaculty.getSelectedRow();
             studentsDetailsId = table.getValueAt(id,1).toString();
             
    }//GEN-LAST:event_allStudentsByFacultyMouseClicked

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed
        // TODO add your handling code here:
               print ok = new print(studentsDetailsId);
               ok.prints();
    }//GEN-LAST:event_printbtnActionPerformed

    private void view3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view3ActionPerformed
        // TODO add your handling code here:
        
        AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_view3ActionPerformed

    private void jLabel103MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel103MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel103MouseClicked

    private void sstatusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sstatusBtnActionPerformed
        // TODO add your handling code here:
        String text = sstatusBtn.getText();
        if("Active".equals(text)){
            try {
                status("student","Disable");
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{ 
            try {
                status("student","Active");
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_sstatusBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
            DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
             int id = allStudentsByFaculty.getSelectedRow();
             if(id>=0){ 
                studentsDetailsId="";
                studentsDetailsId = table.getValueAt(id,1).toString();
                deleteStudent(studentsDetailsId);
                table.removeRow(id);
                AdminTabedPane.setSelectedIndex(16);
             }else{ 
               alert("error","true","No field selected");
            }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
//        DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
//        int id = allStudentsByFaculty.getSelectedRow();
//        studentsDetailsId="";
//        studentsDetailsId = table.getValueAt(id,1).toString();
        deleteStudent(sid.getText());
        AdminTabedPane.setSelectedIndex(15);
//            table.removeRow(id);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void facValItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facValItemStateChanged
        // TODO add your handling code here:
        
        String fac = (String)facVal.getSelectedItem();
        fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac);
        System.out.println(fac);
    }//GEN-LAST:event_facValItemStateChanged

    private void agriSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_agriSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty="";
        selectedFaculty = "agri";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_agriSessionItemStateChanged

    private void dvmSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dvmSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "dvm";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_dvmSessionItemStateChanged

    private void bamSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bamSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "bam";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_bamSessionItemStateChanged

    private void fishSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fishSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "fish";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_fishSessionItemStateChanged

    private void nfsSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_nfsSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "nfs";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_nfsSessionItemStateChanged

    private void esdmSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_esdmSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "esdm";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_esdmSessionItemStateChanged

    private void llaSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_llaSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "lam";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_llaSessionItemStateChanged

    private void ahSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ahSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty="";
        selectedFaculty = "ah";
          AdminTabedPane.setSelectedIndex(15);
    }//GEN-LAST:event_ahSessionItemStateChanged

    private void studentSidebarLogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSidebarLogoutBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSidebarLogoutBtnActionPerformed

    private void studentSideBarDashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSideBarDashboardBtnActionPerformed
        // TODO add your handling code here:
        
        StudentTabedPane.setSelectedIndex(0);
    }//GEN-LAST:event_studentSideBarDashboardBtnActionPerformed

    private void studentSidebarViewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSidebarViewBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSidebarViewBtnActionPerformed

    private void studentSidebarPaymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSidebarPaymentBtnActionPerformed
        // TODO add your handling code here:
        StudentTabedPane.setSelectedIndex(1);
    }//GEN-LAST:event_studentSidebarPaymentBtnActionPerformed

    private void studentSideBarClassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSideBarClassBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSideBarClassBtnActionPerformed

    private void studentSideBarNotificationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSideBarNotificationBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSideBarNotificationBtnActionPerformed

    private void Hall2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hall2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hall2ActionPerformed

    private void library2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_library2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_library2ActionPerformed

    private void studentSideBarExamBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSideBarExamBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSideBarExamBtnActionPerformed

    private void studentSideBarResultBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSideBarResultBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSideBarResultBtnActionPerformed

    private void organaization2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organaization2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_organaization2ActionPerformed

    private void file1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_file1MouseClicked

    private void jLabel106MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel106MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel106MouseClicked

    private void adminPhone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPhone1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminPhone1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void serchIdField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdField3FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField3FocusGained

    private void serchIdField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdField3FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField3FocusLost

    private void serchIdField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchIdField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField3ActionPerformed

    private void addNewStudent5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStudent5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addNewStudent5ActionPerformed

    private void jComboBox20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox20ActionPerformed

    private void jComboBox21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox21ActionPerformed

    private void jComboBox22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox22ActionPerformed

    private void jComboBox23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox23ActionPerformed

    private void jComboBox24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox24ActionPerformed

    private void jComboBox25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox25ActionPerformed

    private void jComboBox26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox26ActionPerformed

    private void jComboBox27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox27ActionPerformed

    private void jComboBox28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox28ActionPerformed

    private void addStudentBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addStudentBtn3ActionPerformed

    private void nameVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameVal1ActionPerformed

    private void fnameVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameVal1ActionPerformed

    private void mnameVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnameVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnameVal1ActionPerformed

    private void addrVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addrVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addrVal1ActionPerformed

    private void nidVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nidVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nidVal1ActionPerformed

    private void dobVal1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dobVal1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dobVal1MouseClicked

    private void dobVal1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dobVal1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dobVal1PropertyChange

    private void idVal1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idVal1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_idVal1FocusLost

    private void idVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idVal1ActionPerformed

    private void idVal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idVal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idVal1KeyPressed

    private void idVal1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idVal1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_idVal1KeyTyped

    private void regVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regVal1ActionPerformed

    private void facVal1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facVal1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_facVal1ItemStateChanged

    private void facVal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facVal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facVal1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void userVerifyIdentity1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userVerifyIdentity1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userVerifyIdentity1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void verifyUsername1ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_verifyUsername1ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_verifyUsername1ComponentRemoved

    private void verifyUsername1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_verifyUsername1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_verifyUsername1FocusGained

    private void verifyUsername1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyUsername1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verifyUsername1ActionPerformed

    private void view4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_view4ActionPerformed

    private void allStudentsByFaculty1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allStudentsByFaculty1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_allStudentsByFaculty1MouseClicked

    private void cseSession3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cseSession3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cseSession3ItemStateChanged

    private void cseSession3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cseSession3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cseSession3ActionPerformed

    private void view5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_view5ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void allStudent17ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_allStudent17ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_allStudent17ComponentShown

    private void jLabel171MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel171MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel171MouseClicked

    private void jLabel177MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel177MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel177MouseClicked

    private void jLabel178MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel178MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel178MouseClicked

    private void jLabel179MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel179MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel179MouseClicked

    private void jLabel180MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel180MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel180MouseClicked

    private void jLabel181MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel181MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel181MouseClicked

    private void jLabel182MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel182MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel182MouseClicked

    private void jLabel183MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel183MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel183MouseClicked

    private void jLabel184MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel184MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel184MouseClicked

    private void jLabel185MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel185MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel185MouseClicked

    private void jLabel192MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel192MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel192MouseClicked

    private void jLabel193MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel193MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel193MouseClicked

    private void jLabel194MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel194MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel194MouseClicked

    private void jLabel195MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel195MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel195MouseClicked

    private void jLabel196MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel196MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel196MouseClicked

    private void jLabel197MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel197MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel197MouseClicked

    private void jLabel198MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel198MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel198MouseClicked

    private void printbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_printbtn1ActionPerformed

    private void view6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_view6ActionPerformed

    private void jLabel200MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel200MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel200MouseClicked

    private void sstatusBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sstatusBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sstatusBtn1ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void studentsDetails18ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_studentsDetails18ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_studentsDetails18ComponentShown

    private void studentSideBarDashboardBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentSideBarDashboardBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSideBarDashboardBtnMouseClicked

    private void profileViewPanel3ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_profileViewPanel3ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_profileViewPanel3ComponentShown

    private void serchIdFieldComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_serchIdFieldComponentShown
        // TODO add your handling code here:
         serchIdField.setBorder(BorderFactory.createCompoundBorder(regVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
    }//GEN-LAST:event_serchIdFieldComponentShown

    private void studentPanel03ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_studentPanel03ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_studentPanel03ComponentShown

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
    private javax.swing.JPanel AdminSideBarPanel;
    private javax.swing.JTabbedPane AdminTabedPane;
    private javax.swing.JPanel AdministrationPanel10;
    private javax.swing.JPanel AdministrationPanel11;
    private javax.swing.JButton Hall1;
    private javax.swing.JButton Hall2;
    private javax.swing.JPanel LibraryPanel07;
    private javax.swing.JPanel LibraryPanel8;
    private javax.swing.JPanel StudentSideBarPanel;
    private javax.swing.JTabbedPane StudentTabedPane;
    private javax.swing.JLabel UpdateProfilePic;
    private javax.swing.JLabel UpdateProfilePic1;
    private javax.swing.JButton addNewStudent2;
    private javax.swing.JButton addNewStudent3;
    private javax.swing.JButton addNewStudent5;
    private javax.swing.JPanel addStudent14;
    private javax.swing.JPanel addStudent15;
    private javax.swing.JButton addStudentBtn;
    private javax.swing.JButton addStudentBtn2;
    private javax.swing.JButton addStudentBtn3;
    private javax.swing.JTextField addrVal;
    private javax.swing.JTextField addrVal1;
    private javax.swing.JLabel adminBlood;
    private javax.swing.JLabel adminBlood1;
    private javax.swing.JLabel adminBlood2;
    private javax.swing.JLabel adminBlood3;
    private javax.swing.JLabel adminBlood4;
    private javax.swing.JLabel adminBlood5;
    private javax.swing.JLabel adminDob;
    private javax.swing.JLabel adminDob1;
    private javax.swing.JLabel adminDob10;
    private javax.swing.JLabel adminDob11;
    private javax.swing.JLabel adminDob12;
    private javax.swing.JLabel adminDob13;
    private javax.swing.JLabel adminDob14;
    private javax.swing.JLabel adminDob15;
    private javax.swing.JLabel adminDob16;
    private javax.swing.JLabel adminDob17;
    private javax.swing.JLabel adminDob18;
    private javax.swing.JLabel adminDob19;
    private javax.swing.JLabel adminDob2;
    private javax.swing.JLabel adminDob20;
    private javax.swing.JLabel adminDob21;
    private javax.swing.JLabel adminDob22;
    private javax.swing.JLabel adminDob23;
    private javax.swing.JLabel adminDob24;
    private javax.swing.JLabel adminDob25;
    private javax.swing.JLabel adminDob3;
    private javax.swing.JLabel adminDob4;
    private javax.swing.JLabel adminDob5;
    private javax.swing.JLabel adminDob6;
    private javax.swing.JLabel adminDob7;
    private javax.swing.JLabel adminDob8;
    private javax.swing.JLabel adminDob9;
    private javax.swing.JTextField adminEmail;
    private javax.swing.JTextField adminEmail1;
    private javax.swing.JLabel adminName;
    private javax.swing.JLabel adminName1;
    private javax.swing.JLabel adminNid;
    private javax.swing.JLabel adminNid1;
    private javax.swing.JLabel adminNid2;
    private javax.swing.JLabel adminNid3;
    private javax.swing.JLabel adminNid4;
    private javax.swing.JLabel adminNid5;
    private javax.swing.JTextField adminPassword;
    private javax.swing.JTextField adminPassword1;
    private javax.swing.JTextField adminPhone;
    private javax.swing.JTextField adminPhone1;
    private javax.swing.JTextField adminUsername;
    private javax.swing.JTextField adminUsername1;
    private javax.swing.JButton administrator1;
    private javax.swing.JComboBox<String> agriSession;
    private javax.swing.JComboBox<String> ahSession;
    private javax.swing.JPanel alertPanel;
    private javax.swing.JLabel alertSuccessText;
    private javax.swing.JLabel alertSuccessText1;
    private javax.swing.JPanel allStudent16;
    private javax.swing.JPanel allStudent17;
    private javax.swing.JTable allStudentsByFaculty;
    private javax.swing.JTable allStudentsByFaculty1;
    private javax.swing.JComboBox<String> bamSession;
    private javax.swing.JButton bank1;
    private javax.swing.JPanel bankPanel08;
    private javax.swing.JPanel bankPanel9;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel bg1;
    private javax.swing.JComboBox<String> bloodVal;
    private javax.swing.JComboBox<String> bloodVal1;
    private javax.swing.JLabel bottomrightborder2;
    private javax.swing.JLabel bottomrightborder3;
    private javax.swing.JLabel bottomrightborder4;
    private javax.swing.JLabel bottomrightborder5;
    private javax.swing.JLabel bottomrightborder6;
    private javax.swing.JLabel bottomrightborder7;
    private javax.swing.JLabel bottomrightborder8;
    private javax.swing.JLabel bottomrightborder9;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JComboBox<String> cseSession;
    private javax.swing.JComboBox<String> cseSession1;
    private javax.swing.JComboBox<String> cseSession3;
    private javax.swing.JButton dashboard;
    private javax.swing.JPanel dashboardPanel01;
    private com.toedter.calendar.JDateChooser dobVal;
    private com.toedter.calendar.JDateChooser dobVal1;
    private javax.swing.JComboBox<String> dvmSession;
    private javax.swing.JLabel error;
    private javax.swing.JButton errorClose;
    private javax.swing.JLabel errorLogo;
    private javax.swing.JLabel errorText;
    private javax.swing.JComboBox<String> esdmSession;
    private javax.swing.JComboBox<String> facVal;
    private javax.swing.JComboBox<String> facVal1;
    private javax.swing.JButton faculty1;
    private javax.swing.JPanel facultyPanel05;
    private javax.swing.JPanel facultyPanel6;
    private javax.swing.JLabel file;
    private javax.swing.JLabel file1;
    private javax.swing.JComboBox<String> fishSession;
    private javax.swing.JTextField fnameVal;
    private javax.swing.JTextField fnameVal1;
    private javax.swing.JPanel hallPanel06;
    private javax.swing.JPanel hallPanel7;
    private javax.swing.JComboBox<String> hallVal;
    private javax.swing.JComboBox<String> hallVal1;
    private javax.swing.JButton health1;
    private javax.swing.JPanel healthPanel09;
    private javax.swing.JPanel healthPanel10;
    private javax.swing.JTextField idVal;
    private javax.swing.JTextField idVal1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JComboBox<String> jComboBox22;
    private javax.swing.JComboBox<String> jComboBox23;
    private javax.swing.JComboBox<String> jComboBox24;
    private javax.swing.JComboBox<String> jComboBox25;
    private javax.swing.JComboBox<String> jComboBox26;
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JComboBox<String> jComboBox28;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton library1;
    private javax.swing.JButton library2;
    private javax.swing.JComboBox<String> llaSession;
    private javax.swing.JButton logout;
    private javax.swing.JTextField mnameVal;
    private javax.swing.JTextField mnameVal1;
    private javax.swing.JTextField nameVal;
    private javax.swing.JTextField nameVal1;
    private javax.swing.JComboBox<String> nfsSession;
    private javax.swing.JTextField nidVal;
    private javax.swing.JTextField nidVal1;
    private javax.swing.JButton organaization1;
    private javax.swing.JButton organaization2;
    private javax.swing.JPanel organaizationPanel11;
    private javax.swing.JPanel organaizationPanel12;
    private javax.swing.JButton others1;
    private javax.swing.JPanel othersPanel13;
    private javax.swing.JPanel othersPanel14;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton printbtn1;
    private javax.swing.JPanel profileViewPanel02;
    private javax.swing.JPanel profileViewPanel3;
    private javax.swing.JLabel profilepic;
    private javax.swing.JTextField regVal;
    private javax.swing.JTextField regVal1;
    private javax.swing.JLabel sDetailsProfilePic;
    private javax.swing.JLabel sDetailsProfilePic1;
    private javax.swing.JLabel saddr;
    private javax.swing.JLabel saddr1;
    private javax.swing.JLabel sblood;
    private javax.swing.JLabel sblood1;
    private javax.swing.JLabel sdob;
    private javax.swing.JLabel sdob1;
    private javax.swing.JLabel semail;
    private javax.swing.JLabel semail1;
    private javax.swing.JTextField serchIdField;
    private javax.swing.JTextField serchIdField1;
    private javax.swing.JTextField serchIdField3;
    private javax.swing.JComboBox<String> sessionVal;
    private javax.swing.JComboBox<String> sessionVal1;
    private javax.swing.JLabel sfac;
    private javax.swing.JLabel sfac1;
    private javax.swing.JLabel sfname;
    private javax.swing.JLabel sfname1;
    private javax.swing.JLabel shall;
    private javax.swing.JLabel shall1;
    private javax.swing.JLabel sid;
    private javax.swing.JLabel sid1;
    private javax.swing.JLabel smname;
    private javax.swing.JLabel smname1;
    private javax.swing.JLabel sname;
    private javax.swing.JLabel sname1;
    private javax.swing.JLabel snid;
    private javax.swing.JLabel snid1;
    private javax.swing.JLabel sphone;
    private javax.swing.JLabel sphone1;
    private javax.swing.JLabel sreg;
    private javax.swing.JLabel sreg1;
    private javax.swing.JLabel ssem;
    private javax.swing.JLabel ssem1;
    private javax.swing.JLabel ssession;
    private javax.swing.JLabel ssession1;
    private javax.swing.JButton sstatusBtn;
    private javax.swing.JButton sstatusBtn1;
    private javax.swing.JButton student1;
    private javax.swing.JPanel studentDashboard;
    private javax.swing.JPanel studentPanel03;
    private javax.swing.JPanel studentPanel4;
    private javax.swing.JButton studentSideBarClassBtn;
    private javax.swing.JButton studentSideBarDashboardBtn;
    private javax.swing.JButton studentSideBarExamBtn;
    private javax.swing.JButton studentSideBarNotificationBtn;
    private javax.swing.JButton studentSideBarResultBtn;
    private javax.swing.JButton studentSidebarLogoutBtn;
    private javax.swing.JButton studentSidebarPaymentBtn;
    private javax.swing.JLabel studentSidebarProfilepic;
    private javax.swing.JButton studentSidebarViewBtn;
    private javax.swing.JPanel studentsDetails17;
    private javax.swing.JPanel studentsDetails18;
    private javax.swing.JLabel success;
    private javax.swing.JButton successClose;
    private javax.swing.JLabel successLogo;
    private javax.swing.JLabel successText;
    private javax.swing.JButton teacher1;
    private javax.swing.JPanel teacherPanel04;
    private javax.swing.JPanel teacherPanel5;
    private javax.swing.JButton transport1;
    private javax.swing.JPanel transportPanel12;
    private javax.swing.JPanel transportPanel13;
    private javax.swing.JComboBox<String> userVerifyIdentity;
    private javax.swing.JComboBox<String> userVerifyIdentity1;
    private javax.swing.JTextField verifyUsername;
    private javax.swing.JTextField verifyUsername1;
    private javax.swing.JPanel verifyaccount15;
    private javax.swing.JPanel verifyaccount16;
    private javax.swing.JButton view;
    private javax.swing.JButton view1;
    private javax.swing.JButton view2;
    private javax.swing.JButton view3;
    private javax.swing.JButton view4;
    private javax.swing.JButton view5;
    private javax.swing.JButton view6;
    private javax.swing.JTextField vkey;
    private javax.swing.JTextField vkey1;
    // End of variables declaration//GEN-END:variables
}
