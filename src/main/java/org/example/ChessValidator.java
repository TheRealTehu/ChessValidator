package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ChessValidator {
    public static final int BOARD_SIZE = 8;
    private static final char WHITE_KING = 'k';
    private static final char BLACK_KING = 'K';
    private static final char WHITE_QUEEN = 'v';
    private static final char BLACK_QUEEN = 'V';
    private static final char WHITE_ROOK = 'b';
    private static final char BLACK_ROOK = 'B';
    private static final char WHITE_BISHOP = 'f';
    private static final char BLACK_BISHOP = 'F';
    private static final char WHITE_KNIGHT = 'h';
    private static final char BLACK_KNIGHT = 'H';
    private static final char WHITE_PAWN = 'g';
    private static final char BLACK_PAWN = 'G';
    private Map<Character, Integer> pieces;

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
        if (!hasValidBoardSize(board) || !noPawnsInIncorrectRows(board)) {
            return false;
        }
        printBoard(board);
        countPieces(board);

        System.out.println("hasCorrectNumberOfPawns: " + hasCorrectNumberOfPawns());
        System.out.println("hasOneKingEach: " + hasOneKingEach());
        System.out.println("hasCorrectNumberOfPieces: " + hasCorrectNumberOfPieces());
        System.out.println("!kingsAreNextToEachOther: " + !kingsAreNextToEachOther(board));

        return hasCorrectNumberOfPawns() && hasOneKingEach()
               && hasCorrectNumberOfPieces() && !kingsAreNextToEachOther(board);
    }

    private static void printBoard(char[][] board) {
        for (char[] chars : board) {
            System.out.println(new String(chars));
        }
    }

    private boolean kingsAreNextToEachOther(char[][] board) {
        return findAKing(board).map(kingPos -> kingPos.getNeighbours()
                .anyMatch(pos -> {
                    char c = board[pos.row()][pos.column()];
                    return c == BLACK_KING || c == WHITE_KING;
                })).orElse(false);
    }

    private Optional<Position> findAKing(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == BLACK_KING || board[i][j] == WHITE_KING) {
                    return Optional.of(new Position(i, j));
                }
            }
        }
        return Optional.empty();
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

    private boolean hasValidBoardSize(char[][] board) {
        return board.length == BOARD_SIZE &&
               Arrays.stream(board)
                       .allMatch(row -> row.length == BOARD_SIZE);
    }
}

record Position(int row, int column) {
    public Stream<Position> getNeighbours() {
        return IntStream.range(row - 1, row + 2)
                .boxed()
                .flatMap(r -> IntStream.range(column - 1, column + 2).mapToObj(c -> new Position(r, c)))
                .filter(p -> !p.equals(this))
                .filter(Position::isInsideBoard);
    }

    private boolean isInsideBoard() {
        return 0 <= row && row < ChessValidator.BOARD_SIZE &&
               0 <= column && column < ChessValidator.BOARD_SIZE;
    }
}