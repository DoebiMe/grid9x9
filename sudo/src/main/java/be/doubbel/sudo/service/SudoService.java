package be.doubbel.sudo.service;

import be.doubbel.sudo.common.RowCol;

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
				sudoField[row][col][INDEX_VALUE] = value;
				if (!cellIsEmpy(row, col)) {
						removeAllCellCandidateValues(row, col);
				}
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

		static void removeAllCellCandidateValues(Integer row, Integer col) {
				sudoField[row][col][INDEX_CANDIDATES] = 0;
		}

		static void replaceCellCandidateValue(Integer row, Integer col, Integer value) {
				sudoField[row][col][INDEX_CANDIDATES] = value;
		}

		static void replaceCellCandidateValues(Integer row, Integer col, List<Integer> candidateValues) {
				Integer result = 0;
				for (Integer candidateValue : candidateValues) {
						if (candidateValue > 0) {
										result += 2 << candidateValue - 1;
						}
				}
				sudoField[row][col][INDEX_CANDIDATES] = result;
		}

		static void removeCellCandidateValue(Integer row, Integer col, Integer candidateValue) {
				List<Integer> candidateValues = getCellCandidatesValues(row, col);
				if (candidateValues.contains(candidateValue)) {
						candidateValues.remove(candidateValue);
						//replaceCellCandidateValues(row, col, candidateValues);
				}
		}

		static void addCellCandidateValue(Integer row, Integer col, Integer candidateValue) {
				List<Integer> candidateValues = getCellCandidatesValues(row, col);
				if (!candidateValues.contains(candidateValue)) {
						candidateValues.add(candidateValue);
						replaceCellCandidateValues(row, col, candidateValues);
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

		static void initialize9x9() {
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								setCellValueAffectCandidates(row, col, 0);
								replaceCellCandidateValue(row, col, 0);
						}
				}
		}

		static void removeCandidatesWhereValueIsFilled() {
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								if (!cellIsEmpy(row, col)) {
										removeAllCellCandidateValues(row, col);
								}
						}
				}
		}

		static boolean cellIsEmpy(Integer row, Integer col) {
				return sudoField[row][col][INDEX_VALUE].equals(0);
		}

		static Integer countCandidateIn3x3(Integer row3x3, Integer col3x3, Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer row = 0; row < 3; row++) {
						for (Integer col = 0; col < 3; col++) {
								if ( getCellCandidatesValues(row3x3 * 3 + row,col3x3 * 3 + col).contains(candidate)) {
										listHasCandidate.add(new RowCol(row3x3 * 3 + row, col3x3 * 3 + col));
										count++;
								}
						}
				}
				return count;
		}

		static Integer countCandidateIn3x3(Integer row3x3, Integer col3x3, Integer candidate) {
				return countCandidateIn3x3(row3x3, col3x3, candidate, new ArrayList<>());
		}

		static Integer countCandidateIn9x9(Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								if (getCellCandidatesValues(row, col).contains(candidate)) {
										listHasCandidate.add(new RowCol(row, col));
										count++;
								}
						}
				}
				return count;
		}

		static Integer countCandidateIn9x9(Integer candidate) {
				return countCandidateIn9x9(candidate, new ArrayList<>());
		}

		static Integer countCandidateInRow(Integer row, Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer col = 0; col < 9; col++) {
						if (getCellCandidatesValues(row, col).contains(candidate)) {
								listHasCandidate.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		static Integer countCandidateInRow(Integer row, Integer candidate) {
				return countCandidateInRow(row, candidate, new ArrayList<>());
		}

		static Integer countCandidateInCol(Integer col, Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer row = 0; row < 9; row++) {
						if (getCellCandidatesValues(row, col).contains(candidate)) {
								listHasCandidate.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		static Integer countCandidateInCol(Integer col, Integer candidate) {
				return countCandidateInCol(col, candidate, new ArrayList<>());
		}

		static Integer countEmptyCellsIn3x3(Integer row3x3, Integer col3x3, List<RowCol> listEmptyCells) {
				Integer count = 0;
				for (Integer row = 0; row < 3; row++) {
						for (Integer col = 0; col < 3; col++) {
								if (sudoField[(row3x3 * 3) + row][(col3x3 * 3) + col][INDEX_VALUE].equals(0)) {
										listEmptyCells.add(new RowCol(row3x3 * 3 + row, col3x3 * 3 + col));
										count++;
								}
						}
				}
				return count;
		}

		static Integer countEmptyCellsIn3x3(Integer row3x3, Integer col3x3) {
				return countEmptyCellsIn3x3(row3x3, col3x3, new ArrayList<>());
		}

		static Integer countEmptyCellsIn9x9(List<RowCol> listEmptyCells) {
				Integer count = 0;
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								if (sudoField[row][col][INDEX_VALUE].equals(0)) {
										listEmptyCells.add(new RowCol(row, col));
										count++;
								}
						}
				}
				return count;
		}

		static Integer countEmptyCellsIn9x9() {
				return countEmptyCellsIn9x9(new ArrayList<>());
		}

		static Integer countEmptyCellsInRow(Integer row, List<RowCol> listEmptyCells) {
				Integer count = 0;
				for (Integer col = 0; col < 9; col++) {
						if (sudoField[row][col][INDEX_VALUE].equals(0)) {
								listEmptyCells.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		static Integer countEmptyCellsInRow(Integer row) {
				return countEmptyCellsInRow(row, new ArrayList<>());
		}

		static Integer countEmptyCellsInCol(Integer col, List<RowCol> listEmptyCells) {
				Integer count = 0;
				for (Integer row = 0; row < 9; row++) {
						if (sudoField[row][col][INDEX_VALUE].equals(0)) {
								listEmptyCells.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		static Integer countEmptyCellsInCol(Integer col) {
				return countEmptyCellsInCol(col, new ArrayList<>());
		}
}
