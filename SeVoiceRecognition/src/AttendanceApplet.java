// import the built-in java library class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 
public class AttendanceApplet extends JApplet implements ActionListener
 {

    public static JTextField display;  // declare java Text Field
    public static JTextField name;   // // declare java Text Field
    Attendance voiceAttendance = new Attendance();
    public JPanel studentPanel;  // // declare java Panel

    public void init()
      {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(20,15));  // define layout of the frame
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));   // define java panel
        JLabel prompt = new JLabel("Display Messages:");         
        centerPanel.add(prompt);
        display = new JTextField("Please Fill for Attendance ...");  // the text field filled 
        display.setEditable(false);    // field cannot be editable
        centerPanel.add(display);      // add field with panel
        contentPane.add(centerPanel, BorderLayout.NORTH);    // layout the border
        JPanel buttonPanel = new JPanel();
            studentPanel = new JPanel(new GridLayout(8, 2));      // define java panel
            addLabel(studentPanel, "Name :");                      // add name label    
            name = new JTextField("");                              // create a text field with the label name
            studentPanel.add(name);                                 // add a text field with the label name
            JButton voice = new JButton("Check Attendance");  // define button 1
            voice.setPreferredSize(new Dimension(100, 30));        // set size of the button 1
            voice.addActionListener(this);
            buttonPanel.add(voice);                                     // add button to panel  

//            studentPanel.add(voice,BorderLayout.CENTER);
            contentPane.add(studentPanel, BorderLayout.CENTER);     // add panel to layout
            JButton clear = new JButton("Clear");                       // create button
            clear.addActionListener(this);                              // add action performed by the button
            buttonPanel.add(clear);                                     // add button to panel  
            contentPane.add(buttonPanel, BorderLayout.SOUTH);           // add button to content
     }//end init()

// create the addlabel method for creating and adding label to the panel 
private void addLabel(Container panel, String labelText)
  {
        JLabel label = new JLabel(labelText);
        panel.add(label);
  }//end addLabel

// create method for performing action by the button
    public void actionPerformed(ActionEvent event)
      {
        String command = event.getActionCommand();
         
        if(command.equals("Check Attendance"))
         {         
          checkStudentRecord();   
          voiceAttendance.voiceRecord();           
         }//end if
        
        if(command.equals("Clear"))
        clearUserRecord();        
    } //end actionPerformed()

// declare method for performing action by the method    
  public void checkStudentRecord()
    {                 
        String studentName = name.getText();
        
        String filePath = voiceAttendance.wavFile.toString();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(new File(filePath));
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        
   // check if all fields are filled correctly 
    if(studentName.length()>1){        
        JOptionPane.showMessageDialog(null, "Processing Records ...\n");
        try {  
             JOptionPane.showMessageDialog(null, "Check for Attendance...\n");
             DatabaseAttendance(studentName, inputStream);
            } catch (IOException ex) {
                Logger.getLogger(AttendanceApplet.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.exit(0);     
     }
     else{        
        JOptionPane.showMessageDialog(null, "Fill-up all the fields with correct data ...\n");
      }
    }//end checkStudentRecord()
   
// declare method for performing action by the method       
public void clearUserRecord()
  {
   name.setText("");
  }     
// performing database operation
public void DatabaseAttendance(String studentName, InputStream studentVoice) throws IOException
  {
    StudentsAttendance S1 = new StudentsAttendance(studentName, studentVoice);
    DatabaseAttendance D1 =  new DatabaseAttendance(S1);
    
  }

}