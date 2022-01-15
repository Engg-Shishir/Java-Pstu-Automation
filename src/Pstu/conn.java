/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;
import java.sql.*;  

/**
 *
 * @author ASUS
 */
public class conn {
    Connection c;
    Statement s;
    
//    public static void main(String[] args){
//         new conn();
//    }
     
     
    public conn(){  
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root",""); 
            s = c.createStatement();
            
        }catch(Exception e){ 
            System.out.println(e);
        }  
    } 
}
