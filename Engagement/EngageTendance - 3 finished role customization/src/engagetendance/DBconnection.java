/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engagetendance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Sam
 */
public class DBconnection {
    Connection con;
    
       DBconnection(){
           
           try{
           String host = "jdbc:mysql://localhost:3306/tendancedb?zeroDateTimeBehavior=convertToNull";
           String uName = "root";
           String uPass = "root";
           
           con = DriverManager.getConnection(host, uName, uPass);
           }
           catch(SQLException err){
               System.out.println(err.getMessage());
           }
           
       }
       
       
       public ResultSet query(String q){
           try{
               Statement stmt = con.createStatement();
               
              
               ResultSet rs = stmt.executeQuery(q);
               
               return rs;
               
           }
           catch(SQLException err){
               System.out.println(err.getMessage());
               return null;
           }
           
       }
    
}
