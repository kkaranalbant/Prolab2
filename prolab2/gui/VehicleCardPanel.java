package prolab2.gui;

import javax.swing.*;
import java.awt.*;
import prolab2.model.WarVehicle;

public class VehicleCardPanel extends JPanel {

    private final WarVehicle vehicle;
    private boolean isSelected;
    private boolean isPlayerCard;

    public VehicleCardPanel(WarVehicle vehicle, boolean isPlayerCard) {
        this.vehicle = vehicle;
        this.isPlayerCard = isPlayerCard;
        this.isSelected = false;

        setPreferredSize(new Dimension(150, 200));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initializeComponents();
    }

    private void initializeComponents() {
        if (!isPlayerCard) {
            return;
        }

        JLabel idLabel = new JLabel(String.valueOf(vehicle.getId()));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(vehicle.getName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel typeLabel = new JLabel("Type: " + vehicle.getVehicleType());
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel attackLabel = new JLabel("Attack: " + vehicle.getAttack());
        attackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel defenseLabel = new JLabel("Defense: " + vehicle.getDefence());
        defenseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel levelLabel = new JLabel("Level: " + vehicle.getLevelPoint());
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 5)));
        add(idLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(nameLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(typeLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(attackLabel);
        add(defenseLabel);
        add(levelLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));

        if (!vehicle.getAdvantageTypes().isEmpty()) {
            JLabel advantagesTitle = new JLabel("Advantages:");
            advantagesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(advantagesTitle);

            vehicle.getAdvantageTypes().forEach(advantage -> {
                JLabel advantageLabel = new JLabel(
                        advantage.getVehicleType() + ": +" + advantage.getAdvantageAmount()
                );
                advantageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(advantageLabel);
            });
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isPlayerCard) {
            g.setColor(new Color(50, 50, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("WAR CARD", getWidth() / 2 - 30, getHeight() / 2);

            if (!vehicle.getIsActive()) {
                g.setColor(new Color(100, 100, 100, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.drawString("ELIMINATED", getWidth() / 2 - 35, getHeight() / 2 + 20);
            }
            return;
        }

        if (!vehicle.getIsActive()) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (isSelected) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 100, 255, 50));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public WarVehicle getVehicle() {
        return vehicle;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }
}
