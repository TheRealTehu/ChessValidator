package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessFileManager {
    public List<char[][]> readFromFile(){
        String filePath = "src/main/resources/input.txt";

        List<char[][]> boardStates = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filePath))) {

            while (scanner.hasNextLine()){
                String row = scanner.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error while reading file!");
            return boardStates;
        }
    }
}
