/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prolab2;

import javax.swing.*;
import prolab2.service.WarMechanic;
import prolab2.gui.GameFrame;


public class Prolab2 {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            WarMechanic warMechanic = WarMechanic.getInstance(5, 20, 0);
            warMechanic.init();

            GameFrame frame = new GameFrame(warMechanic);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}