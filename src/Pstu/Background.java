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

/**
 *
 * @author ASUS
 */
public class Background extends JFrame {
    JLabel background;
    JButton close;
    
    
    
    public Background(){
        Border emptyBorder = BorderFactory.createEmptyBorder();
        
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        
        background=new JLabel();
        background.setBounds(0,0,width,height);
        background.setLayout(null);
        ImageIcon bgImg = new ImageIcon(ClassLoader.getSystemResource("photo/bg6.jpg"));
        Image bgImgIcon = bgImg.getImage().getScaledInstance(width, height,Image.SCALE_FAST);
        ImageIcon iconBg = new ImageIcon(bgImgIcon);
        background.setIcon(iconBg);
        add(background);
        
        
        close = new JButton();
        close.setBounds(width-100,50,30,30);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon closeImg = new ImageIcon(ClassLoader.getSystemResource("university/management/system/icons/Button/close1.png"));
        Image closeIcon = closeImg.getImage().getScaledInstance(30, 30,Image.SCALE_FAST);
        ImageIcon iconClose = new ImageIcon(closeIcon);
        close.setIcon(iconClose);
        close.setBorder(emptyBorder);
        background.add(close);
       
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                  setVisible(false);
            }

        });
        
        
        
        
        
               
        setUndecorated(true);
        setVisible(true);
        setSize(width,height);
        setLocationRelativeTo(null);
//        setLocation(width-550, 50);
    }
    
    public static void main(String[] args) {
        new Background().setVisible(true);
    }
}
