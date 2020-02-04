package be.doubbel.sudo.service;

import org.junit.Assert;
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
        // we do not us '0', the sudo values start from '1'
        List<Integer> list1 = new ArrayList<>(List.of(1,2,3,7,9));
        SudoService.setCellCandidateValues(0,0,list1);
        List<Integer> result1 = SudoService.getCellCandidatesValues(0,0);
        assertEquals(list1,result1);
        assertTrue(result1.contains(3));
        assertTrue(result1.contains(7));
        assertFalse(result1.contains(6));
        assertTrue(result1.contains(9));
    }


}
