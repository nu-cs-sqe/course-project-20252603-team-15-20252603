package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    // TC1 – Game accepts exactly 3 players
    @Test
    public void testGameAccepts3Players() {
        Game game = new Game(3);
        assertEquals(3, game.getNumberOfPlayers());
    }

    // TC2 – Game accepts exactly 4 players
    @Test
    public void testGameAccepts4Players() {
        Game game = new Game(4);
        assertEquals(4, game.getNumberOfPlayers());
    }

    // TC3 – Game rejects 2 players
    @Test
    public void testGameRejects2Players() {
        assertThrows(IllegalArgumentException.class, () -> new Game(2));
    }

    // TC4 – Game rejects 5 players
    @Test
    public void testGameRejects5Players() {
        assertThrows(IllegalArgumentException.class, () -> new Game(5));
    }

    // TC5 – Player with highest dice roll goes first
    @Test
    public void testHighestRollGoesFirst() {
        int[] rolls = {5, 10, 3}; // player 1 rolls highest
        Game game = new Game(3, stubDiceRoller(rolls));
        assertEquals(1, game.getFirstPlayerIndex());
    }

    // TC6 – Turn order proceeds clockwise from starting player
    @Test
    public void testTurnOrderClockwiseFromStartingPlayer() {
        int[] rolls = {4, 9, 6}; // player 1 rolls highest
        Game game = new Game(3, stubDiceRoller(rolls));
        assertArrayEquals(new int[]{1, 2, 0}, game.getTurnOrder());
    }

    // TC7 – Tied dice rolls are re-rolled
    @Test
    public void testTiedRollsAreReRolled() {
        // First round: player 0 and 1 tie at 8, player 2 gets 5
        // Re-roll between 0 and 1: player 0 gets 3, player 1 gets 7
        // Player 1 wins
        int[] rolls = {8, 8, 5, 3, 7};
        Game game = new Game(3, stubDiceRoller(rolls));
        assertEquals(1, game.getFirstPlayerIndex());
    }

    private DiceRoller stubDiceRoller(int[] rolls) {
        return new DiceRoller() {
            private int index = 0;
            @Override
            public int roll() {
                return rolls[index++];
            }
        };
    }
}