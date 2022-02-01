/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Md Shishir Bhuiyan
 */
public class sendMail {
    private final String touser;
    private final int random;
    private final String subject;
    Boolean mailSendSuccessStatus = false;
    
        
    sendMail(String toMail, int random,String sub){
       this.touser = toMail;
       this.random = random;
       this.subject = sub;
       
    }
    
    
    public boolean send(){ 
       
            
            String to = this.touser; // to address. It can be any like gmail, yahoo etc.
            String from = "somethingisw@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
            String password = "bismillahw@gmail.com180204308453"; // password for from gmail address that you have used in above line. 

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(from, password);
             }
            });


            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(this.subject);
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
"                <p style=\"display:block;text-decoration:none!important;color:#fff;cursor:pointer;background-color:#91034a;margin:0px auto;margin-top:30px;font-size:1.5rem;text-align:center;padding:10px 0px;width:90%;\">"+this.touser+"</p>" +
"                <p style=\"color:#000;display:block;background-color:#299314;margin:0px auto;font-size:1.5rem;text-align:center;padding:5px 0px;width:90%;\">Verification key : "+this.random+"</p>\n" +
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
                  Transport.send(message);
                  mailSendSuccessStatus = true;
                }
                catch(MessagingException ee){
                  mailSendSuccessStatus = false;
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        if(mailSendSuccessStatus){
          return true;
        }else{ 
          return false;
        }
    }
    
    

}
