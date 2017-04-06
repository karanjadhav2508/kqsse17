import java.io.InputStream;

public class StudentsAttendance {

   public String studentName;
   InputStream studentVoice;

   StudentsAttendance(String studentName, InputStream studentVoice)
    {
        this.studentName = studentName;
        this.studentVoice = studentVoice;
    }

    public void listAll ()
    {
        System.out.println("User Name: " + studentName + " ");
    }
}
