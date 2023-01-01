package model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;


class GameMultiPlayerTest {

    private GameMultiPlayer game;

    @BeforeEach
    public void setUp() {
        game = new GameMultiPlayer();
        Controller c = new Controller();
        game.setUpHost(2);
        game.init(c);
        game.getList().set(0,"test");

    }

    @Test
    public void testSetUp() {
        String hostAddress = "localhost";
        game.setUp(hostAddress, false);
        assertEquals(hostAddress, GameMultiPlayer.SERVER_HOST);
    }

    @Test
    public void testSetUpHost() {
        int nbPlayers = 2;
        game.setUpHost(nbPlayers);
        assertEquals(nbPlayers, game.nbPlayers);
        assertTrue(GameMultiPlayer.isHost);

    }

    @Test
    public void testInit() {
        assertEquals(Mode.MULTI, game.mode);
        assertNotNull(game.currentList);
        assertEquals(0, game.currentPos);
        assertEquals(0, game.correctCharacters);
        assertEquals(0, game.typedCharacters);
        assertEquals(20, game.lives);
        assertTrue(game.gameRunning);
        assertNotNull(game.redWordsPos);
        assertNotNull(game.regularityList);
    }

    @Test
    public void testInitRedWords() {
        game.currentList = WordList.startingList();
        game.initRedWords();
        assertNotNull(game.redWordsPos);
        assertTrue(game.redWordsPos.size() <= game.currentList.size());
    }

    @Test
    public void testKeyInputCorrectCharacter() {
        assertTrue(game.keyInput('t'));
        assertEquals(1, game.getCurrentPos());
        assertEquals(1, game.getCorrectCharacters());
        assertEquals(1, game.getTypedCharacters());
    }

    @Test
    public void testKeyInputLivesExpired() {
        game.lives = 1;
        game.currentList = List.of("test");
        game.currentPos = 0;
        game.keyInput('x');
        assertEquals(0, game.lives);
    }

    @Test
    public void testUpdateList() {
        /*
         * This is the same function as in the other Game(...) classes
         * with server implications, which is difficult to test here.
         */
    }




}