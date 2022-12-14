package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChessFileManager manager = new ChessFileManager();
        List<char[][]> boards = manager.readFromFile();

//        for (char[][] board: boards) {
//            for (int i = 0; i < board.length; i++) {
//                for (int j = 0; j < board[i].length; j++) {
//                    System.out.print(board[i][j]);
//                }
//                System.out.println();
//            }
//        }
        ChessValidator validator = new ChessValidator();
        List<char[][]> validBoards = boards.stream().filter(validator::isValidState).toList();
        manager.writeToFile(validBoards);
    }
}