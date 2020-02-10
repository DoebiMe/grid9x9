package be.doubbel.sudo.service;

import be.doubbel.sudo.common.RowCol;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudoServiceTest {
    public SudoService sudoService;

    @Before
    public void beforeTest() {
		sudoService = SudoService.getInstance();
		sudoService.initialize9x9();
		//System.out.println("Is null = " + sudoService==null);
    }
    @Test
	public void initialize9x9() {
		sudoService = SudoService.getInstance();
    	sudoService.initialize9x9();
	}

    @Test
    public void getSetBits() {
        // we do not us '0', the sudo values start from '1'
        List<Integer> list1 = new ArrayList<>(List.of(1, 2));
        assertEquals(sudoService.getSetBits(6), list1);

        List<Integer> list2 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(sudoService.getSetBits(1022), list2);
    }

    @Test
    public void setCellCandidateValues() {
        sudoService.initialize9x9();

        // we do not us '0', the sudo values start from '1'
        List<Integer> list1 = new ArrayList<>(List.of(1, 2, 3, 7, 9));
        sudoService.replaceCellCandidateValues(0, 0, list1);
        List<Integer> result1 = sudoService.getCellCandidatesValues(0, 0);
        assertEquals(list1, result1);
        assertTrue(result1.contains(3));
        assertTrue(result1.contains(7));
        assertFalse(result1.contains(6));
        assertTrue(result1.contains(9));
    }

    @Test
    public void getCellValue() {
        sudoService.initialize9x9();

        sudoService.setCellValueAffectCandidates(0, 0, 5);
        assertNotEquals(8, (int) sudoService.getCellValue(0, 0));
        assertEquals(5, (int) sudoService.getCellValue(0, 0));

        sudoService.setCellValueAffectCandidates(0, 0, 4);
        assertNotEquals(9, (int) sudoService.getCellValue(0, 0));
        assertEquals(4, (int) sudoService.getCellValue(0, 0));

        sudoService.setCellValueAffectCandidates(8, 2, 3);
        assertNotEquals(8, (int) sudoService.getCellValue(8, 2));
        assertEquals(3, (int) sudoService.getCellValue(8, 2));

        sudoService.setCellValueAffectCandidates(2, 8, 9);
        assertNotEquals(3, (int) sudoService.getCellValue(2, 8));
        assertEquals(9, (int) sudoService.getCellValue(2, 8));
    }

    @Test
    public void setCellValueAffectCandidates() {
        sudoService.initialize9x9();

        List<Integer> candidateList1 = new ArrayList<>(List.of(1, 2, 3));
        sudoService.replaceCellCandidateValues(0, 0, candidateList1);
        sudoService.setCellValueAffectCandidates(0, 0, 0);
        assertEquals(sudoService.getCellCandidatesValues(0, 0), candidateList1);

        sudoService.replaceCellCandidateValues(1, 1, candidateList1);
        sudoService.setCellValueAffectCandidates(1, 1, 9);
        assertEquals(9, (int) sudoService.getCellValue(1, 1));

        sudoService.setCellValueAffectCandidates(8, 8, 9);
        assertEquals(9, (int) sudoService.getCellValue(8, 8));

        sudoService.setCellValueAffectCandidates(8, 3, 7);
        assertEquals(7, (int) sudoService.getCellValue(8, 3));
    }

    @Test
    public void getRowValues() {
        sudoService.initialize9x9();

        Integer[] values = {1, 2, 0, 0, 5, 6, 0, 0, 9};

        sudoService.setCellValueAffectCandidates(8, 0, 1);
        sudoService.setCellValueAffectCandidates(8, 1, 2);
        sudoService.setCellValueAffectCandidates(8, 2, 0);
        sudoService.setCellValueAffectCandidates(8, 3, 0);
        sudoService.setCellValueAffectCandidates(8, 4, 5);
        sudoService.setCellValueAffectCandidates(8, 5, 6);
        sudoService.setCellValueAffectCandidates(8, 6, 0);
        sudoService.setCellValueAffectCandidates(8, 7, 0);
        sudoService.setCellValueAffectCandidates(8, 8, 9);
        assertEquals(values, sudoService.getRowValues(8));
        assertNotEquals(values, sudoService.getRowValues(7));
    }

    @Test
    public void getColValues() {
        sudoService.initialize9x9();
        Integer[] values = {1, 2, 0, 0, 5, 6, 0, 0, 9};

        sudoService.setCellValueAffectCandidates(0, 7, 1);
        sudoService.setCellValueAffectCandidates(1, 7, 2);
        sudoService.setCellValueAffectCandidates(2, 7, 0);
        sudoService.setCellValueAffectCandidates(3, 7, 0);
        sudoService.setCellValueAffectCandidates(4, 7, 5);
        sudoService.setCellValueAffectCandidates(5, 7, 6);
        sudoService.setCellValueAffectCandidates(6, 7, 0);
        sudoService.setCellValueAffectCandidates(7, 7, 0);
        sudoService.setCellValueAffectCandidates(8, 7, 9);
        assertEquals(values, sudoService.getColValues(7));
        assertNotEquals(values, sudoService.getColValues(6));
    }

    @Test
    public void getSquareValues() {
        sudoService.initialize9x9();
        Integer[] values = {1, 2, 0, 0, 5, 6, 0, 0, 9};

        sudoService.setCellValueAffectCandidates(6, 3, 1);
        sudoService.setCellValueAffectCandidates(6, 4, 2);
        sudoService.setCellValueAffectCandidates(6, 5, 0);
        sudoService.setCellValueAffectCandidates(7, 3, 0);
        sudoService.setCellValueAffectCandidates(7, 4, 5);
        sudoService.setCellValueAffectCandidates(7, 5, 6);
        sudoService.setCellValueAffectCandidates(8, 3, 0);
        sudoService.setCellValueAffectCandidates(8, 4, 0);
        sudoService.setCellValueAffectCandidates(8, 5, 9);
        assertEquals(values, sudoService.getSquareValues(2, 1));
        assertNotEquals(values, sudoService.getSquareValues(2, 2));
    }

    @Test
    public void getCellCandidatesValue() {
        sudoService.initialize9x9();
        assertEquals(0, (int) sudoService.getCellCandidatesValue(0, 0));
        sudoService.replaceCellCandidateValues(8, 8, List.of(2, 3));
        assertEquals(12, (int) sudoService.getCellCandidatesValue(8, 8));
        sudoService.replaceCellCandidateValues(0, 8, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(1022, (int) sudoService.getCellCandidatesValue(0, 8));
    }

    @Test
    public void getCellCandidatesValues() {
        sudoService.initialize9x9();
        assertEquals(0, (int) sudoService.getCellCandidatesValue(0, 0));
        sudoService.replaceCellCandidateValues(8, 8, List.of(2, 3));
        assertEquals(List.of(2, 3), sudoService.getCellCandidatesValues(8, 8));
        sudoService.replaceCellCandidateValues(0, 8, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), sudoService.getCellCandidatesValues(0, 8));
    }

    @Test
    public void countCandidateIn3x3() {
        sudoService.initialize9x9();
        assertEquals(0, (int) sudoService.countCandidateIn3x3(0, 0, 1));
        sudoService.replaceCellCandidateValues(0, 0, List.of(1));
        assertEquals(1, (int) sudoService.countCandidateIn3x3(0, 0, 1));
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countCandidateIn3x3(0, 0, 1, resultList);
        assertTrue(resultList.get(0).equals(new RowCol(0, 0)));
    }

    @Test
    public void countCandidateIn9x9() {
        sudoService.initialize9x9();
        assertEquals(0, (int) sudoService.countCandidateIn9x9(5));
        sudoService.replaceCellCandidateValues(4, 4, List.of(5));
        assertEquals(1, (int) sudoService.countCandidateIn9x9(5));
        sudoService.replaceCellCandidateValues(8, 8, List.of(5));
        assertEquals(2, (int) sudoService.countCandidateIn9x9(5));
        sudoService.replaceCellCandidateValues(0, 0, List.of(5));
        assertEquals(3, (int) sudoService.countCandidateIn9x9(5));
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countCandidateIn9x9(5, resultList);
        System.out.println("Contains " + resultList.size());
        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(new RowCol(8, 8)));
        assertFalse(resultList.contains(new RowCol(7, 7)));
    }

    @Test
    public void countCandidateInRow() {
        sudoService.initialize9x9();
        assertEquals(0, (int) sudoService.countCandidateInRow(0, 5));
        sudoService.replaceCellCandidateValues(8, 4, List.of(5));
        assertEquals(1, (int) sudoService.countCandidateInRow(8, 5));
        sudoService.replaceCellCandidateValues(8, 8, List.of(5, 7, 8));
        assertEquals(2, (int) sudoService.countCandidateInRow(8, 5));
        sudoService.replaceCellCandidateValues(8, 0, List.of(5, 6));
        assertEquals(3, (int) sudoService.countCandidateInRow(8, 5));
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countCandidateInRow(8, 5, resultList);
        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(new RowCol(8, 4)));
    }

    @Test
    public void countCandidateInCol() {
        sudoService.initialize9x9();
        assertEquals(0, (int) sudoService.countCandidateInCol(0, 5));
        sudoService.replaceCellCandidateValues(2, 8, List.of(5));
        assertEquals(1, (int) sudoService.countCandidateInCol(8, 5));
        sudoService.replaceCellCandidateValues(5, 8, List.of(5, 7, 8));
        assertEquals(2, (int) sudoService.countCandidateInCol(8, 5));
        sudoService.replaceCellCandidateValues(7, 8, List.of(5, 6));
        assertEquals(3, (int) sudoService.countCandidateInCol(8, 5));
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countCandidateInCol(8, 5, resultList);
        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(new RowCol(5, 8)));
    }

    @Test
    public void countEmptyCellsIn3x3() {
        sudoService.initialize9x9();
        assertEquals((int) sudoService.countEmptyCellsIn3x3(1, 0), 9);
        sudoService.setCellValueAffectCandidates(4, 0, 5);
        assertEquals((int) sudoService.countEmptyCellsIn3x3(1, 0), 8);
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countEmptyCellsIn3x3(1, 0, resultList);
        assertFalse(resultList.contains(new RowCol(4, 0)));
        assertTrue(resultList.contains(new RowCol(4, 1)));
    }

    @Test
    public void countEmptyCellsIn9x9() {
        sudoService.initialize9x9();
        assertEquals(81, (int) sudoService.countEmptyCellsIn9x9());
        sudoService.setCellValueAffectCandidates(8, 0, 1);
        assertEquals(80, (int) sudoService.countEmptyCellsIn9x9());
        sudoService.setCellValueAffectCandidates(4, 1, 2);
        sudoService.setCellValueAffectCandidates(3, 4, 3);
        sudoService.setCellValueAffectCandidates(0, 0, 4);
        sudoService.setCellValueAffectCandidates(5, 8, 5);
        sudoService.setCellValueAffectCandidates(1, 1, 6);
        sudoService.setCellValueAffectCandidates(7, 7, 7);
        sudoService.setCellValueAffectCandidates(6, 2, 8);
        sudoService.setCellValueAffectCandidates(8, 8, 9);
        assertEquals(72, (int) sudoService.countEmptyCellsIn9x9());
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countEmptyCellsIn9x9(resultList);
        assertEquals(72, resultList.size());
        assertFalse(resultList.contains(new RowCol(8, 8)));
        assertTrue(resultList.contains(new RowCol(8, 7)));
    }

    @Test
    public void countEmptyCellsInRow() {
        sudoService.initialize9x9();
        assertEquals(9, (int) sudoService.countEmptyCellsInRow(3));
        sudoService.setCellValueAffectCandidates(8, 0, 1);
        assertEquals(8, (int) sudoService.countEmptyCellsInRow(8));
        sudoService.setCellValueAffectCandidates(8, 1, 2);
        sudoService.setCellValueAffectCandidates(8, 2, 3);
        sudoService.setCellValueAffectCandidates(8, 3, 4);
        sudoService.setCellValueAffectCandidates(8, 4, 5);
        sudoService.setCellValueAffectCandidates(8, 5, 6);
        sudoService.setCellValueAffectCandidates(8, 6, 7);
        sudoService.setCellValueAffectCandidates(8, 7, 8);
        sudoService.setCellValueAffectCandidates(8, 8, 9);
        assertEquals(0, (int) sudoService.countEmptyCellsInRow(8));
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countEmptyCellsInRow(8, resultList);
        assertEquals(0, resultList.size());
        sudoService.setCellValueAffectCandidates(8, 7, 0);
        sudoService.countEmptyCellsInRow(8, resultList);
        assertTrue(resultList.contains(new RowCol(8, 7)));
    }

    @Test
    public void countEmptyCellsInCol() {
        sudoService.initialize9x9();
        assertEquals(9, (int) sudoService.countEmptyCellsInCol(0));
        sudoService.setCellValueAffectCandidates(0, 0, 1);
        assertEquals(8, (int) sudoService.countEmptyCellsInCol(0));
        sudoService.setCellValueAffectCandidates(1, 0, 2);
        sudoService.setCellValueAffectCandidates(2, 0, 3);
        sudoService.setCellValueAffectCandidates(3, 0, 4);
        sudoService.setCellValueAffectCandidates(4, 0, 5);
        sudoService.setCellValueAffectCandidates(5, 0, 6);
        sudoService.setCellValueAffectCandidates(6, 0, 7);
        sudoService.setCellValueAffectCandidates(7, 0, 8);
        sudoService.setCellValueAffectCandidates(8, 0, 9);
        assertEquals(0, (int) sudoService.countEmptyCellsInCol(0));
        List<RowCol> resultList = new ArrayList<>();
        sudoService.countEmptyCellsInCol(0, resultList);
        assertEquals(0, resultList.size());
        sudoService.setCellValueAffectCandidates(8, 0, 0);
        sudoService.countEmptyCellsInCol(0, resultList);
        assertTrue(resultList.contains(new RowCol(8, 0)));
    }

    @Test
    public void replaceCellCandidateValue() {
        sudoService.replaceCellCandidateValue(7, 8, 6);
        assertEquals(6, (int) sudoService.getCellCandidatesValue(7, 8));
        assertEquals(List.of(1, 2), sudoService.getCellCandidatesValues(7, 8));
    }

    @Test
    public void initializeSudo() {
        sudoService.replaceCellCandidateValues(3, 3, List.of(2, 4, 6));
        sudoService.setCellValueAffectCandidates(8, 1, 2);
        assertEquals(List.of(2, 4, 6), sudoService.getCellCandidatesValues(3, 3));
        assertEquals(2, (int) sudoService.getCellValue(8, 1));

        sudoService.initialize9x9();
        assertNotEquals(List.of(2, 4, 6), sudoService.getCellCandidatesValues(3, 3));
        assertNotEquals(2, (int) sudoService.getCellValue(8, 1));
    }

    @Test
    public void removeCellCandidateValue() {
        sudoService.initialize9x9();
        sudoService.replaceCellCandidateValues(8, 8, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), sudoService.getCellCandidatesValues(8, 8));
        sudoService.removeCellCandidateValue(8, 8, 1);
        sudoService.replaceCellCandidateValues(8, 8, List.of(2, 3, 4, 5, 6, 7, 8, 9));
        assertNotEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), sudoService.getCellCandidatesValues(8, 8));
        assertEquals(List.of(2, 3, 4, 5, 6, 7, 8, 9), sudoService.getCellCandidatesValues(8, 8));
    }

    @Test
    public void addCellCandidateValue() {
        //ToDo
    }


}
