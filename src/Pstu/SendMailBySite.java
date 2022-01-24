package Pstu;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailBySite {
 public static void main(String[] args) {

      // Assuming you are sending email from localhost
//      String host = "localhost";
//  final String user="shishirbhuiyan83@gmail.com";//change accordingly
//  final String password="bismillah83@gmail.com180204308453";//change accordingly
 
  
    String to = "mehedihasan.cse18@gmail.com"; // to address. It can be any like gmail, yahoo etc.
    String from = "shishirbhuiyan83@gmail.com"; // from address. As this is using Gmail SMTP your from address should be gmail
    String password = "bismillah83@gmail.com180204308453"; // password for from gmail address that you have used in above line. 

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
        message.setSubject("Test Mail From Gmail Accocunt");
        message.setContent("<h1 style='color:red;'>Hello my sweet heart Mahedi</h1>", "text/html");
        Transport.send(message);
        System.out.println("Mail Sent...");
    } catch (MessagingException e) {
        e.printStackTrace();
    }
  
  
  
 }
}