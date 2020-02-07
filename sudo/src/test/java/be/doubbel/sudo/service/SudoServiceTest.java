package be.doubbel.sudo.service;

import be.doubbel.sudo.common.RowCol;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudoServiceTest {

    @Test
    public void getSetBits() {
        // we do not us '0', the sudo values start from '1'
        List<Integer> list1 = new ArrayList<>(List.of(1,2));
        assertEquals(SudoService.getSetBits(6),list1);

        List<Integer> list2 = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
        assertEquals(SudoService.getSetBits(1022),list2);
    }

    @Test
    public void setCellCandidateValues() {
				SudoService.initialize9x9();

				// we do not us '0', the sudo values start from '1'
        List<Integer> list1 = new ArrayList<>(List.of(1,2,3,7,9));
        SudoService.replaceCellCandidateValues(0,0,list1);
        List<Integer> result1 = SudoService.getCellCandidatesValues(0,0);
        assertEquals(list1,result1);
        assertTrue(result1.contains(3));
        assertTrue(result1.contains(7));
        assertFalse(result1.contains(6));
        assertTrue(result1.contains(9));
    }

		@Test public
		void getCellValue() {
				SudoService.initialize9x9();

				SudoService.setCellValueAffectCandidates(0,0,5);
				assertNotEquals(8, (int) SudoService.getCellValue(0, 0));
				assertEquals(5, (int) SudoService.getCellValue(0, 0));

				SudoService.setCellValueAffectCandidates(0,0,4);
				assertNotEquals(9, (int) SudoService.getCellValue(0, 0));
				assertEquals(4, (int) SudoService.getCellValue(0, 0));

				SudoService.setCellValueAffectCandidates(8,2,3);
				assertNotEquals(8, (int) SudoService.getCellValue(8, 2));
				assertEquals(3, (int) SudoService.getCellValue(8, 2));

				SudoService.setCellValueAffectCandidates(2,8,9);
				assertNotEquals(3, (int) SudoService.getCellValue(2, 8));
				assertEquals(9, (int) SudoService.getCellValue(2, 8));
		}

		@Test public void setCellValueAffectCandidates() {
				SudoService.initialize9x9();

				List<Integer> candidateList1 = new ArrayList<>(List.of(1,2,3));
    		SudoService.replaceCellCandidateValues(0,0,candidateList1);
				SudoService.setCellValueAffectCandidates(0,0,0);
				assertEquals(SudoService.getCellCandidatesValues(0,0), candidateList1);

				SudoService.replaceCellCandidateValues(1,1,candidateList1);
				SudoService.setCellValueAffectCandidates(1,1,9);
				assertEquals(9, (int) SudoService.getCellValue(1, 1));

				SudoService.setCellValueAffectCandidates(8,8,9);
				assertEquals(9,(int) SudoService.getCellValue(8,8));

				SudoService.setCellValueAffectCandidates(8,3,7);
				assertEquals(7,(int) SudoService.getCellValue(8,3));
		}

		@Test public void getRowValues() {
				SudoService.initialize9x9();

				Integer[] values = {1,2,0,0,5,6,0,0,9};

				SudoService.setCellValueAffectCandidates(8,0,1);
				SudoService.setCellValueAffectCandidates(8,1,2);
				SudoService.setCellValueAffectCandidates(8,2,0);
				SudoService.setCellValueAffectCandidates(8,3,0);
				SudoService.setCellValueAffectCandidates(8,4,5);
				SudoService.setCellValueAffectCandidates(8,5,6);
				SudoService.setCellValueAffectCandidates(8,6,0);
				SudoService.setCellValueAffectCandidates(8,7,0);
				SudoService.setCellValueAffectCandidates(8,8,9);
				assertEquals(values,SudoService.getRowValues(8));
				assertNotEquals(values,SudoService.getRowValues(7));
		}

		@Test public void getColValues() {
				SudoService.initialize9x9();
				Integer[] values = {1,2,0,0,5,6,0,0,9};

				SudoService.setCellValueAffectCandidates(0,7,1);
				SudoService.setCellValueAffectCandidates(1,7,2);
				SudoService.setCellValueAffectCandidates(2,7,0);
				SudoService.setCellValueAffectCandidates(3,7,0);
				SudoService.setCellValueAffectCandidates(4,7,5);
				SudoService.setCellValueAffectCandidates(5,7,6);
				SudoService.setCellValueAffectCandidates(6,7,0);
				SudoService.setCellValueAffectCandidates(7,7,0);
				SudoService.setCellValueAffectCandidates(8,7,9);
				assertEquals(values,SudoService.getColValues(7));
				assertNotEquals(values,SudoService.getColValues(6));
		}

		@Test public void getSquareValues() {
				SudoService.initialize9x9();
				Integer[] values = {1,2,0,0,5,6,0,0,9};

				SudoService.setCellValueAffectCandidates(6,3,1);
				SudoService.setCellValueAffectCandidates(6,4,2);
				SudoService.setCellValueAffectCandidates(6,5,0);
				SudoService.setCellValueAffectCandidates(7,3,0);
				SudoService.setCellValueAffectCandidates(7,4,5);
				SudoService.setCellValueAffectCandidates(7,5,6);
				SudoService.setCellValueAffectCandidates(8,3,0);
				SudoService.setCellValueAffectCandidates(8,4,0);
				SudoService.setCellValueAffectCandidates(8,5,9);
				assertEquals(values,SudoService.getSquareValues(2,1));
				assertNotEquals(values,SudoService.getSquareValues(2,2));
		}

		@Test public void getCellCandidatesValue() {
				SudoService.initialize9x9();
				assertEquals(0,(int)SudoService.getCellCandidatesValue(0,0));
				SudoService.replaceCellCandidateValues(8,8,List.of(2,3));
				assertEquals(12,(int)SudoService.getCellCandidatesValue(8,8));
				SudoService.replaceCellCandidateValues(0,8,List.of(1,2,3,4,5,6,7,8,9));
				assertEquals(1022,(int)SudoService.getCellCandidatesValue(0,8));
		}

		@Test public void getCellCandidatesValues() {
				SudoService.initialize9x9();
				assertEquals(0,(int)SudoService.getCellCandidatesValue(0,0));
				SudoService.replaceCellCandidateValues(8,8,List.of(2,3));
				assertEquals(List.of(2,3),SudoService.getCellCandidatesValues(8,8));
				SudoService.replaceCellCandidateValues(0,8,List.of(1,2,3,4,5,6,7,8,9));
				assertEquals(List.of(1,2,3,4,5,6,7,8,9),SudoService.getCellCandidatesValues(0,8));
		}

		@Test public void countCandidateIn3x3() {
				SudoService.initialize9x9();
				assertEquals(0,(int) SudoService.countCandidateIn3x3(0,0,1));
				SudoService.replaceCellCandidateValues(0,0,List.of(1));
				assertEquals(1,(int) SudoService.countCandidateIn3x3(0,0,1));
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countCandidateIn3x3(0,0,1,resultList);
				assertTrue(resultList.get(0).equals(new RowCol(0,0)));
		}

		@Test public void countCandidateIn9x9() {
				SudoService.initialize9x9();
				assertEquals(0,(int)SudoService.countCandidateIn9x9(5));
				SudoService.replaceCellCandidateValues(4,4,List.of(5));
				assertEquals(1,(int)SudoService.countCandidateIn9x9(5));
				SudoService.replaceCellCandidateValues(8,8,List.of(5));
				assertEquals(2,(int)SudoService.countCandidateIn9x9(5));
				SudoService.replaceCellCandidateValues(0,0,List.of(5));
				assertEquals(3,(int)SudoService.countCandidateIn9x9(5));
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countCandidateIn9x9(5,resultList);
				System.out.println("Contains " +resultList.size());
				assertEquals(3, resultList.size());
				assertTrue(resultList.contains(new RowCol(8,8)));
				assertFalse(resultList.contains(new RowCol(7,7)));
		}

		@Test public void countCandidateInRow() {
				SudoService.initialize9x9();
				assertEquals(0,(int)SudoService.countCandidateInRow(0,5));
				SudoService.replaceCellCandidateValues(8,4,List.of(5));
				assertEquals(1,(int)SudoService.countCandidateInRow(8,5));
				SudoService.replaceCellCandidateValues(8,8,List.of(5,7,8));
				assertEquals(2,(int)SudoService.countCandidateInRow(8,5));
				SudoService.replaceCellCandidateValues(8,0,List.of(5,6));
				assertEquals(3,(int)SudoService.countCandidateInRow(8,5));
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countCandidateInRow(8,5,resultList);
				assertEquals(3, resultList.size());
				assertTrue(resultList.contains(new RowCol(8,4)));
		}

		@Test public void countCandidateInCol() {
				SudoService.initialize9x9();
				assertEquals(0,(int)SudoService.countCandidateInCol(0,5));
				SudoService.replaceCellCandidateValues(2,8,List.of(5));
				assertEquals(1,(int)SudoService.countCandidateInCol(8,5));
				SudoService.replaceCellCandidateValues(5,8,List.of(5,7,8));
				assertEquals(2,(int)SudoService.countCandidateInCol(8,5));
				SudoService.replaceCellCandidateValues(7,8,List.of(5,6));
				assertEquals(3,(int)SudoService.countCandidateInCol(8,5));
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countCandidateInCol(8,5,resultList);
				assertEquals(3, resultList.size());
				assertTrue(resultList.contains(new RowCol(5,8)));
		}

		@Test public void countEmptyCellsIn3x3() {
				SudoService.initialize9x9();
				assertEquals((int)SudoService.countEmptyCellsIn3x3(1, 0), 9);
				SudoService.setCellValueAffectCandidates(4,0,5);
				assertEquals((int)SudoService.countEmptyCellsIn3x3(1, 0), 8);
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countEmptyCellsIn3x3(1,0,resultList);
				assertFalse(resultList.contains(new RowCol(4,0)));
				assertTrue(resultList.contains(new RowCol(4,1)));
		}

		@Test public void countEmptyCellsIn9x9() {
				SudoService.initialize9x9();
				assertEquals(81,(int)SudoService.countEmptyCellsIn9x9());
				SudoService.setCellValueAffectCandidates(8,0,1);
				assertEquals(80,(int)SudoService.countEmptyCellsIn9x9());
				SudoService.setCellValueAffectCandidates(4,1,2);
				SudoService.setCellValueAffectCandidates(3,4,3);
				SudoService.setCellValueAffectCandidates(0,0,4);
				SudoService.setCellValueAffectCandidates(5,8,5);
				SudoService.setCellValueAffectCandidates(1,1,6);
				SudoService.setCellValueAffectCandidates(7,7,7);
				SudoService.setCellValueAffectCandidates(6,2,8);
				SudoService.setCellValueAffectCandidates(8,8,9);
				assertEquals(72,(int)SudoService.countEmptyCellsIn9x9());
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countEmptyCellsIn9x9(resultList);
				assertEquals(72, resultList.size());
				assertFalse(resultList.contains(new RowCol(8,8)));
				assertTrue(resultList.contains(new RowCol(8,7)));
		}

		@Test public void countEmptyCellsInRow() {
				SudoService.initialize9x9();
				assertEquals(9,(int)SudoService.countEmptyCellsInRow(3));
				SudoService.setCellValueAffectCandidates(8,0,1);
				assertEquals(8,(int)SudoService.countEmptyCellsInRow(8));
				SudoService.setCellValueAffectCandidates(8,1,2);
				SudoService.setCellValueAffectCandidates(8,2,3);
				SudoService.setCellValueAffectCandidates(8,3,4);
				SudoService.setCellValueAffectCandidates(8,4,5);
				SudoService.setCellValueAffectCandidates(8,5,6);
				SudoService.setCellValueAffectCandidates(8,6,7);
				SudoService.setCellValueAffectCandidates(8,7,8);
				SudoService.setCellValueAffectCandidates(8,8,9);
				assertEquals(0,(int)SudoService.countEmptyCellsInRow(8));
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countEmptyCellsInRow(8,resultList);
				assertEquals(0, resultList.size());
				SudoService.setCellValueAffectCandidates(8,7,0);
				SudoService.countEmptyCellsInRow(8,resultList);
				assertTrue(resultList.contains(new RowCol(8,7)));
		}

		@Test public void countEmptyCellsInCol() {
				SudoService.initialize9x9();
				assertEquals(9,(int)SudoService.countEmptyCellsInCol(0));
				SudoService.setCellValueAffectCandidates(0,0,1);
				assertEquals(8,(int)SudoService.countEmptyCellsInCol(0));
				SudoService.setCellValueAffectCandidates(1,0,2);
				SudoService.setCellValueAffectCandidates(2,0,3);
				SudoService.setCellValueAffectCandidates(3,0,4);
				SudoService.setCellValueAffectCandidates(4,0,5);
				SudoService.setCellValueAffectCandidates(5,0,6);
				SudoService.setCellValueAffectCandidates(6,0,7);
				SudoService.setCellValueAffectCandidates(7,0,8);
				SudoService.setCellValueAffectCandidates(8,0,9);
				assertEquals(0,(int)SudoService.countEmptyCellsInCol(0));
				List<RowCol> resultList = new ArrayList<>();
				SudoService.countEmptyCellsInCol(0,resultList);
				assertEquals(0, resultList.size());
				SudoService.setCellValueAffectCandidates(8,0,0);
				SudoService.countEmptyCellsInCol(0,resultList);
				assertTrue(resultList.contains(new RowCol(8,0)));
		}

		@Test public void replaceCellCandidateValue() {
    		SudoService.replaceCellCandidateValue(7,8,6);
    		assertEquals(6,(int)SudoService.getCellCandidatesValue(7,8));
    		assertEquals(List.of(1,2),SudoService.getCellCandidatesValues(7,8));
		}

		@Test public void initializeSudo() {
    		SudoService.replaceCellCandidateValues(3,3,List.of(2,4,6));
    		SudoService.setCellValueAffectCandidates(8,1,2);
    		assertEquals(List.of(2,4,6),SudoService.getCellCandidatesValues(3,3));
    		assertEquals(2,(int) SudoService.getCellValue(8,1));

    		SudoService.initialize9x9();
				assertNotEquals(List.of(2,4,6),SudoService.getCellCandidatesValues(3,3));
				assertNotEquals(2,(int) SudoService.getCellValue(8,1));
		}

		@Test public void removeCellCandidateValue() {
    		SudoService.initialize9x9();
    		SudoService.replaceCellCandidateValues(8,8,List.of(1,2,3,4,5,6,7,8,9));
    		assertEquals(List.of(1,2,3,4,5,6,7,8,9),SudoService.getCellCandidatesValues(8,8));
    		SudoService.removeCellCandidateValue(8,8,1);
				SudoService.replaceCellCandidateValues(8,8,List.of(2,3,4,5,6,7,8,9));
				assertNotEquals(List.of(1,2,3,4,5,6,7,8,9),SudoService.getCellCandidatesValues(8,8));
    		assertEquals(List.of(2,3,4,5,6,7,8,9),SudoService.getCellCandidatesValues(8,8));
		}

		@Test public void addCellCandidateValue() {
    		//ToDo
		}
}
