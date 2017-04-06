import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Databases {

  VoiceRecognition voiceRecord = new VoiceRecognition();
  //database connection setup
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
    String sql = "INSERT INTO STUDENT_DETAILS(NAME, COURSE_APPLIED, SEMESTER, EMAIL, VOICE) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement statement = con.prepareStatement(sql);    
    statement.setString(1, student.studentName);                    //1 specifies the first parameter in the query  
    statement.setString(2, student.studentCourseApplied);
    statement.setString(3, student.studentSemester);
    statement.setString(4, student.studentEmailAdd);
    statement.setBlob(5, student.studentVoice);

    int row = statement.executeUpdate();
    if (row > 0) {
      System.out.println("Record is successfully inserted into the database...");
    }
    con.close();

    }
      catch (SQLException e) {
             System.out.println(
               "An error occured\n" +
               "The SQLException message is: " + e.getMessage());
             return;
	   }   
     
  }

}