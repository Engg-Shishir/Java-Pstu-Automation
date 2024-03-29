/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import ImageInsert_ImageShow.Insert;
import static com.mysql.cj.util.SaslPrep.StringType.QUERY;
import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
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
import javax.swing.DefaultComboBoxModel;
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
    public String identity = "";
    public String logeduserid = "";
    public String dateFromDatePicker = "";
    public String studentsDetailsId = "",teacherDetailsId="";
    public int tableRowNo;
    private int random;
    public String selectedFaculty = "";
    boolean dataFoundOrNot = false;
    boolean click = false;
    String imagePath;
    int upDateOrnot;
    String selectedFacultyName;
    
        ResultSet rs;
	PreparedStatement ps=null;
    	Connection con = null;
	FileInputStream fs=null;
    
    int ip = 0;
    
    ArrayList<String> column = new ArrayList<String>();
     ArrayList<String> TeacherColumn = new ArrayList<String>();

       
       
    public AdminDashboards(String username, String identitys) {
        logeduserid = username;
        identity = identitys;
        initComponents();
    }
    

    
    public void hide(String identity){
       StudentSideBarPanel.setVisible(false);
       teacherSideBarPanel.setVisible(false);
       
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
           }else{
               if("admin".equals(identity)){
                  ProjectTab.setSelectedIndex(0); 
               }
              teacherSideBarPanel.setVisible(true);
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
               alert("error","false","");
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
       
       cseSession_16.setSelectedIndex(0);
       agriSession_16.setSelectedIndex(0);
       dvmSession_16.setSelectedIndex(0);
       bamSession_16.setSelectedIndex(0);
       fishSession_16.setSelectedIndex(0);
       nfsSession_16.setSelectedIndex(0);
       esdmSession_16.setSelectedIndex(0);
       llaSession_16.setSelectedIndex(0);
       ahSession_16.setSelectedIndex(0);
    }
    public void insertStudent(ArrayList<String> languages,String type,String uid){

        String[] data = new String[languages.size()];
        data = languages.toArray(data);

        try{
            conn cc = new conn();
            
            if("insert".equals(type)){
                int random = new Random().nextInt(900000) + 100000;
                Statement stmt = cc.c.createStatement();
                 stmt.executeUpdate("insert into users (username,uid,password,status,role)values('','"+data[6]+"','"+random+"','active','student') ");
                 stmt.executeUpdate("insert into student (`name`, `fname`, `mname`, `addr`, `nid`,`dob`, `uid`, `reg`, `session`, `blood`, `fac`, `hall`)"
                         + "values('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"')");
                alert("error","false","");
                alert("success","true","Student Successfully Added");
            }else{ 
                                                     
                String querys = "UPDATE teacher, users SET teacher.name=?,teacher.addr=?,"
                        + "teacher.dob=?,teacher.blood=?,teacher.nid=?,teacher.fac=?,teacher.dept=?,users.role=?"
                        + "WHERE teacher.uid = users.uid AND users.uid=?";

                ps= cc.c.prepareStatement(querys);
                ps.setString(1, data[0]);
                ps.setString(2, data[1]);
                ps.setString(3, data[2]);
                ps.setString(4, data[3]);
                ps.setString(5, data[4]);
                ps.setString(6, data[5]);
                ps.setString(7, data[6]);
                ps.setString(8, data[7]);
                ps.setString(9, uid);
                ps.execute();
                upDateOrnot = ps.executeUpdate();

                if(upDateOrnot > 0){ 
                   alert("success","true","Teacher Successfully Updated");
                }else{ 
                   alert("error","true","Somethig is wrong with your data");
                }
                
            }
            
            column.clear(); 
        }catch(SQLException ee){
            System.out.println("The error is:"+ee);
        }
    }
    
    public void insertTeacher(ArrayList<String> languages){

        String[] data = new String[languages.size()];
        data = languages.toArray(data);

        try{
            conn cc = new conn();
                int random = new Random().nextInt(900000) + 100000;
                
                Statement stmt = cc.c.createStatement();
                stmt.executeUpdate("insert into users (username,uid,password,status,role)values('','"+data[4]+"','"+random+"','active','teacher') ");
                stmt.executeUpdate("insert into teacher (`name`,`addr`,`phone`,`dob`,`uid`, `reg`, `nid`,`fac`, `dept`,`blood`)"
                        + "values('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"')");
                 
//                  System.out.println(languages.toString());
//                System.out.println("Name"+data[0]);                 
//                System.out.println("Addr"+data[1]);                 
//                System.out.println("Phone"+data[2]);                 
//                System.out.println("Birth"+data[3]);                 
//                System.out.println("Uid"+data[4]);                 
//                System.out.println("Reg"+data[5]);                 
//                System.out.println("Nid"+data[6]);                 
//                System.out.println("Faculty"+data[7]);                 
//                System.out.println("Dept"+data[8]);              
//                System.out.println("Blood"+data[9]);
                     
                alert("error","false","");
                alert("success","true","Teacher Successfully Added");
                TeacherColumn.clear(); 
//                System.out.println("Afeter Clear"+languages.toString());
        }catch(SQLException ee){
            System.out.println("The error is:"+ee);
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
                      query = "UPDATE student SET photo=? Where uid=?";
                    }else{ 
                      query = "UPDATE teacher SET photo=? Where uid=?";
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
            String query = "SELECT student.*, users.* FROM student,users  WHERE student.uid = users.uid AND users.uid =?";
            
            
            PreparedStatement ps=con.c.prepareStatement(query);
            ps.setString(1,id);
            rs  = ps.executeQuery();
            return rs;

        }
        public ResultSet findTeacher(String id) throws SQLException{

            conn con = new conn();
            String query = "SELECT teacher.*, users.* FROM teacher,users  WHERE teacher.uid = users.uid AND users.uid =?";
            
            
            PreparedStatement ps=con.c.prepareStatement(query);
            ps.setString(1,id);
            rs  = ps.executeQuery();
            return rs;

        }
    }
    public void  deleteStudent(String id){

            conn conn = new conn();
               try{		
                    String sql = "DELETE student, users FROM student INNER JOIN users ON student.uid = users.uid WHERE users.uid='"+id+"'";
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
                    String status = "active";
                    UpdateToken object = new UpdateToken(user,"status",status,sid.getText());
                    if(object.UpadetData()){
                        sstatusBtn.setText("Active");
                        sstatusBtn.setBackground(Color.GREEN);
                        sstatusBtn.setForeground(Color.BLACK);
                        alert("success","true","Status updated");
                    }else{
                        alert("error","true","Something going wrong with database,Try again!");
                    }
                }else{ 
                    String status = "inactive";
                    UpdateToken object = new UpdateToken(user,"status",status,sid.getText());
                    if(object.UpadetData()){
                        sstatusBtn.setText("Disable");
                        sstatusBtn.setBackground(Color.RED);
                        sstatusBtn.setForeground(Color.WHITE);
                        alert("success","true","Status updated");
                    }else{
                        alert("error","true","Something going wrong with database,Try again!");
                    }
                }
            }else if("teacher".equals(user)){
                if("Active".equals(mode)){ 
                    String status = "active";
                    UpdateToken object = new UpdateToken(user,"status",status,tid.getText());
                    if(object.UpadetData()){
                        sstatusBtn1.setText("Active");
                        sstatusBtn1.setBackground(Color.GREEN);
                        sstatusBtn1.setForeground(Color.BLACK);
                        alert("success","true","Status updated");
                    }else{
                        alert("error","true","Something going wrong with database,Try again!");
                    }
                }else{ 
                    String status = "inactive";
                    UpdateToken object = new UpdateToken(user,"status",status,tid.getText());
                    if(object.UpadetData()){
                        sstatusBtn1.setText("Disable");
                        sstatusBtn1.setBackground(Color.RED);
                        sstatusBtn1.setForeground(Color.WHITE);
                        alert("success","true","Status updated");
                    }else{
                        alert("error","true","Something going wrong with database,Try again!");
                    }
                }
            }
        
    }
    public String getfirstLeter_replace_some_speacial_charecter_and_word(String str)
    {
        
        
        
        boolean continueAdding = true;
        int wordCount = 0;
        String forLongString = "";
        String forShortString = "";
        // First remove and,& from string    
        if(str != null){ 
            str = str.replaceAll("and", "");
            str = str.replaceAll("&", "");
            
                    
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
    
    
    public boolean checkWordIsFoundOrnOT(String sentence,String words){
        // To break the sentence in words
        String []eachWords = sentence.split(" ");

        // To temporarily store each individual word
        for ( String temp : eachWords)
        {
            // Comparing the current word
            // with the word to be searched
            if (temp.compareTo(words) == 0)
            {
                //If match
                return false;
            }
        }
        
        // If not match
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
                    System.out.println("found"+rs.getString("email"));
                    dataFoundOrNot=true;
                    
                    do{
                        String name = rs.getString("name");
                        String id = rs.getString("uid");
                        String reg = rs.getString("reg");
                        String email = rs.getString("email");
                        String phone = rs.getString("phone");
                        String hall = rs.getString("hall");
                        String st[] = {name,id,reg,email,phone,hall};
                        table.addRow(st);
                    }while(rs.next());
                    
                    
                    if(dataFoundOrNot){
                        facultyAllStudentSession.getModel().setSelectedItem(ses);
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
    
    
    
        
    public boolean showFacultyTeacher(String fac,String dept){
        String depert;
        selectedFacultyName = fac;
        return false;
    } 
    
    
    
    
    
    
    
        public class func{
        public ResultSet find(String usernames, String identy) throws SQLException{
            
            conn cc = new conn();
            String query = "";
            
            if("student".equals(identy)){ 
                query = "SELECT student.*, users.* FROM student, users WHERE student.uid = users.uid AND users.uid = '"+usernames+"';";
            
            }else{
                   query = "SELECT teacher.*, users.* FROM teacher, users WHERE teacher.uid = users.uid AND users.uid = '"+usernames+"';";
            
            }
           
            
            PreparedStatement ps=cc.c.prepareStatement(query);
            rs  = ps.executeQuery(query); 
            
            return rs;

        }
    } 
    public void profileView(){
        System.out.println(logeduserid + identity);
        AdminDashboards.func object = new AdminDashboards.func();
        try { 
            //  recive func meathoad return query result                   
            rs = object.find(logeduserid,identity);
            
            if(rs.next()){
               if("student".equals(identity)){ 
                   
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
                        Image newImage1 = img1.getScaledInstance(studentSidebarProfilepic.getWidth(), studentSidebarProfilepic.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon image1 = new ImageIcon(newImage1);
                        studentSidebarProfilepic.setIcon(image1);
                        studentUpdateProfilePic.setIcon(image1);
                    }
                      
               }
               else{
                    if("admin".equals(identity)){         
                        labelForShowTeacherAdminTitlePic.setVisible(true);
                        adminSidebar.setVisible(true);
                    }else if("register".equals(identity)){
                        
                    }else if("controller".equals(identity)){
                        
                    }else if("din".equals(identity)){
                        labelForShowTeacherDinTitlePic.setVisible(true);
                        DinSidebar.setVisible(true);
                    }else{ 
                        labelForShowTeacherTitlePic.setVisible(true);
                    }
                        byte[] img = rs.getBytes("photo");
                        if(img != null){ 
                            ImageIcon MyImage1 = new ImageIcon(img);
                            Image img1 = MyImage1.getImage();
                            Image newImage1 = img1.getScaledInstance(teacherSidebarProfilepic.getWidth(), teacherSidebarProfilepic.getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon image1 = new ImageIcon(newImage1);
                            teacherSidebarProfilepic.setIcon(image1);
                            teacherUpdateProfilepic.setIcon(image1);
                        }
                        
                        teacherName.setText(rs.getString("name"));
                        teacherNid.setText(rs.getString("nid"));
                        teacherBlood.setText(rs.getString("blood"));
                        teacherDob.setText(rs.getString("dob"));
                        teacherUsername.setText(rs.getString("username"));
                        teacherPhone.setText(rs.getString("phone"));
                        teacherPassword.setText(rs.getString("password"));
                        teacherPassword.setEchoChar('*');
                        teacherEmail.setText(rs.getString("email"));
               }
            }
                      
        } catch (SQLException ex) {
        }
    
    
    }
    
    
    
        
    public void PasswordShowAndHide(boolean get){
        if(get==false){
            studentPassword.setEchoChar((char)0);
            adminPassword.setEchoChar((char)0);
            teacherPassword.setEchoChar((char)0);
            click=true;
        }else{
            studentPassword.setEchoChar('*');
            adminPassword.setEchoChar('*');
            teacherPassword.setEchoChar('*');
            click=false;
        }
    }
    
    public void findData_According_to_SingleDara(String table,String column,String data) throws SQLException{ 
  
        conn con = new conn();
        String query = null;
        if("teacher".equals(table)){
            if("faculty".equals(column)){ 
              query = "select * from teacher where fac='"+data+"' ";
            }else if("depertment".equals(column)){ 
              query = "select * from teacher where dept='"+data+"' ";
            }
           rs = con.s.executeQuery(query);
           
                    DefaultTableModel tables = (DefaultTableModel)allTeachersByFaculty.getModel();
                    for( int i = tables.getRowCount() - 1; i >= 0; i-- ){
                        tables.removeRow(i);
                    }

                    if(rs.next()){
                        
                                    
                        int count=0;
                        do{
                            facultyAllTeacher.addItem(rs.getString("dept"));
                            count++;
                            String name = rs.getString("name");
                            String id = rs.getString("uid");
                            String reg = rs.getString("reg");
                            String email = rs.getString("email");
                            String phone = rs.getString("phone");
                            String nid = rs.getString("nid");
                            String st[] = {name,id,reg,email,phone,nid};
                            tables.addRow(st);
                        }while(rs.next());
                            alert("success","true","Total "+count+" data found");
                            ProjectTab.setSelectedIndex(25);
                    }else{ 
                         alert("error","true","No Teacher found");
                    }
                    
        }else if("student".equals(table)){
//           query = "select * from student where fac='"+data+"' ";
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        teacherSideBarPanel = new javax.swing.JPanel();
        teacherSidebarProfilepic = new javax.swing.JLabel();
        teacherSidebarLogoutBtn = new javax.swing.JButton();
        teacherProfileViewBtn_ = new javax.swing.JButton();
        teacherDashboardBtn_ = new javax.swing.JButton();
        DinSidebar = new javax.swing.JPanel();
        adminStudentBtn_4 = new javax.swing.JButton();
        adminTeacherBtn_5 = new javax.swing.JButton();
        adminTransectionBtn_9 = new javax.swing.JButton();
        adminHallBtn_7 = new javax.swing.JButton();
        adminSidebar = new javax.swing.JPanel();
        adminStudentBtn_03 = new javax.swing.JButton();
        adminTeacherBtn_04 = new javax.swing.JButton();
        adminTransectionBtn_08 = new javax.swing.JButton();
        adminHallBtn_06 = new javax.swing.JButton();
        adminLibraryBtn_07 = new javax.swing.JButton();
        adminHealthCareBtn_09 = new javax.swing.JButton();
        adminCourseBtn_28 = new javax.swing.JButton();
        bg2 = new javax.swing.JLabel();
        StudentSideBarPanel = new javax.swing.JPanel();
        studentSidebarProfilepic = new javax.swing.JLabel();
        studentSidebarLogoutBtn = new javax.swing.JButton();
        studentProfileViewBtn_17 = new javax.swing.JButton();
        studentDashboardBtn_18 = new javax.swing.JButton();
        studentPaymentBtn_19 = new javax.swing.JButton();
        studentFacultyBtn_20 = new javax.swing.JButton();
        studentExamBtn_21 = new javax.swing.JButton();
        studentResultBtn_22 = new javax.swing.JButton();
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
        jLabel26 = new javax.swing.JLabel();
        serchIdField1 = new javax.swing.JTextField();
        searchStudent_17 = new javax.swing.JButton();
        jLabel120 = new javax.swing.JLabel();
        serchTeacherWithId1 = new javax.swing.JTextField();
        addNewStudent4 = new javax.swing.JButton();
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
        sendUpdateVerificationCode_14 = new javax.swing.JButton();
        adminDob = new javax.swing.JLabel();
        adminNid = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel61 = new javax.swing.JLabel();
        adminPassword = new javax.swing.JPasswordField();
        adminPassEye = new javax.swing.JButton();
        adminId = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        adminStudent_03 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        addStudentBtn_13 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        serchIdField = new javax.swing.JTextField();
        searchStudent_16 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cseSession_16 = new javax.swing.JComboBox<>();
        agriSession_16 = new javax.swing.JComboBox<>();
        dvmSession_16 = new javax.swing.JComboBox<>();
        bamSession_16 = new javax.swing.JComboBox<>();
        fishSession_16 = new javax.swing.JComboBox<>();
        nfsSession_16 = new javax.swing.JComboBox<>();
        esdmSession_16 = new javax.swing.JComboBox<>();
        llaSession_16 = new javax.swing.JComboBox<>();
        ahSession_16 = new javax.swing.JComboBox<>();
        llaSession_17 = new javax.swing.JComboBox<>();
        adminTeacher_04 = new javax.swing.JPanel();
        serchTeacherWithId = new javax.swing.JTextField();
        addNewStudent3 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        addStudentBtn2 = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jLabel160 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jLabel161 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jLabel162 = new javax.swing.JLabel();
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
        AccountVerify_14 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        vkey = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        accountverifyForUpdateProfile = new javax.swing.JButton();
        verifyUsername = new javax.swing.JTextField();
        adminFacultyStudent_15 = new javax.swing.JPanel();
        back_03 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        allStudentsByFaculty = new javax.swing.JTable();
        facultyAllStudentSession = new javax.swing.JComboBox<>();
        viewStudentfaculty_15 = new javax.swing.JButton();
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
        printbtn = new javax.swing.JButton();
        view3 = new javax.swing.JButton();
        jLabel102 = new javax.swing.JLabel();
        adminDob13 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        sstatusBtn = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        smname = new javax.swing.JTextField();
        saddr = new javax.swing.JTextField();
        sdob = new javax.swing.JTextField();
        sblood = new javax.swing.JTextField();
        sphone = new javax.swing.JTextField();
        snid = new javax.swing.JTextField();
        sname = new javax.swing.JTextField();
        sfname = new javax.swing.JTextField();
        sid = new javax.swing.JTextField();
        sreg = new javax.swing.JTextField();
        semail = new javax.swing.JTextField();
        ssession = new javax.swing.JTextField();
        sfac = new javax.swing.JTextField();
        shall = new javax.swing.JTextField();
        ssem = new javax.swing.JTextField();
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
        adminTeacherAdd_23 = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        ta_name = new javax.swing.JTextField();
        ta_addr = new javax.swing.JTextField();
        ta_phone = new javax.swing.JTextField();
        ta_dob = new com.toedter.calendar.JDateChooser();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        ta_id = new javax.swing.JTextField();
        ta_reg = new javax.swing.JTextField();
        ta_dept = new javax.swing.JComboBox<>();
        ta_blood = new javax.swing.JComboBox<>();
        ta_fac = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        ta_nid = new javax.swing.JTextField();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        teacherProfileView_24 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        labelForShowTeacherAdminTitlePic = new javax.swing.JLabel();
        labelForShowTeacherDinTitlePic = new javax.swing.JLabel();
        labelForShowTeacherTitlePic = new javax.swing.JLabel();
        teacherUpdateProfilepic = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        teacherName = new javax.swing.JLabel();
        file2 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        teacherEmail = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        teacherBlood = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        teacherUsername = new javax.swing.JTextField();
        teacherPhone = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        teacherDob = new javax.swing.JLabel();
        teacherNid = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel156 = new javax.swing.JLabel();
        teacherPassword = new javax.swing.JPasswordField();
        studentPassEye1 = new javax.swing.JButton();
        teacherDashboard_25 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        totalStudent = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        totalStudent1 = new javax.swing.JLabel();
        adminFacultyTeacher_26 = new javax.swing.JPanel();
        back_4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        allTeachersByFaculty = new javax.swing.JTable();
        facultyAllTeacher = new javax.swing.JComboBox<>();
        viewStudentfaculty_16 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        facultyAllTeacherSession1 = new javax.swing.JComboBox<>();
        adminTeacherDetails_27 = new javax.swing.JPanel();
        jLabel195 = new javax.swing.JLabel();
        tDetailsProfilePic = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel200 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        adminDob25 = new javax.swing.JLabel();
        adminDob27 = new javax.swing.JLabel();
        adminDob28 = new javax.swing.JLabel();
        adminDob29 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        adminDob30 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        adminNid6 = new javax.swing.JLabel();
        adminNid7 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        adminBlood6 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        adminDob31 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();
        adminDob33 = new javax.swing.JLabel();
        jLabel217 = new javax.swing.JLabel();
        adminDob34 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        view5 = new javax.swing.JButton();
        jLabel226 = new javax.swing.JLabel();
        adminDob36 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        sstatusBtn1 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        adminDob35 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel218 = new javax.swing.JLabel();
        printbtn3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        roleController = new javax.swing.JCheckBox();
        roleDin = new javax.swing.JCheckBox();
        roleRegister = new javax.swing.JCheckBox();
        roleCoAdmin = new javax.swing.JCheckBox();
        adminProfileBtn_02 = new javax.swing.JButton();
        facultyAllTeacher2 = new javax.swing.JComboBox<>();
        facultyAllTeacher1 = new javax.swing.JComboBox<>();
        storeFac = new javax.swing.JLabel();
        storeDep = new javax.swing.JLabel();
        tnid = new javax.swing.JTextField();
        taddr = new javax.swing.JTextField();
        tdob = new javax.swing.JTextField();
        tblood = new javax.swing.JTextField();
        tphone = new javax.swing.JTextField();
        tname = new javax.swing.JTextField();
        tid = new javax.swing.JTextField();
        treg = new javax.swing.JTextField();
        temail = new javax.swing.JTextField();
        tfac = new javax.swing.JTextField();
        tdept = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        course_28 = new javax.swing.JPanel();
        cocode = new javax.swing.JTextField();
        jLabel129 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        coname = new javax.swing.JTextField();
        jLabel163 = new javax.swing.JLabel();
        cohour = new javax.swing.JTextField();
        cofac = new javax.swing.JComboBox<>();
        jLabel166 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        courseTable = new javax.swing.JTable();
        addStudentBtn_14 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        teacherSideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        teacherSideBarPanel.add(teacherSidebarProfilepic, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 200, 180));

        teacherSidebarLogoutBtn.setBackground(new java.awt.Color(14, 0, 82));
        teacherSidebarLogoutBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        teacherSidebarLogoutBtn.setForeground(new java.awt.Color(255, 255, 255));
        teacherSidebarLogoutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/logout.png"))); // NOI18N
        teacherSidebarLogoutBtn.setText("Logout");
        teacherSidebarLogoutBtn.setBorder(null);
        teacherSidebarLogoutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherSidebarLogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherSidebarLogoutBtnActionPerformed(evt);
            }
        });
        teacherSideBarPanel.add(teacherSidebarLogoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, 40));

        teacherProfileViewBtn_.setBackground(new java.awt.Color(14, 0, 82));
        teacherProfileViewBtn_.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        teacherProfileViewBtn_.setForeground(new java.awt.Color(255, 255, 255));
        teacherProfileViewBtn_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        teacherProfileViewBtn_.setText("View");
        teacherProfileViewBtn_.setBorder(null);
        teacherProfileViewBtn_.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileViewBtn_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherProfileViewBtn_ActionPerformed(evt);
            }
        });
        teacherSideBarPanel.add(teacherProfileViewBtn_, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

        teacherDashboardBtn_.setBackground(new java.awt.Color(14, 0, 82));
        teacherDashboardBtn_.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        teacherDashboardBtn_.setForeground(new java.awt.Color(255, 255, 255));
        teacherDashboardBtn_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/home.png"))); // NOI18N
        teacherDashboardBtn_.setText("  Dashboard");
        teacherDashboardBtn_.setBorder(null);
        teacherDashboardBtn_.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherDashboardBtn_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                teacherDashboardBtn_MouseClicked(evt);
            }
        });
        teacherDashboardBtn_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherDashboardBtn_ActionPerformed(evt);
            }
        });
        teacherSideBarPanel.add(teacherDashboardBtn_, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 300, 40));

        DinSidebar.setBackground(new java.awt.Color(0, 5, 42));
        DinSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        adminStudentBtn_4.setBackground(new java.awt.Color(14, 0, 82));
        adminStudentBtn_4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminStudentBtn_4.setForeground(new java.awt.Color(255, 255, 255));
        adminStudentBtn_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/student.png"))); // NOI18N
        adminStudentBtn_4.setText("Student");
        adminStudentBtn_4.setBorder(null);
        adminStudentBtn_4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentBtn_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminStudentBtn_4ActionPerformed(evt);
            }
        });
        DinSidebar.add(adminStudentBtn_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, 40));

        adminTeacherBtn_5.setBackground(new java.awt.Color(14, 0, 82));
        adminTeacherBtn_5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTeacherBtn_5.setForeground(new java.awt.Color(255, 255, 255));
        adminTeacherBtn_5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/teacher.png"))); // NOI18N
        adminTeacherBtn_5.setText("Teacher");
        adminTeacherBtn_5.setBorder(null);
        adminTeacherBtn_5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherBtn_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTeacherBtn_5ActionPerformed(evt);
            }
        });
        DinSidebar.add(adminTeacherBtn_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 140, 40));

        adminTransectionBtn_9.setBackground(new java.awt.Color(14, 0, 82));
        adminTransectionBtn_9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTransectionBtn_9.setForeground(new java.awt.Color(255, 255, 255));
        adminTransectionBtn_9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        adminTransectionBtn_9.setText("Transection");
        adminTransectionBtn_9.setBorder(null);
        adminTransectionBtn_9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTransectionBtn_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTransectionBtn_9ActionPerformed(evt);
            }
        });
        DinSidebar.add(adminTransectionBtn_9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, 40));

        adminHallBtn_7.setBackground(new java.awt.Color(14, 0, 82));
        adminHallBtn_7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminHallBtn_7.setForeground(new java.awt.Color(255, 255, 255));
        adminHallBtn_7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/hall.png"))); // NOI18N
        adminHallBtn_7.setText("Staff");
        adminHallBtn_7.setBorder(null);
        adminHallBtn_7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminHallBtn_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminHallBtn_7ActionPerformed(evt);
            }
        });
        DinSidebar.add(adminHallBtn_7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 140, 40));

        teacherSideBarPanel.add(DinSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 340, 130));

        adminSidebar.setBackground(new java.awt.Color(0, 5, 42));
        adminSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        adminStudentBtn_03.setBackground(new java.awt.Color(14, 0, 82));
        adminStudentBtn_03.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminStudentBtn_03.setForeground(new java.awt.Color(255, 255, 255));
        adminStudentBtn_03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/student.png"))); // NOI18N
        adminStudentBtn_03.setText("Student");
        adminStudentBtn_03.setBorder(null);
        adminStudentBtn_03.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminStudentBtn_03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminStudentBtn_03ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminStudentBtn_03, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, 40));

        adminTeacherBtn_04.setBackground(new java.awt.Color(14, 0, 82));
        adminTeacherBtn_04.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTeacherBtn_04.setForeground(new java.awt.Color(255, 255, 255));
        adminTeacherBtn_04.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/teacher.png"))); // NOI18N
        adminTeacherBtn_04.setText("Teacher");
        adminTeacherBtn_04.setBorder(null);
        adminTeacherBtn_04.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherBtn_04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTeacherBtn_04ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminTeacherBtn_04, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 140, 40));

        adminTransectionBtn_08.setBackground(new java.awt.Color(14, 0, 82));
        adminTransectionBtn_08.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminTransectionBtn_08.setForeground(new java.awt.Color(255, 255, 255));
        adminTransectionBtn_08.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        adminTransectionBtn_08.setText("Transection");
        adminTransectionBtn_08.setBorder(null);
        adminTransectionBtn_08.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTransectionBtn_08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminTransectionBtn_08ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminTransectionBtn_08, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, 40));

        adminHallBtn_06.setBackground(new java.awt.Color(14, 0, 82));
        adminHallBtn_06.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminHallBtn_06.setForeground(new java.awt.Color(255, 255, 255));
        adminHallBtn_06.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/hall.png"))); // NOI18N
        adminHallBtn_06.setText(" Hall");
        adminHallBtn_06.setBorder(null);
        adminHallBtn_06.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminHallBtn_06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminHallBtn_06ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminHallBtn_06, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 140, 40));

        adminLibraryBtn_07.setBackground(new java.awt.Color(14, 0, 82));
        adminLibraryBtn_07.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminLibraryBtn_07.setForeground(new java.awt.Color(255, 255, 255));
        adminLibraryBtn_07.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/library.png"))); // NOI18N
        adminLibraryBtn_07.setText("Others");
        adminLibraryBtn_07.setBorder(null);
        adminLibraryBtn_07.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminLibraryBtn_07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminLibraryBtn_07ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminLibraryBtn_07, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 140, 40));

        adminHealthCareBtn_09.setBackground(new java.awt.Color(14, 0, 82));
        adminHealthCareBtn_09.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminHealthCareBtn_09.setForeground(new java.awt.Color(255, 255, 255));
        adminHealthCareBtn_09.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/doctor.png"))); // NOI18N
        adminHealthCareBtn_09.setText("Health care");
        adminHealthCareBtn_09.setBorder(null);
        adminHealthCareBtn_09.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminHealthCareBtn_09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminHealthCareBtn_09ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminHealthCareBtn_09, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 140, 40));

        adminCourseBtn_28.setBackground(new java.awt.Color(14, 0, 82));
        adminCourseBtn_28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminCourseBtn_28.setForeground(new java.awt.Color(255, 255, 255));
        adminCourseBtn_28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/library.png"))); // NOI18N
        adminCourseBtn_28.setText("Library");
        adminCourseBtn_28.setBorder(null);
        adminCourseBtn_28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminCourseBtn_28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminCourseBtn_28ActionPerformed(evt);
            }
        });
        adminSidebar.add(adminCourseBtn_28, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 140, 40));

        teacherSideBarPanel.add(adminSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 330, 250));

        bg2.setBackground(new java.awt.Color(0, 5, 42));
        bg2.setOpaque(true);
        teacherSideBarPanel.add(bg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 855));

        getContentPane().add(teacherSideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 860));

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

        studentProfileViewBtn_17.setBackground(new java.awt.Color(14, 0, 82));
        studentProfileViewBtn_17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentProfileViewBtn_17.setForeground(new java.awt.Color(255, 255, 255));
        studentProfileViewBtn_17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        studentProfileViewBtn_17.setText("View");
        studentProfileViewBtn_17.setBorder(null);
        studentProfileViewBtn_17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentProfileViewBtn_17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentProfileViewBtn_17ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentProfileViewBtn_17, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 120, 40));

        studentDashboardBtn_18.setBackground(new java.awt.Color(14, 0, 82));
        studentDashboardBtn_18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentDashboardBtn_18.setForeground(new java.awt.Color(255, 255, 255));
        studentDashboardBtn_18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/home.png"))); // NOI18N
        studentDashboardBtn_18.setText("Dashboard");
        studentDashboardBtn_18.setBorder(null);
        studentDashboardBtn_18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentDashboardBtn_18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentDashboardBtn_18MouseClicked(evt);
            }
        });
        studentDashboardBtn_18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentDashboardBtn_18ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentDashboardBtn_18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, 40));

        studentPaymentBtn_19.setBackground(new java.awt.Color(14, 0, 82));
        studentPaymentBtn_19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentPaymentBtn_19.setForeground(new java.awt.Color(255, 255, 255));
        studentPaymentBtn_19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/money.png"))); // NOI18N
        studentPaymentBtn_19.setText("Payment");
        studentPaymentBtn_19.setBorder(null);
        studentPaymentBtn_19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentPaymentBtn_19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPaymentBtn_19ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentPaymentBtn_19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 40));

        studentFacultyBtn_20.setBackground(new java.awt.Color(14, 0, 82));
        studentFacultyBtn_20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentFacultyBtn_20.setForeground(new java.awt.Color(255, 255, 255));
        studentFacultyBtn_20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/faculty.png"))); // NOI18N
        studentFacultyBtn_20.setText("Faculty");
        studentFacultyBtn_20.setBorder(null);
        studentFacultyBtn_20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentFacultyBtn_20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentFacultyBtn_20ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentFacultyBtn_20, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 140, 40));

        studentExamBtn_21.setBackground(new java.awt.Color(14, 0, 82));
        studentExamBtn_21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentExamBtn_21.setForeground(new java.awt.Color(255, 255, 255));
        studentExamBtn_21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/exam.png"))); // NOI18N
        studentExamBtn_21.setText("Exam");
        studentExamBtn_21.setBorder(null);
        studentExamBtn_21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentExamBtn_21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentExamBtn_21ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentExamBtn_21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 140, 40));

        studentResultBtn_22.setBackground(new java.awt.Color(14, 0, 82));
        studentResultBtn_22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        studentResultBtn_22.setForeground(new java.awt.Color(255, 255, 255));
        studentResultBtn_22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/result.png"))); // NOI18N
        studentResultBtn_22.setText("Result");
        studentResultBtn_22.setBorder(null);
        studentResultBtn_22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentResultBtn_22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentResultBtn_22ActionPerformed(evt);
            }
        });
        StudentSideBarPanel.add(studentResultBtn_22, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 140, 40));

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
        adminDashboard_01.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminDashboard_01ComponentShown(evt);
            }
        });
        adminDashboard_01.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/student.png"))); // NOI18N
        adminDashboard_01.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 400, 60));

        serchIdField1.setBackground(new java.awt.Color(255, 153, 0));
        serchIdField1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        serchIdField1.setText("   Search with student id");
        serchIdField1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        serchIdField1.setMargin(new java.awt.Insets(2, 20, 2, 2));
        serchIdField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchIdField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchIdField1FocusLost(evt);
            }
        });
        serchIdField1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                serchIdField1ComponentShown(evt);
            }
        });
        serchIdField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchIdField1ActionPerformed(evt);
            }
        });
        adminDashboard_01.add(serchIdField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 310, 50));

        searchStudent_17.setBackground(new java.awt.Color(14, 0, 82));
        searchStudent_17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        searchStudent_17.setActionCommand("Add Student");
        searchStudent_17.setBorder(null);
        searchStudent_17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchStudent_17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStudent_17ActionPerformed(evt);
            }
        });
        adminDashboard_01.add(searchStudent_17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, 50, 50));

        jLabel120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        adminDashboard_01.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 240, 390, 60));

        serchTeacherWithId1.setBackground(new java.awt.Color(255, 153, 0));
        serchTeacherWithId1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        serchTeacherWithId1.setText("   Search with Teacher id");
        serchTeacherWithId1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        serchTeacherWithId1.setMargin(new java.awt.Insets(2, 20, 2, 2));
        serchTeacherWithId1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchTeacherWithId1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchTeacherWithId1FocusLost(evt);
            }
        });
        serchTeacherWithId1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                serchTeacherWithId1ComponentShown(evt);
            }
        });
        serchTeacherWithId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchTeacherWithId1ActionPerformed(evt);
            }
        });
        adminDashboard_01.add(serchTeacherWithId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, 310, 50));

        addNewStudent4.setBackground(new java.awt.Color(14, 0, 82));
        addNewStudent4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addNewStudent4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        addNewStudent4.setActionCommand("Add Student");
        addNewStudent4.setBorder(null);
        addNewStudent4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewStudent4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStudent4ActionPerformed(evt);
            }
        });
        adminDashboard_01.add(addNewStudent4, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 300, 50, 50));

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
        adminProfile_02.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 30, 30));

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/dob.png"))); // NOI18N
        jLabel67.setText("jLabel56");
        jLabel67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 30));

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
        adminProfile_02.add(adminBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 300, -1));
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

        sendUpdateVerificationCode_14.setBackground(new java.awt.Color(14, 0, 82));
        sendUpdateVerificationCode_14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sendUpdateVerificationCode_14.setForeground(new java.awt.Color(255, 255, 255));
        sendUpdateVerificationCode_14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/send.png"))); // NOI18N
        sendUpdateVerificationCode_14.setText("Update");
        sendUpdateVerificationCode_14.setBorder(null);
        sendUpdateVerificationCode_14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendUpdateVerificationCode_14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendUpdateVerificationCode_14ActionPerformed(evt);
            }
        });
        adminProfile_02.add(sendUpdateVerificationCode_14, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 110, 50));

        adminDob.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob.setForeground(new java.awt.Color(255, 204, 0));
        adminDob.setText("Name");
        adminProfile_02.add(adminDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 310, -1));

        adminNid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid.setForeground(new java.awt.Color(255, 204, 0));
        adminNid.setText("Nid");
        adminProfile_02.add(adminNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 260, 30));
        adminProfile_02.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 300, 10));

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/blood.png"))); // NOI18N
        jLabel61.setText("jLabel56");
        jLabel61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 30, 30));

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

        adminId.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminId.setForeground(new java.awt.Color(255, 204, 0));
        adminId.setText("Name");
        adminProfile_02.add(adminId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 310, -1));

        jLabel158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/uid.png"))); // NOI18N
        jLabel158.setText("jLabel56");
        jLabel158.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfile_02.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 30, 30));

        ProjectTab.addTab("tab2", adminProfile_02);

        adminStudent_03.setBackground(new java.awt.Color(0, 5, 42));
        adminStudent_03.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminStudent_03ComponentShown(evt);
            }
        });
        adminStudent_03.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/student.png"))); // NOI18N
        adminStudent_03.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 340, 410, 60));

        addStudentBtn_13.setBackground(new java.awt.Color(14, 0, 82));
        addStudentBtn_13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addStudentBtn_13.setForeground(new java.awt.Color(255, 255, 255));
        addStudentBtn_13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/addNew.png"))); // NOI18N
        addStudentBtn_13.setText(" Add New");
        addStudentBtn_13.setBorder(null);
        addStudentBtn_13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudentBtn_13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentBtn_13ActionPerformed(evt);
            }
        });
        adminStudent_03.add(addStudentBtn_13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 170, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        adminStudent_03.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 390, 270));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 160, 130));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        adminStudent_03.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 160, 200, 200));

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
        adminStudent_03.add(serchIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, 330, 50));

        searchStudent_16.setBackground(new java.awt.Color(14, 0, 82));
        searchStudent_16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/search.png"))); // NOI18N
        searchStudent_16.setActionCommand("Add Student");
        searchStudent_16.setBorder(null);
        searchStudent_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchStudent_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStudent_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(searchStudent_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 410, 50, 50));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 160, 130));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 160, 130));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, 160, 130));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 480, 160, 130));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 480, 160, 130));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 160, 130));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 660, 160, 130));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        adminStudent_03.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 660, 160, 130));

        cseSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cseSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cse Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        cseSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cseSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cseSession_16ItemStateChanged(evt);
            }
        });
        cseSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cseSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(cseSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 160, 40));

        agriSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        agriSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agri Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        agriSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        agriSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                agriSession_16ItemStateChanged(evt);
            }
        });
        agriSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agriSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(agriSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 610, 160, 40));

        dvmSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        dvmSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dvm Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-20211" }));
        dvmSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dvmSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dvmSession_16ItemStateChanged(evt);
            }
        });
        dvmSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dvmSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(dvmSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 610, 160, 40));

        bamSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        bamSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bam Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021" }));
        bamSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bamSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bamSession_16ItemStateChanged(evt);
            }
        });
        bamSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bamSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(bamSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 610, 160, 40));

        fishSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        fishSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fish Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021" }));
        fishSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fishSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fishSession_16ItemStateChanged(evt);
            }
        });
        fishSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fishSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(fishSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 610, 160, 40));

        nfsSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        nfsSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nfs Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021" }));
        nfsSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nfsSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                nfsSession_16ItemStateChanged(evt);
            }
        });
        nfsSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nfsSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(nfsSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 610, 160, 40));

        esdmSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        esdmSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disaster Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        esdmSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        esdmSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                esdmSession_16ItemStateChanged(evt);
            }
        });
        esdmSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                esdmSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(esdmSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 790, 160, 40));

        llaSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        llaSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lla Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        llaSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        llaSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                llaSession_16ItemStateChanged(evt);
            }
        });
        llaSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                llaSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(llaSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 790, 160, 40));

        ahSession_16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ahSession_16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ah Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021" }));
        ahSession_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ahSession_16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ahSession_16ItemStateChanged(evt);
            }
        });
        ahSession_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ahSession_16ActionPerformed(evt);
            }
        });
        adminStudent_03.add(ahSession_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 730, 160, 40));

        llaSession_17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        llaSession_17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lla Session", "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        llaSession_17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        llaSession_17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                llaSession_17ItemStateChanged(evt);
            }
        });
        llaSession_17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                llaSession_17ActionPerformed(evt);
            }
        });
        adminStudent_03.add(llaSession_17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 790, 160, 40));

        ProjectTab.addTab("tab3", adminStudent_03);

        adminTeacher_04.setBackground(new java.awt.Color(0, 5, 42));
        adminTeacher_04.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacher_04.setPreferredSize(new java.awt.Dimension(1100, 770));
        adminTeacher_04.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminTeacher_04ComponentShown(evt);
            }
        });
        adminTeacher_04.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        serchTeacherWithId.setBackground(new java.awt.Color(255, 153, 0));
        serchTeacherWithId.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        serchTeacherWithId.setText(" Search with Teacher id");
        serchTeacherWithId.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        serchTeacherWithId.setMargin(new java.awt.Insets(2, 20, 2, 2));
        serchTeacherWithId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serchTeacherWithIdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                serchTeacherWithIdFocusLost(evt);
            }
        });
        serchTeacherWithId.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                serchTeacherWithIdComponentShown(evt);
            }
        });
        serchTeacherWithId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchTeacherWithIdActionPerformed(evt);
            }
        });
        adminTeacher_04.add(serchTeacherWithId, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 330, 50));

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

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminTeacher.png"))); // NOI18N
        adminTeacher_04.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 350, 350));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        adminTeacher_04.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 270, 390, 80));

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
        adminTeacher_04.add(addStudentBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 220, 50));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel57.setText("jLabel8");
        adminTeacher_04.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 200, 200));

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/cse.png"))); // NOI18N
        jButton11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 160, 130));

        jLabel1.setBackground(new java.awt.Color(255, 153, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CSE");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 570, 160, 30));

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ag.png"))); // NOI18N
        jButton12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 160, 130));

        jLabel2.setBackground(new java.awt.Color(255, 153, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ariculture");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 570, 160, 30));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/dvm.png"))); // NOI18N
        jButton13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 440, 160, 130));

        jLabel18.setBackground(new java.awt.Color(255, 153, 0));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("DVM");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel18.setOpaque(true);
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 570, 160, 30));

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/bba.png"))); // NOI18N
        jButton14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, 160, 130));

        jLabel19.setBackground(new java.awt.Color(255, 153, 0));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("BAM");
        jLabel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel19.setOpaque(true);
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 570, 160, 30));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/fish.png"))); // NOI18N
        jButton15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 440, 160, 130));

        jLabel20.setBackground(new java.awt.Color(255, 153, 0));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Fisherise");
        jLabel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel20.setOpaque(true);
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 570, 160, 30));

        jLabel21.setBackground(new java.awt.Color(255, 153, 0));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("NFS");
        jLabel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel21.setOpaque(true);
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 160, 30));

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/nfs.png"))); // NOI18N
        jButton16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 440, 160, 130));

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/disster.png"))); // NOI18N
        jButton17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacher_04.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 620, 160, 130));

        jLabel160.setBackground(new java.awt.Color(255, 153, 0));
        jLabel160.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel160.setText("ESDM");
        jLabel160.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel160.setOpaque(true);
        jLabel160.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel160MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 750, 160, 30));

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/land.png"))); // NOI18N
        jButton18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 620, 160, 130));

        jLabel161.setBackground(new java.awt.Color(255, 153, 0));
        jLabel161.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel161.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel161.setText("LLAM");
        jLabel161.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel161.setOpaque(true);
        jLabel161.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel161MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 750, 160, 30));

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Faculty/Ah.png"))); // NOI18N
        jButton19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacher_04.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 620, 160, 130));

        jLabel162.setBackground(new java.awt.Color(255, 153, 0));
        jLabel162.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel162.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel162.setText("AH");
        jLabel162.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel162.setOpaque(true);
        jLabel162.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel162MouseClicked(evt);
            }
        });
        adminTeacher_04.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 750, 160, 30));

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
        adminStudentAdd_13.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminStudentAdd_13ComponentShown(evt);
            }
        });
        adminStudentAdd_13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/addStudent.png"))); // NOI18N
        adminStudentAdd_13.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 470, 80));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminStudentImage1.png"))); // NOI18N
        jLabel30.setText("jLabel5");
        adminStudentAdd_13.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 390, 260));

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

        ProjectTab.addTab("tab14", adminStudentAdd_13);

        AccountVerify_14.setBackground(new java.awt.Color(0, 5, 42));
        AccountVerify_14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/verifyAccount.png"))); // NOI18N
        AccountVerify_14.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 580, 90));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel58.setText("jLabel8");
        AccountVerify_14.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 200, 200));

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
        AccountVerify_14.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 560, 100, 45));

        vkey.setBackground(new java.awt.Color(0, 5, 42));
        vkey.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        vkey.setForeground(new java.awt.Color(255, 255, 255));
        vkey.setBorder(null);
        AccountVerify_14.add(vkey, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, 290, 50));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        AccountVerify_14.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 30, 40));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        AccountVerify_14.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 410, 20));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Code :");
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        AccountVerify_14.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 490, 70, 40));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        AccountVerify_14.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, 40, 40));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        AccountVerify_14.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, 410, 10));

        accountverifyForUpdateProfile.setBackground(new java.awt.Color(14, 0, 82));
        accountverifyForUpdateProfile.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        accountverifyForUpdateProfile.setForeground(new java.awt.Color(255, 255, 255));
        accountverifyForUpdateProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/login.png"))); // NOI18N
        accountverifyForUpdateProfile.setText("Verify");
        accountverifyForUpdateProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accountverifyForUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountverifyForUpdateProfileActionPerformed(evt);
            }
        });
        AccountVerify_14.add(accountverifyForUpdateProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, 120, 47));

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
        AccountVerify_14.add(verifyUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 350, 40));

        ProjectTab.addTab("tab15", AccountVerify_14);

        adminFacultyStudent_15.setBackground(new java.awt.Color(0, 5, 42));
        adminFacultyStudent_15.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminFacultyStudent_15ComponentShown(evt);
            }
        });
        adminFacultyStudent_15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back_03.setBackground(new java.awt.Color(14, 0, 82));
        back_03.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        back_03.setForeground(new java.awt.Color(255, 255, 255));
        back_03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        back_03.setText("Back");
        back_03.setBorder(null);
        back_03.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        back_03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_03ActionPerformed(evt);
            }
        });
        adminFacultyStudent_15.add(back_03, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 120, 48));

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
            allStudentsByFaculty.getColumnModel().getColumn(4).setHeaderValue("phone");
        }

        adminFacultyStudent_15.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 1050, 520));

        facultyAllStudentSession.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facultyAllStudentSession.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2015-2016", "2016-2017", "2017-2018", "2018-2019", "2019-2020", "2020-2021", " " }));
        facultyAllStudentSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facultyAllStudentSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facultyAllStudentSessionItemStateChanged(evt);
            }
        });
        facultyAllStudentSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyAllStudentSessionActionPerformed(evt);
            }
        });
        adminFacultyStudent_15.add(facultyAllStudentSession, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, 240, 48));

        viewStudentfaculty_15.setBackground(new java.awt.Color(14, 0, 82));
        viewStudentfaculty_15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        viewStudentfaculty_15.setForeground(new java.awt.Color(255, 255, 255));
        viewStudentfaculty_15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        viewStudentfaculty_15.setText("View");
        viewStudentfaculty_15.setBorder(null);
        viewStudentfaculty_15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewStudentfaculty_15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStudentfaculty_15ActionPerformed(evt);
            }
        });
        adminFacultyStudent_15.add(viewStudentfaculty_15, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 190, 120, 48));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        adminFacultyStudent_15.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 190, -1, 48));

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
        adminStudentDetails_16.add(view3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 40));

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

        smname.setBackground(new java.awt.Color(0, 5, 42));
        smname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        smname.setForeground(new java.awt.Color(255, 255, 255));
        smname.setText("hjhhjhjh");
        smname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        smname.setMargin(new java.awt.Insets(2, 10, 2, 2));
        adminStudentDetails_16.add(smname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 240, 40));

        saddr.setBackground(new java.awt.Color(0, 5, 42));
        saddr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saddr.setForeground(new java.awt.Color(255, 255, 255));
        saddr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(saddr, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 240, 40));

        sdob.setBackground(new java.awt.Color(0, 5, 42));
        sdob.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sdob.setForeground(new java.awt.Color(255, 255, 255));
        sdob.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(sdob, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 560, 240, 40));

        sblood.setBackground(new java.awt.Color(0, 5, 42));
        sblood.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sblood.setForeground(new java.awt.Color(255, 255, 255));
        sblood.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sblood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbloodActionPerformed(evt);
            }
        });
        adminStudentDetails_16.add(sblood, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 610, 240, 40));

        sphone.setBackground(new java.awt.Color(0, 5, 42));
        sphone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sphone.setForeground(new java.awt.Color(255, 255, 255));
        sphone.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(sphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 660, 240, 40));

        snid.setBackground(new java.awt.Color(0, 5, 42));
        snid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        snid.setForeground(new java.awt.Color(255, 255, 255));
        snid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(snid, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 710, 240, 40));

        sname.setBackground(new java.awt.Color(0, 5, 42));
        sname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sname.setForeground(new java.awt.Color(255, 255, 255));
        sname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sname.setMargin(new java.awt.Insets(2, 10, 2, 2));
        adminStudentDetails_16.add(sname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 240, 40));

        sfname.setBackground(new java.awt.Color(0, 5, 42));
        sfname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfname.setForeground(new java.awt.Color(255, 255, 255));
        sfname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sfname.setMargin(new java.awt.Insets(2, 10, 2, 2));
        adminStudentDetails_16.add(sfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 240, 40));

        sid.setBackground(new java.awt.Color(0, 5, 42));
        sid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sid.setForeground(new java.awt.Color(255, 255, 255));
        sid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(sid, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 360, 240, 40));

        sreg.setBackground(new java.awt.Color(0, 5, 42));
        sreg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sreg.setForeground(new java.awt.Color(255, 255, 255));
        sreg.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(sreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 410, 240, 40));

        semail.setBackground(new java.awt.Color(0, 5, 42));
        semail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        semail.setForeground(new java.awt.Color(255, 255, 255));
        semail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(semail, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 460, 240, 40));

        ssession.setBackground(new java.awt.Color(0, 5, 42));
        ssession.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssession.setForeground(new java.awt.Color(255, 255, 255));
        ssession.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(ssession, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 510, 240, 40));

        sfac.setBackground(new java.awt.Color(0, 5, 42));
        sfac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sfac.setForeground(new java.awt.Color(255, 255, 255));
        sfac.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(sfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 560, 240, 40));

        shall.setBackground(new java.awt.Color(0, 5, 42));
        shall.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shall.setForeground(new java.awt.Color(255, 255, 255));
        shall.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        shall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shallActionPerformed(evt);
            }
        });
        adminStudentDetails_16.add(shall, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 610, 240, 40));

        ssem.setBackground(new java.awt.Color(0, 5, 42));
        ssem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ssem.setForeground(new java.awt.Color(255, 255, 255));
        ssem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminStudentDetails_16.add(ssem, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 660, 240, 40));

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

        adminTeacherAdd_23.setBackground(new java.awt.Color(0, 5, 42));
        adminTeacherAdd_23.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminTeacherAdd_23ComponentShown(evt);
            }
        });
        adminTeacherAdd_23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/addTeacher.png"))); // NOI18N
        adminTeacherAdd_23.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 470, 80));

        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/adminTeacher.png"))); // NOI18N
        jLabel114.setText("jLabel5");
        adminTeacherAdd_23.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, 390, 300));

        jLabel116.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setText(":");
        adminTeacherAdd_23.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 10, -1));

        jLabel130.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setText(":");
        adminTeacherAdd_23.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, 10, 40));

        jLabel131.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(255, 255, 255));
        jLabel131.setText(":");
        adminTeacherAdd_23.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 460, 10, 30));

        jLabel132.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setText(":");
        adminTeacherAdd_23.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 510, 10, 40));

        jLabel133.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(255, 255, 255));
        jLabel133.setText("Birth Date");
        adminTeacherAdd_23.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, 90, 40));

        jLabel134.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(255, 255, 255));
        jLabel134.setText("Phone");
        adminTeacherAdd_23.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, 70, 30));

        jLabel135.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setText("Address");
        adminTeacherAdd_23.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 410, 80, 40));

        jLabel138.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(255, 255, 255));
        jLabel138.setText("Name");
        adminTeacherAdd_23.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 60, 40));

        ta_name.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_name.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_nameActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(ta_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, 300, 40));

        ta_addr.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_addr.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_addr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_addrActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(ta_addr, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 300, 40));

        ta_phone.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ta_phone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_phoneActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(ta_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 460, 300, 40));

        ta_dob.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_dob.setDateFormatString("dd-MM-yyyy");
        ta_dob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ta_dobMouseClicked(evt);
            }
        });
        ta_dob.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ta_dobPropertyChange(evt);
            }
        });
        adminTeacherAdd_23.add(ta_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 510, 300, 40));

        jLabel139.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setText(":");
        adminTeacherAdd_23.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 570, 20, -1));

        jLabel140.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setText(":");
        adminTeacherAdd_23.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 620, 20, -1));

        jLabel141.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setText(":");
        adminTeacherAdd_23.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 560, 20, 40));

        jLabel142.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setText(":");
        adminTeacherAdd_23.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 610, 20, 30));

        jLabel143.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setText(":");
        adminTeacherAdd_23.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, 20, 40));

        jLabel145.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("Faculty");
        adminTeacherAdd_23.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 510, 60, 40));

        jLabel146.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("Blood");
        adminTeacherAdd_23.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 610, 50, 30));

        jLabel147.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setText("Dept");
        adminTeacherAdd_23.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 560, 50, 40));

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setText("Reg");
        adminTeacherAdd_23.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 610, 40, 40));

        jLabel149.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(255, 255, 255));
        jLabel149.setText("Id");
        adminTeacherAdd_23.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 560, 30, 40));

        ta_id.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_id.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_id.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ta_idFocusLost(evt);
            }
        });
        ta_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_idActionPerformed(evt);
            }
        });
        ta_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ta_idKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ta_idKeyTyped(evt);
            }
        });
        adminTeacherAdd_23.add(ta_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 560, 300, 40));

        ta_reg.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_reg.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_regActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(ta_reg, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 610, 300, 40));

        ta_dept.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_dept.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherAdd_23.add(ta_dept, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 560, 300, 40));

        ta_blood.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_blood.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "select one", "A +", "A -", "B +", "B -", "O +", "O -", "AB +", "AB -", " " }));
        ta_blood.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherAdd_23.add(ta_blood, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 610, 300, 40));

        ta_fac.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ta_fac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Computer Science & Engineering", "Agriculture", "Business Administration & Management", "Animal Husbandry", "Doctor Veterinary Medicine", "Fisheries", "Environmental Science and Disaster Management", "Nutrition and Food Science", "Land Management and Administration" }));
        ta_fac.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ta_fac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ta_facItemStateChanged(evt);
            }
        });
        ta_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_facActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(ta_fac, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 300, 40));

        jButton3.setBackground(new java.awt.Color(14, 0, 82));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        jButton3.setText("Back");
        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 100, 40));

        jButton4.setBackground(new java.awt.Color(14, 0, 82));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/save.png"))); // NOI18N
        jButton4.setText("  Add");
        jButton4.setBorder(null);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 660, 100, 40));

        ta_nid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ta_nid.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ta_nid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ta_nidActionPerformed(evt);
            }
        });
        adminTeacherAdd_23.add(ta_nid, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 660, 300, 40));

        jLabel151.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setText(":");
        adminTeacherAdd_23.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 660, 10, 30));

        jLabel152.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(255, 255, 255));
        jLabel152.setText("Nid No ");
        adminTeacherAdd_23.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 660, 70, 40));

        ProjectTab.addTab("tab14", adminTeacherAdd_23);

        teacherProfileView_24.setBackground(new java.awt.Color(0, 5, 42));
        teacherProfileView_24.setPreferredSize(new java.awt.Dimension(1110, 890));
        teacherProfileView_24.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                teacherProfileView_24ComponentShown(evt);
            }
        });
        teacherProfileView_24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel118.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(255, 255, 255));
        jLabel118.setText("Change Profile :");
        teacherProfileView_24.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 160, 30));

        labelForShowTeacherAdminTitlePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/pastu-admin.png"))); // NOI18N
        teacherProfileView_24.add(labelForShowTeacherAdminTitlePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 470, 60));

        labelForShowTeacherDinTitlePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/dinTitle.png"))); // NOI18N
        teacherProfileView_24.add(labelForShowTeacherDinTitlePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 470, 60));

        labelForShowTeacherTitlePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teaher.png"))); // NOI18N
        labelForShowTeacherTitlePic.setText("jLabel16");
        teacherProfileView_24.add(labelForShowTeacherTitlePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 470, 70));

        teacherUpdateProfilepic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        teacherProfileView_24.add(teacherUpdateProfilepic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 200, 180));

        jLabel136.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel136.setText("jLabel56");
        jLabel136.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 30, 40));

        teacherName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        teacherName.setForeground(new java.awt.Color(153, 0, 102));
        teacherName.setText("Name");
        teacherProfileView_24.add(teacherName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 410, 40));

        file2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/folder.png"))); // NOI18N
        file2.setText("jLabel56");
        file2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                file2MouseClicked(evt);
            }
        });
        teacherProfileView_24.add(file2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, 40, 30));

        jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/email.png"))); // NOI18N
        jLabel137.setText("jLabel56");
        jLabel137.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, 30, 30));

        jLabel144.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/nid.png"))); // NOI18N
        jLabel144.setText("jLabel56");
        jLabel144.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel144.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel144MouseClicked(evt);
            }
        });
        teacherProfileView_24.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 30, 30));

        jLabel150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/dob.png"))); // NOI18N
        jLabel150.setText("jLabel56");
        jLabel150.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 30, 30));

        jLabel153.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/uname.png"))); // NOI18N
        jLabel153.setText("jLabel56");
        jLabel153.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 30, 30));

        jLabel154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/phone.png"))); // NOI18N
        jLabel154.setText("jLabel56");
        jLabel154.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 30, 30));

        jLabel155.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel155.setText("jLabel56");
        jLabel155.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 520, 30, 30));

        teacherEmail.setBackground(new java.awt.Color(0, 5, 42));
        teacherEmail.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        teacherEmail.setForeground(new java.awt.Color(255, 255, 255));
        teacherEmail.setText("gfhgj");
        teacherEmail.setBorder(null);
        teacherProfileView_24.add(teacherEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 290, 40));
        teacherProfileView_24.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 300, 10));

        teacherBlood.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        teacherBlood.setForeground(new java.awt.Color(255, 204, 0));
        teacherBlood.setText("Name");
        teacherProfileView_24.add(teacherBlood, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 300, -1));
        teacherProfileView_24.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 300, 10));
        teacherProfileView_24.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 300, 10));

        teacherUsername.setBackground(new java.awt.Color(0, 5, 42));
        teacherUsername.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        teacherUsername.setForeground(new java.awt.Color(255, 255, 255));
        teacherUsername.setText("ghghghgh");
        teacherUsername.setBorder(null);
        teacherProfileView_24.add(teacherUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 300, 40));

        teacherPhone.setBackground(new java.awt.Color(0, 5, 42));
        teacherPhone.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        teacherPhone.setForeground(new java.awt.Color(255, 255, 255));
        teacherPhone.setText("iiiiii");
        teacherPhone.setBorder(null);
        teacherPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherPhoneActionPerformed(evt);
            }
        });
        teacherProfileView_24.add(teacherPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 290, 40));

        jButton9.setBackground(new java.awt.Color(14, 0, 82));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/send.png"))); // NOI18N
        jButton9.setText("Update");
        jButton9.setBorder(null);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        teacherProfileView_24.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 620, 110, 50));

        teacherDob.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        teacherDob.setForeground(new java.awt.Color(255, 204, 0));
        teacherDob.setText("Name");
        teacherProfileView_24.add(teacherDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 310, -1));

        teacherNid.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        teacherNid.setForeground(new java.awt.Color(255, 204, 0));
        teacherNid.setText("Nid");
        teacherProfileView_24.add(teacherNid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 260, 30));
        teacherProfileView_24.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 300, 10));

        jLabel156.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/blood.png"))); // NOI18N
        jLabel156.setText("jLabel56");
        jLabel156.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teacherProfileView_24.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 30, 30));

        teacherPassword.setBackground(new java.awt.Color(0, 5, 42));
        teacherPassword.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        teacherPassword.setForeground(new java.awt.Color(255, 255, 255));
        teacherPassword.setBorder(null);
        teacherProfileView_24.add(teacherPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 510, 210, 40));

        studentPassEye1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/verify.png"))); // NOI18N
        studentPassEye1.setBorder(null);
        studentPassEye1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        studentPassEye1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPassEye1ActionPerformed(evt);
            }
        });
        teacherProfileView_24.add(studentPassEye1, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 510, 40, 30));

        ProjectTab.addTab("tab2", teacherProfileView_24);

        teacherDashboard_25.setBackground(new java.awt.Color(0, 5, 42));
        teacherDashboard_25.setPreferredSize(new java.awt.Dimension(1200, 855));
        teacherDashboard_25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo200_200.png"))); // NOI18N
        jLabel24.setText("jLabel8");
        teacherDashboard_25.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, 200, 240));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/controllerTitle.png"))); // NOI18N
        teacherDashboard_25.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 290, 50));

        totalStudent.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalStudent.setForeground(new java.awt.Color(255, 255, 255));
        totalStudent.setText("ppp");
        teacherDashboard_25.add(totalStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 50, 40));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/controllerTitle.png"))); // NOI18N
        teacherDashboard_25.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, 290, 50));

        totalStudent1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalStudent1.setForeground(new java.awt.Color(255, 255, 255));
        totalStudent1.setText("ppp");
        teacherDashboard_25.add(totalStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 220, 70, 30));

        ProjectTab.addTab("tab1", teacherDashboard_25);

        adminFacultyTeacher_26.setBackground(new java.awt.Color(0, 5, 42));
        adminFacultyTeacher_26.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminFacultyTeacher_26ComponentShown(evt);
            }
        });
        adminFacultyTeacher_26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back_4.setBackground(new java.awt.Color(14, 0, 82));
        back_4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        back_4.setForeground(new java.awt.Color(255, 255, 255));
        back_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        back_4.setText("Back");
        back_4.setBorder(null);
        back_4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        back_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_4ActionPerformed(evt);
            }
        });
        adminFacultyTeacher_26.add(back_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 120, 48));

        allTeachersByFaculty.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        allTeachersByFaculty.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Id", "Depertment", "email", "nid"
            }
        ));
        allTeachersByFaculty.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allTeachersByFaculty.setRowHeight(30);
        allTeachersByFaculty.setSelectionBackground(new java.awt.Color(0, 5, 42));
        allTeachersByFaculty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allTeachersByFacultyMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(allTeachersByFaculty);

        adminFacultyTeacher_26.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 1050, 520));

        facultyAllTeacher.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facultyAllTeacher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facultyAllTeacher.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facultyAllTeacherItemStateChanged(evt);
            }
        });
        facultyAllTeacher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyAllTeacherActionPerformed(evt);
            }
        });
        adminFacultyTeacher_26.add(facultyAllTeacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 240, 48));

        viewStudentfaculty_16.setBackground(new java.awt.Color(14, 0, 82));
        viewStudentfaculty_16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        viewStudentfaculty_16.setForeground(new java.awt.Color(255, 255, 255));
        viewStudentfaculty_16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        viewStudentfaculty_16.setText("View");
        viewStudentfaculty_16.setBorder(null);
        viewStudentfaculty_16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewStudentfaculty_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStudentfaculty_16ActionPerformed(evt);
            }
        });
        adminFacultyTeacher_26.add(viewStudentfaculty_16, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 210, 120, 48));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        adminFacultyTeacher_26.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 210, -1, 48));

        facultyAllTeacherSession1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facultyAllTeacherSession1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facultyAllTeacherSession1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facultyAllTeacherSession1ItemStateChanged(evt);
            }
        });
        adminFacultyTeacher_26.add(facultyAllTeacherSession1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 240, 48));

        ProjectTab.addTab("tab16", adminFacultyTeacher_26);

        adminTeacherDetails_27.setBackground(new java.awt.Color(0, 5, 42));
        adminTeacherDetails_27.setForeground(new java.awt.Color(255, 255, 255));
        adminTeacherDetails_27.setPreferredSize(new java.awt.Dimension(1110, 890));
        adminTeacherDetails_27.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                adminTeacherDetails_27ComponentShown(evt);
            }
        });
        adminTeacherDetails_27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel195.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/teachers details.png"))); // NOI18N
        jLabel195.setText("jLabel16");
        adminTeacherDetails_27.add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 620, 70));

        tDetailsProfilePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        adminTeacherDetails_27.add(tDetailsProfilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 200, 180));

        jLabel197.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel197.setText("jLabel56");
        jLabel197.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel197, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 610, 30, 30));

        jLabel198.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(255, 255, 255));
        jLabel198.setText(":");
        jLabel198.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel198.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel198MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel198, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 650, 20, 30));

        jLabel200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel200.setText("jLabel56");
        jLabel200.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, 30, 30));

        jLabel201.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel201.setText("jLabel56");
        jLabel201.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 510, 30, 30));

        jLabel202.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel202.setText("jLabel56");
        jLabel202.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 560, 30, 30));

        adminDob25.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob25.setForeground(new java.awt.Color(255, 255, 255));
        adminDob25.setText("Phone");
        adminTeacherDetails_27.add(adminDob25, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 610, 70, -1));

        adminDob27.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob27.setForeground(new java.awt.Color(255, 255, 255));
        adminDob27.setText("Address");
        adminTeacherDetails_27.add(adminDob27, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 90, -1));

        adminDob28.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob28.setForeground(new java.awt.Color(255, 255, 255));
        adminDob28.setText("Birth Date");
        adminTeacherDetails_27.add(adminDob28, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 510, 110, -1));

        adminDob29.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob29.setForeground(new java.awt.Color(255, 255, 255));
        adminDob29.setText("Blood Group ");
        adminTeacherDetails_27.add(adminDob29, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 560, 120, -1));

        jLabel203.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel203.setText("jLabel56");
        jLabel203.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 650, 30, 30));

        adminDob30.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob30.setForeground(new java.awt.Color(255, 255, 255));
        adminDob30.setText("Nid no");
        adminTeacherDetails_27.add(adminDob30, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 650, 70, -1));

        jLabel204.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel204.setText("jLabel56");
        jLabel204.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel204.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel204MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 30, 30));

        jLabel205.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel205.setForeground(new java.awt.Color(255, 255, 255));
        jLabel205.setText(":");
        jLabel205.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel205.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel205MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, 20, 20));

        jLabel208.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel208.setForeground(new java.awt.Color(255, 255, 255));
        jLabel208.setText(":");
        jLabel208.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel208.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel208MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel208, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 20, 20));

        jLabel209.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel209.setForeground(new java.awt.Color(255, 255, 255));
        jLabel209.setText(":");
        jLabel209.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel209.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel209MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel209, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 510, 20, 20));

        jLabel210.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel210.setForeground(new java.awt.Color(255, 255, 255));
        jLabel210.setText(":");
        jLabel210.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel210.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel210MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, 20, 20));

        jLabel211.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel211.setForeground(new java.awt.Color(255, 255, 255));
        jLabel211.setText(":");
        jLabel211.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel211.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel211MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel211, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 610, 20, -1));

        adminNid6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid6.setForeground(new java.awt.Color(255, 255, 255));
        adminNid6.setText("Name");
        adminTeacherDetails_27.add(adminNid6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 90, 30));

        adminNid7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminNid7.setForeground(new java.awt.Color(255, 255, 255));
        adminNid7.setText("Id");
        adminTeacherDetails_27.add(adminNid7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 405, 90, 30));

        jLabel212.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel212.setText("jLabel56");
        jLabel212.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel212.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel212MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel212, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 30, 40));

        jLabel213.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel213.setText("jLabel56");
        jLabel213.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel213, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 450, 30, 40));

        adminBlood6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminBlood6.setForeground(new java.awt.Color(255, 255, 255));
        adminBlood6.setText("Reg");
        adminTeacherDetails_27.add(adminBlood6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 455, 130, 30));

        jLabel214.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel214.setText("jLabel56");
        jLabel214.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 500, 30, 40));

        adminDob31.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob31.setForeground(new java.awt.Color(255, 255, 255));
        adminDob31.setText("Email");
        adminTeacherDetails_27.add(adminDob31, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 505, 130, -1));

        jLabel216.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel216.setText("jLabel56");
        jLabel216.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel216, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 550, 30, 40));

        adminDob33.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob33.setForeground(new java.awt.Color(255, 255, 255));
        adminDob33.setText("Faculty");
        adminTeacherDetails_27.add(adminDob33, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 555, 110, -1));

        jLabel217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel217.setText("jLabel56");
        jLabel217.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 600, 30, 40));

        adminDob34.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob34.setForeground(new java.awt.Color(255, 255, 255));
        adminDob34.setText("depertment");
        adminTeacherDetails_27.add(adminDob34, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 605, 120, -1));

        jLabel219.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel219.setForeground(new java.awt.Color(255, 255, 255));
        jLabel219.setText(":");
        jLabel219.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel219.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel219MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, 20, 30));

        jLabel220.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel220.setForeground(new java.awt.Color(255, 255, 255));
        jLabel220.setText(":");
        jLabel220.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel220.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel220MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel220, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, 20, 30));

        jLabel221.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel221.setForeground(new java.awt.Color(255, 255, 255));
        jLabel221.setText(":");
        jLabel221.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel221.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel221MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel221, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 505, 20, -1));

        jLabel223.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel223.setForeground(new java.awt.Color(255, 255, 255));
        jLabel223.setText(":");
        jLabel223.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel223.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel223MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel223, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 555, 20, 20));

        jLabel224.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel224.setForeground(new java.awt.Color(255, 255, 255));
        jLabel224.setText(":");
        jLabel224.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel224.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel224MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel224, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 605, 20, 20));

        view5.setBackground(new java.awt.Color(14, 0, 82));
        view5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view5.setForeground(new java.awt.Color(255, 255, 255));
        view5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/back.png"))); // NOI18N
        view5.setText("Back");
        view5.setBorder(null);
        view5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view5ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(view5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 40));

        jLabel226.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel226.setText("jLabel56");
        jLabel226.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel226, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 30, 30));

        adminDob36.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob36.setForeground(new java.awt.Color(255, 255, 255));
        adminDob36.setText("Status");
        adminTeacherDetails_27.add(adminDob36, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 70, 30));

        jLabel227.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel227.setForeground(new java.awt.Color(255, 255, 255));
        jLabel227.setText(":");
        jLabel227.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel227.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel227MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel227, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 20, 30));

        sstatusBtn1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sstatusBtn1.setBorder(null);
        sstatusBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sstatusBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sstatusBtn1ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(sstatusBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 270, 110, 35));

        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/trash.png"))); // NOI18N
        jButton21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 270, 60, 40));

        adminDob35.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        adminDob35.setForeground(new java.awt.Color(255, 255, 255));
        adminDob35.setText("Others Role");
        adminTeacherDetails_27.add(adminDob35, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 660, 120, -1));

        jLabel225.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel225.setForeground(new java.awt.Color(255, 255, 255));
        jLabel225.setText(":");
        jLabel225.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel225.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel225MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel225, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 660, 20, 20));

        jLabel218.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Flower/blue.png"))); // NOI18N
        jLabel218.setText("jLabel56");
        jLabel218.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminTeacherDetails_27.add(jLabel218, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 650, 30, 40));

        printbtn3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        printbtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/print.png"))); // NOI18N
        printbtn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printbtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtn3ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(printbtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 270, 60, 40));

        jPanel1.setBackground(new java.awt.Color(0, 5, 42));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roleController.setBackground(new java.awt.Color(0, 5, 42));
        roleController.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        roleController.setForeground(new java.awt.Color(255, 255, 255));
        roleController.setText("Controller");
        roleController.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        roleController.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleControllerActionPerformed(evt);
            }
        });
        jPanel1.add(roleController, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 160, 30));

        roleDin.setBackground(new java.awt.Color(0, 5, 42));
        roleDin.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        roleDin.setForeground(new java.awt.Color(255, 255, 255));
        roleDin.setText("Din");
        roleDin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        roleDin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                roleDinStateChanged(evt);
            }
        });
        roleDin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleDinActionPerformed(evt);
            }
        });
        jPanel1.add(roleDin, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 60, 30));

        roleRegister.setBackground(new java.awt.Color(0, 5, 42));
        roleRegister.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        roleRegister.setForeground(new java.awt.Color(255, 255, 255));
        roleRegister.setText("Register");
        roleRegister.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        roleRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleRegisterActionPerformed(evt);
            }
        });
        jPanel1.add(roleRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 100, 30));

        roleCoAdmin.setBackground(new java.awt.Color(0, 5, 42));
        roleCoAdmin.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        roleCoAdmin.setForeground(new java.awt.Color(255, 255, 255));
        roleCoAdmin.setText(" Co-Admin");
        roleCoAdmin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        roleCoAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleCoAdminActionPerformed(evt);
            }
        });
        jPanel1.add(roleCoAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        adminTeacherDetails_27.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 700, 950, 50));

        adminProfileBtn_02.setBackground(new java.awt.Color(14, 0, 82));
        adminProfileBtn_02.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adminProfileBtn_02.setForeground(new java.awt.Color(255, 255, 255));
        adminProfileBtn_02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/bee.png"))); // NOI18N
        adminProfileBtn_02.setText("Update");
        adminProfileBtn_02.setBorder(null);
        adminProfileBtn_02.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminProfileBtn_02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminProfileBtn_02ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(adminProfileBtn_02, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 770, 120, 45));

        facultyAllTeacher2.setBackground(new java.awt.Color(0, 5, 42));
        facultyAllTeacher2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facultyAllTeacher2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facultyAllTeacher2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facultyAllTeacher2ItemStateChanged(evt);
            }
        });
        facultyAllTeacher2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyAllTeacher2ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(facultyAllTeacher2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 310, 80, 40));

        facultyAllTeacher1.setBackground(new java.awt.Color(0, 5, 42));
        facultyAllTeacher1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        facultyAllTeacher1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        facultyAllTeacher1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                facultyAllTeacher1ItemStateChanged(evt);
            }
        });
        facultyAllTeacher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyAllTeacher1ActionPerformed(evt);
            }
        });
        adminTeacherDetails_27.add(facultyAllTeacher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 260, 80, 40));
        adminTeacherDetails_27.add(storeFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 860, 100, 30));
        adminTeacherDetails_27.add(storeDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 850, 100, 30));

        tnid.setBackground(new java.awt.Color(0, 5, 42));
        tnid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tnid.setForeground(new java.awt.Color(255, 255, 255));
        tnid.setText("jTextField1");
        tnid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 650, 240, 40));

        taddr.setBackground(new java.awt.Color(0, 5, 42));
        taddr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        taddr.setForeground(new java.awt.Color(255, 255, 255));
        taddr.setText("jTextField1");
        taddr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(taddr, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 450, 240, 40));

        tdob.setBackground(new java.awt.Color(0, 5, 42));
        tdob.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tdob.setForeground(new java.awt.Color(255, 255, 255));
        tdob.setText("jTextField1");
        tdob.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tdob, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 500, 240, 40));

        tblood.setBackground(new java.awt.Color(0, 5, 42));
        tblood.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblood.setForeground(new java.awt.Color(255, 255, 255));
        tblood.setText("jTextField1");
        tblood.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tblood, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 550, 240, 40));

        tphone.setBackground(new java.awt.Color(0, 5, 42));
        tphone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tphone.setForeground(new java.awt.Color(255, 255, 255));
        tphone.setText("jTextField1");
        tphone.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 600, 240, 40));

        tname.setBackground(new java.awt.Color(0, 5, 42));
        tname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tname.setForeground(new java.awt.Color(255, 255, 255));
        tname.setText("jTextField1");
        tname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tname, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 400, 240, 40));

        tid.setBackground(new java.awt.Color(0, 5, 42));
        tid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tid.setForeground(new java.awt.Color(255, 255, 255));
        tid.setText("jTextField1");
        tid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tid, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 400, 270, 40));

        treg.setBackground(new java.awt.Color(0, 5, 42));
        treg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        treg.setForeground(new java.awt.Color(255, 255, 255));
        treg.setText("jTextField1");
        treg.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(treg, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 450, 270, 40));

        temail.setBackground(new java.awt.Color(0, 5, 42));
        temail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        temail.setForeground(new java.awt.Color(255, 255, 255));
        temail.setText("jTextField1");
        temail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(temail, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 500, 270, 40));

        tfac.setBackground(new java.awt.Color(0, 5, 42));
        tfac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfac.setForeground(new java.awt.Color(255, 255, 255));
        tfac.setText("jTextField1");
        tfac.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 550, 270, 40));

        tdept.setBackground(new java.awt.Color(0, 5, 42));
        tdept.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tdept.setForeground(new java.awt.Color(255, 255, 255));
        tdept.setText("jTextField1");
        tdept.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        adminTeacherDetails_27.add(tdept, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 600, 270, 40));

        jLabel22.setBackground(new java.awt.Color(0, 5, 42));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/down arrow.png"))); // NOI18N
        jLabel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.setOpaque(true);
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        adminTeacherDetails_27.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 660, 50, 40));

        ProjectTab.addTab("tab28", adminTeacherDetails_27);

        course_28.setBackground(new java.awt.Color(0, 5, 42));
        course_28.setPreferredSize(new java.awt.Dimension(1200, 855));
        course_28.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                course_28ComponentShown(evt);
            }
        });
        course_28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cocode.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cocode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cocodeActionPerformed(evt);
            }
        });
        course_28.add(cocode, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 300, 40));

        jLabel129.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 255, 255));
        jLabel129.setText("Course Code : ");
        course_28.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 120, 40));

        jLabel159.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel159.setForeground(new java.awt.Color(255, 255, 255));
        jLabel159.setText("Faculty   :");
        course_28.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 90, 40));

        coname.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        coname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        coname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conameActionPerformed(evt);
            }
        });
        course_28.add(coname, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, 300, 40));

        jLabel163.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(255, 255, 255));
        jLabel163.setText("Credit Hour  :");
        course_28.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 260, 120, 40));

        cohour.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cohour.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cohour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cohourActionPerformed(evt);
            }
        });
        course_28.add(cohour, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 260, 300, 40));

        cofac.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cofac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Faculty", "Computer Science & Engineering", "Agriculture", "Business Administration & Management", "Animal Husbandry", "Doctor Veterinary Medicine", "Fisheries", "Environmental Science and Disaster Management", "Nutrition and Food Science", "Land Management and Administration" }));
        cofac.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cofac.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cofac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cofacItemStateChanged(evt);
            }
        });
        cofac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cofacActionPerformed(evt);
            }
        });
        course_28.add(cofac, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 300, 40));

        jLabel166.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(255, 255, 255));
        jLabel166.setText("Course Name :");
        course_28.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 120, 40));

        courseTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        courseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Faculty", "Course Name", "Course Code", "Credit Hour"
            }
        ));
        jScrollPane4.setViewportView(courseTable);

        course_28.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 340, 1020, 480));

        addStudentBtn_14.setBackground(new java.awt.Color(14, 0, 82));
        addStudentBtn_14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        addStudentBtn_14.setForeground(new java.awt.Color(255, 255, 255));
        addStudentBtn_14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/addNew.png"))); // NOI18N
        addStudentBtn_14.setText(" Add New");
        addStudentBtn_14.setBorder(null);
        addStudentBtn_14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addStudentBtn_14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentBtn_14ActionPerformed(evt);
            }
        });
        course_28.add(addStudentBtn_14, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 150, 170, 50));

        ProjectTab.addTab("tab1", course_28);

        contentPanel.add(ProjectTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -35, 1120, 935));

        getContentPane().add(contentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, -35, 1115, 890));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void adminHallBtn_06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminHallBtn_06ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(5);
    }//GEN-LAST:event_adminHallBtn_06ActionPerformed

    private void adminLibraryBtn_07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminLibraryBtn_07ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(6);
    }//GEN-LAST:event_adminLibraryBtn_07ActionPerformed

    private void adminTeacherBtn_04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTeacherBtn_04ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(3);
    }//GEN-LAST:event_adminTeacherBtn_04ActionPerformed

    private void adminTransectionBtn_08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTransectionBtn_08ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(7);
    }//GEN-LAST:event_adminTransectionBtn_08ActionPerformed

    private void adminHealthCareBtn_09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminHealthCareBtn_09ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(8);
    }//GEN-LAST:event_adminHealthCareBtn_09ActionPerformed

    private void adminStudentBtn_03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminStudentBtn_03ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(2);
    }//GEN-LAST:event_adminStudentBtn_03ActionPerformed

    private void adminProfileBtn_02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminProfileBtn_02ActionPerformed
        // TODO add your handling code here:
        int roleCount =0;
        String name = make_String_Cappitalize(tname.getText());
        String addr = tid.getText();
        String dob =  tdob.getText();
        String blood = tblood.getText();
        String nid =  tnid.getText();
        String fac =  tfac.getText();
        String dept =  tdept.getText();
        String role = null;
        if(roleCoAdmin.isSelected()){ 
            role = roleCoAdmin.getText().toLowerCase();
            roleCount++;
        }
        if(roleRegister.isSelected()){ 
            role = roleRegister.getText().toLowerCase();
            roleCount++;
        }
        if(roleDin.isSelected()){ 
            role = roleDin.getText().toLowerCase();
            roleCount++;
        }
        
        
        if(role == null){
            role = "teacher";
        }
        
        String regexAlphabet = "^[A-Za-z_ ]+$";
        String regexNumber = "^[0-9]+$";
        
//        if(name.matches(regexAlphabet)){column.add(name);
            column.add(name);
           if(!addr.isEmpty()){column.add(addr);
            if(!dob.isEmpty()){column.add(dob);
                if(!blood.isEmpty()){column.add(blood);
                    if(nid.matches(regexNumber)){
                       if(count(nid,10,"Nid no sholuld be 10 digit") == true){column.add(nid);
                           if(fac.matches(regexAlphabet)){column.add(fac);
                                if(!"".equals(dept)){column.add(dept);
                                    if(!(roleCount > 1)){
                                        column.add(role);
                                        insertStudent(column,"update",tid.getText());
                                    }else{alert("error","true","Role should be one");};
                                }else{alert("error","true","Select Depertment");};
                           }else{alert("error","true","Select faculty");};
                       }
                    }else{alert("error","true","You insert wrong Nid");}  
                }else{alert("error","true","Blood field is empty");};
            }else{alert("error","true","DOB field is empty");};
           }else{alert("error","true","Address field is empty");};
//        }else{alert("error","true","Name should be alpahbet");};
        
        
        
        
        System.out.println(roleCount);
    }//GEN-LAST:event_adminProfileBtn_02ActionPerformed

    private void addStudentBtn_13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtn_13ActionPerformed
        // TODO add your handling code here:        
        ProjectTab.setSelectedIndex(12);
    }//GEN-LAST:event_addStudentBtn_13ActionPerformed

    private void cseSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cseSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cseSession_16ActionPerformed

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

    private void searchStudent_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStudent_16ActionPerformed
        // TODO add your handling code here:
             studentsDetailsId = serchIdField.getText();
             if("".equals(studentsDetailsId) || "Search with student id".equals(studentsDetailsId)){ 
             
                alert("error","true","Insert student id");
             }else{ 
             ProjectTab.setSelectedIndex(15);     
               serchIdField.setText("Search with student id");
             }
    }//GEN-LAST:event_searchStudent_16ActionPerformed

    private void agriSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agriSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_agriSession_16ActionPerformed

    private void dvmSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dvmSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dvmSession_16ActionPerformed

    private void bamSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bamSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bamSession_16ActionPerformed

    private void fishSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fishSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fishSession_16ActionPerformed

    private void nfsSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nfsSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nfsSession_16ActionPerformed

    private void esdmSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esdmSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_esdmSession_16ActionPerformed

    private void llaSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_llaSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_llaSession_16ActionPerformed

    private void ahSession_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ahSession_16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ahSession_16ActionPerformed

    private void serchTeacherWithIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchTeacherWithIdFocusGained
        // TODO add your handling code here:
        serchTeacherWithId.setText("");
    }//GEN-LAST:event_serchTeacherWithIdFocusGained

    private void serchTeacherWithIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchTeacherWithIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_serchTeacherWithIdFocusLost

    private void serchTeacherWithIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchTeacherWithIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serchTeacherWithIdActionPerformed

    private void addNewStudent3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStudent3ActionPerformed
        // TODO add your handling code here:
             teacherDetailsId = serchTeacherWithId.getText();
             if("".equals(teacherDetailsId) || " Search with Teacher id".equals(teacherDetailsId)){ 
             
                alert("error","true","Insert Teacher id");
             }else{ 
                 ProjectTab.setSelectedIndex(26);     
                 serchTeacherWithId.setText("Search with teacher id");
             }
    }//GEN-LAST:event_addNewStudent3ActionPerformed

    private void addStudentBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtn2ActionPerformed
        // TODO add your handling code here:       
        ProjectTab.setSelectedIndex(22);
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
            
            dateFromDatePicker="";
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
                tname.setBorder(BorderFactory.createCompoundBorder(tname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tphone.setBorder(BorderFactory.createCompoundBorder(tphone.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tnid.setBorder(BorderFactory.createCompoundBorder(tnid.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                taddr.setBorder(BorderFactory.createCompoundBorder(taddr.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tid.setBorder(BorderFactory.createCompoundBorder(tid.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tblood.setBorder(BorderFactory.createCompoundBorder(tblood.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tdob.setBorder(BorderFactory.createCompoundBorder(tdob.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tnid.setBorder(BorderFactory.createCompoundBorder(tnid.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                treg.setBorder(BorderFactory.createCompoundBorder(treg.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                temail.setBorder(BorderFactory.createCompoundBorder(temail.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tfac.setBorder(BorderFactory.createCompoundBorder(tfac.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                tdept.setBorder(BorderFactory.createCompoundBorder(tdept.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                jPanel1.setVisible(false);
                
                  serchIdField.setBorder(BorderFactory.createCompoundBorder(serchIdField.getBorder(), BorderFactory.createEmptyBorder(18, 18, 18, 18)));
                 serchTeacherWithId.setBorder(BorderFactory.createCompoundBorder(serchTeacherWithId.getBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                 
                 
                labelForShowTeacherTitlePic.setVisible(false);
                labelForShowTeacherAdminTitlePic.setVisible(false);
                labelForShowTeacherDinTitlePic.setVisible(false);
                
                serchIdField1.setBorder(BorderFactory.createCompoundBorder(serchIdField1.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                serchTeacherWithId1.setBorder(BorderFactory.createCompoundBorder(serchTeacherWithId1.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                
                coname.setBorder(BorderFactory.createCompoundBorder(coname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                cocode.setBorder(BorderFactory.createCompoundBorder(cocode.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                cohour.setBorder(BorderFactory.createCompoundBorder(cohour.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                
                
                
                
                
                
                   adminSidebar.setVisible(false);
                   DinSidebar.setVisible(false);
                 
        
        try {
            // TODO add your handling code here:
            conn con = new conn();
//            String query = "SELECT teacher.*, users.* FROM teacher,users  WHERE teacher.uid = users.uid AND users.uid =?";
            String query = "SELECT * FROM users  where uid =?";
            
            
            
            PreparedStatement ps=con.c.prepareStatement(query);
            ps.setString(1,logeduserid);
            rs  = ps.executeQuery();
            if(rs.next()){
                if("admin".equals(rs.getString("role"))){ 
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
        profileView();

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
            column.add(name);//01
            if(fname.matches(regexAlphabet)){ 
                column.add(fname);//02
                if(mname.matches(regexAlphabet)){ 
                    column.add(mname);//03
                    if(!"".equals(addr)){
                        column.add(addr);//04
                        if(nid.matches(regexNumber)){ // Check nid only have digits or not
                            if(count(nid,10,"Nid no sholuld be 10 digit") == true){ // Call count() meathoad with three arguements
                                column.add(nid);//05
                                if(!"".equals(dob)){
                                    column.add(dob);//06
                                    if(id.matches(regexNumber)){
                                        if(count(id,7,"Id no sholuld be 7 digit") == true){
                                            column.add(id);//07
                                            if(reg.matches(regexNumber)){
                                                if(count(reg,5,"Reg no sholuld be 5 digit") == true){
                                                    column.add(reg);//08
                                                    if(!"Select one".equals(session)){
                                                        column.add(session);//09
                                                        if(!"Select one".equals(blood)){
                                                            column.add(blood);//10
                                                            if(!"Select one".equals(fac)){
                                                                column.add(fac);//11
                                                                if(!"Select one".equals(hall)){
                                                                column.add(hall);//12
                                                                 //  If Everything is ok lets begain insert data into database
                                                                  insertStudent(column,"insert","");
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
                imagePath = photoPath;
                
                AdminDashboards.test f = new AdminDashboards.test();
                rs = f.finds(logeduserid,identity); 
                
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

    private void sendUpdateVerificationCode_14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendUpdateVerificationCode_14ActionPerformed
        // TODO add your handling code here:
        
            conn cc = new conn();
            random = new Random().nextInt(900000) + 100000;
            String data = String.valueOf(random);
            String toSend = adminEmail.getText(); // to address. It can be any like gmail, yahoo etc.
            
                sendMail mail = new sendMail(toSend,random,"Verification for Update Profile ");
                if(mail.send()){
                    UpdateToken object = new UpdateToken(identity,"token",data,logeduserid);
                    if(object.UpadetData()){
                        alert("success","true","Please verify your mail");
                        ProjectTab.setSelectedIndex(13);
                    }else{
                        alert("error","true","Something going wrong with database,Try again!");
                    }
                }else{
                    alert("error","true","Check your connection or try after sometime");
                }
    }//GEN-LAST:event_sendUpdateVerificationCode_14ActionPerformed

    private void successCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_successCloseActionPerformed
        // TODO add your handling code here:
        alert("success","false","");
        alert("error","false","");
    }//GEN-LAST:event_successCloseActionPerformed

    private void accountverifyForUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountverifyForUpdateProfileActionPerformed
//        try {
            // TODO add your handling code here:
            String verifyUserEmail = verifyUsername.getText();
            String verifyUserVkey = vkey.getText();
            
           if(!"".equals(verifyUserEmail)){ 
                    if(!"".equals(verifyUserVkey)){ 
                        try {
                            conn cc = new conn();
                            String query = "SELECT * FROM users where uid='"+logeduserid+"'and token='"+verifyUserVkey+"'";
                            ResultSet rs = cc.s.executeQuery(query);
                                if(rs.next()){
                                    
                                    if("student".equals(rs.getString("role"))){ 
                                        ps= cc.c.prepareStatement("UPDATE student,users SET users.username=?, users.password=?, users.token=?, student.email=?, student.phone=? Where users.uid=?");
                                        
                                        ps.setString(1, studentUsername.getText());
                                        ps.setString(2, studentPassword.getText());
                                        ps.setString(3, "");
                                        ps.setString(4, studentEmail.getText());
                                        ps.setString(5, studentPhone.getText());
                                        ps.setString(6,logeduserid);

                                        ps.execute();
                                        upDateOrnot = ps.executeUpdate();
                                   
                                        if(upDateOrnot > 0){ 
                                           alert("success","true","Your profile is upto date");
                                           verifyUsername.setText("Your Email Address");
                                           ProjectTab.setSelectedIndex(16);
                                        }else{ 
                                           alert("error","true","Somethis is wrong with your data");
                                        }
                                   }else{ 
                                        String querys = "UPDATE teacher, users SET users.username = ?,users.password = ?, users.token=?,teacher.email = ?,teacher.phone = ? WHERE users.uid = ?";
                                        ps= cc.c.prepareStatement(querys);
                                        
                                        ps.setString(1, teacherUsername.getText());
                                        ps.setString(2, teacherPassword.getText());
                                        ps.setString(3, "");
                                        ps.setString(4, teacherEmail.getText());
                                        ps.setString(5, teacherPhone.getText());
                                        ps.setString(6,logeduserid);
                                        ps.execute();
                                        
                                        int count = ps.executeUpdate();
                                        if(count > 0){ 
                                           alert("success","true","Your profile is upto date");
                                           verifyUsername.setText("Your Email Address");
                                           ProjectTab.setSelectedIndex(23);
                                        }else{ 
                                           alert("error","true","Somethis is wrong with your data");
                                        }

                                   }
                                   
                                   
                                   
                                }
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{ 
                      alert("error","true","Insert verification code");
                    }
               }else{ 
                 alert("error","true","Insert your registered Email");
               }

    }//GEN-LAST:event_accountverifyForUpdateProfileActionPerformed

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

    private void cseSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cseSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "cse";
        String sessions = (String)cseSession_16.getSelectedItem();
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
        
        
    }//GEN-LAST:event_cseSession_16ItemStateChanged

    private void facValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facValActionPerformed

    private void adminFacultyStudent_15ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminFacultyStudent_15ComponentShown

    }//GEN-LAST:event_adminFacultyStudent_15ComponentShown

    private void facultyAllStudentSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facultyAllStudentSessionItemStateChanged
        // TODO add your handling code here:
        String sessions = (String)facultyAllStudentSession.getSelectedItem();
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

                    if(rs.next()){
                        int count=0;
                        do{
                            count++;
                            String name = rs.getString("name");
                            String id = rs.getString("uid");
                            String reg = rs.getString("reg");
                            String email = rs.getString("email");
                            String phone = rs.getString("phone");
                            String hall = rs.getString("hall");
                            String st[] = {name,id,reg,email,phone,hall};
                            table.addRow(st);
                        }while(rs.next());
                         alert("success","true","Total "+count+" data found");
                    }else{ 
                         alert("error","true","No student found");
                    }
                }
                   cc.s.close();

            }catch(SQLException e){
            }
        }

    }//GEN-LAST:event_facultyAllStudentSessionItemStateChanged

    private void facultyAllStudentSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyAllStudentSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facultyAllStudentSessionActionPerformed

    private void back_03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_03ActionPerformed
        // TODO add your handling code here:
               
       
       
       ta_name.setText("");
       ta_addr.setText("");
       ta_phone.setText("");
       ta_nid.setText("");
       ta_id.setText("");
       ta_reg.setText("");  
//       ta_blood.setSelectedIndex(0);    
//       ta_fac.setSelectedIndex(0);
//       ta_dept.setSelectedIndex(0); 
        resetField();
        studentsDetailsId = "";
        ProjectTab.setSelectedIndex(2); 
    }//GEN-LAST:event_back_03ActionPerformed

    private void viewStudentfaculty_15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewStudentfaculty_15ActionPerformed
        // TODO add your handling code here:
        
             DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
             
             tableRowNo = allStudentsByFaculty.getSelectedRow();
             if(tableRowNo>=0){ 
              ProjectTab.setSelectedIndex(15);
             }else{ 
               alert("error","true","No field selected");
             }
             
    }//GEN-LAST:event_viewStudentfaculty_15ActionPerformed

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
                sname.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sfname.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                smname.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                saddr.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sdob.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sblood.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sphone.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                snid.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sid.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sreg.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                semail.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                ssession.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                sfac.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                shall.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
                ssem.setBorder(BorderFactory.createCompoundBorder(sname.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
            
        
            
        
        
        
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
                               
                               if("active".equals(rs.getString("status"))){
                                    sstatusBtn.setText("Active");
                                    sstatusBtn.setBackground(Color.GREEN);
                                    sstatusBtn.setForeground(Color.BLACK);
                               }else{ 
                                    sstatusBtn.setText("Disable");
                                    sstatusBtn.setBackground(Color.RED);
                                    sstatusBtn.setForeground(Color.WHITE);
                               }
                                
                               
                                sname.setText(rs.getString("name"));
                                sfname.setText(rs.getString("fname"));
                                smname.setText(rs.getString("mname"));
                                saddr.setText(rs.getString("addr"));
                                sdob.setText(rs.getString("dob"));
                                sblood.setText(rs.getString("blood"));
                                sphone.setText(rs.getString("phone"));
                                snid.setText(rs.getString("nid"));
                                sid.setText(rs.getString("uid"));
                                sreg.setText(rs.getString("reg"));
                                semail.setText(rs.getString("email"));
                                ssession.setText(rs.getString("session"));
                                sfac.setText(rs.getString("fac"));
                                shall.setText(rs.getString("hall"));
                                
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
        
        
             DefaultTableModel table = (DefaultTableModel)allStudentsByFaculty.getModel();
             deleteStudent(sid.getText());
             table.removeRow(tableRowNo);
        ProjectTab.setSelectedIndex(14);
//             if(tableRowNo>=0){ 
//              ProjectTab.setSelectedIndex(15);
//             }else{ 
//               alert("error","true","No field selected");
//             }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void facValItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facValItemStateChanged
        // TODO add your handling code here:
        
        String fac = (String)facVal.getSelectedItem();
        fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac);
        System.out.println(fac);
    }//GEN-LAST:event_facValItemStateChanged

    private void agriSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_agriSession_16ItemStateChanged
        // TODO add your handling code here:
        
        selectedFaculty = "agri";
        String sessions = (String)agriSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
       
    }//GEN-LAST:event_agriSession_16ItemStateChanged

    private void dvmSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dvmSession_16ItemStateChanged
        // TODO add your handling code here:

        selectedFaculty = "dvm";
        String sessions = (String)dvmSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_dvmSession_16ItemStateChanged

    private void bamSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bamSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "bam";
        String sessions = (String)bamSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_bamSession_16ItemStateChanged

    private void fishSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fishSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "fish";
        String sessions = (String)fishSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_fishSession_16ItemStateChanged

    private void nfsSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_nfsSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "nfs";
        String sessions = (String)nfsSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_nfsSession_16ItemStateChanged

    private void esdmSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_esdmSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "esdm";
        String sessions = (String)esdmSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_esdmSession_16ItemStateChanged

    private void llaSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_llaSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "lam";
        String sessions = (String) llaSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_llaSession_16ItemStateChanged

    private void ahSession_16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ahSession_16ItemStateChanged
        // TODO add your handling code here:
        selectedFaculty = "ah";
        String sessions = (String)ahSession_16.getSelectedItem();
        
        if(checkWordIsFoundOrnOT(sessions,"Session")){ 
          showFacultyStudent(selectedFaculty,sessions);
        }
    }//GEN-LAST:event_ahSession_16ItemStateChanged

    private void studentSidebarLogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSidebarLogoutBtnActionPerformed
        // TODO add your handling code here:
        dispose();
                Login log = new Login();
                log.setVisible(true);
    }//GEN-LAST:event_studentSidebarLogoutBtnActionPerformed

    private void studentDashboardBtn_18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentDashboardBtn_18ActionPerformed
        // TODO add your handling code here:
        
        ProjectTab.setSelectedIndex(17);
    }//GEN-LAST:event_studentDashboardBtn_18ActionPerformed

    private void studentProfileViewBtn_17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentProfileViewBtn_17ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(16);
    }//GEN-LAST:event_studentProfileViewBtn_17ActionPerformed

    private void studentPaymentBtn_19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPaymentBtn_19ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(18);
    }//GEN-LAST:event_studentPaymentBtn_19ActionPerformed

    private void studentFacultyBtn_20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentFacultyBtn_20ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(19);
    }//GEN-LAST:event_studentFacultyBtn_20ActionPerformed

    private void studentExamBtn_21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentExamBtn_21ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(20);
    }//GEN-LAST:event_studentExamBtn_21ActionPerformed

    private void studentResultBtn_22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentResultBtn_22ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(21);
    }//GEN-LAST:event_studentResultBtn_22ActionPerformed

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
                rs = f.finds(logeduserid,identity); 
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
            conn cc = new conn();
            random = new Random().nextInt(900000) + 100000;
            String data = String.valueOf(random);
            String toSend = studentEmail.getText(); 
            
            sendMail mail = new sendMail(toSend,random,"Verification for Update Profile ");
            if(mail.send()){
                UpdateToken object = new UpdateToken(identity,"token",data,logeduserid);
                if(object.UpadetData()){
                    alert("success","true","Please verify your mail");
                    ProjectTab.setSelectedIndex(13);
                }else{
                    alert("error","true","Something going wrong with database,Try again!");
                }
            }else{
                alert("error","true","Check your connection or try after sometime");
            }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void studentDashboardBtn_18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentDashboardBtn_18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_studentDashboardBtn_18MouseClicked

    private void studentProfileView_17ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_studentProfileView_17ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_studentProfileView_17ComponentShown

    private void serchIdFieldComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_serchIdFieldComponentShown
        // TODO add your handling code here:
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

    private void ta_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_nameActionPerformed

    private void ta_addrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_addrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_addrActionPerformed

    private void ta_phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_phoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_phoneActionPerformed

    private void ta_dobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ta_dobMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_dobMouseClicked

    private void ta_dobPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ta_dobPropertyChange
        // TODO add your handling code here:
        if ("date".equals(evt.getPropertyName())){
            Date getDate =  ta_dob.getDate();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFromDatePicker="";
            dateFromDatePicker = dateFormat.format(getDate);
        }
    }//GEN-LAST:event_ta_dobPropertyChange

    private void ta_idFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ta_idFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_idFocusLost

    private void ta_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_idActionPerformed

    private void ta_idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ta_idKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_idKeyPressed

    private void ta_idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ta_idKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_idKeyTyped

    private void ta_regActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_regActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_regActionPerformed

    private void ta_facItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ta_facItemStateChanged
        // TODO add your handling code here:
        conn con = new conn();
        
        String fac = (String)ta_fac.getSelectedItem();
        fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac).toLowerCase();
        String query = "SELECT dept FROM faculty where fac='"+fac+"'";
     
        try {
            ps = con.c.prepareStatement(query);
            rs  = ps.executeQuery(query);
            
            
                ta_dept.removeAllItems();
                ta_dept.addItem("Select depertment");
                while (rs.next()){ 
                    ta_dept.addItem(rs.getString("dept"));  
                }
                 ta_dept.getModel().setSelectedItem("Select depertment");

        }catch (SQLException ex){
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_ta_facItemStateChanged

    private void ta_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_facActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_facActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(3);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String name = make_String_Cappitalize(ta_name.getText());
        String addr = ta_addr.getText();
        String phone = ta_phone.getText();
        String dob = dateFromDatePicker;
        String id = ta_id.getText();
        String reg =  ta_reg.getText();
        String nid =  ta_nid.getText();
        String fac = (String)ta_fac.getSelectedItem();
        String dept = (String)ta_dept.getSelectedItem();
        fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac).toLowerCase();
        if(!"".equals(dept)){ 
          dept = getfirstLeter_replace_some_speacial_charecter_and_word(dept).toLowerCase();
        }
        
        String blood = (String)ta_blood.getSelectedItem();
             

        
        String regexAlphabet = "^[A-Za-z_ ]+$";
        String regexNumber = "^[0-9]+$";
        


         if(name.matches(regexAlphabet)){TeacherColumn.add(name);
           if(!"".equals(addr)){TeacherColumn.add(addr);
             if(phone.matches(regexNumber)){ 
               if(count(phone,11,"Phone no sholuld be 11 digit") == true){TeacherColumn.add(phone); 
                if(!"".equals(dob)){ TeacherColumn.add(dob);
                 if(id.matches(regexNumber)){ 
                  if(count(id,7,"Id no sholuld be 7 digit") == true){ TeacherColumn.add(id);
                    if(reg.matches(regexNumber)){ 
                        if(count(reg,5,"Reg no sholuld be 5 digit") == true){ TeacherColumn.add(reg);
                            if(nid.matches(regexNumber)){ 
                             if(count(nid,10,"Nid no sholuld be 10 digit") == true){ TeacherColumn.add(nid);
                             
                               if(checkWordIsFoundOrnOT(fac,"select")){TeacherColumn.add(fac);
                                if(checkWordIsFoundOrnOT(dept,"select")){TeacherColumn.add(dept);
                                    if(checkWordIsFoundOrnOT(blood,"select")){TeacherColumn.add(blood);
                                        insertTeacher(TeacherColumn);
                                        
                                        resetField();
                                            alert("success","true","Teacher Successfully Added");
                                    }else{alert("error","true","Select Teacher Blood group");}
                                }else{alert("error","true","Select asociated Depertment");}
                               }else{alert("error","true","Select asociated faculty");}
                               
                             }
                            }else{alert("error","true","wrong Nid");}
                        }
                    }else{alert("error","true","wrong Registration number");}
                  }
                 }else{alert("error","true","wrong Id");}
                }else{alert("error","true","Insert teacher Birth date");}
               }
             }else{alert("error","true","Wrong phone no");}
           }else{alert("error","true","Insert teacher Address");}
         }else{alert("error","true","Name should be alpahbet");}
        
 
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ta_nidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ta_nidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ta_nidActionPerformed

    private void teacherSidebarLogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherSidebarLogoutBtnActionPerformed
        // TODO add your handling code here:
        
        dispose();
                Login log = new Login();
                log.setVisible(true);
    }//GEN-LAST:event_teacherSidebarLogoutBtnActionPerformed

    private void teacherProfileViewBtn_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherProfileViewBtn_ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(23);
    }//GEN-LAST:event_teacherProfileViewBtn_ActionPerformed

    private void teacherDashboardBtn_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teacherDashboardBtn_MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherDashboardBtn_MouseClicked

    private void teacherDashboardBtn_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherDashboardBtn_ActionPerformed
        // TODO add your handling code here:
        if("admin".equals(identity)){ 
          ProjectTab.setSelectedIndex(0);
        }else{
          ProjectTab.setSelectedIndex(24);
        }
    }//GEN-LAST:event_teacherDashboardBtn_ActionPerformed

    private void file2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_file2MouseClicked
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
                teacherSidebarProfilepic.setIcon(ResizeImage(photoPath));
                teacherUpdateProfilepic.setIcon(ResizeImage(photoPath));
                imagePath = photoPath;
                
                AdminDashboards.test f = new AdminDashboards.test();
                rs = f.finds(logeduserid,identity); 
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       }
    }//GEN-LAST:event_file2MouseClicked

    private void jLabel144MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel144MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel144MouseClicked

    private void teacherPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherPhoneActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
            conn cc = new conn();
            random = new Random().nextInt(900000) + 100000;
            String data = String.valueOf(random);
            String toSend = teacherEmail.getText(); // to address. It can be any like gmail, yahoo etc.
            
                sendMail mail = new sendMail(toSend,random,"Verification for Update Profile ");
                if(mail.send()){
                    UpdateToken object = new UpdateToken(identity,"token",data,logeduserid);
                    if(object.UpadetData()){
                        alert("success","true","Please verify your mail");
                        ProjectTab.setSelectedIndex(13);
                    }else{
                        alert("error","true","Something going wrong with database,Try again!");
                    }
                }else{
                    alert("error","true","Check your connection or try after sometime");
                }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void studentPassEye1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPassEye1ActionPerformed
        // TODO add your handling code here:
        PasswordShowAndHide(click);
    }//GEN-LAST:event_studentPassEye1ActionPerformed

    private void teacherProfileView_24ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_teacherProfileView_24ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherProfileView_24ComponentShown

    private void sbloodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbloodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sbloodActionPerformed

    private void shallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shallActionPerformed

    private void back_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_4ActionPerformed
        // TODO add your handling code here:
        facultyAllTeacher.removeAllItems();
        ProjectTab.setSelectedIndex(3);
    }//GEN-LAST:event_back_4ActionPerformed

    private void allTeachersByFacultyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allTeachersByFacultyMouseClicked
        // TODO add your handling code here:
        
             DefaultTableModel table = (DefaultTableModel)allTeachersByFaculty.getModel();
             int id = allTeachersByFaculty.getSelectedRow();
             teacherDetailsId = table.getValueAt(id,1).toString();
    }//GEN-LAST:event_allTeachersByFacultyMouseClicked

    private void facultyAllTeacherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facultyAllTeacherItemStateChanged
        // TODO add your handling code here:
        conn con = new conn();
        String faculty = (String)facultyAllTeacher.getSelectedItem();
        selectedFaculty = faculty;
        String query = "SELECT dept FROM faculty where fac='"+faculty+"'";
        try {
            ps = con.c.prepareStatement(query);
            rs  = ps.executeQuery(query);
            
                facultyAllTeacherSession1.removeAllItems();
                facultyAllTeacherSession1.addItem("All");
                while (rs.next()){ 
                    facultyAllTeacherSession1.addItem(rs.getString("dept"));  
                }
                facultyAllTeacherSession1.getModel().setSelectedItem("All");
                
        }catch (SQLException ex){
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_facultyAllTeacherItemStateChanged

    private void facultyAllTeacherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyAllTeacherActionPerformed
        String deprtment = (String)facultyAllTeacher.getSelectedItem();
        showFacultyTeacher(selectedFacultyName,deprtment);
    }//GEN-LAST:event_facultyAllTeacherActionPerformed

    private void viewStudentfaculty_16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewStudentfaculty_16ActionPerformed
        // TODO add your handling code here:
            DefaultTableModel table = (DefaultTableModel)allTeachersByFaculty.getModel();
             tableRowNo = allTeachersByFaculty.getSelectedRow();
             if(tableRowNo>=0){ 
//                 teachersDetails();
                ProjectTab.setSelectedIndex(26);
             }else{ 
               alert("error","true","No field selected");
             }
    }//GEN-LAST:event_viewStudentfaculty_16ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed
    
    public boolean teacher() throws SQLException{
            ResultSet rs1;
            PreparedStatement pss1=null;
            
            conn cc = new conn();
            String query = "SELECT * FROM teacher where fac='"+selectedFaculty+"'";
            pss1 = cc.c.prepareStatement(query);
            rs1  = pss1.executeQuery(query);
        
        
        if(rs1.next()){ 
          return true;
        }else{
          return false;
        }

        
    }
    
    public void facultyTeacherTable(){
            try {
            ResultSet rs1;
            PreparedStatement pss1=null;
            conn cc = new conn();
            
            DefaultTableModel table = (DefaultTableModel)allTeachersByFaculty.getModel();
            for( int i = table.getRowCount() - 1; i >= 0; i-- ){
                table.removeRow(i);
            }
            
            pss1 = cc.c.prepareStatement("SELECT * FROM faculty ");
            rs1  = pss1.executeQuery("SELECT * FROM faculty ");
            if(rs1.next()){
                facultyAllTeacher.removeAllItems();  
                facultyAllTeacher.getModel().setSelectedItem(selectedFaculty);
                    facultyAllTeacher.addItem("Select Faculty");
                while (rs1.next()){
                    facultyAllTeacher.addItem(rs1.getString("fac"));  
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void adminFacultyTeacher_26ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminFacultyTeacher_26ComponentShown

    }//GEN-LAST:event_adminFacultyTeacher_26ComponentShown

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        try {
            // TODO add your handling code here:
            selectedFaculty = "cse";
            if(teacher()){
                facultyTeacherTable();
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "agri";
            if(teacher()){
                facultyTeacherTable();
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "dvm";
            if(teacher()){
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "bam";
            if(teacher()){
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "fish";
            if(teacher()){
                facultyTeacherTable();
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "nfs";
            if(teacher()){
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel160MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel160MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "esdm";
            if(teacher()){
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel160MouseClicked

    private void jLabel161MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel161MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "lla";
            if(teacher()){
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel161MouseClicked

    private void jLabel162MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel162MouseClicked
        try {
            // TODO add your handling code here:
            selectedFaculty = "ah";
            if(teacher()){
                ProjectTab.setSelectedIndex(25);
            }else{
                alert("error","true","Teacher is not avilable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel162MouseClicked

    private void facultyAllTeacherSession1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facultyAllTeacherSession1ItemStateChanged
        // TODO add your handling code here:
        ResultSet rs = null;
             conn cc = new conn();
             String depert = (String)facultyAllTeacherSession1.getSelectedItem();
             try{
                 
                 if("All".equals(depert)){ 
                   rs = cc.s.executeQuery("select * from teacher where fac='"+selectedFaculty+"'");
                 }else{ 
                   rs = cc.s.executeQuery("select * from teacher where fac='"+selectedFaculty+"' and dept='"+depert+"' ");
                 }
                 
                 DefaultTableModel table = (DefaultTableModel)allTeachersByFaculty.getModel();
                 for( int i = table.getRowCount() - 1; i >= 0; i-- ){
                     table.removeRow(i);
                 }
                 
                 int counts=0;
                 if(rs.next()){
                     do{
                         String name = rs.getString("name");
                         String id = rs.getString("uid");
                         String dep = rs.getString("dept");
                         String email = rs.getString("email");
                         String nid = rs.getString("nid");
                         String st[] = {name,id,dep,email,nid};
                         table.addRow(st);
                         ++counts;
                     }while(rs.next());
                     
                 }
                 
                 if(counts > 0){
                     alert("success","true","Total "+counts+" Teachers found");
                 }else{
                 }
                 
             } catch (SQLException ex){
                 Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
             }
    }//GEN-LAST:event_facultyAllTeacherSession1ItemStateChanged

    private void jLabel198MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel198MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel198MouseClicked

    private void jLabel204MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel204MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel204MouseClicked

    private void jLabel205MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel205MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel205MouseClicked

    private void jLabel208MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel208MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel208MouseClicked

    private void jLabel209MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel209MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel209MouseClicked

    private void jLabel210MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel210MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel210MouseClicked

    private void jLabel211MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel211MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel211MouseClicked

    private void jLabel212MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel212MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel212MouseClicked

    private void jLabel219MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel219MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel219MouseClicked

    private void jLabel220MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel220MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel220MouseClicked

    private void jLabel221MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel221MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel221MouseClicked

    private void jLabel223MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel223MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel223MouseClicked

    private void jLabel224MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel224MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel224MouseClicked

    private void view5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view5ActionPerformed
        // TODO add your handling code here:


                                storeFac.setText("");
                                storeDep.setText("");

          ProjectTab.setSelectedIndex(25);
    }//GEN-LAST:event_view5ActionPerformed

    private void jLabel227MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel227MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel227MouseClicked

    private void sstatusBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sstatusBtn1ActionPerformed
        // TODO add your handling code here:
        
        String text = sstatusBtn1.getText();
        if("Active".equals(text)){
            try {
                status("teacher","Disable");
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{ 
            try {
                status("teacher","Active");
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_sstatusBtn1ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    public void teachersDetails(){         
    }
    private void adminTeacherDetails_27ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminTeacherDetails_27ComponentShown
        // TODO add your handling code here:
               facultyAllTeacher1.setVisible(false);
               facultyAllTeacher2.setVisible(false);
               
               roleCoAdmin.setSelected(false);
               roleRegister.setSelected(false);
               roleDin.setSelected(false);
               roleController.setSelected(false);
               
        if(roleCoAdmin.isSelected()){ 
        }
        if(roleRegister.isSelected()){
        }
        if(roleController.isSelected()){ 
            
        } 
                
                            
                    AdminDashboards.selectForPrint f = new AdminDashboards.selectForPrint();
                    try {
                        
                        //  recive func meathoad return query result                   
                        rs = f.findTeacher(teacherDetailsId);
                        if(rs.next()){
                                byte[] img = rs.getBytes("photo");
                                
                               if(img != null){
                                   
                               
                                int imgs = img[0];
                                if(imgs == -1) {
                                    ImageIcon MyImage1 = new ImageIcon(img);
                                    Image img1 = MyImage1.getImage();
                                    Image newImage1 = img1.getScaledInstance(tDetailsProfilePic.getWidth(), tDetailsProfilePic.getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon image1 = new ImageIcon(newImage1);
                                    tDetailsProfilePic.setIcon(image1);
                                }
                               }else{
                                   alert("error","true","Image is not inserted yet");
                                    tDetailsProfilePic.setIcon(null);
                               }
                               
                               if("active".equals(rs.getString("status"))){
                                    sstatusBtn1.setText("Active");
                                    sstatusBtn1.setBackground(Color.GREEN);
                                    sstatusBtn1.setForeground(Color.BLACK);
                               }else{ 
                                    sstatusBtn1.setText("Disable");
                                    sstatusBtn1.setBackground(Color.RED);
                                    sstatusBtn1.setForeground(Color.WHITE);
                               }
                                
                               
                                tname.setText(rs.getString("name"));
                                tnid.setText(rs.getString("nid"));
                                taddr.setText(rs.getString("addr"));
                                tdob.setText(rs.getString("dob"));
                                tblood.setText(rs.getString("blood"));
                                tphone.setText(rs.getString("phone"));
                                tid.setText(rs.getString("uid"));
                                treg.setText(rs.getString("reg"));
                                temail.setText(rs.getString("email"));
                                tfac.setText(rs.getString("fac"));
                                tdept.setText(rs.getString("dept"));
                                
                                if("din".equals(rs.getString("role"))){ 
                                    roleDin.setSelected(true);
                                }
                                
                                if("register".equals(rs.getString("role"))){ 
                                    roleRegister.setSelected(true);
                                }
                                
                                if("Co-Admin".equals(rs.getString("role"))){ 
                                    roleCoAdmin.setSelected(true);
                                }
                                
                                if("Controller".equals(rs.getString("role"))){ 
                                    roleController.setSelected(true);
                                }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            try {
                                ResultSet rs1;
                                PreparedStatement pss1=null;
                                conn cc = new conn();
                                
                                pss1 = cc.c.prepareStatement("SELECT * FROM faculty ");
                                rs1  = pss1.executeQuery("SELECT * FROM faculty ");
                                if(rs1.next()){
                                        facultyAllTeacher1.removeAllItems();  
                                        facultyAllTeacher1.addItem("Faculty");
                                    while (rs1.next()){
                                        facultyAllTeacher1.addItem(rs1.getString("fac"));  
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    
    }//GEN-LAST:event_adminTeacherDetails_27ComponentShown

    private void adminDashboard_01ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminDashboard_01ComponentShown

    }//GEN-LAST:event_adminDashboard_01ComponentShown

    private void jLabel225MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel225MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel225MouseClicked

    private void roleDinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleDinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roleDinActionPerformed

    private void roleCoAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleCoAdminActionPerformed

    }//GEN-LAST:event_roleCoAdminActionPerformed

    private void printbtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtn3ActionPerformed
        // TODO add your handling code here:
                teacherDetailsPrint ok = new teacherDetailsPrint(studentsDetailsId);
                ok.prints();
    }//GEN-LAST:event_printbtn3ActionPerformed

    private void facultyAllTeacher2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facultyAllTeacher2ItemStateChanged

    }//GEN-LAST:event_facultyAllTeacher2ItemStateChanged

    private void facultyAllTeacher2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyAllTeacher2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facultyAllTeacher2ActionPerformed

    private void facultyAllTeacher1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_facultyAllTeacher1ItemStateChanged

    }//GEN-LAST:event_facultyAllTeacher1ItemStateChanged

    private void facultyAllTeacher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyAllTeacher1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facultyAllTeacher1ActionPerformed

    private void roleRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roleRegisterActionPerformed

    private void roleControllerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleControllerActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_roleControllerActionPerformed

    private void adminStudentAdd_13ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminStudentAdd_13ComponentShown
        // TODO add your handling code here:
        nameVal.setBorder(BorderFactory.createCompoundBorder(nameVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        fnameVal.setBorder(BorderFactory.createCompoundBorder(fnameVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        mnameVal.setBorder(BorderFactory.createCompoundBorder(mnameVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        addrVal.setBorder(BorderFactory.createCompoundBorder(addrVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        nidVal.setBorder(BorderFactory.createCompoundBorder(nidVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        dobVal.setBorder(BorderFactory.createCompoundBorder(dobVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        idVal.setBorder(BorderFactory.createCompoundBorder(idVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        regVal.setBorder(BorderFactory.createCompoundBorder(regVal.getBorder(), BorderFactory.createEmptyBorder(6, 6, 6, 6)));
    }//GEN-LAST:event_adminStudentAdd_13ComponentShown

    private void adminTeacherAdd_23ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminTeacherAdd_23ComponentShown
        // TODO add your handling code here:
        ta_name.setBorder(BorderFactory.createCompoundBorder(ta_name.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        ta_addr.setBorder(BorderFactory.createCompoundBorder(ta_addr.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        ta_phone.setBorder(BorderFactory.createCompoundBorder(ta_phone.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        ta_dob.setBorder(BorderFactory.createCompoundBorder(ta_dob.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        ta_id.setBorder(BorderFactory.createCompoundBorder(ta_id.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        ta_reg.setBorder(BorderFactory.createCompoundBorder(ta_reg.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        ta_nid.setBorder(BorderFactory.createCompoundBorder(ta_nid.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
    }//GEN-LAST:event_adminTeacherAdd_23ComponentShown

    private void adminTeacher_04ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_adminTeacher_04ComponentShown
        // TODO add your handling code here:
           
    }//GEN-LAST:event_adminTeacher_04ComponentShown

    private void serchTeacherWithIdComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_serchTeacherWithIdComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_serchTeacherWithIdComponentShown

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        
        if(ip==0){
            jPanel1.setVisible(true);
            ip=1;
            System.out.println("open");
        }else{
            jPanel1.setVisible(false);
            System.out.println("Close");
            ip=0;
        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private void adminHallBtn_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminHallBtn_7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminHallBtn_7ActionPerformed

    private void adminTransectionBtn_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTransectionBtn_9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminTransectionBtn_9ActionPerformed

    private void adminTeacherBtn_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminTeacherBtn_5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminTeacherBtn_5ActionPerformed

    private void adminStudentBtn_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminStudentBtn_4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminStudentBtn_4ActionPerformed

    private void llaSession_17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_llaSession_17ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_llaSession_17ItemStateChanged

    private void llaSession_17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_llaSession_17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_llaSession_17ActionPerformed

    private void roleDinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_roleDinStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_roleDinStateChanged

    private void serchIdField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdField1FocusGained
        // TODO add your handling code here:
        serchIdField1.setText("");
    }//GEN-LAST:event_serchIdField1FocusGained

    private void serchIdField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchIdField1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField1FocusLost

    private void serchIdField1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_serchIdField1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField1ComponentShown

    private void serchIdField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchIdField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serchIdField1ActionPerformed

    private void searchStudent_17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStudent_17ActionPerformed
        // TODO add your handling code here:
             studentsDetailsId = serchIdField1.getText();
             if("".equals(studentsDetailsId) || "Search with student id".equals(studentsDetailsId)){ 
                alert("error","true","Insert student id");
             }else{ 
             ProjectTab.setSelectedIndex(15);     
               serchIdField1.setText("Search with student id");
             }
    }//GEN-LAST:event_searchStudent_17ActionPerformed

    private void serchTeacherWithId1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchTeacherWithId1FocusGained
        // TODO add your handling code here:
        serchTeacherWithId1.setText("");
    }//GEN-LAST:event_serchTeacherWithId1FocusGained

    private void serchTeacherWithId1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serchTeacherWithId1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_serchTeacherWithId1FocusLost

    private void serchTeacherWithId1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_serchTeacherWithId1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_serchTeacherWithId1ComponentShown

    private void serchTeacherWithId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchTeacherWithId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serchTeacherWithId1ActionPerformed

    private void addNewStudent4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStudent4ActionPerformed
        // TODO add your handling code here:
            teacherDetailsId = serchTeacherWithId1.getText();
            if("".equals(teacherDetailsId) || " Search with Teacher id".equals(teacherDetailsId)){ 
               alert("error","true","Insert Teacher id");
            }else{ 
                ProjectTab.setSelectedIndex(26);     
                serchTeacherWithId1.setText("Search with teacher id");
            }
    }//GEN-LAST:event_addNewStudent4ActionPerformed

    private void adminCourseBtn_28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminCourseBtn_28ActionPerformed
        // TODO add your handling code here:
        ProjectTab.setSelectedIndex(27);
    }//GEN-LAST:event_adminCourseBtn_28ActionPerformed

    private void cocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cocodeActionPerformed

    private void conameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conameActionPerformed

    private void cohourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cohourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cohourActionPerformed

    private void cofacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cofacItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cofacItemStateChanged

    private void cofacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cofacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cofacActionPerformed

    private void addStudentBtn_14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtn_14ActionPerformed
        // TODO add your handling code here:
        String fac = (String)cofac.getSelectedItem();
        fac = fac.toLowerCase();
        
        String name = coname.getText();
        String code = cocode.getText();
        String hour = cohour.getText();
        System.out.println(fac);
          conn cc = new conn();
          ResultSet rs = null;
        if(checkWordIsFoundOrnOT(fac,"select")){ 
            fac = getfirstLeter_replace_some_speacial_charecter_and_word(fac);
           if(!"".equals(name)){ 
                if(!"".equals(code)){
                    if(!"".equals(hour)){
                        try {
                            rs = cc.s.executeQuery("select * from course where faculty='"+fac+"' and name='"+name+"' and code='"+code+"' ") ;
                            
                            if(rs.next()){
                                alert("error","true","This course is already inserted");
                            }else{ 
                                    String query = " insert into course (faculty,name,code,creadit)"
                                        + " values (?, ?, ?, ?)";

                                      PreparedStatement preparedStmt = cc.c.prepareStatement(query);
                                      preparedStmt.setString (1, fac);
                                      preparedStmt.setString (2, name);
                                      preparedStmt.setString (3, code);
                                      preparedStmt.setString (4, hour);
                                      preparedStmt.execute();
                                      cc.c.close();
                                      
                                      getCourse();
                                   alert("success","true","Course Successfully Added");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{ 
                        alert("error","true","Course Credit hour is empty");
                    }
                }else{ 
                    alert("error","true","Course code is empty");
                 }
           }else{ 
              alert("error","true","Course name is empty");
           }
        }else{ 
           alert("error","true","Select faculty");
        }
    }//GEN-LAST:event_addStudentBtn_14ActionPerformed

    public void getCourse(){
             conn cc = new conn();
             ResultSet rs = null;
             try{
                
                 rs = cc.s.executeQuery("select * from course");
                 
                 DefaultTableModel table = (DefaultTableModel)courseTable.getModel();
                 for( int i = table.getRowCount() - 1; i >= 0; i-- ){
                     table.removeRow(i);
                 }
                 
                 int counts=0;
                 if(rs.next()){
                     do{
                         String fac = rs.getString("faculty");
                         String code = rs.getString("code");
                         String name = rs.getString("name");
                         String credits = rs.getString("creadit");
                        
                         String st[] = {fac,name,code,credits};
                         table.addRow(st);
                         ++counts;
                     }while(rs.next());
                     
                 }
                 
                 if(counts > 0){
                     alert("success","true","Total "+counts+" Course found");
                 }else{
                     alert("error","true","No course added yet");
                 }
                 
             } catch (SQLException ex){
                 Logger.getLogger(AdminDashboards.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    
    private void course_28ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_course_28ComponentShown
        // TODO add your handling code here:
       getCourse();
    }//GEN-LAST:event_course_28ComponentShown

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
    private javax.swing.JPanel AccountVerify_14;
    private javax.swing.JPanel AlertPanel;
    private javax.swing.JPanel DinSidebar;
    private javax.swing.JTabbedPane ProjectTab;
    private javax.swing.JPanel StudentSideBarPanel;
    private javax.swing.JButton accountverifyForUpdateProfile;
    private javax.swing.JButton addNewStudent3;
    private javax.swing.JButton addNewStudent4;
    private javax.swing.JButton addStudentBtn2;
    private javax.swing.JButton addStudentBtn_13;
    private javax.swing.JButton addStudentBtn_14;
    private javax.swing.JTextField addrVal;
    private javax.swing.JPanel adminAdministration_10;
    private javax.swing.JLabel adminBlood;
    private javax.swing.JLabel adminBlood1;
    private javax.swing.JLabel adminBlood2;
    private javax.swing.JLabel adminBlood6;
    private javax.swing.JButton adminCourseBtn_28;
    private javax.swing.JPanel adminDashboard_01;
    private javax.swing.JLabel adminDob;
    private javax.swing.JLabel adminDob1;
    private javax.swing.JLabel adminDob10;
    private javax.swing.JLabel adminDob11;
    private javax.swing.JLabel adminDob13;
    private javax.swing.JLabel adminDob2;
    private javax.swing.JLabel adminDob25;
    private javax.swing.JLabel adminDob27;
    private javax.swing.JLabel adminDob28;
    private javax.swing.JLabel adminDob29;
    private javax.swing.JLabel adminDob3;
    private javax.swing.JLabel adminDob30;
    private javax.swing.JLabel adminDob31;
    private javax.swing.JLabel adminDob33;
    private javax.swing.JLabel adminDob34;
    private javax.swing.JLabel adminDob35;
    private javax.swing.JLabel adminDob36;
    private javax.swing.JLabel adminDob4;
    private javax.swing.JLabel adminDob5;
    private javax.swing.JLabel adminDob6;
    private javax.swing.JLabel adminDob7;
    private javax.swing.JLabel adminDob8;
    private javax.swing.JLabel adminDob9;
    private javax.swing.JTextField adminEmail;
    private javax.swing.JPanel adminFacultyStudent_15;
    private javax.swing.JPanel adminFacultyTeacher_26;
    private javax.swing.JPanel adminFaculty_05;
    private javax.swing.JButton adminHallBtn_06;
    private javax.swing.JButton adminHallBtn_7;
    private javax.swing.JPanel adminHall_06;
    private javax.swing.JButton adminHealthCareBtn_09;
    private javax.swing.JPanel adminHealthCare_09;
    private javax.swing.JLabel adminId;
    private javax.swing.JButton adminLibraryBtn_07;
    private javax.swing.JPanel adminLibrary_07;
    private javax.swing.JLabel adminName;
    private javax.swing.JLabel adminNid;
    private javax.swing.JLabel adminNid2;
    private javax.swing.JLabel adminNid3;
    private javax.swing.JLabel adminNid6;
    private javax.swing.JLabel adminNid7;
    private javax.swing.JPanel adminOrganaization_11;
    private javax.swing.JButton adminPassEye;
    private javax.swing.JPasswordField adminPassword;
    private javax.swing.JTextField adminPhone;
    private javax.swing.JButton adminProfileBtn_02;
    private javax.swing.JPanel adminProfile_02;
    private javax.swing.JPanel adminSidebar;
    private javax.swing.JPanel adminStudentAdd_13;
    private javax.swing.JButton adminStudentBtn_03;
    private javax.swing.JButton adminStudentBtn_4;
    private javax.swing.JPanel adminStudentDetails_16;
    private javax.swing.JPanel adminStudent_03;
    private javax.swing.JPanel adminTeacherAdd_23;
    private javax.swing.JButton adminTeacherBtn_04;
    private javax.swing.JButton adminTeacherBtn_5;
    private javax.swing.JPanel adminTeacherDetails_27;
    private javax.swing.JPanel adminTeacher_04;
    private javax.swing.JButton adminTransectionBtn_08;
    private javax.swing.JButton adminTransectionBtn_9;
    private javax.swing.JPanel adminTransection_08;
    private javax.swing.JPanel adminTranspoart_12;
    private javax.swing.JLabel adminUpdateProfilePic;
    private javax.swing.JTextField adminUsername;
    private javax.swing.JComboBox<String> agriSession_16;
    private javax.swing.JComboBox<String> ahSession_16;
    private javax.swing.JLabel alertSuccessText;
    private javax.swing.JTable allStudentsByFaculty;
    private javax.swing.JTable allTeachersByFaculty;
    private javax.swing.JButton back_03;
    private javax.swing.JButton back_4;
    private javax.swing.JComboBox<String> bamSession_16;
    private javax.swing.JLabel bg1;
    private javax.swing.JLabel bg2;
    private javax.swing.JComboBox<String> bloodVal;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cocode;
    private javax.swing.JComboBox<String> cofac;
    private javax.swing.JTextField cohour;
    private javax.swing.JTextField coname;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JTable courseTable;
    private javax.swing.JPanel course_28;
    private javax.swing.JComboBox<String> cseSession_16;
    private com.toedter.calendar.JDateChooser dobVal;
    private javax.swing.JComboBox<String> dvmSession_16;
    private javax.swing.JLabel error;
    private javax.swing.JButton errorClose;
    private javax.swing.JLabel errorLogo;
    private javax.swing.JLabel errorText;
    private javax.swing.JComboBox<String> esdmSession_16;
    private javax.swing.JComboBox<String> facVal;
    private javax.swing.JComboBox<String> facultyAllStudentSession;
    private javax.swing.JComboBox<String> facultyAllTeacher;
    private javax.swing.JComboBox<String> facultyAllTeacher1;
    private javax.swing.JComboBox<String> facultyAllTeacher2;
    private javax.swing.JComboBox<String> facultyAllTeacherSession1;
    private javax.swing.JLabel file;
    private javax.swing.JLabel file1;
    private javax.swing.JLabel file2;
    private javax.swing.JComboBox<String> fishSession_16;
    private javax.swing.JTextField fnameVal;
    private javax.swing.JComboBox<String> hallVal;
    private javax.swing.JTextField idVal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
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
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel labelForShowTeacherAdminTitlePic;
    private javax.swing.JLabel labelForShowTeacherDinTitlePic;
    private javax.swing.JLabel labelForShowTeacherTitlePic;
    private javax.swing.JComboBox<String> llaSession_16;
    private javax.swing.JComboBox<String> llaSession_17;
    private javax.swing.JTextField mnameVal;
    private javax.swing.JTextField nameVal;
    private javax.swing.JComboBox<String> nfsSession_16;
    private javax.swing.JTextField nidVal;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton printbtn3;
    private javax.swing.JTextField regVal;
    private javax.swing.JCheckBox roleCoAdmin;
    private javax.swing.JCheckBox roleController;
    private javax.swing.JCheckBox roleDin;
    private javax.swing.JCheckBox roleRegister;
    private javax.swing.JLabel sDetailsProfilePic;
    private javax.swing.JTextField saddr;
    private javax.swing.JTextField sblood;
    private javax.swing.JTextField sdob;
    private javax.swing.JButton searchStudent_16;
    private javax.swing.JButton searchStudent_17;
    private javax.swing.JTextField semail;
    private javax.swing.JButton sendUpdateVerificationCode_14;
    private javax.swing.JTextField serchIdField;
    private javax.swing.JTextField serchIdField1;
    private javax.swing.JTextField serchTeacherWithId;
    private javax.swing.JTextField serchTeacherWithId1;
    private javax.swing.JComboBox<String> sessionVal;
    private javax.swing.JTextField sfac;
    private javax.swing.JTextField sfname;
    private javax.swing.JTextField shall;
    private javax.swing.JTextField sid;
    private javax.swing.JTextField smname;
    private javax.swing.JTextField sname;
    private javax.swing.JTextField snid;
    private javax.swing.JTextField sphone;
    private javax.swing.JTextField sreg;
    private javax.swing.JTextField ssem;
    private javax.swing.JTextField ssession;
    private javax.swing.JButton sstatusBtn;
    private javax.swing.JButton sstatusBtn1;
    private javax.swing.JLabel storeDep;
    private javax.swing.JLabel storeFac;
    private javax.swing.JLabel studentBlood;
    private javax.swing.JButton studentDashboardBtn_18;
    private javax.swing.JPanel studentDashboard_18;
    private javax.swing.JLabel studentDob;
    private javax.swing.JTextField studentEmail;
    private javax.swing.JButton studentExamBtn_21;
    private javax.swing.JPanel studentExam_21;
    private javax.swing.JButton studentFacultyBtn_20;
    private javax.swing.JPanel studentFaculty_20;
    private javax.swing.JLabel studentName;
    private javax.swing.JLabel studentNid;
    private javax.swing.JButton studentPassEye;
    private javax.swing.JButton studentPassEye1;
    private javax.swing.JPasswordField studentPassword;
    private javax.swing.JButton studentPaymentBtn_19;
    private javax.swing.JPanel studentPayment_19;
    private javax.swing.JTextField studentPhone;
    private javax.swing.JButton studentProfileViewBtn_17;
    private javax.swing.JPanel studentProfileView_17;
    private javax.swing.JButton studentResultBtn_22;
    private javax.swing.JPanel studentResult_22;
    private javax.swing.JButton studentSidebarLogoutBtn;
    private javax.swing.JLabel studentSidebarProfilepic;
    private javax.swing.JLabel studentUpdateProfilePic;
    private javax.swing.JTextField studentUsername;
    private javax.swing.JLabel success;
    private javax.swing.JButton successClose;
    private javax.swing.JLabel successLogo;
    private javax.swing.JLabel successText;
    private javax.swing.JLabel tDetailsProfilePic;
    private javax.swing.JTextField ta_addr;
    private javax.swing.JComboBox<String> ta_blood;
    private javax.swing.JComboBox<String> ta_dept;
    private com.toedter.calendar.JDateChooser ta_dob;
    private javax.swing.JComboBox<String> ta_fac;
    private javax.swing.JTextField ta_id;
    private javax.swing.JTextField ta_name;
    private javax.swing.JTextField ta_nid;
    private javax.swing.JTextField ta_phone;
    private javax.swing.JTextField ta_reg;
    private javax.swing.JTextField taddr;
    private javax.swing.JTextField tblood;
    private javax.swing.JTextField tdept;
    private javax.swing.JTextField tdob;
    private javax.swing.JLabel teacherBlood;
    private javax.swing.JButton teacherDashboardBtn_;
    private javax.swing.JPanel teacherDashboard_25;
    private javax.swing.JLabel teacherDob;
    private javax.swing.JTextField teacherEmail;
    private javax.swing.JLabel teacherName;
    private javax.swing.JLabel teacherNid;
    private javax.swing.JPasswordField teacherPassword;
    private javax.swing.JTextField teacherPhone;
    private javax.swing.JButton teacherProfileViewBtn_;
    private javax.swing.JPanel teacherProfileView_24;
    private javax.swing.JPanel teacherSideBarPanel;
    private javax.swing.JButton teacherSidebarLogoutBtn;
    private javax.swing.JLabel teacherSidebarProfilepic;
    private javax.swing.JLabel teacherUpdateProfilepic;
    private javax.swing.JTextField teacherUsername;
    private javax.swing.JTextField temail;
    private javax.swing.JTextField tfac;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tname;
    private javax.swing.JTextField tnid;
    private javax.swing.JLabel totalStudent;
    private javax.swing.JLabel totalStudent1;
    private javax.swing.JTextField tphone;
    private javax.swing.JTextField treg;
    private javax.swing.JTextField verifyUsername;
    private javax.swing.JButton view3;
    private javax.swing.JButton view5;
    private javax.swing.JButton viewStudentfaculty_15;
    private javax.swing.JButton viewStudentfaculty_16;
    private javax.swing.JTextField vkey;
    // End of variables declaration//GEN-END:variables
}
