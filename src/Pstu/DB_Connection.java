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
public class DB_Connection {
    Connection c;
    Statement s;
    
    public static void main(String[] args){
         new DB_Connection();
    }
     
     
    public DB_Connection(){  
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","");   
            System.out.println("Alhamdulillah");
        }catch(Exception e){ 
            System.out.println(e);
        }  
    } 
}
