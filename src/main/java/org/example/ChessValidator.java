package org.example;

import java.util.HashMap;
import java.util.Map;

public class ChessValidator {
    private final int BOARD_SIZE = 8;
    private final char WHITE_KING = 'k';
    private final char BLACK_KING = 'K';
    private final char WHITE_QUEEN = 'v';
    private final char BLACK_QUEEN = 'V';
    private final char WHITE_ROOK = 'b';
    private final char BLACK_ROOK = 'B';
    private final char WHITE_BISHOP = 'f';
    private final char BLACK_BISHOP = 'F';
    private final char WHITE_KNIGHT = 'h';
    private final char BLACK_KNIGHT = 'H';
    private final char WHITE_PAWN = 'g';
    private final char BLACK_PAWN = 'G';
    private Map<Character, Integer> pieces;

    public ChessValidator() {
    }

    private void countPieces(char[][] board) {
        pieces = new HashMap<>();

        initializeMap();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (pieces.containsKey(board[i][j])) {
                    pieces.put(board[i][j], pieces.get(board[i][j]) + 1);
                }
            }
        }
    }

    private void initializeMap() {
        pieces.put(WHITE_KING, 0);
        pieces.put(BLACK_KING, 0);
        pieces.put(WHITE_QUEEN, 0);
        pieces.put(BLACK_QUEEN, 0);
        pieces.put(WHITE_ROOK, 0);
        pieces.put(BLACK_ROOK, 0);
        pieces.put(WHITE_BISHOP, 0);
        pieces.put(BLACK_BISHOP, 0);
        pieces.put(WHITE_KNIGHT, 0);
        pieces.put(BLACK_KNIGHT, 0);
        pieces.put(WHITE_PAWN, 0);
        pieces.put(BLACK_PAWN, 0);
    }

    public boolean isValidState(char[][] board) {

        System.out.println("New board");

        if (!isValidBoard(board) || !noPawnsInIncorrectRows(board)) {
            return false;
        }


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }


        countPieces(board);

        System.out.println("hasCorrectNumberOfPawns: " + hasCorrectNumberOfPawns());
        System.out.println("hasOneKingEach: " + hasOneKingEach());
        System.out.println("hasCorrectNumberOfPieces: " + hasCorrectNumberOfPieces());
        System.out.println("!kingsAreNextToEachOther: " + !kingsAreNextToEachOther(board));

        return hasCorrectNumberOfPawns() && hasOneKingEach()
               && hasCorrectNumberOfPieces() && !kingsAreNextToEachOther(board);
    }

    private boolean kingsAreNextToEachOther(char[][] board) {
        int[] kingPosition = findAKing(board);
        int startI = (kingPosition[0] - 1 < 0) ? kingPosition[0] : kingPosition[0] - 1;
        int startJ = (kingPosition[1] - 1 < 0) ? kingPosition[1] : kingPosition[1] - 1;
        int endI = (kingPosition[0] + 1 >= BOARD_SIZE) ? kingPosition[0] : kingPosition[0] + 1;
        int endJ = (kingPosition[1] + 1 >= BOARD_SIZE) ? kingPosition[1] : kingPosition[1] + 1;

        for (int i = startI; i <= endI; i++) {
            for (int j = startJ; j <= endJ; j++) {
                if ((board[i][j] == BLACK_KING || board[i][j] == WHITE_KING) && (i != kingPosition[0] || j != kingPosition[1])) {
                    return true;
                }
            }
        }

        return false;
    }

    private int[] findAKing(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == BLACK_KING || board[i][j] == WHITE_KING) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private boolean hasCorrectNumberOfPieces() {
        int maxNumberOfWhitePieces = 10 - pieces.get(WHITE_PAWN);
        int maxNumberOfBlackPieces = 10 - pieces.get(BLACK_PAWN);

        return hasCorrectNumberOfPiece(WHITE_QUEEN, maxNumberOfWhitePieces - 1) &&
               hasCorrectNumberOfPiece(BLACK_QUEEN, maxNumberOfBlackPieces - 1) &&
               hasCorrectNumberOfPiece(WHITE_ROOK, maxNumberOfWhitePieces) &&
               hasCorrectNumberOfPiece(BLACK_ROOK, maxNumberOfBlackPieces) &&
               hasCorrectNumberOfPiece(WHITE_BISHOP, maxNumberOfWhitePieces) &&
               hasCorrectNumberOfPiece(BLACK_BISHOP, maxNumberOfBlackPieces) &&
               hasCorrectNumberOfPiece(WHITE_KNIGHT, maxNumberOfWhitePieces) &&
               hasCorrectNumberOfPiece(BLACK_KNIGHT, maxNumberOfBlackPieces);
    }

    private boolean hasCorrectNumberOfPiece(char piece, int maxNumberOfPiece) {
        return pieces.get(piece) <= maxNumberOfPiece;
    }

    private boolean hasOneKingEach() {
        return pieces.get(WHITE_KING) == 1 && pieces.get(BLACK_KING) == 1;
    }

    private boolean hasCorrectNumberOfPawns() {
        return pieces.get(WHITE_PAWN) <= 8 && pieces.get(BLACK_PAWN) <= 8;
    }

    private boolean noPawnsInIncorrectRows(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[0][i] == WHITE_PAWN || board[BOARD_SIZE - 1][i] == WHITE_PAWN
                || board[0][i] == BLACK_PAWN || board[BOARD_SIZE - 1][i] == BLACK_PAWN) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidBoard(char[][] board) {
        if (board.length != BOARD_SIZE) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            if (board[i].length != BOARD_SIZE) {
                return false;
            }
        }
        return true;
    }
}
