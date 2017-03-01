/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engagetendance;

import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Sam
 */
public class Quiz {
    
    DBconnection con;
        ResultSet rs;
    
     String date;
     String ClassID;
     String TimeOpen;
     String TimeClose;
     int quizID;
     int Done;
     int type;
    
    
    public Quiz(DBconnection con,String ClassID){
        this.ClassID=ClassID;
        this.con = con;
        
    }
    
    public void setDate(String date ){
        this.date = date;
       
    }
    public void setType(int type){
        this.type = type;
    }
    
    public void setTime(String starttime,int interval ){
        TimeOpen = starttime;
        String[] timestartarray = getTimeOpen().split(":");
        int Minutes = Integer.parseInt(timestartarray[1]);
        Minutes += interval;
        System.out.print("heyyyyy"+Minutes);
        int Hours = Minutes/60;
        Minutes= Minutes - (Hours*(60));
        Hours+= Integer.parseInt(timestartarray[0]);
        TimeClose= Hours+":"+Minutes+":"+timestartarray[2];
    }
    public String getTimeOpen(){
        
        return TimeOpen;
    }
    public String getTimeClose(){
        return TimeClose;
    }
    
    
    public boolean checkInProgress(){
        LocalDateTime currentDateTime = LocalDateTime.now();
           // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
           // String nowDate = currentDateTime.format(formatter);
          
            String[] datarrayString = date.split("-");
            String[] timeendarray = getTimeOpen().split(":");
          
            LocalDateTime pickedTimeStart = LocalDateTime.of(Integer.parseInt(datarrayString[0]), Integer.parseInt(datarrayString[1]), Integer.parseInt(datarrayString[2]), Integer.parseInt(timeendarray[0]), Integer.parseInt(timeendarray[1]), Integer.parseInt(timeendarray[2]));
                if(currentDateTime.isBefore(pickedTimeStart) ||checkDone()){
                    return false; //since the current time is still before the start time of todays quiz, or its done already, its not in progress
                }
                else{
                    return true;
                }
                
    }
    public boolean checkDone(){
         LocalDateTime currentDateTime = LocalDateTime.now();
           // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
           // String nowDate = currentDateTime.format(formatter);
          
            String[] datarrayString = date.split("-");
            String[] timeendarray = getTimeClose().split(":");
          
            LocalDateTime pickedTimeEnd = LocalDateTime.of(Integer.parseInt(datarrayString[0]), Integer.parseInt(datarrayString[1]), Integer.parseInt(datarrayString[2]), Integer.parseInt(timeendarray[0]), Integer.parseInt(timeendarray[1]), Integer.parseInt(timeendarray[2]));
   
            if (pickedTimeEnd.isBefore(currentDateTime)){
                return true;
            }
            else{
                
                return false;
            }
    
    
    }
    
    public boolean checkDateTime(){ /// not working...?
        LocalDateTime currentDateTime = LocalDateTime.now();
           // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
           // String nowDate = currentDateTime.format(formatter);
          
            String[] datarrayString = date.split("-");
            String[] timeendarray = "23:59:59".split(":");
            
            
            LocalDateTime pickedTimeEnd = LocalDateTime.of(Integer.parseInt(datarrayString[0]),Integer.parseInt(datarrayString[1]),Integer.parseInt(datarrayString[2]), Integer.parseInt(timeendarray[0]), Integer.parseInt(timeendarray[1]), Integer.parseInt(timeendarray[2]));
                  
            if(pickedTimeEnd.isBefore(currentDateTime)){
                return false;
            }
            //else if(pickedTimeEnd.isBefore(currentDateTime)){
                
              //  return false;
            //}
            else{
                System.out.print(pickedTimeEnd +"  "+currentDateTime);
                return true;
            }
    
    }                    



   public boolean addStudentInstance(int quizID, String userID){
      
      rs= con.query("Select MAX(InstanceID) from StudentQuizInstance;");
        
        
                  
                    try {
                        rs.next();
                        int index = 0;
                        index=  rs.getInt("Max(InstanceID)");
                        index++;
                  
                                 con.update("Insert into StudentQuizInstance values(\""+index+"\", \""+quizID+"\",\""+userID+"\");");
                              return true;
                       } catch (SQLException ex) {
                        Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
                           return false;
                    }
                
       
       
   }                  

    
    public ArrayList<String> findSQInstance(int QuizID){
        
        ArrayList<String> ls = new ArrayList<>();
        rs= con.query("SELECT users.`name` FROM studentquizinstance, users,Quiz Where users.`userID`=studentquizinstance.`UserID` and quiz.`QuizID`=studentquizinstance.`QuizID` and quiz.`QuizID`=\""+ QuizID+"\";");
        System.out.print(quizID);
        
        
                  
                    try {
                        
                        
                        if( rs.next()){
                           
                            
                            while(!rs.isAfterLast()){
                                ls.add(  rs.getString("name"));
                                rs.next();
                            }
                       
                        }
                               
                             
                       } catch (SQLException ex) {
                        Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
                          
                    }
        
                    return ls;
        
        
        
        
    }
    
    
    public int searchDate(String date){
        this.date = date;
        
        rs= con.query("Select quizID,TimeOpen,TimeClose from Quiz where date =\""+this.date+"\" and classID= \""+ClassID+"\";");
        
        
                  
                    try {
                        
                       if(rs.last()){
                            
                            quizID = rs.getInt("quizID");
                            TimeOpen = rs.getString("TimeOpen");
                            TimeClose = rs.getString("TimeClose");
                            return quizID;
                       }
                       else{
                           return -1;
                       }
                
                    } catch (SQLException ex) {
                        Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
                        
                          return -1; 
                    }
        
        
    }
    private void setTimes(String Open,String Close){
        TimeOpen=Open;
        TimeClose=Close;
    }
 
    
    public boolean createQuiz(){
        
        rs= con.query("Select MAX(QuizID) from Quiz");
        
        
                  
                    try {
                        rs.next();
                        int index = rs.getInt("Max(QuizID)");
                        index++;
                     
                           con.update("Insert into quiz Values (\""+index+"\",\""+ClassID+"\",\""+date+"\",\""+TimeOpen+"\",\""+TimeClose+"\",\"0\",\""+type+"\");");
                           return true;
                
                    } catch (SQLException ex) {
                        Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
                           return false;
                    }
        
        
        
        
        
    }
    
    
    
}
