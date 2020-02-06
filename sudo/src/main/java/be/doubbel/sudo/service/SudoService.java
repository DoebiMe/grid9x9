package be.doubbel.sudo.service;

import java.util.ArrayList;
import java.util.List;

public class SudoService {
    
    static Integer[][][] sudoField = new Integer[9][9][3];
    public static final Integer INDEX_CANDIDATES = 0;
    public static final Integer INDEX_VALUE = 1;
    public static final Integer INDEX_ORIGINAL_VALUE = 2;

    //dimension 1 : row, 0 = upper row
    //dimension 2 : col, 0 = left row
    //dimension 3 : index 0 : candidates, value 1 = can1,value 2 = can2, value 3 = can1 + can2 , ...
    //              index 1 : value
    //              index 2 : original value , like when sudo is loaded, the value is not changeable
    public static Integer getCellValue(Integer row, Integer col) {
        return sudoField[row][col][INDEX_VALUE];
    }

    static void setCellValueAffectCandidates(Integer row, Integer col, Integer value) {
        if (value > 0) {
            sudoField[row][col][INDEX_CANDIDATES] = 0;
        }
        sudoField[row][col][INDEX_VALUE] = value;
    }

    static Integer[] getRowValues(Integer row) {
        Integer[] result = new Integer[9];
        for (Integer col = 0; col < 9; col++) {
            result[col] = sudoField[row][col][INDEX_VALUE];
        }
        return result;
    }

    static Integer[] getColValues(Integer col) {
        Integer[] result = new Integer[9];
        for (Integer row = 0; row < 9; row++) {
            result[row] = sudoField[row][col][INDEX_VALUE];
        }
        return result;
    }

    static Integer[] getSquareValues(Integer row3x3, Integer col3x3) {
        Integer[] result = new Integer[9];
        Integer index = 0;
        for (Integer row = 0; row < 3; row++) {
            for (Integer col = 0; col < 3; col++) {
                result[index] = sudoField[(row3x3 * 3) + row][(col3x3 * 3) + col][INDEX_VALUE];
                index++;
            }
        }
        return result;
    }

    static void replaceCellCandidateValue(Integer row,Integer col,Integer value) {
        sudoField[row][col][INDEX_CANDIDATES] = value;
    }

    static void replaceCellCandidateValues(Integer row, Integer col, List<Integer> candidateValues) {
        Integer result = 0;
        for (Integer candidateValue : candidateValues) {
            if (candidateValue > 0) {
                if (!getCellCandidatesValues(row,col).contains(candidateValue)) {
                    result += 2 << candidateValue - 1;
                }
            }
        }
        sudoField[row][col][INDEX_CANDIDATES] = result;
    }

    static void removeCellCandidateValue(Integer row,Integer col,Integer candidateValue) {
        List<Integer> candidateValues = getCellCandidatesValues(row,col);
        if (candidateValues.contains(candidateValue)) {
            candidateValues.remove(candidateValue);
            replaceCellCandidateValues(row, col, candidateValues);
        }
    }

    static void addCellCandidateValue(Integer row,Integer col,Integer candidateValue) {
        List<Integer> candidateValues = getCellCandidatesValues(row,col);
        if (!candidateValues.contains(candidateValue)) {
            candidateValues.add(candidateValue);
            replaceCellCandidateValues(row,col,candidateValues);
        }
    }


    static Integer getCellCandidatesValue(Integer row, Integer col) {

        return sudoField[row][col][INDEX_CANDIDATES];
    }

    static List<Integer> getCellCandidatesValues(Integer row, Integer col) {

        return getSetBits(getCellCandidatesValue(row, col));
    }

    static List<Integer> getSetBits(Integer value) {
        List<Integer> result = new ArrayList<>();
        for (Integer lus = 0; lus < 10; lus++) {
            Integer bitValue = 1 << lus;
            if ((value | bitValue) == value) {
                result.add(lus);
            }
        }
        return result;
    }

    static void initializeSudo() {
        for (Integer row = 0;row<9;row++) {
            for (Integer col = 0;col<9;col++) {
                setCellValueAffectCandidates(row,col,0);
                replaceCellCandidateValue(row,col,0);
            }
        }
    }

    static Integer countCandidateIn3x3(Integer row3x3, Integer col3x3, Integer candidate) {
        Integer result = 0;
        for (Integer row = 0; row < 3; row++) {
            for (Integer col = 0; col < 3; col++) {
                if (sudoField[(row3x3 * 3) + row][(col3x3 * 3) + col][INDEX_CANDIDATES].equals(candidate)) {
                    result++;
                }
            }
        }
        return result;
    }

    static Integer countCandidateIn9x9(Integer candidate) {
        Integer result = 0;
        for (Integer row = 0; row < 9; row++) {
            for (Integer col = 0; col < 9; col++) {
                if (getCellCandidatesValues(row,col).contains(candidate)) {
                    result++;
                }
            }
        }
        return result;
    }

    static Integer countCandidateInRow(Integer row, Integer candidate) {
        Integer result = 0;
        for (Integer col = 0; col < 9; col++) {
            if (getCellCandidatesValues(row,col).contains(candidate)) {
                result++;
            }
        }
        return result;
    }

    static Integer countCandidateInCol(Integer col, Integer candidate) {
        Integer result = 0;
        for (Integer row = 0; row < 9; row++) {
            if (getCellCandidatesValues(row,col).contains(candidate)) {
                result++;
            }
        }
        return result;
    }

    static Integer countEmptyCellsIn3x3(Integer row, Integer col) {
        Integer result = 0;
        return result;
    }

    static Integer countEmptyCellsIn9x9() {
        Integer result = 0;
        for (Integer row = 0; row < 9; row++) {
            for (Integer col = 0; col < 9; col++) {
                if (sudoField[row][col][INDEX_VALUE].equals(0)) {
                    result++;
                }
            }
        }
        return result;
    }

    static Integer countEmptyCellsInRow(Integer row) {
        Integer result = 0;
        for (Integer loop = 0; loop < 9; loop++) {
            if (sudoField[row][loop][INDEX_VALUE].equals(0)) {
                result++;
            }
        }
        return result;
    }

    static Integer countEmptyCellsInCol(Integer col) {
        Integer result = 0;
        for (Integer loop = 0; loop < 9; loop++) {
            if (sudoField[loop][col][INDEX_VALUE].equals(0)) {
                result++;
            }
        }
        return result;
    }
}
