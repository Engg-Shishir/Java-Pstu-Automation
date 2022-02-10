/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 *
 * @author ASUS
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Logins
     */
    private String logeduserId = "";
    private String logedUserIdentity = "";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs;
    private int random;
    Boolean mailSendSuccessStatus = false;

        String role="",requestEmail="";
        
        
    public Login() {
        initComponents();
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

    //    Alert Show and hide
    public void alert(String type, String permission, String message) {
        if ("error".equals(type)) {
            if ("false".equals(permission)) {
                alertPanel.setVisible(false);
                error.setVisible(false);
                errorLogo.setVisible(false);
                errorText.setText(message);
                errorClose.setVisible(false);
            } else {
                alertPanel.setVisible(true);
                error.setVisible(true);
                errorLogo.setVisible(true);
                errorText.setText(message);
                errorClose.setVisible(true);
            }
        } else {
            if ("false".equals(permission)) {
                alertPanel.setVisible(false);
                success.setVisible(false);
                successLogo.setVisible(false);
                successText.setText(message);
                successClose.setVisible(false);
            } else {
                alertPanel.setVisible(true);
                success.setVisible(true);
                successLogo.setVisible(true);
                successText.setText(message);
                successClose.setVisible(true);
            }
        }
    }

    // Hide meathod
    public void hideComponent() {
        alertPanel.setVisible(false);
        alert("error", "false", "");
        alert("success", "false", "");
    }

    public void sendmail(String idnty, String toSend) throws SQLException {

        String query;

        if ("student".equals(idnty)) {
            query = "SELECT * FROM student where(email='" + toSend + "') ";
        } else {
            query = "SELECT * FROM users where(email='" + toSend + "') ";
        }

        conn cc = new conn();
        ResultSet rs = cc.s.executeQuery(query);
        if (rs.next()) {

            int randoms = new Random().nextInt(900000) + 100000;

            String to = toSend; // to address. It can be any like gmail, yahoo etc.
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
                message.setSubject("PSTU Password reset verification code");

                message.setContent("<div style=\" max-width:450px;margin:0px auto; background-color:white;box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;  \"><table style=\"font-family:'Helvetica Neue',Helvetica,Arial,'Lucida Grande',sans-serif;border:solid lightgray 1px;background-color:transparent;max-width:450px;margin:0px auto\" cellpadding=\"0\" cellspacing=\"0\">\n"
                        + "    <tbody>"
                        + "        <tr>"
                        + "            <td style=\"text-align:center\">"
                        + "                <p style=\"color:#000;display:block;background-color:#91034a;margin:0px auto;font-size:1.5rem;text-align:center;padding:10px 0px;width:100%;\">Java, PSTU Mail System</p>\n"
                        + "                <img src=\"https://i.postimg.cc/qqjdGYYD/pstulogo.png\" style=\"width:300px;margin-bottom:10px\">\n"
                        + "            </td>\n"
                        + "        </tr>\n"
                        + "\n"
                        + "        <tr>\n"
                        + "\n"
                        + "            <td style=\"padding:10px;text-align:justify\">\n"
                        + "                <p><strong>Dear User,</strong><br><br>\n"
                        + "\n"
                        + "                    PSTU send this verification mail.So that your account alive with secure. If you think this proces is going to with your permission, please give us your verification code. Otherwise change your password quickly & inform PSTU. \n"
                        + " \n"
                        + "                </p>\n"
                        + "\n"
                        + "\n"
                        + "                <p style=\"display:block;text-decoration:none!important;color:#fff;cursor:pointer;background-color:#91034a;margin:0px auto;margin-top:30px;font-size:1.5rem;text-align:center;padding:10px 0px;width:90%;\">" + toSend + "</p>"
                        + "                <p style=\"color:#000;display:block;background-color:#299314;margin:0px auto;font-size:1.5rem;text-align:center;padding:5px 0px;width:90%;\">Verification key : " + randoms + "</p>\n"
                        + "\n"
                        + "                <p>\n"
                        + "                    I am Shishir and interested in doing positive things about every aspect of life. I love projects with challenges. I like works to make an impact in the real world. I always try to work for my world with my community. I learn to extended . Also I am a specialized in Front-End and Back-End web Development.Besides within the web designing and development area every sector I want to deliver my best. I always try to maintain performance with achievements. Actually I am not a successful programmer but I want to be also successful. Because the program is my way & programming is my passion. If you think? I will help you with your project! yes, I am ready to assist you InsyaAllah.Send your query  \n"
                        + "                    <p></strong><strong>shishir16@cse.pstu.ac.bd</strong></p>\n"
                        + "                </p>\n"
                        + "                <p>\n"
                        + "                    If you are having any issue, kindly reach out to us by replying to this email. \n"
                        + "                    <a href=\"https://pstu.ac.bd/contact-us/\" target=\"_blank\"\">Patuakhali Science & Technology University</a>\n"
                        + "                </p>\n"
                        + "\n"
                        + "                <p>Thanking you<br>PSTU Atumation Team</p>\n"
                        + "            </td>\n"
                        + "\n"
                        + "        </tr>\n"
                        + "        <tr>\n"
                        + "\n"
                        + "            <td>\n"
                        + "                <img src=\"https://i.postimg.cc/qvz0d0ph/152-405-copy-removebg-preview.png\" style=\"color:#000;display:block;margin:0px auto;margin-botom:-10px;width:250px; height:300px;\""
                        + "            </td>"
                        + "        </tr>"
                        + "        <tr>"
                        + "            <td>"
                        + "                   <p style=\"color:#000;display:block;background-color:#91034a;margin:2px auto;font-size:20px;text-align:center;padding:10px 0px;width:100%;\">&copy; All right reseve by PSTU</p>\n"
                        + "\n"
                        + "            </td>\n"
                        + "\n"
                        + "        </tr>\n"
                        + "    </tbody>\n"
                        + "</table></div>'", "text/html");

                try {
                    Transport.send(message);
                    mailSendSuccessStatus = true;
                } catch (MessagingException ee) {
                    alert("error", "true", "Please check your network connection");
                }

                if (mailSendSuccessStatus) {
                    updateTokenForverification(idnty, toSend, randoms);
                }

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            alert("error", "true", "This email is not registered yet");
        }

    }

    public void updateTokenForverification(String identy, String toSend, int rand) {
        String query;
        try {
            if ("student".equals(identy)) {
                query = "UPDATE student SET token=? Where email=?";
            } else {
                query = "UPDATE users SET token=? Where email=?";
            }
            conn cc = new conn();
            ps = cc.c.prepareStatement(query);
            ps.setString(1, String.valueOf(rand));
            ps.setString(2, toSend);
            int count = ps.executeUpdate();
            System.out.println(count);

            if (count > 0) {
                Login_Forgot.setSelectedIndex(1);
                alert("success", "true", "Please verify your mail");
            }

        } catch (Exception ex) {
//          alert("error","true","Something going wrong !20");
            alert("error", "true", "This");
        }
    }

    public void updatePasswordFormverification(String identy, String email, String passwords) {
        String query;
        System.out.println(identy);
        System.out.println(email);
        System.out.println(passwords);
        try {
                
            query = "UPDATE users SET password=?,token=? Where uid=?";
            
            conn cc = new conn();
            ps = cc.c.prepareStatement(query);
            ps.setString(1, passwords);
            ps.setString(2, "");
            ps.setString(3, email);
            int count = ps.executeUpdate();
            System.out.println(count);

            if (count > 0) {
                Login_Forgot.setSelectedIndex(0);
                alert("success", "true", "Your password is updated");
            }

        } catch (Exception ex) {
            System.out.println(ex);
            alert("error", "true", "Alert form catch section");
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

        bg = new javax.swing.JPanel();
        alertPanel = new javax.swing.JPanel();
        errorClose = new javax.swing.JButton();
        errorLogo = new javax.swing.JLabel();
        errorText = new javax.swing.JLabel();
        error = new javax.swing.JLabel();
        successLogo = new javax.swing.JLabel();
        successText = new javax.swing.JLabel();
        successClose = new javax.swing.JButton();
        success = new javax.swing.JLabel();
        Login_Forgot = new javax.swing.JTabbedPane();
        LoginPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        LoginUsername = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        LoginPassword = new javax.swing.JPasswordField();
        jSeparator4 = new javax.swing.JSeparator();
        loginBtn = new javax.swing.JButton();
        backtoVerify = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        ForgotPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        forgotUsername = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        forgotbtn = new javax.swing.JButton();
        backtoLogin = new javax.swing.JLabel();
        forgotVerificationCode = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        forgotPassword = new javax.swing.JPasswordField();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        bg.setBackground(new java.awt.Color(0, 51, 51));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        alertPanel.setBackground(new java.awt.Color(74, 0, 52));
        alertPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        errorClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        errorClose.setBorder(null);
        errorClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        errorClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                errorCloseActionPerformed(evt);
            }
        });
        alertPanel.add(errorClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 15, 40, 40));

        errorLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/warning.png"))); // NOI18N
        errorLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alertPanel.add(errorLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 45, 40));

        errorText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        errorText.setForeground(new java.awt.Color(255, 255, 255));
        alertPanel.add(errorText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 340, 50));

        error.setBackground(new java.awt.Color(127, 13, 92));
        error.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        error.setOpaque(true);
        alertPanel.add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 450, 60));

        successLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/successlogos.png"))); // NOI18N
        successLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alertPanel.add(successLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 7, 50, 50));

        successText.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        successText.setForeground(new java.awt.Color(255, 255, 255));
        alertPanel.add(successText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 5, 340, 50));

        successClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        successClose.setBorder(null);
        successClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        successClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                successCloseActionPerformed(evt);
            }
        });
        alertPanel.add(successClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 40, 40));

        success.setBackground(new java.awt.Color(0, 102, 51));
        success.setOpaque(true);
        alertPanel.add(success, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 450, 60));

        bg.add(alertPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, -1, 70));

        Login_Forgot.setBackground(new java.awt.Color(0, 51, 51));
        Login_Forgot.setAlignmentX(0.0F);
        Login_Forgot.setAlignmentY(0.0F);
        Login_Forgot.setPreferredSize(new java.awt.Dimension(900, 500));

        LoginPanel.setBackground(new java.awt.Color(74, 0, 52));
        LoginPanel.setPreferredSize(new java.awt.Dimension(900, 530));
        LoginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstall420_420.png"))); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LoginPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 380, 370));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/login.png"))); // NOI18N
        LoginPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, -1, 70));

        LoginUsername.setBackground(new java.awt.Color(74, 0, 52));
        LoginUsername.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        LoginUsername.setForeground(new java.awt.Color(255, 255, 255));
        LoginUsername.setBorder(null);
        LoginUsername.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                LoginUsernameComponentRemoved(evt);
            }
        });
        LoginUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                LoginUsernameFocusGained(evt);
            }
        });
        LoginPanel.add(LoginUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, 250, 40));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/user.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LoginPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 30, 40));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        LoginPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 390, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Username :");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LoginPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 230, 80, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LoginPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 40, 40));

        LoginPassword.setBackground(new java.awt.Color(74, 0, 52));
        LoginPassword.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        LoginPassword.setForeground(new java.awt.Color(255, 255, 255));
        LoginPassword.setBorder(null);
        LoginPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                LoginPasswordFocusGained(evt);
            }
        });
        LoginPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginPasswordActionPerformed(evt);
            }
        });
        LoginPanel.add(LoginPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 250, 40));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        LoginPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 330, 390, 10));

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        loginBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/login.png"))); // NOI18N
        loginBtn.setText("Login");
        loginBtn.setBorder(null);
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });
        LoginPanel.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 360, 100, 40));

        backtoVerify.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        backtoVerify.setForeground(new java.awt.Color(0, 153, 153));
        backtoVerify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/refresh.png"))); // NOI18N
        backtoVerify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backtoVerify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backtoVerifyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backtoVerifyMouseEntered(evt);
            }
        });
        LoginPanel.add(backtoVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, 40, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Forgot Password ?");
        LoginPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, -1, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Password  :");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LoginPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, 80, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Alert Icon/close.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        LoginPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 40, 40));

        Login_Forgot.addTab("Login Frame", LoginPanel);

        ForgotPanel.setBackground(new java.awt.Color(74, 0, 52));
        ForgotPanel.setPreferredSize(new java.awt.Dimension(900, 530));
        ForgotPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pstu/pstulogo400_400.png"))); // NOI18N
        ForgotPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 410, 370));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Border/verify.png"))); // NOI18N
        ForgotPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, -1, 70));

        forgotUsername.setBackground(new java.awt.Color(74, 0, 52));
        forgotUsername.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        forgotUsername.setForeground(new java.awt.Color(255, 255, 255));
        forgotUsername.setBorder(null);
        forgotUsername.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                forgotUsernameComponentRemoved(evt);
            }
        });
        forgotUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                forgotUsernameFocusGained(evt);
            }
        });
        ForgotPanel.add(forgotUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 220, 270, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/user.png"))); // NOI18N
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForgotPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 30, 40));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        ForgotPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 380, 20));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ID :");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForgotPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 50, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForgotPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 40, 40));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        ForgotPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, 380, 10));

        forgotbtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        forgotbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/verify.png"))); // NOI18N
        forgotbtn.setText("Verify");
        forgotbtn.setBorder(null);
        forgotbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotbtnActionPerformed(evt);
            }
        });
        ForgotPanel.add(forgotbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 380, 110, 40));

        backtoLogin.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        backtoLogin.setForeground(new java.awt.Color(0, 153, 153));
        backtoLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/refresh.png"))); // NOI18N
        backtoLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backtoLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backtoLoginMouseClicked(evt);
            }
        });
        ForgotPanel.add(backtoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 40, 30));

        forgotVerificationCode.setBackground(new java.awt.Color(74, 0, 52));
        forgotVerificationCode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        forgotVerificationCode.setForeground(new java.awt.Color(255, 255, 255));
        forgotVerificationCode.setBorder(null);
        forgotVerificationCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                forgotVerificationCodeFocusGained(evt);
            }
        });
        forgotVerificationCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotVerificationCodeActionPerformed(evt);
            }
        });
        ForgotPanel.add(forgotVerificationCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 270, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Back to Login ?");
        ForgotPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, 140, 30));

        forgotPassword.setBackground(new java.awt.Color(74, 0, 52));
        forgotPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        forgotPassword.setForeground(new java.awt.Color(255, 255, 255));
        forgotPassword.setBorder(null);
        forgotPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                forgotPasswordFocusGained(evt);
            }
        });
        forgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotPasswordActionPerformed(evt);
            }
        });
        ForgotPanel.add(forgotPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 320, 270, 40));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Pass :");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForgotPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 320, 50, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Btn/key.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForgotPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 320, 40, 40));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        ForgotPanel.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 360, 380, 10));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Code :");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForgotPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, 50, 40));

        Login_Forgot.addTab("Forgot Frame", ForgotPanel);

        bg.add(Login_Forgot, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 910, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 488, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void forgotUsernameComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_forgotUsernameComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotUsernameComponentRemoved

    private void forgotUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_forgotUsernameFocusGained
        // TODO add your handling code here:
        forgotUsername.setText("");
    }//GEN-LAST:event_forgotUsernameFocusGained

    private void forgotbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forgotbtnActionPerformed
        // TODO add your handling code here:

        conn cc = new conn();
        String userName = forgotUsername.getText();
        String vkey = forgotVerificationCode.getText();
        String pass = forgotPassword.getText();

            if ("".equals(userName)) {
                alert("error", "true", "User id is required");
            } else {
                if ("".equals(vkey)) {
                    alert("error", "true", "Write your verification code");
                } else {
                    if ("".equals(pass)) {
                        alert("error", "true", "Write your secure Password");
                    }else{ 
                        try {
                            String  query = "SELECT * FROM users where(uid='" +userName+"' and token='" +vkey+"')";
                            ResultSet rs1 = cc.s.executeQuery(query);
                            if(rs1.next()){
                                System.out.println("loadig.....");
                               updatePasswordFormverification(role, userName, pass);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }
//        if (!checkWordIsFoundOrnOT(identity,"select")) {
//            alert("error", "true", "Selct your identity first");
//        } else {
//            if ("".equals(userName)) {
//                alert("error", "true", "Email address is required");
//            } else {
//                if ("".equals(vkey)) {
//                    alert("error", "true", "Write your verification code");
//                } else {
//                    if ("".equals(pass)) {
//                        alert("error", "true", "Write your secure Password");
//                    } else {
//                        if ("student".equals(identity)) {
//                            String query = "SELECT * FROM student where(email='" + userName + "')";
//
//                            try {
//                                rs= cc.s.executeQuery(query);
//                                if (rs.next()) {
//                                    
//                                     String q = "SELECT * FROM users where(uid='" + rs.getString("uid") + "' and token='" + vkey + "')";
//                                     rs = cc.s.executeQuery(q);
//                                      if (rs.next()){
//                                        updatePasswordFormverification(identity, rs.getString("uid"), pass);
//                                      }
//                                } else {
//                                    alert("error", "true", "Your creadential is not match");
//                                }
//                            } catch (SQLException ex) {
//                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        } else if("admin".equals(identity)){
//                            String query = "SELECT * FROM administrator where(email='" + userName + "')";
//
//                            try {
//                                rs = cc.s.executeQuery(query);
//                                if (rs.next()) {
//                                    
//                                     String q = "SELECT * FROM users where(uid='" + rs.getString("uid") + "' and token='" + vkey + "')";
//                                     rs = cc.s.executeQuery(q);
//                                      if (rs.next()){
//                                        updatePasswordFormverification(identity, rs.getString("uid"), pass);
//                                      }
//                                } else {
//                                    alert("error", "true", "Your creadential is not match");
//                                }
//                            } catch (SQLException ex) {
//                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        } else if("teacher".equals(identity)){
//                            String query = "SELECT * FROM teacher where(email='" + userName + "')";
//
//                            try {
//                                rs = cc.s.executeQuery(query);
//                                if (rs.next()) {
//                                    
//                                     String q = "SELECT * FROM users where(uid='" + rs.getString("uid") + "' and token='" + vkey + "')";
//                                     rs = cc.s.executeQuery(q);
//                                      if (rs.next()){
//                                        updatePasswordFormverification(identity, rs.getString("uid"), pass);
//                                      }
//                                } else {
//                                    alert("error", "true", "Your creadential is not match");
//                                }
//                            } catch (SQLException ex) {
//                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    }
//                }
//            }
    }//GEN-LAST:event_forgotbtnActionPerformed

    private void backtoLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backtoLoginMouseClicked
        // TODO add your handling code here:

        Login_Forgot.setSelectedIndex(0);
    }//GEN-LAST:event_backtoLoginMouseClicked

    private void forgotVerificationCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_forgotVerificationCodeFocusGained
        // TODO add your handling code here:
        forgotVerificationCode.setText("");
    }//GEN-LAST:event_forgotVerificationCodeFocusGained

    private void forgotVerificationCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forgotVerificationCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotVerificationCodeActionPerformed

    private void LoginUsernameComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_LoginUsernameComponentRemoved
        // TODO add your handling code here:
        LoginUsername.setText("Enter username / email / Id");
    }//GEN-LAST:event_LoginUsernameComponentRemoved

    private void LoginUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LoginUsernameFocusGained

    }//GEN-LAST:event_LoginUsernameFocusGained

    private void LoginPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LoginPasswordFocusGained

    }//GEN-LAST:event_LoginPasswordFocusGained

    private void LoginPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoginPasswordActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        // TODO add your handling code here:
        try {
            conn cc = new conn();

            String userName = LoginUsername.getText();
            String pss = LoginPassword.getText();
            
                String query = "SELECT * FROM users where(uid='" + userName + "' and password='" + pss + "')"
                        + "or(username='"+userName+"' and password='" + pss + "')";
                ResultSet rs = cc.s.executeQuery(query);

                if (rs.next()){
                    if("active".equals(rs.getString("status"))){
                        logeduserId = rs.getString("uid");
                        logedUserIdentity = rs.getString("role");
                        this.dispose();
                        AdminDashboards home = new AdminDashboards(logeduserId, logedUserIdentity);
                        home.setVisible(true);
                    }else{
                      alert("error", "true", "Acount is diabled as a "+rs.getString("role").toUpperCase());
                    }
                }else{
                    alert("error", "true", "Account credential is not match");
                }

        } catch (SQLException e) {
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void backtoVerifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backtoVerifyMouseClicked
        // TODO add your handling code here:
        String id = LoginUsername.getText();
            conn cc = new conn();
                        ResultSet rs1;
            if ("".equals(id)) {
                alert("error", "true", "Insert your Id");
            }else {
                String query = null;
                try {
                    query = "SELECT * FROM users where uid='"+id+"' ";
                    ResultSet rs = cc.s.executeQuery(query);
                    if (rs.next()) {
                        System.out.println("Pass 01");
                        role = rs.getString("role");
                        if("student".equals(rs.getString("role"))){ 
                          String query1 = "SELECT * FROM student where(uid='" +id+"')";
                           rs1 = cc.s.executeQuery(query1);
                        }else{ 
                          String query1 = "SELECT * FROM teacher where(uid='"+id+"')";
                          rs1 = cc.s.executeQuery(query1);
                        }
                        
                        if(rs1.next()){ 
                        System.out.println("Pass 02");
                            if(rs1.getString("email") != null){
                              System.out.println("Email ace");
                                requestEmail = rs1.getString("email");
                                System.out.println(requestEmail);
                                int randoms = new Random().nextInt(900000) + 100000;
                                String data = String.valueOf(randoms);
                                sendMail mail = new sendMail(requestEmail,randoms,"Reset Password Verification Code");
                                if(mail.send()){
                                    UpdateToken token = new UpdateToken(role,"token",data,id);
                                    if(token.UpadetData()){ 
                                       System.out.println("Token updated");
                                        System.out.println("asce");
                                        Login_Forgot.setSelectedIndex(1);
                                        alert("success", "true", "Please verify your mail");
                                    }
                                }
                            }else{
                              alert("error", "true", "No email found this account");
                            }
                        }else{
                            alert("error", "true", "Account is not avilable now");
                        }
                        

                    }else{
                     alert("error", "true", "Uid is not registered yet");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }//GEN-LAST:event_backtoVerifyMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        hideComponent();
    }//GEN-LAST:event_formComponentShown

    private void errorCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_errorCloseActionPerformed
        // TODO add your handling code here:

        alert("error", "false", "");
    }//GEN-LAST:event_errorCloseActionPerformed

    private void successCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_successCloseActionPerformed
        // TODO add your handling code here:
        alert("success", "false", "");
    }//GEN-LAST:event_successCloseActionPerformed

    private void forgotPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_forgotPasswordFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotPasswordFocusGained

    private void forgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forgotPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotPasswordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void backtoVerifyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backtoVerifyMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_backtoVerifyMouseEntered

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ForgotPanel;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JPasswordField LoginPassword;
    private javax.swing.JTextField LoginUsername;
    private javax.swing.JTabbedPane Login_Forgot;
    private javax.swing.JPanel alertPanel;
    private javax.swing.JLabel backtoLogin;
    private javax.swing.JLabel backtoVerify;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel error;
    private javax.swing.JButton errorClose;
    private javax.swing.JLabel errorLogo;
    private javax.swing.JLabel errorText;
    private javax.swing.JPasswordField forgotPassword;
    private javax.swing.JTextField forgotUsername;
    private javax.swing.JPasswordField forgotVerificationCode;
    private javax.swing.JButton forgotbtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton loginBtn;
    private javax.swing.JLabel success;
    private javax.swing.JButton successClose;
    private javax.swing.JLabel successLogo;
    private javax.swing.JLabel successText;
    // End of variables declaration//GEN-END:variables
}
