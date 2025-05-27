package ui;

import controller.GameController;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {
    private final GameController controller = new GameController();
    private JTextField guessField;
    private JLabel resultLabel, attemptsLabel;
    private JComboBox<String> difficultyBox;

    public GameWindow() {
        setTitle("Picas y Fijas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Dificultad:"));
        difficultyBox = new JComboBox<>(new String[]{"Muy fácil (10)", "Fácil (7)", "Normal (5)", "Difícil (4)", "Muy difícil (3)"});
        topPanel.add(difficultyBox);
        JButton startBtn = new JButton("Iniciar Juego");
        topPanel.add(startBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Tu intento:"));
        guessField = new JTextField(5);
        centerPanel.add(guessField);
        JButton guessBtn = new JButton("Probar");
        centerPanel.add(guessBtn);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        resultLabel = new JLabel(" ");
        attemptsLabel = new JLabel(" ");
        bottomPanel.add(resultLabel);
        bottomPanel.add(attemptsLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        startBtn.addActionListener(this::startGame);
        guessBtn.addActionListener(this::makeGuess);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame(ActionEvent e) {
        int attempts = switch (difficultyBox.getSelectedIndex()) {
            case 0 -> 10;
            case 1 -> 7;
            case 2 -> 5;
            case 3 -> 4;
            default -> 3;
        };
        controller.startNewGame(attempts);
        resultLabel.setText("¡Juego iniciado! Ingresa tu intento.");
        attemptsLabel.setText("Intentos restantes: " + attempts);
        guessField.setText("");
        guessField.setEnabled(true);
    }

    private void makeGuess(ActionEvent e) {
        String guess = guessField.getText();
        if (guess.length() != 4 || !guess.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Ingresa un número de 4 dígitos sin repetir.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Game.Result result = controller.guess(guess);
        if (result.win) {
            resultLabel.setText("¡Ganaste! El número era: " + controller.getGame().getSecretNumber());
            guessField.setEnabled(false);
        } else if (controller.getGame().getAttemptsLeft() == 0) {
            resultLabel.setText("¡Perdiste! El número era: " + controller.getGame().getSecretNumber());
            guessField.setEnabled(false);
        } else {
            resultLabel.setText("Picas: " + result.picas + " | Fijas: " + result.fijas);
            attemptsLabel.setText("Intentos restantes: " + controller.getGame().getAttemptsLeft());
        }
        guessField.setText("");
    }
}
