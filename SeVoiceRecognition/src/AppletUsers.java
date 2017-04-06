// import the built-in java library class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class AppletUsers extends JApplet implements ActionListener
{
    public static JTextField display;  // declare java Text Field
    public static JTextField name,age,couseApplied,semester,phone,email;   // // declare java Text Field
    VoiceRecognition voiceReg = new VoiceRecognition();
    public JPanel studentPanel;  // // declare java Panel

    public void init()
    {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(20,15));  // define layout of the frame
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));   // define java panel
        JLabel prompt = new JLabel("Display Messages:");
        centerPanel.add(prompt);
        display = new JTextField("Please Fill in All Details to register");  // the text field filled 
        display.setEditable(false);    // field cannot be editable
        centerPanel.add(display);      // add field with panel

        contentPane.add(centerPanel, BorderLayout.NORTH);    // layout the border

        JPanel buttonPanel = new JPanel();
        studentPanel = new JPanel(new GridLayout(8, 2));      // define java panel
        addLabel(studentPanel, "Name :");                      // add name label
        name = new JTextField("");                              // create a text field with the label name
        studentPanel.add(name);                                 // add a text field with the label name
        addLabel(studentPanel, "Course Applied For :");          // add couseApplied label
        couseApplied = new JTextField("");                      // create a text field with the label couseApplied
        studentPanel.add(couseApplied);                         // add a text field with the label couseApplied
        addLabel(studentPanel, "Semester :");                   // add semester label
        semester = new JTextField("");                          // create a text field with the label semester
        studentPanel.add(semester);                             // add a text field with the label semester
        addLabel(studentPanel, "Email Address :");              // add email label
        email = new JTextField("");                             // create a text field with the label email
        studentPanel.add(email);                                // add a text field with the label email
        addLabel(studentPanel, "Record your Voice :");          // add voice label
        JButton voice = new JButton("Record");  // define button 1
        voice.setPreferredSize(new Dimension(100, 30));        // set size of the button 1
        voice.addActionListener(this);
        studentPanel.add(voice,BorderLayout.CENTER);
        contentPane.add(studentPanel, BorderLayout.CENTER);     // add panel to layout

        JButton register = new JButton("Register");                 // create button
        register.addActionListener(this);                           // add action performed by the button
        buttonPanel.add(register);                                  // add button to panel
        JButton clear = new JButton("Clear");                       // create button
        clear.addActionListener(this);                              // add action performed by the button
        buttonPanel.add(clear);                                     // add button to panel  
        contentPane.add(buttonPanel, BorderLayout.SOUTH);           // add button to content
    }

    // create the addlabel method for creating and adding label to the panel
    private void addLabel(Container panel, String labelText)
    {
        JLabel label = new JLabel(labelText);
        panel.add(label);
    }

    // create method for performing action by the button
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        if(command.equals("Record"))
        {
            voiceReg.voiceRecord();
        }
        if(command.equals("Register"))
        {
            createStudentRecord();
            clearUserRecord();
        }

        if(command.equals("Clear"))
            clearUserRecord();
    }


    // declare method for performing action by the method
    public void createStudentRecord()
    {
        String studentName = name.getText();
        String studentCourseApplied = couseApplied.getText();
        String studentSemester = semester.getText();
        String studentEmailAdd = email.getText();
        String filePath = voiceReg.wavFile.toString();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(new File(filePath));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        // check if all fields are filled correctly
        if(studentName.length()>1 && studentCourseApplied.length()>1 && studentEmailAdd.length()>1 && studentSemester.length()>1){
            JOptionPane.showMessageDialog(null, "Processing Records ...\n");
            Databases(studentName, studentCourseApplied, studentSemester, studentEmailAdd, inputStream);
            JOptionPane.showMessageDialog(null, "Records saved sucessfully...\n");
//        JOptionPane.showMessageDialog(null, "Registered : " + userName + "\nUser Id : " + Databases.id + " \n Course Applied : " + Databases.userCourseApplied + "\n Voice : " + Databases.userVoice + "\n");
            System.exit(0);
        }
        else{
            JOptionPane.showMessageDialog(null, "Fill-up all the fields with correct data ...\n");
        }
    }

    // declare method for performing action by the method
    public void redisplay()
    {
        name.setText("");
        name.setEditable(false);
//        age.setText("");
//        age.setEditable(false);
        couseApplied.setText("");
        couseApplied.setEditable(false);
        semester.setText("");
        semester.setEditable(false);
//        phone.setText("");
//        phone.setEditable(false);
        email.setText("");
        email.setEditable(false);
    }
    // declare method for performing action by the method
    public void clearUserRecord()
    {
        name.setText("");
//   age.setText("");
        couseApplied.setText("");
        semester.setText("");
//   phone.setText("");
        email.setText("");
    }

    // performing database operation
    public void Databases(String studentName, String studentCourseApplied, String studentSemester, String studentEmailAdd, InputStream studentVoice)
    {
        Students S1 = new Students(studentName, studentCourseApplied, studentSemester, studentEmailAdd, studentVoice);
        Databases D1 =  new Databases(S1);

    }


}    