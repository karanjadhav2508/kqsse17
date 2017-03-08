package voicerecognition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Databases {

  VoiceRecognition voiceRecord = new VoiceRecognition();

  Databases (Students student) // throws FileNotFoundException
    { 
 
     Connection con; 
    
	   try {
	       Class.forName("com.mysql.jdbc.Driver").newInstance();
           }
           catch (Exception e) {
             System.out.println(
               "Unable to register the JDBC Driver.\n" +
               "Make sure the JDBC driver is in the\n" +
               "classpath.\n");
           }

           // This URL specifies we are connecting with a database server
           // on localhost.
           String url = "jdbc:mysql://localhost:3306/speech_apps";

           String username = "root";
           String password = "Alokozai?1986";

           // Make a connection with the database.
           try {
             con = DriverManager.getConnection(url, username, password);
             System.out.println("Connection Established");
 
           }
           catch (SQLException e) {
             System.out.println(
               "Unable to make a connection to the database.\n" +
               "The reason: " + e.getMessage());
             return;
           }
     
   try {
       
/*
       String filePath = voiceRecord.wavFile.toString();
       InputStream studentVoice = null;    
       try{
        studentVoice = new FileInputStream(new File(filePath));
//        sign = new FileInputStream(new File(filePath2));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
*/

    // Create an SQL statement
    String sql = "INSERT INTO STUDENT_DETAILS(NAME, COURSE_APPLIED, SEMESTER, EMAIL, VOICE) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement statement = con.prepareStatement(sql);    
    statement.setString(1, student.studentName);                    //1 specifies the first parameter in the query  
    statement.setString(2, student.studentCourseApplied);
    statement.setString(3, student.studentSemester);
    statement.setString(4, student.studentEmailAdd);
    statement.setBlob(5, student.studentVoice);

    
    int row = statement.executeUpdate();
    if (row > 0) {
      System.out.println("Record is sucessfully inserted...");
    }
    con.close();

//    Statement stmt = con.createStatement();
//    stmt.executeUpdate("INSERT INTO STUDENT_DETAILS(NAME, COURSE_APPLIED, SEMESTER, EMAIL, VOICE) VALUES ('" + student.studentName + "', '" + student.studentCourseApplied + "', '" + student.studentSemester + "', '" + student.studentEmailAdd + "', '" + student.studentVoice + "')");
    // Catch any exceptions that are thrown.
    }
 
     catch (SQLException e) {
             System.out.println(
               "An error occured\n" +
               "The SQLException message is: " + e.getMessage());
             return;
	   }   
     
  }

    
}