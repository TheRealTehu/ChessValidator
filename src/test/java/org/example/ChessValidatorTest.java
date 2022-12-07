package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessValidatorTest {

    ChessValidator validator = new ChessValidator();

    @BeforeEach
    void setUp() {
    }

    @Test
    void given_KingsAreNextToEachOther_When_isValidState_Then_ReturnFalse() {
        char[][] board = convert("""
                ........
                ........
                ........
                ........
                ........
                ........
                ........
                ......Kk""");
        assertFalse(validator.isValidState(board));
    }

    @Test
    void given_PlayerHasMoreKings_When_isValidState_Then_ReturnFalse() {
        char[][] board = convert("""
                bhfvkfhb
                vvvvvvv.
                ........
                ........
                .k..K...
                ........
                ........
                ........""");
        assertFalse(validator.isValidState(board));
    }
    @Test
    void given_normalState1_When_isValidState_Then_ReturnTrue() {
        char[][] board = convert("""
                bhfvkfhb
                vvvvvvvv
                ........
                ........
                ....K...
                ........
                ........
                ........""");
        assertFalse(validator.isValidState(board));
    }
    private char[][] convert(String field) {
        String[] lines = field.split("\n ");
        char[][] result = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            result[i] = lines[i].toCharArray();
        }
        return result;
    }
}