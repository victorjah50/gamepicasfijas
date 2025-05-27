package model;

import java.util.Random;

public class Game {
    private final String secretNumber;
    private int attemptsLeft;

    public Game(int attempts) {
        this.secretNumber = generateSecretNumber();
        this.attemptsLeft = attempts;
    }

    private String generateSecretNumber() {
        Random rand = new Random();
        String num;
        do {
            num = String.format("%04d", rand.nextInt(10000));
        } while (hasRepeatedDigits(num));
        return num;
    }

    private boolean hasRepeatedDigits(String num) {
        boolean[] seen = new boolean[10];
        for (char c : num.toCharArray()) {
            int d = c - '0';
            if (seen[d]) return true;
            seen[d] = true;
        }
        return false;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public Result tryGuess(String guess) {
        if (guess.length() != 4) throw new IllegalArgumentException("Debe tener 4 dígitos");
        int picas = 0, fijas = 0;
        for (int i = 0; i < 4; i++) {
            char g = guess.charAt(i);
            if (g == secretNumber.charAt(i)) {
                fijas++;
            } else if (secretNumber.indexOf(g) != -1) {
                picas++;
            }
        }
        attemptsLeft--;
        return new Result(picas, fijas, secretNumber.equals(guess));
    }

    public static class Result {
        public final int picas;
        public final int fijas;
        public final boolean win;

        public Result(int picas, int fijas, boolean win) {
            this.picas = picas;
            this.fijas = fijas;
            this.win = win;
        }
    }
}
