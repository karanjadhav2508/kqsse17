/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engagetendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Sam
 */
public class login {
    
    private String user;
    private String pass;
    private String name;
    private int role;
    DBconnection con;
    
    
    login(String user, String pass){
        this.user=user;
        this.pass=pass;
    }
    
    public boolean verifyLogin(){
        String q = "Select users.userID,name,roleID,pass from users,password where users.userID=\""+user+"\" and password.userID=\""+user+"\"";
        
        con = new DBconnection();
        ResultSet rs = con.query(q);
        
        String checkuser="";
        String checkpass="";
        int checkrole= 0;
        String checkname="";
       
          try {
                rs.next();
       
                checkuser = rs.getString("users.userID");
                checkpass = rs.getString("pass");
                checkrole = rs.getInt("roleID");
                checkname = rs.getString("name");
             
                if(checkuser.equals(user) && checkpass.equals(pass)){
               
                    ContinueLogin(checkuser,checkrole,checkname);
                    
                    return true;
                }
                
            
        } catch (SQLException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
        }
      //  System.out.print(checkuser+checkpass+checkrole+checkname);
        return false;
        
    }
    
    private void ContinueLogin(String user,int role, String name){
        this.role = role;
        this.name = name;
        this.user = user;
    
                homescreen h = new homescreen();
                h.setVisible(true);
                h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                h.setLocation(600, 300);
                h.setAttributes(this.user, this.name, this.role, con);
        
    }
    
}
