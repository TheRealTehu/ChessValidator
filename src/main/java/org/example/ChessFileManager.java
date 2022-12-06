package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessFileManager {
    public List<char[][]> readFromFile() {
        String filePath = "src/main/resources/input2.txt";

        List<char[][]> boardStates = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {

            char[][] state = new char[8][8];
            int rowNum = 0;

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (!row.contains("=")) {
                    state[rowNum] = row.toCharArray();
                    rowNum++;
                } else {
                    boardStates.add(state);
                    state = new char[8][8];
                    rowNum = 0;
                }

            }

            boardStates.add(state);

        } catch (FileNotFoundException e) {
            System.out.println("Error while reading file!");
            return boardStates;
        }

        return boardStates;
    }

    public void writeToFile(List<char[][]> boards){
        File file = new File("valid_boards.txt");

        try {
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);

            for (char[][] board: boards) {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        fileWriter.append(board[i][j]);
                    }
                    fileWriter.append(System.lineSeparator());
                }
                fileWriter.append("=").append(System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
