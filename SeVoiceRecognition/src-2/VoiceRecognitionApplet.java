package voicerecognition;

// import the built-in java library class 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class VoiceRecognitionApplet extends JApplet implements Runnable  {
    
//    JButton button1, button2;    
//    JLabel name,age,couse_applied,semester,ph,email;   
   
    VoiceRecognitionApplet me;

    AppletUsers new_User = new AppletUsers();  // create object of AppletUsers class 
    
    public void init() {
        
            Container contentPane = getContentPane();
            contentPane.setLayout(new BorderLayout(80,80));   // define layout of the frame
 
            JPanel northPanel = new JPanel(); //  (new GridLayout(3,3,2,2));   // define java panel
            JButton register = new JButton("New User Registration with Voice Record");  // define button 1 
            register.setPreferredSize(new Dimension(300, 30));        // set size of the button 1
//            register.addActionListener(this);
            northPanel.add(register);                                 // add button 1 to the panel
            register.addActionListener(new ButtonListener());        // add action performed by the buuton 1
            contentPane.add(northPanel); //BorderLayout.NORTH        // add field with panel
            
            JPanel southPanel = new JPanel(); //(new GridLayout(3,3,2,2));
            JButton newUser = new JButton("Daily Attendance");         // define button 2
            newUser.setPreferredSize(new Dimension(300, 30));       // set size of the button 2
//           newUser.addActionListener(this);
            southPanel.add(newUser);                                // add button 2 to the panel
            newUser.addActionListener(new ButtonListener());        // add action performed by the buuton 2
            contentPane.add(southPanel);                            // add field with panel
            
        
        //WindowUtilities.setNativeLookAndFeel();
	Container content = getContentPane();
	content.setBackground(Color.white);                         // set background color
	content.setLayout(new FlowLayout());                        // set the layout
        
	me = this;
    }
    
 
    public void run() {
	
    }

// create a method for performing operation done by button 
public class ButtonListener implements ActionListener 
      {
       public void actionPerformed(ActionEvent event)
         {
          
          String command = event.getActionCommand();
 
          
        if(command.equals("New User Registration with Voice Record"))
         {  
           JOptionPane.showMessageDialog(null, "Link to New USER Entry page...\n");
           new_User.init();
           Frame frame1 = new JFrame();  // add frame 1
           frame1.setVisible(true);
           frame1.add(new_User);        // add all fields to frame 1
           frame1.setSize(500,300);     // add set dimension size to frame 1
         }
          
          if(command.equals("Daily Attendance"))
            {  
              JOptionPane.showMessageDialog(null, "Check for User access permission ...\n");             
//              CheckUser.init();
              JFrame frame2 = new JFrame();    // add frame 2
              frame2.setVisible(true);
//              frame2.add(CheckUser);
              frame2.setSize(500,300);        // add set dimension size to frame 2      
            }

          
	}
    }
    
}
