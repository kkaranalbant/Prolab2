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
            String input = JOptionPane.showInputDialog(null,
                    "Lütfen maksimum adım sayısını girin (1-20 arası):",
                    "Adım Sayısı",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null || input.trim().isEmpty()) {
                System.exit(0);
            }

            try {
                int maxSteps = Integer.parseInt(input.trim());

                if (maxSteps < 1 || maxSteps > 20) {
                    JOptionPane.showMessageDialog(null,
                            "Geçersiz adım sayısı! 1-20 arası bir değer giriniz.",
                            "Hata",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                WarMechanic warMechanic = WarMechanic.getInstance(maxSteps, 20, 0);
                warMechanic.init();

                GameFrame frame = new GameFrame(warMechanic);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Lütfen geçerli bir sayı giriniz!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        });
    }
}