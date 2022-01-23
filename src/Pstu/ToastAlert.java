
package Pstu;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.Border;

import java.io.*;
import java.nio.file.*;


public class ToastAlert extends JFrame {
    JLabel checkMark,title,background;
    JButton close;
    
    ToastAlert(String s,String imgs, String cancle){
        
        Border emptyBorder = BorderFactory.createEmptyBorder();
        
        background=new JLabel();
        background.setBounds(0,0,500,80);
        background.setLayout(null);
        background.setOpaque(true);
        background.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        background.setBackground(new Color(0, 10, 66));
        
       checkMark = new JLabel();
       checkMark.setBounds(20,15,50,50);
       ImageIcon closeIcon1 = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/"+imgs));
       Image img1 = closeIcon1.getImage().getScaledInstance(50, 50,Image.SCALE_FAST);
       ImageIcon icon1 = new ImageIcon(img1);
       checkMark.setIcon(icon1);
       background.add(checkMark);
       
        title = new JLabel(s);
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setBounds(85,17,400,50);
        if(imgs.equals("checkToast.png")){ 
            background.setBackground(new Color(4, 112, 0));
            title.setForeground(Color.white);
        }else{ 
            background.setBackground(new Color(168, 14, 0));
            title.setForeground(Color.black);
        }
       background.add(title);
        
        
       close = new JButton();
       close.setBounds(450,20,30,30);
       ImageIcon closeIcon = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/"+cancle));
       Image img = closeIcon.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
       ImageIcon icon = new ImageIcon(img);
       close.setIcon(icon);
       close.setBorder(emptyBorder);
       background.add(close);
       
         close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 dispose();
            }

        });
        
        
        
        
        add(background);
        
        setUndecorated(true);
        setVisible(true);
        setSize(500,80);
        
//        setLocationRelativeTo(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setLocation(width-670, 80);
    }
    
    public static void main(String[ ] arg){
        new ToastAlert("Message","checkToast.png","delete2.png").setVisible(true);
    }
    
}
