package com.beaker.cpen321tutorial;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class TicTacToeGame {
    char[] board;
    Set<Integer> played;
    public static final String TAG = "TicTacTpeGame";

    TicTacToeGame()
    {
        board = new char[9];
        for(int i = 0; i < board.length; i++)
        {
            board[i] = '-';
        }
        played = new HashSet<>();
    }

    public boolean playerMove(int move)
    {
        if(move < 0 || move > 9 || played.contains(move)) return false;
        else
        {
            board[move] = 'X';
            played.add(move);
            return true;
        }
    }

    public int computerMove()
    {
        double rand = Math.random() * 8;
        int move = (int) rand;

        while(true)
        {
            if(!played.contains(move))
            {
                played.add(move);
                board[move] = 'O';
                return move;
            }
            rand = Math.random() * 8;
            move = (int) rand;
        }

    }

    public boolean hasWon(char player) throws IllegalArgumentException
    {
        if(player != 'X' && player != 'O')
            throw new IllegalArgumentException("Player must be X or O");

        boolean win = false;

        for(int i = 0; i < 3; i++)
        {
            if ((board[i] == player) && (board[i + 3] == player) && (board[i+6] == player))
            {
                win = true;
                Log.d(TAG, "V" + (board[i + 3] == player));
            }
        }

        for(int i = 0; i <= 6; i += 3)
        {
            if(board[i] == player && board[i + 1] == player && board[i+2] == player)
            {
                win = true;
                Log.d(TAG, "H" + i);
            }
        }

        Log.d(TAG, "H+V" + win);
        if(board[0] == player && board[4] == player && board[8] == player) win = true;
        if(board[2] == player && board[4] == player && board[6] == player) win = true;

        return win;
    }

    /**
     * Checks whether to end game on player's turn
     * @return
     * 0 - Incomplete game,
     * 1 - User wins,
     * 2 - com wins,
     * 3 - Tie
     */
    public int terminate()
    {
        if (!hasWon('X') && !hasWon('O') && played.size() == 9) return 3;

        //Returns results
        if(hasWon('X')) return 1;
        else if (hasWon('O')) return 2;
        else return 0;
    }

    public void reset()
    {
        board = new char[9];
        played.clear();
    }

}
