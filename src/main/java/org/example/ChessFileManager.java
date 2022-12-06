package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessFileManager {
    public List<char[][]> readFromFile() {
        String filePath = "src/main/resources/input.txt";

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
}
