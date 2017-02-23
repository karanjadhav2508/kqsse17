package voicerecognition;

// import the built-in java library class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AppletUsers extends JApplet implements ActionListener
{
    public static JTextField display;  // declare java Text Field

    public static JTextField name,age,couseApplied,semester,phone,email,voice;   // // declare java Text Field

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


        studentPanel = new JPanel(new GridLayout(10, 8));      // define java panel
        addLabel(studentPanel, "Name :");                      // add name label
        name = new JTextField("");                              // create a text field with the label name
        studentPanel.add(name);                                 // add a text field with the label name
        addLabel(studentPanel, "Age :");                        // add age label
        age = new JTextField("");                               // create a text field with the label age
        studentPanel.add(age);                                  // add a text field with the label age
        addLabel(studentPanel, "Couse Applied For :");          // add couseApplied label
        couseApplied = new JTextField("");                      // create a text field with the label couseApplied
        studentPanel.add(couseApplied);                         // add a text field with the label couseApplied
        addLabel(studentPanel, "Semester :");                   // add semester label
        semester = new JTextField("");                          // create a text field with the label semester
        studentPanel.add(semester);                             // add a text field with the label semester
        addLabel(studentPanel, "Phone Number :");               // add phone label
        phone = new JTextField("");                             // create a text field with the label phone
        studentPanel.add(phone);                                // add a text field with the label phone
        addLabel(studentPanel, "Email Address :");              // add email label
        email = new JTextField("");                             // create a text field with the label email
        studentPanel.add(email);                                // add a text field with the label email
        addLabel(studentPanel, "Record your Voice :");          // add voice label
        voice = new JTextField("");                             // create a text field with the label voice
        studentPanel.add(voice);                                // add a text field with the label voice
        contentPane.add(studentPanel, BorderLayout.CENTER);     // add panel to layout

        JPanel buttonPanel = new JPanel();

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

        if(command.equals("Register"))
        {
            createUserRecord();
            clearUserRecord();
//          customerPanel.setVisible(true);

        }

        if(command.equals("Clear"))
            clearUserRecord();

    }


    // declare method for performing action by the method
    public void createUserRecord()
    {

        String userName = name.getText();
        String userAge = age.getText();
        String userCourseApplied = couseApplied.getText();
        String userSemester = semester.getText();
        String userPhoneNum = phone.getText();
        String userEmailAdd = email.getText();
        String userVoice = voice.getText();

        // check if all fields are filled correctly
        if(userName.length()>1 && userAge.length()>1 && userCourseApplied.length()>1 && userPhoneNum.length()>1  && userEmailAdd.length()>1 && userSemester.length()>1 && userVoice.length()!=0){
            JOptionPane.showMessageDialog(null, "Processing Records ...\n");
//        Databases(userName, userAge, userCourseApplied, userSemester, userPhoneNum, userEmailAdd, userVoice);
            JOptionPane.showMessageDialog(null, "Records saved sucessfully...\n");
//        JOptionPane.showMessageDialog(null, "Registered : " + userName + "\nUser Id : " + Databases.id + " \n Course Applied : " + Databases.userCourseApplied + "\n Voice : " + Databases.userVoice + "\n");
            System.exit(0);
        }
        else{
            JOptionPane.showMessageDialog(null, "Fill-up all the fields with correct data ...\n");
        }

//    display.setText("Records saved sucessfully...\n");
//    display.setText("Registered:" + userName);
//    display.setText("\n");
//    display.setText("User Id: " + Databases.id + " \n RSA Key: " + Databases.RSAKey + " \n SHA Key: " + Databases.SHAKey + " \n");

    }

    // declare method for performing action by the method
    public void redisplay()
    {
        name.setText("");
        name.setEditable(false);
        age.setText("");
        age.setEditable(false);
        couseApplied.setText("");
        couseApplied.setEditable(false);
        semester.setText("");
        semester.setEditable(false);
        phone.setText("");
        phone.setEditable(false);
        email.setText("");
        email.setEditable(false);
        voice.setText("");
        voice.setEditable(false);
    }


    // declare method for performing action by the method
    public void clearUserRecord()
    {
        name.setText("");
        age.setText("");
        couseApplied.setText("");
        semester.setText("");
        phone.setText("");
        email.setText("");
        voice.setText("");
    }


    // performing database operation
    public void Databases(String userName, String userAge, String userCourseApplied, String userPhoneNum, String userEmailAdd, String userSemester, String userVoice)
    {
//    Users U1 = new Users(userName, userAge, userCourseApplied, userSemester, userPhoneNum, userEmailAdd, userVoice);
//    Databases D1 =  new Databases(U1);


    }


}

