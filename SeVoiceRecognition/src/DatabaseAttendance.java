import com.musicg.wave.Wave;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


public class DatabaseAttendance {

  Attendance voiceAttendance = new Attendance();
  Recognition RecognitionCal = new Recognition();
  int[] actualBuffer; // = new int[1024];
  
  DatabaseAttendance(StudentsAttendance student) throws IOException // throws FileNotFoundException
    { 
     Connection con;
     InputStream input = null; 
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
           // The username / password to connect under.
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
    // Create an SQL statement
    String sql = "SELECT VOICE FROM STUDENT_DETAILS WHERE NAME = ?"; 
    PreparedStatement statement = con.prepareStatement(sql);    
    statement.setString(1, student.studentName);                    //1 specifies the first parameter in the query  
    
    ResultSet rs = statement.executeQuery();
    int count=0, counter=0;
    while(rs.next())
      {
       input = rs.getBinaryStream("VOICE");   
       byte[] buffer = new byte[1024];      
       while (input.read(buffer) > 0) {
           counter = counter + 1;
       }
      actualBuffer = new int[counter];
      while (input.read(buffer) > 0) {
           actualBuffer[count] = input.read(buffer); 
           count = count + 1;
       }
            }
    con.close();

  // Catch any exceptions that are thrown.
    } 
     catch (SQLException e) {
             System.out.println(
               "An error occured\n" +
               "The SQLException message is: " + e.getMessage());
             return;
	   }        

   String wavFile1 = "C:\\Users\\QaissKhanAlokozai\\Documents\\FinalSound\\checkRecord.wav";
   Wave wave1 = new Wave(wavFile1); // create a wave object    
   String receivedVoiceBuffer = wave1.getFingerprint().toString();
   String actualVoiceBuffer = actualBuffer.toString();
   RecognitionCal.RecognitionRate(student.studentName, actualVoiceBuffer, receivedVoiceBuffer);

  }  
}
