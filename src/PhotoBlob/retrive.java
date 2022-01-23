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
 
public class retrive {
    Connection c;
    Statement s;
    public static void main(String args[]) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/blob","root","");
            File file=new File("E:\\image1.jpg");
            FileOutputStream fos=new FileOutputStream(file);
            byte b[];
            Blob blob;
            PreparedStatement ps=con.prepareStatement("select * from image");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                blob=rs.getBlob("photo2");
                System.out.println(blob);
                b=blob.getBytes(1,(int)blob.length());
                System.out.println(b);
                fos.write(b);
            }

    }
}