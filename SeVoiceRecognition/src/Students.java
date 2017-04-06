import java.io.InputStream;

public class Students
{

   public String studentName, studentCourseApplied, studentSemester, studentEmailAdd;
   InputStream studentVoice;

   Students(String studentName, String studentCourseApplied, String studentSemester, String studentEmailAdd, InputStream studentVoice)
    {
        this.studentName = studentName; 
        this.studentCourseApplied = studentCourseApplied;
        this.studentSemester = studentSemester;
        this.studentEmailAdd = studentEmailAdd;
        this.studentVoice = studentVoice;
    }
    public void listAll ()
    {
        System.out.println("User Name: " + studentName + " ");
    }
}
