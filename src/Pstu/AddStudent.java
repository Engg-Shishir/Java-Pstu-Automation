
package Pstu;

import java.awt.*;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.Border;

import java.io.*;
import java.nio.file.*;





public class AddStudent extends JFrame implements  ActionListener{

    JFrame f;
    JLabel title,studentGig,background,name,fname,mname,roll,reg,session,dob,addr,fac,hall,nid,blood;
    JTextField nameVal,fnameVal,mnameVal,rollVal,regVal,dobVal,addrVal,sessionVal,nidVal;
    JButton submit,close,closeBtn;
    JComboBox faculty,hallSelect,bloodSelect;
    
    String photoPath="", imgExtention="";
    

    public AddStudent(){
        
        Border emptyBorder = BorderFactory.createEmptyBorder();
        
        background=new JLabel();
        background.setBounds(0,0,1000,600);
        background.setLayout(null);
        background.setOpaque(true);
        background.setBackground(new Color(0, 10, 66));

        studentGig = new JLabel();
        studentGig.setBounds(150,-5,120,120);
        ImageIcon img2 = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/student.gif"));
        Image i32 = img2.getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT);
        ImageIcon icc32 = new ImageIcon(i32);
        studentGig.setIcon(icc32);
        background.add(studentGig);
        
        
        title = new JLabel("Add New Student");
        title.setBounds(420,30,500,50);
        title.setFont(new Font("serif",Font.ITALIC,35));
        title.setForeground(Color.white);
        background.add(title);
        add(background);
        
        JSeparator sep = new JSeparator();  
        sep.setOrientation(SwingConstants.HORIZONTAL);
        sep.setBounds(220,80,600,50);
        sep.setBackground(Color.white);
        background.add(sep);  
        
        
        //##################################################
        name = new JLabel("Name :");
        name.setBounds(100,150,100,30);
        name.setFont(new Font("serif",Font.BOLD,20));
        name.setForeground(Color.WHITE);
        background.add(name);
        //##################################################
        fname= new JLabel("Father Name : ");
        fname.setBounds(100,200,150,30);
        fname.setFont(new Font("serif",Font.BOLD,20));
        fname.setForeground(Color.WHITE);
        background.add(fname);
        //##################################################
        mname= new JLabel("Mother Name : ");
        mname.setBounds(100,250,150,30);
        mname.setFont(new Font("serif",Font.BOLD,20));
        mname.setForeground(Color.WHITE);
        background.add(mname);
        //##################################################
        addr= new JLabel("Address : ");
        addr.setBounds(100,300,150,30);
        addr.setFont(new Font("serif",Font.BOLD,20));
        addr.setForeground(Color.WHITE);
        background.add(addr);
        //##################################################
        session= new JLabel("Session : ");
        session.setBounds(100,350,150,30);
        session.setFont(new Font("serif",Font.BOLD,20));
        session.setForeground(Color.WHITE);
        background.add(session);
        //##################################################
        nid= new JLabel("Nid No : ");
        nid.setBounds(100,400,150,30);
        nid.setFont(new Font("serif",Font.BOLD,20));
        nid.setForeground(Color.WHITE);
        background.add(nid);
        //##################################################
//        image= new JLabel("Image : ");
//        image.setBounds(100,450,150,30);
//        image.setFont(new Font("serif",Font.BOLD,20));
//        image.setForeground(Color.WHITE);
//        background.add(image);
        
        //##################################################      
        //##################################################   
        //##################################################   
        //##################################################   
        nameVal=new JTextField(); 
        nameVal.setBounds(260,150,200,30);
        background.add(nameVal);
        nameVal.addActionListener(this);
        //################################################## 
        fnameVal=new JTextField();
        fnameVal.setBounds(260,200,200,30);
        background.add(fnameVal);
        //################################################## 
        mnameVal=new JTextField();
        mnameVal.setBounds(260,250,200,30);
        background.add(mnameVal);
        //################################################## 
        addrVal=new JTextField();
        addrVal.setBounds(260,300,200,30);
        background.add(addrVal);
        //################################################## 
        sessionVal=new JTextField();
        sessionVal.setBounds(260,350,200,30);
        background.add(sessionVal);
        //################################################## 
        nidVal=new JTextField();
        nidVal.setBounds(260,400,200,30);
        background.add(nidVal);

        
        //################################################## 
//        file = new JLabel();
//        file.setBounds(420,450,30,30);
//        file.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        
//       ImageIcon f = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/open-folder.png"));
//       Image i3f = f.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
//       ImageIcon icc3f = new ImageIcon(i3f);
//       file.setIcon(icc3f);
//       background.add(file);
        
//        file.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//                 int result = file.showSaveDialog(null);
//                    if(result == JFileChooser.APPROVE_OPTION){
//                        File selectedFile = file.getSelectedFile();
//                        photoPath = selectedFile.getAbsolutePath();
//                    }
//            }
//
//        });
        

        
        //##################################################
        roll = new JLabel("Roll No :");
        roll.setBounds(500,150,200,30);
        roll.setFont(new Font("serif",Font.BOLD,20));
        roll.setForeground(Color.WHITE);
        background.add(roll); 
        //##################################################
        reg = new JLabel("Registration No :");
        reg.setBounds(500,200,200,30);
        reg.setFont(new Font("serif",Font.BOLD,20));
        reg.setForeground(Color.WHITE);
        background.add(reg);  
        //##################################################
        dob = new JLabel("Birth Date :");
        dob.setBounds(500,250,200,30);
        dob.setFont(new Font("serif",Font.BOLD,20));
        dob.setForeground(Color.WHITE);
        background.add(dob); 
        //##################################################
        blood = new JLabel("Blood group :");
        blood.setBounds(500,300,200,30);
        blood.setFont(new Font("serif",Font.BOLD,20));
        blood.setForeground(Color.WHITE);
        background.add(blood);  
        //##################################################
        fac = new JLabel("Chose your faculty :");
        fac.setBounds(500,350,200,30);
        fac.setFont(new Font("serif",Font.BOLD,20));
        fac.setForeground(Color.WHITE);
        background.add(fac); 
        //##################################################
        hall = new JLabel("Select Your Hall :");
        hall.setBounds(500,400,200,30);
        hall.setFont(new Font("serif",Font.BOLD,20));
        hall.setForeground(Color.WHITE);
        background.add(hall); 
        
        
        //##################################################
        rollVal=new JTextField(); 
        rollVal.setBounds(700,150,200,30);
        background.add(rollVal);
        //##################################################
        regVal=new JTextField(); 
        regVal.setBounds(700,200,200,30);
        background.add(regVal);
        //##################################################
        dobVal=new JTextField(); 
        dobVal.setBounds(700,250,200,30);
        background.add(dobVal);
        //##################################################
        String bloods[] = {"Select blood group","Unknown","B +","AB +","B -","O +","O +","AB +"};
        bloodSelect = new JComboBox(bloods);
        bloodSelect.setBackground(Color.WHITE);
        bloodSelect.setBounds(700,300,200,30);
        background.add(bloodSelect);
        //##################################################
        String facultys[] = {"Select Faculty","Computer Science & Engineering","Agriculture","Nutrion & Food Science","Mechanical","Civil"};
        faculty = new JComboBox(facultys);
        faculty.setBackground(Color.WHITE);
        faculty.setBounds(700,350,200,30);
        background.add(faculty);
        //##################################################
        String halls[] = {"Select your hall","Sher-E-Bangla-1","Sher-E-Bangla-2","Bangabandhu Shekh Muzib","Keramot Ali","Shekh Fazilatunneca Muzib","Sufiya Kamal"};
        hallSelect = new JComboBox(halls);
        hallSelect.setBackground(Color.WHITE);
        hallSelect.setBounds(700,400,200,30);
        background.add(hallSelect);
        
        submit = new JButton();
        submit.setBounds(250,500,120,40);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon submitBtn = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/submit.png"));
        Image submitBtnSize = submitBtn.getImage().getScaledInstance(130, 40,Image.SCALE_DEFAULT);
        ImageIcon submitBtnIcon = new ImageIcon(submitBtnSize);
        submit.setIcon(submitBtnIcon);
        
        submit.setBorder(emptyBorder);
        background.add(submit);
        
        close = new JButton();
        close.setBounds(550,500,130,40);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon closeBtns = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/close.png"));
        Image closeBtnSize = closeBtns.getImage().getScaledInstance(140, 40,Image.SCALE_DEFAULT);
        ImageIcon closeBtnIcon = new ImageIcon(closeBtnSize);
        close.setIcon(closeBtnIcon);
                
        close.setBorder(emptyBorder);
        background.add(close);
        
        closeBtn = new JButton();
        closeBtn.setBounds(900,30,30,30);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/close1.png"));
        Image i3 = img.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
        ImageIcon icc3 = new ImageIcon(i3);
        closeBtn.setIcon(icc3);
        closeBtn.setBorder(emptyBorder);
        background.add(closeBtn);
        
        

        
        
        
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                  System.exit(0);
            }

        });
        
       
        submit.addActionListener(this);
        close.addActionListener(this);
       
       
        setUndecorated(true);
        setVisible(true);
        setSize(1000,600);
        setLocationRelativeTo(null); 
    }
//name,fname,mname,roll,reg,dob,email,addr,fac,phone,hall,image,nid,blood
    public void actionPerformed(ActionEvent ae){
        String name = nameVal.getText();
        String fname = fnameVal.getText();
        String mname = mnameVal.getText();
        String roll = rollVal.getText();
        String reg =  regVal.getText();
        String session =  sessionVal.getText();
        String dob =  dobVal.getText();
        String addr = addrVal.getText();
        String fac = (String)faculty.getSelectedItem();
        String hall = (String)hallSelect.getSelectedItem();
        String nid =  nidVal.getText();
        String blood = (String)bloodSelect.getSelectedItem();
        
        
        if(ae.getSource() == submit){
                
//            if(photoPath.length()>0){
//                // create object of Path
//                Path paths = Paths.get(photoPath);
//                // call getFileName() and get FileName path object
//                Path fileName = paths.getFileName();
//                img = fileName.toString();
//                int index = img.lastIndexOf('.');
//                if(index > 0) {
//                     imgExtention = img.substring(index + 1);
//                     img = roll+"."+imgExtention;
//                }
//            }
      
            
            try{
                conn cc = new conn();
                if(name.equals("") || fname.equals("")|| mname.equals("")|| roll.equals("") || reg.equals("")|| dob.equals("")||addr.equals("")|| fac.equals("")|| session.equals("")|| hall.equals("")|| blood.equals("")){

                      new ToastAlert("Please insert carefully","warningToast.png","close1.png").setVisible(true);
                }else{
//                    File src = new File(photoPath);
//                    File dest = new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Java Pstu Automation\\src\\university\\management\\system\\upload\\"+img);
//

//                    //   rename & Copy selected file                     
//                    try {
//                        Files.copy(src.toPath(), dest.toPath());
//                    } catch (IOException ex) {
//                        System.out.println("Exception is here");
//                    }
                    
                    String query = "INSERT INTO `student`(`name`, `fname`, `mname`, `addr`, `session`, `nid`,`roll`, `reg`, `dob`, `fac`, `hall`, `blood`) VALUES ('"+name+"','"+fname+"','"+mname+"','"+addr+"','"+session+"','"+nid+"','"+roll+"','"+reg+"','"+dob+"','"+fac+"','"+hall+"','"+blood+"')";
                    int i = cc.s.executeUpdate(query);
                    if(i==1){
                        this.dispose();
                      new ToastAlert("Student Added successfully","checkToast.png","delete2.png").setVisible(true);
                        
                    }
                }            
            }catch(Exception ee){
                System.out.println("The error is:"+ee);
            }
        }else{
            System.exit(0);
        }

    }
    public static void main(String[ ] arg){
        new AddStudent().setVisible(true);
    }
}