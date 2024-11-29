package prolab2.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import prolab2.model.WarVehicle;
import prolab2.service.WarMechanic;
import prolab2.exception.NotActiveVehicleChoosingException;
import prolab2.exception.VehicleChoosingNumberException;

public class GameFrame extends JFrame {
    private final WarMechanic warMechanic;
    private JPanel playerCardsPanel;
    private JPanel computerCardsPanel;
    private JPanel battleAreaPanel;
    private JLabel playerScoreLabel;
    private JLabel computerScoreLabel;
    private JButton playButton;
    private List<WarVehicle> selectedCards;
    private List<VehicleCardPanel> playerCardPanels;
    private List<VehicleCardPanel> computerCardPanels;

    public GameFrame(WarMechanic warMechanic) {
        this.warMechanic = warMechanic;
        this.selectedCards = new ArrayList<>();
        this.playerCardPanels = new ArrayList<>();
        this.computerCardPanels = new ArrayList<>();

        setTitle("War Vehicles Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        layoutComponents();
        updateGameState();
    }

    private void initializeComponents() {
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        playerScoreLabel = new JLabel("Player Score: 0");
        computerScoreLabel = new JLabel("Computer Score: 0");
        scorePanel.add(playerScoreLabel);
        scorePanel.add(computerScoreLabel);
        add(scorePanel, BorderLayout.NORTH);

        computerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        computerCardsPanel.setBorder(BorderFactory.createTitledBorder("Computer's Cards"));

        battleAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        battleAreaPanel.setBorder(BorderFactory.createTitledBorder("Battle Area"));

        playerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerCardsPanel.setBorder(BorderFactory.createTitledBorder("Your Cards"));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playButton = new JButton("Play Selected Cards");
        playButton.addActionListener(e -> handlePlayButtonClick());
        playButton.setEnabled(false);
        controlPanel.add(playButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void layoutComponents() {
        JPanel gameAreaPanel = new JPanel();
        gameAreaPanel.setLayout(new BoxLayout(gameAreaPanel, BoxLayout.Y_AXIS));
        gameAreaPanel.add(computerCardsPanel);
        gameAreaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameAreaPanel.add(battleAreaPanel);
        gameAreaPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameAreaPanel.add(playerCardsPanel);

        add(gameAreaPanel, BorderLayout.CENTER);
    }

    private void updateGameState() {
        playerCardsPanel.removeAll();
        computerCardsPanel.removeAll();
        playerCardPanels.clear();
        computerCardPanels.clear();

        for (WarVehicle vehicle : warMechanic.getVehicles()) {
            if (vehicle.getIsForHuman()) {
                VehicleCardPanel cardPanel = new VehicleCardPanel(vehicle, true);
                cardPanel.addMouseListener(new CardClickListener(cardPanel));
                playerCardPanels.add(cardPanel);
                playerCardsPanel.add(cardPanel);
            } else {
                VehicleCardPanel cardPanel = new VehicleCardPanel(vehicle, false);
                computerCardPanels.add(cardPanel);
                computerCardsPanel.add(cardPanel);
            }
        }

        playerScoreLabel.setText("Player Score: " + warMechanic.getHumanScore());
        computerScoreLabel.setText("Computer Score: " + warMechanic.getComputerScore());

        revalidate();
        repaint();
    }

    private void handlePlayButtonClick() {
        if (warMechanic.getCurrentStepNumber() == 0 && selectedCards.size() != 3) {
            JOptionPane.showMessageDialog(this, "Please select 3 cards for the first round!");
            return;
        }

        if (warMechanic.getCurrentStepNumber() > 0 && selectedCards.size() != 1) {
            JOptionPane.showMessageDialog(this, "Please select 1 card for this round!");
            return;
        }

        try {
            List<WarVehicle> computerCards;
            if (warMechanic.getCurrentStepNumber() == 0) {
                computerCards = warMechanic.getRandomWarVehiclesForFirstRound();
            } else {
                computerCards = List.of(warMechanic.getRandomWarVehicleForOtherRounds());
            }

            warMechanic.war(selectedCards, computerCards);
            updateBattleArea(selectedCards, computerCards);

            // Check if game is over
            if (warMechanic.isWarOverByStepNumber() ||
                    warMechanic.isWarOverByVehiclesForHuman() ||
                    warMechanic.isWarOverByVehiclesForComputer()) {

                JOptionPane.showMessageDialog(this, warMechanic.getWarResultMessage());
                System.exit(0);
            }

            selectedCards.clear();
            updateGameState();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateBattleArea(List<WarVehicle> playerCards, List<WarVehicle> computerCards) {
        battleAreaPanel.removeAll();

        JPanel playerBattleCards = new JPanel(new FlowLayout());
        JPanel computerBattleCards = new JPanel(new FlowLayout());

        for (WarVehicle card : playerCards) {
            playerBattleCards.add(new VehicleCardPanel(card, true));
        }

        for (WarVehicle card : computerCards) {
            VehicleCardPanel cardPanel = new VehicleCardPanel(card, true);
            computerBattleCards.add(cardPanel);
        }

        battleAreaPanel.add(playerBattleCards);
        battleAreaPanel.add(new JLabel(" VS "));
        battleAreaPanel.add(computerBattleCards);

        battleAreaPanel.revalidate();
        battleAreaPanel.repaint();
    }

    private class CardClickListener extends MouseAdapter {
        private final VehicleCardPanel cardPanel;

        public CardClickListener(VehicleCardPanel cardPanel) {
            this.cardPanel = cardPanel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!cardPanel.getVehicle().getIsActive()) {
                JOptionPane.showMessageDialog(GameFrame.this, "This card is not active!");
                return;
            }

            if (!selectedCards.contains(cardPanel.getVehicle())) {
                if (warMechanic.getCurrentStepNumber() == 0 && selectedCards.size() < 3) {
                    selectedCards.add(cardPanel.getVehicle());
                    cardPanel.setSelected(true);
                } else if (warMechanic.getCurrentStepNumber() > 0 && selectedCards.isEmpty()) {
                    selectedCards.add(cardPanel.getVehicle());
                    cardPanel.setSelected(true);
                }
            } else {
                selectedCards.remove(cardPanel.getVehicle());
                cardPanel.setSelected(false);
            }

            playButton.setEnabled(!selectedCards.isEmpty());
            revalidate();
            repaint();
        }
    }
}
