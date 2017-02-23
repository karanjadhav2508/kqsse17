/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
