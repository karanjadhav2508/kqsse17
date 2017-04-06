import javax.swing.*;

public class Recognition extends JApplet{

   public void RecognitionRate(String studentName, String fingerprint1, String fingerprint2)
    {
     int countSim = 0;
     double recognition_rate = 0.0;
     // checking actual vs recorded data
     for (int i=0; i<fingerprint1.length(); i++){
        if(fingerprint1.charAt(i) == fingerprint2.charAt(i))
          {  
           countSim = countSim + 1;   
          }
     }
    // calculate recognition rate
    recognition_rate = (countSim*100/fingerprint1.length());  
//    System.out.println("Recognition Rate: " + recognition_rate + "%");
//    System.out.println("Counter: " + countSim + "Length: " + fingerprint1.length());
    if (recognition_rate > 20)
    // deciding thresholding value for comparison
      { 
      JOptionPane.showMessageDialog(null, "Recognition Rate :" + recognition_rate + "%\n *****" + studentName + " is Present for todays class ***** ");
     }else
      {
      JOptionPane.showMessageDialog(null, "Recognition Rate :" + recognition_rate + "%\n *****" + studentName + " is Not Present for todays class ***** ");
      }        
    }
}
