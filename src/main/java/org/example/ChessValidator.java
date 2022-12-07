package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

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
        getAllBoardLocations().forEach(p -> pieces.computeIfPresent(boardAt(board, p), (ch, i) -> i + 1));
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
        return findAKing(board).map(
                kingPos -> kingPos.getNeighbours()
                        .map(p -> boardAt(board, p))
                        .anyMatch(c -> c == BLACK_KING || c == WHITE_KING))
                .orElse(false);
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
        boolean pawnInFirstRow = range(0, BOARD_SIZE).mapToObj(i -> board[0][i]).anyMatch(c -> c == WHITE_PAWN || c == BLACK_PAWN);
        boolean pawnInLastRow = range(0, BOARD_SIZE).mapToObj(i -> board[BOARD_SIZE - 1][i]).anyMatch(c -> c == WHITE_PAWN || c == BLACK_PAWN);
        return !pawnInFirstRow && !pawnInLastRow;
    }

    private boolean hasValidBoardSize(char[][] board) {
        return board.length == BOARD_SIZE && Arrays.stream(board).allMatch(row -> row.length == BOARD_SIZE);
    }

    private Stream<Position> getAllBoardLocations() {
        return range(0, BOARD_SIZE)
                .boxed()
                .flatMap(row -> range(0, BOARD_SIZE).mapToObj(col -> new Position(row, col)));
    }

    private char boardAt(char[][] board, Position p) {
        return board[p.row()][p.column()];
    }
}

record Position(int row, int column) {
    public Stream<Position> getNeighbours() {
        return range(row - 1, row + 2)
                .boxed()
                .flatMap(r -> range(column - 1, column + 2).mapToObj(c -> new Position(r, c)))
                .filter(p -> !p.equals(this))
                .filter(Position::isInsideBoard);
    }

    private boolean isInsideBoard() {
        return 0 <= row && row < ChessValidator.BOARD_SIZE &&
               0 <= column && column < ChessValidator.BOARD_SIZE;
    }
}