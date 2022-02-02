/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Md Shishir Bhuiyan
 */
public class UpdateToken {
    private final String identity;
    private final String updateColumn;
    private final String UpdateData;
    private final String userName;
    Boolean updateStatus = false;
    
    
    	Connection con = null;
	FileInputStream fs=null;
	PreparedStatement ps=null;
    
    
    UpdateToken(String identity,String updateColumn,String data,String userName){
      this.identity = identity;
      this.updateColumn = updateColumn;
      this.UpdateData = data;
      this.userName = userName;
    }
    
    
    public boolean UpadetData(){ 
        String query;
        conn cc = new conn();
        try{

            
            if("token".equals(updateColumn)){
                query = "UPDATE users SET token=? Where uid=?";
            }else{
                query = "UPDATE users SET status=? Where uid=?";
            }
            
            

            
            ps= cc.c.prepareStatement(query);
            ps.setString(1, UpdateData);
            ps.setString(2, userName);
            int count = ps.executeUpdate();
            
            if(count > 0){ 
               updateStatus = true;
            }
        }catch(SQLException ex){
            return false;
        }
        
        if(updateStatus){
            return true;
        }else{
            return false;
        }
    }
    
}
