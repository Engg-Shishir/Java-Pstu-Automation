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
    boolean dataFoundOrNot = false;
    boolean click = false;
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
    

    
    public void hide(String identity){
       StudentSideBarPanel.setVisible(false);
       AdminSideBarPanel.setVisible(false);
       
        error.setVisible(false);
        errorLogo.setVisible(false);
        errorText.setText("");
        errorClose.setVisible(false);
        
        success.setVisible(false);
        successLogo.setVisible(false);
        successText.setText("");
        successClose.setVisible(false);
        
        openSideBar(identity);
    }
    

     public void openSideBar(String identity){
            if("student".equals(identity)){
               StudentSideBarPanel.setVisible(true);
               ProjectTab.setSelectedIndex(17);
            }else if("admin".equals(identity)){
               AdminSideBarPanel.setVisible(true);
              ProjectTab.setSelectedIndex(2);
            }else if("teacher".equals(identity)){
                      
            }else{
            
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
            
            conn cc = new conn();
            String query = "";
            
            
            if("student".equals(identy)){ 
                query = "SELECT * FROM student where(email='"+usernames+"')"
                + "or(roll='"+usernames+"')"
                + "or(username='"+usernames+"')"
                + "or(phone='"+usernames+"')";
            }else{ 
                query = "SELECT * FROM users where(email='"+usernames+"' and role='"+identy+"') "
                + "or(uid='"+usernames+"' and role='"+identy+"')"
                + "or(username='"+usernames+"' and role='"+identy+"')";
            }
           
            
            PreparedStatement ps=cc.c.prepareStatement(query);
            rs  = ps.executeQuery(query); 
            
            return rs;

        }
    }   
    public ImageIcon ResizeImage(String imafePaths){
        ImageIcon MyImage = new ImageIcon(imafePaths);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(adminUpdateProfilePic.getWidth(), adminUpdateProfilePic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
    public class test {
            public ResultSet finds(String usernames, String identy) throws SQLException, ClassNotFoundException, FileNotFoundException{
                     String query = "";
                     conn cc = new conn();
                    
                    
                    
                    File f=new File(imagePath);
                    fs=new FileInputStream(f);
                    
                    if("student".equals(identy)){
                      query = "UPDATE student SET photo=? Where roll=?";
                    }else{ 
                      query = "UPDATE users SET photo=? Where uid=?";
                    }
                    ps= cc.c.prepareStatement(query);
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
    
    
    public boolean checkWordIsFoundOrnOT(String fac){ 
        String words="Session";
        // To break the sentence in words
        String []eachWords = fac.split(" ");

        // To temporarily store each individual word
        for ( String temp : eachWords)
        {
            // Comparing the current word
            // with the word to be searched
            if (temp.compareTo(words) == 0)
            {
                return false;
            }
        }
        return true;
    }
    
    
    
    
    public boolean showFacultyStudent(String fac,String ses){
                try{
            conn cc = new conn();
            try{
                ResultSet rs = cc.s.executeQuery("select * from student where session='"+ses+"' and fac='"+fac+"' ");
                DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
                for( int i = table.getRowCount() - 1; i >= 0; i-- ){
                    table.removeRow(i);
                }

                if(rs.next()){
                    dataFoundOrNot=true;
                    
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
                    
                    
                    if(dataFoundOrNot){
                        cseSession1.getModel().setSelectedItem(cseSession.getSelectedItem());
                        ProjectTab.setSelectedIndex(14);
                    }
                }else{ 
                   alert("error","true","No data found");
                }
            }catch(SQLException e){
            }
               cc.s.close();
               
        }catch(SQLException e){
        }
        return false;
    } 
    
    public void profileView(){
        
        AdminDashboards.func object = new AdminDashboards.func();
        try { 
            //  recive func meathoad return query result                   
            rs = object.find(getadminusername,identity);
                System.out.println("Paici rs");
                         
             if(rs.next()){
                if("student".equals(identity)){
                    logeduserid = rs.getString("roll");
                    byte[] img = rs.getBytes("photo");
                    studentName.setText(rs.getString("name"));
                    studentNid.setText(rs.getString("nid"));
                    studentBlood.setText(rs.getString("blood"));
                    studentDob.setText(rs.getString("dob"));
                    studentUsername.setText(rs.getString("username"));
                    studentPhone.setText(rs.getString("phone"));
                    studentPassword.setText(rs.getString("password"));
                    studentPassword.setEchoChar('*');
                    studentEmail.setText(rs.getString("email"));
                    
                    
                    if(img != null){ 
                        ImageIcon MyImage1 = new ImageIcon(img);
                        Image img1 = MyImage1.getImage();
                        Image newImage1 = img1.getScaledInstance(adminProfilepic.getWidth(), adminProfilepic.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon image1 = new ImageIcon(newImage1);
                        studentSidebarProfilepic.setIcon(image1);
                        studentUpdateProfilePic.setIcon(image1);
                    } 
                }else{
                    logeduserid = rs.getString("uid");
                    byte[] img = rs.getBytes("photo");
                    
                    if(img != null){ 
                        ImageIcon MyImage1 = new ImageIcon(img);
                        Image img1 = MyImage1.getImage();
                        Image newImage1 = img1.getScaledInstance(adminProfilepic.getWidth(), adminProfilepic.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon image1 = new ImageIcon(newImage1);
                        adminProfilepic.setIcon(image1);
                        adminUpdateProfilePic.setIcon(image1);
                    }
                    
                    
                        adminName.setText(rs.getString(2));
                        adminNid.setText(rs.getString(8));
                        adminBlood.setText(rs.getString(11));
                        adminDob.setText(rs.getString(10));
                        adminUsername.setText(rs.getString(3));
                        adminPhone.setText(rs.getString(9));
                        adminPassword.setText(rs.getString("password"));
                        adminPassword.setEchoChar('*');
                        adminEmail.setText(rs.getString(7));
                
                }
             }

//            if(rs.next()){
//                
//                if("student".equals(identity)){                
//                    logeduserid = rs.getString("roll");
//                    byte[] img = rs.getBytes("photo");
//                    
//                    if(img != null){ 
//                        ImageIcon MyImage1 = new ImageIcon(img);
//                        Image img1 = MyImage1.getImage();
//                        Image newImage1 = img1.getScaledInstance(adminProfilepic.getWidth(), adminProfilepic.getHeight(), Image.SCALE_SMOOTH);
//                        ImageIcon image1 = new ImageIcon(newImage1);
//                        studentSidebarProfilepic.setIcon(image1);
//                        studentUpdateProfilePic.setIcon(image1);
//                    }
//                }else{                
//                    logeduserid = rs.getString("uid");
//                    byte[] img = rs.getBytes("photo");
//                    
//                    if(img != null){ 
//                        ImageIcon MyImage1 = new ImageIcon(img);
//                        Image img1 = MyImage1.getImage();
//                        Image newImage1 = img1.getScaledInstance(adminProfilepic.getWidth(), adminProfilepic.getHeight(), Image.SCALE_SMOOTH);
//                        ImageIcon image1 = new ImageIcon(newImage1);
//                        adminProfilepic.setIcon(image1);
//                        adminUpdateProfilePic.setIcon(image1);
//                    }
//                    
//                    
//                        adminName.setText(rs.getString(2));
//                        adminNid.setText(rs.getString(8));
//                        adminBlood.setText(rs.getString(11));
//                        adminDob.setText(rs.getString(10));
//                        adminUsername.setText(rs.getString(3));
//                        adminPhone.setText(rs.getString(9));
//                        adminPassword.setText(rs.getString(4));
//                        adminEmail.setText(rs.getString(7));
//                }
//                
//                
//
//                
//                
//            }
        } catch (SQLException ex) {
        }
    
    
    }
    
    
    
        
    public void PasswordShowAndHide(boolean get){
        if(get==false){
            studentPassword.setEchoChar((char)0);
            adminPassword.setEchoChar((char)0);
            click=true;
        }else{
            studentPassword.setEchoChar('*');
            adminPassword.setEchoChar('*');
            click=false;
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

        AdminSideBarPanel = new javax.swing.JPanel();
        adminProfilepic = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        adminDashboardBtn = new javax.swing.JButton();
        adminProfileBtn = new javax.swing.JButton();
        adminStudentBtn = new javax.swing.JButton();
        adminTeacherBtn = new javax.swing.JButton();
        adminFacultyBtn = new javax.swing.JButton();
        adminHallBtn = new javax.swing.JButton();
        adminLibraryBtn = new javax.swing.JButton();
        adminTransectionBtn = new javax.swing.JButton();
        adminHealthCareBtn = new javax.swing.JButton();
        adminAdministrationBtn = new javax.swing.JButton();
        adminOrganaizationBtn = new javax.swing.JButton();
        adminTranspoartBtn = new javax.swing.JButton();
        bg = new javax.swing.JLabel();
        StudentSideBarPanel = new javax.swing.JPanel();
        studentSidebarProfilepic = new javax.swing.JLabel();
        studentSidebarLogoutBtn = new javax.swing.JButton();
        studentProfileViewBtn = new javax.swing.JButton();
        studentDashboardBtn = new javax.swing.JButton();
        studentPaymentBtn = new javax.swing.JButton();
        studentFacultyBtn = new javax.swing.JButton();
        studentExamBtn = new javax.swing.JButton();
        studentResultBtn = new javax.swing.JButton();
        bg1 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        AlertPanel = new javax.swing.JPanel();
        errorClose = new javax.swing.JButton();
        errorLogo = new javax.swing.JLabel();
        errorText = new javax.swing.JLabel();
        error = new javax.swing.JLabel();
        successClose = new javax.swing.JButton();
        successLogo = new javax.swing.JLabel();
        successText = new javax.swing.JLabel();
        success = new javax.swing.JLabel();
        ProjectTab = new javax.swing.JTabbedPane();
        adminDashboard_01 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        adminProfile_02 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        adminUpdateProfilePic = new javax.swing.JLabel();
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
        jButton3 = new javax.swing.JButton();
        adminDob = new javax.swing.JLabel();
        adminNid = new javax.swing.JLabel();
        bottomrightborder5 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel61 = new javax.swing.JLabel();
        adminPassword = new javax.swing.JPasswordField();
        adminPassEye = new javax.swing.JButton();
        adminStudent_03 = new javax.swing.JPanel();
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
        adminTeacher_04 = new javax.swing.JPanel();
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
        adminFaculty_05 = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        adminHall_06 = new javax.swing.JPanel();
        jLabel122 = new javax.swing.JLabel();
        adminLibrary_07 = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        adminTransection_08 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        adminHealthCare_09 = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        adminAdministration_10 = new javax.swing.JPanel();
        jLabel126 = new javax.swing.JLabel();
        adminOrganaization_11 = new javax.swing.JPanel();
        jLabel127 = new javax.swing.JLabel();
        adminTranspoart_12 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        adminStudentAdd_13 = new javax.swing.JPanel();
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
        adminAccountVerify_14 = new javax.swing.JPanel();
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
        adminFacultyStudent_15 = new javax.swing.JPanel();
        view1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        allStudentsByFaculty = new javax.swing.JTable();
        cseSession1 = new javax.swing.JComboBox<>();
        view2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        adminStudentDetails_16 = new javax.swing.JPanel();
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
        studentProfileView_17 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        studentUpdateProfilePic = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        studentName = new javax.swing.JLabel();
        file1 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        studentEmail = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        studentBlood = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        studentUsername = new javax.swing.JTextField();
        studentPhone = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        studentDob = new javax.swing.JLabel();
        studentNid = new javax.swing.JLabel();
        bottomrightborder6 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel111 = new javax.swing.JLabel();
        studentPassword = new javax.swing.JPasswordField();
        studentPassEye = new javax.swing.JButton();
        studentDashboard_18 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        studentPayment_19 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        studentFaculty_20 = new javax.swing.JPanel();
        jLabel119 = new javax.swing.JLabel();
        studentExam_21 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        studentResult_22 = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AdminSideBarPanel.setPreferredSize(new java.awt.Dimension(350, 855));
        AdminSideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        AdminSideBarPanel.add(adminProfilepic, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 200, 180));

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

        adminDashboardBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminDashboardBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminDashboardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/home.png"))); // NOI18N
        adminDashboardBtn.setText("Dashboard");
        adminDashboardBtn.setBorder(null);
        adminDashboardBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminDashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDashboardBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminDashboardBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, 40));

        adminProfileBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminProfileBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminProfileBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminProfileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        adminProfileBtn.setText("View");
        adminProfileBtn.setBorder(null);
        adminProfileBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminProfileBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminProfileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

        adminStudentBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminStudentBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminStudentBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminStudentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/student.png"))); // NOI18N
        adminStudentBtn.setText("Student");
        adminStudentBtn.setBorder(null);
        adminStudentBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminStudentBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminStudentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 40));

        adminTeacherBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminTeacherBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTeacherBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminTeacherBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/teacher.png"))); // NOI18N
        adminTeacherBtn.setText("Teacher");
        adminTeacherBtn.setBorder(null);
        adminTeacherBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTeacherBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminTeacherBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 40));

        adminFacultyBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminFacultyBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminFacultyBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminFacultyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/faculty.png"))); // NOI18N
        adminFacultyBtn.setText("Faculty");
        adminFacultyBtn.setBorder(null);
        adminFacultyBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminFacultyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminFacultyBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminFacultyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 140, 40));

        adminHallBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminHallBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminHallBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminHallBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/hall.png"))); // NOI18N
        adminHallBtn.setText(" Hall");
        adminHallBtn.setBorder(null);
        adminHallBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminHallBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminHallBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminHallBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 140, 40));

        adminLibraryBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminLibraryBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminLibraryBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminLibraryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/library.png"))); // NOI18N
        adminLibraryBtn.setText("Library");
        adminLibraryBtn.setBorder(null);
        adminLibraryBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminLibraryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminLibraryBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminLibraryBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 140, 40));

        adminTransectionBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminTransectionBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTransectionBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminTransectionBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        adminTransectionBtn.setText("Transection");
        adminTransectionBtn.setBorder(null);
        adminTransectionBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTransectionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTransectionBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminTransectionBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, 140, 40));

        adminHealthCareBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminHealthCareBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminHealthCareBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminHealthCareBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/doctor.png"))); // NOI18N
        adminHealthCareBtn.setText("Health care");
        adminHealthCareBtn.setBorder(null);
        adminHealthCareBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminHealthCareBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminHealthCareBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminHealthCareBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 140, 40));

        adminAdministrationBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminAdministrationBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminAdministrationBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminAdministrationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bulb.png"))); // NOI18N
        adminAdministrationBtn.setText("Administration");
        adminAdministrationBtn.setBorder(null);
        adminAdministrationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminAdministrationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminAdministrationBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminAdministrationBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 510, 140, 40));

        adminOrganaizationBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminOrganaizationBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminOrganaizationBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminOrganaizationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/organaization.png"))); // NOI18N
        adminOrganaizationBtn.setText("Oraniztion");
        adminOrganaizationBtn.setBorder(null);
        adminOrganaizationBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminOrganaizationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminOrganaizationBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminOrganaizationBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 140, 40));

        adminTranspoartBtn.setBackground(new java.awt.Color(14, 0, 82));
        adminTranspoartBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTranspoartBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminTranspoartBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bus.png"))); // NOI18N
        adminTranspoartBtn.setText("Transport");
        adminTranspoartBtn.setBorder(null);
        adminTranspoartBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTranspoartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTranspoartBtnActionPerformed(evt);
            }
        });
        AdminSideBarPanel.add(adminTranspoartBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 560, 140, 40));

        bg.setBackground(new java.awt.Color(0, 5, 42));
        bg.setOpaque(true);
        AdminSideBarPanel.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 855));

        getContentPane().add(AdminSideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 860));

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

        studentProfileViewBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentProfileViewBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentProfileViewBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentProfileViewBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        studentProfileViewBtn.setText("View");
        studentProfileViewBtn.setBorder(null);
        studentProfileViewBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileViewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentProfileViewBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentProfileViewBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

        studentDashboardBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentDashboardBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentDashboardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/home.png"))); // NOI18N
        studentDashboardBtn.setText("Dashboard");
        studentDashboardBtn.setBorder(null);
        studentDashboardBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentDashboardBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentDashboardBtnMouseClicked(evt);
            }
        });
        studentDashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentDashboardBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentDashboardBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, 40));

        studentPaymentBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentPaymentBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentPaymentBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentPaymentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        studentPaymentBtn.setText("Payment");
        studentPaymentBtn.setBorder(null);
        studentPaymentBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentPaymentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPaymentBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentPaymentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 40));

        studentFacultyBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentFacultyBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentFacultyBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentFacultyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/faculty.png"))); // NOI18N
        studentFacultyBtn.setText("Faculty");
        studentFacultyBtn.setBorder(null);
        studentFacultyBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentFacultyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentFacultyBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentFacultyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 40));

        studentExamBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentExamBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentExamBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentExamBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/exam.png"))); // NOI18N
        studentExamBtn.setText("Exam");
        studentExamBtn.setBorder(null);
        studentExamBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentExamBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentExamBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentExamBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 140, 40));

        studentResultBtn.setBackground(new java.awt.Color(14, 0, 82));
        studentResultBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentResultBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentResultBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/result.png"))); // NOI18N
        studentResultBtn.setText("Result");
        studentResultBtn.setBorder(null);
        studentResultBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentResultBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentResultBtnActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentResultBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 140, 40));

        bg1.setBackground(new java.awt.Color(0, 5, 42));
        bg1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        bg1.setOpaque(true);
        StudentSideBarPanel.add(bg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 855));

        getContentPane().add(StudentSideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 860));

        contentPanel.setBackground(new java.awt.Color(159, 0, 87));
        contentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AlertPanel.setBackground(new java.awt.Color(0, 5, 42));
        AlertPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        errorClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        errorClose.setBorder(null);
        errorClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        errorClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                errorCloseActionPerformed(evt);
            }
        });
        AlertPanel.add(errorClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 45, 40, 40));

        errorLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/warning.png"))); // NOI18N
        errorLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AlertPanel.add(errorLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, -1, 40));

        errorText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        errorText.setForeground(new java.awt.Color(255, 255, 255));
        AlertPanel.add(errorText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 370, 50));

        error.setBackground(new java.awt.Color(102, 0, 102));
        error.setOpaque(true);
        AlertPanel.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 35, 490, 60));

        successClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        successClose.setBorder(null);
        successClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        successClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                successCloseActionPerformed(evt);
            }
        });
        AlertPanel.add(successClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 45, 40, 40));

        successLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/successlogos.png"))); // NOI18N
        successLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AlertPanel.add(successLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, -1, 40));

        successText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        successText.setForeground(new java.awt.Color(255, 255, 255));
        AlertPanel.add(successText, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 35, 370, 60));

        success.setBackground(new java.awt.Color(0, 102, 51));
        success.setOpaque(true);
        AlertPanel.add(success, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 35, 490, 60));

        contentPanel.add(AlertPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 510, 100));

        adminDashboard_01.setBackground(new java.awt.Color(0, 5, 42));
        adminDashboard_01.setPreferredSize(new java.awt.Dimension(1200, 855));
        adminDashboard_01.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel120.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(255, 255, 255));
        jLabel120.setText("Admin Dashboard");
        adminDashboard_01.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 560, 87));

        ProjectTab.addTab("tab1", adminDashboard_01);

        adminProfile_02.setBackground(new java.awt.Color(0, 5, 42));
        adminProfile_02.setPreferredSize(new java.awt.Dimension(1110, 890));
        adminProfile_02.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminProfile_02ComponentShown(evt);
            }
        });
        adminProfile_02.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Change Profile :");
        adminProfile_02.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 160, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/pastu-admin.png"))); // NOI18N
        jLabel16.setText("jLabel16");
        adminProfile_02.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 400, 70));

        adminUpdateProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        adminProfile_02.add(adminUpdateProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 200, 180));

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel56.setText("jLabel56");
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 30, 40));

        adminName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        adminName.setForeground(new java.awt.Color(153, 0, 102));
        adminName.setText("Name");
        adminProfile_02.add(adminName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 410, 40));

        file.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/folder.png"))); // NOI18N
        file.setText("jLabel56");
        file.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileMouseClicked(evt);
            }
        });
        adminProfile_02.add(file, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, 40, 30));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel65.setText("jLabel56");
        jLabel65.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, 30, 30));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/nid.png"))); // NOI18N
        jLabel66.setText("jLabel56");
        jLabel66.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });
        adminProfile_02.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 30, 30));

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/dob.png"))); // NOI18N
        jLabel67.setText("jLabel56");
        jLabel67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 30, 30));

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/uname.png"))); // NOI18N
        jLabel68.setText("jLabel56");
        jLabel68.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 30, 30));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/phone.png"))); // NOI18N
        jLabel69.setText("jLabel56");
        jLabel69.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 30, 30));

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel70.setText("jLabel56");
        jLabel70.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 30, 30));

        adminEmail.setBackground(new java.awt.Color(0, 5, 42));
        adminEmail.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminEmail.setForeground(new java.awt.Color(255, 255, 255));
        adminEmail.setText("gfhgj");
        adminEmail.setBorder(null);
        adminProfile_02.add(adminEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 290, 40));
        adminProfile_02.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 300, 10));

        adminBlood.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood.setForeground(new java.awt.Color(255, 204, 0));
        adminBlood.setText("Name");
        adminProfile_02.add(adminBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 300, -1));
        adminProfile_02.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 300, 10));
        adminProfile_02.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 300, 10));

        adminUsername.setBackground(new java.awt.Color(0, 5, 42));
        adminUsername.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminUsername.setForeground(new java.awt.Color(255, 255, 255));
        adminUsername.setText("ghghghgh");
        adminUsername.setBorder(null);
        adminProfile_02.add(adminUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 300, 40));

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
        adminProfile_02.add(adminPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 290, 40));

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
        adminProfile_02.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 110, 50));

        adminDob.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob.setForeground(new java.awt.Color(255, 204, 0));
        adminDob.setText("Name");
        adminProfile_02.add(adminDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 310, -1));

        adminNid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid.setForeground(new java.awt.Color(255, 204, 0));
        adminNid.setText("Nid");
        adminProfile_02.add(adminNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 260, 30));

        bottomrightborder5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        adminProfile_02.add(bottomrightborder5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 330, 340));
        adminProfile_02.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 300, 10));

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/blood.png"))); // NOI18N
        jLabel61.setText("jLabel56");
        jLabel61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 30));

        adminPassword.setBackground(new java.awt.Color(0, 5, 42));
        adminPassword.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        adminPassword.setForeground(new java.awt.Color(255, 255, 255));
        adminPassword.setBorder(null);
        adminProfile_02.add(adminPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 510, 210, 40));

        adminPassEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/verify.png"))); // NOI18N
        adminPassEye.setBorder(null);
        adminPassEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminPassEye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPassEyeActionPerformed(evt);
            }
        });
        adminProfile_02.add(adminPassEye, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 510, 40, 30));

        ProjectTab.addTab("tab2", adminProfile_02);

        adminStudent_03.setBackground(new java.awt.Color(0, 5, 42));
        adminStudent_03.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminStudent_03ComponentShown(evt);
            }
        });
        adminStudent_03.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/student.png"))); // NOI18N
        adminStudent_03.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, 410, 60));

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
        adminStudent_03.add(addStudentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 170, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        adminStudent_03.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 390, 270));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        adminStudent_03.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 200, 200));

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
        adminStudent_03.add(serchIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

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
        adminStudent_03.add(addNewStudent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

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
        adminStudent_03.add(cseSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

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
        adminStudent_03.add(agriSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

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
        adminStudent_03.add(dvmSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

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
        adminStudent_03.add(bamSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

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
        adminStudent_03.add(fishSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

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
        adminStudent_03.add(nfsSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

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
        adminStudent_03.add(esdmSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

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
        adminStudent_03.add(llaSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

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
        adminStudent_03.add(ahSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        ProjectTab.addTab("tab3", adminStudent_03);

        adminTeacher_04.setBackground(new java.awt.Color(0, 5, 42));
        adminTeacher_04.setPreferredSize(new java.awt.Dimension(1100, 770));
        adminTeacher_04.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        adminTeacher_04.add(serchIdField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

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
        adminTeacher_04.add(addNewStudent3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 350, 50, 50));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, 130));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 160, 130));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 160, 130));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 160, 130));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 160, 130));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 160, 130));

        jComboBox11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NFS Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 160, 40));

        jComboBox12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 160, 40));

        jComboBox13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BBA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 160, 40));

        jComboBox14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DVM Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 160, 40));

        jComboBox15.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 160, 40));

        jComboBox16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CSE Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, 40));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, 130));

        jComboBox17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 160, 40));

        jComboBox18.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LLA Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021", " " }));
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 160, 40));

        jComboBox19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AH Session", "2015 - 2016", "2016 - 20117", "2017 - 2018", "2018 - 2019", "2019  - 2020", "2020 - 2021" }));
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });
        adminTeacher_04.add(jComboBox19, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 160, 130));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminTeacher_04.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 600, 160, 130));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminTeacher.png"))); // NOI18N
        adminTeacher_04.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 350, 350));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        adminTeacher_04.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 240, -1, 80));

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
        adminTeacher_04.add(addStudentBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 220, 50));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel57.setText("jLabel8");
        adminTeacher_04.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, 200, 200));

        ProjectTab.addTab("tab4", adminTeacher_04);

        adminFaculty_05.setBackground(new java.awt.Color(0, 5, 42));
        adminFaculty_05.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel121.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(255, 255, 255));
        jLabel121.setText("Admin faculty");
        adminFaculty_05.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 410, 87));

        ProjectTab.addTab("tab5", adminFaculty_05);

        adminHall_06.setBackground(new java.awt.Color(0, 5, 42));
        adminHall_06.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel122.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(255, 255, 255));
        jLabel122.setText("Admin Hall");
        adminHall_06.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 380, 87));

        ProjectTab.addTab("tab6", adminHall_06);

        adminLibrary_07.setBackground(new java.awt.Color(0, 5, 42));
        adminLibrary_07.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel123.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 255, 255));
        jLabel123.setText("AZdmin Library");
        adminLibrary_07.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 400, 87));

        ProjectTab.addTab("tab7", adminLibrary_07);

        adminTransection_08.setBackground(new java.awt.Color(0, 5, 42));
        adminTransection_08.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel124.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 255, 255));
        jLabel124.setText("Admin Transection");
        adminTransection_08.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 560, 87));

        ProjectTab.addTab("tab8", adminTransection_08);

        adminHealthCare_09.setBackground(new java.awt.Color(0, 5, 42));
        adminHealthCare_09.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(255, 255, 255));
        jLabel125.setText("Admin Healthcare");
        adminHealthCare_09.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, 560, 87));

        ProjectTab.addTab("tab9", adminHealthCare_09);

        adminAdministration_10.setBackground(new java.awt.Color(0, 5, 42));
        adminAdministration_10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel126.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setText("Admin administration");
        adminAdministration_10.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 560, 87));

        ProjectTab.addTab("tab10", adminAdministration_10);

        adminOrganaization_11.setBackground(new java.awt.Color(0, 5, 42));
        adminOrganaization_11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel127.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 255, 255));
        jLabel127.setText("Admin Organaization");
        adminOrganaization_11.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 560, 87));

        ProjectTab.addTab("tab11", adminOrganaization_11);

        adminTranspoart_12.setBackground(new java.awt.Color(0, 5, 42));
        adminTranspoart_12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel128.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(255, 255, 255));
        jLabel128.setText("Admin Transport");
        adminTranspoart_12.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 560, 87));

        ProjectTab.addTab("tab12", adminTranspoart_12);

        adminStudentAdd_13.setBackground(new java.awt.Color(0, 5, 42));
        adminStudentAdd_13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/addStudent.png"))); // NOI18N
        adminStudentAdd_13.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 470, 80));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        jLabel30.setText("jLabel5");
        adminStudentAdd_13.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 390, 260));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText(":");
        adminStudentAdd_13.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 10, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText(":");
        adminStudentAdd_13.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 10, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText(":");
        adminStudentAdd_13.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 470, 10, 40));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText(":");
        adminStudentAdd_13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 520, 10, 40));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText(":");
        adminStudentAdd_13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, 10, 30));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText(":");
        adminStudentAdd_13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 10, 40));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Birth Date");
        adminStudentAdd_13.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 90, 40));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Nid No ");
        adminStudentAdd_13.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 70, 40));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Address");
        adminStudentAdd_13.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 520, 80, 40));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Mother Name");
        adminStudentAdd_13.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 120, 40));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Father Name");
        adminStudentAdd_13.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 110, 40));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Name");
        adminStudentAdd_13.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 60, 40));

        nameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        nameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameValActionPerformed(evt);
            }
        });
        adminStudentAdd_13.add(nameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 300, 40));

        fnameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fnameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fnameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameValActionPerformed(evt);
            }
        });
        adminStudentAdd_13.add(fnameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 300, 40));

        mnameVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        mnameVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnameVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnameValActionPerformed(evt);
            }
        });
        adminStudentAdd_13.add(mnameVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, 300, 40));

        addrVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addrVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addrVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrValActionPerformed(evt);
            }
        });
        adminStudentAdd_13.add(addrVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 300, 40));

        nidVal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nidVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nidVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nidValActionPerformed(evt);
            }
        });
        adminStudentAdd_13.add(nidVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, 300, 40));

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
        adminStudentAdd_13.add(dobVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 300, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText(":");
        adminStudentAdd_13.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, 20, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText(":");
        adminStudentAdd_13.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 430, 20, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText(":");
        adminStudentAdd_13.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 20, 40));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText(":");
        adminStudentAdd_13.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 520, 20, 30));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText(":");
        adminStudentAdd_13.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 570, 20, 40));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText(":");
        adminStudentAdd_13.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 620, 20, 30));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Faculty");
        adminStudentAdd_13.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 570, 60, 40));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Blood");
        adminStudentAdd_13.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 520, 50, 30));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Session");
        adminStudentAdd_13.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 470, 70, 40));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Reg");
        adminStudentAdd_13.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 40, 40));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Id");
        adminStudentAdd_13.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 30, 40));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Hall");
        adminStudentAdd_13.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 40, 30));

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
        adminStudentAdd_13.add(idVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, 300, 40));

        regVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        regVal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        regVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regValActionPerformed(evt);
            }
        });
        adminStudentAdd_13.add(regVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 300, 40));

        sessionVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        sessionVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", "2021-2022", "2022-2023", "2023-2024", "2024-2025", "2025-2026", " " }));
        sessionVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentAdd_13.add(sessionVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 470, 300, 40));

        bloodVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bloodVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "A +", "A -", "B +", "B -", "O +", "O -", "AB +", "AB -", " " }));
        bloodVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentAdd_13.add(bloodVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 300, 40));

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
        adminStudentAdd_13.add(facVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 570, 300, 40));

        hallVal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        hallVal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Sher-E-Bangla Hall 1", "Sher-E-Bangla Hall 2", "Keramat Ali Hall", "Bangabandhu Sheikh Mujibor Rahman Hall", "Kobi Begum Sufia Kamal Hall", "Sheikh Fajilatunnesa Mujib Hall", "Captain Mohiuddin Jahangir Hall", "Sheikh Fajilatunnesa Mujib Hall-Babuganj", " " }));
        hallVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentAdd_13.add(hallVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 620, 300, 40));

        alertSuccessText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        alertSuccessText.setForeground(new java.awt.Color(255, 255, 255));
        adminStudentAdd_13.add(alertSuccessText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 360, 50));

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
        adminStudentAdd_13.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 100, 40));

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
        adminStudentAdd_13.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 670, 100, 40));

        bottomrightborder4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/bordertop left270_270.png"))); // NOI18N
        adminStudentAdd_13.add(bottomrightborder4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, 240));

        ProjectTab.addTab("tab14", adminStudentAdd_13);

        adminAccountVerify_14.setBackground(new java.awt.Color(0, 5, 42));
        adminAccountVerify_14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userVerifyIdentity.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        userVerifyIdentity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select yuor identity", "Student", "Teacher", "Admin" }));
        userVerifyIdentity.setBorder(null);
        userVerifyIdentity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userVerifyIdentityActionPerformed(evt);
            }
        });
        adminAccountVerify_14.add(userVerifyIdentity, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, 190, 40));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/verifyAccount.png"))); // NOI18N
        adminAccountVerify_14.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 580, 90));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel58.setText("jLabel8");
        adminAccountVerify_14.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 200, 200));

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
        adminAccountVerify_14.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 560, 100, 45));

        bottomrightborder2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border left bottom .png"))); // NOI18N
        adminAccountVerify_14.add(bottomrightborder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 350, 370));

        bottomrightborder3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        adminAccountVerify_14.add(bottomrightborder3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, 330, 340));

        vkey.setBackground(new java.awt.Color(0, 5, 42));
        vkey.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        vkey.setForeground(new java.awt.Color(255, 255, 255));
        vkey.setBorder(null);
        adminAccountVerify_14.add(vkey, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, 300, 50));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        adminAccountVerify_14.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 30, 40));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        adminAccountVerify_14.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 410, 20));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Code");
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        adminAccountVerify_14.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 490, 40, 40));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        adminAccountVerify_14.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, 40, 40));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        adminAccountVerify_14.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, 410, 10));

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
        adminAccountVerify_14.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, 120, 47));

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
        adminAccountVerify_14.add(verifyUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 350, 40));

        ProjectTab.addTab("tab15", adminAccountVerify_14);

        adminFacultyStudent_15.setBackground(new java.awt.Color(0, 5, 42));
        adminFacultyStudent_15.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminFacultyStudent_15ComponentShown(evt);
            }
        });
        adminFacultyStudent_15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        adminFacultyStudent_15.add(view1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 120, 48));

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

        adminFacultyStudent_15.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 1050, 520));

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
        adminFacultyStudent_15.add(cseSession1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 240, 48));

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
        adminFacultyStudent_15.add(view2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 120, 48));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        adminFacultyStudent_15.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 110, -1, 48));

        ProjectTab.addTab("tab16", adminFacultyStudent_15);

        adminStudentDetails_16.setBackground(new java.awt.Color(0, 5, 42));
        adminStudentDetails_16.setPreferredSize(new java.awt.Dimension(1110, 890));
        adminStudentDetails_16.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminStudentDetails_16ComponentShown(evt);
            }
        });
        adminStudentDetails_16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/studentDetails.png"))); // NOI18N
        jLabel63.setText("jLabel16");
        adminStudentDetails_16.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 620, 70));

        sDetailsProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        adminStudentDetails_16.add(sDetailsProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 200, 180));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel64.setText("jLabel56");
        jLabel64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 30, 30));

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel71.setText("jLabel56");
        jLabel71.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 670, 30, 30));

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText(":");
        jLabel72.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel72MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 710, 20, 30));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel73.setText("jLabel56");
        jLabel73.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 30, 30));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel74.setText("jLabel56");
        jLabel74.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 30, 30));

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel75.setText("jLabel56");
        jLabel75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 30, 30));

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel76.setText("jLabel56");
        jLabel76.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 620, 30, 30));

        adminBlood1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood1.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood1.setText("Father Name");
        adminStudentDetails_16.add(adminBlood1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 130, 30));

        adminDob1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob1.setForeground(new java.awt.Color(255, 255, 255));
        adminDob1.setText("Phone");
        adminStudentDetails_16.add(adminDob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 670, 70, -1));

        snid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        snid.setForeground(new java.awt.Color(255, 204, 0));
        snid.setText("Name");
        adminStudentDetails_16.add(snid, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 710, 240, 30));

        adminDob2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob2.setForeground(new java.awt.Color(255, 255, 255));
        adminDob2.setText("Mother Name");
        adminStudentDetails_16.add(adminDob2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 130, -1));

        adminDob3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob3.setForeground(new java.awt.Color(255, 255, 255));
        adminDob3.setText("Address");
        adminStudentDetails_16.add(adminDob3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 90, -1));

        adminDob4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob4.setForeground(new java.awt.Color(255, 255, 255));
        adminDob4.setText("Birth Date");
        adminStudentDetails_16.add(adminDob4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 570, 110, -1));

        adminDob5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob5.setForeground(new java.awt.Color(255, 255, 255));
        adminDob5.setText("Blood Group ");
        adminStudentDetails_16.add(adminDob5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 620, 120, -1));

        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel77.setText("jLabel56");
        jLabel77.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 710, 30, 30));

        adminDob6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob6.setForeground(new java.awt.Color(255, 255, 255));
        adminDob6.setText("Nid no");
        adminStudentDetails_16.add(adminDob6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 710, 70, -1));

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel78.setText("jLabel56");
        jLabel78.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 30, 30));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText(":");
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel79MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 20, 20));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText(":");
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel80MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, 20, 20));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText(":");
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel81MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, 20, 20));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText(":");
        jLabel82.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel82MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 20, 20));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText(":");
        jLabel83.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel83MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 570, 20, 20));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText(":");
        jLabel84.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel84MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 620, 20, 20));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText(":");
        jLabel85.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel85MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 670, 20, -1));

        adminNid2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid2.setForeground(new java.awt.Color(255, 255, 255));
        adminNid2.setText("Name");
        adminStudentDetails_16.add(adminNid2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 90, 30));

        sname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sname.setForeground(new java.awt.Color(255, 204, 0));
        sname.setText("Name");
        adminStudentDetails_16.add(sname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 240, 40));

        sfname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfname.setForeground(new java.awt.Color(255, 204, 0));
        sfname.setText("Name");
        adminStudentDetails_16.add(sfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 240, 40));

        smname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        smname.setForeground(new java.awt.Color(255, 204, 0));
        smname.setText("Name");
        adminStudentDetails_16.add(smname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 240, 40));

        saddr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saddr.setForeground(new java.awt.Color(255, 204, 0));
        saddr.setText("Name");
        adminStudentDetails_16.add(saddr, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 240, 40));

        sdob.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sdob.setForeground(new java.awt.Color(255, 204, 0));
        sdob.setText("Name");
        adminStudentDetails_16.add(sdob, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 560, 240, 40));

        sblood.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sblood.setForeground(new java.awt.Color(255, 204, 0));
        sblood.setText("Name");
        adminStudentDetails_16.add(sblood, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 610, 240, 40));

        sphone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sphone.setForeground(new java.awt.Color(255, 204, 0));
        sphone.setText("Name");
        adminStudentDetails_16.add(sphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 660, 240, 40));

        adminNid3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid3.setForeground(new java.awt.Color(255, 255, 255));
        adminNid3.setText("Id");
        adminStudentDetails_16.add(adminNid3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, 90, 30));

        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel86.setText("jLabel56");
        jLabel86.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel86.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel86MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 30, 30));

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel87.setText("jLabel56");
        jLabel87.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 30, 30));

        adminBlood2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood2.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood2.setText("Reg");
        adminStudentDetails_16.add(adminBlood2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 130, 30));

        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel88.setText("jLabel56");
        jLabel88.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 470, 30, 30));

        adminDob7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob7.setForeground(new java.awt.Color(255, 255, 255));
        adminDob7.setText("Email");
        adminStudentDetails_16.add(adminDob7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 130, -1));

        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel89.setText("jLabel56");
        jLabel89.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 520, 30, 30));

        adminDob8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob8.setForeground(new java.awt.Color(255, 255, 255));
        adminDob8.setText("Session");
        adminStudentDetails_16.add(adminDob8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, 90, -1));

        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel90.setText("jLabel56");
        jLabel90.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, 30, 30));

        adminDob9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob9.setForeground(new java.awt.Color(255, 255, 255));
        adminDob9.setText("Faculty");
        adminStudentDetails_16.add(adminDob9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 570, 110, -1));

        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel91.setText("jLabel56");
        jLabel91.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 620, 30, 30));

        adminDob10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob10.setForeground(new java.awt.Color(255, 255, 255));
        adminDob10.setText("Hall");
        adminStudentDetails_16.add(adminDob10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 120, -1));

        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel92.setText("jLabel56");
        jLabel92.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 670, 30, 30));

        adminDob11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob11.setForeground(new java.awt.Color(255, 255, 255));
        adminDob11.setText("Semmister");
        adminStudentDetails_16.add(adminDob11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 670, 140, -1));

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText(":");
        jLabel94.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel94.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel94MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, 20, 20));

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText(":");
        jLabel95.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel95.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel95MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, 20, 20));

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText(":");
        jLabel96.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel96.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel96MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 470, 20, 20));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText(":");
        jLabel97.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel97.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel97MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 520, 20, 20));

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText(":");
        jLabel98.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel98.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel98MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 570, 20, 20));

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText(":");
        jLabel99.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel99.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel99MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 620, 20, 20));

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setText(":");
        jLabel100.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel100.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel100MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 670, 20, -1));

        ssem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssem.setForeground(new java.awt.Color(255, 204, 0));
        ssem.setText("Name");
        adminStudentDetails_16.add(ssem, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 660, 240, 40));

        shall.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shall.setForeground(new java.awt.Color(255, 204, 0));
        shall.setText("Name");
        adminStudentDetails_16.add(shall, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 610, 240, 40));

        sfac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfac.setForeground(new java.awt.Color(255, 204, 0));
        sfac.setText("Name");
        adminStudentDetails_16.add(sfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 560, 240, 40));

        ssession.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssession.setForeground(new java.awt.Color(255, 204, 0));
        ssession.setText("Name");
        adminStudentDetails_16.add(ssession, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 240, 40));

        semail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        semail.setForeground(new java.awt.Color(255, 204, 0));
        semail.setText("Name");
        adminStudentDetails_16.add(semail, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 460, 240, 40));

        sreg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sreg.setForeground(new java.awt.Color(255, 204, 0));
        sreg.setText("Name");
        adminStudentDetails_16.add(sreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 410, 240, 40));

        sid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sid.setForeground(new java.awt.Color(255, 204, 0));
        sid.setText("Name");
        adminStudentDetails_16.add(sid, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 360, 240, 40));

        printbtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        printbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/print.png"))); // NOI18N
        printbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtnActionPerformed(evt);
            }
        });
        adminStudentDetails_16.add(printbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 60, 40));

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
        adminStudentDetails_16.add(view3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 40));

        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel102.setText("jLabel56");
        jLabel102.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentDetails_16.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 30, 30));

        adminDob13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob13.setForeground(new java.awt.Color(255, 255, 255));
        adminDob13.setText("Status");
        adminStudentDetails_16.add(adminDob13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 70, 30));

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setText(":");
        jLabel103.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel103.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel103MouseClicked(evt);
            }
        });
        adminStudentDetails_16.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 20, 30));

        sstatusBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sstatusBtn.setBorder(null);
        sstatusBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sstatusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sstatusBtnActionPerformed(evt);
            }
        });
        adminStudentDetails_16.add(sstatusBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 110, 35));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        adminStudentDetails_16.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, 60, 40));

        ProjectTab.addTab("tab2", adminStudentDetails_16);

        studentProfileView_17.setBackground(new java.awt.Color(0, 5, 42));
        studentProfileView_17.setPreferredSize(new java.awt.Dimension(1110, 890));
        studentProfileView_17.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                studentProfileView_17ComponentShown(evt);
            }
        });
        studentProfileView_17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setText("Change Profile :");
        studentProfileView_17.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 160, 30));

        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/studentProfile.png"))); // NOI18N
        jLabel101.setText("jLabel16");
        studentProfileView_17.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 470, 70));

        studentUpdateProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        studentProfileView_17.add(studentUpdateProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 200, 180));

        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel104.setText("jLabel56");
        jLabel104.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 30, 40));

        studentName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        studentName.setForeground(new java.awt.Color(153, 0, 102));
        studentName.setText("Name");
        studentProfileView_17.add(studentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 410, 40));

        file1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/folder.png"))); // NOI18N
        file1.setText("jLabel56");
        file1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                file1MouseClicked(evt);
            }
        });
        studentProfileView_17.add(file1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, 40, 30));

        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel105.setText("jLabel56");
        jLabel105.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, 30, 30));

        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/nid.png"))); // NOI18N
        jLabel106.setText("jLabel56");
        jLabel106.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel106.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel106MouseClicked(evt);
            }
        });
        studentProfileView_17.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 30, 30));

        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/dob.png"))); // NOI18N
        jLabel107.setText("jLabel56");
        jLabel107.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 30, 30));

        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/uname.png"))); // NOI18N
        jLabel108.setText("jLabel56");
        jLabel108.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 30, 30));

        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/phone.png"))); // NOI18N
        jLabel109.setText("jLabel56");
        jLabel109.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 30, 30));

        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel110.setText("jLabel56");
        jLabel110.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 30, 30));

        studentEmail.setBackground(new java.awt.Color(0, 5, 42));
        studentEmail.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        studentEmail.setForeground(new java.awt.Color(255, 255, 255));
        studentEmail.setText("gfhgj");
        studentEmail.setBorder(null);
        studentProfileView_17.add(studentEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 290, 40));
        studentProfileView_17.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 300, 10));

        studentBlood.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        studentBlood.setForeground(new java.awt.Color(255, 204, 0));
        studentBlood.setText("Name");
        studentProfileView_17.add(studentBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 300, -1));
        studentProfileView_17.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 300, 10));
        studentProfileView_17.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 300, 10));

        studentUsername.setBackground(new java.awt.Color(0, 5, 42));
        studentUsername.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        studentUsername.setForeground(new java.awt.Color(255, 255, 255));
        studentUsername.setText("ghghghgh");
        studentUsername.setBorder(null);
        studentProfileView_17.add(studentUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 300, 40));

        studentPhone.setBackground(new java.awt.Color(0, 5, 42));
        studentPhone.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        studentPhone.setForeground(new java.awt.Color(255, 255, 255));
        studentPhone.setText("iiiiii");
        studentPhone.setBorder(null);
        studentPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPhoneActionPerformed(evt);
            }
        });
        studentProfileView_17.add(studentPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 290, 40));

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
        studentProfileView_17.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 110, 50));

        studentDob.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        studentDob.setForeground(new java.awt.Color(255, 204, 0));
        studentDob.setText("Name");
        studentProfileView_17.add(studentDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 310, -1));

        studentNid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        studentNid.setForeground(new java.awt.Color(255, 204, 0));
        studentNid.setText("Nid");
        studentProfileView_17.add(studentNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 260, 30));

        bottomrightborder6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/border right bottom.png"))); // NOI18N
        studentProfileView_17.add(bottomrightborder6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 330, 340));
        studentProfileView_17.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 300, 10));

        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/blood.png"))); // NOI18N
        jLabel111.setText("jLabel56");
        jLabel111.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileView_17.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 30));

        studentPassword.setBackground(new java.awt.Color(0, 5, 42));
        studentPassword.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        studentPassword.setForeground(new java.awt.Color(255, 255, 255));
        studentPassword.setBorder(null);
        studentProfileView_17.add(studentPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 510, 210, 40));

        studentPassEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/verify.png"))); // NOI18N
        studentPassEye.setBorder(null);
        studentPassEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentPassEye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPassEyeActionPerformed(evt);
            }
        });
        studentProfileView_17.add(studentPassEye, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 510, 40, 30));

        ProjectTab.addTab("tab2", studentProfileView_17);

        studentDashboard_18.setBackground(new java.awt.Color(0, 5, 42));
        studentDashboard_18.setPreferredSize(new java.awt.Dimension(1200, 855));
        studentDashboard_18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Student   Dashboard");
        studentDashboard_18.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 560, 87));

        ProjectTab.addTab("tab1", studentDashboard_18);

        studentPayment_19.setBackground(new java.awt.Color(0, 5, 42));
        studentPayment_19.setPreferredSize(new java.awt.Dimension(1200, 855));
        studentPayment_19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 255, 255));
        jLabel113.setText("Student  Payment");
        studentPayment_19.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 560, 87));

        ProjectTab.addTab("tab1", studentPayment_19);

        studentFaculty_20.setBackground(new java.awt.Color(0, 5, 42));
        studentFaculty_20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel119.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(255, 255, 255));
        jLabel119.setText("Student faculty");
        studentFaculty_20.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 558, 156));

        ProjectTab.addTab("tab5", studentFaculty_20);

        studentExam_21.setBackground(new java.awt.Color(0, 5, 42));
        studentExam_21.setPreferredSize(new java.awt.Dimension(1200, 855));
        studentExam_21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 255, 255));
        jLabel115.setText("Student Exam");
        studentExam_21.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 560, 87));

        ProjectTab.addTab("tab1", studentExam_21);

        studentResult_22.setBackground(new java.awt.Color(0, 5, 42));
        studentResult_22.setPreferredSize(new java.awt.Dimension(1200, 855));
        studentResult_22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(255, 255, 255));
        jLabel117.setText("Student   Result");
        studentResult_22.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 560, 87));

        ProjectTab.addTab("tab1", studentResult_22);

        contentPanel.add(ProjectTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 1115, 890));

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

    private void adminHallBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminHallBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(5);
    }//GEN-LAST:event_adminHallBtnActionPerformed

    private void adminLibraryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminLibraryBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(6);
    }//GEN-LAST:event_adminLibraryBtnActionPerformed

    private void adminFacultyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminFacultyBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(4);
    }//GEN-LAST:event_adminFacultyBtnActionPerformed

    private void adminTeacherBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTeacherBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(3);
    }//GEN-LAST:event_adminTeacherBtnActionPerformed

    private void adminTransectionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTransectionBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(7);
    }//GEN-LAST:event_adminTransectionBtnActionPerformed

    private void adminHealthCareBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminHealthCareBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(8);
    }//GEN-LAST:event_adminHealthCareBtnActionPerformed

    private void adminAdministrationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminAdministrationBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(9);
    }//GEN-LAST:event_adminAdministrationBtnActionPerformed

    private void adminOrganaizationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminOrganaizationBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(10);
    }//GEN-LAST:event_adminOrganaizationBtnActionPerformed

    private void adminTranspoartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTranspoartBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(11);
    }//GEN-LAST:event_adminTranspoartBtnActionPerformed

    private void adminStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminStudentBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(2);
    }//GEN-LAST:event_adminStudentBtnActionPerformed

    private void adminProfileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminProfileBtnActionPerformed
        // TODO add your handling code here:
        
         ProjectTab.setSelectedIndex(1);
    }//GEN-LAST:event_adminProfileBtnActionPerformed

    private void adminDashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDashboardBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(0);
    }//GEN-LAST:event_adminDashboardBtnActionPerformed

    private void addStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtnActionPerformed
        // TODO add your handling code here:        
        ProjectTab.setSelectedIndex(12);
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
             ProjectTab.setSelectedIndex(15);     
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
       ProjectTab.setSelectedIndex(2);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling
//        hide();

        hide(identity);
         profileView();
        
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
                adminUpdateProfilePic.setIcon(ResizeImage(photoPath));
                adminProfilepic.setIcon(ResizeImage(photoPath));
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
                    ProjectTab.setSelectedIndex(13);

                    
                    
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
                            String query = "SELECT * FROM users where uid='"+logeduserid+"' AND role='"+identitys+"' AND token='"+verifyUserVkey+"' ";
                            ResultSet rs = cc.s.executeQuery(query);
                            if(rs.next()){  
                                ps= cc.c.prepareStatement("UPDATE users SET username=?, password=?, email=?, phone=?, token='' Where uid=?");
                                ps.setString(1, adminUsername.getText());
                                ps.setString(2, adminPassword.getText());
                                ps.setString(3, adminEmail.getText());
                                ps.setString(4, adminPhone.getText());

//                                ps.setString(5, "");
                                ps.setString(5,logeduserid);
                                
                                    ps.execute();
                                int count = ps.executeUpdate();
                                if(count > 0){ 
                                   alert("success","true","Your profile is upto date");
                                   verifyUsername.setText("Your Email Address");
                                   ProjectTab.setSelectedIndex(1);
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
        ProjectTab.setSelectedIndex(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cseSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cseSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "cse";
        String sessions = (String)cseSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
        
        
    }//GEN-LAST:event_cseSessionItemStateChanged

    private void facValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facValActionPerformed

    private void adminFacultyStudent_15ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminFacultyStudent_15ComponentShown
        // TODO add your handling code here:
//        String sessions = (String)cseSession.getSelectedItem();
//        cseSession1.getModel().setSelectedItem(cseSession.getSelectedItem());
//        try{
//            conn cc = new conn();
//            try{
//                ResultSet rs = cc.s.executeQuery("select * from student where session='"+sessions+"' and fac='"+selectedFaculty+"' ");
//                DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
//                for( int i = table.getRowCount() - 1; i >= 0; i-- ){
//                    table.removeRow(i);
//                }
//
//                if(rs.next()){
//                    dataFoundOrNot=true;
//                    do{
//                        String name = rs.getString("name");
//                        String id = rs.getString("roll");
//                        String reg = rs.getString("reg");
//                        String email = rs.getString("email");
//                        String phone = rs.getString("phone");
//                        String hall = rs.getString("hall");
//                        String st[] = {name,id,reg,email,phone,hall};
//                        table.addRow(st);
//                    }while(rs.next());
//                }else{ 
//                
//                   alert("error","true","No data found");
//                }
//            }catch(SQLException e){
//            }
//               cc.s.close();
//               
//        }catch(SQLException e){
//        }
    }//GEN-LAST:event_adminFacultyStudent_15ComponentShown

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
         ProjectTab.setSelectedIndex(2);
         
    }//GEN-LAST:event_view1ActionPerformed

    private void view2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view2ActionPerformed
        // TODO add your handling code here:
        
             DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
             int id = allStudentsByFaculty.getSelectedRow();
             if(id>=0){ 
              ProjectTab.setSelectedIndex(15);
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

    private void adminStudentDetails_16ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminStudentDetails_16ComponentShown
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
    }//GEN-LAST:event_adminStudentDetails_16ComponentShown

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
        
        ProjectTab.setSelectedIndex(14);
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
             }else{ 
               alert("error","true","No field selected");
            }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        deleteStudent(sid.getText());
        ProjectTab.setSelectedIndex(14);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void facValItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facValItemStateChanged
        // TODO add your handling code here:
        
        String fac = (String)facVal.getSelectedItem();
        fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac);
        System.out.println(fac);
    }//GEN-LAST:event_facValItemStateChanged

    private void agriSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_agriSessionItemStateChanged
        // TODO add your handling code here:
        
        selectedFaculty = "agri";
        String sessions = (String)agriSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
       
    }//GEN-LAST:event_agriSessionItemStateChanged

    private void dvmSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dvmSessionItemStateChanged
        // TODO add your handling code here:

        selectedFaculty = "dvm";
        String sessions = (String)dvmSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_dvmSessionItemStateChanged

    private void bamSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bamSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "bam";
        String sessions = (String)bamSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_bamSessionItemStateChanged

    private void fishSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fishSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "fish";
        String sessions = (String)fishSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_fishSessionItemStateChanged

    private void nfsSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_nfsSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "nfs";
        String sessions = (String)nfsSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_nfsSessionItemStateChanged

    private void esdmSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_esdmSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "esdm";
        String sessions = (String)esdmSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_esdmSessionItemStateChanged

    private void llaSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_llaSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "lam";
        String sessions = (String) llaSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_llaSessionItemStateChanged

    private void ahSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ahSessionItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "ah";
        String sessions = (String)ahSession.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions)){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_ahSessionItemStateChanged

    private void studentSidebarLogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSidebarLogoutBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentSidebarLogoutBtnActionPerformed

    private void studentDashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentDashboardBtnActionPerformed
        // TODO add your handling code here:
        
        ProjectTab.setSelectedIndex(17);
    }//GEN-LAST:event_studentDashboardBtnActionPerformed

    private void studentProfileViewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentProfileViewBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(16);
    }//GEN-LAST:event_studentProfileViewBtnActionPerformed

    private void studentPaymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPaymentBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(18);
    }//GEN-LAST:event_studentPaymentBtnActionPerformed

    private void studentFacultyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentFacultyBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(19);
    }//GEN-LAST:event_studentFacultyBtnActionPerformed

    private void studentExamBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentExamBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(20);
    }//GEN-LAST:event_studentExamBtnActionPerformed

    private void studentResultBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentResultBtnActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(21);
    }//GEN-LAST:event_studentResultBtnActionPerformed

    private void file1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file1MouseClicked
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
                studentUpdateProfilePic.setIcon(ResizeImage(photoPath));
                studentSidebarProfilepic.setIcon(ResizeImage(photoPath));
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
    }//GEN-LAST:event_file1MouseClicked

    private void jLabel106MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel106MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel106MouseClicked

    private void studentPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentPhoneActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void studentDashboardBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentDashboardBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_studentDashboardBtnMouseClicked

    private void studentProfileView_17ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_studentProfileView_17ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_studentProfileView_17ComponentShown

    private void serchIdFieldComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_serchIdFieldComponentShown
        // TODO add your handling code here:
         serchIdField.setBorder(BorderFactory.createCompoundBorder(regVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
    }//GEN-LAST:event_serchIdFieldComponentShown

    private void adminStudent_03ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminStudent_03ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_adminStudent_03ComponentShown

    private void adminProfile_02ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminProfile_02ComponentShown
        // TODO add your handling code here:
       
    }//GEN-LAST:event_adminProfile_02ComponentShown

    
    
    private void studentPassEyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPassEyeActionPerformed
        // TODO add your handling code here:
        PasswordShowAndHide(click);
    }//GEN-LAST:event_studentPassEyeActionPerformed

    private void adminPassEyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPassEyeActionPerformed
        // TODO add your handling code here:
        System.out.println("eyeee");
        PasswordShowAndHide(click);
    }//GEN-LAST:event_adminPassEyeActionPerformed

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
    private javax.swing.JPanel AlertPanel;
    private javax.swing.JTabbedPane ProjectTab;
    private javax.swing.JPanel StudentSideBarPanel;
    private javax.swing.JButton addNewStudent2;
    private javax.swing.JButton addNewStudent3;
    private javax.swing.JButton addStudentBtn;
    private javax.swing.JButton addStudentBtn2;
    private javax.swing.JTextField addrVal;
    private javax.swing.JPanel adminAccountVerify_14;
    private javax.swing.JButton adminAdministrationBtn;
    private javax.swing.JPanel adminAdministration_10;
    private javax.swing.JLabel adminBlood;
    private javax.swing.JLabel adminBlood1;
    private javax.swing.JLabel adminBlood2;
    private javax.swing.JButton adminDashboardBtn;
    private javax.swing.JPanel adminDashboard_01;
    private javax.swing.JLabel adminDob;
    private javax.swing.JLabel adminDob1;
    private javax.swing.JLabel adminDob10;
    private javax.swing.JLabel adminDob11;
    private javax.swing.JLabel adminDob13;
    private javax.swing.JLabel adminDob2;
    private javax.swing.JLabel adminDob3;
    private javax.swing.JLabel adminDob4;
    private javax.swing.JLabel adminDob5;
    private javax.swing.JLabel adminDob6;
    private javax.swing.JLabel adminDob7;
    private javax.swing.JLabel adminDob8;
    private javax.swing.JLabel adminDob9;
    private javax.swing.JTextField adminEmail;
    private javax.swing.JButton adminFacultyBtn;
    private javax.swing.JPanel adminFacultyStudent_15;
    private javax.swing.JPanel adminFaculty_05;
    private javax.swing.JButton adminHallBtn;
    private javax.swing.JPanel adminHall_06;
    private javax.swing.JButton adminHealthCareBtn;
    private javax.swing.JPanel adminHealthCare_09;
    private javax.swing.JButton adminLibraryBtn;
    private javax.swing.JPanel adminLibrary_07;
    private javax.swing.JLabel adminName;
    private javax.swing.JLabel adminNid;
    private javax.swing.JLabel adminNid2;
    private javax.swing.JLabel adminNid3;
    private javax.swing.JButton adminOrganaizationBtn;
    private javax.swing.JPanel adminOrganaization_11;
    private javax.swing.JButton adminPassEye;
    private javax.swing.JPasswordField adminPassword;
    private javax.swing.JTextField adminPhone;
    private javax.swing.JButton adminProfileBtn;
    private javax.swing.JPanel adminProfile_02;
    private javax.swing.JLabel adminProfilepic;
    private javax.swing.JPanel adminStudentAdd_13;
    private javax.swing.JButton adminStudentBtn;
    private javax.swing.JPanel adminStudentDetails_16;
    private javax.swing.JPanel adminStudent_03;
    private javax.swing.JButton adminTeacherBtn;
    private javax.swing.JPanel adminTeacher_04;
    private javax.swing.JButton adminTransectionBtn;
    private javax.swing.JPanel adminTransection_08;
    private javax.swing.JButton adminTranspoartBtn;
    private javax.swing.JPanel adminTranspoart_12;
    private javax.swing.JLabel adminUpdateProfilePic;
    private javax.swing.JTextField adminUsername;
    private javax.swing.JComboBox<String> agriSession;
    private javax.swing.JComboBox<String> ahSession;
    private javax.swing.JLabel alertSuccessText;
    private javax.swing.JTable allStudentsByFaculty;
    private javax.swing.JComboBox<String> bamSession;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel bg1;
    private javax.swing.JComboBox<String> bloodVal;
    private javax.swing.JLabel bottomrightborder2;
    private javax.swing.JLabel bottomrightborder3;
    private javax.swing.JLabel bottomrightborder4;
    private javax.swing.JLabel bottomrightborder5;
    private javax.swing.JLabel bottomrightborder6;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JComboBox<String> cseSession;
    private javax.swing.JComboBox<String> cseSession1;
    private com.toedter.calendar.JDateChooser dobVal;
    private javax.swing.JComboBox<String> dvmSession;
    private javax.swing.JLabel error;
    private javax.swing.JButton errorClose;
    private javax.swing.JLabel errorLogo;
    private javax.swing.JLabel errorText;
    private javax.swing.JComboBox<String> esdmSession;
    private javax.swing.JComboBox<String> facVal;
    private javax.swing.JLabel file;
    private javax.swing.JLabel file1;
    private javax.swing.JComboBox<String> fishSession;
    private javax.swing.JTextField fnameVal;
    private javax.swing.JComboBox<String> hallVal;
    private javax.swing.JTextField idVal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
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
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JComboBox<String> llaSession;
    private javax.swing.JButton logout;
    private javax.swing.JTextField mnameVal;
    private javax.swing.JTextField nameVal;
    private javax.swing.JComboBox<String> nfsSession;
    private javax.swing.JTextField nidVal;
    private javax.swing.JButton printbtn;
    private javax.swing.JTextField regVal;
    private javax.swing.JLabel sDetailsProfilePic;
    private javax.swing.JLabel saddr;
    private javax.swing.JLabel sblood;
    private javax.swing.JLabel sdob;
    private javax.swing.JLabel semail;
    private javax.swing.JTextField serchIdField;
    private javax.swing.JTextField serchIdField1;
    private javax.swing.JComboBox<String> sessionVal;
    private javax.swing.JLabel sfac;
    private javax.swing.JLabel sfname;
    private javax.swing.JLabel shall;
    private javax.swing.JLabel sid;
    private javax.swing.JLabel smname;
    private javax.swing.JLabel sname;
    private javax.swing.JLabel snid;
    private javax.swing.JLabel sphone;
    private javax.swing.JLabel sreg;
    private javax.swing.JLabel ssem;
    private javax.swing.JLabel ssession;
    private javax.swing.JButton sstatusBtn;
    private javax.swing.JLabel studentBlood;
    private javax.swing.JButton studentDashboardBtn;
    private javax.swing.JPanel studentDashboard_18;
    private javax.swing.JLabel studentDob;
    private javax.swing.JTextField studentEmail;
    private javax.swing.JButton studentExamBtn;
    private javax.swing.JPanel studentExam_21;
    private javax.swing.JButton studentFacultyBtn;
    private javax.swing.JPanel studentFaculty_20;
    private javax.swing.JLabel studentName;
    private javax.swing.JLabel studentNid;
    private javax.swing.JButton studentPassEye;
    private javax.swing.JPasswordField studentPassword;
    private javax.swing.JButton studentPaymentBtn;
    private javax.swing.JPanel studentPayment_19;
    private javax.swing.JTextField studentPhone;
    private javax.swing.JButton studentProfileViewBtn;
    private javax.swing.JPanel studentProfileView_17;
    private javax.swing.JButton studentResultBtn;
    private javax.swing.JPanel studentResult_22;
    private javax.swing.JButton studentSidebarLogoutBtn;
    private javax.swing.JLabel studentSidebarProfilepic;
    private javax.swing.JLabel studentUpdateProfilePic;
    private javax.swing.JTextField studentUsername;
    private javax.swing.JLabel success;
    private javax.swing.JButton successClose;
    private javax.swing.JLabel successLogo;
    private javax.swing.JLabel successText;
    private javax.swing.JComboBox<String> userVerifyIdentity;
    private javax.swing.JTextField verifyUsername;
    private javax.swing.JButton view1;
    private javax.swing.JButton view2;
    private javax.swing.JButton view3;
    private javax.swing.JTextField vkey;
    // End of variables declaration//GEN-END:variables
}
