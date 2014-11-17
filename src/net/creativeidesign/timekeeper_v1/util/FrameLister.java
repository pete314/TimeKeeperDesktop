/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.creativeidesign.timekeeper_v1.util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Pi
 */
public class FrameLister extends WindowAdapter{
    public void windowClosing(WindowEvent e) {
       SyncData.upSync();
       
       System.exit(0);
                
    }
}
