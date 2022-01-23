/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.Border;

import java.io.*;
import java.nio.file.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class adminDashboard extends JFrame {
    JLabel background,sdeComponent,profilePic,showName,showEmail,sCard,tCard,hCard,dCard,lCard,fCard,oCard,trCard,gcard;
    JButton ChangeProfile,logout,student,teacher,hall,doctor,library,organization,transport,guard;
    JButton close,minimize,sCardTitle,tCardBtn,hCardBtn,dCardBtn,lCardBtn,fcardBtn,ocardBtn,trCardBtn,gCardBtn;
    JPanel mainBackground;
    
    public String getImageName = "";
    public static int adminTeacherVisible = 0,adminStudentVisible = 0;
//    new adminStudent().setVisible(true);
    
    
    public adminDashboard(String username, String identity){
        Border emptyBorder = BorderFactory.createEmptyBorder();
        
//        
        conn cc = new conn();
        String q = "select * from student where name='"+username+"'";
        try { 
            ResultSet rs = cc.s.executeQuery(q);
            if(rs.next()){
             getImageName = rs.getString(16);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        
        background=new JLabel();
        background.setBounds(0,0,width-300,height-200);
        background.setLayout(null);
        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("photo/bg6.jpg"));
        Image bgImgIcon = bgImg.getImage().getScaledInstance(width, height,Image.SCALE_FAST);
        ImageIcon iconBg = new ImageIcon(bgImgIcon);
        background.setIcon(iconBg);
        add(background);
        
               //##################################################
        close = new JButton();
        close.setBounds(width-350,15,30,30);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon closeImg = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/close1.png"));
        Image closeIcon = closeImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconClose = new ImageIcon(closeIcon);
        close.setIcon(iconClose);
        close.setBorder(emptyBorder);
        background.add(close);
        
        
        
//        sdeComponent=new JLabel();
//        sdeComponent.setBounds(483,0,1137,height-200);
//        sdeComponent.setLayout(null);
//        ImageIcon sdeComponentmg = new ImageIcon(ClassLoader.getSystemResource("photo/bg6.jpg"));
//        Image sdeComponentIcon = sdeComponentmg.getImage().getScaledInstance(width, height,Image.SCALE_FAST);
//        ImageIcon sdeComponentBg = new ImageIcon(sdeComponentIcon);
//        sdeComponent.setIcon(sdeComponentBg);
//        background.add(sdeComponent);
//        
        
        
        
        
 
        //##################################################
        profilePic=new JLabel();
        profilePic.setBounds(60,15,150,150);
        profilePic.setLayout(null);
        ImageIcon profilePicImg;
        if(getImageName==null){
            profilePicImg = new ImageIcon(ClassLoader.getSystemResource("university/management/system/upload/default.png"));
        }else{
            profilePicImg = new ImageIcon(ClassLoader.getSystemResource("university/management/system/upload/"+getImageName));
        }
        
        Image profilePicIcon = profilePicImg.getImage().getScaledInstance(profilePic.getWidth(), profilePic.getHeight(),Image.SCALE_FAST);
        ImageIcon profilePicBg = new ImageIcon(profilePicIcon);
        profilePic.setIcon(profilePicBg);
        background.add(profilePic);
        //##################################################
        showName = new JLabel("Loged User namme");
        showName.setBounds(60,150,400,60);
        showName.setFont(new Font("serif",Font.BOLD,25));
        showName.setForeground(Color.WHITE);
        background.add(showName); 
        
        
        //##################################################
        showEmail = new JLabel("shishir@gmail.com");
        showEmail.setBounds(60,175,400,60);
        showEmail.setFont(new Font("serif",Font.BOLD,20));
        showEmail.setForeground(Color.WHITE);
        background.add(showEmail); 
        //##################################################
        JSeparator sep = new JSeparator();  
        sep.setOrientation(SwingConstants.HORIZONTAL);
        sep.setBounds(60,230,250,100);
        sep.setBackground(Color.white);
        background.add(sep);
        
        
        
        //##################################################
        sCard=new JLabel();
        sCard.setBounds(60,310,130,130);
        sCard.setLayout(null);
        ImageIcon sCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/student.png"));
        Image sCardIcon = sCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon sCardBg = new ImageIcon(sCardIcon);
        sCard.setIcon(sCardBg);
        background.add(sCard);
        
        
        //##################################################
        sCardTitle = new JButton("Student");
        sCardTitle.setBounds(60,440,130,40);
        sCardTitle.setFont(new Font("serif",Font.BOLD,20));
        sCardTitle.setForeground(Color.WHITE);
        sCardTitle.setBackground(new Color(65, 12, 62));
        sCardTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon sCardTitleImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image sCardTitleIcon = sCardTitleImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconsCardTitle = new ImageIcon(sCardTitleIcon);
        sCardTitle.setIcon(iconsCardTitle);
        sCardTitle.setBorder(emptyBorder);
        background.add(sCardTitle);
        
        sCardTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(adminStudentVisible==0){
                    adminStudentVisible=1;
                    new adminStudent().setVisible(true);
                }
            }

        });
        
        
        //##################################################
        tCard=new JLabel();
        tCard.setBounds(200,310,130,130);
        tCard.setLayout(null);
        ImageIcon tCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/teacher.png"));
        Image tCardIcon = tCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon tCardBg = new ImageIcon(tCardIcon);
        tCard.setIcon(tCardBg);
        background.add(tCard);
        //##################################################
        tCardBtn = new JButton("Teacher");
        tCardBtn.setBounds(200,440,130,40);
        tCardBtn.setFont(new Font("serif",Font.BOLD,20));
        tCardBtn.setForeground(Color.WHITE);
        tCardBtn.setBackground(new Color(65, 12, 62));
        tCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon tCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image tCardBtnIcon = tCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon icontCardBtn = new ImageIcon(tCardBtnIcon);
        tCardBtn.setIcon(icontCardBtn);
        tCardBtn.setBorder(emptyBorder);
        background.add(tCardBtn);
        
        tCardBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(adminTeacherVisible==0){
                    adminTeacherVisible=1;
                    new adminTeacher().setVisible(true);
                }
            }

        });
        
        
        //##################################################
        hCard=new JLabel();
        hCard.setBounds(340,310,130,130);
        hCard.setLayout(null);
        ImageIcon hCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/hall.png"));
        Image hCardIcon = hCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon hCardBg = new ImageIcon(hCardIcon);
        hCard.setIcon(hCardBg);
        background.add(hCard);
        //##################################################
        hCardBtn = new JButton("Hall");
        hCardBtn.setBounds(340,440,130,40);
        hCardBtn.setFont(new Font("serif",Font.BOLD,20));
        hCardBtn.setForeground(Color.WHITE);
        hCardBtn.setBackground(new Color(65, 12, 62));
        hCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon hCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image hCardBtnIcon = hCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconhCardBtn = new ImageIcon(hCardBtnIcon);
        hCardBtn.setIcon(iconhCardBtn);
        hCardBtn.setBorder(emptyBorder);
        background.add(hCardBtn);
        
        
        
        
        
        //##################################################
        dCard=new JLabel();
        dCard.setBounds(340,490,130,130);
        dCard.setLayout(null);
        ImageIcon dCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/doctor.png"));
        Image dCardIcon = dCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon dCardBg = new ImageIcon(dCardIcon);
        dCard.setIcon(dCardBg);
        background.add(dCard);
        //##################################################
        dCardBtn = new JButton("Doctor");
        dCardBtn.setBounds(340,620,130,40);
        dCardBtn.setFont(new Font("serif",Font.BOLD,20));
        dCardBtn.setForeground(Color.WHITE);
        dCardBtn.setBackground(new Color(65, 12, 62));
        dCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon dCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image dCardBtnIcon = dCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon icondCardBtn = new ImageIcon(dCardBtnIcon);
        dCardBtn.setIcon(icondCardBtn);
        dCardBtn.setBorder(emptyBorder);
        background.add(dCardBtn);
        //##################################################
        lCard=new JLabel();
        lCard.setBounds(200,490,130,130);
        lCard.setLayout(null);
        ImageIcon lCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/library.png"));
        Image lCardIcon = lCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon lCardBg = new ImageIcon(lCardIcon);
        lCard.setIcon(lCardBg);
        background.add(lCard);
        //##################################################
        lCardBtn = new JButton("Library");
        lCardBtn.setBounds(200,620,130,40);
        lCardBtn.setFont(new Font("serif",Font.BOLD,20));
        lCardBtn.setForeground(Color.WHITE);
        lCardBtn.setBackground(new Color(65, 12, 62));
        lCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon lCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image lCardBtnIcon = lCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconlCardBtn = new ImageIcon(lCardBtnIcon);
        lCardBtn.setIcon(iconlCardBtn);
        lCardBtn.setBorder(emptyBorder);
        background.add(lCardBtn);
        //##################################################
        fCard=new JLabel();
        fCard.setBounds(60,490,130,130);
        fCard.setLayout(null);
        ImageIcon fCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/faculty.png"));
        Image fCardIcon = fCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon fCardBg = new ImageIcon(fCardIcon);
        fCard.setIcon(fCardBg);
        background.add(fCard);
        //##################################################
        fcardBtn = new JButton("Faculty");
        fcardBtn.setBounds(60,620,130,40);
        fcardBtn.setFont(new Font("serif",Font.BOLD,20));
        fcardBtn.setForeground(Color.WHITE);
        fcardBtn.setBackground(new Color(65, 12, 62));
        fcardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon fCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image fCardBtnIcon = fCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconfCardBtn = new ImageIcon(fCardBtnIcon);
        fcardBtn.setIcon(iconfCardBtn);
        fcardBtn.setBorder(emptyBorder);
        background.add(fcardBtn);
        
        
        
        
        
        
        //##################################################
        oCard=new JLabel();
        oCard.setBounds(60,670,130,130);
        oCard.setLayout(null);
        ImageIcon oCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/faculty.png"));
        Image oCardIcon = oCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon oCardBg = new ImageIcon(oCardIcon);
        oCard.setIcon(oCardBg);
        background.add(oCard);
        //##################################################
        ocardBtn = new JButton("Org");
        ocardBtn.setBounds(60,800,130,40);
        ocardBtn.setFont(new Font("serif",Font.BOLD,20));
        ocardBtn.setForeground(Color.WHITE);
        ocardBtn.setBackground(new Color(65, 12, 62));
        ocardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon oCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image oCardBtnIcon = oCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconoCardBtn = new ImageIcon(oCardBtnIcon);
        ocardBtn.setIcon(iconoCardBtn);
        ocardBtn.setBorder(emptyBorder);
        background.add(ocardBtn);
        //##################################################
        trCard=new JLabel();
        trCard.setBounds(340,670,130,130);
        trCard.setLayout(null);
        ImageIcon trCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/transport.png"));
        Image trCardIcon = trCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon trCardBg = new ImageIcon(trCardIcon);
        trCard.setIcon(trCardBg);
        background.add(trCard);
        //##################################################
        trCardBtn = new JButton("Transport");
        trCardBtn.setBounds(340,800,130,40);
        trCardBtn.setFont(new Font("serif",Font.BOLD,20));
        trCardBtn.setForeground(Color.WHITE);
        trCardBtn.setBackground(new Color(65, 12, 62));
        trCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon trCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image trCardBtnIcon = trCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon icontrCardBtn = new ImageIcon(trCardBtnIcon);
        trCardBtn.setIcon(icontrCardBtn);
        trCardBtn.setBorder(emptyBorder);
        background.add(trCardBtn);
        //##################################################
        gcard=new JLabel();
        gcard.setBounds(200,670,130,130);
        gcard.setLayout(null);
        ImageIcon gCardImg = new ImageIcon(ClassLoader.getSystemResource("photo/dashboard/sec.png"));
        Image gCardIcon = gCardImg.getImage().getScaledInstance(130, 130,Image.SCALE_FAST);
        ImageIcon gCardBg = new ImageIcon(gCardIcon);
        gcard.setIcon(gCardBg);
        background.add(gcard);
        //##################################################
        gCardBtn = new JButton("Security");
        gCardBtn.setBounds(200,800,130,40);
        gCardBtn.setFont(new Font("serif",Font.BOLD,20));
        gCardBtn.setForeground(Color.WHITE);
        gCardBtn.setBackground(new Color(65, 12, 62));
        gCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon gCardBtnImg = new ImageIcon(ClassLoader.getSystemResource("photo/Dashboard/rightarrowblue.png"));
        Image gCardBtnIcon = gCardBtnImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon icongCardBtn = new ImageIcon(gCardBtnIcon);
        gCardBtn.setIcon(icongCardBtn);
        gCardBtn.setBorder(emptyBorder);
        background.add(gCardBtn);
        
        
       
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    // Minimize JFrame
                    setState(JFrame.ICONIFIED);
            }

        });
        
        
        
        
        
               
        setUndecorated(true);
        setVisible(true);
        setSize(width-300,height-200);
        setLocationRelativeTo(null);
//        setLocation(width-550, 50);
    }
    
    public static void main(String[] args) {
        String user="shishir", identity="";
        new adminDashboard(user,identity).setVisible(true);
    }
}
