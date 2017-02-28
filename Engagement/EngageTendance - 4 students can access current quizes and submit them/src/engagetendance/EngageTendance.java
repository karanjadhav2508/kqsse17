/* Created by Samantha Scoggins with Group F: Karan Jadhav and Qaiss Khan Alokozai
 * 
 *Purpose: Group F researched and conducted surveys to learn more about the problem of Attendence Management in a University Setting.
 *This program is one way of approaching the problem. Most professor say that Attendance tracking is time consuming, so we either need 
 *          to eliminate time as much as possible, or make it a part of class so that the professor can measure the level of engagement as well.
 *          Basic functionality will be to keep track of attendendance however. 
 *
 */
package engagetendance;

import javax.swing.JFrame;

/**
 *
 * @author Sam
 */
public class EngageTendance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // This is for attendance management to focus on class engagement and participation.
        
        Password p = new Password();
        p.setVisible(true);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setLocation(600, 300);
    }
    
}
