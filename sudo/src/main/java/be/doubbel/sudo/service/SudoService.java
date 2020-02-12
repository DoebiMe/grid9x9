package be.doubbel.sudo.service;

import be.doubbel.sudo.common.RowCol;

import java.util.ArrayList;
import java.util.List;

public class SudoService {

		private static SudoService instance;
		private static Integer[][][] sudoField = new Integer[9][9][3];
		public final Integer INDEX_CANDIDATES = 0;
		public final Integer INDEX_VALUE = 1;
		public final Integer INDEX_ORIGINAL_VALUE = 2;

		//dimension 1 : row, 0 = upper row
		//dimension 2 : col, 0 = left row
		//dimension 3 : index 0 : candidates, value 1 = can1,value 2 = can2, value 3 = can1 + can2 , ...
		//              index 1 : value
		//              index 2 : original value , like when sudo is loaded, the value is not changeable

		// when starting
		// always first loadOriginalValues
		// then		initialize9x9

		private Integer userSelectedRow = 0;
		private Integer userSelectedCol = 0;
		private Integer oldUserSelectedRow = 0;
		private Integer oldUserSelectedCol = 0;

		public Integer getOldUserSelectedRow() {
				return oldUserSelectedRow;
		}

		public Integer getOldUserSelectedCol() {
				return oldUserSelectedCol;
		}

		public Integer getUserSelectedRow() {
				return userSelectedRow;
		}

		public Integer getUserSelectedCol() {
				return userSelectedCol;
		}

		public SudoService setUserSelectedRowAndCol(Integer userSelectedRow, Integer userSelectedCol) {
				oldUserSelectedCol = this.userSelectedCol;
				oldUserSelectedRow = this.userSelectedRow;
				this.userSelectedRow = userSelectedRow;
				this.userSelectedCol = userSelectedCol;
				return this;
		}

		private SudoService() {
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								sudoField[row][col][INDEX_CANDIDATES] = 0;
								sudoField[row][col][INDEX_VALUE] = 0;
								sudoField[row][col][INDEX_ORIGINAL_VALUE] = 0;
						}
				}
		}

		public static SudoService getInstance() {
				// zie https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
				if (instance == null) {
						synchronized (SudoService.class) {
								if (instance == null) {
										instance = new SudoService();
								}
						}
				}
				return instance;
		}

		public Integer getCellValue(Integer row, Integer col) {
				if (getOriginalValueFromCell(row, col) == 0) {
						return sudoField[row][col][INDEX_VALUE];
				} else
						return getOriginalValueFromCell(row, col);
		}

		public void setUserSelectedCellValueAffectCanditates(Integer value) {
				setCellValueAffectCandidates(getUserSelectedRow() - 1, getUserSelectedCol() - 1, value);
				System.out.println("Set value ");
				System.out.println("row = " + (getUserSelectedRow() - 1));
				System.out.println("col = " + (getUserSelectedCol() - 1));
		}

		public void setCellValueAffectCandidates(Integer row, Integer col, Integer value) {
				if (getOriginalValueFromCell(row, col) == 0) {

						sudoField[row][col][INDEX_VALUE] = value;

						if (!cellIsEmpy(row, col)) {
								removeAllCellCandidateValues(row, col);
						}
				} else {
						System.out.println("Original value can not be overwritten");
				}
		}

		Integer[] getRowValues(Integer row) {
				Integer[] result = new Integer[9];
				for (Integer col = 0; col < 9; col++) {
						result[col] = sudoField[row][col][INDEX_VALUE];
				}
				return result;
		}

		Integer[] getColValues(Integer col) {
				Integer[] result = new Integer[9];
				for (Integer row = 0; row < 9; row++) {
						result[row] = sudoField[row][col][INDEX_VALUE];
				}
				return result;
		}

		Integer[] getSquareValues(Integer row3x3, Integer col3x3) {
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

		void removeAllCellCandidateValues(Integer row, Integer col) {
				sudoField[row][col][INDEX_CANDIDATES] = 0;
		}

		void replaceCellCandidateValue(Integer row, Integer col, Integer value) {
				sudoField[row][col][INDEX_CANDIDATES] = value;
		}

		void replaceCellCandidateValues(Integer row, Integer col, List<Integer> candidateValues) {
				Integer result = 0;
				for (Integer candidateValue : candidateValues) {
						if (candidateValue > 0) {
								result += 2 << candidateValue - 1;
						}
				}
				sudoField[row][col][INDEX_CANDIDATES] = result;
		}

		void removeCellCandidateValue(Integer row, Integer col, Integer candidateValue) {
				List<Integer> candidateValues = getCellCandidatesValues(row, col);
				if (candidateValues.contains(candidateValue)) {
						candidateValues.remove(candidateValue);
						//replaceCellCandidateValues(row, col, candidateValues);
				}
		}

		void addCellCandidateValue(Integer row, Integer col, Integer candidateValue) {
				List<Integer> candidateValues = getCellCandidatesValues(row, col);
				if (!candidateValues.contains(candidateValue)) {
						candidateValues.add(candidateValue);
						replaceCellCandidateValues(row, col, candidateValues);
				}
		}

		public Integer getCellCandidatesValue(Integer row, Integer col) {
				return sudoField[row][col][INDEX_CANDIDATES];
		}

		public List<Integer> getCellCandidatesValues(Integer row, Integer col) {
				return getSetBits(getCellCandidatesValue(row, col));
		}

		public List<Integer> getSetBits(Integer value) {
				List<Integer> result = new ArrayList<>();
				for (Integer lus = 0; lus < 10; lus++) {
						Integer bitValue = 1 << lus;
						if ((value | bitValue) == value) {
								result.add(lus);
						}
				}
				return result;
		}

		public Integer getOriginalValueFromCell(Integer row, Integer col) {
				return sudoField[row][col][INDEX_ORIGINAL_VALUE];
		}

		void initialize9x9() {
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								setCellValueAffectCandidates(row, col, getOriginalValueFromCell(row, col));
								replaceCellCandidateValue(row, col, 0);
						}
				}
		}

		public void loadOriginalValues(String recourceName) {
				FileService fileService = FileService.getInstance();
				Integer[][] resource = fileService.readSudo(recourceName);
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								sudoField[row][col][INDEX_ORIGINAL_VALUE] = resource[row][col];
								setCellValueAffectCandidates(row, col, resource[row][col]);
						}
				}
		}

		public void resetToOriginalValues() {
				for (Integer row = 0; row<9;row++) {
						for (Integer col=0;col<9;col++) {
								setCellValueAffectCandidates(row,col,getOriginalValueFromCell(row,col));
						}
				}
		}

		public void setAllCandidatesInCellWithoutValue() {
				List<Integer> allCandidates = List.of(1,2,3,4,5,6,7,8,9);
				for (Integer row = 0; row<9;row++) {
						for (Integer col=0;col<9;col++) {
								if (cellIsEmpy(row,col)) {
										replaceCellCandidateValues(row,col,allCandidates);
								}
						}
				}
		}

		void removeCandidatesWhereValueIsFilled() {
				for (Integer row = 0; row < 9; row++) {
						for (Integer col = 0; col < 9; col++) {
								if (!cellIsEmpy(row, col)) {
										removeAllCellCandidateValues(row, col);
								}
						}
				}
		}

		boolean cellIsEmpy(Integer row, Integer col) {
				return sudoField[row][col][INDEX_VALUE].equals(0);
		}

		Integer countCandidateIn3x3(Integer row3x3, Integer col3x3, Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer row = 0; row < 3; row++) {
						for (Integer col = 0; col < 3; col++) {
								if (getCellCandidatesValues(row3x3 * 3 + row, col3x3 * 3 + col).contains(candidate)) {
										listHasCandidate.add(new RowCol(row3x3 * 3 + row, col3x3 * 3 + col));
										count++;
								}
						}
				}
				return count;
		}

		Integer countCandidateIn3x3(Integer row3x3, Integer col3x3, Integer candidate) {
				return countCandidateIn3x3(row3x3, col3x3, candidate, new ArrayList<>());
		}

		Integer countCandidateIn9x9(Integer candidate, List<RowCol> listHasCandidate) {
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

		Integer countCandidateIn9x9(Integer candidate) {
				return countCandidateIn9x9(candidate, new ArrayList<>());
		}

		Integer countCandidateInRow(Integer row, Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer col = 0; col < 9; col++) {
						if (getCellCandidatesValues(row, col).contains(candidate)) {
								listHasCandidate.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		Integer countCandidateInRow(Integer row, Integer candidate) {
				return countCandidateInRow(row, candidate, new ArrayList<>());
		}

		Integer countCandidateInCol(Integer col, Integer candidate, List<RowCol> listHasCandidate) {
				Integer count = 0;
				for (Integer row = 0; row < 9; row++) {
						if (getCellCandidatesValues(row, col).contains(candidate)) {
								listHasCandidate.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		Integer countCandidateInCol(Integer col, Integer candidate) {
				return countCandidateInCol(col, candidate, new ArrayList<>());
		}

		Integer countEmptyCellsIn3x3(Integer row3x3, Integer col3x3, List<RowCol> listEmptyCells) {
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

		Integer countEmptyCellsIn3x3(Integer row3x3, Integer col3x3) {
				return countEmptyCellsIn3x3(row3x3, col3x3, new ArrayList<>());
		}

		Integer countEmptyCellsIn9x9(List<RowCol> listEmptyCells) {
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

		Integer countEmptyCellsIn9x9() {
				return countEmptyCellsIn9x9(new ArrayList<>());
		}

		Integer countEmptyCellsInRow(Integer row, List<RowCol> listEmptyCells) {
				Integer count = 0;
				for (Integer col = 0; col < 9; col++) {
						if (sudoField[row][col][INDEX_VALUE].equals(0)) {
								listEmptyCells.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		Integer countEmptyCellsInRow(Integer row) {
				return countEmptyCellsInRow(row, new ArrayList<>());
		}

		Integer countEmptyCellsInCol(Integer col, List<RowCol> listEmptyCells) {
				Integer count = 0;
				for (Integer row = 0; row < 9; row++) {
						if (sudoField[row][col][INDEX_VALUE].equals(0)) {
								listEmptyCells.add(new RowCol(row, col));
								count++;
						}
				}
				return count;
		}

		Integer countEmptyCellsInCol(Integer col) {
				return countEmptyCellsInCol(col, new ArrayList<>());
		}
}
