package com.njok.GameUtils;

import javax.swing.*;
import java.util.Objects;

public class WinDetection {

    public static boolean isWin(JButton[][] board, String player) {
        if (diagonalMatch(board, player)) {
            return true;
        } else if (horizontalMatch(board, player)) {
            return true;
        } else if (verticalMatch(board, player)) {
            return true;
        }

        return false;
    }

    private static boolean diagonalMatch(JButton[][] board, String player) {
        //The easy part
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (Objects.equals(board[i][i].getText(), player)) {
                count++;
            }
        }

        if (count == 3) {
            return true;
        }
        count = 0;

        //The harder part
        for (int i = 0; i < board.length; i++) {
            if (i == 0) {
                count += Objects.equals(board[i][2].getText(), player) ? 1 : 0;
            } else if (i == 2) {
                count += Objects.equals(board[i][0].getText(), player) ? 1 : 0;
            } else {
                count += Objects.equals(board[i][i].getText(), player) ? 1 : 0;
            }
            if (count != i + 1) {
                return false;
            }
        }

        return true;
    }
    //TODO: Impl
    private static boolean horizontalMatch(JButton[][] board, String player) {
        int count = 0;
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
                if (Objects.equals(board[i][j].getText(), player) && Objects.equals(board[i][j].getText(), board[i - 1][j - 1].getText())) {
                    count++;
                }
            }
        }

        return count == 3;
    }

    private static boolean verticalMatch(JButton[][] board, String player) {
        int count = 0;
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board.length; j++) {
                if (Objects.equals(board[j][i].getText(), player) && Objects.equals(board[j][i].getText(), board[j - 1][i - 1].getText())) {
                    count++;
                }
            }
        }

        return count == 3;
    }

    private static boolean isValidPoint(int[] point) {
        return point[0] == 0 || point[0] == 1 && point[1] == 2 || point[0] == 2 && point[1] == 0;
    }
}
