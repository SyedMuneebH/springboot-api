package com.example.springbootapi.leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TwoSumTest {

    private TwoSum twoSum = new TwoSum();

    @Test
    public void testTwoSumBasic() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum.twoSum(nums, target);
        
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(target, nums[result[0]] + nums[result[1]]);
    }

    @Test
    public void testTwoSumWithNegatives() {
        int[] nums = {3, 2, 4};
        int target = 6;
        int[] result = twoSum.twoSum(nums, target);
        
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
        assertEquals(target, nums[result[0]] + nums[result[1]]);
    }

    @Test
    public void testTwoSumDuplicates() {
        int[] nums = {3, 3};
        int target = 6;
        int[] result = twoSum.twoSum(nums, target);
        
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(target, nums[result[0]] + nums[result[1]]);
    }

    @Test
    public void testTwoSumNoSolution() {
        int[] nums = {1, 2, 3};
        int target = 10;
        
        assertThrows(IllegalArgumentException.class, () -> {
            twoSum.twoSum(nums, target);
        });
    }

    @Test
    public void testTwoSumNegativeNumbers() {
        int[] nums = {-1, -2, -3, 5, 10};
        int target = 7;
        int[] result = twoSum.twoSum(nums, target);
        
        assertNotNull(result);
        assertEquals(target, nums[result[0]] + nums[result[1]]);
    }
}
