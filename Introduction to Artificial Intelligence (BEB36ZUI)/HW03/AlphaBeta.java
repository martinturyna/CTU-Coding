package cz.cvut.student;

import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.ArrayList;
import java.util.Random;

public class AlphaBeta extends Algorithm {

    private int maximizingPlayer = 999;
    private int minimizingPlayer = 999;

    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {

        if (maximizingPlayer == 999) {
            if (player == 0) {
                maximizingPlayer = player;
                minimizingPlayer = 4;
            } else {
                maximizingPlayer = player;
                minimizingPlayer = 0;
            }
        }

        ArrayList<Move> possibleMoves = game.generatePossibleMoves(player);
        ArrayList<Board> nextBoards = new ArrayList<>();

        for (Move m : possibleMoves) {
            Board board = new Board(game);
            board.makeMove(m);
            nextBoards.add(board);
        }

        if (player == maximizingPlayer) {
            nextBoards.sort(((o1, o2) -> o2.evaluateBoard() - o1.evaluateBoard()));
        } else {
            nextBoards.sort(((o1, o2) -> o1.evaluateBoard() - o2.evaluateBoard()));
        }

        if (depth == 0 || possibleMoves.isEmpty() || game.isTerminate(player) != Board.DUMMY) {
            return game.evaluateBoard();
        }

        if (depth == 1) {
            return nextBoards.get(0).evaluateBoard();
        }


        if (player == maximizingPlayer) {
            int v = Integer.MIN_VALUE;
            for (Board b : nextBoards) {
                v = Integer.max(v, run(b, depth-1,  minimizingPlayer, alpha, beta));
                alpha = Integer.max(alpha, v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        } else {
            int v = Integer.MAX_VALUE;
            for (Board b : nextBoards) {
                v = Integer.min(v, run(b, depth-1, maximizingPlayer, alpha, beta));
                beta = Integer.min(beta, v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }
    }
}
