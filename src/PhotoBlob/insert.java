/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PhotoBlob;

/**
 *
 * @author ASUS
 */

 
import java.sql.*;
import java.io.*;
 
public class insert {
    Connection c;
    Statement s;
public static void main(String args[]){
try{      
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/blob","root","");
    File file=new File("E:\\image.jpg");
    FileInputStream fis=new FileInputStream(file);
    PreparedStatement ps=con.prepareStatement("insert into image (photo1) values(?)");
    ps.setBinaryStream(1,fis,(int)file.length());
    ps.executeUpdate();

    ps.close();
    fis.close();
    con.close();
}catch(Exception e){
e.printStackTrace();
}
}
}