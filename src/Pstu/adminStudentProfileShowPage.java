/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import static Pstu.adminDashboard.adminStudentVisible;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author ASUS
 */
public class adminStudentProfileShowPage extends JFrame {
    JLabel background;
    JButton close;


    public adminStudentProfileShowPage(){
        Border emptyBorder = BorderFactory.createEmptyBorder();
        background=new JLabel();
        background.setBounds(0,0,1115,855);
        background.setLayout(null);
        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("photo/1115855.png"));
        Image bgImgIcon = bgImg.getImage().getScaledInstance(1115, 855,Image.SCALE_FAST);
        ImageIcon iconBg = new ImageIcon(bgImgIcon);
        background.setIcon(iconBg);
        add(background);
        
        
        
        
        close = new JButton("Close");
        close.setBounds(990,10,110,50);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon closeImg = new ImageIcon(ClassLoader.getSystemResource("icon/close Jframe.png"));
        Image closeIcon = closeImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconClose = new ImageIcon(closeIcon);
        close.setIcon(iconClose);
        background.add(close);
        
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }

        });
        
        
        setUndecorated(true);
        setVisible(true);
        setSize(1115,855);
        setLocation(634, 80);

    }
    
    
    
    public static void main(String[] args) {
        new adminStudentProfileShowPage().setVisible(true);
    }
}
